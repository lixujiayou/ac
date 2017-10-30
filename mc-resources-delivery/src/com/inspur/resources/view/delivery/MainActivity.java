package com.inspur.resources.view.delivery;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult.AddressComponent;
import com.inspur.easyresources.R;
import com.inspur.resources.base.BaseActivity;
import com.inspur.resources.base.DemoApplication;
import com.inspur.resources.gpsservice.Gps;
import com.inspur.resources.gpsservice.UtilTool;
import com.inspur.resources.utils.ApplicationValue;
import com.inspur.resources.view.delivery.guang.MapTemp;
import com.inspur.resources.view.delivery.guang.ZSLGuangLine;
import com.inspur.resources.view.delivery.offline.MainOfflineActivity;
import com.inspur.resources.view.delivery.transroute.JingSelectActivity;
import com.inspur.resources.view.delivery.transroute.ZSLConst;
import com.inspur.resources.view.delivery.transroute.ZSLTransmissionLine;

import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public  class MainActivity extends BaseActivity {
	
	 	
	    
	    /* static {	
	    	 MainActivity.locData = null;
	    	 MainActivity.addr = "";
	    	 MainActivity.myWellNameAddress = "";
	    	 MainActivity.mSearch = null;
	    	 MainActivity.lasthandler = null;
	     }*/

	@Override
	protected void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		setContentView(R.layout.activity_delivery_main);

		//   this.myListener = new MyLocationListenner();



	}




	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();

	}


	@Override
	protected boolean isUseDefaultToolBar() {
		return false;
	}

	public void chuanshu_onc(View v) {
		if (!UtilTool.isGpsEnabled((LocationManager) getSystemService(Context.LOCATION_SERVICE))) {
			Toast.makeText(this, "GPS当前已禁用，请在您的系统设置屏幕启动.", Toast.LENGTH_LONG).show();
			Intent callGPSSettingIntent = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
			startActivity(callGPSSettingIntent);
			return;
		}
		Intent intent = new Intent(MainActivity.this, ZSLTransmissionLine.class);
		//Intent intent = new Intent(MainActivity.this, MapTemp.class);
		intent.putExtra("type", false);
		startActivity(intent);
		//startActivity(new Intent(MainActivity.this, ZSLTransmissionLine.class));
	}

	public void jizhanjishebei_onc(View v) {
		if (!UtilTool.isGpsEnabled((LocationManager) getSystemService(Context.LOCATION_SERVICE))) {
			Toast.makeText(this, "GPS当前已禁用，请在您的系统设置屏幕启动.", Toast.LENGTH_LONG).show();
			Intent callGPSSettingIntent = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
			startActivity(callGPSSettingIntent);
			return;
		}
		startActivity(new Intent(MainActivity.this, ZSLGuangLine.class));
	}

	public void layuanzhan_onc(View v) {
		startActivity(new Intent(MainActivity.this, MainOfflineActivity.class));
	}

	public void zhifangzhan_onc(View v) {

	}

	public void redian_onc(View v) {


	}


}
