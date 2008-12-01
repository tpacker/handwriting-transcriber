package classification;


import java.util.ArrayList;



public class RecordRow
{
	private final int rowLength = 3;
	private ArrayList<RecordCell> cells = new ArrayList<RecordCell>();
	
	

	public RecordRow()
	{
		for (int cellPos = 0; cellPos < rowLength; cellPos++)
		{
			cells.add(new RecordCell());
		}
	}
	
	
	public void readImage(String fileNameBase)
	{
		for (RecordCell cell : cells)
		{
			cell.MakeFeatures();
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
