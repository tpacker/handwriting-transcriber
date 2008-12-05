package classification;


import java.util.ArrayList;



public class RecordRow
{
	public static final int rowLength = 3;
	
	
	private String imageName;
	private ArrayList<RecordCell> cells = new ArrayList<RecordCell>(rowLength);
	
	

	public void setImageName(String imageName)
	{
		this.imageName = imageName;
	}


	public String getImageName()
	{
		return imageName;
	}
	

	public void setCells(ArrayList<RecordCell> cells)
	{
		this.cells = cells;
	}


	public ArrayList<RecordCell> getCells()
	{
		return cells;
	}
	
	
	
	/**
	 * Constructor.  Create 3 cells.
	 */
	public RecordRow(String fileNameBase)
	{
		setImageName(fileNameBase.substring(fileNameBase.lastIndexOf('\\') + 1, fileNameBase.indexOf('_', fileNameBase.lastIndexOf('\\'))));
		
		for (int cellPos = 0; cellPos < rowLength; cellPos++)
		{
			getCells().add(new RecordCell());
		}
		
		for (int cellPos = 0; cellPos < rowLength; cellPos++)
		{
			getCells().get(cellPos).readImage(fileNameBase + (cellPos + 1) + ".pgm");
		}
	}
	
	
	public void setTranscription(String line)
	{
		String[] cellTranscriptions = line.split("\t");

		for (int cellPos1 = 0; cellPos1 < RecordRow.rowLength; cellPos1++)
		{
			getCells().get(cellPos1).setTranscription(cellTranscriptions[cellPos1 + 1]);
			//getCells().get(cellPos1).makeCheatingFeature();
		}
	}

}
