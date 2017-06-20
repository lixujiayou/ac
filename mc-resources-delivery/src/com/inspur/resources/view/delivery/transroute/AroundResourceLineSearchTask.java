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
import com.inspur.resources.utils.MyLog;
import com.inspur.resources.view.delivery.transroute.bean.PointlikeResourceInfoBean;
import com.inspur.resources.view.delivery.transroute.bean.ResourceInfoBean;
import com.inspur.resources.view.delivery.transroute.bean.ResourceLineBean;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Toast;
import cn.trinea.android.common.util.ToastUtils;

/**
 *周边一定范围内的资源点及线搜索任务
 */
public class AroundResourceLineSearchTask extends AsyncTask<String, String, String>
{

	private Context context;
	private LatLng center;

	private ProgressDialog pd;


	public AroundResourceLineSearchTask(Context context,LatLng center)
	{
		super();
		this.context = context;
		this.center = center;

	}

	@Override
	protected void onPreExecute()
	{
		pd = new ProgressDialog(context, ProgressDialog.STYLE_SPINNER);
		pd.setCanceledOnTouchOutside(false);
		pd.setMessage("正在获取周边资源，请稍后...");
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

//		String json = "{\"latitude\":"+center.latitude+",\"longitude\":"+center.longitude+"}";
		String json = "{\"latitude\":"+ZSLConst.curGpsLocation.getLatitude()+",\"longitude\":"+ZSLConst.curGpsLocation.getLongitude()+"}";
//		String json = "{\"latitude\":"+37.60359+",\"longitude\":"+114.6023+"}";

//		String json = "{\"latitude\":"+38.040757+",\"longitude\":"+114.621817+"}";
		System.out.println(json);
//		MyLog.i("loc", "请求数据"+json);
		final ArrayList<NameValuePair> list = new ArrayList<NameValuePair>();
		list.add((NameValuePair)new BasicNameValuePair("jsonRequest", json));
		final String httpGetData = new httpconnect().httpGetData("pdaMainTask!getSegInfo.interface", list, context);
		return httpGetData;
	}

	@Override
	protected void onPostExecute(String httpGetData)
	{
		pd.dismiss();

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
//			info = "[{\"start\":{\"resourceID\":360088289,\"longitude\":114.61581,\"latitude\":38.039591,\"resourceName\":\"石家庄市开发区生产楼局前管道4号井\",\"resourceType\":\"管井\"},\"relatedBranch\":597220655,\"end\":{\"resourceID\":360087212,\"longitude\":114.615632,\"latitude\":38.039584,\"resourceName\":\"石家庄市开发区生产楼局前管道3号井\",\"resourceType\":\"管井\"}},{\"start\":{\"resourceID\":360087212,\"longitude\":114.615632,\"latitude\":38.039584,\"resourceName\":\"石家庄市开发区生产楼局前管道3号井\",\"resourceType\":\"管井\"},\"relatedBranch\":597220655,\"end\":null},{\"start\":{\"resourceID\":360094412,\"longitude\":114.616067,\"latitude\":38.039676,\"resourceName\":\"石家庄市开发区生产楼局前管道5号井\",\"resourceType\":\"管井\"},\"relatedBranch\":597220655,\"end\":{\"resourceID\":360088289,\"longitude\":114.61581,\"latitude\":38.039591,\"resourceName\":\"石家庄市开发区生产楼局前管道4号井\",\"resourceType\":\"管井\"}},{\"start\":null,\"relatedBranch\":597221111,\"end\":{\"resourceID\":360093264,\"longitude\":114.61569,\"latitude\":38.04024,\"resourceName\":\"石家庄市开发区传输二楼管道1号井\",\"resourceType\":\"管井\"}},{\"start\":null,\"relatedBranch\":597223536,\"end\":{\"resourceID\":360095172,\"longitude\":114.616044,\"latitude\":38.039424,\"resourceName\":\"石家庄市传输机房管道3号井\",\"resourceType\":\"管井\"}},{\"start\":{\"resourceID\":360095172,\"longitude\":114.616044,\"latitude\":38.039424,\"resourceName\":\"石家庄市传输机房管道3号井\",\"resourceType\":\"管井\"},\"relatedBranch\":597223536,\"end\":{\"resourceID\":360095171,\"longitude\":114.616153,\"latitude\":38.039467,\"resourceName\":\"石家庄市传输机房管道2号井\",\"resourceType\":\"管井\"}},{\"start\":{\"resourceID\":360095171,\"longitude\":114.616153,\"latitude\":38.039467,\"resourceName\":\"石家庄市传输机房管道2号井\",\"resourceType\":\"管井\"},\"relatedBranch\":597223536,\"end\":{\"resourceID\":360095170,\"longitude\":114.616318,\"latitude\":38.0396,\"resourceName\":\"石家庄市传输机房管道1号井\",\"resourceType\":\"管井\"}},{\"start\":null,\"relatedBranch\":597224810,\"end\":{\"resourceID\":360089579,\"longitude\":114.616973,\"latitude\":38.039707,\"resourceName\":\"石家庄市开发区生产楼局前管道7号井\",\"resourceType\":\"管井\"}},{\"start\":{\"resourceID\":360089579,\"longitude\":114.616973,\"latitude\":38.039707,\"resourceName\":\"石家庄市开发区生产楼局前管道7号井\",\"resourceType\":\"管井\"},\"relatedBranch\":597224810,\"end\":{\"resourceID\":360088290,\"longitude\":114.6163,\"latitude\":38.039767,\"resourceName\":\"石家庄市开发区生产楼局前管道6号井\",\"resourceType\":\"管井\"}},{\"start\":{\"resourceID\":360088290,\"longitude\":114.6163,\"latitude\":38.039767,\"resourceName\":\"石家庄市开发区生产楼局前管道6号井\",\"resourceType\":\"管井\"},\"relatedBranch\":597220655,\"end\":{\"resourceID\":360094412,\"longitude\":114.616067,\"latitude\":38.039676,\"resourceName\":\"石家庄市开发区生产楼局前管道5号井\",\"resourceType\":\"管井\"}},{\"start\":{\"resourceID\":360088290,\"longitude\":114.6163,\"latitude\":38.039767,\"resourceName\":\"石家庄市开发区生产楼局前管道6号井\",\"resourceType\":\"管井\"},\"relatedBranch\":597223536,\"end\":{\"resourceID\":360095170,\"longitude\":114.616318,\"latitude\":38.0396,\"resourceName\":\"石家庄市传输机房管道1号井\",\"resourceType\":\"管井\"}},{\"start\":{\"resourceID\":599123632,\"longitude\":114.61591,\"latitude\":38.04036,\"resourceName\":\"石家庄裕华区yxj测试0004号人井\",\"resourceType\":\"管井\"},\"relatedBranch\":599123654,\"end\":null},{\"start\":null,\"relatedBranch\":599123654,\"end\":{\"resourceID\":599123632,\"longitude\":114.61591,\"latitude\":38.04036,\"resourceName\":\"石家庄裕华区yxj测试0004号人井\",\"resourceType\":\"管井\"}},{\"start\":{\"resourceID\":599123630,\"longitude\":114.61591,\"latitude\":38.04036,\"resourceName\":\"石家庄裕华区yxj测试0002号人井测试\",\"resourceType\":\"管井\"},\"relatedBranch\":599123654,\"end\":null},{\"start\":null,\"relatedBranch\":599123654,\"end\":{\"resourceID\":599123630,\"longitude\":114.61591,\"latitude\":38.04036,\"resourceName\":\"石家庄裕华区yxj测试0002号人井测试\",\"resourceType\":\"管井\"}},{\"start\":{\"resourceID\":360093264,\"longitude\":114.61569,\"latitude\":38.04024,\"resourceName\":\"石家庄市开发区传输二楼管道1号井\",\"resourceType\":\"管井\"},\"relatedBranch\":0,\"end\":{\"resourceID\":541048912,\"longitude\":114.61578,\"latitude\":38.04037,\"resourceName\":\"石家庄裕华区审计局\",\"resourceType\":\"站点\"}},{\"start\":{\"resourceID\":360093264,\"longitude\":114.61569,\"latitude\":38.04024,\"resourceName\":\"石家庄市开发区传输二楼管道1号井\",\"resourceType\":\"管井\"},\"relatedBranch\":0,\"end\":{\"resourceID\":541048912,\"longitude\":114.61578,\"latitude\":38.04037,\"resourceName\":\"石家庄裕华区审计局\",\"resourceType\":\"站点\"}}]";
			result = jsonObject.getString("result");
		} catch (JSONException e)
		{
			e.printStackTrace();
		}

		if("0".equals(result))
		{
			final Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss Z").create();
			List<ResourceLineBean> resourceList = (List<ResourceLineBean>)gson.fromJson(info, new TypeToken<List<ResourceLineBean>>() {}.getType());
			EventBus.getDefault().post(resourceList, ZSLConst.tag_onResourceLineBeanList_get_ok);
		}else
		{
//			EventBus.getDefault().post(new ArrayList<ResourceLineBean>(), ZSLConst.tag_onResourceLineBeanList_get_ok);
			ToastUtils.show(context, info);
		}
		super.onPostExecute(httpGetData);
	}

}
