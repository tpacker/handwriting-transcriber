package classification;


import java.util.ArrayList;

import jigl.image.GrayImage;

import utilities.File;



public class RecordCell
{
	private GrayImage image;
	private String transcription;
	private ArrayList<Double> features = new ArrayList();

	
	
	public void readImage(String fileName)
	{
		image = File.openGrayImage(fileName);
	}
	

	private void setTranscription(String transcription)
	{
		this.transcription = transcription;
	}


	private String getTranscription()
	{
		return transcription;
	}	
	
	
	public void addFeature(double featureValue)
	{
		features.add(featureValue);
	}
	
	
	public void MakeFeatures()
	{
		// Run through the individual features, extract one feature each.
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
