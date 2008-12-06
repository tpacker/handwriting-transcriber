package test;

import imageprocessing.*;
import utilities.BoundingBox;
import utilities.DFT;
import utilities.FFT;
import utilities.Plot;
import jigl.image.GrayImage;

public class Test {
	public static void main(String[] args) {
		
//		System.out.println("hello world");
//		Test.ReadInFile();
//		Test.PlotProjectionProfile();
//		Test.DFTProfile();
//		Test.PlotUpperWordProfile();
//		Test.DFT();
//		Test.BoundingFeatures();
//		Test.PlotUpperWordProfile();
		Test.PlotLowerWordProfile();
	}
	public static void BoundingFeatures() {
		GrayImage image = ImageProcessing.readPgmImage("./Test/images/wife.pgm");
		ImageProcessing.displayImage(image, "file");
		BoundingBox bb = PatternRecognition.getBoundingBox(image, 0);
		System.out.println("width: "+bb.getWidth());
		System.out.println("height: "+bb.getHeigth());

	}

	public static void ReadInFile() {
		GrayImage image = ImageProcessing.readPgmImage("./Test/images/wife.pgm");
		ImageProcessing.displayImage(image, "file");

	}

	public static void PlotProjectionProfile() {
		GrayImage image = ImageProcessing.readPgmImage("./Test/images/wife.pgm");
		ImageProcessing.displayImage(image, "file");
		Plot chart = new Plot();
		int classId = 0;

		double[] Xprofile = ImageProcessing.getXProfile(image, classId);
		double[] Yprofile = ImageProcessing.getYProfile(image, classId);

		chart.setSubplot(Xprofile, "Projection Profile");
//		chart.setSubplot(Yprofile, "Y profile");

		chart.draw();

	}

	public static void DFT() {
		DFT dft = new DFT();
		FFT fft = new FFT();
		double[] input = { 0, 0, 0,0,0,5,6,0};
		input = ImageProcessing.fixProfile(input);
//		double[] output = DFT.forwardMagnitude(input);

//		double[] sines = DFT.sineValues(input);
		double[] sines = FFT.sineValues(input);
		System.out.println("sines");
		PatternRecognition.print(sines);
//		
////		double[] cosines = DFT.cosineValues(input);
		double[] cosines = FFT.cosineValues(input);
		System.out.println("cosines");
		PatternRecognition.print(cosines);
//		
//		System.out.println("output");
//		PatternRecognition.print(output);
	}
	
	public static void DFTProfile() {
		GrayImage image = ImageProcessing.readPgmImage("./Test/images/wife.pgm");
		ImageProcessing.displayImage(image, "file");
		Plot chart = new Plot();
		int classId = 0;

		double[] Xprofile = ImageProcessing.getXProfile(image, classId);
		double[] fixedprofile = ImageProcessing.fixProfile(Xprofile);
//		float[] Yprofile = ImageProcessing.getYProfile(image, classId);

//		double[] sines = DFT.sineValues(Xprofile);
		double[] sines = FFT.sineValues(fixedprofile);
		System.out.println("sines");
		PatternRecognition.print(sines);
		double[] cosines = FFT.cosineValues(fixedprofile);
		System.out.println("cosines ------------");
		PatternRecognition.print(cosines);
	}
	
	public static void PlotUpperWordProfile() {
		GrayImage image = ImageProcessing.readPgmImage("./Test/images/wife.pgm");
		ImageProcessing.displayImage(image, "file");
		Plot chart = new Plot();
		int classId = 0;

		double[] profile = ImageProcessing.getUpperProfile(image, classId);
//		double[] Yprofile = ImageProcessing.getYProfile(image, classId);

		chart.setSubplot(profile, "Upper profile");
//		chart.setSubplot(Yprofile, "Y profile");

		chart.draw();

	}
	
	public static void PlotLowerWordProfile() {
		GrayImage image = ImageProcessing.readPgmImage("./Test/images/circle.pgm");
		ImageProcessing.displayImage(image, "file");
		Plot chart = new Plot();
		int classId = 0;

		double[] profile = ImageProcessing.getLowerProfile(image, classId);
//		double[] Yprofile = ImageProcessing.getYProfile(image, classId);

		chart.setSubplot(profile, "Lower profile");
//		chart.setSubplot(Yprofile, "Y profile");

		chart.draw();

	}


}
