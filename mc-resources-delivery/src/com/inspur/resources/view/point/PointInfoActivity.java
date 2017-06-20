// 

// 

package com.inspur.resources.view.point;

import java.io.Serializable;
import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.GsonBuilder;import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.inspur.easyresources.R;
import com.inspur.resources.base.BaseActivity;
import com.inspur.resources.bean.PointInfoBean;
import com.inspur.resources.http.httpconnect;
import com.inspur.resources.utils.ApplicationValue;
import com.inspur.resources.utils.CommonUtils;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class PointInfoActivity extends BaseActivity
{
    String ResultStr;
    protected PointInfoBean Upoint;
    protected boolean jb_isQuxiao;
    @SuppressLint({ "HandlerLeak" })
    private Handler mHandler;
    private ProgressDialog mProgress;
    protected PointInfoBean point;
    protected Button point_activity_bt_jb_change;
    protected Button point_activity_bt_jb_ok;
    protected TextView pointinfo_activity_edit_bz;
    protected EditText pointinfo_activity_edit_czyw;
    protected EditText pointinfo_activity_edit_ywbh;
    protected EditText pointinfo_activity_edit_ywlx;
    protected Spinner pointinfo_activity_spinner_dzfx;
    protected Spinner pointinfo_activity_spinner_dzlx;
    protected Spinner pointinfo_activity_spinner_dzzt;
    protected TextView pointinfo_activity_text_bz;
    protected TextView pointinfo_activity_text_czyw;
    protected TextView pointinfo_activity_text_dzbm;
    protected TextView pointinfo_activity_text_dzfx;
    protected TextView pointinfo_activity_text_dzlx;
    protected TextView pointinfo_activity_text_dzzt;
    protected TextView pointinfo_activity_text_xgr;
    protected TextView pointinfo_activity_text_xgsj;
    protected TextView pointinfo_activity_text_ywbh;
    protected TextView pointinfo_activity_text_ywlx;

    public PointInfoActivity() {
        this.ResultStr = "";
        this.pointinfo_activity_text_dzbm = null;
        this.pointinfo_activity_text_dzzt = null;
        this.pointinfo_activity_spinner_dzzt = null;
        this.pointinfo_activity_text_ywbh = null;
        this.pointinfo_activity_edit_ywbh = null;
        this.pointinfo_activity_text_dzfx = null;
        this.pointinfo_activity_spinner_dzfx = null;
        this.pointinfo_activity_text_ywlx = null;
        this.pointinfo_activity_edit_ywlx = null;
        this.pointinfo_activity_text_dzlx = null;
        this.pointinfo_activity_spinner_dzlx = null;
        this.pointinfo_activity_text_czyw = null;
        this.pointinfo_activity_edit_czyw = null;
        this.pointinfo_activity_text_xgr = null;
        this.pointinfo_activity_text_xgsj = null;
        this.pointinfo_activity_text_bz = null;
        this.pointinfo_activity_edit_bz = null;
        this.point_activity_bt_jb_ok = null;
        this.point_activity_bt_jb_change = null;
        this.point = null;
        this.Upoint = null;
        this.jb_isQuxiao = false;
        this.mHandler = new Handler() {
            public void handleMessage(final Message message) {
                super.handleMessage(message);
                if (PointInfoActivity.this.mProgress != null) {
                    PointInfoActivity.this.mProgress.dismiss();
                }
                switch (message.what) {
                    case 1: {
                        final Intent intent = new Intent();
                        intent.putExtra("point", (Serializable)PointInfoActivity.this.Upoint);
                        PointInfoActivity.this.setResult(2, intent);
                        PointInfoActivity.this.finish();
                        break;
                    }
                    case 2: {
                        break;
                    }
                }
            }
        };
    }

    static /* synthetic */ void access$1(final PointInfoActivity pointInfoActivity, final ProgressDialog mProgress) {
        pointInfoActivity.mProgress = mProgress;
    }

    private void upPointD() {
        new Thread() {
            @Override
            public void run() {
                new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss Z").create();
                final ArrayList<NameValuePair> list = new ArrayList<NameValuePair>();
                list.add((NameValuePair)new BasicNameValuePair("jsonRequest", PointInfoActivity.this.ResultStr));
                final String httpGetData = new httpconnect().httpGetData("pdaEqut!updatePoint.interface", list, (Context)PointInfoActivity.this);
                if (httpGetData == null) {
                    if ("".equals(httpGetData)) {
                        return;
                    }
                }
                try {
                    final Message message = new Message();
                    final JSONObject jsonObject = new JSONObject(httpGetData.toString());
                    Log.d("-------", "jsonObject="+jsonObject);
                    final String string = jsonObject.getString("info");
                    if (jsonObject.getString("result").equals("0")) {
                        message.setData((Bundle)null);
                        message.what = 1;
                        message.obj = string;
                        PointInfoActivity.this.mHandler.sendMessage(message);
                        return;
                    }
                    message.setData((Bundle)null);
                    message.what = 2;
                    message.obj = string;
                    PointInfoActivity.this.mHandler.sendMessage(message);
                }
                catch (JSONException ex) {}
            }
        }.start();
    }

    protected void inUpdatePoint_JB() {
        (this.Upoint = new PointInfoBean(this.point)).setSERVNO(this.pointinfo_activity_edit_ywbh.getText().toString());
        this.Upoint.setSERVTYPE(this.pointinfo_activity_edit_ywlx.getText().toString());
        this.Upoint.setPSERV(this.pointinfo_activity_edit_czyw.getText().toString());
        this.Upoint.setNOTE(this.pointinfo_activity_edit_bz.getText().toString());
        this.Upoint.setPSTAT((short)(this.pointinfo_activity_spinner_dzzt.getSelectedItemPosition() + 1));
        this.Upoint.setDIRECTION((short)(this.pointinfo_activity_spinner_dzfx.getSelectedItemPosition() + 1));
        this.Upoint.setMP(ApplicationValue.mUser);
        switch (this.pointinfo_activity_spinner_dzlx.getSelectedItemPosition()) {
            default: {}
            case 0: {
                this.Upoint.setPTYPE((short)1);
                break;
            }
            case 1: {
                this.Upoint.setPTYPE((short)2);
                break;
            }
            case 2: {
                this.Upoint.setPTYPE((short)3);
                break;
            }
            case 3: {
                this.Upoint.setPTYPE((short)4);
                break;
            }
            case 4: {
                this.Upoint.setPTYPE((short)5);
                break;
            }
            case 5: {
                this.Upoint.setPTYPE((short)0);
                break;
            }
        }
    }

    @SuppressLint("NewApi")
    protected void init() {
        if (this.point.getPCODE() != null && !this.point.getPCODE().equals("null")) {
            this.pointinfo_activity_text_dzbm.setText((CharSequence)this.point.getPCODE());
        }
        this.pointinfo_activity_text_dzzt.setText((CharSequence)this.point.getStatString());
        if (this.point.getSERVNO() != null && !this.point.getSERVNO().equals("null")) {
            this.pointinfo_activity_text_ywbh.setText((CharSequence)this.point.getSERVNO());
            this.pointinfo_activity_edit_ywbh.setText((CharSequence)this.point.getSERVNO());
        }
        this.pointinfo_activity_text_dzfx.setText((CharSequence)this.point.getDIRECTIONString());
        if (this.point.getSERVTYPE() != null && !this.point.getSERVTYPE().equals("null")) {
            this.pointinfo_activity_text_ywlx.setText((CharSequence)this.point.getSERVTYPE());
            this.pointinfo_activity_edit_ywlx.setText((CharSequence)this.point.getSERVTYPE());
        }
        this.pointinfo_activity_text_dzlx.setText((CharSequence)this.point.getPtypeString());
        if (this.point.getPSERV() != null && !this.point.getPSERV().equals("null")) {
            this.pointinfo_activity_text_czyw.setText((CharSequence)this.point.getPSERV());
            this.pointinfo_activity_edit_czyw.setText((CharSequence)this.point.getPSERV());
        }
        if (this.point.getMP() != null && !this.point.getMP().equals("null")) {
            this.pointinfo_activity_text_xgr.setText((CharSequence)this.point.getMP());
        }
        if (this.point.getMTIME() != null && !this.point.getMTIME().equals("null")) {
            this.pointinfo_activity_text_xgsj.setText((CharSequence)CommonUtils.datetoStringNoTime(this.point.getMTIME()));
        }
        if (this.point.getNOTE() != null && !this.point.getNOTE().equals("null")) {
            this.pointinfo_activity_text_bz.setText((CharSequence)this.point.getNOTE());
            this.pointinfo_activity_edit_bz.setText((CharSequence)this.point.getNOTE());
        }
        if (this.point.getPSTAT() != null) {
            switch (this.point.getPSTAT()) {
                case 1: {
                    this.pointinfo_activity_spinner_dzzt.setSelection(0);
                    break;
                }
                case 2: {
                    this.pointinfo_activity_spinner_dzzt.setSelection(1);
                    break;
                }
                case 3: {
                    this.pointinfo_activity_spinner_dzzt.setSelection(2);
                    break;
                }
                case 4: {
                    this.pointinfo_activity_spinner_dzzt.setSelection(3);
                    break;
                }
                case 5: {
                    this.pointinfo_activity_spinner_dzzt.setSelection(4);
                    break;
                }
            }
        }
        if (this.point.getDIRECTION() != null) {
            switch (this.point.getDIRECTION()) {
                case 1: {
                    this.pointinfo_activity_spinner_dzfx.setSelection(0);
                    break;
                }
                case 2: {
                    this.pointinfo_activity_spinner_dzfx.setSelection(1);
                    break;
                }
            }
        }
        if (this.point.getPTYPE() != null) {
            switch (this.point.getPTYPE()) {
                case 1: {
                    this.pointinfo_activity_spinner_dzlx.setSelection(0);
                    break;
                }
                case 2: {
                    this.pointinfo_activity_spinner_dzlx.setSelection(1);
                    break;
                }
                case 3: {
                    this.pointinfo_activity_spinner_dzlx.setSelection(2);
                    break;
                }
                case 4: {
                    this.pointinfo_activity_spinner_dzlx.setSelection(3);
                    break;
                }
                case 5: {
                    this.pointinfo_activity_spinner_dzlx.setSelection(4);
                    break;
                }
                case 0: {
                    this.pointinfo_activity_spinner_dzlx.setSelection(5);
                    break;
                }
            }
        }
        point_activity_bt_jb_change.callOnClick();
    }

    protected void initEdit_JB() {
        this.jb_isQuxiao = true;
    }


    @Override
    protected void onCreate(final Bundle bundle) {
        super.onCreate(bundle);
        this.point = (PointInfoBean)this.getIntent().getSerializableExtra("point");
        Log.d("====", "point="+new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss")
                .create().toJson(point));
        this.setContentView(R.layout.activity_pointinfo);
        this.init();
    }




}
