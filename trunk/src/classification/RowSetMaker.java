package classification;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;



public class RowSetMaker
{
	public static String exractedRectangleFolder = "\\ProcessedImages\\ExtractedRectangles";
	public static String exractedRectanglePath;
	
	
	
	public static ArrayList<RecordRow> MakeRowSet()
	{
		ArrayList<RecordRow> rowSet = new ArrayList<RecordRow>();
		String curDir = System.getProperty("user.dir");
		
		// Read in extracted rectangles.
		exractedRectanglePath = curDir + exractedRectangleFolder;
		File fileView = new File(exractedRectanglePath);
		String[] fileList = fileView.list();
		Map<String, String> rowFileBaseNameList = new HashMap<String, String>();
		
		for (String file : fileList)
		{
			if (file.substring(file.length() - 4).compareTo(".pgm") == 0)
			{
				String fileBaseName = file.substring(0, file.length() - 5);
				if (!rowFileBaseNameList.containsKey(fileBaseName))
				{
					rowFileBaseNameList.put(fileBaseName, fileBaseName);
				}
			}
		}
		
		for (String fileBaseName : rowFileBaseNameList.values())
		{
			RecordRow row = new RecordRow(exractedRectanglePath + "\\" + fileBaseName);
			rowSet.add(row);
		}
		
		
		// Read in transcriptions.
		
		// Create features.
		
		return rowSet;
	}
}
