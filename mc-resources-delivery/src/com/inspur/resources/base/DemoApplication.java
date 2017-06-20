package com.inspur.resources.base;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult.AddressComponent;
import com.inspur.CatchExceptionLog;
import com.inspur.component.androidlifecyle.ActivityLifecycleCallbacksCompat;
import com.inspur.component.androidlifecyle.LifecycleDispatcher;
import com.inspur.resources.utils.ApplicationValue;
import com.yolanda.nohttp.Logger;
import com.yolanda.nohttp.NoHttp;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

public class DemoApplication extends Application  implements OnGetGeoCoderResultListener
{
    public static String addr;
    private static Handler lasthandler;
    public static MyLocationData locData;
    public static GeoCoder mSearch;
    public static String myWellNameAddress;
    public static LocationClient mLocClient;
    public MyLocationListenner myListener;
    public static AddressComponent mAddressComponent = null;

    private String curVersionName;
    private int curVersionCode;


    static {
        DemoApplication.locData = null;
        DemoApplication.addr = "";
        DemoApplication.myWellNameAddress = "";
        DemoApplication.mSearch = null;
        DemoApplication.lasthandler = null;
    }

    public DemoApplication() {
        this.myListener = new MyLocationListenner();
    }

    public void onCreate() {
        super.onCreate();
       CatchExceptionLog.catchException(this);
         LifecycleDispatcher.registerActivityLifecycleCallbacks(this, lifecycleCallback);
      SDKInitializer.initialize(getApplicationContext());
        if(DemoApplication.mAddressComponent == null){
            this.mapSet();
        }
        NoHttp.init(this);
        Logger.setTag("resource_delivery");
        Logger.setDebug(false);//开始NoHttp的调试模式, 这样就能看到请求过程和日志
    }

    ActivityLifecycleCallbacksCompat lifecycleCallback = new ActivityLifecycleCallbacksCompat() {

        @Override
        public void onActivityStopped(Activity paramActivity) {
        }

        @Override
        public void onActivityStarted(Activity paramActivity) {
        }

        @Override
        public void onActivitySaveInstanceState(Activity paramActivity, Bundle paramBundle) {
        }

        @Override
        public void onActivityResumed(Activity paramActivity) {
        }

        @Override
        public void onActivityPaused(Activity paramActivity) {
        }

        @Override
        public void onActivityDestroyed(Activity paramActivity) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onActivityCreated(Activity paramActivity, Bundle paramBundle) {
            // TODO Auto-generated method stub

        }
    };


    public static void fcode(final double n, final double n2, final Handler lasthandler) {
        DemoApplication.lasthandler = lasthandler;
//        Log.d("\u5750\u68071\uff01\uff01\uff01", String.valueOf(n) + "," + n2);
        DemoApplication.mSearch.reverseGeoCode(new ReverseGeoCodeOption().location(new LatLng(n, n2)));
//        Log.d("addr1+++++++++", String.valueOf(DemoApplication.addr) + "00");
    }

    public void mapSet() {
        Log.d("-------", "mapSet");
        (DemoApplication.mSearch = GeoCoder.newInstance()).setOnGetGeoCodeResultListener(this);
        (this.mLocClient = new LocationClient((Context)this)).registerLocationListener(this.myListener);
        final LocationClientOption locOption = new LocationClientOption();

        locOption.setOpenGps(true);
        locOption.setCoorType("bd09ll");
        //   locOption.setScanSpan(60*1000);
        locOption.setScanSpan(0);
        this.mLocClient.setLocOption(locOption);
        this.mLocClient.start();

    }

    public void onGetGeoCodeResult(final GeoCodeResult geoCodeResult) {
//    	Log.d("=====", "onGetGeoCodeResult");
        if (geoCodeResult == null || geoCodeResult.error != SearchResult.ERRORNO.NO_ERROR) {
            Toast.makeText((Context)this, (CharSequence)"抱歉，未能找到结果", Toast.LENGTH_SHORT).show();
            return;
        }
        Toast.makeText((Context)this, (CharSequence)String.format("\u7eac\u5ea6\uff1a%f \u7ecf\u5ea6\uff1a%f", geoCodeResult.getLocation().latitude, geoCodeResult.getLocation().longitude), Toast.LENGTH_SHORT).show();
    }

    public void onGetReverseGeoCodeResult(final ReverseGeoCodeResult obj) {
//    	Log.d("=====", "onGetReverseGeoCodeResult");
        if (obj == null || obj.error != SearchResult.ERRORNO.NO_ERROR) {
//            Toast.makeText((Context)this, (CharSequence)"抱歉，未能找到结果", 1).show();
        }
        else {
            DemoApplication.addr = obj.getAddress();
            ApplicationValue.nowAddress = DemoApplication.addr;
//            Log.d("重载里的addr", new StringBuilder(String.valueOf(obj.getAddress())).toString());
            if (obj.getAddressDetail() != null) {
                DemoApplication.mAddressComponent = obj.getAddressDetail();
                DemoApplication.myWellNameAddress = String.valueOf(obj.getAddressDetail().province) + obj.getAddressDetail().city + obj.getAddressDetail().district + obj.getAddressDetail().street;
                ApplicationValue.jfSuoshuwhqu = String.valueOf(obj.getAddressDetail().province) + obj.getAddressDetail().city + obj.getAddressDetail().district;
                if(lasthandler!=null){
                    final Message message = new Message();
                    message.what = 103;
                    message.obj = obj;
                    DemoApplication.lasthandler.sendMessage(message);
                }
            }
        }
    }

    public class MyLocationListenner implements BDLocationListener
    {
        @Override
        public void onReceiveLocation(final BDLocation bdLocation) {
//        	Log.d("=====", "onReceiveLocation");
            if(DemoApplication.mAddressComponent == null){
                Log.d("lixu","定位当前位置======= 1");

                if (bdLocation == null) {
                    return;
                }
                DemoApplication.locData = new MyLocationData.Builder().accuracy(bdLocation.getRadius()).direction(100.0f).latitude(bdLocation.getLatitude()).longitude(bdLocation.getLongitude()).build();
                ApplicationValue.lat = DemoApplication.locData.latitude;
                ApplicationValue.lon = DemoApplication.locData.longitude;
                Log.d("lixu","定位当前位置=======");

                fcode(ApplicationValue.lat,ApplicationValue.lon,null);

            }else{
                Log.d("lixu","定位当前位置=======2");
            }
        }
    }

}
