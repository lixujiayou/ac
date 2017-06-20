// 

// 

package com.inspur.resources.view.tube;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.GsonBuilder;import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.inspur.easyresources.R;
import com.inspur.resources.base.BaseActivity;
import com.inspur.resources.base.SysApplication;
import com.inspur.resources.bean.OpticalInfoBean;
import com.inspur.resources.http.httpconnect;
import com.inspur.resources.utils.MySimpleAdapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class OpticalCableSelectResultActivity extends BaseActivity
{
    private Handler handler;
    private ProgressDialog mProgress;
    private EditText mc;
    private SysApplication mySys;
    List<HashMap<String, Object>> optResultdata;
    private List<OpticalInfoBean> opticalCableList;
    private ListView result_list;

    public OpticalCableSelectResultActivity() {
        this.result_list = null;
        this.opticalCableList = new ArrayList<OpticalInfoBean>();
        this.optResultdata = new ArrayList<HashMap<String, Object>>();
        this.handler = new Handler() {
            public void handleMessage(final Message message) {
                if (OpticalCableSelectResultActivity.this.mProgress != null) {
                    OpticalCableSelectResultActivity.this.mProgress.dismiss();
                }
                switch (message.what) {
                    case 1: {
                        OpticalCableSelectResultActivity.access$1(OpticalCableSelectResultActivity.this, (List<OpticalInfoBean>)new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss Z").create().fromJson(message.obj.toString(), new TypeToken<List<OpticalInfoBean>>() {}.getType()));
                        OpticalCableSelectResultActivity.this.bindListView();
                        break;
                    }
                    case 2: {
                        break;
                    }
                }
                super.handleMessage(message);
            }
        };
    }

    static /* synthetic */ void access$1(final OpticalCableSelectResultActivity opticalCableSelectResultActivity, final List opticalCableList) {
        opticalCableSelectResultActivity.opticalCableList = (List<OpticalInfoBean>)opticalCableList;
    }

    private void getData(final OpticalInfoBean opticalInfoBean) {
        if (this.mProgress == null) {
            this.mProgress = ProgressDialog.show((Context)this, (CharSequence)"系统提示", (CharSequence)"\u6b63\u5728\u83b7\u53d6\u5149\u7f06\u4fe1\u606f\u2026\u2026");
        }
        else {
            this.mProgress.setMessage((CharSequence)"\u6b63\u5728\u83b7\u53d6\u5149\u7f06\u4fe1\u606f\u2026\u2026");
            this.mProgress.show();
        }
        new Thread() {
            @Override
            public void run() {
                final String json = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss Z").create().toJson(opticalInfoBean);
                Log.d("ResultStr", json);
                final ArrayList<NameValuePair> list = new ArrayList<NameValuePair>();
                list.add((NameValuePair)new BasicNameValuePair("jsonRequest", json));
                final String httpGetData = new httpconnect().httpGetData("pdaRoute!getRoute.interface", list, (Context)OpticalCableSelectResultActivity.this);
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
                        message.setData((Bundle)null);
                        message.what = 1;
                        message.obj = string;
                        OpticalCableSelectResultActivity.this.handler.sendMessage(message);
                        return;
                    }
                    message.setData((Bundle)null);
                    message.what = 2;
                    message.obj = string;
                    OpticalCableSelectResultActivity.this.handler.sendMessage(message);
                }
                catch (JSONException ex) {}
            }
        }.start();
    }

    private void init() {
        this.setContentView(R.layout.r_search_result_list);
        this.setTitle((CharSequence)"\u5149\u7f06\u67e5\u8be2\u5217\u8868");
        this.mc = (EditText)this.findViewById(R.id.mc_edit);
        mc.setHint("光缆名称");
        this.getData(new OpticalInfoBean());
    }

    protected void bindListView() {
        this.optResultdata.clear();
        for (int i = 0; i < this.opticalCableList.size(); ++i) {
            final OpticalInfoBean opticalInfoBean = this.opticalCableList.get(i);
            final HashMap<String, Object> hashMap = new HashMap<String, Object>();
            hashMap.put("text_ename", "\u5149\u7f06\u540d\u79f0:");
            hashMap.put("text_result_ename", opticalInfoBean.getOptical_cable_name());
            hashMap.put("text_station", "\u8d77\u6b62\u5c40\u7ad9:");
            if (opticalInfoBean.getStart_site_name() != null && !opticalInfoBean.getStart_site_name().equals("") && opticalInfoBean.getEnd_site_name() != null && !opticalInfoBean.getEnd_site_name().equals("")) {
                hashMap.put("text_result_station", String.valueOf(opticalInfoBean.getStart_site_name()) + "-" + opticalInfoBean.getEnd_site_name());
            }
            else if (opticalInfoBean.getStart_site_name() != null && !opticalInfoBean.getStart_site_name().equals("") && (opticalInfoBean.getEnd_site_name() == null || (opticalInfoBean.getEnd_site_name() != null && opticalInfoBean.getEnd_site_name().equals("")))) {
                hashMap.put("text_result_station", String.valueOf(opticalInfoBean.getStart_site_name()) + "-");
            }
            else if (opticalInfoBean.getEnd_site_name() != null && !opticalInfoBean.getEnd_site_name().equals("") && (opticalInfoBean.getStart_site_name() == null || (opticalInfoBean.getStart_site_name() != null && opticalInfoBean.getStart_site_name().equals("")))) {
                hashMap.put("text_result_station", "-" + opticalInfoBean.getEnd_site_name());
            }
            else {
                hashMap.put("text_result_station", "");
            }
            this.optResultdata.add(hashMap);
        }
        final MySimpleAdapter adapter = new MySimpleAdapter((Context)this, this.optResultdata, 2130903119, new String[] { "text_ename", "text_result_ename", "text_station", "text_result_station" }, new int[] { 2131297318, 2131297319, 2131297320, 2131297321 });
        this.result_list.setHorizontalScrollBarEnabled(true);
        this.result_list.setAdapter((ListAdapter)adapter);
        this.result_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(final AdapterView<?> adapterView, final View view, final int n, final long n2) {
                final Intent intent = new Intent();
                intent.putExtra("optName", (Serializable)OpticalCableSelectResultActivity.this.opticalCableList.get(n));
                OpticalCableSelectResultActivity.this.setResult(1, intent);
                OpticalCableSelectResultActivity.this.finish();
            }
        });
    }

    public void chaxun(final View view) {
        final OpticalInfoBean opticalInfoBean = new OpticalInfoBean();
        opticalInfoBean.setOptical_cable_name(this.mc.getText().toString());
        this.getData(opticalInfoBean);
    }

    @Override
    protected void onCreate(final Bundle bundle) {
        super.onCreate(bundle);
        (this.mySys = SysApplication.getInstance()).addActivity(this);
        this.init();
    }
}
