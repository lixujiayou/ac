package com.inspur.resources.view.delivery.transroute;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.GsonBuilder;
import com.inspur.StringUtils;
import com.inspur.common.HttpListener;
import com.inspur.common.RegionWheelDialog;
import com.inspur.common.RequestTool;
import com.inspur.easyresources.R;
import com.inspur.resources.base.BaseActivity;
import com.inspur.resources.utils.ApplicationValue;
import com.inspur.resources.view.delivery.guang.PhotoShopeActivityGuang;
import com.inspur.resources.view.delivery.transroute.bean.ErrorInfoBean;
import com.inspur.resources.view.delivery.transroute.bean.PhotoInfoBean;
import com.inspur.resources.view.delivery.transroute.bean.ResourceInfoBean;
import com.inspur.resources.view.map.LocationOverlayDemo;
import com.yolanda.nohttp.OnUploadListener;
import com.yolanda.nohttp.Response;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import cn.trinea.android.common.util.PreferencesUtils;

public class HiddenTroubleReportActivity extends BaseActivity {
	public final static String INTENT_DATA_FLAG = "ErrorInfo";
	private final static int REQUESTCODE_GET_AREA = 1;
	private final static int REQUESTCODE_GET_RESOURCE = 2;
	private final static int REQUESTCODE_LOCATION = 3;
	private static final int REQUESTCODE_TAKE_PHOTO = 4;
	private EditText sbr_edit,qy_edit, jd_edit, wd_edit, yhdx_edit, yhms_edit, xcclqk_edit, ylwt_edit, cljy_edit;
	private Spinner jjcd_spinner;//问题大类

	private ErrorInfoBean errorInfoBean;
	private ResourceInfoBean resourceInfoBean;
	private boolean isUpdate = false;
	private int routeID = 0;
	//标识点击拍照后是否已返回（返回后才可以提交）
	private boolean startPhotoBacked = true;

	@Override
	protected void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
		setContentView(R.layout.activity_hiddentroublereport);
		setTitle("隐患上报");
		initUI();

		final Intent intent = this.getIntent();
		isUpdate = intent.getBooleanExtra("isUpdate", false);
		routeID = intent.getIntExtra("routeID", 0);
		if (isUpdate) {
			errorInfoBean = (ErrorInfoBean) intent.getSerializableExtra(INTENT_DATA_FLAG);
		}else{
			errorInfoBean = new ErrorInfoBean();
			errorInfoBean.setRouteID(routeID);
			/*if(ZSLConst.curLocation!=null){
				errorInfoBean.setLatitude(ZSLConst.curLocation.getLatitude());
				errorInfoBean.setLongitude(ZSLConst.curLocation.getLongitude());
			}*/
			if(ZSLConst.curGpsLocation!=null){
				errorInfoBean.setLatitude(ZSLConst.curGpsLocation.getLatitude());
				errorInfoBean.setLongitude(ZSLConst.curGpsLocation.getLongitude());
			}

		}
		initData(errorInfoBean);
	}

	private void initUI() {
		sbr_edit = (EditText) findViewById(R.id.sbr_edit);
		qy_edit = (EditText) findViewById(R.id.qy_edit);
		jd_edit = (EditText) findViewById(R.id.jd_edit);
		wd_edit = (EditText) findViewById(R.id.wd_edit);
		yhdx_edit = (EditText) findViewById(R.id.yhdx_edit);
		yhms_edit = (EditText) findViewById(R.id.yhms_edit);
		xcclqk_edit = (EditText) findViewById(R.id.xcclqk_edit);
		ylwt_edit = (EditText) findViewById(R.id.ylwt_edit);
		cljy_edit = (EditText) findViewById(R.id.cljy_edit);

		jjcd_spinner = (Spinner) findViewById(R.id.jjcd_spinner);
		ArrayAdapter<CharSequence> jjcdAdapter = ArrayAdapter.createFromResource(this, R.array.jinjichengdu,
				android.R.layout.simple_spinner_item);
		jjcd_spinner.setAdapter(jjcdAdapter);



	}

	private void initData(ErrorInfoBean errorInfoBean) {
		sbr_edit.setText(PreferencesUtils.getString(HiddenTroubleReportActivity.this, "UID", ""));
		qy_edit.setText(errorInfoBean.getCity());
		jd_edit.setText(String.valueOf(errorInfoBean.getLongitude()));
		wd_edit.setText(String.valueOf(errorInfoBean.getLatitude()));
		yhdx_edit.setText(errorInfoBean.getErrorObjectName());
		yhms_edit.setText(errorInfoBean.getErrorDescribe());
		/*xcclqk_edit.setText(errorInfoBean.getSituation());
		ylwt_edit.setText(errorInfoBean.getLeftProblem());
		cljy_edit.setText(errorInfoBean.getSuggest());

        jjcd_spinner.setSelection(StringUtils.getInt(errorInfoBean.getUrgencyLevel()));*/
		if(errorInfoBean.getResourceID()!=null){
			resourceInfoBean = new ResourceInfoBean();
			resourceInfoBean.setResourceID(errorInfoBean.getResourceID());
			resourceInfoBean.setResourceName(errorInfoBean.getErrorObjectName());
			resourceInfoBean.setResourceType(errorInfoBean.getResourceType());
		}
	}

	private ErrorInfoBean editInfo(ErrorInfoBean errorInfo) {
		if(resourceInfoBean==null){
			Toast.makeText(this, "请选择隐患资源!", Toast.LENGTH_SHORT).show();
			yhdx_edit.requestFocus();
			return null;
		}
		if (errorInfo != null) {
			errorInfoBean = errorInfo;
		}
		else {
			errorInfoBean = new ErrorInfoBean();
		}
		errorInfoBean.setCity(qy_edit.getText().toString());
		errorInfoBean.setLongitude(Double.parseDouble(jd_edit.getText().toString()));
		errorInfoBean.setLatitude(Double.parseDouble(wd_edit.getText().toString()));
		errorInfoBean.setErrorObjectName(resourceInfoBean.getResourceName());
		errorInfoBean.setResourceID(resourceInfoBean.getResourceID());
		errorInfoBean.setResourceType(resourceInfoBean.getResourceType());
		errorInfoBean.setErrorDescribe(yhms_edit.getText().toString());
/*        errorInfoBean.setSituation(xcclqk_edit.getText().toString());
        errorInfoBean.setLeftProblem(ylwt_edit.getText().toString());
        errorInfoBean.setSuggest(cljy_edit.getText().toString());
        errorInfoBean.setUrgencyLevel(String.valueOf(jjcd_spinner.getSelectedItemPosition()));*/
		return errorInfoBean;

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK){
			if (requestCode == REQUESTCODE_GET_AREA) {
				qy_edit.setText((CharSequence)data.getStringExtra("region"));
			}else if(requestCode == REQUESTCODE_GET_RESOURCE){
				resourceInfoBean = (ResourceInfoBean) data.getSerializableExtra("deliveryResource");
				yhdx_edit.setText(resourceInfoBean.getResourceName());
			}else if(requestCode == REQUESTCODE_LOCATION){
				jd_edit.setText(data.getStringExtra("lonstr2"));
				wd_edit.setText(data.getStringExtra("latstr2"));
			}else if(requestCode == REQUESTCODE_TAKE_PHOTO){
				ArrayList<PhotoInfoBean> photoList = (ArrayList<PhotoInfoBean>) data.getSerializableExtra("photos");
				System.out.println("photoList="+photoList);
				startPhotoBacked = true;
				if(photoList==null||photoList.size()==0){
					return;
				}
				/*if(errorInfoBean.getErrorsPhotos()==null){
					errorInfoBean.setErrorsPhotos(new ArrayList<PhotoInfoBean>());
				}
				errorInfoBean.getErrorsPhotos().addAll(photoList);*/

			}

		}
	}


	// 区域获取
	public void qy_select_onc(View v) {
		startActivityForResult(new Intent(this, RegionWheelDialog.class), REQUESTCODE_GET_AREA);
	}

	// 经纬度获取
	public void getjwd(View v) {
		startActivityForResult(new Intent(this, LocationOverlayDemo.class), REQUESTCODE_LOCATION);
	}

	// 隐患对象获取
	public void yhdx_onc(View v) {
		startActivityForResult(new Intent(HiddenTroubleReportActivity.this, DeliveryResourceSearchListActivity.class), REQUESTCODE_GET_RESOURCE);
	}

	// 拍照
	public void cs_take_photo(View v) {
		if(resourceInfoBean==null){
			Toast.makeText(HiddenTroubleReportActivity.this, "请先选择隐患资源!", Toast.LENGTH_SHORT).show();
			return;
		}
		startPhotoBacked = false;
		Intent intent = new Intent(HiddenTroubleReportActivity.this, PhotoShopeActivityGuang.class);
		intent.putExtra("mPhotoType", ZSLConst.PHOTO_TYPE_ERROR);
		intent.putExtra("routeId", routeID);
		intent.putExtra("relatedId", resourceInfoBean.getResourceID());
		intent.putExtra("resourceType", resourceInfoBean.getResourceType());

		startActivityForResult(intent, REQUESTCODE_TAKE_PHOTO);
	}

	// 保存本地
	public void cs_save(View v) {
		if(resourceInfoBean==null){
			Toast.makeText(HiddenTroubleReportActivity.this, "请先选择隐患资源!", Toast.LENGTH_SHORT).show();
			return;
		}
//		CacheHelper.getInstance(this).saveObject(editInfo(errorInfoBean), "HiddenTrouble_"+routeID+"_"+Math.random());
		Intent data = new Intent();
		data.putExtra("errorInfo", editInfo(errorInfoBean));
		setResult(RESULT_OK,data);
		finish();
	}

	// 提交
	public void cs_submit(View v) {
		if(resourceInfoBean==null){
			Toast.makeText(HiddenTroubleReportActivity.this, "请先选择隐患资源!", Toast.LENGTH_SHORT).show();
			return;
		}
		if(!startPhotoBacked){
			Toast.makeText(HiddenTroubleReportActivity.this, "照片信息尚未加载,请稍后提交...", Toast.LENGTH_SHORT).show();
			return;
		}
		errorInfoBean = editInfo(errorInfoBean);

		final Map<String,File> fileMap = new HashMap<String, File>();
//	        File file = null;
//			if(errorInfoBean.getErrorsPhotos()!=null){
//				for (PhotoInfoBean photo : errorInfoBean.getErrorsPhotos()) {
//					file = new File(ZSLConst.photodir, photo.getPhotoName());
//					if(file!=null){
//						fileMap.put(photo.getPhotoName(), file);
//					}
//				}
//			}else{
//				String msg = "隐患上报必须拍照!是否重新拍照?";
//				AlertDialog ad = new AlertDialog.Builder(HiddenTroubleReportActivity.this).setTitle("温馨提示").setMessage(msg)
//						.setPositiveButton("拍照", new DialogInterface.OnClickListener() {
//							@Override
//							public void onClick(DialogInterface dialog, int which) {
//								dialog.dismiss();
//								cs_take_photo(null);
//							}
//							}).setNegativeButton("暂不拍照", new DialogInterface.OnClickListener() {
//
//							@Override
//							public void onClick(DialogInterface dialog, int which) {
//								dialog.dismiss();
//								return;
//							}
//						}).create();
//				ad.show();
//			}

		String uid = PreferencesUtils.getString(HiddenTroubleReportActivity.this, "UID", "");
		String jsonRequest = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss Z").create().toJson(errorInfoBean);
		System.out.println("jsonRequest="+jsonRequest);

		Map<String,String> paramMap = new HashMap<String, String>();
		paramMap.put("uid", uid);
		paramMap.put("jsonRequest", jsonRequest);


		RequestTool.uploadFileNoHttp(HiddenTroubleReportActivity.this, ApplicationValue.url+"errorUpload", paramMap, fileMap, new OnUploadListener() {

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
					Toast.makeText(HiddenTroubleReportActivity.this, "服务端异常!", Toast.LENGTH_SHORT).show();
					return;
				}
				try {
					JSONObject jsonObject = new JSONObject(response.get());

					if(jsonObject!=null){

						if ("0".equals(jsonObject.getString("result"))) {
							Toast.makeText(HiddenTroubleReportActivity.this, "隐患提交成功!", Toast.LENGTH_SHORT).show();
//						  for (File file : fileMap.values()) {
//					        	file.delete();
//					      }
							finish();
						}else{
							String string = jsonObject.getString("info");
							Toast.makeText(HiddenTroubleReportActivity.this, string, Toast.LENGTH_SHORT).show();
						}
					}
				} catch (JSONException e) {
					Toast.makeText(HiddenTroubleReportActivity.this, "隐患提交失败！", Toast.LENGTH_SHORT).show();
					e.printStackTrace();
				}


			}

			@Override
			public void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis) {
				Toast.makeText(HiddenTroubleReportActivity.this, "隐患提交失败！"+exception.getMessage(), Toast.LENGTH_SHORT).show();
			}
		});
		/*Map<String, String> params = new HashMap<String, String>();
		params.put("uid", uid);
		params.put("jsonRequest", jsonRequest);
		PostFormBuilder pfb = OkHttpUtils.post().url("").params(params);
		File file;
		for (PhotoInfoBean photo : errorInfoBean.getErrorsPhotos()) {
			file = new File(ZSLConst.photodir, photo.getPhotoName());
			if(file!=null){
				pfb.addFile("files", photo.getPhotoName(), file);
			}
		}

		pfb.build().execute(new StringCallback() {
			
			@Override
			public void onResponse(String paramT) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onError(Call paramCall, Exception paramException) {
				// TODO Auto-generated method stub
				
			}
		});*/
	}
}