package com.inspur.resources.view.delivery.transroute;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;
import org.simple.eventbus.EventBus;

import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.model.LatLng;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.inspur.Const;
import com.inspur.StringUtils;
import com.inspur.resources.http.httpconnect;
import com.inspur.resources.view.delivery.transroute.bean.PointlikeResourceInfoBean;
import com.inspur.resources.view.delivery.transroute.bean.ResourceInfoBean;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Toast;
import cn.trinea.android.common.util.ToastUtils;

/**
 * @author Dell-
 *周边一定范围内的资源搜索任务
 */
public class AroundResourceSearchTask extends AsyncTask<String, String, String>
{

	private Context context;
	private MapView mapView;
	private LatLng center;

	private ResourceInfoBean mResourceInfoBean;
	private ProgressDialog pd;


	public AroundResourceSearchTask(Context context, MapView mapView, LatLng center)
	{
		super();
		this.context = context;
		this.mapView = mapView;
		this.center = center;

		mResourceInfoBean = new ResourceInfoBean();
		mResourceInfoBean.setLatitude(center.latitude);
		mResourceInfoBean.setLongitude(center.longitude);
	}

	@Override
	protected void onPreExecute()
	{
		pd = new ProgressDialog(context, ProgressDialog.STYLE_SPINNER);
		pd.setCanceledOnTouchOutside(false);
		pd.setMessage("正在获取周边资源点，请稍后...");
		pd.show();
		super.onPreExecute();
	}

	@Override
	protected String doInBackground(String... params)
	{
		if(ZSLConst.useFalseData)
		{
			try
			{
				Thread.sleep(1500);
			} catch (InterruptedException e)
			{
				e.printStackTrace();
			}
			return "";
		}

		String json = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss Z").create().toJson(mResourceInfoBean);
		System.out.println(json);
		final ArrayList<NameValuePair> list = new ArrayList<NameValuePair>();
		list.add((NameValuePair)new BasicNameValuePair("jsonRequest", json));
		final String httpGetData = new httpconnect().httpGetData("pdaMainTask!getRalitonRes.interface", list, context);
		return httpGetData;
	}

	@Override
	protected void onPostExecute(String httpGetData)
	{
		pd.dismiss();
		if(ZSLConst.useFalseData)
		{
			List<ResourceInfoBean> mResourceInfoBeanList = FalseDataFactory.createResourceInfoBeanList(mapView, center);
			EventBus.getDefault().post(mResourceInfoBeanList, ZSLConst.tag_onResourceInfoBeanList_get_ok);
			return;
		}


		String info = null;
		String result = null;
		if(StringUtils.isEmpty(httpGetData)){
			Toast.makeText(context, "服务端异常!", Toast.LENGTH_SHORT).show();
			return;
		}
		try
		{
			System.out.println(httpGetData);
			JSONObject jsonObject = new JSONObject(httpGetData);
			info = jsonObject.getString("info");
			result = jsonObject.getString("result");
		} catch (JSONException e)
		{
			e.printStackTrace();
		}

		if("0".equals(result))
		{
			final Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss Z").create();
			List<ResourceInfoBean> resourceList = (List<ResourceInfoBean>)gson.fromJson(info, new TypeToken<List<ResourceInfoBean>>() {}.getType());
			EventBus.getDefault().post(resourceList, ZSLConst.tag_onResourceInfoBeanList_get_ok);
		}else
		{
			ToastUtils.show(context, "资源信息获取失败");
		}
		super.onPostExecute(httpGetData);
	}

}
