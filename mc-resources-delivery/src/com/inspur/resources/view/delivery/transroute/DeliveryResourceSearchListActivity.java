package com.inspur.resources.view.delivery.transroute;

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
import com.inspur.resources.view.delivery.transroute.bean.ResourceInfoBean;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
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

public class DeliveryResourceSearchListActivity extends BaseActivity {
	private Spinner zylx_spinner;
	private EditText mc_edit;
	private ListView listview;
	private DeliveryResourceAdepter mAdepter;
	private List<ResourceInfoBean> deliveryResourceList = new ArrayList<ResourceInfoBean>();
	private ProgressDialog mProgress;
	private ResourceInfoBean resourceInfo;
	private double longitude,latitude;
	private Handler handler = new Handler(new Handler.Callback() {

		public boolean handleMessage(Message msg) {
			if (mProgress != null) {
				mProgress.dismiss();
			}
			switch (msg.what) {
				case 1:
					deliveryResourceList.clear();
					List<ResourceInfoBean> list = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss Z").create().fromJson(msg.obj.toString(), new TypeToken<List<ResourceInfoBean>>() {}.getType());
					deliveryResourceList.addAll(list);
					mAdepter.notifyDataSetChanged();
					break;
				case 2:
					Toast.makeText(DeliveryResourceSearchListActivity.this, msg.obj.toString(), Toast.LENGTH_SHORT).show();
					break;
			}
			return true;
		}

	});

	@Override
	protected void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		setContentView(R.layout.activty_delivery_resource_select);
		setTitle("资源查询");
		init();
	}

	private void init(){
		zylx_spinner = (Spinner) findViewById(R.id.zylx_spinner);
		ArrayAdapter<CharSequence> zylxAdapter = ArrayAdapter.createFromResource(this, R.array.ziyuanleixing,
				android.R.layout.simple_spinner_item);
		zylx_spinner.setAdapter(zylxAdapter);
		mc_edit = (EditText) findViewById(R.id.mc_edit);
		listview = (ListView) findViewById(R.id.listView);
		mAdepter = new DeliveryResourceAdepter(DeliveryResourceSearchListActivity.this,deliveryResourceList);
		listview.setAdapter(mAdepter);
		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Intent data = new Intent();
				data.putExtra("deliveryResource", deliveryResourceList.get(position));
				setResult(RESULT_OK, data);
				finish();
			}
		});
	}

	private void getData() {
		if (this.mProgress == null) {
			this.mProgress = ProgressDialog.show(this, "系统提示", "正在加载资源数据...");
		} else {
			this.mProgress.setMessage("正在加载资源数据...");
			this.mProgress.show();
		}
		new Thread() {
			@Override
			public void run() {
				resourceInfo.setLatitude(ZSLConst.curGpsLocation.getLatitude());//37.60359
				resourceInfo.setLongitude(ZSLConst.curGpsLocation.getLongitude());//114.6023
				final String json = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss Z").create().toJson(resourceInfo);
				System.out.println("ResultStr="+json);
				final ArrayList<NameValuePair> list = new ArrayList<NameValuePair>();
				list.add((NameValuePair) new BasicNameValuePair("jsonRequest", json));
				final String httpGetData = new httpconnect().httpGetData("pdaMainTask!getRalitonRes.interface", list,
						DeliveryResourceSearchListActivity.this);
				Log.d("AddrList==>", "result==>" + httpGetData);
				System.out.println("ResultStr="+httpGetData);
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

	public class DeliveryResourceAdepter extends BaseAdapter{
		private Context mContext;
		private List<ResourceInfoBean> mList;

		private DeliveryResourceAdepter(Context context, List<ResourceInfoBean> list) {
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
				final ResourceInfoBean resourceInfoBean = mList.get(n);
				if (!StringUtils.isEmpty(resourceInfoBean.getResourceName())) {
					tag.name.setText(resourceInfoBean.getResourceName());
				}
				if (!StringUtils.isEmpty(resourceInfoBean.getResourceType())) {
					tag.type.setText(resourceInfoBean.getResourceType());
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
