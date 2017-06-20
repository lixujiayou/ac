package com.inspur.resources.view.delivery.offline;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;
import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

import com.baidu.location.BDLocation;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.inspur.StringUtils;
import com.inspur.common.CallServer;
import com.inspur.common.HttpListener;
import com.inspur.common.RequestTool;
import com.inspur.easyresources.R;
import com.inspur.resources.base.BaseActivity;
import com.inspur.resources.http.httpconnect;
import com.inspur.resources.utils.ApplicationValue;
import com.inspur.resources.utils.ImageUtils;
import com.inspur.resources.utils.PictureUtil;
import com.inspur.resources.utils.sq.DataBaseHelper;
import com.inspur.resources.utils.sq.MySQLHelper;
import com.inspur.resources.view.delivery.guang.GuangSubmitActivity;
import com.inspur.resources.view.delivery.guang.bean.GuangPhotoInfoBean;
import com.inspur.resources.view.delivery.guang.bean.PhotoBean;
import com.inspur.resources.view.delivery.transroute.MyDeliveryRouteListActivity;
import com.inspur.resources.view.delivery.transroute.RouteSubmitActivity;
import com.inspur.resources.view.delivery.transroute.ZSLConst;
import com.inspur.resources.view.delivery.transroute.bean.PhotoInfoBean;
import com.inspur.resources.view.delivery.transroute.bean.RouteInfoBean;
import com.squareup.picasso.Picasso;
import com.yolanda.nohttp.FileBinary;
import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.OnUploadListener;
import com.yolanda.nohttp.Request;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.Response;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.Toast;
import cn.trinea.android.common.util.FileUtils;
import cn.trinea.android.common.util.PreferencesUtils;

public class PhotoOfficeActivity extends BaseActivity {

	private final Context context = this;

	// views-----------------------------
	private GridView mGridView;
	private GridViewAdapter mGridViewAdapter;
	// ----------------------------------
	// 接收的参数
	private String mPhotoType;// 照片类型（起始点照片、终止点照片、中途照片、隐患照片）
	//private BDLocation lastLocation;// 照片水印（拍摄新照片时需打上）
	//private ArrayList<PhotoInfoBean> mPhotoInfoBeanList = new ArrayList<PhotoInfoBean>();// 照片列表

	// 照片缓存
	private Map<Integer, SoftReference<Bitmap>> cache = new HashMap<Integer, SoftReference<Bitmap>>();

//	private BDLocation mLastLocation;

	private final static int REQUESTCODE_SHOOT_PHOTO = 1;
	private int routeId;
	private String  relatedId;
	private String resourceType;
	//标识是否拍摄了照片
	private boolean photoed ;
	private ProgressDialog mProgress;
	private String requestStr;

	private String mImgUrl = ApplicationValue.url+"";
	private String deleteImgUrl = "deletePhotoByPath";//删除图片接口
	private double mLon,mLat;


	private SQLiteDatabase photoDateBase;
	private DataBaseHelper photoHelper;
	private boolean isHavePhoto  = false;//是否已拍照

	private List<File> photoList = new ArrayList<File>();


	private Handler handler = new Handler(new Handler.Callback() {

		public boolean handleMessage(Message msg) {
			if (mProgress != null) {
				mProgress.dismiss();
			}
			switch (msg.what) {
				case 1:
					photoed = true;
					break;
				//添加单张图片成功
				case 2:
					mGridViewAdapter.notifyDataSetChanged();
					photoed = true;
					break;
				case -1:
					Toast.makeText(PhotoOfficeActivity.this, msg.obj.toString(), Toast.LENGTH_SHORT).show();
					break;
				//删除成功
				case 3:
					Toast.makeText(PhotoOfficeActivity.this, msg.obj.toString(), Toast.LENGTH_SHORT).show();
					mGridViewAdapter.notifyDataSetChanged();
					break;
				//查询图片成功
				case 4:
				/*mPhotoInfoBeanList.clear();
				List<PhotoInfoBean> list = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss Z").create()
						.fromJson(msg.obj.toString(), new TypeToken<List<PhotoInfoBean>>() {
				}.getType());

				if(list!=null && list.size()!=0){
					photoed = true;
				}

				mPhotoInfoBeanList.addAll(list);
				mGridViewAdapter.notifyDataSetChanged();*/
					break;
			}
			return true;
		}
	});

	@SuppressLint("NewApi") @Override
	protected void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		EventBus.getDefault().register(this);
		setContentView(R.layout.zsl_activity_photo_shope);
		setTitle("拍照");
		// 接收参数
		Intent intent = getIntent();

		mPhotoType = intent.getStringExtra("mPhotoType");
		routeId = intent.getIntExtra("routeId", 0);
		relatedId = intent.getExtras().getString("relatedId", "");
		resourceType = intent.getExtras().getString("resourceType",null);
		mLon = intent.getExtras().getDouble("lon",0);
		mLat = intent.getExtras().getDouble("lat",1);
		Log.d("lixu", "打水印9RelatedID==="+relatedId);

		initViews();
		photoSelect(null);
		//	getData();
	}

	private void initViews() {
		mGridView = (GridView) findViewById(R.id.photo_GridView);
		mGridViewAdapter = new GridViewAdapter();
		mGridView.setAdapter(mGridViewAdapter);
		mGridView.setOnItemLongClickListener(new OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
										   final int position, long id) {

				AlertDialog ad = new AlertDialog.Builder(PhotoOfficeActivity.this).setTitle("温馨提示").setMessage("是否删除此照片？")
						.setPositiveButton("是", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								deleteImg(position);
								dialog.dismiss();
							}
						}).setNegativeButton("否", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								dialog.dismiss();

							}
						}).create();
				ad.show();
				return true;
			}
		});
	}
	/**
	 * 初始化图片
	 */
	private void getData(){
		Log.d("lixu", "relatedId == " + relatedId);
		if (this.mProgress == null) {
			this.mProgress = ProgressDialog.show(this, "系统提示", "正在加载数据...");
			this.mProgress.setCancelable(true);
		} else {
			this.mProgress.setMessage("正在加载数据...");
			this.mProgress.show();
		}

		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub


				final ArrayList<NameValuePair> list = new ArrayList<NameValuePair>();

				PhotoBean bean = new PhotoBean();
				if(routeId != 0){
					bean.setRouteId(routeId+"");
				}
				if(relatedId!=null || !relatedId.isEmpty()){
					bean.setRelatedID(relatedId);
				}

				if(mPhotoType.equals(ZSLConst.PHOTO_TYPE_GJ)){
					bean.setRouteId("");
				}
				bean.setPhotoType(mPhotoType);
				Gson json = new Gson();




				//	String json = "{\"routeID\":"+routeId+",\"relatedID\":"+relatedId+"}";


				list.add((NameValuePair)new BasicNameValuePair("jsonRequest",json.toJson(bean)));

				final String httpGetData = new httpconnect().httpGetData("pdaMainTask!getPhotoById.interface", list,
						PhotoOfficeActivity.this);

				if (httpGetData == null) {
					if ("".equals(httpGetData)) {
						return;
					}
				}

				try {
					final Message message = new Message();
					final JSONObject jsonObject = new JSONObject(httpGetData.toString());
					final String string = jsonObject.getString("info");
					if (jsonObject.getString("result").equals("0")) {
						message.what = 4;
						message.obj = string;
						handler.sendMessage(message);
						return;
					}
					message.what = -1;
					message.obj = string;
					handler.sendMessage(message);
				} catch (JSONException ex) {
				}

			}
		}).start();

	}

	private void deleteImg(final int pos){
		Message message = Message.obtain();

		try{
			photoList.remove(pos);
			message.what = 3;
			message.obj = "删除成功";
			handler.sendMessage(message);
		}catch(Exception e){
			message.what = -1;
			message.obj = "删除失败，请重试";
			handler.sendMessage(message);
			Log.d("lixu", "删除异常"+e.toString());
		}


		/*final ArrayList<NameValuePair> list = new ArrayList<NameValuePair>();
		String json = "{\"photoName\":\""+dId+"\"}";
        list.add((NameValuePair)new BasicNameValuePair("jsonRequest",json));

        new Thread(new Runnable() {
			@Override
			public void run() {
				  String request = new httpconnect().httpGetData("pdaMainTask!"+deleteImgUrl+".interface", list,
							PhotoOfficeActivity.this);

				 try{
					 JSONObject jsonObject = new JSONObject(request.toString());
					 final Message message = Message.obtain();

					 final String string = jsonObject.getString("info");

						if (jsonObject.getString("result").equals("0")) {
							message.what = 3;
							message.obj = string;
							mPhotoInfoBeanList.remove(pos);
							handler.sendMessage(message);
							return;
						}
					message.what = -1;
					message.obj = string;
					handler.sendMessage(message);

				 }catch(Exception e){

				 }


			}
		}).start();*/



	}

	class GridViewAdapter extends BaseAdapter {
		@Override
		public int getCount() {
			if (photoList != null) {
				return photoList.size();
			}
			return 0;
		}

		@Override
		public Object getItem(final int n) {
			return photoList.get(n);
		}

		@Override
		public long getItemId(final int n) {
			return n;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = new ImageView(context);
			}

			ImageView iv = (ImageView) convertView;
			iv.setBackgroundColor(Color.GRAY);

			AbsListView.LayoutParams lp = new AbsListView.LayoutParams(300, 300);
			iv.setLayoutParams(lp);
			iv.setScaleType(ScaleType.FIT_XY);
			Log.d("lixu", "图片地址---"+(photoList.get(position)));
			Picasso.with(context).load(photoList.get(position)).into(iv);
			return convertView;
		}

	}

	@Override
	protected void onDestroy() {
		EventBus.getDefault().unregister(this);
		super.onDestroy();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK) {
			if (requestCode == REQUESTCODE_SHOOT_PHOTO) {
				processPhoto();
			}
		}
	}

	String photoPath;
	//TODO 先实现，需要优化
	private void processPhoto() {
		if (this.mProgress == null) {
			this.mProgress = ProgressDialog.show(this, "系统提示", "正在处理照片...");
		} else {
			this.mProgress.setMessage("正在处理照片...");
			this.mProgress.show();
		}
		new Thread() {
			@Override
			public void run() {

				File dir = new File(ZSLConst.photodir);
				if (!dir.exists()) {
					dir.mkdirs();
				}

				File destination = new File(dir, System.currentTimeMillis() + ".jpg");
				ByteArrayOutputStream bytes = new ByteArrayOutputStream();
				PictureUtil.getSmallBitmap(photoPath).compress(Bitmap.CompressFormat.JPEG, 90, bytes);

				new File(photoPath).delete();

				//打水印
				FileOutputStream fo;
				try {
					destination.createNewFile();

					fo = new FileOutputStream(destination);

					fo.write(bytes.toByteArray());
					fo.close();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				String photoName = destination.getName();

				List<String> waterMarkerList = new ArrayList<String>();

				waterMarkerList.add(StringUtils.toDateString(new Date()));

				waterMarkerList.add(String.valueOf(ZSLConst.curGpsLocation.getLongitude()) + "-"
						+ String.valueOf(ZSLConst.curGpsLocation.getLatitude()));
				File file = ImageUtils.addWaterMarker(destination.getAbsolutePath(), photoName+".tmp", waterMarkerList);//getAbsolutePath()
				photoList.add(file);
				//------------------------------------------------------------------------------------------------

				String uid = PreferencesUtils.getString(context, "UID", "");

				PhotoInfoBean mPhotoBean = new PhotoInfoBean();
				mPhotoBean.setRouteID(routeId);
				mPhotoBean.setUserId(uid);
				mPhotoBean.setPhotoType(mPhotoType);
				mPhotoBean.setResourceType(resourceType);
				mPhotoBean.setLatitude(mLat);
				mPhotoBean.setLongitude(mLon);
				mPhotoBean.setPhotoString(PictureUtil.bitmapToString(file.getPath()));
				if(relatedId!=null || !relatedId.isEmpty()){
					try{
						mPhotoBean.setRelatedID(Integer.parseInt(relatedId));
					}catch(Exception e){
						Toast.makeText(PhotoOfficeActivity.this, "数据异常，请退出重试", Toast.LENGTH_SHORT).show();
						return;
					}
				}

				if(MySQLHelper.savePhoto(PhotoOfficeActivity.this, mPhotoBean)){
					Message message = Message.obtain();
					message.what = 2;
					message.obj = "保存成功";
					handler.sendMessage(message);
				}
				/*
				photoHelper = new DataBaseHelper(PhotoOfficeActivity.this, ApplicationValue.PHOTOTABLENAME, null, 1);
		        photoDateBase = photoHelper.getWritableDatabase();
		        ContentValues mContent = new ContentValues();
				mContent.put(ApplicationValue.PHOTOTABLE_ROUTEID,routeId);
				mContent.put(ApplicationValue.PHOTOTABLE_USERID,uid);
				mContent.put(ApplicationValue.PHOTOTABLE_PHOTOTYPE,mPhotoType);
				mContent.put(ApplicationValue.PHOTOTABLE_RESOURCETYPE,resourceType);
				mContent.put(ApplicationValue.PHOTOTABLE_LAT,mLat);
				mContent.put(ApplicationValue.PHOTOTABLE_LON,mLon);
				mContent.put(ApplicationValue.PHOTOTABLE_POHOTO,PictureUtil.bitmapToString(file.getPath()));
				if(relatedId!=null || !relatedId.isEmpty()){
					try{
						mContent.put(ApplicationValue.PHOTOTABLE_RELATEDID, Integer.parseInt(relatedId));
					}catch(Exception e){
						Toast.makeText(PhotoOfficeActivity.this, "数据异常，请退出重试", Toast.LENGTH_SHORT).show();
						return;
					}
				}
				photoDateBase.insert(ApplicationValue.PHOTOTABLENAME, null, mContent);*/



			/*	photoBean.setRouteID(routeId);
				photoBean.setUserId(uid);
				photoBean.setPhotoType(mPhotoType);
				photoBean.setResourceType(resourceType);
				photoBean.setLatitude(mLat);
				photoBean.setLongitude(mLon);*/
				/*Gson gson = new Gson();
				Log.d("lixu", "打水印10");
		        list.add((NameValuePair)new BasicNameValuePair("jsonRequest", gson.toJson(photoBean)));

		        requestStr = new httpconnect().httpGetData("pdaMainTask!uploadPhotoAndInfo.interface", list,
						PhotoOfficeActivity.this);
		        Log.d("lixu", "打水印11");
				Log.d("lixu", "----上传图片返回数据" + requestStr);
				try {

				final Message message = new Message();
				JSONObject jsonObject = new JSONObject(requestStr.toString());

				final String string = jsonObject.getString("info");
					if (jsonObject.getString("result").equals("0")) {
						message.what = 2;
						message.obj = string;
						handler.sendMessage(message);
						return;
					}

				message.what = -1;
				message.obj = string;
				handler.sendMessage(message);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}*/

				/*new File(photoPath).delete();

				FileOutputStream fo;
				try {
					destination.createNewFile();
					fo = new FileOutputStream(destination);
					fo.write(bytes.toByteArray());
					fo.close();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}

				String photoName = destination.getName();
				List<String> waterMarkerList = new ArrayList<String>();
				waterMarkerList.add(StringUtils.toDateString(new Date()));
				waterMarkerList.add(String.valueOf(ZSLConst.curGpsLocation.getLongitude()) + "-"
						+ String.valueOf(ZSLConst.curGpsLocation.getLatitude()));
				File file = ImageUtils.addWaterMarker(destination.getAbsolutePath(), photoName+".tmp", waterMarkerList);

				destination.delete();

				File destination2 = new File(dir, photoName);
				ByteArrayOutputStream bytes2 = new ByteArrayOutputStream();
				PictureUtil.getSmallBitmap(file.getAbsolutePath()).compress(Bitmap.CompressFormat.JPEG, 90, bytes2);

				file.delete();
				FileOutputStream fo2;
				try {
					destination2.createNewFile();
					fo2 = new FileOutputStream(destination2);
					fo2.write(bytes2.toByteArray());
					fo2.close();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}

				GuangPhotoInfoBean photoBean = new GuangPhotoInfoBean();
				photoBean.setPhotoName(destination2.getName());

				photoBean.setRelatedID(relatedId==0?null:relatedId);
				photoBean.setLatitude(ZSLConst.curLocation.getLatitude());
				photoBean.setLongitude(ZSLConst.curLocation.getLongitude());

				mPhotoInfoBeanList.add(photoBean);*/

			}
		}.start();
	}

	public void photoSelect(View v) {
		File dir = new File(ZSLConst.photodir);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		String photoName = System.currentTimeMillis() + ".jpg";
		photoPath = ZSLConst.photodir + photoName;
		File destination = new File(dir, photoName);
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		if (!destination.exists()) {

			try {
				destination.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
		intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(destination));
		startActivityForResult(intent, REQUESTCODE_SHOOT_PHOTO);
	}

	public void save(View v) {
		if(!photoed){
			//没有拍照直接点保存时不做任何处理
			Toast.makeText(PhotoOfficeActivity.this, "您并未拍照!", Toast.LENGTH_SHORT).show();
			return;
		}

		Intent data = new Intent();
		data.putExtra("mPhotoType", mPhotoType);
		if(photoList != null && photoList.size() != 0){
			data.putExtra("isTake", true);
		}else{
			data.putExtra("isTake", false);
		}

		setResult(RESULT_OK,data);
		finish();

	}

	public void submit(View v) {
		/*if(mPhotoInfoBeanList==null||mPhotoInfoBeanList.size()==0){
			Toast.makeText(PhotoOfficeActivity.this, "暂无照片可提交...", Toast.LENGTH_SHORT).show();
			return;
		}*/
		//	uploadFileNoHttp();
	}

	public void onBackPressed() {
		if(photoList == null || photoList.size() == 0){
			String msg = "按照要求必须拍摄照片,退出会放弃本次交割!继续拍照?";
			AlertDialog ad = new AlertDialog.Builder(context).setTitle("温馨提示").setMessage(msg)
					.setPositiveButton("拍照", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							dialog.dismiss();
							photoSelect(null);
						}
					}).setNegativeButton("放弃", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							dialog.dismiss();
							Intent data = new Intent();
							data.putExtra("mPhotoType", mPhotoType);
							data.putExtra("isTake", false);
							setResult(RESULT_CANCELED);
							finish();
						}
					}).create();
			ad.show();
		}else{
			Intent data = new Intent();
			//	data.putExtra("photos", mPhotoInfoBeanList);
			data.putExtra("mPhotoType", mPhotoType);
			data.putExtra("isTake", true);

			setResult(RESULT_OK, data);
			finish();
		}
	}

	/**
	 * 用NoHtt默认实现上传文件.
	 */
    /*private void uploadFileNoHttp() {

    	String uid = PreferencesUtils.getString(PhotoShopeActivityGuang.this, "UID", "");
		String jsonRequest = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss Z").create().toJson(mPhotoInfoBeanList);

		 Map<String,String> paramMap = new HashMap<String, String>();
	        paramMap.put("uid", uid);
	        paramMap.put("jsonRequest", jsonRequest);
	        final Map<String,File> fileMap = new HashMap<String, File>();
	        File file = null;

				for (GuangPhotoInfoBean photo : mPhotoInfoBeanList) {
					file = new File(ZSLConst.photodir, photo.getPhotoName());
					if(file!=null){
						fileMap.put(photo.getPhotoName(), file);
					}
				}

	        RequestTool.uploadFileNoHttp(PhotoShopeActivityGuang.this, ApplicationValue.url+"photoUpload", paramMap, fileMap, new OnUploadListener() {

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
					String result = response.get();
					if(StringUtils.isEmpty(result)){
						Toast.makeText(PhotoShopeActivityGuang.this, "服务端异常!", Toast.LENGTH_SHORT).show();
						return;
					}
					try {
						JSONObject jsonObject = new JSONObject(response.get());
					if(jsonObject!=null){
						if ("0".equals(jsonObject.getString("result"))) {
							Toast.makeText(PhotoShopeActivityGuang.this, "提交成功!", Toast.LENGTH_SHORT).show();
							  for (File file : fileMap.values()) {
						        	file.delete();
						      }
							  mPhotoInfoBeanList.clear();
							  mGridViewAdapter.notifyDataSetChanged();
						}else{
							String string = jsonObject.getString("info");
							Toast.makeText(PhotoShopeActivityGuang.this, string, Toast.LENGTH_SHORT).show();
						}
					}
					} catch (JSONException e) {
						Toast.makeText(PhotoShopeActivityGuang.this, "提交失败！", Toast.LENGTH_SHORT).show();
						e.printStackTrace();
					}


				}

				@Override
				public void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis) {
					Toast.makeText(PhotoShopeActivityGuang.this, "提交失败！"+exception.getMessage(), Toast.LENGTH_SHORT).show();
				}
			});
    }*/

}
