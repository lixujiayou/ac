// 

// 

package com.inspur.resources.view.suspension;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.inspur.common.RegionWheelDialog;
import com.inspur.easyresources.R;
import com.inspur.resources.base.ActionSheetDialog;
import com.inspur.resources.base.BaseActivity;
import com.inspur.resources.base.SysApplication;
import com.inspur.resources.bean.PolelineInfoBean;
import com.inspur.resources.bean.SuspensionWireInfoBean;
import com.inspur.resources.http.httpconnect;
import com.inspur.resources.utils.ApplicationValue;
import com.inspur.resources.utils.CommonUtils;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;
import cn.trinea.android.common.util.PreferencesUtils;

public class SuspensionWireInfoActivity extends BaseActivity
{
    private final Context context = this;
    private EditText changdu_edit;
    private Handler handler;
    private int indexIn;
    private int mDay;
    @SuppressLint({ "HandlerLeak" })
    private Handler mHandler;
    private int mMonth;
    private ProgressDialog mProgress;
    private int mYear;
    private SysApplication mySys;
    private EditText note_edit;
    private int poleLineId;
    private String polelineNameIn;
    private int polelineidIn;
    private Spinner sfsqguanli_spinner;
    private ImageView shengmzhouqishijian_button;
    private EditText shengmzhouqishijian_edit;
    private EditText sjyt_edit;
    private DatePickerDialog.OnDateSetListener smzqDateSetListener;
    private EditText sqgldw_edit;
    private SuspensionWireInfoBean suspensionInfoIn;
    private EditText sw_code_edit;
    private EditText sw_maintenance_edit;
    private EditText sw_name_edit;
    private EditText sw_poleline_edit;
    private Spinner ziyuansmzq_spinner;
    private EditText sjzlzrr;
    private EditText yxwhr;



    public SuspensionWireInfoActivity() {
        this.sw_name_edit = null;
        this.sw_code_edit = null;
        this.sw_maintenance_edit = null;
        this.sw_poleline_edit = null;
        this.changdu_edit = null;
        this.ziyuansmzq_spinner = null;
        this.shengmzhouqishijian_edit = null;
        this.shengmzhouqishijian_button = null;
        this.sfsqguanli_spinner = null;
        this.sqgldw_edit = null;
        this.sjyt_edit = null;
        this.note_edit = null;
        this.suspensionInfoIn = null;
        this.indexIn = 0;
        this.polelineidIn = 0;
        this.polelineNameIn = null;
        this.mHandler = new Handler() {
            public void handleMessage(final Message message) {
                super.handleMessage(message);
                if (SuspensionWireInfoActivity.this.mProgress != null) {
                    SuspensionWireInfoActivity.this.mProgress.dismiss();
                }
                switch (message.what) {
                    default: {}
                    case 1: {
                        ApplicationValue.suspensionWireList.set(SuspensionWireInfoActivity.this.indexIn, SuspensionWireInfoActivity.this.suspensionInfoIn);
                        SuspensionWireInfoActivity.this.finish();
                        break;
                    }
                    case 2: {
                        SuspensionWireInfoActivity.this.suspensionInfoIn.setSuspensionWireId(new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss Z").create().fromJson(message.obj.toString(), SuspensionWireInfoBean.class).getSuspensionWireId());
                        ApplicationValue.suspensionWireList.add(SuspensionWireInfoActivity.this.suspensionInfoIn);
                        SuspensionWireInfoActivity.this.finish();
                        break;
                    }
                    case 3: {
                        break;
                    }
                }
            }
        };
        this.handler = new Handler() {
            public void handleMessage(final Message message) {
                if (SuspensionWireInfoActivity.this.mProgress != null) {
                    SuspensionWireInfoActivity.this.mProgress.dismiss();
                }
                switch (message.what) {
                    case 2: {
                        break;
                    }
                    case 3: {
                        ApplicationValue.suspensionWireList.remove(SuspensionWireInfoActivity.this.indexIn);
                        SuspensionWireInfoActivity.this.finish();
                        break;
                    }
                }
                super.handleMessage(message);
            }
        };
        this.smzqDateSetListener = new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(final DatePicker datePicker, final int n, final int n2, final int n3) {
                SuspensionWireInfoActivity.access$3(SuspensionWireInfoActivity.this, n);
                SuspensionWireInfoActivity.access$4(SuspensionWireInfoActivity.this, n2);
                SuspensionWireInfoActivity.access$5(SuspensionWireInfoActivity.this, n3);
                SuspensionWireInfoActivity.this.updateSmzqDisplay();
            }
        };
    }

    static /* synthetic */ void access$13(final SuspensionWireInfoActivity suspensionWireInfoActivity, final ProgressDialog mProgress) {
        suspensionWireInfoActivity.mProgress = mProgress;
    }

    static /* synthetic */ void access$3(final SuspensionWireInfoActivity suspensionWireInfoActivity, final int mYear) {
        suspensionWireInfoActivity.mYear = mYear;
    }

    static /* synthetic */ void access$4(final SuspensionWireInfoActivity suspensionWireInfoActivity, final int mMonth) {
        suspensionWireInfoActivity.mMonth = mMonth;
    }

    static /* synthetic */ void access$5(final SuspensionWireInfoActivity suspensionWireInfoActivity, final int mDay) {
        suspensionWireInfoActivity.mDay = mDay;
    }

    private void dataInit() {
        final boolean b = false;
        final EditText sw_name_edit = this.sw_name_edit;
        String suspensionWireName;
        if (this.suspensionInfoIn.getSuspensionWireName() == null) {
            suspensionWireName = "";
        }
        else {
            suspensionWireName = this.suspensionInfoIn.getSuspensionWireName();
        }
        sw_name_edit.setText((CharSequence)suspensionWireName);
        final EditText sw_code_edit = this.sw_code_edit;
        String suspensionWireCode;
        if (this.suspensionInfoIn.getSuspensionWireCode() == null) {
            suspensionWireCode = "";
        }
        else {
            suspensionWireCode = this.suspensionInfoIn.getSuspensionWireCode();
        }
        sw_code_edit.setText((CharSequence)suspensionWireCode);
        final EditText sw_maintenance_edit = this.sw_maintenance_edit;
        String region;
        if (this.suspensionInfoIn.getRegion() == null) {
            region = "";
        }
        else {
            region = this.suspensionInfoIn.getRegion();
        }
        sw_maintenance_edit.setText((CharSequence)region);
        final EditText sw_poleline_edit = this.sw_poleline_edit;
        String poleLineName;
        if (this.suspensionInfoIn.getPoleLineName() == null) {
            poleLineName = "";
        }
        else {
            poleLineName = this.suspensionInfoIn.getPoleLineName();
        }
        sw_poleline_edit.setText((CharSequence)poleLineName);
        final EditText changdu_edit = this.changdu_edit;
        String length;
        if (this.suspensionInfoIn.getLength() == null) {
            length = "";
        }
        else {
            length = this.suspensionInfoIn.getLength();
        }
        changdu_edit.setText((CharSequence)length);
        final Spinner ziyuansmzq_spinner = this.ziyuansmzq_spinner;
        int intValue;
        if (this.suspensionInfoIn.getResourceLifePeriodEnumId() == null) {
            intValue = 0;
        }
        else {
            intValue = this.suspensionInfoIn.getResourceLifePeriodEnumId();
        }
        ziyuansmzq_spinner.setSelection(intValue);
        this.shengmzhouqishijian_edit.setText((CharSequence)CommonUtils.datetoStringNoTime(this.suspensionInfoIn.getLifecycleTime()));
        final Spinner sfsqguanli_spinner = this.sfsqguanli_spinner;
        int intValue2;
        if (this.suspensionInfoIn.getIsAuthorizationManagement() == null) {
            intValue2 = (b ? 1 : 0);
        }
        else {
            intValue2 = this.suspensionInfoIn.getIsAuthorizationManagement();
        }
        sfsqguanli_spinner.setSelection(intValue2);
        final EditText sqgldw_edit = this.sqgldw_edit;
        String authorizationManagementUnit;
        if (this.suspensionInfoIn.getAuthorizationManagementUnit() == null) {
            authorizationManagementUnit = "";
        }
        else {
            authorizationManagementUnit = this.suspensionInfoIn.getAuthorizationManagementUnit();
        }
        sqgldw_edit.setText((CharSequence)authorizationManagementUnit);
        final EditText sjyt_edit = this.sjyt_edit;
        String designPurposes;
        if (this.suspensionInfoIn.getDesignPurposes() == null) {
            designPurposes = "";
        }
        else {
            designPurposes = this.suspensionInfoIn.getDesignPurposes();
        }
        sjyt_edit.setText((CharSequence)designPurposes);
        final EditText note_edit = this.note_edit;
        String note;
        if (this.suspensionInfoIn.getNote() == null) {
            note = "";
        }
        else {
            note = this.suspensionInfoIn.getNote();
        }
        note_edit.setText((CharSequence)note);
        sjzlzrr.setText(suspensionInfoIn.getDataQualityPrincipal());
        yxwhr.setText(suspensionInfoIn.getParties());
    }

    private void dataSave(final SuspensionWireInfoBean suspensionWireInfoBean) {
        suspensionWireInfoBean.setSuspensionWireName(this.sw_name_edit.getText().toString());
        suspensionWireInfoBean.setSuspensionWireCode(this.sw_code_edit.getText().toString());
        suspensionWireInfoBean.setRegion(this.sw_maintenance_edit.getText().toString());
        suspensionWireInfoBean.setPoleLineName(this.sw_poleline_edit.getText().toString());
        suspensionWireInfoBean.setPoleLineId(this.poleLineId);
        suspensionWireInfoBean.setLength(this.changdu_edit.getText().toString());
        suspensionWireInfoBean.setResourceLifePeriodEnumId(this.ziyuansmzq_spinner.getSelectedItemPosition());
        suspensionWireInfoBean.setLifecycleTime(CommonUtils.StringToDate(this.shengmzhouqishijian_edit.getText().toString()));
        suspensionWireInfoBean.setIsAuthorizationManagement(this.sfsqguanli_spinner.getSelectedItemPosition());
        suspensionWireInfoBean.setAuthorizationManagementUnit(this.sqgldw_edit.getText().toString());
        suspensionWireInfoBean.setDesignPurposes(this.sjyt_edit.getText().toString());
        suspensionWireInfoBean.setNote(this.note_edit.getText().toString());
        suspensionWireInfoBean.setDataQualityPrincipal(sjzlzrr.getText().toString());
        suspensionWireInfoBean.setParties(yxwhr.getText().toString());
    }

    private void getData(final int n) {
        if (this.mProgress == null) {
            if (n == 0) {
                this.mProgress = ProgressDialog.show((Context)this, (CharSequence)"系统提示", (CharSequence)"正在核查吊线信息……");
            }
            else {
                this.mProgress = ProgressDialog.show((Context)this, (CharSequence)"系统提示", (CharSequence)"正在添加吊线信息……");
            }
        }
        else {
            if (n == 0) {
                this.mProgress.setMessage((CharSequence)"正在核查吊线信息……");
            }
            else {
                this.mProgress.setMessage((CharSequence)"正在添加吊线信息……");
            }
            this.mProgress.show();
        }
        new Thread() {
            @Override
            public void run() {
                final String json = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss Z").create().toJson(SuspensionWireInfoActivity.this.suspensionInfoIn);
                Log.d("ResultStr", json);
                String s = "pdaPoleline!updateSuspension.interface";
                if (n != 0) {
                    s = "pdaPoleline!insertSuspension.interface";
                }

                final ArrayList<NameValuePair> list = new ArrayList<NameValuePair>();
                String uid = PreferencesUtils.getString(context, "UID", null);
                list.add((NameValuePair)new BasicNameValuePair("UID", uid));
                list.add((NameValuePair)new BasicNameValuePair("jsonRequest", json));
                final String httpGetData = new httpconnect().httpGetData(s, list, (Context)SuspensionWireInfoActivity.this);
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
                        if (n == 0) {
                            message.what = 1;
                        }
                        else {
                            message.what = 2;
                        }
                        message.obj = string;
                        SuspensionWireInfoActivity.this.mHandler.sendMessage(message);
                        return;
                    }
                    message.setData((Bundle)null);
                    message.what = 3;
                    message.obj = string;
                    SuspensionWireInfoActivity.this.mHandler.sendMessage(message);
                    return;
                }
                catch (JSONException ex) {}

            }

        }.start();
    }

    private void saveInfo() {
        if (this.suspensionInfoIn != null) {
            this.dataSave(this.suspensionInfoIn);
            this.getData(0);
            return;
        }
        this.dataSave(this.suspensionInfoIn = new SuspensionWireInfoBean());
        this.getData(1);
    }

    private void updateSmzqDisplay() {
        final EditText shengmzhouqishijian_edit = this.shengmzhouqishijian_edit;
        final StringBuilder append = new StringBuilder().append(this.mYear).append("-");
        Serializable s;
        if (this.mMonth + 1 < 10) {
            s = "0" + (this.mMonth + 1);
        }
        else {
            s = this.mMonth + 1;
        }
        final StringBuilder append2 = append.append(s).append("-");
        Serializable s2;
        if (this.mDay < 10) {
            s2 = "0" + this.mDay;
        }
        else {
            s2 = this.mDay;
        }
        shengmzhouqishijian_edit.setText((CharSequence)append2.append(s2));
    }

    public void cs_cancel(final View view) {
        this.finish();
    }

    public void cs_delete(final View view) {
        if (this.suspensionInfoIn == null) {
            return;
        }
        new ActionSheetDialog((Context)this).builder().setTitle("确定\u5220\u9664\u5f53\u524d\u540a\u7ebf\uff1f").setCancelable(false).setCanceledOnTouchOutside(false).addSheetItem("确定", ActionSheetDialog.SheetItemColor.Red, (ActionSheetDialog.OnSheetItemClickListener)new ActionSheetDialog.OnSheetItemClickListener() {
            @Override
            public void onClick(final int n) {
                if (SuspensionWireInfoActivity.this.mProgress == null) {
                    SuspensionWireInfoActivity.access$13(SuspensionWireInfoActivity.this, ProgressDialog.show((Context)SuspensionWireInfoActivity.this, (CharSequence)"系统提示", (CharSequence)"正在进行删除操作……"));
                }
                else {
                    SuspensionWireInfoActivity.this.mProgress.setMessage((CharSequence)"正在进行删除操作……");
                    SuspensionWireInfoActivity.this.mProgress.show();
                }
                new Thread() {
                    @Override
                    public void run() {
                        final Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss Z").create();
                        final SuspensionWireInfoBean suspensionWireInfoBean = new SuspensionWireInfoBean();
                        suspensionWireInfoBean.setSuspensionWireId(SuspensionWireInfoActivity.this.suspensionInfoIn.getSuspensionWireId());
                        final String json = gson.toJson(suspensionWireInfoBean);
                        Log.d("ResultStr", json);
                        final ArrayList<NameValuePair> list = new ArrayList<NameValuePair>();
                        String uid = PreferencesUtils.getString(context, "UID", null);
                        list.add((NameValuePair)new BasicNameValuePair("UID", uid));
                        list.add((NameValuePair)new BasicNameValuePair("jsonRequest", json));
                        final String httpGetData = new httpconnect().httpGetData("pdaPoleline!deleteSuspension.interface", list, (Context)SuspensionWireInfoActivity.this);
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
                                message.what = 3;
                                message.obj = string;
                                SuspensionWireInfoActivity.this.handler.sendMessage(message);
                                return;
                            }
                            message.setData((Bundle)null);
                            message.what = 2;
                            message.obj = string;
                            SuspensionWireInfoActivity.this.handler.sendMessage(message);
                        }
                        catch (JSONException ex) {}
                    }
                }.start();
            }
        }).show();
    }

    public void cs_save(final View view) {
        this.saveInfo();
    }

    public void init() {
    }

    public void maintenance_select_onc(final View view) {
        this.startActivityForResult(new Intent((Context)this, (Class)RegionWheelDialog.class), 2);
    }

    protected void onActivityResult(final int n, final int n2, final Intent intent) {
        super.onActivityResult(n, n2, intent);
        if (n2 == 1) {
            final PolelineInfoBean polelineInfoBean = (PolelineInfoBean)intent.getExtras().get("ganluName");
            this.sw_poleline_edit.setText((CharSequence)polelineInfoBean.getPoleLineName());
            this.poleLineId = polelineInfoBean.getPoleLineId();
        }
        if (n == 2 && n2 == 20) {
            this.sw_maintenance_edit.setText((CharSequence)intent.getStringExtra("region"));
        }
    }

    @Override
    protected void onCreate(final Bundle bundle) {
        super.onCreate(bundle);
        (this.mySys = SysApplication.getInstance()).addActivity(this);
        this.setContentView(R.layout.activity_suspension_info);
        this.setTitle((CharSequence)"\u540a\u7ebf");
        final Intent intent = this.getIntent();
        this.suspensionInfoIn = (SuspensionWireInfoBean)intent.getSerializableExtra("suspension");
        this.indexIn = intent.getIntExtra("selectIndex", 0);
        this.polelineidIn = intent.getIntExtra("polelineid", 0);
        this.polelineNameIn = intent.getStringExtra("polelineName");
        this.init();
        if (this.suspensionInfoIn != null) {
            this.dataInit();
            this.poleLineId = this.suspensionInfoIn.getPoleLineId();
        }
        else if (this.polelineidIn != 0) {
            this.sw_poleline_edit.setText((CharSequence)this.polelineNameIn);
            this.poleLineId = this.polelineidIn;
        }
    }

    public void poleline_select_onc(final View view) {
        this.startActivityForResult(new Intent((Context)this, (Class)PoleLineSelectResultActivity.class), 1);
    }

    class GetSmzqsjClickListener implements View.OnClickListener
    {
        public void onClick(final View view) {
            if (SuspensionWireInfoActivity.this.shengmzhouqishijian_edit.getText().toString().equals("")) {
                final Calendar instance = Calendar.getInstance();
                new DatePickerDialog((Context)SuspensionWireInfoActivity.this, SuspensionWireInfoActivity.this.smzqDateSetListener, SuspensionWireInfoActivity.this.mYear, SuspensionWireInfoActivity.this.mMonth, SuspensionWireInfoActivity.this.mDay).show();
                return;
            }
            final String string = SuspensionWireInfoActivity.this.shengmzhouqishijian_edit.getText().toString();
            SuspensionWireInfoActivity.access$3(SuspensionWireInfoActivity.this, Integer.parseInt(string.substring(0, 4)));
            SuspensionWireInfoActivity.access$4(SuspensionWireInfoActivity.this, Integer.parseInt(string.substring(5, 7)));
            SuspensionWireInfoActivity.access$5(SuspensionWireInfoActivity.this, Integer.parseInt(string.substring(8, 10)));
            new DatePickerDialog((Context)SuspensionWireInfoActivity.this, SuspensionWireInfoActivity.this.smzqDateSetListener, SuspensionWireInfoActivity.this.mYear, SuspensionWireInfoActivity.this.mMonth - 1, SuspensionWireInfoActivity.this.mDay).show();
        }
    }
}
