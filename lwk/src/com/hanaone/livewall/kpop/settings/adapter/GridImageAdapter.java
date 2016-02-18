package com.hanaone.livewall.kpop.settings.adapter;

import java.util.List;

import com.hanaone.livewall.kpop.R;
import com.hanaone.livewall.kpop.settings.dataset.FilterData;
import com.hanaone.livewall.kpop.settings.dataset.ImageData;
import com.hanaone.livewall.kpop.settings.util.ImageUtils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class GridImageAdapter extends BaseAdapter {
	private Context mContext;
	private LayoutInflater mInflater;
	private List<ImageData> mDataSet;
	private ImageListener mListener;
	public GridImageAdapter(Context mContext, ImageListener mListener) {
		super();
		this.mContext = mContext;
		this.mInflater = LayoutInflater.from(mContext);
		this.mListener = mListener;
	}

	
	public void setmDataSet(List<ImageData> mDataSet) {
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
			convertView = mInflater.inflate(R.layout.layout_grid_image, parent, false);
			
			holder = new ViewHolder();
			holder.image = (ImageView) convertView.findViewById(R.id.img_grid_image);
			holder.name = (TextView) convertView.findViewById(R.id.txt_grid_image);
			
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		ImageData data = this.mDataSet.get(position);
		
		holder.image.setImageBitmap(ImageUtils.decodeSampledBitmapFromResource(mContext.getResources(), data.getId(), 200, 200));
		holder.name.setText(data.getName());
		
		final int mPosition = position;
		holder.image.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				mListener.onSelect(mPosition);				
			}
		});
		return convertView;
	}

	
	private class ViewHolder{
		ImageView image;
		TextView name;
	}



}
