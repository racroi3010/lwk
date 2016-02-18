package com.hanaone.livewall.kpop.settings.dataset;

import android.graphics.Bitmap;

public class FilterData {
	private int type;
	private int hanType;
	private Bitmap bitmap;
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public Bitmap getBitmap() {
		return bitmap;
	}
	public void setBitmap(Bitmap bitmap) {
		this.bitmap = bitmap;
	}
	public void setHanType(int hanType) {
		this.hanType = hanType;
	}
	public int getHanType() {
		return hanType;
	}
	
}
