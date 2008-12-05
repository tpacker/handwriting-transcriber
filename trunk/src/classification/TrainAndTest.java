package classification;

import java.util.ArrayList;

import jigl.image.GrayImage;



/**
 * Train and classify.
 */
public class TrainAndTest
{
	public static final int testSetSize = 10;
	public static final int[] cellStateOrder = {1, 0, 2};
	
	
	
	/**
	 * Train and classify.
	 */
	public static void main(String[] args)
	{
		// Create row set.
		ArrayList<RecordRow> rowSet = RowSetMaker.MakeRowSet();
		
		// Split into train and test sets.
		ArrayList<RecordRow> trainSet = new ArrayList<RecordRow>();
		ArrayList<RecordRow> testSet = new ArrayList<RecordRow>();
		for (int rowPos = 0; rowPos < rowSet.size(); rowPos++)
		{
			if (rowPos < rowSet.size() - testSetSize)
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
		
		//iraykhel 12/02
		System.out.println("Training set:");
		printDataset(trainSet);
		
		// Test.
		test(probabilityModel, testSet);		
		
		System.out.println("Done.");
	}
	
	
	//iraykhel 12/02 cutesy print dataset
	private static void printDataset(ArrayList<RecordRow> dataSet) {
		for (RecordRow row : dataSet) {
			ArrayList<RecordCell> cells = row.getCells();
			for (RecordCell cell : cells) {
				ArrayList<Double> features = cell.getFeatures();
				System.out.print(String.format("%1$15s:", cell.getTranscription()));
				for (Double feature : features) {
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
	
	
	private static void test(ProbabilityModel probabilityModel, ArrayList<RecordRow> testSet)
	{
		// Decide on the best classifications.
		for (RecordRow row : testSet)
		{
			//probabilityModel.classify(row);
			probabilityModel.bruteClassify(row);
		}

		// Print out results.
		double accuracy = 0.0;
		int cellCount = 0;
		System.out.println("Transcription\tPrediction\tPrediction Prob");
		
		for (RecordRow row : testSet)
		{
			for (RecordCell cell : row.getCells())
			{
				cellCount++;
				if (cell.getTranscription().compareTo(cell.getPredictedTranscription()) == 0)
				{
					accuracy += 1.0;
				}
				System.out.println(cell.getTranscription() + "\t" + cell.getPredictedTranscription() + "\t" + cell.getPredictionProbability());
			}
		}
		
		accuracy /= cellCount;

		System.out.println("Accuracy: " + accuracy);
	}
}
