package com.hanaone.livewall.kpop.settings.adapter;

import java.util.List;

import com.hanaone.livewall.kpop.R;
import com.hanaone.livewall.kpop.settings.dataset.FilterData;
import com.hanaone.livewall.kpop.settings.util.ImageUtils;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

public class ListImageAdapter extends BaseAdapter {
	private Context mContext;
	private LayoutInflater mInflater;
	private List<FilterData> mDataSet;
	private ListImageListener mListener;
	private ImageView currentSelectedItem = null;
	public ListImageAdapter(Context mContext, ListImageListener mListener) {
		super();
		this.mContext = mContext;
		this.mInflater = LayoutInflater.from(mContext);
		this.mListener = mListener;
	}

	
	public void setmDataSet(List<FilterData> mDataSet) {
		this.mDataSet = mDataSet;
		this.notifyDataSetChanged();
	}


	@Override
	public int getCount() {
		if(this.mDataSet != null) return this.mDataSet.size();
		return 0;
	}

	@Override
	public Object getItem(int position) {
		if(this.mDataSet != null 
				&& this.mDataSet.size() > position) 
			return this.mDataSet.get(position);
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final ViewHolder holder;
		if(convertView == null){
			convertView = mInflater.inflate(R.layout.layout_list_image, parent, false);
			
			holder = new ViewHolder();
			holder.imgView = (ImageView) convertView.findViewById(R.id.img_list_image);
			//holder.imgOverlay = (ImageView) convertView.findViewById(R.id.img_list_overlay);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		final FilterData data = mDataSet.get(position);
		
		holder.imgView.setImageBitmap(data.getBitmap());
		
//		holder.imgView.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				if(currentSelectedItem != null){
//					currentSelectedItem.imgOverlay.setBackgroundResource(R.drawable.image_overlay_n);
//				}							
//				currentSelectedItem = holder;
//				holder.imgOverlay.setBackgroundResource(R.drawable.image_overlay_p);
//				mListener.onSelect(data);
//			}
//		});
		return convertView;
	}

	public void onChangeOverLay(ImageView cur){
		if(currentSelectedItem != null){
			currentSelectedItem.setBackgroundResource(R.drawable.image_overlay_n);
		}							
		currentSelectedItem = cur;		
		currentSelectedItem.setBackgroundResource(R.drawable.image_overlay_p);
	}
	private class ViewHolder{
		ImageView imgView;
		//ImageView imgOverlay;
	}
}
