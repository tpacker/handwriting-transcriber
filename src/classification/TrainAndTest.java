package classification;

import java.util.ArrayList;



/**
 * Train and classify.
 */
public class TrainAndTest
{
	public static final int testSetSize = 5;   
	//public static final int[] cellStateOrder = {0, 1, 2};  // 88.5 -> 89.1
	//public static final int[] cellStateOrder = {2, 0, 1};  // 88.5 -> 88.3
	//public static final int[] cellStateOrder = {0, 2, 1};  // 89.6 -> 89.3
	//public static final int[] cellStateOrder = {1, 0, 2};  // 89.9 -> 89.3
	//public static final int[] cellStateOrder = {2, 1, 0};  // 90.4 -> 90.9
	public static final int[] cellStateOrder = {1, 2, 0};  // 91.2 -> 91.5

	public static final boolean debug = false;
	
	
	
	/**
	 * Train and classify.
	 */
	public static void main(String[] args)
	{
		// Create row set.
		ArrayList<RecordRow> rowSet = RowSetMaker.MakeRowSet();
		double accuracy = 0.0;
		int folds = 0;
		
		for (int setOffset = 0; setOffset < rowSet.size(); setOffset += testSetSize)
		{
			// Split into train and test sets.
			ArrayList<RecordRow> trainSet = new ArrayList<RecordRow>();
			ArrayList<RecordRow> testSet = new ArrayList<RecordRow>();
			for (int rowPos = 0; rowPos < rowSet.size(); rowPos++)
			{
				if (((rowPos + setOffset) % rowSet.size()) < (rowSet.size() - testSetSize))
				{
					trainSet.add(rowSet.get(rowPos));
				}
				else
				{
					testSet.add(rowSet.get(rowPos));
				}
			}
			
			// Train.
			ProbabilityModel probabilityModel = train(trainSet);
			
			if (debug)
			{
				//iraykhel 12/02
				System.out.println("Training set:");
				printDataset(trainSet);
			}
			
			// Test.
			accuracy += test(probabilityModel, testSet);		
			
			if (debug)
			{
				System.out.println();
				System.out.println("Done.");
			}
			
			folds++;
		}
		
		accuracy /= folds;
		System.out.println("Average Accuracy: " + accuracy);
		System.out.println();
		System.out.println("Done.");
	}
	
	
	//iraykhel 12/02 cutesy print dataset
	private static void printDataset(ArrayList<RecordRow> dataSet)
	{
		for (RecordRow row : dataSet)
		{
			ArrayList<RecordCell> cells = row.getCells();
			for (RecordCell cell : cells)
			{
				ArrayList<Double> features = cell.getFeatures();
				System.out.print(String.format("%1$15s:", cell.getTranscription()));
				for (Double feature : features)
				{
					System.out.print(String.format("%1$9.3f ", feature));
				}
				System.out.print("\n");
			}
		}
	}
	
	
	private static ProbabilityModel train(ArrayList<RecordRow> trainSet)
	{
		ProbabilityModel probabilityModel = new ProbabilityModel();
		
		// Compute state transition probability model statistics.
		probabilityModel.buildTransitionModel(trainSet, cellStateOrder);
		
		// Compute observation probability model.
		probabilityModel.buildObservationModel(trainSet, cellStateOrder);
		
		return probabilityModel;
	}
	
	
	private static double test(ProbabilityModel probabilityModel, ArrayList<RecordRow> testSet)
	{
		
		// Decide on the best classifications.
		for (RecordRow row : testSet)
		{
			probabilityModel.bruteClassify(row);
		}
		
		// Print out results.
		double accuracy = 0.0;
		int cellCount = 0;
		
		if (debug)
		{
			System.out.println();
			System.out.println("Transcription\tPrediction\tPrediction Prob");
		}
		
		for (RecordRow row : testSet)
		{
			//ViterbiTree.labelSequence(probabilityModel, row);
			//root.setPredictions(probabilityModel, row);
			
			int cellPos = 0;
			for (RecordCell cell : row.getCells())
			{
				//if (cellPos++ != 0)
				{
					cellCount++;
					if (cell.getTranscription().compareTo(cell.getPredictedTranscription()) == 0)
					{
						accuracy += 1.0;
					}
					
					//if (debug)
					{
						System.out.println(cell.getTranscription() + "\t" + cell.getPredictedTranscription() + 
								"\t" + cell.getPredictedObservationProbability() + "\t" + cell.getPredictedTransitionProbability());
					}
				}
			}
		}
		
		accuracy /= cellCount;

		System.out.println("Accuracy: " + accuracy);
		
		return accuracy;
	}
}
