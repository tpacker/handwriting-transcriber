package classification;


import java.util.ArrayList;

import jigl.image.GrayImage;

import utilities.File;



public class RecordCell
{
	private String fileName;
	private GrayImage image;
	private String transcription;
	private ArrayList<Double> features = new ArrayList<Double>();

	
	
	public void readImage(String fileName)
	{
		this.fileName = fileName;
		image = File.openGrayImage(fileName);
		MakeFeatures();
	}
	

	public void setTranscription(String transcription)
	{
		this.transcription = transcription;
	}

	
	public String getTranscription()
	{
		return transcription;
	}	
	
	
	public void addFeature(double featureValue)
	{
		features.add(featureValue);
	}
	
	
	/**
	 * Run through the individual features, extract one feature each.
	 */
	private void MakeFeatures()
	{
		MakeWidthFeature();
		MakeProfileFeature("TopProfile");
	}
	
	
	private void MakeWidthFeature()
	{
		// Read image from cell.

		double width = 0.0;
		
		// Insert this feature into cell.
		this.addFeature(width);
	}
	
	
	private void MakeProfileFeature(String profileType)
	{
		// Read image from cell.

		// DFT.
		
		// Insert 7 features into cell.
		
	}
}
