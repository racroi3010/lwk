package com.hanaone.livewall.kpop.settings;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import pe.phantasia.Amaro;
import pe.phantasia.EarlyBird;
import pe.phantasia.HanFilter;
import pe.phantasia.LomoFi;
import uk.co.senab.photoview.PhotoViewAttacher;

import com.devsmart.android.ui.HorizontalListView;
import com.hanaone.livewall.kpop.R;

import com.hanaone.ip.Constants;
import com.hanaone.ip.ImageFilter;
import com.hanaone.livewall.kpop.settings.adapter.GridImageAdapter;
import com.hanaone.livewall.kpop.settings.adapter.ImageListener;
import com.hanaone.livewall.kpop.settings.adapter.ListImageAdapter;
import com.hanaone.livewall.kpop.settings.adapter.ListImageListener;
import com.hanaone.livewall.kpop.settings.dataset.FilterData;
import com.hanaone.livewall.kpop.settings.dataset.ImageData;
import com.hanaone.livewall.kpop.settings.util.ImageUtils;
import com.hanaone.livewall.kpop.settings.util.MySharedPreference;
import com.jabistudio.androidjhlabs.filter.BlockFilter;
import com.jabistudio.androidjhlabs.filter.CellularFilter;
import com.jabistudio.androidjhlabs.filter.ChannelMixFilter;
import com.jabistudio.androidjhlabs.filter.CircleFilter;
import com.jabistudio.androidjhlabs.filter.ColorHalftoneFilter;
import com.jabistudio.androidjhlabs.filter.ContourFilter;
import com.jabistudio.androidjhlabs.filter.ContrastFilter;
import com.jabistudio.androidjhlabs.filter.CrystallizeFilter;
import com.jabistudio.androidjhlabs.filter.Curve;
import com.jabistudio.androidjhlabs.filter.CurvesFilter;
import com.jabistudio.androidjhlabs.filter.DiffuseFilter;
import com.jabistudio.androidjhlabs.filter.DiffusionFilter;
import com.jabistudio.androidjhlabs.filter.DisplaceFilter;
import com.jabistudio.androidjhlabs.filter.DoGFilter;
import com.jabistudio.androidjhlabs.filter.EmbossFilter;
import com.jabistudio.androidjhlabs.filter.ExposureFilter;
import com.jabistudio.androidjhlabs.filter.FieldWarpFilter;
import com.jabistudio.androidjhlabs.filter.GainFilter;
import com.jabistudio.androidjhlabs.filter.GammaFilter;
import com.jabistudio.androidjhlabs.filter.GlowFilter;
import com.jabistudio.androidjhlabs.filter.HSBAdjustFilter;
import com.jabistudio.androidjhlabs.filter.HalftoneFilter;
import com.jabistudio.androidjhlabs.filter.KaleidoscopeFilter;
import com.jabistudio.androidjhlabs.filter.MarbleFilter;
import com.jabistudio.androidjhlabs.filter.MotionBlurFilter;
import com.jabistudio.androidjhlabs.filter.OilFilter;
import com.jabistudio.androidjhlabs.filter.OpacityFilter;
import com.jabistudio.androidjhlabs.filter.PerspectiveFilter;
import com.jabistudio.androidjhlabs.filter.PinchFilter;
import com.jabistudio.androidjhlabs.filter.ShearFilter;
import com.jabistudio.androidjhlabs.filter.SolarizeFilter;
import com.jabistudio.androidjhlabs.filter.SphereFilter;
import com.jabistudio.androidjhlabs.filter.StampFilter;
import com.jabistudio.androidjhlabs.filter.SwimFilter;
import com.jabistudio.androidjhlabs.filter.TritoneFilter;
import com.jabistudio.androidjhlabs.filter.WaterFilter;
import com.jabistudio.androidjhlabs.filter.WeaveFilter;
import com.jabistudio.androidjhlabs.filter.util.AndroidUtils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap.Config;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ImageView.ScaleType;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;

public class ImageProcessingActivity extends PreferenceActivity {
//	private Bitmap img;
//	private Bitmap imgThumbnail;
	private Context mContext;
	private ImageView imgView;
//	private SolarizeFilter mSolarizeFilter;
//	private Amaro mAmaro;
//	private EarlyBird mEarlyBird;
//	private LomoFi mLomoFi;
//	private HanFilter mHanFilter;
	
	private List<ImageData> dataSet;
	private List<FilterData> filterDataSet;
	private Dialog dialog;
	
	private ListImageAdapter filterAdapter;
	
	private LinearLayout layoutIp;
	private LinearLayout layoutEffect;
	private MySharedPreference mPreference;
	
	private Spinner spQuality;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);	
		setContentView(R.layout.activity_image_processing);				
		
		mContext = getApplicationContext();
		
		mPreference = new MySharedPreference(mContext);
		
		layoutIp = (LinearLayout) findViewById(R.id.layout_ip);
		layoutEffect = (LinearLayout) findViewById(R.id.layout_effect);
		
		spQuality = (Spinner) findViewById(R.id.sp_quality);
		spQuality.setSelection(mPreference.getIntPreference(MySharedPreference.KEY_QUALITY));
		spQuality.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				if(mPreference.getIntPreference(MySharedPreference.KEY_QUALITY) != position){
					mPreference.setBooleanPreference(MySharedPreference.KEY_CHANGE_QUALITY, true);
					mPreference.setIntPreference(MySharedPreference.KEY_QUALITY, position);					
				}

			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
				
			}
			
		});
		
		
		
		imgView = (ImageView) findViewById(R.id.img_view);	
		
		// copy original image
		for(int i = 0; i < Constants.IMAGE_DATA.length; i ++){
			File file = new File(Constants.PATH + Constants.IMAGE_DATA[i]);
			if(!file.exists()){
				try {
					InputStream is = getAssets().open(Constants.IMAGE_DATA[i]);
					FileOutputStream os = new FileOutputStream(file);
					byte[] buf = new byte[2014];
					int read = 0;
					while((read = is.read(buf)) > 0){
						os.write(buf, 0, read);
					}
					os.close();
					is.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		}
		
		
//		img = BitmapFactory.decodeResource(getResources(), R.drawable.iu);
//		imgView.setImageBitmap(img);
		
//		imgThumbnail = ImageUtils.decodeSampledBitmapFromResource(getResources(), R.drawable.iu, 50, 50);
		//
		
		
		// add view
		filterDataSet = new ArrayList<FilterData>();
		
		HorizontalListView listView = (HorizontalListView) findViewById(R.id.lv_preview);
		filterAdapter = new ListImageAdapter(getBaseContext(), mListener);
		filterAdapter.setmDataSet(filterDataSet);
		listView.setAdapter(filterAdapter);
		
		
		listView.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				
				mListener.onSelect(filterDataSet.get(position));
				if(view instanceof RelativeLayout){
					filterAdapter.onChangeOverLay((ImageView)((RelativeLayout)view).getChildAt(1));
				}
				
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
				
			}
			
		});
		
		InitThumbnail(Constants.PATH + Constants.IMAGE_DATA[0]);
		imgView.setImageBitmap(BitmapFactory.decodeFile(Constants.PATH + Constants.IMAGE_DATA[0]));
		
		PhotoViewAttacher mAttacher = new PhotoViewAttacher(imgView);
        mAttacher.setScaleType(ScaleType.FIT_XY);		
	}

	public void onClick(View v){
		switch (v.getId()) {
		case R.id.btn_star_collection:			
			dialog = new Dialog(getApplicationContext());
			dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
			dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
			dialog.setContentView(R.layout.dialog_image_collection);
			WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
			lp.copyFrom(dialog.getWindow().getAttributes());
			lp.width = WindowManager.LayoutParams.MATCH_PARENT;
			lp.height = WindowManager.LayoutParams.MATCH_PARENT;
			dialog.getWindow().setAttributes(lp);			
			
			
			
			GridView gridView = (GridView) dialog.findViewById(R.id.grid_view_image_collection);
			GridImageAdapter adapter = new GridImageAdapter(mContext, mGridListener);
			
			dataSet = new ArrayList<ImageData>();
			ImageData data = new ImageData();
			data.setId(R.drawable.iu);
			data.setName("IU Star Kpop");
			dataSet.add(data);

			data = new ImageData();
			data.setId(R.drawable.kpop_star_1);
			data.setName("Star 1 Kpop");
			dataSet.add(data);

			data = new ImageData();
			data.setId(R.drawable.kpop_star_2);
			data.setName("Star 2 Kpop");
			dataSet.add(data);
			
			adapter.setmDataSet(dataSet);
			gridView.setAdapter(adapter);
			

			dialog.show();
			break;
		case R.id.btn_effect:
			layoutIp.setVisibility(LinearLayout.GONE);
			layoutEffect.setVisibility(LinearLayout.VISIBLE);
			break;
		case R.id.btn_effect_ok:
			layoutIp.setVisibility(LinearLayout.VISIBLE);
			layoutEffect.setVisibility(LinearLayout.GONE);			
		default:
			break;
		}
	}
	
	
	@Override
	protected void onPause() {
		if(dialog != null){
			dialog.dismiss();
		}
		if(mLoadingDialog != null){
			mLoadingDialog.dismiss();
		}
		super.onPause();
	}


	private ImageListener mGridListener = new ImageListener() {
		
		@Override
		public void onSelect(int position) {
//			ImageData data = dataSet.get(position);
//			img = BitmapFactory.decodeResource(getResources(), data.getId());
//			imgView.setImageBitmap(img);
//			imgThumbnail = ImageUtils.decodeSampledBitmapFromResource(getResources(), data.getId(), 50, 50);
//			
//			
//			InitThumbnail("");
//			dialog.dismiss();
		}
	};
	
	private ListImageListener mListener = new ListImageListener() {
		
		@Override
		public void onSelect(FilterData data) {
			new ImageProcessing().execute(data);
		}
	};
	
	
	private void InitThumbnail(String orgImagePath){
		
		filterDataSet.clear();			
//		int[] src = AndroidUtils.bitmapToIntArray(imgThumbnail);
//		int width = imgThumbnail.getWidth();
//		int height = imgThumbnail.getHeight();
		
		
//		mSolarizeFilter = new SolarizeFilter();
//		int[] dest = mSolarizeFilter.filter(src, width, height);
//		Bitmap desBitmap = Bitmap.createBitmap(dest, width, height, Config.ARGB_8888);
//		FilterData data = new FilterData();
//		data.setType(0);
//		data.setBitmap(desBitmap);		
//		filterDataSet.add(data);
//		
//		
//		mAmaro = new Amaro();
//		desBitmap = mAmaro.transform(imgThumbnail);
//		data = new FilterData();
//		data.setType(1);
//		data.setBitmap(desBitmap);		
//		filterDataSet.add(data);
//		
//		mEarlyBird = new EarlyBird();
//		desBitmap = mEarlyBird.transform(imgThumbnail, getResources());
//		data = new FilterData();
//		data.setType(2);
//		data.setBitmap(desBitmap);		
//		filterDataSet.add(data);
//		
//		mLomoFi = new LomoFi();
//		desBitmap = mLomoFi.transform(imgThumbnail);
//		data = new FilterData();
//		data.setType(3);
//		data.setBitmap(desBitmap);		
//		filterDataSet.add(data);		
//		
//		
//		// Han Filter
//		for(int i = 1; i < 36; i ++){
//			mHanFilter = new HanFilter();
//			mHanFilter.setFilterType(i);
//			desBitmap = mHanFilter.transform(imgThumbnail);
//			data = new FilterData();
//			data.setType(4);
//			data.setHanType(i);
//			data.setBitmap(desBitmap);		
//			filterDataSet.add(data);				
//		}	
		
		Bitmap thumImg = ImageUtils.decodeSampledBitmapFromFile(orgImagePath, 50, 50);
		String input = Constants.PATH+ "thum.jpg";
		try {
			FileOutputStream os = new FileOutputStream(new File(input));
			thumImg.compress(CompressFormat.JPEG, 100, os);
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String output = Constants.PATH + "thum_amaro.jpg";
		
		Amaro mAmaro = new Amaro();
		mAmaro.transform(input, output);
		Bitmap desBitmap = BitmapFactory.decodeFile(output);
		
		FilterData data = new FilterData();
		data.setType(0);
		data.setBitmap(desBitmap);
		filterDataSet.add(data);		
		filterAdapter.setmDataSet(filterDataSet);
	}
	
	private class ImageProcessing extends AsyncTask<FilterData, String, String>{

		@Override
		protected void onPostExecute(String result) {
			mPreference.setBooleanPreference(MySharedPreference.KEY_CHANGE_IMAGE_PATH, true);
			mPreference.setStringPreference(MySharedPreference.KEY_IMAGE_PATH, result);
			
			Bitmap desBitmap = BitmapFactory.decodeFile(result);
			imgView.setImageBitmap(desBitmap);	
			imgView.invalidate();
			hideLoadingDialog();
			super.onPostExecute(result);
		}

		@Override
		protected void onPreExecute() {
			showLoadingDialog();
			super.onPreExecute();
		}

		@Override
		protected String doInBackground(FilterData... params) {
			FilterData data = params[0];

			SharedPreferences mPrefence = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
			Editor editor = mPrefence.edit();	
			String output = Constants.PATH + "img_out.jpg";
			String input = Constants.PATH + Constants.IMAGE_DATA[0];
			switch (data.getType()) {
			case 0:
				Amaro amaroFilter = new Amaro();
//				amaroFilter.transform(input, output);
				Bitmap bmp = BitmapFactory.decodeFile(input);
				Bitmap desBitmap = amaroFilter.transform(bmp);	
				

				editor.putBoolean("change", true);
								

				try {
					FileOutputStream os = new FileOutputStream(new File(output));
					desBitmap.compress(CompressFormat.JPEG, 100, os);				

					editor.putString("path", output);
					editor.commit();
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}	
				break;

			default:
				break;
			}
			
			return output;
//			String path = Environment.getExternalStorageDirectory().getPath() + "/KPOP/";
//			File dir = new File(path);
//			if(!dir.exists()){
//				dir.mkdirs();
//			}			
//			String filePath = path + "img_out.jpg";
//			
//			int[] src = AndroidUtils.bitmapToIntArray(img);
//			int width = img.getWidth();
//			int height = img.getHeight();
//			int[] dest = mSolarizeFilter.filter(src, width, height);		
//			
//			
//			switch (data.getType()) {
//			case 0:
//
//				Bitmap desBitmap = Bitmap.createBitmap(dest, width, height, Config.ARGB_8888);	
//
//
//				editor.putBoolean("change", true);
//								
//
//				
//				
//				try {
//					FileOutputStream os = new FileOutputStream(new File(filePath));
//					desBitmap.compress(CompressFormat.JPEG, 100, os);
//					
//
//					editor.putString("path", filePath);
//					editor.commit();
//				} catch (FileNotFoundException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}											
//				
//				break;
//			case 1:
//
//				desBitmap = mAmaro.transform(img);	
//				
//
//				editor.putBoolean("change", true);
//								
//
//				try {
//					FileOutputStream os = new FileOutputStream(new File(filePath));
//					desBitmap.compress(CompressFormat.JPEG, 100, os);				
//
//					editor.putString("path", filePath);
//					editor.commit();
//				} catch (FileNotFoundException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}											
//				
//				break;
//			case 2:
//
//				desBitmap = mEarlyBird.transform(img, getResources());	
//
//
//				editor.putBoolean("change", true);
//								
//
//				try {
//					FileOutputStream os = new FileOutputStream(new File(filePath));
//					desBitmap.compress(CompressFormat.JPEG, 100, os);				
//
//					editor.putString("path", filePath);
//					editor.commit();
//				} catch (FileNotFoundException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}											
//				
//				break;
//			case 3:
//
//				
//				desBitmap = mLomoFi.transform(img);	
//				
//
//				editor.putBoolean("change", true);
//								
//
//				try {
//					FileOutputStream os = new FileOutputStream(new File(filePath));
//					desBitmap.compress(CompressFormat.JPEG, 100, os);				
//
//					editor.putString("path", filePath);
//					editor.commit();
//				} catch (FileNotFoundException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}											
//				
//				break;	
//			case 4:
//
//				mHanFilter.setFilterType(data.getHanType());
//				
//				desBitmap = mHanFilter.transform(img);	
//				
//
//				editor.putBoolean("change", true);
//								
//
//				try {
//					FileOutputStream os = new FileOutputStream(new File(filePath));
//					desBitmap.compress(CompressFormat.JPEG, 100, os);				
//
//					editor.putString("path", filePath);
//					editor.commit();
//				} catch (FileNotFoundException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}											
//				
//				break;				
//			default:
//				break;
//			}
					
//			return filePath;
		}
		
	}
	private Dialog mLoadingDialog = null;

	private void showLoadingDialog() {
		if (!this.isFinishing()) {
			if (this.mLoadingDialog == null) {

				ImageView imgDialog = new ImageView(mContext);
				imgDialog.setBackgroundResource(R.drawable.ic_loading_n1);
				Animation tween = AnimationUtils.loadAnimation(mContext, R.anim.anim_custom_progress_dialog);
				imgDialog.startAnimation(tween);

				mLoadingDialog = new Dialog(mContext);
				mLoadingDialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
				mLoadingDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
				mLoadingDialog.setContentView(imgDialog);
				mLoadingDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
				mLoadingDialog.setCancelable(false);
			}

			this.mLoadingDialog.show();
		}
	}
		
	private void hideLoadingDialog() {
		if (this.mLoadingDialog != null) {
			this.mLoadingDialog.dismiss();
			this.mLoadingDialog = null;
		}
	}	

}
