package imageprocessing;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;
import java.util.StringTokenizer;

//import test.Test;
import utilities.BoundingBox;

import com.sun.corba.se.impl.orbutil.DenseIntMapImpl;
import com.sun.xml.internal.bind.v2.runtime.unmarshaller.Discarder;

import jigl.image.BadImageException;
import jigl.image.ColorImage;
import jigl.image.GrayImage;
import jigl.image.ImageNotSupportedException;
import jigl.image.ops.levelOps.Scale;
//import sun.security.krb5.internal.bj;
//import sun.security.krb5.internal.i;



public class PatternRecognition {
	
	public static void print(double[] array){
		for(int i =0;i<array.length;i++){
//			Pattern pattern = ((Pattern) array[i]);
			System.out.println(array[i]+" ");
//			pattern.print();
		}
	}

	
	
	public static void printPatterns(ArrayList patterns){
		for(int i =0;i<patterns.size();i++){
			Pattern pattern = ((Pattern) patterns.get(i));
			System.out.println("Pattern("+pattern.classId+"): "+pattern.name);
			pattern.print();
		}
	}

	
	public static double[] reverseProfile(double[] profile) {
		double[] tempSignature = new double[profile.length];
		
		for(int i=0,j=profile.length-1;i<tempSignature.length;i++,j--){
			tempSignature[i]= profile[j];
		}
		
		return tempSignature;
	}
	
	
	static float[] getXProfile(int length, GrayImage image, int classId) {
		BoundingBox boundingBox = getBoundingBox(image, classId);
		short[][] pixels = image.getData();
		int lengthOfProfile = boundingBox.maxX - boundingBox.minX;

		float profile[] = new float[lengthOfProfile + 1];

		for (int i = boundingBox.minY; i < boundingBox.maxY + 1; i++) {
			for (int j = boundingBox.minX, bucket = 0; j < boundingBox.maxX + 1; j++,bucket++) {
				if (pixels[i][j] == classId) {
					profile[bucket] = profile[bucket] + 1;
				}
			}
		}

//		profile = fixProfile(profile);

//		profile = scaleDownProfile(length, profile);
		return profile;
		
	}
	

	static float[] getYProfile(int length, GrayImage image, int classId) {
		
		BoundingBox boundingBox = getBoundingBox(image, classId);
		short[][] pixels = image.getData();
		int lengthOfProfile = boundingBox.maxY - boundingBox.minY;

		float profile[] = new float[lengthOfProfile + 1];

		for (int i = boundingBox.minY,bucket = 0; i < boundingBox.maxY + 1; i++,bucket++) {
			for (int j = boundingBox.minX ; j < boundingBox.maxX + 1; j++) {
				if (pixels[i][j] == classId) {
					profile[bucket] = profile[bucket] + 1;
				}
			}
		}

//		profile = fixProfile(profile);

//		profile = scaleDownProfile(length, profile);
		profile = ImageProcessing.reverseProfile(profile);
		return profile;
	}

	static BoundingBox getBoundingBox(GrayImage image, int classId) {
		int minX = Integer.MAX_VALUE;
		int maxX = Integer.MIN_VALUE;
		int minY = Integer.MAX_VALUE;
		int maxY = Integer.MIN_VALUE;

		short[][] pixels = image.getData();
		for (int i = 0; i < pixels.length; i++) {
			for (int j = 0; j < pixels[0].length; j++) {
				if (pixels[i][j] == classId) {
					if (j < minX)
						minX = j;
					if (j > maxX)
						maxX = j;
					if (i < minY)
						minY = i;
					if (i > maxY)
						maxY = i;
				}
			}
		}

		return new BoundingBox(minX, maxX, minY, maxY);
	}
	
	public static float getXcount(GrayImage image, int classId) {
		short[][] pixels = image.getData();
		BoundingBox boundingBox = PatternRecognition.getBoundingBox(image, classId);
		float width = boundingBox.maxX - boundingBox.minX;
		float countx = 0;
		Pixel center = ImageProcessing.getCenterOfGravity(image, classId);
		for (int i = boundingBox.minX; i < boundingBox.maxX + 1; i++) {
			if (pixels[center.y][i] == classId) {
					countx = countx + 1;
				}
		}
		return countx/width;
	}
	
	public static float getYcount(GrayImage image, int classId) {
		short[][] pixels = image.getData();
		BoundingBox boundingBox = PatternRecognition.getBoundingBox(image, classId);
		float height = boundingBox.maxY - boundingBox.minY;
		float county = 0;
		Pixel center = ImageProcessing.getCenterOfGravity(image, classId);
		for (int i = boundingBox.minY; i < boundingBox.maxY + 1; i++) {
			if (pixels[i][center.x] == classId) {
					county = county + 1;
				}
		}
		return county/height;
	}
	
	public static float getShape2Area(GrayImage image, int classId) {
		
		BoundingBox boundingBox = PatternRecognition.getBoundingBox(image, classId);
		float width = boundingBox.maxX - boundingBox.minX;
		float heigth = boundingBox.maxY - boundingBox.minY;
		
		float areaOfBB = width * heigth;
		float areaOfShape = getShapeArea(image,classId);
		
		
		return areaOfShape/areaOfBB;
	}


	public static float getShapeArea(GrayImage image, int classId) {
		short[][] pixels = image.getData();
		BoundingBox boundingBox = PatternRecognition.getBoundingBox(image, classId);
		float area = 0;
		for (int i = boundingBox.minY; i < boundingBox.maxY + 1; i++) {
			for (int j = boundingBox.minX, bucket = 0; j < boundingBox.maxX + 1; j++,bucket++) {
				if (pixels[i][j] == classId) {
					area = area + 1;
				}
			}
		}
		return area;
	}
	
	public static float getSmallestValue(ArrayList<Float>distances){

		float minDist = 100000000;
		for(int i =0;i<distances.size();i++){
			float distance = distances.get(i);
			if(distance < minDist){
				minDist = distance;
			}
		}
		return minDist; //new Float(minDist)
	}
	
	public static float[] reverseArray(float[] array) {
		float[] newarray = new float[array.length];

		for (int i = 0, j = array.length - 1; i < array.length; i++, j--) {
			newarray[i]= array[j];
		}
		return newarray;
	}
}

