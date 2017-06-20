package com.inspur.resources.view.map;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.offline.MKOfflineMap;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.model.LatLngBounds;
import com.inspur.easyresources.R;
import com.inspur.resources.base.BaseActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

public class OverLayDemo extends BaseActivity
{
    BitmapDescriptor bdA;
    boolean isFirstLoc;
    String lat;
    private String latIn;
    private String latNew;
    String lon;
    private String lonIn;
    private String lonNew;
    private BaiduMap mBaiduMap;
    LocationClient mLocClient;
    private MapView mMapView;
    private Marker mMarkerA;
    private MKOfflineMap mOffline;
    public MyLocationListenner myListener;
    private float radius;
    
    public OverLayDemo() {
        this.latNew = "";
        this.lonNew = "";
        this.mOffline = null;
        this.myListener = new MyLocationListenner();
        this.bdA = BitmapDescriptorFactory.fromResource(2130837655);
        this.isFirstLoc = true;
        this.lat = "";
        this.lon = "";
    }
    
    static /* synthetic */ void access$1(final OverLayDemo overLayDemo, final float radius) {
        overLayDemo.radius = radius;
    }
    
    public void commitOverlay(final View view) {
        final LatLng target = this.mBaiduMap.getMapStatus().target;
        this.latNew = new StringBuilder(String.valueOf(target.latitude)).toString();
        this.lonNew = new StringBuilder(String.valueOf(target.longitude)).toString();
        Log.d("\u7ecf\u7eac\u5ea6", String.valueOf(this.latNew) + "," + this.lonNew);
        final Intent intent = new Intent((Context)this, (Class)LocationSubmitActivity.class);
        String s;
        if (this.latNew.equals("")) {
            s = this.latIn;
        }
        else {
            s = this.latNew;
        }
        intent.putExtra("latstr", s);
        String s2;
        if (this.lonNew.equals("")) {
            s2 = this.lonIn;
        }
        else {
            s2 = this.lonNew;
        }
        intent.putExtra("lonstr", s2);
        intent.putExtra("radius", this.radius);
        this.startActivityForResult(intent, 1);
    }
    
    protected void getNow() {
        this.mBaiduMap.setMyLocationEnabled(true);
        (this.mLocClient = new LocationClient((Context)this)).registerLocationListener(this.myListener);
        final LocationClientOption locOption = new LocationClientOption();
        locOption.setOpenGps(true);
        locOption.setCoorType("bd09ll");
        locOption.setScanSpan(1000);
        this.mLocClient.setLocOption(locOption);
        this.mLocClient.start();
    }
    
    public void getNow(final View view) {
        this.mBaiduMap.clear();
        this.getNow();
        this.initOverlay(this.lat, this.lon);
    }
    
    public void initOverlay(String s, final String s2) {
        String s3 = s;
        if (s.equals("")) {
            s3 = "0.0";
        }
        s = s2;
        if (s2.equals("")) {
            s = "0.0";
        }
        this.mBaiduMap.setMapStatus(MapStatusUpdateFactory.newLatLng(new LatLngBounds.Builder().include(new LatLng(Double.parseDouble(s3), Double.parseDouble(s))).include(new LatLng(Double.parseDouble(s3), Double.parseDouble(s))).build().getCenter()));
        this.mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(new MapStatus.Builder(this.mBaiduMap.getMapStatus()).overlook(0).build()));
        this.mBaiduMap.setBuildingsEnabled(false);
    }
    
    protected void onActivityResult(final int n, final int n2, final Intent intent) {
        super.onActivityResult(n, n2, intent);
        if (n == 1 && intent != null && n2 == -1) {
            final Intent intent2 = new Intent();
            String s;
            if (this.latNew.equals("")) {
                s = this.latIn;
            }
            else {
                s = this.latNew;
            }
            intent2.putExtra("latstr1", s);
            String s2;
            if (this.lonNew.equals("")) {
                s2 = this.lonIn;
            }
            else {
                s2 = this.lonNew;
            }
            intent2.putExtra("lonstr1", s2);
            this.setResult(-1, intent2);
            this.finish();
        }
    }
    
    public void onCreate(final Bundle bundle) {
        super.onCreate(bundle);
        this.setContentView(R.layout.activity_overlay);
        final Intent intent = this.getIntent();
        this.latIn = intent.getStringExtra("latstr");
        this.lonIn = intent.getStringExtra("lonstr");
        this.radius = intent.getFloatExtra("radius", 0.0f);
        intent.getBooleanExtra("isEqu", false);
        (this.mBaiduMap = this.mMapView.getMap()).setMapStatus(MapStatusUpdateFactory.zoomTo(19.0f));
        Log.d("333333333", String.valueOf(this.latIn) + "," + this.lonIn);
        this.initOverlay(this.latIn, this.lonIn);
        this.getWindow().addContentView((View)new MyIconCanves((Context)this), new FrameLayout.LayoutParams(-2, -2));
    }
    
    @Override
    public void onDestroy() {
        this.mMapView.onDestroy();
        super.onDestroy();
        this.bdA.recycle();
    }
    
    @Override
    protected void onPause() {
        this.mMapView.onPause();
        super.onPause();
    }
    
    @Override
    protected void onResume() {
        this.mMapView.onResume();
        super.onResume();
    }
    
    public void resetOverlay(final View view) {
        this.mBaiduMap.clear();
        this.initOverlay(this.latIn, this.lonIn);
    }
    
    class MyIconCanves extends View
    {
        private static final String TAG = "Gao";
        private Bitmap mBitmap;
        
        public MyIconCanves(final Context context) {
            super(context);
//            this.mBitmap = BitmapFactory.decodeResource(this.getResources(), R.drawable.nav_turn_via_2);
            this.mBitmap = BitmapFactory.decodeResource(this.getResources(), R.drawable.datouzhen2);
            
        }
        
        protected void onDraw(final Canvas canvas) {
            super.onDraw(canvas);
            canvas.drawBitmap(this.mBitmap, (float)(this.getWidth() / 2 - this.mBitmap.getWidth() / 2), (float)(this.getHeight() / 2 - this.mBitmap.getHeight() / 2), (Paint)null);
        }
    }
    
    public class MyLocationListenner implements BDLocationListener
    {
        @Override
        public void onReceiveLocation(final BDLocation bdLocation) {
            if (bdLocation != null && OverLayDemo.this.mMapView != null) {
                final MyLocationData build = new MyLocationData.Builder().accuracy(bdLocation.getRadius()).direction(100.0f).latitude(bdLocation.getLatitude()).longitude(bdLocation.getLongitude()).build();
                OverLayDemo.access$1(OverLayDemo.this, bdLocation.getRadius());
                OverLayDemo.this.mBaiduMap.setMyLocationData(build);
                OverLayDemo.this.lat = new StringBuilder(String.valueOf(bdLocation.getLatitude())).toString();
                OverLayDemo.this.lon = new StringBuilder(String.valueOf(bdLocation.getLongitude())).toString();
                if (OverLayDemo.this.isFirstLoc) {
                    OverLayDemo.this.isFirstLoc = false;
                    OverLayDemo.this.mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newLatLng(new LatLng(bdLocation.getLatitude(), bdLocation.getLongitude())));
                }
            }
        }
        
    }
}
