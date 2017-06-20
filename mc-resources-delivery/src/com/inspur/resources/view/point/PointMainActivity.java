// 

// 

package com.inspur.resources.view.point;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.GsonBuilder;import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.inspur.easyresources.R;
import com.inspur.resources.bean.ODMInfoBean;
import com.inspur.resources.bean.PointInfoBean;
import com.inspur.resources.http.httpconnect;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

public class PointMainActivity extends Activity
{
    public static PointMainActivity pointMainActivity;
    Gson gson;
    @SuppressLint({ "HandlerLeak" })
    private Handler handler;
    private List<PointInfoBean> list;
    private ProgressDialog mProgress;
    ODMInfoBean odm;
    PointView p;
    private ScrollView scr_view;
    int screenHeight;
    int screenWidth;
    private ImageView shuzishuoming;
    
    static {
        PointMainActivity.pointMainActivity = null;
    }
    
    public PointMainActivity() {
        this.shuzishuoming = null;
        this.gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss Z").create();
        this.odm = null;
        this.p = null;
        this.scr_view = null;
        this.handler = new Handler() {
            public void handleMessage(final Message message) {
                if (PointMainActivity.this.mProgress != null) {
                    PointMainActivity.this.mProgress.dismiss();
                }
                switch (message.what) {
                    case 1: {
                        PointMainActivity.access$1(PointMainActivity.this, (List<PointInfoBean>)gson.fromJson(message.obj.toString(), new TypeToken<List<PointInfoBean>>() {}.getType()));
                        Log.d("=====", "list="+new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss Z").create().toJson(list));
                        if (PointMainActivity.this.list != null && PointMainActivity.this.list.size() > 0) {
                            PointMainActivity.this.initData();
                            break;
                        }
                        break;
                    }
                    case 2: {
                        break;
                    }
                    case 3: {
                        final PointInfoBean pointInfoBean = (PointInfoBean)message.obj;
                        final Intent intent = new Intent((Context)PointMainActivity.this, (Class)PointInfoActivity.class);
                        intent.putExtra("point", pointInfoBean);
                        PointMainActivity.this.startActivityForResult(intent, 3);
                        break;
                    }
                }
                super.handleMessage(message);
            }
        };
        PointMainActivity.pointMainActivity = this;
    }
    
    static /* synthetic */ void access$1(final PointMainActivity pointMainActivity, final List list) {
        pointMainActivity.list = (List<PointInfoBean>)list;
    }
    
    private void getData() {
        if (this.mProgress == null) {
            this.mProgress = ProgressDialog.show((Context)this, (CharSequence)"提示", (CharSequence)"正在获取端子信息……");
        }
        else {
            this.mProgress.setMessage((CharSequence)"正在获取端子信息……");
            this.mProgress.show();
        }
        new Thread() {
            @Override
            public void run() {
                final PointInfoBean pointInfoBean = new PointInfoBean();
                pointInfoBean.setEID(PointMainActivity.this.odm.getEid());
                pointInfoBean.setODMCODE(PointMainActivity.this.odm.getOdmCode());
                final String json = PointMainActivity.this.gson.toJson(pointInfoBean);
                final ArrayList<NameValuePair> list = new ArrayList<NameValuePair>();
                list.add((NameValuePair)new BasicNameValuePair("jsonRequest", json));
                final String httpGetData = new httpconnect().httpGetData("pdaEqut!getPoint.interface", list, (Context)PointMainActivity.this);
                Log.d("AddrList==>", "result==>" + httpGetData);
                if (httpGetData == null) {
                    if ("".equals(httpGetData)) {
                        return;
                    }
                }
                try {
                    final Message message = new Message();
                    final JSONObject jsonObject = new JSONObject(httpGetData.toString());
                    final String string = jsonObject.getString("info");
                    if (jsonObject.getString("result").equals("0")) {
                        message.what = 1;
                        message.obj = string;
                        PointMainActivity.this.handler.sendMessage(message);
                        return;
                    }
                    message.what = 2;
                    message.obj = string;
                    PointMainActivity.this.handler.sendMessage(message);
                }
                catch (JSONException ex) {}
            }
        }.start();
    }
    
    public static PointMainActivity getMainActivity() {
        return PointMainActivity.pointMainActivity;
    }
    
    private void initData() {
        this.scr_view.removeAllViews();
        if (this.odm != null) {
            (this.p = new PointView((Context)this, this.handler)).setPanshu(Integer.parseInt(this.odm.getTerminalRowQuantity()));
            this.p.setPointsize(Integer.parseInt(this.odm.getTerminalColumnQuantity()));
            this.p.setFirstPanelPos(this.odm.getFirstPanelPos());
            this.p.setPointList(this.list);
            this.p.setODM(Integer.parseInt(this.odm.getOdmCode()));
            this.p.setMap();
            this.p.initPointView();
            if (Integer.parseInt(this.odm.getTerminalColumnQuantity()) != 0) {
                this.p.addPoints(this.screenWidth / (Integer.parseInt(this.odm.getTerminalColumnQuantity()) + 3) + 2, this.screenWidth / Integer.parseInt(this.odm.getTerminalColumnQuantity()));
            }
            this.scr_view.addView((View)this.p);
        }
    }
    
    public void ShuzeshuomingSizeChange(final int n, final int n2) {
        this.shuzishuoming.setLayoutParams(new LinearLayout.LayoutParams(n, n2));
    }
    
    public Map<String, Integer> getShuzishuomingSize() {
        final HashMap<String, Integer> hashMap = new HashMap<String, Integer>();
        final int width = this.shuzishuoming.getWidth();
        final int height = this.shuzishuoming.getHeight();
        hashMap.put("width", width);
        hashMap.put("height", height);
        return hashMap;
    }
    
    protected void onActivityResult(int i, final int n, final Intent intent) {
        super.onActivityResult(i, n, intent);
        if (i == 3 && intent != null) {
            final PointInfoBean pointInfoBean = (PointInfoBean)intent.getSerializableExtra("point");
            for (i = 0; i < this.list.size(); ++i) {
                if (this.list.get(i).getPID().equals(pointInfoBean.getPID())) {
                    this.list.set(i, pointInfoBean);
                    break;
                }
            }
            this.initData();
        }
    }
    
    protected void onCreate(final Bundle bundle) {
        super.onCreate(bundle);
        this.setContentView(R.layout.activity_point_main);
        this.odm = (ODMInfoBean)this.getIntent().getSerializableExtra("odm");
        final WindowManager windowManager = this.getWindowManager();
        this.screenWidth = windowManager.getDefaultDisplay().getWidth();
        this.screenHeight = windowManager.getDefaultDisplay().getHeight();
        this.getData();
    }
}
