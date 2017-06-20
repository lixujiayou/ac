// 

// 

package com.inspur.resources.view.map;

import android.content.Intent;
import android.content.Context;
import android.widget.TextView;
import android.os.Bundle;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.model.LatLngBounds;
import com.inspur.resources.base.BaseActivity;

import android.util.Log;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.Stroke;
import com.baidu.mapapi.map.CircleOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.DotOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import java.util.ArrayList;
import com.baidu.mapapi.map.Marker;
import java.util.List;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;

public class MarkerOverlayActivity extends BaseActivity
{
    BitmapDescriptor bdA;
    BitmapDescriptor bdB;
    BitmapDescriptor bdC;
    private String latIn;
    private String lonIn;
    private BaiduMap mBaiduMap;
    private MapView mMapView;
    private List<Marker> mMarkers;
    private float radius;
    
    public MarkerOverlayActivity() {
        this.mMarkers = new ArrayList<Marker>();
        this.bdA = BitmapDescriptorFactory.fromResource(2130837614);
        this.bdB = BitmapDescriptorFactory.fromResource(2130837615);
        this.bdC = BitmapDescriptorFactory.fromResource(2130837616);
    }
    
    public void addCustomElementsDemo() {
        final LatLng ll = new LatLng(41.811175, 123.433244);
        final LatLng ll2 = new LatLng(41.811821, 123.433199);
        final LatLng ll3 = new LatLng(41.811723, 123.433541);
        final LatLng ll4 = new LatLng(41.811965, 123.433394);
        final ArrayList<Test> list = new ArrayList<Test>();
        final Test test = new Test();
        test.setType(0);
        test.setLl(ll);
        list.add(test);
        final Test test2 = new Test();
        test2.setType(0);
        test2.setLl(ll2);
        list.add(test2);
        final Test test3 = new Test();
        test3.setType(1);
        test3.setLl(ll3);
        list.add(test3);
        final Test test4 = new Test();
        test4.setType(2);
        test4.setLl(ll4);
        list.add(test4);
        final LatLng latLng = new LatLng(Double.parseDouble(this.latIn), Double.parseDouble(this.lonIn));
        this.mBaiduMap.addOverlay(new DotOptions().center(latLng).radius(9).color(-11167233));
        this.mBaiduMap.addOverlay(new CircleOptions().fillColor(863493631).center(latLng).stroke(new Stroke(2, -1442792449)).radius((int)this.radius));
        for (int i = 0; i < list.size(); ++i) {
            final Test test5 = list.get(i);
            if (test5.getType() == 0) {
                final Marker marker = (Marker)this.mBaiduMap.addOverlay(new MarkerOptions().position(test5.getLl()).icon(this.bdA).zIndex(9).draggable(true));
                marker.setDraggable(false);
                this.mMarkers.add(marker);
            }
            else if (test5.getType() == 1) {
                final Marker marker2 = (Marker)this.mBaiduMap.addOverlay(new MarkerOptions().position(test5.getLl()).icon(this.bdB).zIndex(9).draggable(true));
                marker2.setDraggable(false);
                this.mMarkers.add(marker2);
            }
            else if (test5.getType() == 2) {
                final Marker marker3 = (Marker)this.mBaiduMap.addOverlay(new MarkerOptions().position(test5.getLl()).icon(this.bdC).zIndex(9).draggable(true));
                marker3.setDraggable(false);
                this.mMarkers.add(marker3);
            }
        }
    }
    
    public void initOverlay() {
        Log.d("\u5750\u6807:", String.valueOf(this.latIn) + "," + this.lonIn);
        this.mBaiduMap.setMapStatus(MapStatusUpdateFactory.newLatLng(new LatLngBounds.Builder().include(new LatLng(Double.parseDouble(this.latIn), Double.parseDouble(this.lonIn))).include(new LatLng(Double.parseDouble(this.latIn), Double.parseDouble(this.lonIn))).build().getCenter()));
        this.mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(new MapStatus.Builder(this.mBaiduMap.getMapStatus()).overlook(0).build()));
        this.mBaiduMap.setBuildingsEnabled(false);
    }
    
    public void onCreate(final Bundle bundle) {
        super.onCreate(bundle);
      //  this.setContentView(2130903126);
        final Intent intent = this.getIntent();
        this.latIn = intent.getStringExtra("latstr");
        this.lonIn = intent.getStringExtra("lonstr");
        this.radius = intent.getFloatExtra("radius", 0.0f);
     //   this.mMapView = (MapView)this.findViewById(2131296461);
        (this.mBaiduMap = this.mMapView.getMap()).setMapStatus(MapStatusUpdateFactory.zoomTo(19.0f));
        this.initOverlay();
        this.addCustomElementsDemo();
        this.mBaiduMap.setOnMarkerClickListener((BaiduMap.OnMarkerClickListener)new BaiduMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(final Marker marker) {
               // new TextView(MarkerOverlayActivity.this.getApplicationContext()).setTextColor(-16777216);
                for (int i = 0; i < MarkerOverlayActivity.this.mMarkers.size(); ++i) {
                    if (marker == MarkerOverlayActivity.this.mMarkers.get(i)) {
                        new ShowMarkerInfoActivity((Context)MarkerOverlayActivity.this, i).show();
                    }
                }
                return true;
            }
        });
    }
    
    @Override
    public void onDestroy() {
        this.mMapView.onDestroy();
        super.onDestroy();
        this.bdA.recycle();
        this.bdB.recycle();
        this.bdC.recycle();
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
}
