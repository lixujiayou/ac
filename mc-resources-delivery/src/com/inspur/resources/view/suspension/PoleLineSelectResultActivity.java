// 

// 

package com.inspur.resources.view.suspension;

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
import com.inspur.resources.bean.PolelineInfoBean;
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
import android.widget.Toast;

public class PoleLineSelectResultActivity extends BaseActivity
{
    private List<PolelineInfoBean> ganluList;
    List<HashMap<String, Object>> ganluResultdata;
    private Handler handler;
    private ProgressDialog mProgress;
    private EditText mc;
    private SysApplication mySys;
    private ListView result_list;

    public PoleLineSelectResultActivity() {
        this.result_list = null;
        this.ganluList = new ArrayList<PolelineInfoBean>();
        this.ganluResultdata = new ArrayList<HashMap<String, Object>>();
        this.handler = new Handler() {
            public void handleMessage(final Message message) {
                if (PoleLineSelectResultActivity.this.mProgress != null) {
                    PoleLineSelectResultActivity.this.mProgress.dismiss();
                }
                switch (message.what) {
                    case 2: {
                        break;
                    }
                    case 4: {
                        PoleLineSelectResultActivity.access$1(PoleLineSelectResultActivity.this, (List<PolelineInfoBean>)new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss Z").create().fromJson(message.obj.toString(), new TypeToken<List<PolelineInfoBean>>() {}.getType()));
                        PoleLineSelectResultActivity.this.bindListView();
                        break;
                    }
                }
                super.handleMessage(message);
            }
        };
    }

    static /* synthetic */ void access$1(final PoleLineSelectResultActivity poleLineSelectResultActivity, final List ganluList) {
        poleLineSelectResultActivity.ganluList = (List<PolelineInfoBean>)ganluList;
    }

    private void getData(final PolelineInfoBean polelineInfoBean) {
        if (this.mProgress == null) {
            this.mProgress = ProgressDialog.show((Context)this, (CharSequence)"系统提示", (CharSequence)"\u6b63\u5728\u83b7\u53d6\u6746\u8def\u4fe1\u606f\u2026\u2026");
        }
        else {
            this.mProgress.setMessage((CharSequence)"\u6b63\u5728\u83b7\u53d6\u6746\u8def\u4fe1\u606f\u2026\u2026");
            this.mProgress.show();
        }
        new Thread() {
            @Override
            public void run() {
                final String json = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss Z").create().toJson(polelineInfoBean);
                Log.d("ResultStr", json);
                final ArrayList<NameValuePair> list = new ArrayList<NameValuePair>();
                list.add((NameValuePair)new BasicNameValuePair("jsonRequest", json));
                final String httpGetData = new httpconnect().httpGetData("pdaPoleline!getPoleline.interface", list, (Context)PoleLineSelectResultActivity.this);
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
                        message.what = 4;
                        message.obj = string;
                        PoleLineSelectResultActivity.this.handler.sendMessage(message);
                        return;
                    }
                    message.setData((Bundle)null);
                    message.what = 2;
                    message.obj = string;
                    PoleLineSelectResultActivity.this.handler.sendMessage(message);
                }
                catch (JSONException ex) {}
            }
        }.start();
    }

    protected void bindListView() {
        this.ganluResultdata.clear();
        for (int i = 0; i < this.ganluList.size(); ++i) {
            final PolelineInfoBean polelineInfoBean = this.ganluList.get(i);
            final HashMap<String, Object> hashMap = new HashMap<String, Object>();
            hashMap.put("text_ename", "\u6746\u8def\u540d\u79f0:");
            hashMap.put("text_result_ename", polelineInfoBean.getPoleLineName());
            hashMap.put("text_eaddr", "\u6746\u8def\u7ea7\u522b:");
            hashMap.put("text_result_eaddr", polelineInfoBean.getPoleLineLevel());
            this.ganluResultdata.add(hashMap);
        }
        final MySimpleAdapter adapter = new MySimpleAdapter((Context)this, this.ganluResultdata, 2130903119, new String[] { "text_ename", "text_result_ename", "text_eaddr", "text_result_eaddr" }, new int[] { 2131297318, 2131297319, 2131297320, 2131297321 });
        this.result_list.setHorizontalScrollBarEnabled(true);
        this.result_list.setAdapter((ListAdapter)adapter);
        this.result_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(final AdapterView<?> adapterView, final View view, final int n, final long n2) {
                final Intent intent = new Intent();
                intent.putExtra("ganluName", (Serializable)PoleLineSelectResultActivity.this.ganluList.get(n));
                PoleLineSelectResultActivity.this.setResult(1, intent);
                PoleLineSelectResultActivity.this.finish();
            }
        });
    }

    public void chaxun(final View view) {
        final PolelineInfoBean polelineInfoBean = new PolelineInfoBean();
        polelineInfoBean.setPoleLineName(this.mc.getText().toString());
        this.getData(polelineInfoBean);
    }

    protected void init() {
        this.setContentView(R.layout.r_search_result_list);
        this.setTitle((CharSequence)"杆路查询列表");
        this.getData(new PolelineInfoBean());
    }

    @Override
    protected void onCreate(final Bundle bundle) {
        super.onCreate(bundle);
        (this.mySys = SysApplication.getInstance()).addActivity(this);
        this.init();
    }
}
