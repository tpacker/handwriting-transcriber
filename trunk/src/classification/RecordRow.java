package classification;

import java.util.ArrayList;



public class RecordRow
{
	private ArrayList<RecordCell> cells = new ArrayList<RecordCell>();
	
	
	
	public void MakeFeatures()
	{
		for (RecordCell cell : cells)
		{
			cell.MakeFeatures();
		}
	}
}
