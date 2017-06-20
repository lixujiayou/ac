package com.inspur.common;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.inspur.component.wheel.OnWheelChangedListener;
import com.inspur.component.wheel.WheelView;
import com.inspur.component.wheel.adapters.ArrayWheelAdapter;
import com.inspur.easyresources.R;
import com.inspur.resources.base.DemoApplication;
import com.inspur.resources.utils.ApplicationValue;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class RegionWheelDialog extends Activity implements OnWheelChangedListener
{
	/**
	 * 把全国的省市区的信息以json的格式保存，解析完成后赋值为null
	 */
	private JSONObject mJsonObj;
	/**
	 * 省的WheelView控件
	 */
	private WheelView mProvince;
	/**
	 * 市的WheelView控件
	 */
	private WheelView mCity;
	/**
	 * 区的WheelView控件
	 */
	private WheelView mArea;

	/**
	 * 所有省
	 */
	private String[] mProvinceDatas;
	/**
	 * key - 省 value - 市s
	 */
	private Map<String, String[]> mCitisDatasMap = new HashMap<String, String[]>();
	/**
	 * key - 市 values - 区s
	 */
	private Map<String, String[]> mAreaDatasMap = new HashMap<String, String[]>();

	/**
	 * 当前省的名称
	 */
	private String mCurrentProviceName;
	/**
	 * 当前市的名称
	 */
	private String mCurrentCityName;
	/**
	 * 当前区的名称
	 */
	private String mCurrentAreaName ="";

	private int indexCurProvice =0,indexCurCity=0,indexCurArea=0;


	public void back(final View view) {
		this.finish();
	}

	protected void onCreate(final Bundle bundle) {
		super.onCreate(bundle);
		this.requestWindowFeature(1);
		setContentView(R.layout.dialog_region_wheel);

		initJsonData();

		mProvince = (WheelView) findViewById(R.id.id_province);
		mCity = (WheelView) findViewById(R.id.id_city);
		mArea = (WheelView) findViewById(R.id.id_area);

		initDatas();

		mProvince.setViewAdapter(new ArrayWheelAdapter<String>(this, mProvinceDatas));
		// 添加change事件
		mProvince.addChangingListener(this);
		// 添加change事件
		mCity.addChangingListener(this);
		// 添加change事件
		mArea.addChangingListener(this);

		mProvince.setVisibleItems(5);
		mCity.setVisibleItems(5);
		mArea.setVisibleItems(5);
		mProvince.setCurrentItem(indexCurProvice);
		updateCities();
		updateAreas();
	}

	/**
	 * 根据当前的市，更新区WheelView的信息
	 */
	private void updateAreas()
	{
		int pCurrent = mCity.getCurrentItem();
		mCurrentCityName = mCitisDatasMap.get(mCurrentProviceName)[pCurrent];
		String[] areas = mAreaDatasMap.get(mCurrentCityName);

		if (areas == null)
		{
			areas = new String[] { "" };
		}
		mArea.setViewAdapter(new ArrayWheelAdapter<String>(this, areas));
		mArea.setCurrentItem(indexCurArea);
	}

	/**
	 * 根据当前的省，更新市WheelView的信息
	 */
	private void updateCities()
	{
		int pCurrent = mProvince.getCurrentItem();
		mCurrentProviceName = mProvinceDatas[pCurrent];
		String[] cities = mCitisDatasMap.get(mCurrentProviceName);
		if (cities == null)
		{
			cities = new String[] { "" };
		}
		mCity.setViewAdapter(new ArrayWheelAdapter<String>(this, cities));
		mCity.setCurrentItem(indexCurCity);

		updateAreas();
	}

	/**
	 * 解析整个Json对象，完成后释放Json对象的内存
	 */
	private void initDatas()
	{
		try
		{
			JSONArray jsonArray = mJsonObj.getJSONArray("citylist");
			mProvinceDatas = new String[jsonArray.length()];
			for (int i = 0; i < jsonArray.length(); i++)
			{
				JSONObject jsonP = jsonArray.getJSONObject(i);// 每个省的json对象
				String province = jsonP.getString("p");// 省名字

				mProvinceDatas[i] = province;
				if(DemoApplication.mAddressComponent!=null&&!"".equalsIgnoreCase(DemoApplication.mAddressComponent.province)){
					if(DemoApplication.mAddressComponent.province.startsWith(province)){
						indexCurProvice = i;
					}
				}

				JSONArray jsonCs = null;
				try
				{
					/**
					 * Throws JSONException if the mapping doesn't exist or is
					 * not a JSONArray.
					 */
					jsonCs = jsonP.getJSONArray("c");
				} catch (Exception e1)
				{
					continue;
				}
				String[] mCitiesDatas = new String[jsonCs.length()];
				for (int j = 0; j < jsonCs.length(); j++)
				{
					JSONObject jsonCity = jsonCs.getJSONObject(j);
					String city = jsonCity.getString("n");// 市名字
					mCitiesDatas[j] = city;
					if(DemoApplication.mAddressComponent!=null&&!"".equalsIgnoreCase(DemoApplication.mAddressComponent.city)){
						if(DemoApplication.mAddressComponent.city.startsWith(city)){
							indexCurCity = j;
						}
					}
					JSONArray jsonAreas = null;
					try
					{
						/**
						 * Throws JSONException if the mapping doesn't exist or
						 * is not a JSONArray.
						 */
						jsonAreas = jsonCity.getJSONArray("a");
					} catch (Exception e)
					{
						continue;
					}

					String[] mAreasDatas = new String[jsonAreas.length()];// 当前市的所有区
					for (int k = 0; k < jsonAreas.length(); k++)
					{
						String area = jsonAreas.getJSONObject(k).getString("s");// 区域的名称
						mAreasDatas[k] = area;

						if(DemoApplication.mAddressComponent!=null&&!"".equalsIgnoreCase(DemoApplication.mAddressComponent.district)){
							if(DemoApplication.mAddressComponent.district.startsWith(area)){
								indexCurArea = k;
							}
						}
					}
					mAreaDatasMap.put(city, mAreasDatas);
				}

				mCitisDatasMap.put(province, mCitiesDatas);
			}

		} catch (JSONException e)
		{
			e.printStackTrace();
		}
		mJsonObj = null;
	}

	/**
	 * 从assert文件夹中读取省市区的json文件，然后转化为json对象
	 */
	private void initJsonData()
	{
		try
		{
			StringBuffer sb = new StringBuffer();
			InputStream is = getAssets().open("city.json");
			int len = -1;
			byte[] buf = new byte[1024];
			while ((len = is.read(buf)) != -1)
			{
				sb.append(new String(buf, 0, len, "UTF-8"));
			}
			is.close();
			mJsonObj = new JSONObject(sb.toString());
		} catch (IOException e)
		{
			e.printStackTrace();
		} catch (JSONException e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * change事件的处理
	 */
	@Override
	public void onChanged(WheelView wheel, int oldValue, int newValue)
	{
		if (wheel == mProvince)
		{
			updateCities();
		} else if (wheel == mCity)
		{
			updateAreas();
		} else if (wheel == mArea)
		{
			mCurrentAreaName = mAreaDatasMap.get(mCurrentCityName)[newValue];
		}
	}

	public void submit(final View view) {
		final Intent intent = new Intent();
		String[] areaArray = mAreaDatasMap.get(mCurrentCityName);
//        String area = areaArray==null||areaArray.length>0?("".equals(mCurrentAreaName)?"":mCurrentAreaName):areaArray[mArea.getCurrentItem()];
		String area= "".equals(mCurrentAreaName)?(areaArray!=null&&areaArray.length>0?areaArray[mArea.getCurrentItem()]:""):mCurrentAreaName;
		intent.putExtra("region", mCurrentProviceName + mCurrentCityName + area);

		intent.putExtra("provice", mCurrentProviceName);
		intent.putExtra("city", mCurrentCityName);
		intent.putExtra("area", area);
		this.setResult(RESULT_OK, intent);
		this.finish();
	}

}
