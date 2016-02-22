package com.hanaone.ip;

import android.os.Environment;

public class Constants {
	public static final int FILTER_HSB_ADJUST = 0;
	
	public static final int FILTER_TEST_JNI = 1;
	
	public static final String PATH = Environment.getExternalStorageDirectory().getPath() + "/KPOP/";
	//public static final String PATH_TEMP = PATH + "TEMP/";
	
	
	public static final String [] IMAGE_DATA = {
		"iu.jpg",
		"kpop_star_1.jpg",
		"kpop_star_2.jpg",
		"utrecht.jpg"
	};
}
