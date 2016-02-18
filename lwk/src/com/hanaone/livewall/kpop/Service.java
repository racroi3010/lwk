package com.hanaone.livewall.kpop;

import rajawali.renderer.RajawaliRenderer;
import rajawali.wallpaper.Wallpaper;
import android.content.Context;

public class Service extends Wallpaper {
	private static RajawaliRipplesRenderer mRenderer;

	public Engine onCreateEngine() {
		mRenderer = new RajawaliRipplesRenderer(this);
		return new WallpaperEngine(this.getSharedPreferences(SHARED_PREFS_NAME,
				Context.MODE_PRIVATE), getBaseContext(), mRenderer, false);
		
	}
	
	public static RajawaliRenderer getRender(){
		return mRenderer;
	}
}