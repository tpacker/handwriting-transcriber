package utilities;

import jigl.image.ColorImage;
import jigl.image.GrayImage;
import jigl.image.Image;
import jigl.image.io.ImageInputStream;
import jigl.image.io.ImageOutputStream;



/**
 * Provides static functions for reading and writing image files.
 */
public class File
{
	/**
	 * Opens and returns a grey-scale image.
	 * @param Name of the file to be opened.
	 * @return An image from the given file.
	 */
	public static GrayImage openGrayImage(String filename)
	{
		GrayImage returnedImage = null;
		
		try
		{
			ImageInputStream returnedInputStream = new ImageInputStream(filename);
			returnedImage = (GrayImage)returnedInputStream.read();
			returnedInputStream.close();
		}
		catch (Exception e)
		{
			System.out.println("Current directory: " + System.getProperty("user.dir"));
			System.out.println("Exception caught " + e.getMessage());
		}
		
		return returnedImage;
	}
	
	
	/**
	 * Opens and returns a ColorImage.
	 * @param Name of the file to be opened.
	 * @return An image from the given file.
	 */
	public static ColorImage openColorImage(String filename)
	{
		ColorImage returnedImage = null;
		
		try
		{
			ImageInputStream returnedInputStream = new ImageInputStream(filename);
			returnedImage = (ColorImage)returnedInputStream.read();
			returnedInputStream.close();
		}
		catch (Exception e)
		{
			System.out.println("Current directory: " + System.getProperty("user.dir"));
			System.out.println("Exception caught " + e.getMessage());
		}
		
		return returnedImage;
	}
	
	
	/**
	 * Writes an image to file.
	 * @param outputImage Image to be written.
	 * @param filename Name of the file to be written.
	 */
	public static void writeImage(Image outputImage, String filename)
	{
		try
		{
			ImageOutputStream outputStream = new ImageOutputStream(filename);
			outputStream.write(outputImage);
			outputStream.close();
		}
		catch (Exception e)
		{
			System.out.println("Current directory: " + System.getProperty("user.dir"));
			System.out.println("Exception caught " + e.getMessage());
		}
	}
}
