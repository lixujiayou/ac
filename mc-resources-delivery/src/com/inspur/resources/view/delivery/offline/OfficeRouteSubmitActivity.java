package com.inspur.resources.view.delivery.offline;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
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
import com.inspur.resources.utils.ImageUtils;
import com.inspur.resources.utils.PictureUtil;
import com.inspur.resources.utils.sq.MySQLHelper;
import com.inspur.resources.view.delivery.guang.GuangSubmitActivity;
import com.inspur.resources.view.delivery.guang.PhotoShopeActivityGuang;
import com.inspur.resources.view.delivery.transroute.HiddenTroubleReportActivity2;
import com.inspur.resources.view.delivery.transroute.ZSLConst;
import com.inspur.resources.view.delivery.transroute.ZSLTransmissionLine;
import com.inspur.resources.view.delivery.transroute.bean.ErrorInfoBean;
import com.inspur.resources.view.delivery.transroute.bean.ErrorInfoGdgzBean;
import com.inspur.resources.view.delivery.transroute.bean.ErrorInfoZzBean;
import com.inspur.resources.view.delivery.transroute.bean.GuangBean;
import com.inspur.resources.view.delivery.transroute.bean.PhotoInfoBean;
import com.inspur.resources.view.delivery.transroute.bean.PointlikeResourceInfoBean;
import com.inspur.resources.view.delivery.transroute.bean.ResourceInfoBean;
import com.inspur.resources.view.delivery.transroute.bean.RouteInfoBean;
import com.yolanda.nohttp.OnUploadListener;
import com.yolanda.nohttp.Response;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
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
import cn.trinea.android.common.util.ToastUtils;

public class OfficeRouteSubmitActivity extends BaseActivity {
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

	private int routeID = 0;
	private int officeRouteId = 0;
	@Override
	protected void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		setContentView(R.layout.activity_route_submit);
		setTitle("资源交割(离线)");

		Intent intent = getIntent();
		canEdit = intent.getBooleanExtra("canEdit", true);

		initUI();
		initData(routeInfoBean = (RouteInfoBean) intent.getSerializableExtra(INTENT_DATA_FLAG));

		officeRouteId = routeInfoBean.getRouteID();

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
		jgr_edit.setText(StringUtils.isEmpty(routeInfo.getDealPerson())?PreferencesUtils.getString(OfficeRouteSubmitActivity.this, "UID", ""):routeInfo.getDealPerson());
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
		routeInfo.setUserId(jgr_edit.getText().toString());

		return routeInfo;
	}

	// 保存本地
	public void cs_save(View v) {
		if(CacheHelper.getInstance(this).saveObject(editInfo(routeInfoBean), ZSLConst.PREFIX_OF_OFFLINE_ROUTE+routeInfoBean.getRouteID())){

			Toast.makeText(OfficeRouteSubmitActivity.this, "保存成功", Toast.LENGTH_SHORT).show();
			setResult(RESULT_OK);
			finish();
		}
	}

	//提交
	public void cs_submit(View v) {
		requestRoute();
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
		AlertDialog ad = new AlertDialog.Builder(OfficeRouteSubmitActivity.this).setTitle("温馨提示").setMessage(msg)
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
			Toast.makeText(OfficeRouteSubmitActivity.this, "您尚未提交计算,请先提交计算", Toast.LENGTH_SHORT).show();
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
						OfficeRouteSubmitActivity.this);
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
					RouteInfoBean routeInfoBean3 = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss Z").create()
							.fromJson(msg.obj.toString(), RouteInfoBean.class);
					initData(routeInfoBean3);
					break;
				case 2:
					Toast.makeText(OfficeRouteSubmitActivity.this, msg.obj.toString(), Toast.LENGTH_SHORT).show();
					break;
				case -1:
					Toast.makeText(OfficeRouteSubmitActivity.this, msg.obj.toString(), Toast.LENGTH_SHORT).show();
					break;
				case 4:
					JSONObject jsonObject;
					String result = mResult;
					Log.d("lixu","++++"+result);
					if(StringUtils.isEmpty(result)){
						Toast.makeText(OfficeRouteSubmitActivity.this, "服务端异常!", Toast.LENGTH_SHORT).show();
						break;
					}
					submitedOK = true;
					RouteInfoBean routeInfoBean2 = null;
					try {
						//routeInfoBean = JSON.parseObject(info, RouteInfoBean.class);

						routeInfoBean2 = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss Z").create()
								.fromJson(result, new TypeToken<RouteInfoBean>() {}.getType());

//					RouteInfoBean aa = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss Z").create()
//							.fromJson(msg, new TypeToken<RouteInfoBean>() {}.getType());


					} catch (Exception e) {
						Log.d("lixu","---json"+e.toString());
						Toast.makeText(OfficeRouteSubmitActivity.this, "服务端异常,请重试!", Toast.LENGTH_SHORT).show();
						e.printStackTrace();
						break;
					}
					if(routeInfoBean2==null){
						Toast.makeText(OfficeRouteSubmitActivity.this, "服务端异常,请重试!", Toast.LENGTH_SHORT).show();
						break;
					}
					initData(routeInfoBean2);
					lyzt_spinner.setEnabled(false);
					jgzt_spinner.setEnabled(false);
					cs_save_button.setEnabled(false);
					submit_button.setEnabled(false);


					if(!canEdit||(routeInfoBean2.getRouteState()!=null&&routeInfoBean2.getRouteState()!=0)){
						Toast.makeText(OfficeRouteSubmitActivity.this, "提交成功!", Toast.LENGTH_SHORT).show();
						//如果是从我的交割过来的,编辑提交后回传新的数据以更新列表
						if(!canEdit){
							Intent data = new Intent();
							data.putExtra("route", routeInfoBean2);
							setResult(RESULT_OK,data);
						}else{
							setResult(RESULT_OK);
						}
						finish();
					}else{
						Toast.makeText(OfficeRouteSubmitActivity.this, "提交成功!", Toast.LENGTH_SHORT).show();
						Intent data = new Intent();
						data.putExtra("route", routeInfoBean2);
						setResult(RESULT_OK);
						finish();
					}

					initData(routeInfoBean2);
					lyzt_spinner.setEnabled(false);
					jgzt_spinner.setEnabled(false);
					cs_save_button.setEnabled(false);
					submit_button.setEnabled(false);
					if(!canEdit||(routeInfoBean2.getRouteState()!=null&&routeInfoBean2.getRouteState()!=0)){
						Toast.makeText(OfficeRouteSubmitActivity.this, "提交成功!", Toast.LENGTH_SHORT).show();
						//如果是从我的交割过来的,编辑提交后回传新的数据以更新列表
						if(!canEdit){
							Intent data = new Intent();
							data.putExtra("route", routeInfoBean2);
							setResult(RESULT_OK,data);
						}else{
							setResult(RESULT_OK);
						}

						finish();
					}

					break;
				//得到routeId后
				case 99:

					Log.d("lixu", "========-查询到routeId"+routeID);
					List<PhotoInfoBean> oPhotoList = MySQLHelper.getPhotos(OfficeRouteSubmitActivity.this, routeInfoBean.getRouteID());
					if(oPhotoList != null && oPhotoList.size() != 0){
						Log.d("lixu", routeInfoBean.getRouteID()+"共查询图片"+oPhotoList.size());
						List<PhotoInfoBean> changeList = changeRouteIdOfPhotos(oPhotoList, routeID);//转换routeId
						Log.d("lixu", "共转换完成图片"+changeList.size());
						processPhoto(changeList);//上传all图片
					}else{
						ToastUtils.show(OfficeRouteSubmitActivity.this, "抱歉，未找到相关图片，无法提交");
						break;
					}
					break;

				//图片上传完成后
				case 96:
					Log.d("lixu", "========-图片上传完成");
					try{
						List<String> erros_1 = MySQLHelper.getErros(OfficeRouteSubmitActivity.this, routeInfoBean.getRouteID(), ApplicationValue.single_list[0]);
						if(erros_1 != null && erros_1.size() != 0){
							Log.d("lixu","查询隐患条数"+erros_1.size());
							commitErros(ApplicationValue.single_list[0] , changeRouteIdOfErros(erros_1, routeID, ApplicationValue.single_list[0]));

						}else{
							erros_1 = MySQLHelper.getErros(OfficeRouteSubmitActivity.this, routeInfoBean.getRouteID(), ApplicationValue.single_list[1]);

							if(erros_1 != null && erros_1.size() != 0){

								commitErros(ApplicationValue.single_list[1] , changeRouteIdOfErros(erros_1, routeID, ApplicationValue.single_list[1]));

							}else{
								erros_1 = MySQLHelper.getErros(OfficeRouteSubmitActivity.this, routeInfoBean.getRouteID(), ApplicationValue.single_list[2]);

								if(erros_1 != null && erros_1.size() != 0){
									commitErros(ApplicationValue.single_list[2] , changeRouteIdOfErros(erros_1, routeID, ApplicationValue.single_list[2]));

								}else{//无隐患，直接提交 交割路由
									submitRoute();
								}
							}
						}
					}catch(Exception e){
						Log.d("lixu", "查询隐患异常-外"+e.toString());
					}
					break;
				//资源隐患提交完成后
				case 10:
					Log.d("lixu", "========-图片上传完成");
					List<String> erros_2 = MySQLHelper.getErros(OfficeRouteSubmitActivity.this, routeInfoBean.getRouteID(), ApplicationValue.single_list[1]);

					if(erros_2 != null && erros_2.size() != 0){

						commitErros(ApplicationValue.single_list[1] , changeRouteIdOfErros(erros_2, routeID, ApplicationValue.single_list[1]));

					}else{
						erros_2 = MySQLHelper.getErros(OfficeRouteSubmitActivity.this, routeInfoBean.getRouteID(), ApplicationValue.single_list[2]);

						if(erros_2 != null && erros_2.size() != 0){
							commitErros(ApplicationValue.single_list[2] , changeRouteIdOfErros(erros_2, routeID, ApplicationValue.single_list[2]));

						}else{//无隐患，直接提交 交割路由
							submitRoute();
						}
					}
					break;
				//综资数据隐患提交后
				case 11:
					List<String> erros_3 = MySQLHelper.getErros(OfficeRouteSubmitActivity.this, routeInfoBean.getRouteID(), ApplicationValue.single_list[2]);
					if(erros_3 != null && erros_3.size() != 0){
						commitErros(ApplicationValue.single_list[2], changeRouteIdOfErros(erros_3, routeID, ApplicationValue.single_list[2]));
					}else{//无隐患，直接提交 交割路由
						submitRoute();
					}

					break;

				//管道故障提交完成后 ,提交 交割路由
				case 12:
					Log.d("lixu", "========-开始提交路由");
					submitRoute();
					break;
			}
			return true;
		}
	});

	/**
	 * 获取路由ID
	 */
	private void requestRoute() {
		/*Message message = Message.obtain();
		message.what = 99;
		routeID = 11111;
		handler.sendMessage(message);*/

		if (this.mProgress == null) {
			this.mProgress = ProgressDialog.show(this, "系统提示", "正在初始化...");
		} else {
			this.mProgress.setMessage("正在初始化...");
			this.mProgress.show();
		}
		new Thread() {
			@Override
			public void run() {

				final ArrayList<NameValuePair> list = new ArrayList<NameValuePair>();
				list.add((NameValuePair) new BasicNameValuePair("jsonRequest", ""));
				final String httpGetData = new httpconnect().httpGetData("pdaMainTask!getRouteInfo.interface", list, OfficeRouteSubmitActivity.this);
				Log.d("AddrList==>", "result==>" + httpGetData);
				System.out.println("httpGetData="+httpGetData);
				if (httpGetData == null) {
					if ("".equals(httpGetData)) {
						return;
					}
				}

				Message message = Message.obtain();
				try {
					final JSONObject jsonObject = new JSONObject(httpGetData.toString());
					final String string = jsonObject.getString("info");
					if (jsonObject.getString("result").equals("0")) {

						message.what = 99;
						routeID = StringUtils.getInt(string);
						handler.sendMessage(message);
						return;
					}
					message.what = 2;
					message.obj = string;
					handler.sendMessage(message);
				} catch (JSONException ex) {
					message.what = 2;
					message.obj = "网络异常";
					handler.sendMessage(message);
				}
			}
		}.start();
	}

	/**
	 * 联网提交隐患
	 */
	private String saveUrl;
	private ArrayList<NameValuePair> listJson;
	private int mWhat = 0;
	private void commitErros(String cType,final List<String> mErros){

		if (this.mProgress == null) {
			this.mProgress = ProgressDialog.show(this, "系统提示", "正在提交隐患数据...");
		} else {
			this.mProgress.setMessage("正在提交隐患数据...");
			this.mProgress.show();
		}
		//资源隐患控制
		if(cType.equals(ApplicationValue.single_list[0])){
			mWhat = 10;
			saveUrl = "saveErrorInfo";
		}else if(cType.equals(ApplicationValue.single_list[1])){
			mWhat = 11;
			saveUrl = "saveErrorZzInfo";
		}else if(cType.equals(ApplicationValue.single_list[2])){
			mWhat = 12;
			saveUrl = "saveErrorGdgzInfo";
		}

		listJson = new ArrayList<NameValuePair>();

		new Thread(new Runnable() {
			@Override
			public void run() {

				for(int i = 0;i < mErros.size();i++){
					Log.d("lixu", "查询==提交的隐患:"+mErros.get(i));
					listJson.clear();
					listJson.add((NameValuePair)new BasicNameValuePair("jsonRequest",mErros.get(i)));

					final String httpGetData = new httpconnect().httpGetData("pdaMainTask!"+saveUrl+".interface", listJson,
							OfficeRouteSubmitActivity.this);
					Message message = Message.obtain();
					try {

						final JSONObject jsonObject = new JSONObject(httpGetData.toString());
						final String string = jsonObject.getString("info");
						if (jsonObject.getString("result").equals("0")) {

							if(i == mErros.size() - 1){

								message.what = mWhat;

								message.obj = string;
								handler.sendMessage(message);
								return;
							}
						}else{
							message.what = -1;
							message.obj = string;
							handler.sendMessage(message);
							return;
						}
					} catch (JSONException ex) {
						message.what = -1;
						message.obj = "网络连接失败";
						handler.sendMessage(message);
						return;
					}

				}
			}
		}).start();
	}


	/**
	 * 联网上传图片
	 */
	private String requestStr;
	private void processPhoto(final List<PhotoInfoBean> cPhotoList) {
		final Gson gson = new Gson();
		if(cPhotoList == null || cPhotoList.size()  == 0){
			return;
		}
		listJson  = new ArrayList<NameValuePair>();

		if (this.mProgress == null) {
			this.mProgress = ProgressDialog.show(this, "系统提示", "正在上传图片...");
		} else {
			this.mProgress.setMessage("正在上传图片...");
			this.mProgress.show();
		}




		new Thread() {
			@Override
			public void run() {

				for(int i = 0 ; i < cPhotoList.size(); i++){
					Log.d("lixu", "上传图片的信息查询==getPhotoName"+cPhotoList.get(i).getPhotoName());
					Log.d("lixu", "上传图片的信息查询==getPhotoType"+cPhotoList.get(i).getPhotoType());
					Log.d("lixu", "上传图片的信息查询==getResourceType"+cPhotoList.get(i).getResourceType());
					Log.d("lixu", "上传图片的信息查询==getUserId"+cPhotoList.get(i).getUserId());
					Log.d("lixu", "上传图片的信息查询==getID"+cPhotoList.get(i).getID());
					Log.d("lixu", "上传图片的信息查询==getLatitude"+cPhotoList.get(i).getLatitude());
					Log.d("lixu", "上传图片的信息查询==getLongitude"+cPhotoList.get(i).getLongitude());
					Log.d("lixu", "上传图片的信息查询==getRelatedID"+cPhotoList.get(i).getRelatedID());
					Log.d("lixu", "上传图片的信息查询==getRouteID"+cPhotoList.get(i).getRouteID());


					listJson.clear();
					listJson.add((NameValuePair)new BasicNameValuePair("jsonRequest", gson.toJson(cPhotoList.get(i))));

					requestStr = new httpconnect().httpGetData("pdaMainTask!uploadPhotoAndInfo.interface", listJson,
							OfficeRouteSubmitActivity.this);
					Message message = Message.obtain();
					try {

						JSONObject jsonObject = new JSONObject(requestStr.toString());

						final String string = jsonObject.getString("info");
						if (jsonObject.getString("result").equals("0")) {

							if(i == cPhotoList.size() - 1){
								message.what = 96;
								message.obj = string;
								handler.sendMessage(message);
							}


						}else{
							message.what = -1;
							message.obj = string;
							handler.sendMessage(message);
							return;
						}
					} catch (Exception e) {
						e.printStackTrace();
						message.what = -1;
						message.obj = "网络连接失败，请稍候重试!";
						handler.sendMessage(message);
					}
				}

			}
		}.start();
	}

	/**
	 * 最后提交交割路由
	 */
	private void submitRoute(){
		if(mProgress == null){
			mProgress = new ProgressDialog(OfficeRouteSubmitActivity.this);
			mProgress.setMessage("正在提交数据...");
			mProgress.show();
		}else{
			mProgress.show();
		}

		routeInfoBean = editInfo(routeInfoBean);
		routeInfoBean.setRouteID(routeID);
		routeInfoBean.getStartPosition().setRouteID(routeID);
		routeInfoBean.getEndPosition().setRouteID(routeID);
		for(int i = 0;i < routeInfoBean.getLocusPoints().size();i++){
			routeInfoBean.getLocusPoints().get(i).setRouteID(routeID);
		}

		try{
			Toast.makeText(OfficeRouteSubmitActivity.this,"轨迹点个数"+routeInfoBean.getLocusPoints().size(),Toast.LENGTH_SHORT).show();
				/*Log.d("lixu", "轨迹点查询=="+routeInfoBean.getLocusPoints().size());
				Log.d("lixu", "起点查询=="+routeInfoBean.getStartPosition().getResourceName());
				Log.d("lixu", "终点查询=="+routeInfoBean.getEndPosition().getResourceName());
				Log.d("lixu", "查询==getRouteID"+routeInfoBean.getRouteID());
				Log.d("lixu", "查询==getUserId"+routeInfoBean.getUserId());
				Log.d("lixu", "查询==getUserId"+routeInfoBean.getName());
				Log.d("lixu", "查询==getID"+routeInfoBean.getID());
				Log.d("lixu", "查询==ToString"+routeInfoBean.toString());
				Log.d("lixu", "查询==+start"+routeInfoBean.getStartPosition().toString());
				Log.d("lixu", "查询==+End"+routeInfoBean.getEndPosition().toString());*/
		}catch(Exception e){
			Log.d("lixu", "查询==出错了"+e.toString());
		}





		String uid = PreferencesUtils.getString(OfficeRouteSubmitActivity.this, "UID", "");
		String jsonRequest = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss Z").create().toJson(routeInfoBean);
		System.out.println(jsonRequest);



		submited = true;
		//	Toast.makeText(RouteSubmitActivity.this, "预计处理过程持续1到3分钟，请耐心等待...", Toast.LENGTH_SHORT).show();
		final String url = "pdaMainTask!saveTask.interface";


		final ArrayList<NameValuePair> list = new ArrayList<NameValuePair>();

		list.add((NameValuePair)new BasicNameValuePair("jsonRequest",	jsonRequest));

		new Thread(new Runnable() {

			@Override
			public void run() {

				final String httpGetData = new httpconnect().httpGetData(url, list,
						OfficeRouteSubmitActivity.this);
				try {
					final Message message = new Message();
					final JSONObject jsonObject = new JSONObject(httpGetData.toString());
					final String string = jsonObject.getString("info");
					if (jsonObject.getString("result").equals("0")) {
						CacheHelper.getInstance(OfficeRouteSubmitActivity.this).deleteObject(ZSLConst.PREFIX_OF_OFFLINE_ROUTE+officeRouteId);

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
	}


	/**
	 * 转换图片的RouteId
	 * @param photos
	 * @param cCRouteId
	 * @return
	 */
	private List<PhotoInfoBean> changeRouteIdOfPhotos(List<PhotoInfoBean> photos,int cCRouteId){
		if (this.mProgress == null) {
			this.mProgress = ProgressDialog.show(this, "系统提示", "正在初始化图片...");
		} else {
			this.mProgress.setMessage("正在初始化图片...");
			this.mProgress.show();
		}

		if(photos == null || photos.size() == 0 ){
			return null;
		}

		for(int i = 0;i < photos.size();i++){
			photos.get(i).setRouteID(cCRouteId);
		}

		if(this.mProgress != null ){
			this.mProgress.dismiss();
		}

		return photos;
	}


	/**
	 * 转换隐患的routeId
	 * @param erros
	 * @param cCRouteId
	 * @param cType
	 * @return
	 */
	private List<String> changeRouteIdOfErros(List<String> erros,int cCRouteId,String cType){

		try{
			if (this.mProgress == null) {
				this.mProgress = ProgressDialog.show(this, "系统提示", "正在初始化隐患数据...");
			} else {
				this.mProgress.setMessage("正在初始化隐患数据...");
				this.mProgress.show();
			}
			List<String> mErros = new ArrayList<String>();
			Gson mGson= new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss Z").create();
			if(erros == null || erros.size() == 0 ){
				return null;
			}

			if(cType.equals(ApplicationValue.single_list[0])){

				for(int i = 0; i < erros.size(); i++ ){
					try{

						ErrorInfoBean erro_1 = mGson.fromJson(erros.get(i), new TypeToken<ErrorInfoBean>() {}.getType());
						erro_1.setRouteID(cCRouteId);
						mErros.add(mGson.toJson(erro_1));
						erro_1 = null;

					}catch(Exception e){
						Log.d("lixu", "解析异常"+e.toString());
					}
				}


			}else if(cType.equals(ApplicationValue.single_list[1])){


				for(int i = 0; i < erros.size(); i++ ){

					ErrorInfoZzBean erro_2 = mGson.fromJson(erros.get(i), new TypeToken<ErrorInfoZzBean>() {}.getType());
					erro_2.setRouteID(cCRouteId);
					mErros.add(mGson.toJson(erro_2));
					erro_2 = null;

				}

			}else if(cType.equals(ApplicationValue.single_list[2])){

				for(int i = 0; i < erros.size(); i++ ){

					ErrorInfoGdgzBean erro_3 = mGson.fromJson(erros.get(i), new TypeToken<ErrorInfoGdgzBean>() {}.getType());
					erro_3.setRouteID(cCRouteId);
					mErros.add(mGson.toJson(erro_3));
					erro_3 = null;

				}

			}
			if(this.mProgress!=null ){
				this.mProgress.dismiss();
			}

			return mErros;
		}catch(Exception e){
			Log.d("lixu", "隐患转RouTEID异常"+e.toString());
			return null;

		}
	}


}