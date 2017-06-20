package com.inspur.resources.gpsservice;

import java.util.HashMap;
import java.util.Map;

import com.inspur.resources.utils.MyLog;
import com.inspur.resources.view.delivery.transroute.ZSLConst;

import android.app.Service;
import android.content.Intent;
import android.location.Location;
import android.os.IBinder;
import android.util.Log;

public class GpsService extends Service {
	private Gps gps = null;
	private boolean threadDisable = false;
	private final static String TAG = GpsService.class.getSimpleName();
	private Map<String,Location> map = new HashMap<String,Location>();

	@Override
	public void onCreate() {
		super.onCreate();

		gps = new Gps(GpsService.this);

		new Thread(new Runnable() {
			@Override
			public void run() {
				while (!threadDisable) {
					try {
						Thread.sleep(2000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}

					if (gps != null) { // 当结束服务时gps为空
						// 获取经纬度
						/*Location location = gps.getLocation();
						System.out.println("gps获取到经纬度："+location);
						MyLog.i("loc", "gps获取到经纬度："+location);*/
						Location location = null;

						// 如果gps无法获取经纬度，改用百度坐标转换而来
						if (location == null) {
							if (ZSLConst.curLocation != null) {
//								System.out.println("百度经纬度："+ZSLConst.curLocation);
//								MyLog.i("loc", "百度经纬度："+ZSLConst.curLocation.getLatitude()+","+ZSLConst.curLocation.getLongitude());

								Log.v(TAG, "gps location null");
								// 2.把百度坐标通过纠偏数据库转换为gps坐标
								String key = ZSLConst.curLocation.getLatitude()+"-"+ZSLConst.curLocation.getLongitude();
								if(map.containsKey(key)){
									location = map.get(key);
									ZSLConst.curGpsLocation = location;
									continue;
								}

								try {
									location = UtilTool.callGear(GpsService.this, ZSLConst.curLocation.getLatitude(),
											ZSLConst.curLocation.getLongitude());
								} catch (Exception e) {
									location = null;
									e.printStackTrace();
								}
								if(location!=null){
									map.put(key, location);
								}
								System.out.println("转换接口获取到经纬度："+location);
								MyLog.i("loc", "转换接口获取到经纬度："+location);

							}
						}
						if (location != null) {
							ZSLConst.curGpsLocation = location;
						}
					}
				}
			}
		}).start();

	}

	@Override
	public void onDestroy() {
		threadDisable = true;
		if (gps != null) {
			gps.closeLocation();
			gps = null;
		}
		super.onDestroy();
	}

	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}




}