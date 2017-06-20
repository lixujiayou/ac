// 

// 

package com.inspur.resources.view.set;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import com.inspur.easyresources.R;
import com.inspur.resources.base.BaseActivity;
import com.inspur.resources.http.httpconnect;
import com.inspur.resources.utils.ApplicationValue;
import com.inspur.resources.utils.StrUtil;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class UpPasswordActivity extends BaseActivity
{
    @SuppressLint({ "HandlerLeak" })
    private final Handler mHandler;
    private ProgressDialog myDialog;
    EditText password_edit;
    EditText password_edit_2;
    EditText username_edit;
    TextView vi_password;

    public UpPasswordActivity() {
        this.vi_password = null;
        this.username_edit = null;
        this.password_edit = null;
        this.password_edit_2 = null;
        this.mHandler = new Handler() {
            public void handleMessage(final Message message) {
                super.handleMessage(message);
                if (UpPasswordActivity.this.myDialog != null) {
                    UpPasswordActivity.this.myDialog.dismiss();
                }
                switch (message.what) {
                    default: {}
                    case 1: {
                        ApplicationValue.mPassword = StrUtil.strToMD5(UpPasswordActivity.this.password_edit_2.getText().toString());

                        break;}
                    case 2: {

                        break; }
                    case 3: {
                        ApplicationValue.mPassword = StrUtil.strToMD5("123456");

                        break; }
                    case 4: {

                        break;}
                }
            }
        };
    }

    public void chongzhi_pass(final View view) {
        if (this.username_edit.getText() != null && this.password_edit.getText() != null && this.password_edit_2.getText() != null && !ApplicationValue.mPassword.equals(StrUtil.strToMD5(this.username_edit.getText().toString()))) {
            return;
        }
        this.initData1("{\"password\":\"" + StrUtil.strToMD5("123456") + "\"}");
    }

    public void initData(final String s) {
        if (this.myDialog == null) {
            this.myDialog = ProgressDialog.show((Context)this, (CharSequence)"\u63d0\u793a", (CharSequence)"\u6b63\u5728\u4fee\u6539\u5bc6\u7801\uff0c\u8bf7\u7a0d\u5019...");
        }
        else {
            this.myDialog.setMessage((CharSequence)"\u6b63\u5728\u4fee\u6539\u5bc6\u7801\uff0c\u8bf7\u7a0d\u5019...");
            this.myDialog.show();
        }
        new Thread() {
            @Override
            public void run() {
                final ArrayList<NameValuePair> list = new ArrayList<NameValuePair>();
                list.add((NameValuePair)new BasicNameValuePair("jsonRequest", s));
                final String httpGetData = new httpconnect().httpGetData("pdaLogin!changePassword.interface", list, (Context)UpPasswordActivity.this);
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
                        UpPasswordActivity.this.mHandler.sendMessage(message);
                        return;
                    }
                    message.setData((Bundle)null);
                    message.what = 2;
                    message.obj = string;
                    UpPasswordActivity.this.mHandler.sendMessage(message);
                }
                catch (JSONException ex) {}
            }
        }.start();
    }

    public void initData1(final String s) {
        if (this.myDialog == null) {
            this.myDialog = ProgressDialog.show((Context)this, (CharSequence)"\u63d0\u793a", (CharSequence)"\u6b63\u5728\u91cd\u7f6e\u5bc6\u7801\uff0c\u8bf7\u7a0d\u5019...");
        }
        else {
            this.myDialog.setMessage((CharSequence)"\u6b63\u5728\u91cd\u7f6e\u5bc6\u7801\uff0c\u8bf7\u7a0d\u5019...");
            this.myDialog.show();
        }
        new Thread() {
            @Override
            public void run() {
                final ArrayList<NameValuePair> list = new ArrayList<NameValuePair>();
                list.add((NameValuePair)new BasicNameValuePair("jsonRequest", s));
                final String httpGetData = new httpconnect().httpGetData("pdaLogin!changePassword.interface", list, (Context)UpPasswordActivity.this);
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
                        message.what = 3;
                        message.obj = string;
                        UpPasswordActivity.this.mHandler.sendMessage(message);
                        return;
                    }
                    message.setData((Bundle)null);
                    message.what = 4;
                    message.obj = string;
                    UpPasswordActivity.this.mHandler.sendMessage(message);
                }
                catch (JSONException ex) {}
            }
        }.start();
    }

    @Override
    protected void onCreate(final Bundle bundle) {
        super.onCreate(bundle);
        this.setContentView(R.layout.activity_password_update);
        setTitle("修改密码");
    }

    public void save_pass(final View view) {
        if (this.username_edit.getText() != null && this.password_edit.getText() != null && this.password_edit_2.getText() != null) {
            if (!ApplicationValue.mPassword.equals(StrUtil.strToMD5(this.username_edit.getText().toString()))) {
                return;
            }
            if (!this.password_edit.getText().toString().equals(this.password_edit_2.getText().toString())) {
                return;
            }
        }
        this.initData("{\"password\":\"" + StrUtil.strToMD5(this.password_edit.getText().toString()) + "\"}");
    }
}
