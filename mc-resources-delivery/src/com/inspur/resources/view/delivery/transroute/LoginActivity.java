// 

// 

package com.inspur.resources.view.delivery.transroute;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.GsonBuilder;import com.google.gson.Gson;
import com.inspur.StringUtils;
import com.inspur.easyresources.R;
import com.inspur.resources.base.BaseActivity;
import com.inspur.resources.base.SysApplication;
import com.inspur.resources.bean.UserInfoBean;
import com.inspur.resources.http.httpconnect;
import com.inspur.resources.utils.ApplicationValue;
import com.inspur.resources.utils.CommonUtils;
import com.inspur.resources.utils.StrUtil;
import com.inspur.resources.view.delivery.MainActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Network;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Toast;
import cn.trinea.android.common.util.NetWorkUtils;
import cn.trinea.android.common.util.PreferencesUtils;

public class LoginActivity extends BaseActivity
{
    private final int HANDLE_UPDATEVISION_HAVE;
    private final int HANDLE_UPDATEVISION_NOHAVE;
    String ResultStr;
    private CheckBox checkbox_login;
    private long exitTime;
    @SuppressLint({ "HandlerLeak" })
    private Handler mHandler;
    private ProgressDialog mProgress;
    //    Handler myHandler;
    private SysApplication mySys;
    private EditText password;
    private String pwdFromPhone;
    private EditText username;
    private TextInputLayout floatingUsernameLabel;
    private TextInputLayout floatingPasswordLabel;

    public LoginActivity() {
        this.ResultStr = "";
        this.username = null;
        this.password = null;
        this.checkbox_login = null;
        this.pwdFromPhone = null;
        this.HANDLE_UPDATEVISION_HAVE = 0;
        this.HANDLE_UPDATEVISION_NOHAVE = 1;
        this.mHandler = new Handler() {
            public void handleMessage(final Message message) {
                super.handleMessage(message);
                if (LoginActivity.this.mProgress != null) {
                    LoginActivity.this.mProgress.dismiss();
                }
                switch (message.what) {
                    case 1:
                        ApplicationValue.UID = message.obj.toString();
                        ApplicationValue.isOffLine = false;
                        final SharedPreferences sharedPreferences = LoginActivity.this.getSharedPreferences("SAVE_LOGINSET", 0);
                        ApplicationValue.dayinji_default_bluetooth_name = sharedPreferences.getString("SAVE_DEF_BLUETOOTH_NAME2", (String)null);
                        ApplicationValue.dayinji_default_bluetooth_address = sharedPreferences.getString("SAVE_DEF_BLUETOOTH_ADDRESS2", (String)null);
                        ApplicationValue.rfid_default_bluetooth_name = sharedPreferences.getString("SAVE_DEF_BLUETOOTH_NAME1", (String)null);
                        ApplicationValue.rfid_default_bluetooth_address = sharedPreferences.getString("SAVE_DEF_BLUETOOTH_ADDRESS1", (String)null);
                        LoginActivity.this.startActivity(new Intent((Context)LoginActivity.this, MainActivity.class));
                        finish();
                        break;
                    case 2: {
                        Toast.makeText((Context)LoginActivity.this, (CharSequence)message.obj.toString(), Toast.LENGTH_SHORT).show();
                        break;
                    }
                }
            }
        };


        this.exitTime = 0L;
    }

    private void init() {
        floatingUsernameLabel = (TextInputLayout) findViewById(R.id.username_text_input_layout);
        floatingPasswordLabel = (TextInputLayout) findViewById(R.id.pwd_text_input_layout);
        this.username = floatingUsernameLabel.getEditText();
        this.password = floatingPasswordLabel.getEditText();
        this.checkbox_login = (CheckBox)this.findViewById(R.id.checkbox_login);
    }

    private void setupFloatingLabelError() {
        floatingUsernameLabel.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence text, int start, int count, int after) {
                if (!StringUtils.isEmpty(text.toString())) {
                    floatingUsernameLabel.setErrorEnabled(false);
                }
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // TODO Auto-generated method stub
            }

            @Override
            public void afterTextChanged(Editable s) {

            }

        });
        floatingPasswordLabel.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence text, int start, int count, int after) {
                if (!StringUtils.isEmpty(text.toString())) {
                    floatingPasswordLabel.setErrorEnabled(false);
                }
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // TODO Auto-generated method stub
            }

            @Override
            public void afterTextChanged(Editable s) {

            }

        });
    }

    /**
     * 判断用户信息填写是否完整
     *
     * @return
     */
    private boolean isUserInfoComplete() {

        if (StringUtils.isEmpty(username.getText().toString().trim())) {
            floatingUsernameLabel.setError("用户名不能为空!");
            floatingUsernameLabel.setErrorEnabled(true);
            floatingUsernameLabel.getEditText().requestFocus();
            return false;
        }
        if (StringUtils.isEmpty(password.getText().toString().trim())) {
            floatingPasswordLabel.setError("密码不能为空!");
            floatingPasswordLabel.setErrorEnabled(true);
            floatingPasswordLabel.getEditText().requestFocus();
            return false;
        }

        return true;
    }

    private void loginD() {
        new Thread() {
            @Override
            public void run() {
                final ArrayList<NameValuePair> list = new ArrayList<NameValuePair>();
                list.add((NameValuePair)new BasicNameValuePair("jsonRequest", LoginActivity.this.ResultStr));
                final String httpGetData = new httpconnect().httpGetData("pdaLogin!login.interface", list, (Context)LoginActivity.this);
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
                        LoginActivity.this.mHandler.sendMessage(message);
                        return;
                    }
                    message.what = 2;
                    message.obj = string;
                    LoginActivity.this.mHandler.sendMessage(message);
                }
                catch (JSONException ex) {}
            }
        }.start();
    }

    protected void EditShow() {
        if (ApplicationValue.login_set_username_pwd_save) {
            final SharedPreferences sharedPreferences = this.getSharedPreferences("SAVE_USERINFO", 0);
            this.username.setText((CharSequence)sharedPreferences.getString("IS_SAVE_USERNAME", ""));
            final String string = sharedPreferences.getString("IS_SAVE_PASSWORD", "");
            this.password.setText((CharSequence)string);
            String pwdFromPhone = string;
            if (string.equals("")) {
                pwdFromPhone = null;
            }
            this.pwdFromPhone = pwdFromPhone;
        }
    }

    protected void Save_UserInfo() {
        SharedPreferences.Editor edit = this.getSharedPreferences("SAVE_USERINFO", 0).edit();
        edit.putString("IS_SAVE_USERNAME", this.username.getText().toString());
        if (ApplicationValue.login_set_username_pwd_save) {
            edit.putString("IS_SAVE_PASSWORD", ApplicationValue.mPassword);
        }
        edit.commit();
    }

    public void click_login(final View view) {
        if (!isUserInfoComplete())
            return;
        ApplicationValue.mUser = this.username.getText().toString();
        if (this.pwdFromPhone == null) {
            ApplicationValue.mPassword = StrUtil.strToMD5(this.password.getText().toString());
        }
        else if (this.pwdFromPhone.equals(this.password.getText().toString())) {
            ApplicationValue.mPassword = this.password.getText().toString();
        }
        else {
            ApplicationValue.mPassword = StrUtil.strToMD5(this.password.getText().toString());
        }
        if (this.username.getText().toString().equals("") || this.password.getText().toString().equals("")) {
            Toast.makeText((Context)this, (CharSequence)"\u7528\u6237\u540d\u548c\u5bc6\u7801\u4e0d\u80fd\u4e3a\u7a7a", Toast.LENGTH_SHORT).show();
            return;
        }
        this.Save_UserInfo();
        if (this.mProgress == null) {
            this.mProgress = ProgressDialog.show((Context)this, (CharSequence)"系统提示", (CharSequence)"\u6b63\u5728\u767b\u5f55\u2026\u2026");
        }
        else {
            this.mProgress.setMessage((CharSequence)"\u6b63\u5728\u8bf7\u6c42\u767b\u5f55\u2026\u2026");
            this.mProgress.show();
        }
        final UserInfoBean userInfoBean = new UserInfoBean();
        userInfoBean.setUsername(this.username.getText().toString());
        userInfoBean.setPassword(ApplicationValue.mPassword);
        String photoNumber;
        if (CommonUtils.getPhotoNumber((Context)this) == null || CommonUtils.getPhotoNumber((Context)this).equals("")) {
            photoNumber = "18561390632";
        }
        else {
            photoNumber = CommonUtils.getPhotoNumber((Context)this);
        }
        userInfoBean.setPhoneNumber(photoNumber);
        this.ResultStr = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss Z").create().toJson(userInfoBean);
        Log.d("LoginActivity==>", "ResultStr==>" + this.ResultStr);
        this.loginD();
    }

    protected void isHaveVision() {
        /*if (ApplicationValue.newVisionInfoBean == null) {
            new LoginUpdateVisionTask((Context)this, this.myHandler).execute(new String[0]);
        }*/
    }

    @Override
    protected void onCreate(final Bundle bundle) {
        super.onCreate(bundle);
        this.mySys = SysApplication.getInstance();
        this.setContentView(R.layout.activity_login);
        this.init();
        setupFloatingLabelError();
        this.readLoginSet();
        this.isHaveVision();
        this.EditShow();
    }

    @Override
    protected boolean isUseDefaultToolBar() {
        return false;
    }

    public boolean onKeyDown(final int n, final KeyEvent keyEvent) {
        if (n == 4 && keyEvent.getAction() == 0) {
            if (System.currentTimeMillis() - this.exitTime > 2000L) {
                Toast.makeText(this.getApplicationContext(), (CharSequence)"\u518d\u6309\u4e00\u6b21\u9000\u51fa\u7a0b\u5e8f", Toast.LENGTH_SHORT).show();
                this.exitTime = System.currentTimeMillis();
            }
            else {
                ApplicationValue.stationList.clear();
                ApplicationValue.generatorList.clear();
                ApplicationValue.polelineInfoBeanList.clear();
                ApplicationValue.poleInfoBeanList.clear();
                ApplicationValue.pipelineInfoBeanList.clear();
                ApplicationValue.pipeSegmentInfoBeanList.clear();
                ApplicationValue.polelineSegmentInfoBeanList.clear();
                ApplicationValue.wellList.clear();
                ApplicationValue.opticalCableList.clear();
                ApplicationValue.supportInfoBeanList.clear();
                ApplicationValue.suspensionWireList.clear();
                ApplicationValue.suspensionWireSegList.clear();
                ApplicationValue.ledupList.clear();
                ApplicationValue.opticalCableList.clear();
                ApplicationValue.cableSectionList.clear();
                ApplicationValue.jointList.clear();
                ApplicationValue.fiberBoxInfoBeanList.clear();
                ApplicationValue.occList.clear();
                this.mySys.exit();
            }
            return true;
        }
        return super.onKeyDown(n, keyEvent);
    }

    protected void readLoginSet() {
        ApplicationValue.login_set_username_pwd_save = this.getSharedPreferences("SAVE_LOGINSET", 0).getBoolean("IS_SAVE_USERNAME_PWD", false);
     //   Log.d("ApplicationValue.login_set_username_pwd_save", new StringBuilder(String.valueOf(ApplicationValue.login_set_username_pwd_save)).toString());
        this.checkbox_login.setChecked(ApplicationValue.login_set_username_pwd_save);
        String ip = getSharedPreferences("SAVE_URLINFO", Context.MODE_PRIVATE).getString("ip", "");
        String port = getSharedPreferences("SAVE_URLINFO", Context.MODE_PRIVATE).getString("port", "");
        String contextName = getSharedPreferences("SAVE_URLINFO", Context.MODE_PRIVATE).getString("contextName", "");
        if(!"".equals(ip)&&!"".equals(port)&&!"".equals(contextName)){
            ApplicationValue.url = "http://"+ip+":"+port+"/"+contextName+"/";
        }
//        ApplicationValue.url = this.getSharedPreferences("SAVE_URLINFO", 0).getString("SAVE_URL", "");
    }

    public void user_pwd_save(final View view) {
        final CheckBox checkBox = (CheckBox)view;
        final SharedPreferences.Editor edit = this.getSharedPreferences("SAVE_LOGINSET", 0).edit();
        edit.putBoolean("IS_SAVE_USERNAME_PWD", checkBox.isChecked());
        ApplicationValue.login_set_username_pwd_save = checkBox.isChecked();
        edit.commit();
    }

    public void set_onc(View view){
        final Dialog dialog = new Dialog(this);
        dialog.setTitle("URL设置");
        dialog.show();
        dialog.setContentView(R.layout.dialog_seturl);
        final EditText et_ip = (EditText) dialog.findViewById(R.id.ip);
        final EditText et_port = (EditText) dialog.findViewById(R.id.port);
        final EditText cname = (EditText) dialog.findViewById(R.id.cname);
        RadioGroup rg = (RadioGroup) dialog.findViewById(R.id.group);
        rg.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.rb_inner) {
                    et_ip.setText("10.110.2.86");
                    et_port.setText("9080");
                    cname.setText("InventoryManager");
                } else if (checkedId == R.id.rb_outter) {
                    et_ip.setText("218.57.146.247");
                    et_port.setText("8318");
                    cname.setText("InventoryManager");
                } else if (checkedId == R.id.rb_custom) {

                }

            }
        });

        String ip = getSharedPreferences("SAVE_URLINFO", Context.MODE_PRIVATE).getString("ip", "");
        String port = getSharedPreferences("SAVE_URLINFO", Context.MODE_PRIVATE).getString("port", "");
        String contextName = getSharedPreferences("SAVE_URLINFO", Context.MODE_PRIVATE).getString("contextName", "");
        if(!"".equals(ip)&&!"".equals(port)&&!"".equals(contextName)){
            et_ip.setText(ip);
            et_port.setText(port);
            cname.setText(contextName);
        }else{
            if(!"".equals(ApplicationValue.url)){
                Log.d("1url", ApplicationValue.url);
                Uri url = Uri.parse(ApplicationValue.url);
                Log.d("url.getHost()", url.getHost());
                Log.d("url.getPort())", ""+url.getPort());
                Log.d("url.getPath())", ""+url.getPath());
                et_ip.setText(url.getHost());
                et_port.setText(String.valueOf(url.getPort()));
                cname.setText(url.getPath().replaceAll("/", ""));
            }
        }
        Button save = (Button) dialog.findViewById(R.id.btn_save);
        Button cancel = (Button) dialog.findViewById(R.id.btn_cancel);
        save.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                String ip = et_ip.getText().toString().trim();
                String port = et_port.getText().toString().trim();
                String contextName = cname.getText().toString().trim();

                getSharedPreferences("SAVE_URLINFO", 0).edit().putString("ip", ip).commit();
                getSharedPreferences("SAVE_URLINFO", 0).edit().putString("port", port).commit();
                getSharedPreferences("SAVE_URLINFO", 0).edit().putString("contextName", contextName).commit();
                ApplicationValue.url = "http://"+ip+":"+port+"/"+contextName+"/";
                Log.d("url", ApplicationValue.url);
                dialog.dismiss();
            }
        });
        cancel.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

    }
}
