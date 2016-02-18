package com.hanaone.livewall.kpop;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Random;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import com.hanaone.livewall.kpop.R;
import com.hanaone.livewall.kpop.settings.util.MySharedPreference;
import com.hanaone.ip.ImageFilter;


import rajawali.BaseObject3D;
import rajawali.animation.Animation3D;
import rajawali.animation.RotateAnimation3D;
import rajawali.filters.TouchRippleFilter;
import rajawali.lights.DirectionalLight;
import rajawali.materials.DiffuseMaterial;
import rajawali.materials.SimpleMaterial;
import rajawali.materials.TextureInfo;
import rajawali.math.Number3D;
import rajawali.primitives.Cube;
import rajawali.primitives.Plane;
import rajawali.renderer.PostProcessingRenderer.PostProcessingQuality;
import rajawali.renderer.RajawaliRenderer;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.Bitmap.CompressFormat;
import android.os.Environment;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.PreferenceManager;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

public class RajawaliRipplesRenderer extends RajawaliRenderer{
//	private final int NUM_CUBES_H = 2;
//	private final int NUM_CUBES_V = 2;
//	private final int NUM_CUBES = NUM_CUBES_H * NUM_CUBES_V;
//	private Animation3D[] mAnims;
	private TouchRippleFilter mFilter;
	private long frameCount;
	private final int QUAD_SEGMENTS = 40;
	private Point mScreenSize;	

	//private String imageFile;
	//private String  input;
	private TextureInfo mTextureInfo;
	private SimpleMaterial planeMat;
	private MySharedPreference mPreference;
	public RajawaliRipplesRenderer(Context context) {
		super(context);
		setFrameRate(60);
		mPreference = new MySharedPreference(mContext);
	}
	protected void initScene() {	
		
		mCamera.setPosition(0, 0, -10);
		
		DirectionalLight light = new DirectionalLight(0, 0, 1);
		light.setPower(1f);
		
		planeMat = new SimpleMaterial();
		
//		Bitmap texture = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.iu);	
//		
//		float scale = (float)texture.getWidth()/(float)texture.getHeight();
//		
//		mTextureInfo = mTextureManager.addTexture(texture);
//		planeMat.addTexture(mTextureInfo);
		
		
		
		Plane plane = new Plane(4, 4, 1, 1);
		plane.setRotZ(-90);
		plane.setScale(3.7f);
		plane.setMaterial(planeMat);
		

		addChild(plane);
		
		mFilter = new TouchRippleFilter();
		mFilter.setRippleSize(90);
		mPostProcessingRenderer.setQuadSegments(QUAD_SEGMENTS);
		mPostProcessingRenderer.setQuality(PostProcessingQuality.MEDIUM);
		addPostProcessingFilter(mFilter);
		
		DisplayMetrics display = mContext.getResources().getDisplayMetrics();
		mScreenSize = new Point();
		mScreenSize.x = display.widthPixels;
		mScreenSize.y = display.heightPixels;		
			
		mCamera.setZ(-20f);
		
		onInitImage();
		onInitEffect();
	}
	
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		super.onSurfaceCreated(gl, config);
		mFilter.setScreenSize(mViewportWidth, mViewportHeight);
		onInitFromPrefence();
	}
	
	public void onDrawFrame(GL10 glUnused) {
		super.onDrawFrame(glUnused);
		mFilter.setTime((float) frameCount++ *.05f);
	}
	
	public void onSurfaceChanged(GL10 gl, int width, int height) {
		super.onSurfaceChanged(gl, width, height);
		mFilter.setScreenSize(width, height);
	}
	public void setTouch(float x, float y) {
		mFilter.addTouch(x, y, frameCount *.05f);
	}

	@Override
	public void onSurfaceDestroyed() {
		//PreferenceManager.getDefaultSharedPreferences(mContext).unregisterOnSharedPreferenceChangeListener(mListener);
		super.onSurfaceDestroyed();
	}
	@Override
	public void onTouchEvent(MotionEvent event) {
		if(event.getAction() == MotionEvent.ACTION_DOWN)
		{
			setTouch(event.getX() / mScreenSize.x, 1.0f - (event.getY() / mScreenSize.y));
		}
		super.onTouchEvent(event);
	}

	private void onInitFromPrefence(){
		if(mPreference.getBooleanPreference(MySharedPreference.KEY_CHANGE_IMAGE_PATH)){
			onInitImage();
		}
		
		if(mPreference.getBooleanPreference(MySharedPreference.KEY_CHANGE_QUALITY)){
			onInitEffect();
		}
		
		
		mPreference.removeAllBooleanPreference();
	}
	private void onInitImage(){
		String imagePath = mPreference.getStringPreference(MySharedPreference.KEY_IMAGE_PATH);
		if(imagePath != null){
			mTextureManager.getTextureInfoList().clear();
			planeMat.unbindTextures();
						
			Bitmap texture = BitmapFactory.decodeFile(imagePath);			
			
			mTextureInfo = mTextureManager.addTexture(texture);
			planeMat.addTexture(mTextureInfo);	
			planeMat.reload();				
		} else {
			mTextureManager.getTextureInfoList().clear();
			planeMat.unbindTextures();			
			Bitmap texture = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.iu);	
			mTextureInfo = mTextureManager.addTexture(texture);
			planeMat.addTexture(mTextureInfo);	
			planeMat.reload();			
		}
	}
	private void onInitEffect(){
		switch (mPreference.getIntPreference(MySharedPreference.KEY_QUALITY)) {
		case 0:
			mPostProcessingRenderer.setQuality(PostProcessingQuality.VERY_LOW);
			break;
		case 1:
			mPostProcessingRenderer.setQuality(PostProcessingQuality.LOW);
			break;
		case 2:
			mPostProcessingRenderer.setQuality(PostProcessingQuality.MEDIUM);
			break;
		case 3:
			mPostProcessingRenderer.setQuality(PostProcessingQuality.HIGH);
			break;				
		default:
			break;
		}		
	}


}