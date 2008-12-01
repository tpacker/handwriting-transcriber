package classification;


import java.util.ArrayList;

import jigl.image.GrayImage;



public class RecordCell
{
	private GrayImage image;
	private String transcription;
	private ArrayList<Double> features = new ArrayList();

	private void setImage(GrayImage image)
	{
		this.image = image;
	}

	private GrayImage getImage()
	{
		return image;
	}
}
