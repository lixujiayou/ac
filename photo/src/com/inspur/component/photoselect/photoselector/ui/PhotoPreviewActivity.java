package com.inspur.component.photoselect.photoselector.ui;
/**
 * 
 * @author Aizaz AZ
 *
 */

import java.util.List;

import com.inspur.component.photoselect.CommonExtras;
import com.inspur.component.photoselect.photoselector.domain.PhotoSelectorDomain;
import com.inspur.component.photoselect.photoselector.model.PhotoModel;
import com.inspur.component.photoselect.photoselector.ui.PhotoSelectorActivity.OnLocalReccentListener;
import com.inspur.component.photoselect.photoselector.util.CommonUtils;

import android.os.Bundle;

public class PhotoPreviewActivity extends BasePhotoPreviewActivity implements OnLocalReccentListener {

	private PhotoSelectorDomain photoSelectorDomain;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		photoSelectorDomain = new PhotoSelectorDomain(getApplicationContext());

		init(getIntent().getExtras());
	}

	@SuppressWarnings("unchecked")
	protected void init(Bundle extras) {
		if (extras == null)
			return;

		if (extras.containsKey(CommonExtras.photos)) { // 预览图片
			photos = (List<PhotoModel>) extras.getSerializable(CommonExtras.photos);
			current = extras.getInt("position", 0);
			updatePercent();
			bindData();
		} else if (extras.containsKey("album")) { // 点击图片查看
			String albumName = extras.getString("album"); // 相册
			this.current = extras.getInt("position");
			if (!CommonUtils.isNull(albumName) && albumName.equals(PhotoSelectorActivity.RECCENT_PHOTO)) {
				photoSelectorDomain.getReccent(this);
			} else {
				photoSelectorDomain.getAlbum(albumName, this);
			}
		}
	}

	@Override
	public void onPhotoLoaded(List<PhotoModel> photos) {
		this.photos = photos;
		updatePercent();
		bindData(); // 更新界面
	}

}
