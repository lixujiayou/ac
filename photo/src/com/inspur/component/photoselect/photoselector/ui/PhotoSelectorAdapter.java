package com.inspur.component.photoselect.photoselector.ui;

import java.util.ArrayList;

import com.inspur.component.photoselect.R;
import com.inspur.component.photoselect.photoselector.model.PhotoModel;
import com.inspur.component.photoselect.photoselector.ui.PhotoItem.onItemClickListener;
import com.inspur.component.photoselect.photoselector.ui.PhotoItem.onPhotoItemCheckedListener;
import com.inspur.component.photoselect.photoselector.util.CommonUtils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView.LayoutParams;
import android.widget.TextView;

/**
 * 
 * @author Aizaz AZ
 *
 */


public class PhotoSelectorAdapter extends MBaseAdapter<PhotoModel> {
	private int itemWidth;
	private int horizentalNum = 3;
	private onPhotoItemCheckedListener listener;
	private LayoutParams itemLayoutParams;
	private onItemClickListener mCallback;
	private OnClickListener cameraListener;

	private PhotoSelectorAdapter(Context context, ArrayList<PhotoModel> models,boolean isShowCamera) {
		super(context, models);
		this.isShowCamera=isShowCamera;
	}



	public PhotoSelectorAdapter(Context context, ArrayList<PhotoModel> models, int screenWidth, onPhotoItemCheckedListener listener, onItemClickListener mCallback,
								OnClickListener cameraListener,boolean isShowCamera) {
		this(context, models,isShowCamera);
		setItemWidth(screenWidth);
		this.listener = listener;
		this.mCallback = mCallback;
		this.cameraListener = cameraListener;
		this.isShowCamera=isShowCamera;
	}

	/** 设置每一个Item的宽高 */
	public void setItemWidth(int screenWidth) {
		int horizentalSpace = context.getResources().getDimensionPixelSize(R.dimen.sticky_item_horizontalSpacing);
		this.itemWidth = (screenWidth - (horizentalSpace * (horizentalNum - 1))) / horizentalNum;
		this.itemLayoutParams = new LayoutParams(itemWidth, itemWidth);
	}

	@Override
	public void notifyDataSetChanged() {
		super.notifyDataSetChanged();
		TextView textView= (TextView) ((Activity)context).findViewById(R.id.tv_album_ar);
		boolean isRentPhoto=false;
		String textValue= textView.getText().toString();
		isRentPhoto=textValue.equals(context.getResources().getString(R.string.recent_photos));
		if (isShowCamera&&isRentPhoto){

			PhotoModel cameraModule=new PhotoModel();
			cameraModule.setOriginalPath("cameraModule");
			models.add(0,cameraModule);
		}
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		PhotoItem item = null;
		if (convertView == null || !(convertView instanceof PhotoItem)) {
			item = new PhotoItem(context, listener);
			item.setLayoutParams(itemLayoutParams);
			convertView = item;
		} else {
			item = (PhotoItem) convertView;
		}
		TextView textView= (TextView) ((Activity)context).findViewById(R.id.tv_album_ar);
		boolean isRentPhoto=false;
				String textValue= textView.getText().toString();
		isRentPhoto=textValue.equals(context.getResources().getString(R.string.recent_photos));
		if (isShowCamera&&isRentPhoto&&position==0){
			item.initSetting(isShowCamera);

			item.setCameraPhoto(R.drawable.camerashow);
			item.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

					CommonUtils.launchActivityForResult((Activity)context, intent, 1);
				}
			});
//			item.setSelected(models.get(position).isChecked());
//			item.setOnClickListener(mCallback, position);
		}else {
			item.initSetting(false);

			item.setImageDrawable(models.get(position));
			item.setSelected(models.get(position).isChecked());
			item.setOnClickListener(mCallback, position);
		}

		return convertView;
	}
}
