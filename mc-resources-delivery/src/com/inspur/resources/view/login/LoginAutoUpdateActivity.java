package com.inspur.resources.view.login;

import java.io.File;

import com.inspur.resources.base.BaseActivity;
import com.inspur.resources.base.MyProgress;
import com.inspur.resources.base.SysApplication;
import com.inspur.resources.utils.ApplicationValue;
import com.inspur.resources.utils.CommonUtils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class LoginAutoUpdateActivity extends BaseActivity
{
    private static final int DOWNLOAD = 1;
    private static final int DOWNLOAD_FINISH = 2;
    private static final int EXCEPTION = -2;
    private static final int NO_SD = -1;
    public static boolean cancelUpdate;
    public static int length;
    public static String mSavePath;
    public static int progress;
    private final int HANDLE_UPDATEVISION_HAVE;
    private final int HANDLE_UPDATEVISION_NOHAVE;
    private final String TAG;
    private Button button_update;
    DialogInterface.OnClickListener cancel;
    private DownloadApkThread downloadApkThread;
    private TextView last_time_content;
    private Handler mHandler;
    Handler myHandler;
    private SysApplication mySys;
    private TextView this_time_content;
    DialogInterface.OnClickListener up1;
    private TextView update_content_content;
    private MyProgress update_progress;
    private TextView update_size_content;

    static {
        LoginAutoUpdateActivity.mSavePath = null;
        LoginAutoUpdateActivity.progress = 0;
        LoginAutoUpdateActivity.length = 0;
        LoginAutoUpdateActivity.cancelUpdate = false;
    }

    public LoginAutoUpdateActivity() {
        this.TAG = "LoginAutoUpdateActivity";
        this.HANDLE_UPDATEVISION_HAVE = 0;
        this.HANDLE_UPDATEVISION_NOHAVE = 1;
        this.last_time_content = null;
        this.this_time_content = null;
        this.update_size_content = null;
        this.update_content_content = null;
        this.update_progress = null;
        this.button_update = null;
        this.downloadApkThread = null;
        this.myHandler = new Handler() {
            public void handleMessage(final Message message) {
                switch (message.what) {
                    case 0: {
                        LoginAutoUpdateActivity.this.initView();
                        break;
                    }
                    case 1: {
                        CommonUtils.showToastLong((Context)LoginAutoUpdateActivity.this, 2131361824);
                        break;
                    }
                }
                super.handleMessage(message);
            }
        };
        this.up1 = (DialogInterface.OnClickListener)new DialogInterface.OnClickListener() {
            public void onClick(final DialogInterface dialogInterface, final int n) {
                LoginAutoUpdateActivity.access$1(LoginAutoUpdateActivity.this, new DownloadApkThread((Context)LoginAutoUpdateActivity.this, LoginAutoUpdateActivity.this.mHandler, ApplicationValue.newVisionInfoBean.getVisionurl()));
                LoginAutoUpdateActivity.this.downloadApkThread.start();
                dialogInterface.dismiss();
            }
        };
        this.cancel = (DialogInterface.OnClickListener)new DialogInterface.OnClickListener() {
            public void onClick(final DialogInterface dialogInterface, final int n) {
                dialogInterface.dismiss();
            }
        };
        this.mHandler = new Handler() {
            public void handleMessage(final Message message) {
                switch (message.what) {
                    default: {}
                    case 1: {
                        LoginAutoUpdateActivity.this.update_progress.setProgress(LoginAutoUpdateActivity.progress);
                        LoginAutoUpdateActivity.this.update_progress.setMax(LoginAutoUpdateActivity.length);
                    }
                    case 2: {
                        LoginAutoUpdateActivity.this.installApk();
                    }
                    case -1: {
                        CommonUtils.showToastLong((Context)LoginAutoUpdateActivity.this, 2131361825);
                    }
                    case -2: {
                        CommonUtils.showToastLong((Context)LoginAutoUpdateActivity.this, 2131361826);
                    }
                }
            }
        };
    }

    static /* synthetic */ void access$1(final LoginAutoUpdateActivity loginAutoUpdateActivity, final DownloadApkThread downloadApkThread) {
        loginAutoUpdateActivity.downloadApkThread = downloadApkThread;
    }

    private void installApk() {
        final File file = new File(LoginAutoUpdateActivity.mSavePath, "resources.apk");
        Log.d("mSavePath", LoginAutoUpdateActivity.mSavePath);
        if (!file.exists()) {
            return;
        }
        final String string = "chmod 777 " + LoginAutoUpdateActivity.mSavePath;
        try {
            Runtime.getRuntime().exec(string);
            final Intent intent = new Intent();
            intent.setAction("android.intent.action.VIEW");
            intent.setDataAndType(Uri.parse("file://" + file.toString()), "application/vnd.android.package-archive");
            this.startActivity(intent);
        }
        catch (Exception ex) {
            ex.printStackTrace();

        }
    }

    protected void init() {

        this.initView();
        if (!this.getIntent().getBooleanExtra("iszidong", false)) {
            this.isHaveVision();
        }
    }

    protected void initView() {
        if (ApplicationValue.newVisionInfoBean != null) {
            this.this_time_content.setText((CharSequence)ApplicationValue.newVisionInfoBean.getVisionTime());
            this.update_size_content.setText((CharSequence)ApplicationValue.newVisionInfoBean.getVisionSize());
            this.update_content_content.setText((CharSequence)ApplicationValue.newVisionInfoBean.getVisionNote());
            this.button_update.setEnabled(true);
            return;
        }
        this.this_time_content.setText((CharSequence)"");
        this.update_size_content.setText((CharSequence)"");
        this.update_content_content.setText((CharSequence)"");
        this.button_update.setEnabled(false);
    }

    protected void initViewLastTime() {
        final String string = this.getSharedPreferences("LAST_UPDATE_TIME_XML", 0).getString("LAST_UPDATE_TIME", (String)null);
        if (string != null) {
            this.last_time_content.setText((CharSequence)string);
            return;
        }
        this.last_time_content.setText((CharSequence)"\u65e0");
    }

    protected void isHaveVision() {
        if (ApplicationValue.newVisionInfoBean == null) {
            new LoginUpdateVisionTask((Context)this, this.myHandler).execute(new String[0]);
        }
    }

    @Override
    protected void onCreate(final Bundle bundle) {
        super.onCreate(bundle);
        (this.mySys = SysApplication.getInstance()).addActivity(this);
        Log.d("LoginAutoUpdateActivity", "-----\u8fdb\u5165\u8f6f\u4ef6\u5347\u7ea7\u754c\u9762-----");
        this.init();
    }

    public boolean onKeyDown(final int n, final KeyEvent keyEvent) {
        if (n == 4) {
            LoginAutoUpdateActivity.cancelUpdate = true;
            this.finish();
            return true;
        }
        return super.onKeyDown(n, keyEvent);
    }

    class UpdateClickListener implements View.OnClickListener
    {
        public void onClick(final View view) {
            final NetworkInfo activeNetworkInfo = ((ConnectivityManager)LoginAutoUpdateActivity.this.getSystemService(Activity.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
            if (activeNetworkInfo != null && activeNetworkInfo.isConnected()) {
                if (activeNetworkInfo.getType() == 1) {
                    LoginAutoUpdateActivity.access$1(LoginAutoUpdateActivity.this, new DownloadApkThread((Context)LoginAutoUpdateActivity.this, LoginAutoUpdateActivity.this.mHandler, ApplicationValue.newVisionInfoBean.getVisionurl()));
                    LoginAutoUpdateActivity.this.downloadApkThread.start();
                }
                else {
                    final AlertDialog create = new AlertDialog.Builder((Context)LoginAutoUpdateActivity.this).create();
                    create.setTitle((CharSequence)"\u6e29\u99a8\u63d0\u793a\uff1a");
                    create.setMessage((CharSequence)"\u5f53\u524d\u8fde\u63a5\u7f51\u7edc\u4e3a\u975eWifi\u7f51\u7edc\uff0c\u8fdb\u884c\u4e0b\u8f7d\u53ef\u80fd\u4f1a\u6d88\u8017\u8f83\u591a\u6d41\u91cf\uff0c\u662f\u5426\u8fdb\u884c\u5347\u7ea7\uff1f");
                    create.setButton(-2, (CharSequence)"取消", LoginAutoUpdateActivity.this.cancel);
                    create.setButton(-1, (CharSequence)"确定", LoginAutoUpdateActivity.this.up1);
                    create.getWindow().setType(2003);
                    create.show();
                }
            }
            else {
            }
            LoginAutoUpdateActivity.this.button_update.setEnabled(false);
        }
    }
}
