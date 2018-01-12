package com.inspur.resources.view.delivery.transroute;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import com.alibaba.fastjson.JSON;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.inspur.StringUtils;
import com.inspur.common.CacheHelper;
import com.inspur.common.HttpListener;
import com.inspur.common.RequestTool;
import com.inspur.easyresources.R;
import com.inspur.resources.base.BaseActivity;
import com.inspur.resources.http.httpconnect;
import com.inspur.resources.utils.ApplicationValue;
import com.inspur.resources.view.delivery.guang.GuangSubmitActivity;
import com.inspur.resources.view.delivery.transroute.bean.ErrorInfoBean;
import com.inspur.resources.view.delivery.transroute.bean.GuangBean;
import com.inspur.resources.view.delivery.transroute.bean.PhotoInfoBean;
import com.inspur.resources.view.delivery.transroute.bean.PointlikeResourceInfoBean;
import com.inspur.resources.view.delivery.transroute.bean.RouteInfoBean;
import com.yolanda.nohttp.OnUploadListener;
import com.yolanda.nohttp.Response;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;
import cn.trinea.android.common.util.PreferencesUtils;

public class RouteSubmitActivity extends BaseActivity {
	public final static String INTENT_DATA_FLAG = "RouteInfo";
	private EditText qsd_edit, zzd_edit, jgr_edit, dzyppl_edit;
	private Spinner lyzt_spinner,jgzt_spinner;
	private LinearLayout layout_button;

	private RouteInfoBean routeInfoBean;
	private boolean canEdit = false;

	private boolean submited = false;
	private boolean submitedOK = false;
	private ProgressDialog mProgress;
	private Button cs_save_button,submit_button,take_photo_button,refresh_button;

	@Override
	protected void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		setContentView(R.layout.activity_route_submit);
		setTitle("资源交割");

		Intent intent = getIntent();
		canEdit = intent.getBooleanExtra("canEdit", true);

		initUI();
		initData(routeInfoBean = (RouteInfoBean) intent.getSerializableExtra(INTENT_DATA_FLAG));

		if( !canEdit ){
			lyzt_spinner.setEnabled(false);
			jgzt_spinner.setEnabled(false);
			take_photo_button.setEnabled(false);
			//	refresh_button.setEnabled(false);
			submit_button.setEnabled(false);
			cs_save_button.setEnabled(false);
		}

	}

	private void initUI() {
		take_photo_button = (Button) findViewById(R.id.take_photo_button);
		refresh_button = (Button) findViewById(R.id.refresh_button);
		cs_save_button = (Button) findViewById(R.id.cs_save_button);
		submit_button = (Button) findViewById(R.id.submit_button);
		layout_button = (LinearLayout) findViewById(R.id.layout_button);
		cs_save_button.setVisibility(canEdit?View.VISIBLE:View.GONE);
		qsd_edit = (EditText) findViewById(R.id.qsd_edit);
		zzd_edit = (EditText) findViewById(R.id.zzd_edit);
		jgr_edit = (EditText) findViewById(R.id.jgr_edit);
		dzyppl_edit = (EditText) findViewById(R.id.dzyppl_edit);

		lyzt_spinner = (Spinner) findViewById(R.id.lyzt_spinner);
		ArrayAdapter<CharSequence> jjcdAdapter = ArrayAdapter.createFromResource(this, R.array.luyouzhuangtai,
				android.R.layout.simple_spinner_item);
		lyzt_spinner.setAdapter(jjcdAdapter);

		jgzt_spinner = (Spinner) findViewById(R.id.jgzt_spinner);
		ArrayAdapter<CharSequence> jgztAdapter = ArrayAdapter.createFromResource(this, R.array.jiaogezhuangtai,
				android.R.layout.simple_spinner_item);
		jgzt_spinner.setAdapter(jgztAdapter);


		lyzt_spinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

				if(position!=0){
					dzyppl_edit.setText("");
					jgzt_spinner.setSelection(0);
					jgzt_spinner.setEnabled(true);
				}else{
					jgzt_spinner.setSelection(2);
					jgzt_spinner.setSelection(routeInfoBean.getRouteState()==null?2:(routeInfoBean.getDeliveryState()==null?2:routeInfoBean.getDeliveryState()));
					jgzt_spinner.setEnabled(false);
				}

			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub

			}
		});
	}

	private void initData(RouteInfoBean routeInfo) {
		if(routeInfo.getStartPosition()!=null){
			qsd_edit.setText(routeInfo.getStartPosition().getResourceName());
		}
		if(routeInfo.getEndPosition()!=null){
			zzd_edit.setText(routeInfo.getEndPosition().getResourceName());
		}
		jgr_edit.setText(StringUtils.isEmpty(routeInfo.getDealPerson())?PreferencesUtils.getString(RouteSubmitActivity.this, "UID", ""):routeInfo.getDealPerson());
		dzyppl_edit.setText(StringUtils.isEmpty(routeInfo.getMatchScores())?"":(routeInfo.getMatchScores()+"%"));

		double matchScores = StringUtils.isEmpty(routeInfo.getMatchScores())?0:Double.parseDouble(routeInfo.getMatchScores());
		lyzt_spinner.setSelection(routeInfo.getRouteState()==null?0:routeInfo.getRouteState());


//				Toast.makeText(RouteSubmitActivity.this, "aa="+(routeInfoBean.getRouteState()==null?2:(routeInfoBean.getDeliveryState()==null?2:routeInfoBean.getDeliveryState())), Toast.LENGTH_SHORT).show();
//				Toast.makeText(RouteSubmitActivity.this, "bb="+routeInfoBean.getDeliveryState(), Toast.LENGTH_SHORT).show();

		//	jgzt_spinner.setSelection(routeInfo.getRouteState()==null?2:(routeInfo.getDeliveryState()==null?2:routeInfo.getDeliveryState()));
		jgzt_spinner.setSelection(routeInfo.getRouteState()==null?2:(routeInfo.getDeliveryState()==null?2:routeInfo.getDeliveryState()));
		jgzt_spinner.setEnabled(false);
	}

	private RouteInfoBean editInfo(RouteInfoBean routeInfo) {
		routeInfo.setRouteState(lyzt_spinner.getSelectedItemPosition());
		routeInfo.setDeliveryState(jgzt_spinner.getSelectedItemPosition());
		routeInfo.setDealPerson(jgr_edit.getText().toString());

		return routeInfo;
	}

	// 保存本地
	public void cs_save(View v) {
		if(CacheHelper.getInstance(this).saveObject(editInfo(routeInfoBean), ZSLConst.PREFIX_OF_OFFLINE_ROUTE+routeInfoBean.getRouteID())){
			Toast.makeText(RouteSubmitActivity.this, "保存成功", Toast.LENGTH_SHORT).show();
			setResult(RESULT_OK);
			finish();
		}
	}

	// 提交
	public void cs_submit(View v) {
		if(mProgress == null){
			mProgress = new ProgressDialog(RouteSubmitActivity.this);
			mProgress.setMessage("正在提交数据...");
			mProgress.show();
		}else{
			mProgress.show();
		}

		routeInfoBean = editInfo(routeInfoBean);


		try{
			Log.d("lixu", "轨迹点查询=="+routeInfoBean.getLocusPoints().size());
			Log.d("lixu", "起点查询=="+routeInfoBean.getStartPosition().getResourceName());
			Log.d("lixu", "终点查询=="+routeInfoBean.getEndPosition().getResourceName());
			Log.d("lixu", "查询==getRouteID"+routeInfoBean.getRouteID());
			Log.d("lixu", "查询==getUserId"+routeInfoBean.getUserId());
			Log.d("lixu", "查询==getUserId"+routeInfoBean.getName());
			Log.d("lixu", "查询==getID"+routeInfoBean.getID());
			Log.d("lixu", "查询==ToString"+routeInfoBean.toString());

			Log.d("lixu", "查询==+tart"+routeInfoBean.getStartPosition().toString());
			Log.d("lixu", "查询==+End"+routeInfoBean.getEndPosition().toString());

		}catch(Exception e){
			Log.d("lixu", "查询==出错了"+e.toString());
		}

		String uid = PreferencesUtils.getString(RouteSubmitActivity.this, "UID", "");
		String jsonRequest = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss Z").create().toJson(routeInfoBean);
		System.out.println(jsonRequest);
       /* Map<String,String> paramMap = new HashMap<String, String>();
        paramMap.put("uid", uid);
        paramMap.put("jsonRequest", jsonRequest);
        Map<String,File> fileMap = new HashMap<String, File>();
        File file = null;*/
		//if(routeInfoBean.getStartPosition()!=null&&routeInfoBean.getStartPosition().getPhotos()!=null){
		/*for (PhotoInfoBean photo : routeInfoBean.getStartPosition().getPhotos()) {
			file = new File(ZSLConst.photodir, photo.getPhotoName());
			if(file!=null){
				fileMap.put(photo.getPhotoName(), file);
			}
		}
        }*/
		/*if(routeInfoBean.getEndPosition()!=null && routeInfoBean.getEndPosition().getPhotos()!=null){
		for (PhotoInfoBean photo : routeInfoBean.getEndPosition().getPhotos()) {
			file = new File(ZSLConst.photodir, photo.getPhotoName());
			if(file!=null){
				fileMap.put(photo.getPhotoName(), file);
			}
		}
		}*/

		/*if(routeInfoBean.getErrors()!=null){
		for (ErrorInfoBean error : routeInfoBean.getErrors()) {
			if(error.getErrorsPhotos()==null||error.getErrorsPhotos().size()==0){
				break;
			}
			for (PhotoInfoBean photo : error.getErrorsPhotos()) {
				file = new File(ZSLConst.photodir, photo.getPhotoName());
				if(file!=null){
					fileMap.put(photo.getPhotoName(), file);
				}
			}
		}
		}*/


		submited = true;
		//	Toast.makeText(RouteSubmitActivity.this, "预计处理过程持续1到3分钟，请耐心等待...", Toast.LENGTH_SHORT).show();
		final String url = "pdaMainTask!saveTask.interface";


		final ArrayList<NameValuePair> list = new ArrayList<NameValuePair>();

		list.add((NameValuePair)new BasicNameValuePair("jsonRequest",	jsonRequest));

		new Thread(new Runnable() {

			@Override
			public void run() {

				final String httpGetData = new httpconnect().httpGetData(url, list,
						RouteSubmitActivity.this);
				try {
					final Message message = new Message();
					final JSONObject jsonObject = new JSONObject(httpGetData.toString());
					Log.d("lixu", "-------"+jsonObject.toString());
					final String string = jsonObject.getString("info");
					Log.d("lixu", "-------"+string);
					if (jsonObject.getString("result").equals("0")) {
						message.what = 4;
						message.obj = string;
						mResult = string;
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




      /*  RequestTool.uploadFileNoHttp(RouteSubmitActivity.this, url, paramMap, fileMap, new OnUploadListener() {

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
				JSONObject jsonObject;
				String info="";
				String result = response.get();
				if(StringUtils.isEmpty(result)){
					Toast.makeText(RouteSubmitActivity.this, "服务端异常!", Toast.LENGTH_SHORT).show();
					return;
				}
				try {
					jsonObject = new JSONObject(result);
					info = jsonObject.get("info").toString();
				if (!"0".equals(jsonObject.getString("result"))) {

					Toast.makeText(RouteSubmitActivity.this, "处理失败,请重试!"+info, Toast.LENGTH_SHORT).show();
					return;
				}
				} catch (JSONException e) {
					e.printStackTrace();
				}
				submitedOK = true;
				RouteInfoBean routeInfoBean = null;
				try {
					routeInfoBean = JSON.parseObject(info, RouteInfoBean.class);
				} catch (Exception e) {
					Toast.makeText(RouteSubmitActivity.this, "服务端异常,请重试!", Toast.LENGTH_SHORT).show();
					e.printStackTrace();
					return;
				}
				if(routeInfoBean==null){
					Toast.makeText(RouteSubmitActivity.this, "服务端异常,请重试!", Toast.LENGTH_SHORT).show();
					return;
				}
				initData(routeInfoBean);
				lyzt_spinner.setEnabled(false);
				jgzt_spinner.setEnabled(false);
				cs_save_button.setEnabled(false);
				submit_button.setEnabled(false);
				if(!canEdit||(routeInfoBean.getRouteState()!=null&&routeInfoBean.getRouteState()!=0)){
					Toast.makeText(RouteSubmitActivity.this, "提交成功!", Toast.LENGTH_SHORT).show();
					//如果是从我的交割过来的,编辑提交后回传新的数据以更新列表
					if(!canEdit){
						Intent data = new Intent();
						data.putExtra("route", routeInfoBean);
						setResult(RESULT_OK,data);
					}else{
						setResult(RESULT_OK);
					}

					finish();
				}
				System.out.println(response.get());
			}

			@Override
			public void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis) {
				exception.printStackTrace();
				Toast.makeText(RouteSubmitActivity.this, "系统超时,请尝试刷新或重试!"+exception.getMessage(), Toast.LENGTH_SHORT).show();
			}
		});*/

	}

	public void onBackPressed() {
		if(!canEdit){
			super.onBackPressed();
			return;
		}
		if(submitedOK){
			setResult(RESULT_OK);
			finish();
			return;
		}
		String msg = "交割尚未成功处理, 请先处理!";
		AlertDialog ad = new AlertDialog.Builder(RouteSubmitActivity.this).setTitle("温馨提示").setMessage(msg)
				.setPositiveButton("处理", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				}).setNegativeButton("放弃", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
						setResult(RESULT_CANCELED);
						finish();
					}
				}).create();
		ad.show();
	}

	public void cs_refresh(View v){
		if(canEdit&&!submited){
			Toast.makeText(RouteSubmitActivity.this, "您尚未提交计算,请先提交计算", Toast.LENGTH_SHORT).show();
			return;
		}
		loadRouteInfo(String.valueOf(routeInfoBean.getRouteID()));
	}

	private void loadRouteInfo(final String routeId){
		if (this.mProgress == null) {
			this.mProgress = ProgressDialog.show(this, "系统提示", "正在加载数据...");
		} else {
			this.mProgress.setMessage("正在加载数据...");
			this.mProgress.show();
		}
		new Thread() {
			@Override
			public void run() {
				final ArrayList<NameValuePair> list = new ArrayList<NameValuePair>();
				String json = "{\"routeID\":"+routeId+"}";
				list.add(new BasicNameValuePair("jsonRequest", json));
				final String httpGetData = new httpconnect().httpGetData("pdaMainTask!getRouteInfoById.interface", list,
						RouteSubmitActivity.this);
				Log.d("AddrList==>", "result==>" + httpGetData);
				System.out.println("result==>" + httpGetData);
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
						message.what = 1;
						message.obj = string;
						handler.sendMessage(message);
						return;
					}
					message.what = 2;
					message.obj = string;
					handler.sendMessage(message);
				} catch (JSONException ex) {
				}
			}
		}.start();
	}


	private String mResult;
	private Handler handler = new Handler(new Handler.Callback() {

		public boolean handleMessage(Message msg) {
			if (mProgress != null) {
				mProgress.dismiss();
			}
			switch (msg.what) {
				case 1:
					RouteInfoBean routeInfoBean2 = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss Z").create()
							.fromJson(msg.obj.toString(), RouteInfoBean.class);
					initData(routeInfoBean2);
					break;
				case 2:
					Toast.makeText(RouteSubmitActivity.this, msg.obj.toString(), Toast.LENGTH_SHORT).show();
					break;
				case -1:
					Toast.makeText(RouteSubmitActivity.this, msg.obj.toString(), Toast.LENGTH_SHORT).show();
					break;
				case 4:
					JSONObject jsonObject;
					String result = mResult;
					Log.d("lixu","++++"+result);
					if(StringUtils.isEmpty(result)){
						Toast.makeText(RouteSubmitActivity.this, "服务端异常!", Toast.LENGTH_SHORT).show();
						break;
					}
					submitedOK = true;
					RouteInfoBean routeInfoBean = null;
					try {
						//routeInfoBean = JSON.parseObject(info, RouteInfoBean.class);

						routeInfoBean = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss Z").create()
								.fromJson(result, new TypeToken<RouteInfoBean>() {}.getType());

//					RouteInfoBean aa = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss Z").create()
//							.fromJson(msg, new TypeToken<RouteInfoBean>() {}.getType());


					} catch (Exception e) {
						Log.d("lixu","---json"+e.toString());
						Toast.makeText(RouteSubmitActivity.this, "服务端异常,请重试!", Toast.LENGTH_SHORT).show();
						e.printStackTrace();
						break;
					}
					if(routeInfoBean==null){
						Toast.makeText(RouteSubmitActivity.this, "服务端异常,请重试!", Toast.LENGTH_SHORT).show();
						break;
					}
					initData(routeInfoBean);
					lyzt_spinner.setEnabled(false);
					jgzt_spinner.setEnabled(false);
					cs_save_button.setEnabled(false);
					submit_button.setEnabled(false);


					if(!canEdit||(routeInfoBean.getRouteState()!=null&&routeInfoBean.getRouteState()!=0)){
						Toast.makeText(RouteSubmitActivity.this, "提交成功!", Toast.LENGTH_SHORT).show();
						//如果是从我的交割过来的,编辑提交后回传新的数据以更新列表
						if(!canEdit){
							Intent data = new Intent();
							data.putExtra("route", routeInfoBean);
							setResult(RESULT_OK,data);
						}else{
							setResult(RESULT_OK);
						}
						finish();
					}else{
						Toast.makeText(RouteSubmitActivity.this, "提交成功!", Toast.LENGTH_SHORT).show();
						Intent data = new Intent();
						data.putExtra("route", routeInfoBean);
						setResult(RESULT_OK);
						finish();
					}

					initData(routeInfoBean);
					lyzt_spinner.setEnabled(false);
					jgzt_spinner.setEnabled(false);
					cs_save_button.setEnabled(false);
					submit_button.setEnabled(false);
					if(!canEdit||(routeInfoBean.getRouteState()!=null&&routeInfoBean.getRouteState()!=0)){
						Toast.makeText(RouteSubmitActivity.this, "提交成功!", Toast.LENGTH_SHORT).show();
						//如果是从我的交割过来的,编辑提交后回传新的数据以更新列表
						if(!canEdit){
							Intent data = new Intent();
							data.putExtra("route", routeInfoBean);
							setResult(RESULT_OK,data);
						}else{
							setResult(RESULT_OK);
						}

						finish();
					}

					break;
			}
			return true;
		}

	});

}