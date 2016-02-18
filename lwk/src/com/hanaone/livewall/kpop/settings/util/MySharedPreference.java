package com.hanaone.livewall.kpop.settings.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class MySharedPreference {
	public static final String KEY_IMAGE_PATH = "KEY_IMAGE_PATH";
	public static final String KEY_QUALITY = "KEY_QUALITY";
	public static final String KEY_CHANGE_IMAGE_PATH = "KEY_CHANGE_IMAGE_PATH";
	public static final String KEY_CHANGE_QUALITY = "KEY_CHANGE_QUALITY";
	
	private Context mContext;
	private SharedPreferences mSharedPreferences;
	public MySharedPreference(Context context){
		this.mContext = context;
		mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);
	}
	public void setBooleanPreference(String key, boolean value){
		SharedPreferences.Editor editor = mSharedPreferences.edit();
		
		editor.putBoolean(key, value);
		editor.commit();
	}
	public boolean getBooleanPreference(String key){
		return mSharedPreferences.getBoolean(key, false);
	}
	
	public void setStringPreference(String key, String value){
		SharedPreferences.Editor editor = mSharedPreferences.edit();
		
		editor.putString(key, value);
		editor.commit();		
	}
	public String getStringPreference(String key){
		return mSharedPreferences.getString(key, null);
	}
	
	public void setIntPreference(String key, int value){
		SharedPreferences.Editor editor = mSharedPreferences.edit();
		
		editor.putInt(key, value);
		editor.commit();		
	}
	public int getIntPreference(String key){
		return mSharedPreferences.getInt(key, 0);
	}
	
	public void removeAllBooleanPreference(){
		SharedPreferences.Editor editor = mSharedPreferences.edit();
		
		editor.remove(KEY_CHANGE_IMAGE_PATH);
		editor.remove(KEY_CHANGE_QUALITY);
		
		editor.commit();
	}
}
