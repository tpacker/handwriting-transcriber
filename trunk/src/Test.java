import jigl.image.GrayImage;


public class Test {

	public static void ReadInFile() {
		GrayImage  image = ImageProcessing.readPgmImage("./Test/images/wife.pgm");
		ImageProcessing.displayImage(image, "file");
		
	}
	public static void PlotProfile() {
		GrayImage  image = ImageProcessing.readPgmImage("./Test/images/wife.pgm");
		ImageProcessing.displayImage(image, "file");
		Plot chart = new Plot();
		int classId = 0;
		
		float[] Xprofile = ImageProcessing.getXProfile(image, classId);
		float[] Yprofile = ImageProcessing.getYProfile(image, classId);
		
		chart.setSubplot(Xprofile,"X profile");
		chart.setSubplot(Yprofile,"Y profile");
		
		chart.draw();
		
	}
	

}
