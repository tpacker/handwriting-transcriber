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
		
		// Test.
		test(probabilityModel, testSet);		
		
		System.out.println("Done.");
	}
	
	
	private static ProbabilityModel train(ArrayList<RecordRow> trainSet)
	{
		ProbabilityModel probabilityModel = new ProbabilityModel();
		
		// Compute state transition probability model statistics.
		probabilityModel.buildStateTransitionModel(trainSet, cellStateOrder);
		
		// Compute observation probability model.
		probabilityModel.buildObservationModel(trainSet, cellStateOrder);
		
		return probabilityModel;
	}
	
	
	private static void test(ProbabilityModel probabilityModel, ArrayList<RecordRow> testSet)
	{
		
	}
}
