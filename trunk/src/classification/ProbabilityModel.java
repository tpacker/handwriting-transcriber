package classification;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import Jama.*;



public class ProbabilityModel
{
	public static final double smoothingConstant = 0.5;
	public int featureCount = 0;
	public int[] cellStateOrder = null;
	public int[] stateCellOrder = null;

	
	
	// State transition model.
	
	/**
	 * Maps cell/column position (int between 0 and 2) to a set of values for that column (each 
	 * value maps to its integer index.
	 * So: state/cell number -> state value string / transcription, state value -> state value index / position in matrix
	 * Ordered by state order, not original column order.
	 */
	private Map<Integer, HashMap<String, Integer>> stateValues = new HashMap<Integer, HashMap<String, Integer>>();
	
	/**
	 * Ordered by state order, not original column order.
	 */
	private Map<Integer, int[][]> stateTransitionStats = new HashMap<Integer, int[][]>();

	/**
	 * How many total observations of any pair of state values were seen.
	 */
	private Map<Integer, Integer> stateTransitionNormalizers = new HashMap<Integer, Integer>();
	
	/**
	 * Ordered by state order, not original column order.
	 */
	private Map<Integer, int[]> stateStats = new HashMap<Integer, int[]>();
	
	/**
	 * How many total observations of any value of each state were seen.
	 */
	private Map<Integer, Integer> stateNormalizers = new HashMap<Integer, Integer>();
	
	
	// Observation model.
	
	/**
	 * cell/state number -> mean of each feature indexed by position (same position as feature vector).
	 * Ordered by state order, not original column order.
	 */
	private Map<Integer, double[]> stateFeatureMeans = new HashMap<Integer, double[]>();
	
	/**
	 * Ordered by state order, not original column order.
	 */
	private Map<Integer, double[][]> stateFeatureCovariances = new HashMap<Integer, double[][]>();
	
	/**
	 * Ordered by state order, not original column order.
	 */
	private Map<Integer, Matrix> stateFeatureCovarianceMatrices = new HashMap<Integer, Matrix>();
	
	/**
	 * Ordered by state order, not original column order.
	 */
	private Map<Integer, Matrix> stateFeatureInverseCovarianceMatrices = new HashMap<Integer, Matrix>();
	
	/**
	 * Ordered by state order, not original column order.
	 */
	private Map<Integer, Double> determinants = new HashMap<Integer, Double>();
	
	
	
	public void buildTransitionModel(ArrayList<RecordRow> trainSet, int[] cellStateOrder)
	{
		// Record our orderings.
		this.cellStateOrder = cellStateOrder;
		stateCellOrder = new int[cellStateOrder.length];
		
		for (int cellPos = 0; cellPos < trainSet.get(0).getCells().size(); cellPos++)
		{
			// Create the map of state values of the right size.
			stateValues.put(cellPos, new HashMap<String, Integer>());
			
			// Record the reverse mapping from state number to cell number.
			for (int statePos = 0; statePos < cellStateOrder.length; statePos++)
			{
				if (cellStateOrder[cellPos] == statePos)
				{
					stateCellOrder[statePos] = cellPos;
				}
			}
		}
		
		// Records the number of values seen so far so we can give each value a unique ID/index.
		// The order is in original column order, not state order.
		int[] stateValueCounts = new int[cellStateOrder.length];
		
		// Record all values of all states.
		for (RecordRow row : trainSet)
		{
			ArrayList<RecordCell> cells = row.getCells();
			for (int cellPos = 0; cellPos < cells.size(); cellPos++)
			{
				String transcription = cells.get(cellPos).getTranscription();
				if (!stateValues.get(cellStateOrder[cellPos]).containsKey(transcription))
				{
					// Record index and state name pairs for each cell of this row.
					stateValues.get(cellStateOrder[cellPos]).put(transcription, stateValueCounts[cellPos]++);
				}
			}
		}
		
		for (int cellPos = 0; cellPos < cellStateOrder.length; cellPos++)
		{
			// Count state statistics for marginal probabilities.
			
			// Create state array of the rigiht size.
			int size = stateValues.get(cellStateOrder[cellPos]).size();
			stateStats.put(cellStateOrder[cellPos], new int[size]);
			
			// Fill in the stats table.  The indices into the table are found in the stateValue map, 
			// indexed by cell number and then by state name (i.e. transcription).  
			for (RecordRow row : trainSet)
			{
				ArrayList<RecordCell> cells = row.getCells();
				String transcription = cells.get(cellPos).getTranscription();
				int valueIndex = stateValues.get(cellStateOrder[cellPos]).get(transcription);
				
				stateStats.get(cellStateOrder[cellPos])[valueIndex]++;
				stateNormalizers.put(cellStateOrder[cellPos], (stateNormalizers.containsKey(cellStateOrder[cellPos]) ? stateNormalizers.get(cellStateOrder[cellPos]) : 0) + 1);
			}
			
			// Count transition statistics. 
			
			// Don't try to make a transition matrix for the last state.
			if (cellStateOrder[cellPos] < cellStateOrder.length - 1)
			{
				// Create the map of state transition probabilities of the right size.
				// The size of each 2-dimensional array is the number of state values for one state by the 
				// number of values for the next state, given the ordering of states in the stateOrder parameters.
				int size1 = stateValues.get(cellStateOrder[cellPos]).size();
				int size2 = stateValues.get(cellStateOrder[cellPos] + 1).size();
				stateTransitionStats.put(cellStateOrder[cellPos], new int[size1][size2]);
				
				// Fill in the stats table.  The indices into the table are found in the stateValue map, 
				// indexed by cell number and then by state name (i.e. transcription).  
				for (RecordRow row : trainSet)
				{
					ArrayList<RecordCell> cells = row.getCells();
					String transcription1 = cells.get(cellPos).getTranscription();
					String transcription2 = cells.get(stateCellOrder[cellStateOrder[cellPos] + 1]).getTranscription();
					int valueIndex1 = stateValues.get(cellStateOrder[cellPos]).get(transcription1);
					int valueIndex2 = stateValues.get(cellStateOrder[cellPos] + 1).get(transcription2);
					
					stateTransitionStats.get(cellStateOrder[cellPos])[valueIndex1][valueIndex2]++;
					stateTransitionNormalizers.put(cellStateOrder[cellPos], (stateTransitionNormalizers.containsKey(cellStateOrder[cellPos]) ? stateTransitionNormalizers.get(cellStateOrder[cellPos]) : 0) + 1);
				}
			}
		}
		
		//iraykhel 12/02
		System.out.println("State transition counts:");
		printStateTransitionStats(cellStateOrder);
	}
	
	
	//iraykhel 12/02: cutesy print transition matrix
	public void printStateTransitionStats(int[] cellStateOrder)
	{
		for (int cellPos = 0; cellPos < cellStateOrder.length; cellPos++)
		{
			if (cellStateOrder[cellPos] < cellStateOrder.length - 1)
			{
				HashMap<String,Integer> valToIndexMap = stateValues.get(cellStateOrder[cellPos]);
				Set<Entry<String,Integer>> vtiSet = valToIndexMap.entrySet();
				HashMap<String,Integer> valToIndexMapNext = stateValues.get(cellStateOrder[cellPos] + 1);
				Set<Entry<String,Integer>> vtiSetNext = valToIndexMapNext.entrySet();
				
				for (Entry<String,Integer> vtiEntry : vtiSet)
				{
					for (Entry<String,Integer> vtiEntryNext : vtiSetNext)
					{
						int count = stateTransitionStats.get(cellStateOrder[cellPos])[vtiEntry.getValue()][vtiEntryNext.getValue()];
						System.out.println(vtiEntry.getKey() + "->" + vtiEntryNext.getKey() + ": " + count);
					}
				}
			}
		}
	}
	
	
	/**
	 * Computes feature value means and covariances to be used below in computing probabilities based on Gaussian distribution assumptions.
	 * @param trainSet
	 * @param cellStateOrder
	 */
	public void buildObservationModel(ArrayList<RecordRow> trainSet, int[] cellStateOrder)
	{
		// Compute means.
		computeStateFeatureMeans(trainSet, cellStateOrder);
		
		// Compute covariances.
		featureCount = trainSet.get(0).getCells().get(0).getFeatures().size();
		stateFeatureCovariances = new HashMap<Integer, double[][]>(cellStateOrder.length);
		for (int statePos = 0; statePos < cellStateOrder.length; statePos++)
		{
			stateFeatureCovariances.put(statePos, new double[featureCount][featureCount]);
			for (int featurePos1 = 0; featurePos1 < featureCount; featurePos1++)
			{
				for (int featurePos2 = 0; featurePos2 < featureCount; featurePos2++)
				{
					stateFeatureCovariances.get(statePos)[featurePos1][featurePos2] = getCovariance(trainSet, statePos, featurePos1, featurePos2);
				}
			}
		}
		
		stateFeatureCovarianceMatrices = new HashMap<Integer, Matrix>(cellStateOrder.length);
		stateFeatureInverseCovarianceMatrices = new HashMap<Integer, Matrix>(cellStateOrder.length);
		for (int statePos = 0; statePos < cellStateOrder.length; statePos++)
		{
			Matrix matrix = new Matrix(stateFeatureCovariances.get(statePos));
			stateFeatureCovarianceMatrices.put(statePos, matrix);
			determinants.put(statePos, matrix.det());
			stateFeatureInverseCovarianceMatrices.put(statePos, matrix.inverse());
		}
	}
	
	
	public void computeStateFeatureMeans(ArrayList<RecordRow> trainSet, int[] cellStateOrder)
	{
		int featureCount = trainSet.get(0).getCells().get(0).getFeatures().size();
		int rowCount = trainSet.size();
		
		// Compute feature value means.
		
		for (RecordRow row : trainSet)
		{
			for (int cellPos = 0; cellPos < cellStateOrder.length; cellPos++)
			{
				RecordCell cell = row.getCells().get(cellPos);
				
				// Make sure we have an array to put means into.
				if (stateFeatureMeans.get(cellStateOrder[cellPos]) == null)
				{
					stateFeatureMeans.put(cellStateOrder[cellPos], new double[featureCount]);
				}
				
				for (int featurePos = 0; featurePos < featureCount; featurePos++)
				{
					stateFeatureMeans.get(cellStateOrder[cellPos])[featurePos] += cell.getFeatures().get(featurePos);
				}
			}
		}
		
		// Normalize all means by instance count.
		for (int cellPos = 0; cellPos < cellStateOrder.length; cellPos++)
		{
			for (int featurePos = 0; featurePos < featureCount; featurePos++)
			{
				stateFeatureMeans.get(cellStateOrder[cellPos])[featurePos] /= rowCount;
			}
		}
	}
	
	
	
	/**
	 * Computes the covariance of two variables/features for a particular column/state given the training set.
	 */
	public double getCovariance(ArrayList<RecordRow> trainSet, int statePos, int featurePos1, int featurePos2)
	{
		double sum = 0;
		double mean1 = stateFeatureMeans.get(statePos)[featurePos1];
		double mean2 = stateFeatureMeans.get(statePos)[featurePos2];
		
		for (RecordRow row : trainSet)
		{
			double featureValue1 = row.getCells().get(stateCellOrder[statePos]).getFeatures().get(featurePos1);
			double featureValue2 = row.getCells().get(stateCellOrder[statePos]).getFeatures().get(featurePos2);
			sum += (double)((featureValue1 - mean1) * (featureValue2 - mean2));
		}
		
		return sum / (trainSet.size() - 1);
	}
	
	
	public String classify(RecordRow row)
	{
		
		
		
		return "";
	}
	
	
	/**
	 * Returns the probability of seeing stateValue2 followed by stateValue1.  If stateIndex
	 * is 0, then stateValue1 should be null and this will return the probability of 
	 * seeing stateValue2 given that it is in the first column state (no preceding states).
	 * @param stateIndex the state number as ordered using our HMM ordering of columns, not the 
	 * original column ordering, zero based with zero meaning the first state.  Use cellStateOrder
	 * to get the state order from the cell order.
	 * @param stateValue1 the transcription of the first of the two states.
	 * @param stateValue2 the transcription of the second of the two states.
	 * @return conditional or marginal state probability.
	 */
	public double transitionProbability(int stateIndex, String stateValue1, String stateValue2)
	{
		double probability = 0.0;
		
		if (stateIndex == 0)
		{
			int valueIndex = stateValues.get(stateIndex).get(stateValue2);
			probability = (stateStats.get(stateIndex)[valueIndex] + smoothingConstant) / stateNormalizers.get(stateIndex); 
		}
		else
		{
			int valueIndex1 = stateValues.get(stateIndex - 1).get(stateValue1);
			int valueIndex2 = stateValues.get(stateIndex).get(stateValue2);
			probability = (stateTransitionStats.get(stateIndex)[valueIndex1][valueIndex2] + smoothingConstant) / stateTransitionNormalizers.get(stateIndex); 
		}
		
		return probability;
	}
	
	
	/**
	 * Returns the probability of seeing the observation feature vector given the word in stateValue.
	 * @param stateValue is the candidate transcription.
	 * @param features is the features observed.
	 * @return an observation probability.
	 */
	public double observationProbability(String stateValue, ArrayList<Double> features)
	{
		if (features.size() != featureCount)
		{
			System.out.println("*** Feature counts do not match! ***");
		}
		
		double probability = 0.0;
		
		// (features - means[stateValue])^T * CovarianceMatrix[stateValue]^-1 * (features - means[stateValue])
		//Matrix featuresMatrix = new Matrix(features.toArray());
		Matrix featureVector = makeVector(features);
		Matrix featureMeanVector = makeVector(stateFeatureMeans.get(stateValue));
		Matrix differenceVector = featureVector.minus(featureMeanVector); 
		Matrix product = differenceVector.transpose().times(stateFeatureInverseCovarianceMatrices.get(stateValue));
		double numerator = Math.exp(product.times(differenceVector).get(0, 0));
		
		double denominator = Math.sqrt(Math.pow(2, featureCount) * Math.pow(Math.PI, featureCount) * determinants.get(stateValue));
				
		probability = Math.exp(numerator / denominator);
		
		return probability;
	}
	
	
	private Matrix makeVector(double[] values)
	{
		Matrix featureMatrix = new Matrix(featureCount, 1);
		
		for (int pos = 0; pos < values.length; pos++)
		{
			featureMatrix.set(pos, 0, values[pos]);
		}
		
		return featureMatrix;
	}
	
	
	private Matrix makeVector(ArrayList<Double> values)
	{
		Matrix featureMatrix = new Matrix(featureCount, 1);
		
		for (int pos = 0; pos < featureCount; pos++)
		{
			double value = values.get(pos);
			featureMatrix.set(pos, 0, value);
		}
		
		return featureMatrix;
	}
}