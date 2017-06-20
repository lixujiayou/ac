package com.inspur.resources.view.delivery.offline;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.CoordinateConverter;
import com.baidu.mapapi.utils.DistanceUtil;
import com.baidu.mapapi.utils.CoordinateConverter.CoordType;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.inspur.StringUtils;
import com.inspur.common.HttpListener;
import com.inspur.common.RegionWheelDialog;
import com.inspur.common.RequestTool;
import com.inspur.easyresources.R;
import com.inspur.resources.base.BaseActivity;
import com.inspur.resources.http.httpconnect;
import com.inspur.resources.utils.ApplicationValue;
import com.inspur.resources.utils.sq.MySQLHelper;
import com.inspur.resources.view.delivery.guang.GuangSubmitActivity;
import com.inspur.resources.view.delivery.guang.MapTemp;
import com.inspur.resources.view.delivery.guang.PhotoShopeActivityGuang;
import com.inspur.resources.view.delivery.guang.bean.PhotoBean;
import com.inspur.resources.view.delivery.guang.bean.Resours;
import com.inspur.resources.view.delivery.offline.PhotoOfficeActivity;
import com.inspur.resources.view.delivery.transroute.DeliveryResourceSearchListActivity;
import com.inspur.resources.view.delivery.transroute.DeliveryResourceSearchListActivity2;
import com.inspur.resources.view.delivery.transroute.JingSelectActivity;
import com.inspur.resources.view.delivery.transroute.ZSLConst;
import com.inspur.resources.view.delivery.transroute.bean.ErrorInfoBean;
import com.inspur.resources.view.delivery.transroute.bean.ErrorInfoGdgzBean;
import com.inspur.resources.view.delivery.transroute.bean.ErrorInfoZzBean;
import com.inspur.resources.view.delivery.transroute.bean.GuangBean;
import com.inspur.resources.view.delivery.transroute.bean.PhotoInfoBean;
import com.inspur.resources.view.delivery.transroute.bean.ResourceInfoBean;
import com.inspur.resources.view.map.LocationOverlayDemo;
import com.yolanda.nohttp.OnUploadListener;
import com.yolanda.nohttp.Response;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.ContactsContract.Contacts.Data;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import cn.trinea.android.common.util.PreferencesUtils;
import cn.trinea.android.common.util.ToastUtils;

public class OfficeHiddenTroubleActivity extends BaseActivity {

	public final static String INTENT_DATA_FLAG = "ErrorInfo";
	private final static int REQUESTCODE_GET_AREA = 1;
	private final static int REQUESTCODE_GET_RESOURCE = 2;
	private final static int REQUESTCODE_LOCATION = 3;
	private static final int REQUESTCODE_TAKE_PHOTO = 4;

	//管道故障相关
	private TextView tv_start_jing,tv_end_jing,tv_start_location,tv_end_location;
	private ImageView iv_start_jing,iv_end_jing;
	private EditText et_lenge,et_lenge_auto;
	private TextView tv_u_name;

	private EditText company_edit;//地市
	private EditText sbr_edit,qy_edit, jd_edit, wd_edit, yhms_edit;//地市公司
	private TextView tv_time;
	private Spinner jjcd_spinner;
	private String[] troubleSp ;

	private Spinner jjcd_spinner_2;//问题小类
	private ArrayAdapter<CharSequence> jjcdAdapter2;
	private String str_x,str_d;
	private String[] troubleSp1;

	private Spinner isChange_spinner;//是否整改
	private String str_isChange;

	private Spinner sp_dataErroType;
	private String str_dataErroType;

	private Spinner sp_dataErroType_2;
	private String  str_dataErroType_2;


	private ErrorInfoBean errorInfoBean;//资源隐患
	private ErrorInfoGdgzBean errorInfoBeanGd;//管道故障问题
	private ErrorInfoZzBean errorInfoBeanZz;//综资数据问题



	private boolean isHavePhoto = false;//是否已拍照



	private boolean isUpdate = false;
	private int routeID = 0;
	//标识点击拍照后是否已返回（返回后才可以提交）
	private boolean startPhotoBacked = true;

	private LinearLayout ll_gone,ll_gone_1,ll_gone_2,ll_gone_6,ll_wang_gone_1,ll_wang_gone_2;
	private TextView tv_change_1,tv_change_2;
	private boolean erroType;//隐患类型
	private ImageView iv_time;
	private ResourceInfoBean resourceInfoBean;

	private ImageView iv_wang,iv_guang;
	private TextView tv_wang,tv_guang;
	private String cType;

	private ResourceInfoBean JingStart;//起点井
	private ResourceInfoBean JingEnd;//重点井
	private double JingDis;//两井距离

	@SuppressLint("NewApi") @Override
	protected void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
		setContentView(R.layout.activity_hiddentroublereport_change_office_2);
		setTitle("隐患上报");

		final Intent intent = this.getIntent();
		isUpdate = intent.getBooleanExtra("isUpdate", false);
		routeID = intent.getIntExtra("routeID", 0);
		cType = intent.getExtras().getString("type");


		if(isUpdate){
		}

		if(cType.equals(ApplicationValue.single_list[0])){

			if (isUpdate) {

				errorInfoBean = (ErrorInfoBean) intent.getSerializableExtra(INTENT_DATA_FLAG);

				if(errorInfoBean.getResourceID() != null ){

					resourceInfoBean = new ResourceInfoBean();

					try{
						resourceInfoBean.setResourceID(errorInfoBean.getResourceID());
					}catch(Exception e){
					}
					resourceInfoBean.setResourceType(errorInfoBean.getResourceType());
				}
			}else{
				resourceInfoBean =  (ResourceInfoBean) intent.getSerializableExtra("bean");
				errorInfoBean = new ErrorInfoBean();
				errorInfoBean.setRouteID(routeID);

				if(ZSLConst.curGpsLocation!=null){
					errorInfoBean.setLatitude(ZSLConst.curGpsLocation.getLatitude());
					errorInfoBean.setLongitude(ZSLConst.curGpsLocation.getLongitude());
				}
			}

		}else if(cType.equals(ApplicationValue.single_list[1])){

			if (isUpdate) {
				errorInfoBean = (ErrorInfoBean) intent.getSerializableExtra(INTENT_DATA_FLAG);
				if(errorInfoBean.getResourceID() != null ){
					resourceInfoBean.setResourceID(errorInfoBean.getResourceID());
					resourceInfoBean.setResourceType(errorInfoBean.getResourceType());
				}
			}else{
				resourceInfoBean =  (ResourceInfoBean) intent.getSerializableExtra("bean");
				errorInfoBeanZz = new ErrorInfoZzBean();
				errorInfoBeanZz.setRouteID(routeID);
			}

		}else if(cType.equals(ApplicationValue.single_list[2])){


			if (isUpdate) {
				errorInfoBean = (ErrorInfoBean) intent.getSerializableExtra(INTENT_DATA_FLAG);
				if(errorInfoBean.getResourceID() != null ){
					resourceInfoBean.setResourceID(errorInfoBean.getResourceID());
					resourceInfoBean.setResourceType(errorInfoBean.getResourceType());
				}
			}else{
				resourceInfoBean =  (ResourceInfoBean) intent.getSerializableExtra("bean");
				errorInfoBeanGd = new ErrorInfoGdgzBean();
				errorInfoBeanGd.setRouteID(routeID);


			}

		}



		initUI();


		if(cType != null){
			if(cType.equals("资源隐患")){
				ll_gone.setVisibility(View.VISIBLE);
				ll_gone_1.setVisibility(View.VISIBLE);
				ll_gone_2.setVisibility(View.GONE);
				ll_gone_6.setVisibility(View.GONE);
				tv_change_1.setText("是否整改");
				tv_change_2.setText("整改日期");
				initData(errorInfoBean);
			}else if(cType.equals("综资数据")){
				ll_gone.setVisibility(View.VISIBLE);
				ll_gone_1.setVisibility(View.GONE);
				ll_gone_2.setVisibility(View.VISIBLE);
				ll_gone_6.setVisibility(View.GONE);
				tv_change_1.setText("是否完成数据修改");
				tv_change_2.setText("数据修改日期");
			}else if(cType.equals(ApplicationValue.single_list[2])){
				ll_gone.setVisibility(View.GONE);
				ll_gone_6.setVisibility(View.VISIBLE);
				ll_gone_2.setVisibility(View.GONE);
				ll_gone_1.setVisibility(View.GONE);
			}
		}
	}

	private void initUI() {
		sbr_edit = (EditText) findViewById(R.id.sbr_edit);//隐患位置
		qy_edit = (EditText) findViewById(R.id.qy_edit);	//区县
		jd_edit = (EditText) findViewById(R.id.jd_edit);	//经度
		wd_edit = (EditText) findViewById(R.id.wd_edit);	//纬度
		yhms_edit = (EditText) findViewById(R.id.yhms_edit);//隐患描述
		company_edit = (EditText) findViewById(R.id.company_edit);		//地市公司
		isChange_spinner = (Spinner) findViewById(R.id.ischange_spinner);//是否整改
		tv_time = (TextView) findViewById(R.id.tv_time);			//整改时间
		iv_time = (ImageView) findViewById(R.id.time_button);

		ll_gone = (LinearLayout) findViewById(R.id.ll_gone);

		ll_gone_1 = (LinearLayout) findViewById(R.id.ll_gone_1);
		ll_gone_2 = (LinearLayout) findViewById(R.id.ll_gone_2);
		ll_gone_6 = (LinearLayout) findViewById(R.id.ll_gone_6);

		tv_change_1 = (TextView) findViewById(R.id.tv_change_1);
		tv_change_2 = (TextView) findViewById(R.id.tv_change_2);
		ll_wang_gone_1 = (LinearLayout) findViewById(R.id.ll_wang_gone_1);
		ll_wang_gone_2 = (LinearLayout) findViewById(R.id.ll_wang_gone_2);

		sp_dataErroType_2 = (Spinner) findViewById(R.id.date_errot_ype_spinner);


		sp_dataErroType = (Spinner) findViewById(R.id.date_erro_spinner);

		Log.i("lixu", "-------初始化数据1");
		iv_wang = (ImageView) findViewById(R.id.iv_wangyuan);
		iv_guang = (ImageView) findViewById(R.id.iv_guang);
		tv_wang = (TextView) findViewById(R.id.tv_wangyuan);
		tv_guang = (TextView) findViewById(R.id.tv_xianlu);

		troubleSp = getResources().getStringArray(R.array.trouble_b);
		troubleSp1 = getResources().getStringArray(R.array.trouble_b_1);

		jjcd_spinner_2 = (Spinner) findViewById(R.id.jjcd_spinner_2);//问题小类
		jjcd_spinner = (Spinner) findViewById(R.id.jjcd_spinner);		//问题大类

		tv_start_jing = (TextView) findViewById(R.id.tv_jing_start);
		tv_end_jing = (TextView) findViewById(R.id.tv_jing_end);
		tv_start_location = (TextView) findViewById(R.id.tv_start_location);
		tv_end_location = (TextView) findViewById(R.id.tv_end_location);
		iv_start_jing = (ImageView) findViewById(R.id.iv_jing_start);
		iv_end_jing = (ImageView) findViewById(R.id.iv_jing_end);
		tv_u_name = (TextView) findViewById(R.id.login_name_edit);
		et_lenge = (EditText) findViewById(R.id.lenge_edit);
		et_lenge_auto = (EditText) findViewById(R.id.sys_lenge_edit);

		String uid = PreferencesUtils.getString(OfficeHiddenTroubleActivity.this, "UID", "");
		tv_u_name.setText(uid);

		//资源隐患
		if(cType.equals(ApplicationValue.single_list[0]) || isUpdate){
			Log.i("lixu", "-------初始化数据2");
			//问题大类小类
			ArrayAdapter<CharSequence> jjcdAdapter = ArrayAdapter.createFromResource(this, R.array.trouble_b,
					android.R.layout.simple_dropdown_item_1line);

			jjcd_spinner.setAdapter(jjcdAdapter);
			jjcd_spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
				@Override
				public void onItemSelected(AdapterView<?> parent, View view,int position, long id) {
					switch (position) {
						case 0:
							troubleSp1 = getResources().getStringArray(R.array.trouble_b_1);
							jjcdAdapter2 = ArrayAdapter.createFromResource(OfficeHiddenTroubleActivity.this, R.array.trouble_b_1,
									android.R.layout.simple_dropdown_item_1line);
							break;
						case 1:
							troubleSp1 = getResources().getStringArray(R.array.trouble_b_2);
							jjcdAdapter2 = ArrayAdapter.createFromResource(OfficeHiddenTroubleActivity.this, R.array.trouble_b_2,
									android.R.layout.simple_dropdown_item_1line);
							break;
						case 2:
							troubleSp1 = getResources().getStringArray(R.array.trouble_b_3);
							jjcdAdapter2 = ArrayAdapter.createFromResource(OfficeHiddenTroubleActivity.this, R.array.trouble_b_3,
									android.R.layout.simple_dropdown_item_1line);
							break;
						case 3:
							troubleSp1 = getResources().getStringArray(R.array.trouble_b_4);
							jjcdAdapter2 = ArrayAdapter.createFromResource(OfficeHiddenTroubleActivity.this, R.array.trouble_b_4,
									android.R.layout.simple_dropdown_item_1line);
							break;
					}

					str_d = troubleSp[position];
					str_x = troubleSp1[0];
					jjcd_spinner_2.setAdapter(jjcdAdapter2);

				}

				@Override
				public void onNothingSelected(AdapterView<?> parent) {
					// TODO Auto-generated method stub

				}
			});


			troubleSp1 = getResources().getStringArray(R.array.trouble_b_1);
			jjcdAdapter2 = ArrayAdapter.createFromResource(OfficeHiddenTroubleActivity.this, R.array.trouble_b_1,
					android.R.layout.simple_dropdown_item_1line);
			jjcd_spinner_2.setAdapter(jjcdAdapter2);
			str_x = troubleSp1[0];
			jjcd_spinner_2.setOnItemSelectedListener(new OnItemSelectedListener() {

				@Override
				public void onItemSelected(AdapterView<?> parent, View view,
										   int position, long id) {
					str_x = troubleSp1[position];

				}

				@Override
				public void onNothingSelected(AdapterView<?> parent) {
					// TODO Auto-generated method stub

				}
			});


			//是否整改
			ArrayAdapter<CharSequence> jjcdAdapter3 = ArrayAdapter.createFromResource(this, R.array.is_change,
					android.R.layout.simple_dropdown_item_1line);
			isChange_spinner.setAdapter(jjcdAdapter3);
			str_isChange = "否";
			isChange_spinner.setOnItemSelectedListener(new OnItemSelectedListener() {

				@Override
				public void onItemSelected(AdapterView<?> parent, View view,
										   int position, long id) {

					if(position == 0){
						str_isChange = "否";
					}else{
						str_isChange = "是";
					}
				}

				@Override
				public void onNothingSelected(AdapterView<?> parent) {
				}
			});


			iv_time.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					Calendar c = Calendar.getInstance();
					// 直接创建一个DatePickerDialog对话框实例，并将它显示出来
					new DatePickerDialog(OfficeHiddenTroubleActivity.this,
							// 绑定监听器
							new DatePickerDialog.OnDateSetListener() {

								@Override
								public void onDateSet(DatePicker view, int year,
													  int monthOfYear, int dayOfMonth) {
									tv_time.setText(year+"-"+monthOfYear+"-"+dayOfMonth);
			                    	/*  Date date = new Date(year, monthOfYear, dayOfMonth);
			                    	  tv_time.setText(date.toString());*/

								}
							}
							// 设置初始日期
							, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c
							.get(Calendar.DAY_OF_MONTH)).show();

				}
			});

		}else if(cType.equals(ApplicationValue.single_list[1])){
			//数据不准确资源类别
			ArrayAdapter jjcdAdapter5 = ArrayAdapter.createFromResource(OfficeHiddenTroubleActivity.this, R.array.data_erro_type,
					android.R.layout.simple_dropdown_item_1line);
			sp_dataErroType.setAdapter(jjcdAdapter5);
			str_dataErroType = "传输网元";
			sp_dataErroType.setOnItemSelectedListener(new OnItemSelectedListener() {

				@Override
				public void onItemSelected(AdapterView<?> parent, View view,
										   int position, long id) {
					if(position == 0){
						str_dataErroType = "传输网元";
						ll_wang_gone_1.setVisibility(View.VISIBLE);
						ll_wang_gone_2.setVisibility(View.GONE);
					}else{
						str_dataErroType = "光缆段";
						ll_wang_gone_1.setVisibility(View.GONE);
						ll_wang_gone_2.setVisibility(View.VISIBLE);
					}


				}

				@Override
				public void onNothingSelected(AdapterView<?> parent) {
					// TODO Auto-generated method stub

				}
			});
			//是否完成整改
			ArrayAdapter<CharSequence> jjcdAdapter3 = ArrayAdapter.createFromResource(this, R.array.is_change,
					android.R.layout.simple_dropdown_item_1line);
			isChange_spinner.setAdapter(jjcdAdapter3);
			str_isChange = "否";
			isChange_spinner.setOnItemSelectedListener(new OnItemSelectedListener() {

				@Override
				public void onItemSelected(AdapterView<?> parent, View view,
										   int position, long id) {
					// TODO Auto-generated method stub
					if(position == 0){
						str_isChange = "否";
					}else{
						str_isChange = "是";
					}

				}

				@Override
				public void onNothingSelected(AdapterView<?> parent) {
				}
			});



			//数据不准确归类
			ArrayAdapter<CharSequence> jjcdAdapter6 = ArrayAdapter.createFromResource(this, R.array.data_erro_type_2,
					android.R.layout.simple_dropdown_item_1line);
			str_dataErroType_2 = "字段缺失";
			sp_dataErroType_2.setAdapter(jjcdAdapter6);
			sp_dataErroType_2.setOnItemSelectedListener(new OnItemSelectedListener() {

				@Override
				public void onItemSelected(AdapterView<?> parent, View view,
										   int position, long id) {
					if(position == 0){
						str_dataErroType_2 = "字段缺失";
					}else if(position == 1){
						str_dataErroType_2 = "字段不准";
					}else{
						str_dataErroType_2 = "无效网元需删除";
					}

				}

				@Override
				public void onNothingSelected(AdapterView<?> parent) {
					// TODO Auto-generated method stub

				}
			});

			//数据不准确网元名称
			iv_wang.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					if(!isKong(company_edit.getText().toString()) && ! isKong(qy_edit.getText().toString())){
						Intent gIntent = new Intent(OfficeHiddenTroubleActivity.this,OfflineSearchActivity.class);
						gIntent.putExtra("type", "传输网元");
						gIntent.putExtra("city", company_edit.getText().toString());
						gIntent.putExtra("country", qy_edit.getText().toString());
						startActivityForResult(gIntent, 1);
					}else{
						Toast.makeText(OfficeHiddenTroubleActivity.this, "请先选择地市和区县", Toast.LENGTH_SHORT).show();
					}
				}
			});
			//数据不准确段落名称
			iv_guang.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					if(!isKong(company_edit.getText().toString()) && ! isKong(qy_edit.getText().toString())){
						Intent gIntent = new Intent(OfficeHiddenTroubleActivity.this,OfflineSearchActivity.class);
						gIntent.putExtra("type", "光缆段");
						gIntent.putExtra("city", company_edit.getText().toString());
						gIntent.putExtra("country", qy_edit.getText().toString());
						startActivityForResult(gIntent, 2);
					}else{
						Toast.makeText(OfficeHiddenTroubleActivity.this, "请先选择地市和区县", Toast.LENGTH_SHORT).show();
					}
				}
			});

			iv_time.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					Calendar c = Calendar.getInstance();
					// 直接创建一个DatePickerDialog对话框实例，并将它显示出来
					new DatePickerDialog(OfficeHiddenTroubleActivity.this,
							// 绑定监听器
							new DatePickerDialog.OnDateSetListener() {
								@Override
								public void onDateSet(DatePicker view, int year,
													  int monthOfYear, int dayOfMonth) {
									tv_time.setText(year+"-"+monthOfYear+"-"+dayOfMonth);
			                    	/*  Date date = new Date(year, monthOfYear, dayOfMonth);
			                    	  tv_time.setText(date.toString());*/

								}
							}
							// 设置初始日期
							, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c
							.get(Calendar.DAY_OF_MONTH)).show();

				}
			});


		}else if(cType.equals(ApplicationValue.single_list[2])){

			//起点井
			iv_start_jing.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent gIntent = new Intent(OfficeHiddenTroubleActivity.this,OfficeJingSelectActivity.class);
					//Intent gIntent = new Intent(HiddenTroubleReportActivity2.this,MapTemp.class);
					gIntent.putExtra("type", true);
					startActivityForResult(gIntent, 1);
				}
			});

			//终点井
			iv_end_jing.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					if(JingStart == null){
						Toast.makeText(OfficeHiddenTroubleActivity.this, "请先选择起点井", Toast.LENGTH_SHORT).show();
						return;
					}
					Intent gIntent = new Intent(OfficeHiddenTroubleActivity.this,OfficeJingSelectActivity.class);
					//	Intent gIntent = new Intent(HiddenTroubleReportActivity2.this,MapTemp.class);
					gIntent.putExtra("type", true);
					startActivityForResult(gIntent, 2);
				}
			});

			//是否整改
			ArrayAdapter<CharSequence> jjcdAdapter3 = ArrayAdapter.createFromResource(this, R.array.is_change,
					android.R.layout.simple_dropdown_item_1line);
			isChange_spinner.setAdapter(jjcdAdapter3);
			str_isChange = "否";
			isChange_spinner.setOnItemSelectedListener(new OnItemSelectedListener() {

				@Override
				public void onItemSelected(AdapterView<?> parent, View view,
										   int position, long id) {

					if(position == 0){
						str_isChange = "否";
					}else{
						str_isChange = "是";
					}
				}

				@Override
				public void onNothingSelected(AdapterView<?> parent) {
				}
			});

			iv_time.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					Calendar c = Calendar.getInstance();
					// 直接创建一个DatePickerDialog对话框实例，并将它显示出来
					new DatePickerDialog(OfficeHiddenTroubleActivity.this,
							// 绑定监听器
							new DatePickerDialog.OnDateSetListener() {
								@Override
								public void onDateSet(DatePicker view, int year,
													  int monthOfYear, int dayOfMonth) {
									tv_time.setText(year+"-"+monthOfYear+"-"+dayOfMonth);
			                    	/*  Date date = new Date(year, monthOfYear, dayOfMonth);
			                    	  tv_time.setText(date.toString());*/

								}
							}
							// 设置初始日期
							, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c
							.get(Calendar.DAY_OF_MONTH)).show();

				}
			});
		}
	}



	private void initData(ErrorInfoBean errorInfoBean) {

		//资源隐患
		if(cType.equals(ApplicationValue.single_list[0]) || isUpdate){
			//大小问题初始化
			if(!isKong(errorInfoBean.getErrorBig())){
				str_d = errorInfoBean.getErrorBig();
				for(int i = 0;i < troubleSp.length; i++){
					if(errorInfoBean.getErrorBig().equals(troubleSp[i])){
						jjcd_spinner.setSelection(i);
						if(i == 0 ){
							troubleSp1 = getResources().getStringArray(R.array.trouble_b_1);
							jjcdAdapter2 = ArrayAdapter.createFromResource(OfficeHiddenTroubleActivity.this, R.array.trouble_b_1,
									android.R.layout.simple_dropdown_item_1line);
						}else if(i == 1){
							troubleSp1 = getResources().getStringArray(R.array.trouble_b_2);
							jjcdAdapter2 = ArrayAdapter.createFromResource(OfficeHiddenTroubleActivity.this, R.array.trouble_b_2,
									android.R.layout.simple_dropdown_item_1line);
						}else if(i == 2){
							troubleSp1 = getResources().getStringArray(R.array.trouble_b_3);
							jjcdAdapter2 = ArrayAdapter.createFromResource(OfficeHiddenTroubleActivity.this, R.array.trouble_b_3,
									android.R.layout.simple_dropdown_item_1line);
						}else{
							troubleSp1 = getResources().getStringArray(R.array.trouble_b_4);
							jjcdAdapter2 = ArrayAdapter.createFromResource(OfficeHiddenTroubleActivity.this, R.array.trouble_b_4,
									android.R.layout.simple_dropdown_item_1line);
						}
						jjcd_spinner_2.setAdapter(jjcdAdapter2);

						//小问题
						if(!isKong(errorInfoBean.getErrorSmall())){
							str_x = errorInfoBean.getErrorSmall();

							for(int ii = 0;ii<troubleSp1.length;ii++){
								if(errorInfoBean.getErrorSmall().equals(troubleSp1[ii])){
									jjcd_spinner_2.setSelection(ii);
								}
							}
						}
					}
				}
			}

			company_edit.setText(errorInfoBean.getCity());  //地市
			qy_edit.setText(errorInfoBean.getCounty());	//区县
			sbr_edit.setText(errorInfoBean.getErrorLocateDes());//隐患位置描述
			jd_edit.setText(String.valueOf(errorInfoBean.getLongitude()));	//经度
			wd_edit.setText(String.valueOf(errorInfoBean.getLatitude()));	//纬度
			yhms_edit.setText(errorInfoBean.getErrorDescribe());  //隐患描述
			tv_time.setText(errorInfoBean.getZgRq());//整改日期

			//是否整改初始化
			if(!isKong(errorInfoBean.getIsZg())){
				str_isChange = errorInfoBean.getIsZg();
				if(str_isChange.equals("是")){
					isChange_spinner.setSelection(1);
				}else if(str_isChange.equals("否")){
					isChange_spinner.setSelection(0);
				}
			}

			//综资数据
		}else if(cType.equals(ApplicationValue.single_list[1])){
			company_edit.setText(errorInfoBeanZz.getCity());  //地市
			qy_edit.setText(errorInfoBeanZz.getCounty());	//区县
			if(errorInfoBeanZz.getErrorResType() != null){
				if(errorInfoBeanZz.getErrorResType().equals("网元")){
					sp_dataErroType.setSelection(0);
					str_dataErroType = "网元";
				}else if(errorInfoBeanZz.getErrorResType().equals("光缆断")){
					sp_dataErroType.setSelection(1);
					str_dataErroType = "光缆断";
				}
			}

			tv_wang.setText(errorInfoBeanZz.getErrorNetName());

			String[] eTypes = getResources().getStringArray(R.array.data_erro_type_2);
			for(int i = 0;i < eTypes.length; i++){
				if(eTypes[i].equals(errorInfoBeanZz.getErrorResRank())){
					sp_dataErroType_2.setSelection(i);
				}
			}


			//是否整改初始化
			if(!isKong(errorInfoBeanZz.getIsZg())){
				str_isChange = errorInfoBeanZz.getIsZg();
				if(str_isChange.equals("是")){
					isChange_spinner.setSelection(1);
				}else if(str_isChange.equals("否")){
					isChange_spinner.setSelection(0);
				}
			}

			tv_time.setText(errorInfoBeanZz.getZgRq());//整改日期

		}else if(cType.equals(ApplicationValue.single_list[2])){

			company_edit.setText(errorInfoBeanGd.getCity());  //地市
			qy_edit.setText(errorInfoBeanGd.getCounty());	//区县

			tv_start_jing.setText(errorInfoBeanGd.getStartResName());//起点名称
			tv_end_jing.setText(errorInfoBeanGd.getEndResName());//终点名称

			tv_start_location.setText(errorInfoBeanGd.getStartLongitude()+","+errorInfoBeanGd.getStartLatitude());
			tv_end_location.setText(errorInfoBeanGd.getEndLongitude()+","+errorInfoBeanGd.getEndLatitude());
			et_lenge.setText(errorInfoBeanGd.getSegSbLength());
			et_lenge_auto.setText(errorInfoBeanGd.getSegSysLength());
			tv_u_name.setText(errorInfoBeanGd.getUserId());
		}





		//设置公司名称

		//    jjcd_spinner.setSelection(StringUtils.getInt(errorInfoBean.getUrgencyLevel()));
		if(errorInfoBean.getResourceID()!=null){
			resourceInfoBean = new ResourceInfoBean();
			resourceInfoBean.setResourceID(errorInfoBean.getResourceID());
			resourceInfoBean.setResourceName(errorInfoBean.getErrorObjectName());
			resourceInfoBean.setResourceType(errorInfoBean.getResourceType());
		}
	}

	private ErrorInfoBean editInfo(ErrorInfoBean errorInfo) {

		if (errorInfo != null) {
			errorInfoBean = errorInfo;
		}
		else {
			errorInfoBean = new ErrorInfoBean();
		}
		String uid = PreferencesUtils.getString(OfficeHiddenTroubleActivity.this, "UID", "");
		errorInfoBean.setCounty(qy_edit.getText().toString());	//区域
		errorInfoBean.setCity(company_edit.getText().toString());	//地市
		errorInfoBean.setResourceID(resourceInfoBean.getResourceID());
		errorInfoBean.setErrorObjectName(resourceInfoBean.getResourceName());
		errorInfoBean.setResourceType(resourceInfoBean.getResourceType());
		errorInfoBean.setLongitude(Double.parseDouble(jd_edit.getText().toString()));//经度
		errorInfoBean.setLatitude(Double.parseDouble(wd_edit.getText().toString()));//纬度
		errorInfoBean.setIsZg(str_isChange);	//是否整改
		errorInfoBean.setZgRq(tv_time.getText().toString());	//整改日期
		errorInfoBean.setRouteID(routeID);
		errorInfoBean.setUserId(uid);	//操作人

		errorInfoBean.setErrorLocateDes(sbr_edit.getText().toString());	//隐患位置
		errorInfoBean.setErrorBig(str_d);	//大问题
		errorInfoBean.setErrorSmall(str_x);	//小问题
		errorInfoBean.setErrorDescribe(yhms_edit.getText().toString());  //隐患描述


		return errorInfoBean;
	}


	/**
	 * 综资数据问题
	 * @param errorInfo
	 * @return
	 */
	private ErrorInfoZzBean editInfoZz(ErrorInfoZzBean errorInfo) {
		String uid = PreferencesUtils.getString(OfficeHiddenTroubleActivity.this, "UID", "");

		if (errorInfo != null) {
			errorInfoBeanZz = errorInfo;
		}
		else {
			errorInfoBeanZz = new ErrorInfoZzBean();
		}

		errorInfoBeanZz.setCounty(qy_edit.getText().toString());	//区域
		errorInfoBeanZz.setCity(company_edit.getText().toString());	//地市

		errorInfoBeanZz.setIsZg(str_isChange);	//是否整改
		errorInfoBeanZz.setZgRq(tv_time.getText().toString());	//整改日期
		errorInfoBeanZz.setRouteID(routeID);
		errorInfoBeanZz.setUserId(uid);	//操作人

		errorInfoBeanZz.setErrorResType(str_dataErroType);
		errorInfoBeanZz.setErrorResRank(str_dataErroType_2);

		if(isKong(tv_guang.getText().toString())){
			errorInfoBeanZz.setErrorNetName(tv_guang.getText().toString());
			Log.d("lixu","-----errorInfoBeanZz.setErrorNetName"+tv_guang.getText().toString());
		}else{
			errorInfoBeanZz.setErrorNetName(tv_wang.getText().toString());
			Log.d("lixu","-----errorInfoBeanZz.setErrorNetName"+tv_wang.getText().toString());
		}


		errorInfoBeanZz.setUserId(uid);

		return errorInfoBeanZz;

	}


	/**
	 * 管道故障
	 * @param errorInfo
	 * @return
	 */
	private ErrorInfoGdgzBean editInfoGd(ErrorInfoGdgzBean errorInfo) {


		if (errorInfo != null) {
			errorInfoBeanGd = errorInfo;
		}
		else {
			errorInfoBeanGd = new ErrorInfoGdgzBean();
		}

		String uid = PreferencesUtils.getString(OfficeHiddenTroubleActivity.this, "UID", "");
		errorInfoBeanGd.setCounty(qy_edit.getText().toString());	//区域
		errorInfoBeanGd.setCity(company_edit.getText().toString());	//地市
		errorInfoBeanGd.setRouteID(routeID);
		if(JingStart != null){
			errorInfoBeanGd.setStartResName(JingStart.getResourceName());//起点井
			errorInfoBeanGd.setStartResId(JingStart.getResourceID());//起点井
			errorInfoBeanGd.setStartLatitude(JingStart.getLatitude());
			errorInfoBeanGd.setStartLongitude(JingStart.getLongitude());
		}

		if(JingEnd != null){
			errorInfoBeanGd.setEndResName(JingEnd.getResourceName());//终点井
			errorInfoBeanGd.setEndResId(JingEnd.getResourceID());//终点井
			errorInfoBeanGd.setEndLatitude(JingEnd.getLatitude());
			errorInfoBeanGd.setEndLongitude(JingEnd.getLongitude());
		}

		errorInfoBeanGd.setSegSbLength(et_lenge.getText().toString());
		errorInfoBeanGd.setSegSysLength(et_lenge_auto.getText().toString());
		errorInfoBeanGd.setUserId(uid);
		tv_u_name.setText(uid);

		return errorInfoBeanGd;

	}

	private boolean isKong(String s){
		if(s == null || s.isEmpty()){
			return true;
		}
		return false;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if(resultCode == 19){
			if(data != null){



				Resours res = (Resours) data.getSerializableExtra("name");
				errorInfoBeanZz.setResId(Integer.parseInt(res.getResId()));
				errorInfoBeanZz.setErrorNetName(res.getResName());
				tv_wang.setText(res.getResName());
				tv_guang.setText(res.getResName());



			}
		}


		if(resultCode == 12){
			//起点井
			if(requestCode == 1){
				if(data != null){
					JingStart = (ResourceInfoBean) data.getExtras().getSerializable("gd");
					tv_start_jing.setText(JingStart.getResourceName());
					tv_start_location.setText(JingStart.getLongitude()+","+JingStart.getLatitude());
				}

				//终点井
			}else if(requestCode ==2){
				JingEnd = (ResourceInfoBean) data.getExtras().getSerializable("gd");
				tv_end_jing.setText(JingEnd.getResourceName());
				tv_end_location.setText(JingEnd.getLongitude()+","+JingStart.getLatitude());
				try{
					JingDis = DistanceUtil.getDistance(coverteToBaidu(JingStart.getLatitude(),JingStart.getLongitude())
							, coverteToBaidu(JingEnd.getLatitude(), JingEnd.getLongitude()));
					et_lenge_auto.setText(JingDis+"");
				}catch(Exception e){
					Log.i("lixu","计算距离异常"+e.toString());
				}
			}
		}


		if (resultCode == RESULT_OK){
			if (requestCode == REQUESTCODE_GET_AREA) {
				company_edit.setText((CharSequence)data.getStringExtra("city"));//地市
				qy_edit.setText((CharSequence)data.getStringExtra("area"));//区县


			}else if(requestCode == REQUESTCODE_LOCATION){
				jd_edit.setText(data.getStringExtra("lonstr2"));
				wd_edit.setText(data.getStringExtra("latstr2"));
			}else if(requestCode == REQUESTCODE_TAKE_PHOTO){  //拍照返回
				if(data != null ){
					isHavePhoto = data.getBooleanExtra("isTake", false);

				}
				startPhotoBacked = true;
			}

		}
	}


	// 区域获取
	public void qy_select_onc(View v) {
		if(!isUpdate){
			startActivityForResult(new Intent(this, RegionWheelDialog.class), REQUESTCODE_GET_AREA);
		}
	}

	// 经纬度获取
	public void getjwd(View v) {
		startActivityForResult(new Intent(this, LocationOverlayDemo.class), REQUESTCODE_LOCATION);
	}

	// 隐患对象获取
	public void yhdx_onc(View v) {
		startActivityForResult(new Intent(OfficeHiddenTroubleActivity.this, DeliveryResourceSearchListActivity.class), REQUESTCODE_GET_RESOURCE);
	}

	// 拍照
	public void cs_take_photo(View v) {
		if(resourceInfoBean == null){
			Toast.makeText(OfficeHiddenTroubleActivity.this, "程序异常，请重新进入!", Toast.LENGTH_SHORT).show();
			return;
		}
		startPhotoBacked = false;
		Intent intent = new Intent(OfficeHiddenTroubleActivity.this, PhotoOfficeActivity.class);

		intent.putExtra("routeId", routeID);
		intent.putExtra("relatedId", resourceInfoBean.getResourceID()+"");
		intent.putExtra("resourceType", resourceInfoBean.getResourceType());
		intent.putExtra("lon",ZSLConst.curGpsLocation.getLongitude());
		intent.putExtra("lat", ZSLConst.curGpsLocation.getLatitude());

		if(cType.equals(ApplicationValue.single_list[0])){
			intent.putExtra("mPhotoType", ZSLConst.PHOTO_TYPE_ERROR);
		}else if(cType.equals(ApplicationValue.single_list[1])){
			intent.putExtra("mPhotoType", ZSLConst.PHOTO_TYPE_ZZ);
		}else if(cType.equals(ApplicationValue.single_list[2])){
			intent.putExtra("mPhotoType", ZSLConst.PHOTO_TYPE_GD);
		}

		startActivityForResult(intent, REQUESTCODE_TAKE_PHOTO);
	}
	/**
	 * 初始化图片
	 */
	private void getData(){
		if (this.mProgress == null) {
			this.mProgress = ProgressDialog.show(this, "系统提示", "正在检测图片...");
			this.mProgress.setCancelable(true);
		} else {
			this.mProgress.setMessage("正在检测图片...");
			this.mProgress.show();
		}

		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub


				final ArrayList<NameValuePair> list = new ArrayList<NameValuePair>();

				PhotoBean bean = new PhotoBean();
				if(routeID != 0){
					bean.setRouteId(routeID+"");
				}
				if(resourceInfoBean.getResourceID()!=null && resourceInfoBean.getResourceID()!=0){
					bean.setRelatedID(resourceInfoBean.getResourceID()+"");
				}


				if(cType.equals(ApplicationValue.single_list[0])){
					bean.setPhotoType(ZSLConst.PHOTO_TYPE_ERROR);
				}else if(cType.equals(ApplicationValue.single_list[1])){
					bean.setPhotoType(ZSLConst.PHOTO_TYPE_ZZ);
				}else if(cType.equals(ApplicationValue.single_list[2])){
					bean.setPhotoType(ZSLConst.PHOTO_TYPE_GD);
				}



				Gson json = new Gson();




				//	String json = "{\"routeID\":"+routeId+",\"relatedID\":"+relatedId+"}";


				list.add((NameValuePair)new BasicNameValuePair("jsonRequest",json.toJson(bean)));

				final String httpGetData = new httpconnect().httpGetData("pdaMainTask!getPhotoById.interface", list,
						OfficeHiddenTroubleActivity.this);

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
						message.what = 9;
						message.obj = string;
						handler.sendMessage(message);
						return;
					}
					message.what = 10;
					message.obj = string;
					handler.sendMessage(message);
				} catch (JSONException ex) {
				}

			}
		}).start();

	}

	private boolean isKongForTextView(TextView tv){
		if(tv != null){
			if(tv.getText().toString() == null || tv.getText().toString().length() < 0){
				return false;
			}
			return true;
		}
		return false;
	}


	// 保存本地
	public void cs_save(View v) {
		if(isHavePhoto){
			upload();
		}else{
			ToastUtils.show(OfficeHiddenTroubleActivity.this, "请先拍照");
		}

	}



	private ProgressDialog mProgress;
	// 提交
	public void cs_submit(View v) {
		getData();


	}
	String jsonRequest;
	private void upload(){
		if(isKong(qy_edit.getText().toString())){
			Toast.makeText(OfficeHiddenTroubleActivity.this, "请选择区县", Toast.LENGTH_SHORT).show();
			return;
		}

		//资源隐患控制
		if(cType.equals(ApplicationValue.single_list[0])){
			if(isKong(sbr_edit.getText().toString())){
				Toast.makeText(OfficeHiddenTroubleActivity.this, "请填写隐患位置描述!", Toast.LENGTH_SHORT).show();
				return;
			}

			if(isKong(yhms_edit.getText().toString())){
				Toast.makeText(OfficeHiddenTroubleActivity.this, "请填写隐患描述", Toast.LENGTH_SHORT).show();
				return;
			}


			if(str_isChange.equals("是")){
				if(isKong(tv_time.getText().toString())){
					Toast.makeText(OfficeHiddenTroubleActivity.this, "是否整改选择是时，整改日期必填", Toast.LENGTH_SHORT).show();
					return;
				}
			}else{
				tv_time.setText("");
			}

			errorInfoBean = editInfo(errorInfoBean);
			jsonRequest = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss Z").create().toJson(errorInfoBean);

		}else if(cType.equals(ApplicationValue.single_list[1])){
			if(isKong(tv_wang.getText().toString())){
				Toast.makeText(OfficeHiddenTroubleActivity.this, "请选择数据不准确网元名称!", Toast.LENGTH_SHORT).show();
				return;
			}

			if(isKong(tv_guang.getText().toString())){
				Toast.makeText(OfficeHiddenTroubleActivity.this, "请选择数据不准确段落名称!", Toast.LENGTH_SHORT).show();
				return;
			}

			if(str_isChange.equals("是")){
				if(isKong(tv_time.getText().toString())){
					Toast.makeText(OfficeHiddenTroubleActivity.this, "是否完成数据修改选择是时，数据修改日期必填", Toast.LENGTH_SHORT).show();
					return;
				}
			} else{
				tv_time.setText("");
			}
			errorInfoBeanZz = editInfoZz(errorInfoBeanZz);
			jsonRequest = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss Z").create().toJson(errorInfoBeanZz);
		}else if(cType.equals(ApplicationValue.single_list[2])){
			if(isKong(tv_start_jing.getText().toString())){
				Toast.makeText(OfficeHiddenTroubleActivity.this,"请选择起点井",Toast.LENGTH_SHORT).show();
				return;
			}
			if(isKong(tv_end_jing.getText().toString())){
				Toast.makeText(OfficeHiddenTroubleActivity.this,"请选择终点井",Toast.LENGTH_LONG).show();
				return;
			}

			if(isKong(et_lenge.getText().toString())){
				Toast.makeText(OfficeHiddenTroubleActivity.this,"请填写故障长度(米)",Toast.LENGTH_SHORT).show();
				return;
			}
			errorInfoBeanGd = editInfoGd(errorInfoBeanGd);
			jsonRequest = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss Z").create().toJson(errorInfoBeanGd);
		}

		if (this.mProgress == null) {
			this.mProgress = ProgressDialog.show(this, "系统提示", "正在初始化数据...");
		} else {
			this.mProgress.setMessage("正在初始化数据...");
			this.mProgress.show();
		}


		if(MySQLHelper.saveErroForZYYH(OfficeHiddenTroubleActivity.this,routeID, jsonRequest,cType)){
			Message message = Message.obtain();
			message.what = 4;
			message.obj = "保存成功";
			handler.sendMessage(message);
		}else{
			Message message = Message.obtain();
			message.what = -1;
			message.obj = "保存失败,请检查内存空间";
			handler.sendMessage(message);
		}
	}


	private Handler handler = new Handler(new Handler.Callback() {

		public boolean handleMessage(Message msg) {
			if (mProgress != null) {
				mProgress.dismiss();
			}
			switch (msg.what) {

				case -1:
					Toast.makeText(OfficeHiddenTroubleActivity.this, msg.obj.toString(), Toast.LENGTH_SHORT).show();
					break;
				case 4:
					Toast.makeText(OfficeHiddenTroubleActivity.this, msg.obj.toString(), Toast.LENGTH_SHORT).show();
					setResult(RESULT_OK);
					finish();
					break;
				case 5:
					//init();
					break;
				case 9:
					List<PhotoInfoBean> list = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss Z").create()
							.fromJson(msg.obj.toString(), new TypeToken<List<PhotoInfoBean>>() {
							}.getType());
					if(list!=null && list.size()!=0){
						upload();
					}else{
						Toast.makeText(OfficeHiddenTroubleActivity.this, "请先上传图片", Toast.LENGTH_SHORT).show();
					}
					break;
				case 10:
					Toast.makeText(OfficeHiddenTroubleActivity.this, msg.obj.toString(), Toast.LENGTH_SHORT).show();
					break;

			}
			return true;
		}

	});


	private LatLng coverteToBaidu(double lat,double lng){
		// 将GPS设备采集的原始GPS坐标转换成百度坐标
		CoordinateConverter converter  = new CoordinateConverter();
		converter.from(CoordType.GPS);
		// sourceLatLng待转换坐标
		converter.coord(new LatLng(lat,lng));
		return converter.convert();
	}
}