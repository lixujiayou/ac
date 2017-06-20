package com.inspur.resources.view.delivery.offline;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;
import org.simple.eventbus.EventBus;

import com.baidu.mapapi.model.LatLng;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.inspur.StringUtils;
import com.inspur.easyresources.R;
import com.inspur.resources.base.BaseActivity;
import com.inspur.resources.http.httpconnect;
import com.inspur.resources.view.delivery.guang.bean.Resours;
import com.inspur.resources.view.delivery.transroute.FalseDataFactory;
import com.inspur.resources.view.delivery.transroute.ZSLConst;
import com.inspur.resources.view.delivery.transroute.bean.ResourceInfoBean;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class OfflineSearchActivity extends BaseActivity {
	private SQLiteDatabase wangDB;
	private File wangFile = new File(MainOfflineActivity.FILE_PATH+MainOfflineActivity.FILE_NAME_WANG); //数据库文件


	private Spinner zylx_spinner;
	private EditText mc_edit;
	private ListView listview;
	private DeliveryResourceAdepter mAdepter;
	private List<Resours> deliveryResourceList = new ArrayList<Resours>();
	private ProgressDialog mProgress;
	private ResourceInfoBean resourceInfo;
	private double longitude,latitude;
	private String mType;
	private String mCity;
	private String mCountry;
	private Handler handler = new Handler(new Handler.Callback() {

		public boolean handleMessage(Message msg) {
			if (mProgress != null) {
				mProgress.dismiss();
			}
			switch (msg.what) {
				case 1:
					mAdepter.notifyDataSetChanged();
					break;
				case 2:
					Toast.makeText(OfflineSearchActivity.this, msg.obj.toString(), Toast.LENGTH_SHORT).show();
					break;
			}
			return true;
		}
	});

	@Override
	protected void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		setContentView(R.layout.activty_delivery_resource_select);
		setTitle("资源查询(离线)");
		Intent gIntent = getIntent();
		mType = gIntent.getExtras().getString("type");
		mCity = gIntent.getExtras().getString("city");
		mCountry = gIntent.getExtras().getString("country");
		init();
		try{
			wangDB = SQLiteDatabase.openOrCreateDatabase(wangFile, null);
			//getResourceLineBeanList();
		}catch(Exception e){
			Log.d("lixu","数据库报错了=="+e.toString());
		}
	}

	private void init(){
		zylx_spinner = (Spinner) findViewById(R.id.zylx_spinner);
		ArrayAdapter<CharSequence> zylxAdapter = ArrayAdapter.createFromResource(this, R.array.ziyuanleixing,
				android.R.layout.simple_spinner_item);
		zylx_spinner.setAdapter(zylxAdapter);
		mc_edit = (EditText) findViewById(R.id.mc_edit);
		listview = (ListView) findViewById(R.id.listView);
		mAdepter = new DeliveryResourceAdepter(OfflineSearchActivity.this,deliveryResourceList);
		listview.setAdapter(mAdepter);
		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


				Intent data = new Intent();
				data.putExtra("name", deliveryResourceList.get(position));
				data.putExtra("type", mType);
				setResult(19, data);
				finish();



			}
		});
	}

	class QueryRes{
		public String  county;
		public String  city;
		public String  resName;
		public String  errorResType;
		public QueryRes(){}

	}
	private boolean isKong(String s){
		if(s == null || s.isEmpty()){
			return true;
		}
		return false;
	}
	private void getData() {

		if(isKong(mc_edit.getText().toString())){
			Toast.makeText(OfflineSearchActivity.this, "资源名称必填", Toast.LENGTH_SHORT).show();
			return;
		}
		if (this.mProgress == null) {
			this.mProgress = ProgressDialog.show(this, "系统提示", "正在加载资源数据...");
		} else {
			this.mProgress.setMessage("正在加载资源数据...");
			this.mProgress.show();
		}
		new Thread(new Runnable() {

			@Override
			public void run() {

				Message message =  Message.obtain();
				try{
					String mSQL;
					if(mType.equals("传输网元")){
						mSQL = "select a.int_id as resId,a.zh_label as resName from RMS_TRANS_ELEMENT a left join RMS_City b on a.city_id = b.int_id left join RMS_County c on a.county_id=c.int_id where a.stateflag = 0 and b.stateflag = 0 and c.stateflag = 0 and b.zh_label = '"+mCity+"' and c.zh_label = '"+mCountry+"' and a.zh_label like '%"+mc_edit.getText().toString()+"%'";
					}else{
						mSQL = "select a.int_id as resId,a.zh_label as resName from RES_OPTI_CAB_SEG a left join RMS_City b on a.city_id = b.int_id left join RMS_County c on a.county_id=c.int_id where a.stateflag = 0 and b.stateflag = 0 and c.stateflag = 0 and b.zh_label = '"+mCity+"' and c.zh_label = '"+mCountry+"' and a.zh_label like '%"+mc_edit.getText().toString()+"%'";
					}

					Cursor cur = wangDB.rawQuery(mSQL, null);
					deliveryResourceList.clear();
					while(cur.moveToNext()){
						String mName = cur.getString(cur.getColumnIndex("resName"));
						String mId = cur.getString(cur.getColumnIndex("resId"));
						Resours resours = new Resours();
						resours.setResId(mId);
						resours.setResName(mName);

						deliveryResourceList.add(resours);

						resours = null;
					}

					message.what = 1;
					handler.sendMessage(message);

				}catch(Exception e){

					message.what = 2;
					message.obj = "查询失败，请重试!";
					handler.sendMessage(message);
				}

			}
		}).start();

	}

	public class DeliveryResourceAdepter extends BaseAdapter{
		private Context mContext;
		private List<Resours> mList;

		private DeliveryResourceAdepter(Context context, List<Resours> list) {
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
				inflate = LayoutInflater.from(mContext).inflate(R.layout.deliveryresource_list_item, null);
				tag = new ViewHolder();
				tag.name = (TextView)inflate.findViewById(R.id.name);
				tag.type = (TextView)inflate.findViewById(R.id.type);
				inflate.setTag((Object)tag);
			}
			else {
				tag = (ViewHolder)inflate.getTag();
			}


			if (mList.size() > 0) {
				final Resours resourceInfoBean = mList.get(n);
				if (!StringUtils.isEmpty(resourceInfoBean.getResName())) {
					tag.name.setText(resourceInfoBean.getResName());
				}else{

				}





			}
			return inflate;
		}

		public class ViewHolder
		{
			public TextView name;
			public TextView type;
		}
	}

	public void chaxun(final View view) {
		if(ZSLConst.useFalseData){
			List<ResourceInfoBean> mResourceInfoBeanList = FalseDataFactory.createResourceInfoBeanList((String)zylx_spinner.getSelectedItem(), new LatLng(ZSLConst.curLocation.getLatitude(), ZSLConst.curLocation.getLongitude()));
			Message message = new Message();
			message.what = 1;
			message.obj = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss Z").create().toJson(mResourceInfoBeanList);
			handler.sendMessage(message);
			return;
		}
		(resourceInfo = new ResourceInfoBean()).setResourceName(mc_edit.getText().toString());
		resourceInfo.setResourceType((String)zylx_spinner.getSelectedItem());
		resourceInfo.setLatitude(latitude);
		resourceInfo.setLongitude(longitude);
		getData();
	}

}
