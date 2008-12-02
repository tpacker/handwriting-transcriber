package classification;


import imageprocessing.ImageProcessing;

import java.util.ArrayList;

import jigl.image.GrayImage;

import utilities.DFT;
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
		MakeUpperProfileFeature();
		MakeProjectionProfileFeature();
	}
	
	
	private void MakeWidthFeature()
	{
		// Read image from cell.

		double width = 0.0;
		
		// Insert this feature into cell.
		this.addFeature(width);
	}
	
	
	private void MakeUpperProfileFeature()
	{
		int classId = 0;
		double[] profile = ImageProcessing.getUpperProfile(image, classId);
		double[] cosines = DFT.cosineValues(profile);
		double[] sines = DFT.sineValues(profile);
		// DFT.
		features.add(cosines[0]);
		features.add(cosines[1]);
		features.add(cosines[2]);
		features.add(cosines[3]);
		
		features.add(sines[0]);
		features.add(sines[1]);
		features.add(sines[2]);
		
		// Insert 7 features into cell.
		
	}
	private void MakeProjectionProfileFeature()
	{
		int classId = 0;
		double[] profile = ImageProcessing.getXProfile(image, classId);
		double[] cosines = DFT.cosineValues(profile);
		double[] sines = DFT.sineValues(profile);
		// DFT.
		features.add(cosines[0]);
		features.add(cosines[1]);
		features.add(cosines[2]);
		features.add(cosines[3]);
		
		features.add(sines[0]);
		features.add(sines[1]);
		features.add(sines[2]);
		
		
	}
}
