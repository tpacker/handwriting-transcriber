import utilities.DFT;
import jigl.image.GrayImage;

public class Test {

	public static void ReadInFile() {
		GrayImage image = ImageProcessing
				.readPgmImage("./Test/images/wife.pgm");
		ImageProcessing.displayImage(image, "file");

	}

	public static void PlotProfile() {
		GrayImage image = ImageProcessing
				.readPgmImage("./Test/images/wife.pgm");
		ImageProcessing.displayImage(image, "file");
		Plot chart = new Plot();
		int classId = 0;

		double[] Xprofile = ImageProcessing.getXProfile(image, classId);
		double[] Yprofile = ImageProcessing.getYProfile(image, classId);

		chart.setSubplot(Xprofile, "X profile");
		chart.setSubplot(Yprofile, "Y profile");

		chart.draw();

	}

	public static void DFT() {
		DFT dft = new DFT();
		double[] input = { 1, 2, 3, 4 };
		double[] output = DFT.forwardMagnitude(input);

		PatternRecognition.print(output);
		
		double[] sines = DFT.sineValues(input);
		System.out.println("sines");
		PatternRecognition.print(sines);
		
	
		double[] cosines = DFT.cosineValues(input);
		System.out.println("cosines");
		PatternRecognition.print(cosines);
	}
	
	public static void DFTProfile() {
		GrayImage image = ImageProcessing.readPgmImage("./Test/images/wife.pgm");
		ImageProcessing.displayImage(image, "file");
		Plot chart = new Plot();
		int classId = 0;

		double[] Xprofile = ImageProcessing.getXProfile(image, classId);
//		float[] Yprofile = ImageProcessing.getYProfile(image, classId);

		double[] sines = DFT.sineValues(Xprofile);
		System.out.println("sines");
		PatternRecognition.print(sines);
	}

}
