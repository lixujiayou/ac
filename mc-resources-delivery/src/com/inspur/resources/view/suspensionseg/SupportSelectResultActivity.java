// 

// 

package com.inspur.resources.view.suspensionseg;

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
import com.inspur.resources.bean.SupportInfoBean;
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

public class SupportSelectResultActivity extends BaseActivity
{
    private Handler handler;
    private ProgressDialog mProgress;
    private EditText mc;
    private SysApplication mySys;
    private TextView name_text;
    private ListView result_list;
    private List<SupportInfoBean> supList;
    List<HashMap<String, Object>> supResultdata;

    public SupportSelectResultActivity() {
        this.result_list = null;
        this.supList = new ArrayList<SupportInfoBean>();
        this.name_text = null;
        this.supResultdata = new ArrayList<HashMap<String, Object>>();
        this.handler = new Handler() {
            public void handleMessage(final Message message) {
                if (SupportSelectResultActivity.this.mProgress != null) {
                    SupportSelectResultActivity.this.mProgress.dismiss();
                }
                switch (message.what) {
                    case 1: {
                        SupportSelectResultActivity.access$1(SupportSelectResultActivity.this, (List<SupportInfoBean>)new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss Z").create().fromJson(message.obj.toString(), new TypeToken<List<SupportInfoBean>>() {}.getType()));
                        SupportSelectResultActivity.this.bindListView();
                        Toast.makeText((Context)SupportSelectResultActivity.this, (CharSequence)"\u83b7\u53d6\u6210\u529f", 1).show();
                        break;
                    }
                    case 2: {
                        Toast.makeText((Context)SupportSelectResultActivity.this, (CharSequence)message.obj.toString(), 0).show();
                        break;
                    }
                }
                super.handleMessage(message);
            }
        };
    }

    static /* synthetic */ void access$1(final SupportSelectResultActivity supportSelectResultActivity, final List supList) {
        supportSelectResultActivity.supList = (List<SupportInfoBean>)supList;
    }

    private void getData(final SupportInfoBean supportInfoBean) {
        if (this.mProgress == null) {
            this.mProgress = ProgressDialog.show((Context)this, (CharSequence)"系统提示", (CharSequence)"\u6b63\u5728\u83b7\u53d6\u6491\u70b9\u4fe1\u606f\u2026\u2026");
        }
        else {
            this.mProgress.setMessage((CharSequence)"\u6b63\u5728\u83b7\u53d6\u6491\u70b9\u4fe1\u606f\u2026\u2026");
            this.mProgress.show();
        }
        new Thread() {
            @Override
            public void run() {
                final String json = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss Z").create().toJson(supportInfoBean);
                Log.d("ResultStr", json);
                final ArrayList<NameValuePair> list = new ArrayList<NameValuePair>();
                list.add((NameValuePair)new BasicNameValuePair("jsonRequest", json));
                final String httpGetData = new httpconnect().httpGetData("pdaPoleline!getSupport.interface", list, (Context)SupportSelectResultActivity.this);
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
                        SupportSelectResultActivity.this.handler.sendMessage(message);
                        return;
                    }
                    message.setData((Bundle)null);
                    message.what = 2;
                    message.obj = string;
                    SupportSelectResultActivity.this.handler.sendMessage(message);
                }
                catch (JSONException ex) {}
            }
        }.start();
    }

    private void init() {
        this.setContentView(R.layout.r_search_result_list);
        this.setTitle((CharSequence)"\u6491\u70b9\u67e5\u8be2\u5217\u8868");
        this.mc = (EditText)this.findViewById(R.id.mc_edit);
        mc.setHint("撑点名称");
        this.getData(new SupportInfoBean());
    }

    protected void bindListView() {
        this.supResultdata.clear();
        for (int i = 0; i < this.supList.size(); ++i) {
            final SupportInfoBean supportInfoBean = this.supList.get(i);
            final HashMap<String, Object> hashMap = new HashMap<String, Object>();
            hashMap.put("text_ename", "\u6491\u70b9\u540d\u79f0:");
            hashMap.put("text_result_ename", supportInfoBean.getSupportName());
            hashMap.put("text_station", "\u6491\u70b9\u5730\u5740:");
            if (supportInfoBean.getAddress() != null) {
                hashMap.put("text_result_station", supportInfoBean.getAddress());
            }
            else {
                hashMap.put("text_result_station", "");
            }
            this.supResultdata.add(hashMap);
        }
        final MySimpleAdapter adapter = new MySimpleAdapter((Context)this, this.supResultdata, 2130903119, new String[] { "text_ename", "text_result_ename", "text_station", "text_result_station" }, new int[] { 2131297318, 2131297319, 2131297320, 2131297321 });
        this.result_list.setHorizontalScrollBarEnabled(true);
        this.result_list.setAdapter((ListAdapter)adapter);
        this.result_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(final AdapterView<?> adapterView, final View view, final int n, final long n2) {
                final Intent intent = new Intent();
                intent.putExtra("supName", (Serializable)SupportSelectResultActivity.this.supList.get(n));
                SupportSelectResultActivity.this.setResult(2, intent);
                SupportSelectResultActivity.this.finish();
            }
        });
    }

    public void chaxun(final View view) {
        final SupportInfoBean supportInfoBean = new SupportInfoBean();
        supportInfoBean.setSupportName(this.mc.getText().toString());
        this.getData(supportInfoBean);
    }

    @Override
    protected void onCreate(final Bundle bundle) {
        super.onCreate(bundle);
        (this.mySys = SysApplication.getInstance()).addActivity(this);
        this.init();
    }
}
