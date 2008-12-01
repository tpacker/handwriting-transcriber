package classification;


import java.util.ArrayList;

import jigl.image.GrayImage;



public class RecordCell
{
	private GrayImage image;
	private String transcription;
	private ArrayList<Double> features = new ArrayList();

	
	public void setImage(GrayImage image)
	{
		this.image = image;
	}

	
	public GrayImage getImage()
	{
		return image;
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
