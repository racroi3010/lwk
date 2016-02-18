package com.hanaone.livewall.kpop;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import com.devsmart.android.ui.HorizontalListView;
import com.hanaone.livewall.kpop.R;
import com.hanaone.ip.ImageFilter;
import com.hanaone.livewall.kpop.settings.adapter.ListImageAdapter;

import rajawali.renderer.RajawaliRenderer;
import rajawali.wallpaper.Wallpaper;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.os.Environment;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.PreferenceActivity;

// Deprecated PreferenceActivity methods are used for API Level 10 (and lower) compatibility
// https://developer.android.com/guide/topics/ui/settings.html#Overview
@SuppressWarnings("deprecation")
public class Settings extends PreferenceActivity{

	protected void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		setContentView(R.layout.activity_image_processing);
		addPreferencesFromResource(R.xml.settings);
		
		// save image
		String input = Environment.getExternalStorageDirectory().getPath() + "/KPOP/iu.jpg"; 
		try {
			InputStream is = getAssets().open("iu.jpg");
			
			
			FileOutputStream os = new FileOutputStream(new File(input));
			byte[] buf = new byte[1024];
			int read = 0;
			while((read = is.read(buf)) > 0){
				os.write(buf, 0, read);
			}
			is.close();
			os.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
//		ImageFilter mFilter = new ImageFilter();
//		
//		String output = Environment.getExternalStorageDirectory().getPath() + "/KPOP/iu_out.jpg";
//		mFilter.filterWhiteBalance(input, output, 4000, 55);
//		
//		List<String> dataSet = new ArrayList<String>();
//		
//		for(int i = 0; i < 10; i ++){
//			dataSet.add(output);
//		}
		
//		// add view
//		HorizontalListView listView = (HorizontalListView) findViewById(R.id.lv_preview);
//		ListImageAdapter adapter = new ListImageAdapter(getBaseContext());
//		adapter.setmDataSet(dataSet);
//		listView.setAdapter(adapter);		
	}

	protected void onResume() {
		super.onResume();
	}

	protected void onDestroy() {
		super.onDestroy();
	}


}