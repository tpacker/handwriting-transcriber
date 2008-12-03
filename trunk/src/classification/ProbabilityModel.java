package classification;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;



public class ProbabilityModel
{
	/**
	 * Maps cell/column position (int between 0 and 2) to a set of values for that column (each 
	 * value maps to its integer index.  
	 * So: cell number -> state value -> position in matrix
	 * Ordered by state order, not original column order.
	 */
	private Map<Integer, HashMap<String, Integer>> stateValues = new HashMap<Integer, HashMap<String, Integer>>();
	
	/**
	 * Ordered by state order, not original column order.
	 */
	private Map<Integer, int[][]> stateTransitionStats = new HashMap<Integer, int[][]>();
	
	
	
	public void buildStateTransitionModel(ArrayList<RecordRow> trainSet, int[] cellStateOrder)
	{
		int[] stateCellOrder = new int[cellStateOrder.length];
		
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
		
		//System.out.println(stateValues);
		
		// Create the map of state transition probabilities of the right size.
		for (int cellPos = 0; cellPos < cellStateOrder.length; cellPos++)
		{
			// Don't try to make a transition matrix for the last state.
			if (cellStateOrder[cellPos] < cellStateOrder.length - 1)
			{
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
				}
				
			}
		}
		
		//iraykhel 12/02
		System.out.println("State transition counts:");
		printStateTransitionStats(cellStateOrder);
		
		
		
		
	}
	
	
	//iraykhel 12/02: cutesy print transition matrix
	public void printStateTransitionStats(int[] cellStateOrder) {
		for (int cellPos = 0; cellPos < cellStateOrder.length; cellPos++)
		{
			if (cellStateOrder[cellPos] < cellStateOrder.length - 1)
			{
				HashMap<String,Integer> valToIndexMap = stateValues.get(cellStateOrder[cellPos]);
				Set<Entry<String,Integer>> vtiSet = valToIndexMap.entrySet();
				HashMap<String,Integer> valToIndexMapNext = stateValues.get(cellStateOrder[cellPos] + 1);
				Set<Entry<String,Integer>> vtiSetNext = valToIndexMapNext.entrySet();
				
				for (Entry<String,Integer> vtiEntry : vtiSet) {
					for (Entry<String,Integer> vtiEntryNext : vtiSetNext) {
						int count = stateTransitionStats.get(cellStateOrder[cellPos])[vtiEntry.getValue()][vtiEntryNext.getValue()];
						System.out.println(vtiEntry.getKey() + "->" + vtiEntryNext.getKey() + ": " + count);
					}
				}
			}
		}
	}


	
	public void buildObservationModel(ArrayList<RecordRow> trainSet, int[] cellStateOrder)
	{
	}	
}
