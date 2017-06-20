// 

// 

package com.inspur.resources.view.map;

import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import android.content.Context;
import android.widget.Toast;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;

import android.content.Intent;
import android.os.Message;
import android.os.Handler;
import android.widget.EditText;
import android.widget.Button;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.inspur.easyresources.R;
import com.inspur.resources.base.BaseActivity;
import com.inspur.resources.base.SysApplication;
import com.inspur.resources.utils.ApplicationValue;

public class LocationSubmitActivity extends BaseActivity implements OnGetGeoCoderResultListener
{
    private static GeoCoder mSearch;
    private Button fanhui_button;
    private Button find_button;
    private String lat="0";
    private EditText latedit;
    private String lon="0";
    private EditText lonedit;
    private Handler mHandler;
    private SysApplication mySys;
    private float radius;

    static {
        LocationSubmitActivity.mSearch = null;
    }

    public LocationSubmitActivity() {
        this.latedit = null;
        this.lonedit = null;
        this.find_button = null;
        this.fanhui_button = null;
        this.mHandler = new Handler() {
            public void handleMessage(final Message message) {
                super.handleMessage(message);
                switch (message.what) {
                    default: {}
                    case 1: {
                        final Intent intent = new Intent();
                        intent.putExtra("latstr1", LocationSubmitActivity.this.lat);
                        intent.putExtra("lonstr1", LocationSubmitActivity.this.lon);
                        intent.putExtra("radius", LocationSubmitActivity.this.radius);
                        LocationSubmitActivity.this.setResult(-1, intent);
                        LocationSubmitActivity.this.finish();
                        break;
                    }
                }
            }
        };
    }

    public static void fcode(final double n, final double n2) {
        ApplicationValue.nowAddress = null;
        LocationSubmitActivity.mSearch.reverseGeoCode(new ReverseGeoCodeOption().location(new LatLng(n, n2)));
    }

    protected void init() {
        this.setContentView(R.layout.activity_location_submit);
        final Intent intent = this.getIntent();
        this.lat = intent.getStringExtra("latstr");
        this.lon = intent.getStringExtra("lonstr");
        this.radius = intent.getFloatExtra("radius", 0.0f);

        this.find_button.setOnClickListener(new LbfSearchButtonOnClickListener());
    }

    @Override
    protected void onCreate(final Bundle bundle) {
        super.onCreate(bundle);
        (this.mySys = SysApplication.getInstance()).addActivity(this);
        (LocationSubmitActivity.mSearch = GeoCoder.newInstance()).setOnGetGeoCodeResultListener(this);
        this.init();
    }

    @Override
    public void onGetGeoCodeResult(final GeoCodeResult geoCodeResult) {
        if (geoCodeResult == null || geoCodeResult.error != SearchResult.ERRORNO.NO_ERROR) {
            Toast.makeText((Context)this, (CharSequence)"抱歉，未能找到结果", Toast.LENGTH_SHORT).show();
            return;
        }
    }

    @Override
    public void onGetReverseGeoCodeResult(final ReverseGeoCodeResult reverseGeoCodeResult) {
        if (reverseGeoCodeResult == null || reverseGeoCodeResult.error != SearchResult.ERRORNO.NO_ERROR) {
            Toast.makeText((Context)this, (CharSequence)"抱歉，未能找到结果", Toast.LENGTH_SHORT).show();
            return;
        }
        if (reverseGeoCodeResult.getAddressDetail() != null) {
            ApplicationValue.addrFull = reverseGeoCodeResult.getAddress();
            ApplicationValue.jfSuoshuwhqu = String.valueOf(reverseGeoCodeResult.getAddressDetail().province) + reverseGeoCodeResult.getAddressDetail().city + reverseGeoCodeResult.getAddressDetail().district;
        }
        final Message message = new Message();
        message.what = 1;
        this.mHandler.sendMessage(message);
    }

    class LbfSearchButtonOnClickListener implements View.OnClickListener
    {
        public void onClick(final View view) {
            if (ApplicationValue.isOffLine) {
                final Message message = new Message();
                message.what = 1;
                LocationSubmitActivity.this.mHandler.sendMessage(message);
                return;
            }
            new Thread() {
                @Override
                public void run() {
                    LocationSubmitActivity.fcode(Double.parseDouble(LocationSubmitActivity.this.lat), Double.parseDouble(LocationSubmitActivity.this.lon));
                }
            }.start();
        }
    }
}
