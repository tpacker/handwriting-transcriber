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
	private String predictedTranscription;
	private double predictionProbability;
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
	

	public void setPredictedTranscription(String predictedTranscription)
	{
		this.predictedTranscription = predictedTranscription;
	}

	
	public String getPredictedTranscription()
	{
		return predictedTranscription;
	}
	

	public void setPredictionProbability(double predictionProbability)
	{
		this.predictionProbability = predictionProbability;
	}


	public double getPredictionProbability()
	{
		return predictionProbability;
	}
	
	
	public void addFeature(double featureValue)
	{
		features.add(featureValue);
	}
	
	//iraykhel 12/02
	public ArrayList<Double> getFeatures()
	{
		return features;
	}
	
	//iraykhel 12/02
	public GrayImage getImage()
	{
		return image;
	}
	
	
	/**
	 * Run through the individual features, extract one feature each.
	 */
	private void MakeFeatures()
	{
		//MakeUpperProtrusionsFeature();
		MakeWidthFeature();
		MakeHeightFeature();
		MakeAspectRatioFeature();
		MakeAreaFeature();
		
		MakeUpperProfileFeature();
		MakeProjectionProfileFeature();
	}
	
	
	public void makeCheatingFeature()
	{
		double classValue = 0.0;
		
		if (getTranscription().compareTo("boarder") == 0)
		{
			classValue = 1.0;
		}
		this.addFeature(classValue);
		
		classValue = 0.0;
		if (getTranscription().compareTo("wife") == 0)
		{
			classValue = 1.0;
		}
		this.addFeature(classValue);
		
		classValue = 0.0;
		if (getTranscription().compareTo("head") == 0)
		{
			classValue = 1.0;
		}
		this.addFeature(classValue);
		
		classValue = 0.0;
		if (getTranscription().compareTo("father") == 0)
		{
			classValue = 1.0;
		}
		this.addFeature(classValue);
		
		classValue = 0.0;
		if (getTranscription().compareTo("mother") == 0)
		{
			classValue = 1.0;
		}
		this.addFeature(classValue);
		
		classValue = 0.0;
		if (getTranscription().compareTo("son") == 0)
		{
			classValue = 1.0;
		}
		this.addFeature(classValue);
		
		classValue = 0.0;
		if (getTranscription().compareTo("daughter") == 0)
		{
			classValue = 1.0;
		}
		this.addFeature(classValue);
		
		classValue = 0.0;
		if (getTranscription().compareTo("brother") == 0)
		{
			classValue = 1.0;
		}
		this.addFeature(classValue);
		
		classValue = 0.0;
		if (getTranscription().compareTo("sister") == 0)
		{
			classValue = 1.0;
		}
		this.addFeature(classValue);
		
		classValue = 0.0;
		if (getTranscription().compareTo("m") == 0)
		{
			classValue = 1.0;
		}
		this.addFeature(classValue);
	}
	
	
	private void MakeHeightFeature()
	{
		this.addFeature(image.Y());
	}
	
	
	private void MakeWidthFeature()
	{
		this.addFeature(image.X());
	}
	
	
	private void MakeAspectRatioFeature()
	{
		this.addFeature(((double)image.X()) / ((double)image.Y()));
	}
	
	
	private void MakeAreaFeature()
	{
		this.addFeature(image.X() * image.Y());
	}
	
	
	/*private void MakeUpperProtrusionsFeature()
	{
		this.addFeature(ProtrusionSupport_BlackAmountPerRectangle((int)(image.Y()*0.33),(int)(image.Y()*0.67)));
		double[] profile = ImageProcessing.getYProfile(image, 0);
		System.out.println("Profile: ");
		for (int i = 0; i < profile.length; i++)
			System.out.print(profile[i] + " ");
		System.out.print("\n");
	}*/
	
	
	private void MakeUpperProfileFeature()
	{
		// DFT.
		int classId = 0;
		double[] profile = ImageProcessing.getUpperProfile(image, classId);
		double[] cosines = DFT.cosineValues(profile);
		double[] sines = DFT.sineValues(profile);

		// Insert 7 features into cell.
		features.add(cosines[0]);
		features.add(cosines[1]);
		features.add(cosines[2]);
		features.add(cosines[3]);
		
		features.add(sines[1]);
		features.add(sines[2]);
		features.add(sines[3]);
	}
	
	
	private void MakeProjectionProfileFeature()
	{
		// DFT.
		int classId = 0;
		double[] profile = ImageProcessing.getXProfile(image, classId);
		double[] cosines = DFT.cosineValues(profile);
		double[] sines = DFT.sineValues(profile);

		// Insert 7 features into cell.
		features.add(cosines[0]);
		features.add(cosines[1]);
		features.add(cosines[2]);
		features.add(cosines[3]);
		
		features.add(sines[1]);
		features.add(sines[2]);
		features.add(sines[3]);
	}
}
