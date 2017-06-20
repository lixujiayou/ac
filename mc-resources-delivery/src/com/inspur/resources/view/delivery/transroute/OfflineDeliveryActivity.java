package com.inspur.resources.view.delivery.transroute;

import java.io.File;
import java.io.FilenameFilter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.GsonBuilder;
import com.inspur.common.CacheHelper;
import com.inspur.common.HttpListener;
import com.inspur.common.RequestTool;
import com.inspur.easyresources.R;
import com.inspur.resources.base.BaseActivity;
import com.inspur.resources.utils.ApplicationValue;
import com.inspur.resources.view.delivery.transroute.bean.ErrorInfoBean;
import com.inspur.resources.view.delivery.transroute.bean.PhotoInfoBean;
import com.inspur.resources.view.delivery.transroute.bean.PointlikeResourceInfoBean;
import com.inspur.resources.view.delivery.transroute.bean.RouteInfoBean;
import com.yolanda.nohttp.OnUploadListener;
import com.yolanda.nohttp.Response;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import cn.trinea.android.common.util.PreferencesUtils;

public class OfflineDeliveryActivity extends BaseActivity {
	private ListView listview;
	private DeliveryRouteAdapter mAdapter;
	private List<RouteInfoBean> deliveryRouteList = new ArrayList<RouteInfoBean>();
	private ProgressDialog mProgress;

	private Handler handler = new Handler(new Handler.Callback() {

		public boolean handleMessage(Message msg) {
			if (mProgress != null) {
				mProgress.dismiss();
			}
			switch (msg.what) {
				case 0:
					Toast.makeText(OfflineDeliveryActivity.this, "暂无离线数据!", Toast.LENGTH_SHORT).show();
					break;
				case 1:
					deliveryRouteList.clear();
					List<RouteInfoBean> list = (List<RouteInfoBean>) msg.obj;
					deliveryRouteList.addAll(list);
					mAdapter.notifyDataSetChanged();
					break;
				case 2:
					Toast.makeText(OfflineDeliveryActivity.this, msg.obj.toString(), Toast.LENGTH_SHORT).show();
					break;
			}
			return true;
		}

	});

	@Override
	protected void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		setContentView(R.layout.activity_offline_delivery);
		init();
		getData();
	}

	private void init() {

		listview = (ListView) findViewById(R.id.listView);
		mAdapter = new DeliveryRouteAdapter(OfflineDeliveryActivity.this, deliveryRouteList);
		listview.setAdapter(mAdapter);
		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				final RouteInfoBean curRouteInfoBean = deliveryRouteList.get(position);
				Intent intent = new Intent(OfflineDeliveryActivity.this,RouteSubmitActivity.class);
				intent.putExtra(RouteSubmitActivity.INTENT_DATA_FLAG, curRouteInfoBean);
				startActivity(intent);
				CacheHelper.getInstance(OfflineDeliveryActivity.this).deleteObject(ZSLConst.PREFIX_OF_OFFLINE_ROUTE+curRouteInfoBean.getRouteID());

				/*String msg = "是否提交此数据？";
				AlertDialog ad;
				ad = new AlertDialog.Builder(OfflineDeliveryActivity.this).setTitle("温馨提示").setMessage(msg)
						.setPositiveButton("是", new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dWialog, int which) {
								sunmitData(curRouteInfoBean);
							}
						}).setNegativeButton("否", null).create();
				ad.show();*/
			}
		});
	}

	private void getData() {
		if (this.mProgress == null) {
			this.mProgress = ProgressDialog.show(this, "系统提示", "正在加载数据...");
		} else {
			this.mProgress.setMessage("正在加载数据...");
			this.mProgress.show();
		}
		new Thread() {
			@Override
			public void run() {
				Message msg = handler.obtainMessage();
				try {
					File dir = getFilesDir();
					File[] offlineFiles = dir.listFiles(new OfflineFilter());
					if (offlineFiles.length == 0) {
						handler.sendEmptyMessage(0);
					} else {
						List<RouteInfoBean> routeList = new ArrayList<RouteInfoBean>(offlineFiles.length);
						for (File file : offlineFiles) {
							routeList.add((RouteInfoBean) CacheHelper.getInstance(OfflineDeliveryActivity.this)
									.readObject(file.getName()));
						}
						msg.what = 1;
						msg.obj = routeList;
						handler.sendMessage(msg);
					}
				} catch (Exception e) {
					msg.what = 2;
					msg.obj = e.getMessage();
					handler.sendMessage(msg);
				}
			}
		}.start();
	}

	private void sunmitData(RouteInfoBean routeInfoBean){
		String uid = PreferencesUtils.getString(OfflineDeliveryActivity.this, "UID", "");
		String jsonRequest = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss Z").create().toJson(routeInfoBean);
		System.out.println(jsonRequest);
		Map<String,String> paramMap = new HashMap<String, String>();
		paramMap.put("uid", uid);
		paramMap.put("jsonRequest", jsonRequest);
		Map<String,File> fileMap = new HashMap<String, File>();
		File file = null;
		for (PhotoInfoBean photo : routeInfoBean.getStartPosition().getPhotos()) {
			file = new File(ZSLConst.photodir, photo.getPhotoName());
			if(file!=null){
				fileMap.put(photo.getPhotoName(), file);
			}
		}
		for (PhotoInfoBean photo : routeInfoBean.getEndPosition().getPhotos()) {
			file = new File(ZSLConst.photodir, photo.getPhotoName());
			if(file!=null){
				fileMap.put(photo.getPhotoName(), file);
			}
		}
		if (routeInfoBean.getLocusResourcePosition() != null) {
			for (PointlikeResourceInfoBean pointlikeResourceInfoBean : routeInfoBean.getLocusResourcePosition()) {
				if (pointlikeResourceInfoBean == null || pointlikeResourceInfoBean.getPhotos() == null
						|| pointlikeResourceInfoBean.getPhotos().size() == 0) {
					break;
				}
				for (PhotoInfoBean photo : pointlikeResourceInfoBean.getPhotos()) {
					file = new File(ZSLConst.photodir, photo.getPhotoName());
					if (file != null) {
						fileMap.put(photo.getPhotoName(), file);
					}
				}
			}
		}


	}

	class OfflineFilter implements FilenameFilter {

		public boolean accept(File dir, String name) {
			return name.startsWith(ZSLConst.PREFIX_OF_OFFLINE_ROUTE);
		}
	}

	public class DeliveryRouteAdapter extends BaseAdapter {
		private Context mContext;
		private List<RouteInfoBean> mList;

		private DeliveryRouteAdapter(Context context, List<RouteInfoBean> list) {
			super();
			this.mContext = context;
			this.mList = list;
		}

		public int getCount() {
			return mList.size();
		}

		public Object getItem(final int n) {
			return mList.get(n);
		}

		public long getItemId(final int n) {
			return n;
		}

		@SuppressLint({ "NewApi" })
		public View getView(final int n, View inflate, final ViewGroup viewGroup) {
			ViewHolder tag;
			if (inflate == null) {
				inflate = LayoutInflater.from(mContext).inflate(R.layout.deliveryroute_list_item, null);
				tag = new ViewHolder();
				tag.name = (TextView) inflate.findViewById(R.id.name);
				tag.state = (TextView) inflate.findViewById(R.id.state);
				tag.time = (TextView) inflate.findViewById(R.id.time);
				tag.iv_info = (ImageView) inflate.findViewById(R.id.iv_info);
				inflate.setTag((Object) tag);
			} else {
				tag = (ViewHolder) inflate.getTag();
			}
			if (mList.size() > 0) {

				final RouteInfoBean routeInfoBean = mList.get(n);
				if (routeInfoBean.getStartPosition() != null && routeInfoBean.getEndPosition() != null) {
					tag.name.setText(routeInfoBean.getStartPosition().getResourceName() + "-"
							+ routeInfoBean.getEndPosition().getResourceName());
				}

				if (routeInfoBean.getDeliveryState() != null) {
					tag.state.setText(
							getResources().getStringArray(R.array.jiaogezhuangtai)[routeInfoBean.getDeliveryState()]);
				}

				if(routeInfoBean.getDeliveryDate() != null){
					SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
					//java.util.Date date=new java.util.Date();  
					String str=sdf.format(routeInfoBean.getDeliveryDate());
					tag.time.setText(str);
				}




				tag.iv_info.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						Intent intent = new Intent(OfflineDeliveryActivity.this, RouteSubmitActivity.class);
						intent.putExtra(RouteSubmitActivity.INTENT_DATA_FLAG, routeInfoBean);
						intent.putExtra("canEdit", false);
						startActivity(intent);
					}
				});
			}
			return inflate;
		}

		public class ViewHolder {
			public TextView name;
			public TextView state;
			public TextView time;
			public ImageView iv_info;
		}
	}

}
