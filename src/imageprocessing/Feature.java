package imageprocessing;


import java.util.ArrayList;

import com.sun.org.apache.bcel.internal.generic.INSTANCEOF;

import sun.security.jca.GetInstance.Instance;

public class Feature {

	public String name;
	public Object value;
	public Float stdev;
	public Feature(String type, Object array) {
		this.name =type;
		this.value = array;
	}
	public Feature(){
		
	}
	public Feature(Object value){
		this.value = value;
	}
	public void print() {
		if(value instanceof float[] )
		{
			System.out.print(name+" ");
			float[] temp = (float[]) value;
			for(int i=0;i<temp.length;i++){
				System.out.print(temp[i]+" ");
			}
			System.out.println();
		}
		else if(value instanceof Integer ||value instanceof Float ){
			System.out.println(value);
		}
	}
	public boolean isSameAs(Feature cofeature) {
		if(value instanceof float[] )
		{	
			float[] val1 = (float[]) value;
			float[] val2 = (float[]) cofeature.value;
			for(int i =0;i< val1.length;i++){
				float v1 = val1[i];
				float v2 = val2[i];
				if(v1!=v2)
					return false;
					
				
			}
				
		}
		else if(value instanceof Integer ){
			
			return ((Integer) this.value).intValue() == ((Integer)cofeature.value).intValue();
		}
		else if(value instanceof Float ){
			
			return ((Float) this.value).floatValue() == ((Float)cofeature.value).floatValue();
		}

		return true;
	}
	public float getDistance(Feature cofeature) {
		float distance = 0;
		
		if(value instanceof float[] )
		{
			//
		}
		else if(value instanceof Integer ){
			float val1;
			float val2;
			if (stdev == null) {
				val1 = ((Integer) this.value).intValue();
				val2 = ((Integer) cofeature.value).intValue();
			} else {
				val1 = ((Integer) this.value).intValue() / this.stdev;
				val2 = ((Integer) cofeature.value).intValue() / this.stdev;
			}
			float diff = val1 - val2;
			// return (float) Math.pow(dif,2);
			distance = Math.abs(diff);
			
			
		}
		else if(value instanceof Float ){
			float val1;
			float val2;
			if (stdev == null) {
				val1 = ((Float) this.value).floatValue();
				val2 = ((Float) cofeature.value).floatValue();
			} else {
				val1 = ((Float) this.value).floatValue() / this.stdev;
				val2 = ((Float) cofeature.value).floatValue() / this.stdev;
			}
			float diff = val1 - val2;
			distance = (float) Math.pow(diff, 2);
			
		}

		return distance;
	}
	
	private float getMinDist(float[] profile, float[] profileX,float[] profileY) {
		
		float[] profileXrev = PatternRecognition.reverseArray(profileX);
		float[] profileYrev = PatternRecognition.reverseArray(profileY);
		
		ArrayList<Float> distances = new ArrayList<Float>();
		
		float dist1 = calculateDistance(profile, profileX);
		distances.add(dist1);
		float dist2 = calculateDistance(profile, profileXrev);
		distances.add(dist2);
		float dist3 = calculateDistance(profile, profileY);
		distances.add(dist3);
		float dist4 = calculateDistance(profile, profileYrev);
		distances.add(dist4);
		
		
		float minDist = PatternRecognition.getSmallestValue(distances); 
		
		return minDist;
	}
	

	
	public float calculateDistance(float[]val1,float[]val2){
		float distance= 0;
		for(int i =0;i< val1.length;i++){
			float v1 = val1[i];
			float v2 = val2[i];
			distance= distance+ (float) Math.pow(v1-v2,2);
//			distance= distance+ (float) Math.abs(v1-v2);
		}
		return distance;
	}
	
	public void add(Feature feature2) {
		if (feature2.value instanceof float[]) {
			if (value == null) {
				value = feature2.value;
			} else {
				 float[] val1 =(float[]) value;
				 float[] val2 =(float[]) feature2.value;
				 for(int i=0;i<val1.length;i++)
				 {
				 val1[i] = val1[i]+val2[i];
				 }
								

			}
		}
		else if(feature2.value instanceof Integer ){
			if(value==null){
				value = feature2.value;
			}
			else{
			value = new Integer(((Integer) this.value).intValue() + ((Integer)feature2.value).intValue());
			}
		}
		else if(feature2.value instanceof Float ){
			if(value==null){
				value = feature2.value;
			}
			else{
			value = new Float(((Float) this.value).floatValue() + ((Float)feature2.value).floatValue());
			}
		}
	}
	
	public void divide(int size) {
		if(value instanceof float[] )
		{	
			if(value==null){
				return;
			}
			else{
				float[] val =(float[]) value; 
				for(int i=0;i<val.length;i++)
				{
					val[i] = val[i]/size;
				}
				
			}
				
		}
		else if(value instanceof Integer ){
			
			value = new Integer(((Integer) this.value).intValue() / size);
		}
		else if(value instanceof Float ){
			
			value = new Float(((Float) this.value).floatValue() / size);
		}
		
	}
	public Feature createCopy() {
		if(value instanceof float[] )
		{
			float[] val = (float[]) value;
			return new Feature("",val.clone());
		}
		else if(value instanceof Integer )
		{
			Integer val = (Integer)value;
			return new Feature("",new Integer((int) val));
		}
		else if(value instanceof Float )
		{
			Float val = (Float)value;
			return new Feature("",new Float((float) val));
		}
		return null;
	}
	

}
