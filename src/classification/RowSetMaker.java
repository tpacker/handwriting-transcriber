package classification;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;



public class RowSetMaker
{
	public static String exractedRectangleFolder = "\\ProcessedImages\\ExtractedRectangles";
	public static String exractedRectanglePath;
	public static String transcriptionFolder = "\\Transcriptions";
	public static String transcriptionPath;
	
	
	
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

					RecordRow row = new RecordRow(exractedRectanglePath + "\\" + fileBaseName);
					rowSet.add(row);
				}
			}
		}
		
		// Read in transcriptions.
		
		transcriptionPath = curDir + transcriptionFolder;
		BufferedReader reader = null;
		try
		{
			try
			{
				reader =  new BufferedReader(new FileReader(transcriptionPath + "\\All Transcriptions.txt"));
				String line = null;
				int rowPos = 0;
				String currentImageName = "";
				
				while ((line = reader.readLine()) != null)
				{
		        	if (line.length() <= 1)
		        	{
		        		// Skip blanks.
		        	}
		        	else if (line.charAt(0) == '*')
		        	{
		        		// Read new image name.
		        		currentImageName = line.substring(2);
		        	}
		        	else if (rowSet.get(rowPos).getImageName().compareTo(currentImageName) == 0)
		        	{
		        		rowSet.get(rowPos).setTranscription(line);
		        		rowPos++;
		        	}
		        	else
		        	{
		        		System.out.println("Problem reading transcription file.");
					}
		        }
			}
			catch (FileNotFoundException e)
			{
				e.printStackTrace();
			}
			finally
			{
				reader.close();
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
		return rowSet;
	}
}
