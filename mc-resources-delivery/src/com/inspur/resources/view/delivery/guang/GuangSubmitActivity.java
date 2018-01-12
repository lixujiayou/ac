package com.inspur.resources.view.delivery.guang;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
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
import com.inspur.resources.utils.MyTimeUtils;
import com.inspur.resources.utils.sq.DataBaseHelper;
import com.inspur.resources.view.delivery.guang.bean.GuangPhotoInfoBean;
import com.inspur.resources.view.delivery.guang.bean.PhotoBean;
import com.inspur.resources.view.delivery.transroute.HiddenTroubleReportActivity2;
import com.inspur.resources.view.delivery.transroute.ZSLConst;
import com.inspur.resources.view.delivery.transroute.bean.ErrorInfoBean;
import com.inspur.resources.view.delivery.transroute.bean.GuangBean;
import com.inspur.resources.view.delivery.transroute.bean.PhotoInfoBean;
import com.inspur.resources.view.delivery.transroute.bean.PointlikeResourceInfoBean;
import com.inspur.resources.view.delivery.transroute.bean.ResourceInfoBean;
import com.inspur.resources.view.delivery.transroute.bean.RouteInfoBean;
import com.yolanda.nohttp.OnUploadListener;
import com.yolanda.nohttp.Response;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import cn.trinea.android.common.util.PreferencesUtils;
import cn.trinea.android.common.util.ToastUtils;

public class GuangSubmitActivity extends BaseActivity {
	private final static int REQUESTCODE_GET_AREA = 1;
	private final static int REQUESTCODE_GET_RESOURCE = 2;
	private final static int REQUESTCODE_LOCATION = 3;
	private static final int REQUESTCODE_TAKE_PHOTO = 4;

	public final static String INTENT_DATA_FLAG = "RouteInfo";
	private EditText city_edit, county_edit, name_edit, mName_edit,erro_edit;
	private TextView tv_time;
	private Button btSave;


	private Spinner isGuang_spinner, isGO_spinner,isErro_spinner,erroType_spinner,isOk_spinner,state_spinner;
	private String isGuang_str,isGo_str,isErro_str,erroType_str,isOk_str,state_str;
	private LinearLayout layout_button;
	private LinearLayout ll_erro;

	public GuangBean routeInfoBean;
	private ArrayAdapter<CharSequence> jgztAdapter3;
	private boolean canEdit = false;
	private boolean submited = false;
	private boolean submitedOK = false;
	private ProgressDialog mProgress;
	private Button submit_button,take_photo_button;
	private boolean isSave = false;

	private SQLiteDatabase db;
	private DataBaseHelper s;

	@Override
	protected void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		setContentView(R.layout.activity_guangjiao_submit);
		setTitle("光交接箱");

		s = new DataBaseHelper(GuangSubmitActivity.this, "USER1.db", null, 1);
		db = s.getWritableDatabase();

		Intent intent = getIntent();
		canEdit = intent.getBooleanExtra("canEdit", false);
		Log.d("qqqqqqq","canEdit=="+canEdit);

		initUI();
		initData(routeInfoBean = (GuangBean) intent.getSerializableExtra(INTENT_DATA_FLAG));

	}

	private void initUI() {
		take_photo_button = (Button) findViewById(R.id.take_photo_button);
		submit_button = (Button) findViewById(R.id.submit_button);
		layout_button = (LinearLayout) findViewById(R.id.layout_button);
		ll_erro = (LinearLayout) findViewById(R.id.ll_erro);

		city_edit = (EditText) findViewById(R.id.qsd_edit);
		county_edit = (EditText) findViewById(R.id.zzd_edit);
		name_edit = (EditText) findViewById(R.id.jgr_edit);
		mName_edit = (EditText) findViewById(R.id.main_name_edit);
		erro_edit = (EditText) findViewById(R.id.info_edit);
		tv_time = (TextView) findViewById(R.id.time_edit);
		btSave = (Button) findViewById(R.id.save_button);

		isGuang_spinner = (Spinner) findViewById(R.id.lyzt_spinner);
		ArrayAdapter<CharSequence> jjcdAdapter = ArrayAdapter.createFromResource(this, R.array.isy,
				android.R.layout.simple_dropdown_item_1line);
		isGuang_spinner.setAdapter(jjcdAdapter);

		isGuang_spinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
									   int position, long id) {
				if(position == 1){
					isGuang_str =  "是";
				}else if(position == 2){
					isGuang_str =  "否";
				}else{
					isGuang_str =  "";
				}
				Log.d("lixu","选择主干广角---"+isGuang_str);
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub

			}
		});

		isGO_spinner = (Spinner) findViewById(R.id.isok_spinner);
		ArrayAdapter<CharSequence> jgztAdapter = ArrayAdapter.createFromResource(this, R.array.isy,
				android.R.layout.simple_dropdown_item_1line);
		isGO_spinner.setAdapter(jgztAdapter);

		isGO_spinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
									   int position, long id) {
				// TODO Auto-generated method stub
				if(position == 1){
					isGo_str =  "是";
				}else if(position == 2){
					isGo_str =  "否";
				}else{
					isGo_str =  "";
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub

			}
		});

		erroType_spinner = (Spinner) findViewById(R.id.type_spinner);
		jgztAdapter3 = ArrayAdapter.createFromResource(this, R.array.erro_type,
				android.R.layout.simple_dropdown_item_1line);
		erroType_spinner.setAdapter(jgztAdapter3);
		erroType_spinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
									   int position, long id) {
				// TODO Auto-generated method stub
				if(position == 1){
					erroType_str ="尾纤使用或盘放不标准";
				}else if(position == 2){
					erroType_str ="无防雷接地";
				}else if(position == 3){
					erroType_str ="法兰盘角度装反或法兰帽缺失";
				}else{
					erroType_str = "";
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub

			}
		});





		isErro_spinner = (Spinner) findViewById(R.id.iserro_spinner);
		ArrayAdapter<CharSequence> jgztAdapter2 = ArrayAdapter.createFromResource(this, R.array.isy,
				android.R.layout.simple_dropdown_item_1line);
		isErro_spinner.setAdapter(jgztAdapter2);
		isErro_spinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
									   int position, long id) {
				// TODO Auto-generated method stub
				if(position == 1){
					isErro_str =  "是";
					ll_erro.setVisibility(View.VISIBLE);


				}else if(position == 2){
					isErro_str =  "否";
					erroType_str = "";
					ll_erro.setVisibility(View.GONE);
					//隐患类型清空
					erroType_spinner.setSelection(0,true);
					erroType_str = "";
					//隐患描述
					erro_edit.setText("");



				}else{
					isErro_str =  "";
					erroType_str = "";
					ll_erro.setVisibility(View.GONE);
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub

			}
		});


		isOk_spinner = (Spinner) findViewById(R.id.is_spinner);
		ArrayAdapter<CharSequence> jgztAdapter4 = ArrayAdapter.createFromResource(this, R.array.isy,
				android.R.layout.simple_dropdown_item_1line);
		isOk_spinner.setAdapter(jgztAdapter4);

		isOk_spinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
									   int position, long id) {

				if(position == 1){
					isOk_str = "是";
				}else if(position == 2){
					isOk_str = "否";
					//整改日期
					tv_time.setText("");
				}else{
					isOk_str = "";
				}

			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub

			}
		});

		state_spinner = (Spinner) findViewById(R.id.state_spinner);
		ArrayAdapter<CharSequence> jgztAdapter5 = ArrayAdapter.createFromResource(this, R.array.state,
				android.R.layout.simple_dropdown_item_1line);
		state_spinner.setAdapter(jgztAdapter5);

		state_spinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
									   int position, long id) {

				if(position == 1){
					state_str = "通过";
				}else if(position == 2){
					state_str = "不通过";
				}else if(position == 3){
					state_str = "";
				}else{
					state_str = "";
				}


			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub

			}
		});

	/*	lyzt_spinner.setOnItemSelectedListener(new OnItemSelectedListener() {

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
		});*/

		tv_time.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				Calendar c = Calendar.getInstance();
				// 直接创建一个DatePickerDialog对话框实例，并将它显示出来
				new DatePickerDialog(GuangSubmitActivity.this,
						// 绑定监听器
						new DatePickerDialog.OnDateSetListener() {

							@Override
							public void onDateSet(DatePicker view, int year,
												  int monthOfYear, int dayOfMonth) {
								int mMonth = monthOfYear + 1;
								tv_time.setText(year+"/"+mMonth+"/"+dayOfMonth);

							}
						}
						// 设置初始日期
						, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c
						.get(Calendar.DAY_OF_MONTH)).show();



//		  new DatePickerDialog(GuangSubmitActivity.this, new OnDateSetListener() {
//
//			@Override
//			public void onDateSet(DatePicker view, int year, int monthOfYear,
//					int dayOfMonth) {
//				// TODO Auto-generated method stub
//
//			}
//		}, c.get(Calendar.YEAR),  c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH))
//		  .show();

//		  new DatePickerDialog(GuangSubmitActivity.this, new OnDateSetListener() {
//
//			@Override
//			public void onDateSet(DatePicker view, int year, int monthOfYear,
//					int dayOfMonth) {
//
//
//			}
//		}, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c
//      .get(Calendar.DAY_OF_MONTH)).show();
				// 直接创建一个DatePickerDialog对话框实例，并将它显示出来

			}
		});

		if(!canEdit){
			city_edit.setEnabled(false);
			county_edit.setEnabled(false);
			name_edit.setEnabled(false);
			mName_edit.setEnabled(false);
			erro_edit.setEnabled(false);
			tv_time.setEnabled(false);

			isGuang_spinner.setEnabled(false);
			isGO_spinner.setEnabled(false);
			state_spinner.setEnabled(false);
			isOk_spinner.setEnabled(false);
			erroType_spinner.setEnabled(false);
			isErro_spinner.setEnabled(false);

			btSave.setVisibility(View.GONE);
		}

	}

	private void initData(GuangBean routeInfo) {




		if(routeInfo.getCity()!=null){
			city_edit.setText(routeInfo.getCity());
		}
		if(routeInfo.getCounty()!=null){
			county_edit.setText(routeInfo.getCounty());
		}
		name_edit.setText(routeInfo.getGjName());
		mName_edit.setText(routeInfo.getJrkhName());
		erro_edit.setText(routeInfo.getYhMs());
		tv_time.setText(routeInfo.getZgDate());


		//是否为主干光交
		if(routeInfo.getIsZg() != null || !routeInfo.getIsZg().isEmpty()){
			if(routeInfo.getIsZg().equals("是")){
				isGuang_spinner.setSelection(1);
			}else if(routeInfo.getIsZg().equals("否")){
				isGuang_spinner.setSelection(2);
			}
		}

		//是否直接接入客户
		if(routeInfo.getIsJrkh() != null || !routeInfo.getIsJrkh().isEmpty()){
			if(routeInfo.getIsJrkh().equals("是")){
				isGO_spinner.setSelection(1);
			}else if(routeInfo.getIsJrkh().equals("否")){
				isGO_spinner.setSelection(2);
			}
		}

		//是否有隐患
		if(routeInfo.getIsYh() != null || !routeInfo.getIsYh().isEmpty()){
			if(routeInfo.getIsYh().equals("是")){
				isErro_spinner.setSelection(1);
			}else if(routeInfo.getIsYh().equals("否")){
				isErro_spinner.setSelection(2);
			}
		}

		//隐患类型
		Log.d("lixu","------隐患类型==="+routeInfo.getYhType());
		if(routeInfo.getYhType() != null || !routeInfo.getYhType().isEmpty()){
			if(routeInfo.getYhType().equals("尾纤使用或盘放不标准")){
				erroType_spinner.setSelection(1);
			}else if(routeInfo.getYhType().equals("无防雷接地")){
				erroType_spinner.setSelection(2);
			}else if(routeInfo.getYhType().equals("法兰盘角度装反或法兰帽缺失")){
				erroType_spinner.setSelection(3);
			}
		}

//        是否整改完成
		if(routeInfo.getIsZgWc() != null || !routeInfo.getIsZgWc().isEmpty()){
			if(routeInfo.getIsZgWc().equals("是")){
				isOk_spinner.setSelection(1);
			}else if(routeInfo.getIsZgWc().equals("否")){
				isOk_spinner.setSelection(2);
			}
		}

//     交割状态
		if(routeInfo.getJgStatus() !=null || routeInfo.getJgStatus().isEmpty()){
			if(routeInfo.getJgStatus().equals("通过")){
				state_spinner.setSelection(1);
			}else if(routeInfo.getJgStatus().equals("不通过")) {
				state_spinner.setSelection(2);
			}else if(routeInfo.getJgStatus().equals("未交割")){
				state_spinner.setSelection(3);
			}
		}
	}


	private GuangBean editInfo(GuangBean routeInfo) {

		routeInfo.setCity(city_edit.getText().toString().toString());
		routeInfo.setCounty(county_edit.getText().toString().trim());
		routeInfo.setGjName(name_edit.getText().toString().trim());
		routeInfo.setIsZg(isGuang_str);
		routeInfo.setIsJrkh(isGo_str);
		routeInfo.setJrkhName(mName_edit.getText().toString().trim());
		routeInfo.setIsYh(isErro_str);
		routeInfo.setYhType(erroType_str);
		routeInfo.setYhMs(erro_edit.getText().toString().trim());
		routeInfo.setIsZgWc(isOk_str);
		routeInfo.setCzr(PreferencesUtils.getString(GuangSubmitActivity.this, "UID", ""));
		if(!isOk_str.equals("否")) {
			routeInfo.setZgDate(MyTimeUtils.getCurrentTime());
		}else{

		}
		routeInfo.setJgStatus(state_str);

		return routeInfo;

	}

	// 保存本地
	public void cs_save(View v) {
		if(!isOK()){
			return;
		}
		getData(2);
	}

	public boolean isKong(String s){
		Log.d("lixu", "判断"+s);
		if(s == null || s.isEmpty()){
			return true;
		}
		return false;
	}

	public boolean isOK(){
		if(isKong(isGuang_str)){
			Toast.makeText(GuangSubmitActivity.this, "请选择“是否主干光交”", Toast.LENGTH_SHORT).show();
			return false;
		}

		Log.d("lixu", "----");
		if(isKong(state_str)){
			Toast.makeText(GuangSubmitActivity.this, "请选择“交割状态”", Toast.LENGTH_SHORT).show();
			return false;
		}
		if(isKong(isOk_str)){
			Toast.makeText(GuangSubmitActivity.this, "请选择“是否整改完成”", Toast.LENGTH_SHORT).show();
			return false;
		}
		if(isKong(isGo_str)){
			Toast.makeText(GuangSubmitActivity.this, "请选择“是否直接进入客户”", Toast.LENGTH_SHORT).show();
			return false;
		}
		if(isKong(isErro_str)){
			Toast.makeText(GuangSubmitActivity.this, "请选择“是否有隐患”", Toast.LENGTH_SHORT).show();
			return false;
		}else{
			if(isErro_str.equals("是")){
				if(isKong(erroType_str)){
					Toast.makeText(GuangSubmitActivity.this, "请选择“隐患类型”", Toast.LENGTH_SHORT).show();
					return false;
				}
			}
		}

		if(isGuang_str != null || !isGuang_str.isEmpty()){
			Log.d("lixu", "11");
			if(isGuang_str.equals("是")){
				Log.d("lixu", "12");
				if(mName_edit.getText().toString().trim() ==null ||mName_edit.getText().toString().trim().isEmpty()){
					Log.d("lixu", "13");
					Toast.makeText(GuangSubmitActivity.this, "是否主干光交选择是时，接入主干光交接箱客户名称必填", Toast.LENGTH_SHORT).show();
					return false;
				}
			}
		}





		if(isErro_str != null || !isErro_str.isEmpty()){
			if(isErro_str.equals("是")){
				if(erro_edit.getText().toString().trim() ==null ||erro_edit.getText().toString().trim().isEmpty()){
					Toast.makeText(GuangSubmitActivity.this, "是否有隐患选是时，隐患描述必填", Toast.LENGTH_SHORT).show();
					return false;
				}
			}
		}



		return true;
	}
	// 提交
	public void cs_submit(View v) {
		if(!canEdit){
			ToastUtils.show(GuangSubmitActivity.this, "不能重复提交");
			finish();
			return;
		}
		getData(1);
	}




	private void init(){

		if(!isOK()){
			return;
		}

		routeInfoBean = editInfo(routeInfoBean);

		Log.d("qqqqqq","routeInfoBean=="+routeInfoBean.getID());

		String uid = PreferencesUtils.getString(GuangSubmitActivity.this, "UID", "");
		String jsonRequest = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss Z").create().toJson(routeInfoBean);
		System.out.println(jsonRequest);
		Map<String,String> paramMap = new HashMap<String, String>();
		paramMap.put("jsonRequest", jsonRequest);
		submited = true;
		String url ;

		if (this.mProgress == null) {
			this.mProgress = ProgressDialog.show(this, "系统提示", "正在提交数据...");
			this.mProgress.setCancelable(true);
		} else {
			this.mProgress.setMessage("正在提交数据...");
			this.mProgress.show();
		}

		Gson gson = new Gson();

		final ArrayList<NameValuePair> list = new ArrayList<NameValuePair>();

		list.add((NameValuePair)new BasicNameValuePair("jsonRequest",	gson.toJson(routeInfoBean)));

		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				final String httpGetData = new httpconnect().httpGetData("pdaMainTask!saveGjJgInfo.interface", list,
						GuangSubmitActivity.this);
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

	private void getData(final int type){

		if (this.mProgress == null) {
			this.mProgress = ProgressDialog.show(this, "系统提示", "正在初始化数据...");
		} else {
			this.mProgress.setMessage("正在初始化数据...");
			this.mProgress.show();
		}

		new Thread(new Runnable() {
			@Override
			public void run() {
				final ArrayList<NameValuePair> list = new ArrayList<NameValuePair>();
				//	String json = "{\"gjId\":"+routeInfoBean.getIntId()+"}";
				PhotoBean bean = new PhotoBean();
				bean.setRouteId("");
				bean.setRelatedID(routeInfoBean.getIntId());
				bean.setPhotoType(ZSLConst.PHOTO_TYPE_GJ);
				Gson json = new Gson();

				list.add((NameValuePair)new BasicNameValuePair("jsonRequest",json.toJson(bean)));

				final String httpGetData = new httpconnect().httpGetData("pdaMainTask!getPhotoById.interface", list,
						GuangSubmitActivity.this);

				if (httpGetData == null) {
					if ("".equals(httpGetData)) {
						return;
					}
				}

				try {
					final Message message = new Message();
					final JSONObject jsonObject = new JSONObject(httpGetData.toString());
					final String string = jsonObject.getString("info");

					String jsonInfo = jsonObject.getString("info");
					if (jsonObject.getString("result").equals("0")) {

						List<PhotoInfoBean> list2 = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss Z").create()
								.fromJson(jsonInfo, new TypeToken<List<PhotoInfoBean>>() {
								}.getType());

						if(list2!=null && list2.size()!=0){

							if(type == 1){
								message.what = 5;
							}else{
								message.what = 6;
							}
							message.obj = string;
							handler.sendMessage(message);
							return;
						}


						message.what = -1;
						message.obj = "您还未拍照";
						handler.sendMessage(message);

					}
					message.what = -1;
					message.obj = "您还未拍照";
					handler.sendMessage(message);
				} catch (JSONException ex) {
					Message message =Message.obtain();
					message.what = -1;
					message.obj = "您还未拍照";
					handler.sendMessage(message);
				}

			}
		}).start();

	}



	/*public void onBackPressed() {
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
		AlertDialog ad = new AlertDialog.Builder(GuangSubmitActivity.this).setTitle("温馨提示").setMessage(msg)
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
	}*/

	public void cs_refresh(View v){
		if(canEdit&&!submited){
			Toast.makeText(GuangSubmitActivity.this, "您尚未提交计算,请先提交计算", Toast.LENGTH_SHORT).show();
			return;
		}
		loadRouteInfo(String.valueOf(routeInfoBean.getIntId()));
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
						GuangSubmitActivity.this);
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

	private Handler handler = new Handler(new Handler.Callback() {

		public boolean handleMessage(Message msg) {
			if (mProgress != null) {
				mProgress.dismiss();
			}
			switch (msg.what) {
				case 1:
					GuangBean routeInfoBean = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss Z").create()
							.fromJson(msg.obj.toString(), GuangBean.class);
					initData(routeInfoBean);
					break;
				case 2:
					Toast.makeText(GuangSubmitActivity.this, msg.obj.toString(), Toast.LENGTH_SHORT).show();
					break;
				case -1:
					Toast.makeText(GuangSubmitActivity.this, "请拍照后再进行提交.", Toast.LENGTH_LONG).show();
					break;
				case 4:
					isSave = true;
					Toast.makeText(GuangSubmitActivity.this, "提交成功", Toast.LENGTH_SHORT).show();
					finish();
					break;
				case 5:
					init();
					break;
				case 6:
					saveThis();
					break;
			}
			return true;
		}
	});


	private void saveThis(){
		routeInfoBean = editInfo(routeInfoBean);

		String jsonRequest = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create().toJson(routeInfoBean);
		ContentValues cv = new ContentValues();
		cv.put("json",jsonRequest);
		db.insert("user1", null, cv);
		Toast.makeText(GuangSubmitActivity.this, "保存成功", Toast.LENGTH_SHORT).show();
		setResult(19);
		finish();
	}

	//  标识点击拍照后是否已返回（返回后才可以提交）
	private boolean startPhotoBacked = true;
	// 拍照
	public void cs_take_photo(View v) {
		if(canEdit){
			startPhotoBacked = false;
			Intent intent = new Intent(GuangSubmitActivity.this, PhotoShopeActivityGuang.class);
			intent.putExtra("mPhotoType", ZSLConst.PHOTO_TYPE_GJ);
			intent.putExtra("routeId", 0);
			intent.putExtra("lat", ZSLConst.curLocation.getLatitude());
			intent.putExtra("lon", ZSLConst.curLocation.getLongitude());
			intent.putExtra("relatedId", routeInfoBean.getIntId());
			intent.putExtra("resourceType", ApplicationValue.GuangJiao);
			startActivityForResult(intent, REQUESTCODE_TAKE_PHOTO);
		}else{
			Toast.makeText(GuangSubmitActivity.this, "已提交数据不能进行拍照", Toast.LENGTH_SHORT).show();
			finish();
		}
	}


	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK){
			if(requestCode == REQUESTCODE_TAKE_PHOTO){
				ArrayList<PhotoInfoBean> photoList = (ArrayList<PhotoInfoBean>) data.getSerializableExtra("photos");
				System.out.println("photoList="+photoList);
				startPhotoBacked = true;
				if(photoList==null||photoList.size()==0){
					return;
				}
				if(routeInfoBean.getPhoto()==null){
					routeInfoBean.setPhoto(new ArrayList<PhotoInfoBean>());
				}
				routeInfoBean.getPhoto().addAll(photoList);
			}

		}
	}
}