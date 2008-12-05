package utilities;

import imageprocessing.PatternRecognition;

public class DFT {
	
	public static final int RECTANGULAR = 0;
	public static final int HANN = 1;
	public static final int HAMMING = 2;
	public static final int BLACKMANN = 3;
	
	public static final double[] forwardMagnitude(double[] input) {
		int N = input.length;
		double[] mag = new double[N];
		double[] c = new double[N];
		double[] s = new double[N];
		double twoPi = 2*Math.PI;
		
		for(int i=0; i<N; i++) {
			for(int j=0; j<N; j++) {
				c[i] += input[j]*Math.cos(i*j*twoPi/N);
				s[i] -= input[j]*Math.sin(i*j*twoPi/N);
			}
			c[i]/=N;
			s[i]/=N;
			
			mag[i]=Math.sqrt(c[i]*c[i]+s[i]*s[i]);
		}
		
		return mag;
	}
	public static final double[] forward(double[] input) {
		int N = input.length;
		int sign = -1;
		double[] W = new double[N];
		double[] X = new double[N];
		
		for(int i=0; i<N; i++) {
				W[i]= Math.exp(sign *2* Math.PI*i/N);
		}
		for(int n=0; n<N; n++) {
			double sum=0;
			for(int k=0; k<N; k++) {
				sum = sum + W[n*k%N]*input[k];
			}
	
		
			X[n] = sum;
		}
		
		return X;
	}
	
//	def dft(x, sign=-1): 
//	    from cmath import pi, exp 
//	    N, W = len(x), [] 
//	    for i in range(N): # exp(-j...) is default 
//	        W.append(exp(sign * 2j * pi * i / N)) 
//	    X = [] 
//	    for n in range(N): 
//	        sum = 0 
//	        for k in range(N): 
//	            sum = sum + W[n * k % N] * x[k] 
//	        X.append(sum) 
//	    return X 
	
	
	public static final double[] sineValues(double[] input) {
		int N = input.length;
		double[] s = new double[N];
		double twoPi = 2*Math.PI;
		
		for(int i=0; i<N; i++) {
			for(int j=0; j<N; j++) {
				s[i] -= input[j]*Math.sin(i*j*twoPi/N);
			}
			s[i]/=N;
			
		}
		
		return s;
	}
	
	public static final double[] cosineValues(double[] input) {
		int N = input.length;
		double[] c = new double[N];
		double twoPi = 2*Math.PI;
		
		for(int i=0; i<N; i++) {
			for(int j=0; j<N; j++) {
				c[i] += input[j]*Math.cos(i*j*twoPi/N);
			}
			c[i]/=N;
			
		}
		
		return c;
	}
	public static void main(String[] args){
		DFT dft = new DFT();
		FFT fft = new FFT();
		double[] input = { 0, 1, 2};
		double[] output = DFT.forward(input);

////		double[] sines = DFT.sineValues(input);
//		double[] sines = FFT.sineValues(input);
//		System.out.println("sines");
//		PatternRecognition.print(sines);
////		
////		double[] cosines = DFT.cosineValues(input);
//		double[] cosines = FFT.cosineValues(input);
//		System.out.println("cosines");
//		PatternRecognition.print(cosines);
		
		System.out.println("output");
		PatternRecognition.print(output);
	}
	
	
}
