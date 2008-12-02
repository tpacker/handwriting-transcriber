package imageprocessing;
import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.Stack;

import utilities.BoundingBox;

import jigl.gui.ImageCanvas;
import jigl.image.BadImageException;
import jigl.image.ColorModelNotSupportedException;
import jigl.image.ColorModelUnknownException;
import jigl.image.GrayImage;
import jigl.image.Image;
import jigl.image.UnknownImageTypeException;
import jigl.image.io.ImageInputStream;
import jigl.image.io.ImageOutputStream;

public class ImageProcessing {

	public static void main(String[] args) {
	}

	public static double[] getUpperProfile(GrayImage image, int classId) {
		
		BoundingBox boundingBox = PatternRecognition.getBoundingBox(image, classId);
		short[][] pixels = image.getData();
		int lengthOfProfile = boundingBox.maxX - boundingBox.minX;

		double profile[] = new double[lengthOfProfile + 1];

	
			for (int j = boundingBox.minX, bucket = 0; j < boundingBox.maxX + 1; j++,bucket++) {
				int length = 0;
				for (int i = boundingBox.minY; i < boundingBox.maxY + 1; i++) {
					
					if (pixels[i][j] == classId) {
						profile[bucket] = length;
					}
					else
					{
						length++;
					}
				}
			}

		return profile;
		
	}
	
	public static double[] getXProfile(GrayImage image, int classId) {
		
		BoundingBox boundingBox = PatternRecognition.getBoundingBox(image, classId);
		short[][] pixels = image.getData();
		int lengthOfProfile = boundingBox.maxX - boundingBox.minX;

		double profile[] = new double[lengthOfProfile + 1];

		for (int i = boundingBox.minY; i < boundingBox.maxY + 1; i++) {
			for (int j = boundingBox.minX, bucket = 0; j < boundingBox.maxX + 1; j++,bucket++) {
				if (pixels[i][j] == classId) {
					profile[bucket] = profile[bucket] + 1;
				}
			}
		}

		return profile;
		
	}
	
	
	public static double[] getYProfile(GrayImage image, int classId) {
		
		BoundingBox boundingBox = PatternRecognition.getBoundingBox(image, classId);
		short[][] pixels = image.getData();
		int lengthOfProfile = boundingBox.maxY - boundingBox.minY;

		double profile[] = new double[lengthOfProfile + 1];

		for (int i = boundingBox.minY,bucket = 0; i < boundingBox.maxY + 1; i++,bucket++) {
			for (int j = boundingBox.minX ; j < boundingBox.maxX + 1; j++) {
				if (pixels[i][j] == classId) {
					profile[bucket] = profile[bucket] + 1;
				}
			}
		}

//		profile = PatternRecognition.reverseProfile(profile);
		return profile;
	}
	
	public static void saveFile(String filename, Image image) {
		try {
			ImageOutputStream ioimg = new ImageOutputStream(filename);
			ioimg.write(image);
			ioimg.close();
		} catch (UnknownImageTypeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ColorModelNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ColorModelUnknownException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static GrayImage connectedComponents(GrayImage image, int i, int j,
			int oldvalue, int newvalue) {
		short[][] pixels = image.getData();
		short snewvalue = (short) newvalue;
		Stack stack = new Stack();

		if (pixels[i][j] == oldvalue) {
			pixels[i][j] = snewvalue;
			stack.push(new Pixel(i, j));

			while (!stack.empty()) {

				Pixel newPixel = (Pixel) stack.pop();
				int x = newPixel.x;
				int y = newPixel.y;

				if ((x - 1 >= 0) && (y - 1 >= 0))
					if (pixels[x - 1][y - 1] == oldvalue) {
						stack.push(new Pixel(x - 1, y - 1));
						pixels[x - 1][y - 1] = snewvalue;
					}
				if ((x - 1 >= 0))
					if (pixels[x - 1][y] == oldvalue) {
						stack.push(new Pixel(x - 1, y));
						pixels[x - 1][y] = snewvalue;
					}
				if ((x - 1 >= 0) && (y + 1 < pixels[0].length))
					if (pixels[x - 1][y + 1] == oldvalue) {
						stack.push(new Pixel(x - 1, y + 1));
						pixels[x - 1][y + 1] = snewvalue;
					}
				if ((y - 1 >= 0))
					if (pixels[x][y - 1] == oldvalue) {
						stack.push(new Pixel(x, y - 1));
						pixels[x][y - 1] = snewvalue;
					}
				if ((y + 1 < pixels[0].length))
					if (pixels[x][y + 1] == oldvalue) {
						stack.push(new Pixel(x, y + 1));
						pixels[x][y + 1] = snewvalue;
					}
				if ((x + 1 < pixels.length) && (y - 1 >= 0))
					if (pixels[x + 1][y - 1] == oldvalue) {
						stack.push(new Pixel(x + 1, y - 1));
						pixels[x + 1][y - 1] = snewvalue;
					}

				if ((x + 1 < pixels.length))
					if (pixels[x + 1][y] == oldvalue) {
						stack.push(new Pixel(x + 1, y));
						pixels[x + 1][y] = snewvalue;
					}
				if ((x + 1 < pixels.length) && (y + 1 < pixels[0].length))
					if (pixels[x + 1][y + 1] == oldvalue) {
						stack.push(new Pixel(x + 1, y + 1));
						pixels[x + 1][y + 1] = snewvalue;
					}

			}

		} else {
			System.out.println("you are trying to change the same color");
		}

		return new GrayImage(pixels);
	}

	public static void displayImage(Image image, String title) {
		ImageCanvas canvas;
		Frame frame = new Frame(title);
		try {
			canvas = new ImageCanvas(image);
			frame.add(canvas);
		} catch (BadImageException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		frame.setSize(image.X(), image.Y() + 20);
		frame.setVisible(true);
		frame.addWindowListener(new WindowAdapter() // Setup quiting on close of
				// window

				{
					public void windowActivated(WindowEvent e) {

					}

					public void windowClosing(WindowEvent evt) {
						System.exit(0);
					}
				});

	}


	public static void displayImage(GrayImage image, String title) {
		ImageCanvas canvas;
		try {
			canvas = new ImageCanvas(image);

			Frame frame = new Frame(title);
			frame.add(canvas);
			frame.setSize(image.X(), image.Y() + 30); // 20 for the title
			frame.setVisible(true);
			frame.addWindowListener(new WindowAdapter() // Setup quiting on
														// close of window
					{
						public void windowClosing(WindowEvent evt) {
							System.exit(0);
						}
					});
		} catch (BadImageException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static GrayImage readPgmImage(String filename) {
		ImageInputStream is;
		GrayImage image = null;
		try {
			is = new ImageInputStream(filename);
			image = (GrayImage) is.read();
			is.close();
		} catch (Exception e) {
			System.out.println("Cannot open image");
			return null;
		}
		return image;

	}

	public static void printPixels(GrayImage image) {
		short[][] pixels = image.getData();
		for (int i = 0; i < pixels.length; i++) {
			for (int j = 0; j < pixels[0].length; j++) {
				System.out.print(pixels[i][j] + " ");

			}
			System.out.println("");
		}

	}

	public static void printArray(short[][] pixels) {
		System.out.println();
		for (int i = 0; i < pixels.length; i++) {
			for (int j = 0; j < pixels[0].length; j++) {
				System.out.print(pixels[i][j] + " ");

			}
			System.out.println("");
		}

	}

	private static GrayImage performCountourFollowingVariation(GrayImage image,
			int t, int m) {
		short[][] pixels = image.getData();
		GrayImage copyImg = (GrayImage) image.copy();
		short[][] pixelsCountour = copyImg.getData();
		Pixel start_pos = null;

		boolean done = false, itHasCountour = false;
		int countourLength = 0;
		int area = 0;
		int objNumber = 0;

		for (int i = 0; i < pixels.length; i++) {// pixels.length
			for (int j = 0; j < pixels[0].length; j++) { // pixels[0].length

				// Pixel p_pos = new Pixel(i, j);
				Pixel p = new Pixel(i, j, pixels, 0);
				Pixel farMost = new Pixel(0, 0);

				if (pixels[i][j] == m) {
					if (j + 1 < pixels.length)
						j++;
					while (pixels[i][j] == m && (j + 1 < pixels.length)) {
						j++;
					}
					while (pixels[i][j] > t && (j + 1 < pixels.length)) {
						j++;
					}
					if (j + 1 < pixels.length)
						j++;
					if (pixels[i][j] == m && j - 1 >= 0)
						j--;
					// p_pos = new Pixel(i, j);
					p = new Pixel(i, j, pixels, 0);
				}

				if ((p.value() > t) && (pixels[p.i][p.j - 1] != m)) { // p-1!=
																		// mpixelsCountour
					// backup one pixel;
					p.backup();
					// /
					start_pos = p.position();
					done = false;
					while (!done) {

						while (p.pixel_in_front() > t) {
							p.turnLeft();
						}
						pixels[p.i][p.j] = (short) m;
						countourLength++;
						if (p.greaterThan(farMost)) {
							farMost.setGreater(p);
						}
						// step;
						p.step();
						done = (start_pos.samePositionAs(p.position()));

						if (!done) {
							// turn right
							p.turnRigth();
						}
					}

					i = start_pos.i;
					j = start_pos.j - 1;
					itHasCountour = true;
				}

				if (itHasCountour) {
					System.out.println("Object " + objNumber + " cl: "
							+ countourLength);
					objNumber++;
					countourLength = 0;
					itHasCountour = false;
				}

			}
		}

		return new GrayImage(pixels);// TODO Auto-generated method stub
	}


	public static double getOrientationAngle(GrayImage image, int classId) {

		short[][] pixels = image.getData();
		double momentsx = 0, momentsy = 0;
		Pixel centerMass = getCenterOfGravity(image, classId);
		double u11 = 0, u20 = 0, u02 = 0;
		for (int y = 0; y < pixels.length; y++) {
			for (int x = 0; x < pixels[0].length; x++) {
				if (pixels[y][x] == classId) {
					momentsx = momentsx + x;
					momentsy = momentsy + y;
					u11 = u11 + (x - centerMass.x) * (y - centerMass.y);
					u20 = u20 + Math.pow((x - centerMass.x), 2);
					u02 = u02 + Math.pow((y - centerMass.y), 2);
				}
			}
		}

		double angle = .5 * Math.atan2((2 * u11), (u20 - u02));
		angle = ConvertRadiansToDegrees(angle);
		return angle;
	}

	public static double ConvertRadiansToDegrees(double radians) {
		double degrees = (180 / Math.PI) * radians;
		return (degrees);
	}

	public static Pixel getCenterOfGravity(GrayImage image, int classId) {

		short[][] pixels = image.getData();
		int momentsx = 0, momentsy = 0, centerMassX = 0, centerMassY = 0, M00 = 0;

		for (int i = 0; i < pixels.length; i++) {
			for (int j = 0; j < pixels[0].length; j++) {
				if (pixels[i][j] == classId) {
					momentsx = momentsx + j;
					momentsy = momentsy + i;
					M00++;
				}
			}
		}
		centerMassX = momentsx / M00;
		centerMassY = momentsy / M00;
		return new Pixel(centerMassX, centerMassY);
	}

	// public static void displayImage(String imagename) {
	// JFrame frame = new JFrame("Display image");
	// Panel panel = new ImageComponent(imagename);
	// frame.getContentPane().add(panel);
	// frame.setSize(800, 800);
	// frame.setVisible(true);
	//			
	// }

	public static float[] reverseProfile(float[] profile) {
		float[] tempProfile = new float[profile.length];

		for (int i = 0, j = profile.length - 1; i < tempProfile.length; i++, j--) {
			tempProfile[i] = profile[j];
		}

		return tempProfile;
	}

}