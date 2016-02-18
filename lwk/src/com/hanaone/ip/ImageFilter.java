package com.hanaone.ip;

public class ImageFilter extends Constants {
	
	static {
		//System.loadLibrary("imagefilter");
	}
	
	public void filter(String input, String output, int filterType){
		switch (filterType) {
		case FILTER_HSB_ADJUST:
			hsbAdFilter(input, output);
			break;

		default:
			break;
		}
	}
	public void filterWhiteBalance(String input, String output, float temporature, float tempStrength){
		WhiteBalance(input, output, temporature, tempStrength);
	}

	private native void hsbAdFilter(String input, String output);
	private native void WhiteBalance(String input, String output, float temporature, float tempStrength);
	
}
