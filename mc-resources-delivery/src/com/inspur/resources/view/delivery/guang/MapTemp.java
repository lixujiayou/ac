package com.inspur.resources.view.delivery.guang;

import org.simple.eventbus.EventBus;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.inspur.easyresources.R;
import com.inspur.resources.view.delivery.transroute.JingSelectActivity;
import com.inspur.resources.view.delivery.transroute.ZSLConst;
import com.inspur.resources.view.delivery.transroute.ZSLTransmissionLine;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MapTemp extends Activity{

	private MapView mv;
	private BaiduMap baiduMap;
	private Button btn;

	/*
	private LocationClient mLocationClient = null;
	private BDLocationListener myListener = new MyLocationListener();
	private BDLocation lastLocation;// 保存最后一次定位到的位置信息
*/
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		//	  SDKInitializer.initialize(this);
		setContentView(R.layout.activity_map_temp);
		mv = (MapView) findViewById(R.id.zsl_mapview);
		btn = (Button) findViewById(R.id.btn);

		btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent gIntent = new Intent(MapTemp.this,JingSelectActivity.class);
				gIntent.putExtra("type", false);
				startActivity(gIntent);
			}
		});
		/*	baiduMap = mv.getMap();

		    mLocationClient = new LocationClient(this);
	        mLocationClient.registerLocationListener(myListener);
	        LocationClientOption option = new LocationClientOption();
	        option.setOpenGps(true); // 打开gps
	        option.setCoorType("bd09ll"); // 设置坐标类型
	        option.setScanSpan(0);
	        mLocationClient.setLocOption(option);
	        mLocationClient.start();*/
	}


	/*private boolean isFirstLoc = true;
	public class MyLocationListener implements BDLocationListener {
		@Override
		public void onReceiveLocation(BDLocation location) {
			 if (location == null || mv == null) {
	                return;
	            }

			double lat = location.getLatitude();
			double lng = location.getLongitude();
			MapStatusUpdate update = MapStatusUpdateFactory.newLatLng(new LatLng(lat, lng));
			baiduMap.animateMapStatus(update);
			ZSLConst.curLocation = lastLocation;

			MyLocationData locData = new MyLocationData.Builder()
            .accuracy(location.getRadius())
                    //此处设置开发者获取到的方向信息，顺时针0-360
            .direction(100).latitude(location.getLatitude())
            .longitude(location.getLongitude()).build();
			
			baiduMap.setMyLocationData(locData);
    
    if (isFirstLoc) {
        isFirstLoc = false;
        LatLng ll = new LatLng(location.getLatitude(),
                location.getLongitude());
        MapStatus.Builder builder = new MapStatus.Builder();
        builder.target(ll).zoom(18.0f);
        baiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
    }
			
			
		}
	}*/



	@Override
	protected void onResume() {
		mv.onResume();
		super.onResume();
	}

	@Override
	protected void onPause() {
		mv.onPause();
		super.onPause();
	}

	@Override
	protected void onDestroy() {
//		mLocationClient.stop();
		mv.onDestroy();
		super.onDestroy();
	}



}
