package com.hanaone.ip;

public class ImageFilter extends Constants {
	
	static {
		System.loadLibrary("imagefilter");
	}
	
	public void filter(String input, String output, int filterType){
		switch (filterType) {
		case FILTER_HSB_ADJUST:
			hsbAdFilter(input, output);
			break;

		case FILTER_TEST_JNI:
			filterCurves(input, output);
		default:
			break;
		}
	}
	public void filterWhiteBalance(String input, String output, float temporature, float tempStrength){
		WhiteBalance(input, output, temporature, tempStrength);
	}
	public void filterCurves(String input, String output){
		nativeCurveFilter(input, output);
	}

	
	
	private native void hsbAdFilter(String input, String output);
	private native void WhiteBalance(String input, String output, float temporature, float tempStrength);
	
	private native void nativeCurveFilter(String input, String output);
}
