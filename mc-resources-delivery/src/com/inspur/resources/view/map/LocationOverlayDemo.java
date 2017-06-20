// 

// 

package com.inspur.resources.view.map;

import com.baidu.mapapi.map.MyLocationData;
import com.baidu.location.BDLocation;

import android.Manifest;
import android.app.AlertDialog.Builder;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.support.v4.app.ActivityCompat;
import android.widget.TextView;

import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.location.LocationClientOption;
import com.baidu.location.BDLocationListener;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.os.Bundle;
import android.content.Intent;

import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.inspur.easyresources.R;
import com.inspur.resources.base.BaseActivity;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.model.LatLng;

import java.util.Iterator;

import android.util.Log;
import android.location.GpsStatus;

import com.baidu.mapapi.map.BitmapDescriptorFactory;

import java.util.ArrayList;

import android.location.GpsStatus.Listener;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.location.GpsSatellite;

import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.map.Marker;

import java.util.List;

import com.baidu.mapapi.map.MapView;
import com.baidu.location.LocationClient;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.BaiduMap;

import android.location.LocationManager;
import android.widget.Button;

import com.baidu.mapapi.map.BitmapDescriptor;

import android.app.Activity;
import android.app.AlertDialog;

public class LocationOverlayDemo extends BaseActivity {
    BitmapDescriptor bdA;
    BitmapDescriptor bdB;
    BitmapDescriptor bdC;
    Button checkNearButton;
    boolean isFirstLoc;
    boolean isRequest;
    String lat;
    LocationManager locationManager;
    String lon;
    BaiduMap mBaiduMap;
    BitmapDescriptor mCurrentMarker;
    private MyLocationConfiguration.LocationMode mCurrentMode;
    LocationClient mLocClient;
    MapView mMapView;
    private List<Marker> mMarkers;
    GeoCoder mSearch;
    Button manualOperationButton;
    public MyLocationListenner myListener;
    private List<GpsSatellite> numSatelliteList;
    RadioGroup.OnCheckedChangeListener radioButtonListener;
    private float radius;
    Button requestLocButton;
    ImageView star1;
    ImageView star2;
    ImageView star3;
    ImageView star4;
    ImageView star5;
    ImageView star6;
    ImageView star7;
    ImageView star8;
    ImageView star9;
    private final GpsStatus.Listener statusListener;
    private String titlestr;

    public LocationOverlayDemo() {
        this.myListener = new MyLocationListenner();
        this.requestLocButton = null;
        this.manualOperationButton = null;
        this.checkNearButton = null;
        this.isRequest = false;
        this.isFirstLoc = true;
        this.titlestr = "";
        this.lat = "";
        this.lon = "";
        this.mSearch = null;
        this.star1 = null;
        this.star2 = null;
        this.star3 = null;
        this.star4 = null;
        this.star5 = null;
        this.star6 = null;
        this.star7 = null;
        this.star8 = null;
        this.star9 = null;
        this.locationManager = null;
        this.mMarkers = new ArrayList<Marker>();
        this.bdA = BitmapDescriptorFactory.fromResource(2130837614);
        this.bdB = BitmapDescriptorFactory.fromResource(2130837615);
        this.bdC = BitmapDescriptorFactory.fromResource(2130837616);
        this.numSatelliteList = new ArrayList<GpsSatellite>();
        this.statusListener = (GpsStatus.Listener) new GpsStatus.Listener() {
            public void onGpsStatusChanged(int int1) {
                if (ActivityCompat.checkSelfPermission(LocationOverlayDemo.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                LocationOverlayDemo.access$1(LocationOverlayDemo.this, LocationOverlayDemo.this.updateGpsStatus(int1, ((LocationManager) LocationOverlayDemo.this.getSystemService(Activity.LOCATION_SERVICE)).getGpsStatus((GpsStatus) null)));
                if (!LocationOverlayDemo.this.titlestr.equals("")) {
                    int1 = Integer.parseInt(LocationOverlayDemo.this.titlestr);
//                    LocationOverlayDemo.this.showStar(int1);
                    if (int1 > 4 || int1 > 1) {
                    }
                }
            }
        };
    }

    static /* synthetic */ void access$1(final LocationOverlayDemo locationOverlayDemo, final String titlestr) {
        locationOverlayDemo.titlestr = titlestr;
    }

    static /* synthetic */ void access$4(final LocationOverlayDemo locationOverlayDemo, final float radius) {
        locationOverlayDemo.radius = radius;
    }


    private String updateGpsStatus(int n, final GpsStatus gpsStatus) {
        Log.d("\u641c\u661f", "111111111111111111111111111111111111111111");
        final StringBuilder sb = new StringBuilder("");
        if (gpsStatus == null) {
            sb.append("\u641c\u7d22\u5230\u536b\u661f\u4e2a\u6570\uff1a0");
        } else if (n == 4) {
            final int maxSatellites = gpsStatus.getMaxSatellites();
            final Iterator<GpsSatellite> iterator = (Iterator<GpsSatellite>) gpsStatus.getSatellites().iterator();
            this.numSatelliteList.clear();
            for (n = 0; iterator.hasNext() && n <= maxSatellites; ++n) {
                this.numSatelliteList.add(iterator.next());
            }
            sb.append(this.numSatelliteList.size());
        }
        return sb.toString();
    }





    protected void onActivityResult(final int n, final int n2, final Intent intent) {
        super.onActivityResult(n, n2, intent);
        if (n == 1 && intent != null && n2 == -1) {
            final Intent intent2 = new Intent();
            intent2.putExtra("latstr2", intent.getStringExtra("latstr1"));
            intent2.putExtra("lonstr2", intent.getStringExtra("lonstr1"));
            intent2.putExtra("radius", this.radius);
            this.setResult(-1, intent2);
            this.finish();
        }
    }

    public void onCreate(final Bundle bundle) {
        super.onCreate(bundle);
//        this.requestWindowFeature(7);
        this.setContentView(R.layout.activity_locationoverlay);
//        this.getWindow().setFeatureInt(7, 2130903113);
        this.mCurrentMode = MyLocationConfiguration.LocationMode.NORMAL;
        (this.requestLocButton = (Button) this.findViewById(R.id.button1)).setText((CharSequence) "\u786e\u8ba4\u5b9a\u4f4d");
        (this.manualOperationButton = (Button) this.findViewById(R.id.button2)).setText((CharSequence) "手动定位");
        (this.checkNearButton = (Button) this.findViewById(R.id.button3)).setText((CharSequence) "\u9644\u8fd1\u8d44\u6e90");
        this.requestLocButton.setOnClickListener((View.OnClickListener) new View.OnClickListener() {
            public void onClick(final View view) {
                Log.d("\u7ecf\u7eac\u5ea6", String.valueOf(LocationOverlayDemo.this.lat) + "," + LocationOverlayDemo.this.lon);
                final Intent intent = new Intent((Context) LocationOverlayDemo.this, (Class) LocationSubmitActivity.class);
                intent.putExtra("latstr", LocationOverlayDemo.this.lat);
                intent.putExtra("lonstr", LocationOverlayDemo.this.lon);
                intent.putExtra("radius", LocationOverlayDemo.this.radius);
                LocationOverlayDemo.this.startActivityForResult(intent, 1);
            }
        });
        this.manualOperationButton.setOnClickListener((View.OnClickListener) new View.OnClickListener() {
            public void onClick(final View view) {
                Log.d("\u7ecf\u7eac\u5ea6", String.valueOf(LocationOverlayDemo.this.lat) + "," + LocationOverlayDemo.this.lon);
                final Intent intent = new Intent((Context) LocationOverlayDemo.this, (Class) OverLayDemo.class);
                intent.putExtra("latstr", LocationOverlayDemo.this.lat);
                intent.putExtra("lonstr", LocationOverlayDemo.this.lon);
                intent.putExtra("radius", LocationOverlayDemo.this.radius);
                LocationOverlayDemo.this.startActivityForResult(intent, 1);
            }
        });
        this.checkNearButton.setOnClickListener((View.OnClickListener) new View.OnClickListener() {
            public void onClick(final View view) {
                final Intent intent = new Intent((Context) LocationOverlayDemo.this, (Class) MarkerOverlayActivity.class);
                intent.putExtra("latstr", LocationOverlayDemo.this.lat);
                intent.putExtra("lonstr", LocationOverlayDemo.this.lon);
                intent.putExtra("radius", LocationOverlayDemo.this.radius);
                LocationOverlayDemo.this.startActivityForResult(intent, 1);
            }
        });
        this.mMapView = (MapView) this.findViewById(R.id.mapview);
        (this.mBaiduMap = this.mMapView.getMap()).setMyLocationEnabled(true);
        (this.mLocClient = new LocationClient((Context) this)).registerLocationListener(this.myListener);
        final LocationClientOption locOption = new LocationClientOption();
        locOption.setOpenGps(true);
        locOption.setCoorType("bd09ll");
        locOption.setScanSpan(1000);
        this.mLocClient.setLocOption(locOption);
        this.mLocClient.start();
        this.mBaiduMap.animateMapStatus(MapStatusUpdateFactory.zoomTo(Float.parseFloat("19")));
        this.mBaiduMap.setBuildingsEnabled(false);
        if (ActivityCompat.checkSelfPermission(LocationOverlayDemo.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        (this.locationManager = (LocationManager) this.getSystemService(Activity.LOCATION_SERVICE)).addGpsStatusListener(this.statusListener);

        this.mBaiduMap.setOnMarkerClickListener((BaiduMap.OnMarkerClickListener)new BaiduMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(final Marker marker) {
                new TextView(LocationOverlayDemo.this.getApplicationContext()).setTextColor(Color.RED);
                for (int i = 0; i < LocationOverlayDemo.this.mMarkers.size(); ++i) {
                    if (marker == LocationOverlayDemo.this.mMarkers.get(i)) {
                        new AlertDialog.Builder((Context)LocationOverlayDemo.this).setMessage((CharSequence)new StringBuilder().append(i).toString()).show();
                    }
                }
                return true;
            }
        });
    }

    public void onDestroy() {
        this.mLocClient.stop();
        this.mBaiduMap.setMyLocationEnabled(false);
        this.mMapView.onDestroy();
        this.mMapView = null;
        this.locationManager.removeGpsStatusListener(this.statusListener);
        this.bdA.recycle();
        this.bdB.recycle();
        this.bdC.recycle();
        super.onDestroy();
    }

    protected void onPause() {
        this.mMapView.onPause();
        super.onPause();
    }

    protected void onResume() {
        this.mMapView.onResume();
        super.onResume();
    }

    class Test
    {
        LatLng ll;
        int type;

        Test() {
            this.type = 0;
            this.ll = null;
        }

        public LatLng getLl() {
            return this.ll;
        }

        public int getType() {
            return this.type;
        }

        public void setLl(final LatLng ll) {
            this.ll = ll;
        }

        public void setType(final int type) {
            this.type = type;
        }
    }

    public class MyLocationListenner implements BDLocationListener
    {
        @Override
        public void onReceiveLocation(final BDLocation bdLocation) {
            if (bdLocation != null && LocationOverlayDemo.this.mMapView != null) {
                final MyLocationData build = new MyLocationData.Builder().accuracy(bdLocation.getRadius()).direction(100.0f).latitude(bdLocation.getLatitude()).longitude(bdLocation.getLongitude()).build();
                LocationOverlayDemo.access$4(LocationOverlayDemo.this, bdLocation.getRadius());
                LocationOverlayDemo.this.mBaiduMap.setMyLocationData(build);
                LocationOverlayDemo.this.lat = new StringBuilder(String.valueOf(bdLocation.getLatitude())).toString();
                LocationOverlayDemo.this.lon = new StringBuilder(String.valueOf(bdLocation.getLongitude())).toString();
                if (LocationOverlayDemo.this.isFirstLoc) {
                    LocationOverlayDemo.this.isFirstLoc = false;
                    LocationOverlayDemo.this.mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newLatLng(new LatLng(bdLocation.getLatitude(), bdLocation.getLongitude())));
                }
            }
        }

    }
}
