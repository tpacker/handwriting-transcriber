package classification;


import java.util.ArrayList;



public class RecordRow
{
	private final int rowLength = 3;
	private ArrayList<RecordCell> cells = new ArrayList<RecordCell>();
	
	
	
	/**
	 * Constructor.  Create 3 cells.
	 */
	public RecordRow(String fileNameBase)
	{
		for (int cellPos = 0; cellPos < rowLength; cellPos++)
		{
			cells.add(new RecordCell());
		}
		
		for (int cellPos = 0; cellPos < rowLength; cellPos++)
		{
			cells.get(cellPos).readImage(fileNameBase + cellPos + ".pgm");
		}
	}
	
	
	public void MakeFeatures()
	{
		for (RecordCell cell : cells)
		{
			cell.MakeFeatures();
		}
	}
}
