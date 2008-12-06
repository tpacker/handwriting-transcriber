package classification;


import imageprocessing.ImageProcessing;
import imageprocessing.PatternRecognition;

import java.util.ArrayList;

import jigl.image.GrayImage;

import utilities.BoundingBox;
import utilities.DFT;
import utilities.FFT;
import utilities.File;



public class RecordCell
{
	private String fileName;
	private GrayImage image;
	private String transcription;
	private String predictedTranscription = "";
	private double predictionProbability;
	private ArrayList<Double> features = new ArrayList<Double>();
	private String predictions = "";
	BoundingBox boundingBox;
	
	
	
	public void readImage(String fileName)
	{
		this.fileName = fileName;
		image = File.openGrayImage(fileName);
		int objcolor =0; 
		boundingBox= PatternRecognition.getBoundingBox(image, objcolor);
		MakeFeatures();
	}
	

	public GrayImage getImage()
	{
		return image;
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


	public void setPredictions(String predictions)
	{
		this.predictions = predictions;
	}


	public String getPredictions()
	{
		return predictions;
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
//		MakeLowerProfileFeature();
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
		if (getTranscription().compareTo("partner") == 0)
		{
			classValue = 1.0;
		}
		this.addFeature(classValue);
		
		classValue = 0.0;
		if (getTranscription().compareTo("servant") == 0)
		{
			classValue = 1.0;
		}
		this.addFeature(classValue);
		
		classValue = 0.0;
		if (getTranscription().compareTo("nephew") == 0)
		{
			classValue = 1.0;
		}
		this.addFeature(classValue);
		
		classValue = 0.0;
		if (getTranscription().compareTo("father-in-law") == 0)
		{
			classValue = 1.0;
		}
		this.addFeature(classValue);
		
		classValue = 0.0;
		if (getTranscription().compareTo("mother-in-law") == 0)
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
		
		classValue = 0.0;
		if (getTranscription().compareTo("f") == 0)
		{
			classValue = 1.0;
		}
		this.addFeature(classValue);
		
		classValue = 0.0;
		if (getTranscription().compareTo("d") == 0)
		{
			classValue = 1.0;
		}
		this.addFeature(classValue);
		
		classValue = 0.0;
		if (getTranscription().compareTo("wd") == 0)
		{
			classValue = 1.0;
		}
		this.addFeature(classValue);
		
		classValue = 0.0;
		if (getTranscription().compareTo("s") == 0)
		{
			classValue = 1.0;
		}
		this.addFeature(classValue);
	}
	
	
	private void MakeHeightFeature()
	{
//		this.addFeature(image.Y());
		
		this.addFeature(boundingBox.getHeigth());
	}
	
	
	private void MakeWidthFeature()
	{
//		this.addFeature(image.X());
		
		this.addFeature(boundingBox.getWidth());
	}
	
	
	private void MakeAspectRatioFeature()
	{
//		this.addFeature(((double)image.X()) / ((double)image.Y()));
		double width = boundingBox.getWidth();
		double height = boundingBox.getHeigth();
		this.addFeature(width/height);
	}
	
	
	private void MakeAreaFeature()
	{
//		this.addFeature(image.X() * image.Y());
		
		double width = boundingBox.getWidth();
		double height = boundingBox.getHeigth();
		this.addFeature(width*height);
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
	
//		profile = ImageProcessing.fixProfile(profile);
//		double[] cosines = FFT.cosineValues(profile);
//		double[] sines = FFT.sineValues(profile);

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
	
	private void MakeLowerProfileFeature()
	{
		// DFT.
		int classId = 0;
		double[] profile = ImageProcessing.getLowerProfile(image, classId);
		
//		profile = ImageProcessing.fixProfile(profile);
//		double[] cosines = FFT.cosineValues(profile);
//		double[] sines = FFT.sineValues(profile);

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
		
//		profile = ImageProcessing.fixProfile(profile);
//		double[] cosines = FFT.cosineValues(profile);
//		double[] sines = FFT.sineValues(profile);
		
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
