package imageprocessing;
import java.util.ArrayList;

import com.sun.xml.internal.fastinfoset.sax.Features;

import jigl.image.GrayImage;



public class Pattern {

	
	
	public ArrayList<Feature> features;
	public String name;
	public int classId = -1;
	public int indexClosestPrototype = -1; 
	public float dstClosestPrototype = 0; 

	public Pattern() {
		
		features = new ArrayList(); 
		name = "";
	}

	public Pattern(int classId, String name) {
		features = new ArrayList(); 
		this.classId = classId;
		this.name = name;
	}
	public Pattern(ArrayList<Feature>features) {
		this.features = features;
	}

	public void addFeature(Feature feature) {
		features.add(feature);
		
	}

	public void print() {
		
		for(int i =0;i<features.size();i++)
		{	
			((Feature) features.get(i)).print();
		}
		
	}

	public Pattern createCopy() {
		Pattern newpattern = new Pattern(this.classId,this.name);
		
		for(int i=0;i<this.features.size();i++)
		{
			Feature feature = features.get(i);
			Feature newfeature = feature.createCopy();
			newpattern.features.add(newfeature);
		}
		return newpattern;
	}

	public boolean isSame(Pattern pattern2) {
		
		for(int i=0;i<this.features.size();i++)
		{
			Feature feature = features.get(i);
			Feature feature2 = pattern2.features.get(i);
			if(!feature.isSameAs(feature2))return false;
		}
		return true;
	}



	
}
