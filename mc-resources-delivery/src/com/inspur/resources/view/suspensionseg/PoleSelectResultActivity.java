// 

// 

package com.inspur.resources.view.suspensionseg;

import android.app.Activity;
import java.io.Serializable;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import java.util.Map;

import org.json.JSONException;
import android.os.Bundle;
import org.json.JSONObject;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.NameValuePair;
import android.util.Log;
import android.content.Context;
import android.widget.Toast;
import com.google.gson.reflect.TypeToken;
import com.inspur.easyresources.R;
import com.inspur.resources.base.BaseActivity;
import com.inspur.resources.base.SysApplication;
import com.inspur.resources.bean.PoleInfoBean;
import com.inspur.resources.http.httpconnect;
import com.inspur.resources.utils.MySimpleAdapter;
import com.google.gson.GsonBuilder;import com.google.gson.Gson;
import android.os.Message;
import java.util.ArrayList;
import android.widget.ListView;
import java.util.HashMap;
import java.util.List;
import android.widget.TextView;
import android.widget.EditText;
import android.app.ProgressDialog;
import android.os.Handler;

public class PoleSelectResultActivity extends BaseActivity
{
    private Handler handler;
    private ProgressDialog mProgress;
    private EditText mc;
    private SysApplication mySys;
    private TextView name_text;
    private List<PoleInfoBean> poleList;
    List<HashMap<String, Object>> poleResultdata;
    private ListView result_list;

    public PoleSelectResultActivity() {
        this.result_list = null;
        this.poleList = new ArrayList<PoleInfoBean>();
        this.name_text = null;
        this.poleResultdata = new ArrayList<HashMap<String, Object>>();
        this.handler = new Handler() {
            public void handleMessage(final Message message) {
                if (PoleSelectResultActivity.this.mProgress != null) {
                    PoleSelectResultActivity.this.mProgress.dismiss();
                }
                switch (message.what) {
                    case 1: {
                        PoleSelectResultActivity.access$1(PoleSelectResultActivity.this, (List<PoleInfoBean>)new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss Z").create().fromJson(message.obj.toString(), new TypeToken<List<PoleInfoBean>>() {}.getType()));
                        PoleSelectResultActivity.this.bindListView();
                        Toast.makeText((Context)PoleSelectResultActivity.this, (CharSequence)"\u83b7\u53d6\u6210\u529f", 1).show();
                        break;
                    }
                    case 2: {
                        Toast.makeText((Context)PoleSelectResultActivity.this, (CharSequence)message.obj.toString(), 0).show();
                        break;
                    }
                }
                super.handleMessage(message);
            }
        };
    }

    static /* synthetic */ void access$1(final PoleSelectResultActivity poleSelectResultActivity, final List poleList) {
        poleSelectResultActivity.poleList = (List<PoleInfoBean>)poleList;
    }

    private void getData(final PoleInfoBean poleInfoBean) {
        if (this.mProgress == null) {
            this.mProgress = ProgressDialog.show((Context)this, (CharSequence)"系统提示", (CharSequence)"\u6b63\u5728\u83b7\u53d6\u7535\u6746\u4fe1\u606f\u2026\u2026");
        }
        else {
            this.mProgress.setMessage((CharSequence)"\u6b63\u5728\u83b7\u53d6\u7535\u6746\u4fe1\u606f\u2026\u2026");
            this.mProgress.show();
        }
        new Thread() {
            @Override
            public void run() {
                final String json = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss Z").create().toJson(poleInfoBean);
                Log.d("ResultStr", json);
                final ArrayList<NameValuePair> list = new ArrayList<NameValuePair>();
                list.add((NameValuePair)new BasicNameValuePair("jsonRequest", json));
                final String httpGetData = new httpconnect().httpGetData("pdaPoleline!getPole.interface", list, (Context)PoleSelectResultActivity.this);
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
                        PoleSelectResultActivity.this.handler.sendMessage(message);
                        return;
                    }
                    message.setData((Bundle)null);
                    message.what = 2;
                    message.obj = string;
                    PoleSelectResultActivity.this.handler.sendMessage(message);
                }
                catch (JSONException ex) {}
            }
        }.start();
    }

    private void init() {
        this.setContentView(R.layout.r_search_result_list);
        this.setTitle((CharSequence)"\u7535\u6746\u67e5\u8be2\u5217\u8868");
        this.mc = (EditText)this.findViewById(R.id.mc_edit);
        mc.setHint("电杆名称");
    }

    protected void bindListView() {
        this.poleResultdata.clear();
        for (int i = 0; i < this.poleList.size(); ++i) {
            final PoleInfoBean poleInfoBean = this.poleList.get(i);
            final HashMap<String, Object> hashMap = new HashMap<String, Object>();
            hashMap.put("text_ename", "\u7535\u6746\u540d\u79f0:");
            hashMap.put("text_result_ename", poleInfoBean.getPoleNameSub());
            hashMap.put("text_station", "\u7535\u6746\u5730\u5740:");
            if (poleInfoBean.getPoleAddress() != null) {
                hashMap.put("text_result_station", poleInfoBean.getPoleAddress());
            }
            else {
                hashMap.put("text_result_station", "");
            }
            this.poleResultdata.add(hashMap);
        }
        final MySimpleAdapter adapter = new MySimpleAdapter((Context)this, this.poleResultdata, 2130903119, new String[] { "text_ename", "text_result_ename", "text_station", "text_result_station" }, new int[] { 2131297318, 2131297319, 2131297320, 2131297321 });
        this.result_list.setHorizontalScrollBarEnabled(true);
        this.result_list.setAdapter((ListAdapter)adapter);
        this.result_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(final AdapterView<?> adapterView, final View view, final int n, final long n2) {
                final Intent intent = new Intent();
                intent.putExtra("poleName", (Serializable)PoleSelectResultActivity.this.poleList.get(n));
                PoleSelectResultActivity.this.setResult(2, intent);
                PoleSelectResultActivity.this.finish();
            }
        });
    }

    public void chaxun(final View view) {
        final PoleInfoBean poleInfoBean = new PoleInfoBean();
        poleInfoBean.setPoleName(this.mc.getText().toString());
        this.getData(poleInfoBean);
    }

    @Override
    protected void onCreate(final Bundle bundle) {
        super.onCreate(bundle);
        (this.mySys = SysApplication.getInstance()).addActivity(this);
        this.init();
    }
}
