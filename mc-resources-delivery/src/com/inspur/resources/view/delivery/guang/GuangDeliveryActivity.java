package com.inspur.resources.view.delivery.guang;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.inspur.common.CacheHelper;
import com.inspur.common.HttpListener;
import com.inspur.common.RequestTool;
import com.inspur.easyresources.R;
import com.inspur.resources.base.BaseActivity;
import com.inspur.resources.utils.ApplicationValue;
import com.inspur.resources.utils.sq.DataBaseHelper;
import com.inspur.resources.view.delivery.transroute.RouteSubmitActivity;
import com.inspur.resources.view.delivery.transroute.ZSLConst;
import com.inspur.resources.view.delivery.transroute.bean.ErrorInfoBean;
import com.inspur.resources.view.delivery.transroute.bean.GuangBean;
import com.inspur.resources.view.delivery.transroute.bean.PhotoInfoBean;
import com.inspur.resources.view.delivery.transroute.bean.PointlikeResourceInfoBean;
import com.inspur.resources.view.delivery.transroute.bean.RouteInfoBean;
import com.yolanda.nohttp.OnUploadListener;
import com.yolanda.nohttp.Response;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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

public class GuangDeliveryActivity extends BaseActivity {

	private SQLiteDatabase db;
	private DataBaseHelper s;

	private ListView listview;
	private DeliveryRouteAdapter mAdapter;
	private List<GuangBean> deliveryRouteList = new ArrayList<GuangBean>();
	private ProgressDialog mProgress;

	private List<String> rList = new ArrayList<String>();

	private Handler handler = new Handler(new Handler.Callback() {

		public boolean handleMessage(Message msg) {
			if (mProgress != null) {
				mProgress.dismiss();
			}
			switch (msg.what) {
				case 0:
					Toast.makeText(GuangDeliveryActivity.this, "暂无离线数据!", Toast.LENGTH_SHORT).show();
					break;
				case 1:

					break;
				case 2:
					Toast.makeText(GuangDeliveryActivity.this, msg.obj.toString(), Toast.LENGTH_SHORT).show();
					break;
			}
			return true;
		}

	});

	@Override
	protected void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		setContentView(R.layout.activity_offline_delivery);
		s = new DataBaseHelper(GuangDeliveryActivity.this, "USER1.db", null, 1);
		db = s.getWritableDatabase();
		init();
		getData();
	}

	private void init() {

		listview = (ListView) findViewById(R.id.listView);
		mAdapter = new DeliveryRouteAdapter(GuangDeliveryActivity.this, deliveryRouteList);
		listview.setAdapter(mAdapter);
		listview.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				final GuangBean curRouteInfoBean = deliveryRouteList.get(position);
				Intent intent = new Intent(GuangDeliveryActivity.this,GuangSubmitActivity.class);
				intent.putExtra(RouteSubmitActivity.INTENT_DATA_FLAG, curRouteInfoBean);
				intent.putExtra("canEdit", false);
				startActivityForResult(intent,1);
				//		CacheHelper.getInstance(GuangDeliveryActivity.this).deleteObject(ZSLConst.PREFIX_OF_OFFLINE_ROUTE+curRouteInfoBean.getRouteID());

				/*String msg = "是否提交此数据？";
				AlertDialog ad;
				ad = new AlertDialog.Builder(OfflineDeliveryActivity.this).setTitle("温馨提示").setMessage(msg)
						.setPositiveButton("是", new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog, int which) {
								sunmitData(curRouteInfoBean);
							}
						}).setNegativeButton("否;", null).create();
				ad.show();*/
			}
		});
	}

	private void getData() {
		deliveryRouteList.clear();
		Cursor cursor = db.query("user1", null, null, null, null, null, null);
		while (cursor.moveToNext()) {
			rList.add(cursor.getString(cursor.getColumnIndex("json")));
			GuangBean a = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create()
					.fromJson(cursor.getString(cursor.getColumnIndex("json")), new TypeToken<GuangBean>() {}.getType());
			deliveryRouteList.add(a);
		}
		if(deliveryRouteList == null || deliveryRouteList.size() < 1){
			Toast.makeText(GuangDeliveryActivity.this, "暂无离线数据!", Toast.LENGTH_SHORT).show();
		}
		mAdapter.notifyDataSetChanged();

	}

	private void sunmitData(RouteInfoBean routeInfoBean){
		String uid = PreferencesUtils.getString(GuangDeliveryActivity.this, "UID", "");
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
		for (ErrorInfoBean error : routeInfoBean.getErrors()) {
			for (PhotoInfoBean photo : error.getFiles()) {
				file = new File(ZSLConst.photodir, photo.getPhotoName());
				if(file!=null){
					fileMap.put(photo.getPhotoName(), file);
				}
			}
		}
		RequestTool.uploadFileNoHttp(GuangDeliveryActivity.this, ApplicationValue.url+"taskUpload", paramMap, fileMap, new OnUploadListener() {

			@Override
			public void onProgress(int what, int progress) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onStart(int what) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onFinish(int what) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onError(int what, Exception exception) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onCancel(int what) {
				// TODO Auto-generated method stub

			}
		}, new HttpListener<String>() {

			@Override
			public void onSucceed(int what, Response<String> response) {
				System.out.println(response.get());
			}

			@Override
			public void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis) {
				// TODO Auto-generated method stub

			}
		});
	}

	class OfflineFilter implements FilenameFilter {

		public boolean accept(File dir, String name) {
			return name.startsWith(ZSLConst.PREFIX_OF_OFFLINE_ROUTE);
		}
	}

	public class DeliveryRouteAdapter extends BaseAdapter {
		private Context mContext;
		private List<GuangBean> mList;
		GuangBean routeInfoBean;
		private DeliveryRouteAdapter(Context context, List<GuangBean> list) {
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
				routeInfoBean = mList.get(n);

				if(routeInfoBean.getGjName()!=null){
					tag.name.setText(routeInfoBean.getGjName());
				}

				tag.iv_info.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						Intent intent = new Intent(GuangDeliveryActivity.this, RouteSubmitActivity.class);
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

	@Override
	protected void onActivityResult(int arg0, int arg1, Intent arg2) {
		// TODO Auto-generated method stub
		super.onActivityResult(arg0, arg1, arg2);

		if(arg1 == Activity.RESULT_OK){
			int pos = arg2.getExtras().getInt("postion");
			String[] args = {rList.get(pos)};
			db.delete("user1", "json=?", args);
			getData();
		}
	}

}
