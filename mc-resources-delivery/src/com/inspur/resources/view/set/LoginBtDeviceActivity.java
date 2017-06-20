// 

// 

package com.inspur.resources.view.set;

import java.util.Comparator;
import java.util.Set;

import com.inspur.resources.utils.ApplicationValue;

import android.app.Activity;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class LoginBtDeviceActivity extends Activity
{
    private final String TAG;
    private int dayinji;
    private BluetoothAdapter mBtAdapter;
    private OnItemClickListener mDeviceClickListener;
    private ArrayAdapter<String> mPairedDevicesArrayAdapter;
    private ListView pairedListView;
    private int rfid;
    private String theAddress;
    private String theName;

    public LoginBtDeviceActivity() {
        this.TAG = "LoginBtDeviceActivity";
        this.theAddress = null;
        this.theName = null;
        this.rfid = 0;
        this.dayinji = 0;
        this.mDeviceClickListener = new OnItemClickListener() {
            public void onItemClick(final AdapterView<?> adapterView, final View view, final int n, final long n2) {
                if (LoginBtDeviceActivity.this.mBtAdapter.isDiscovering()) {
                    LoginBtDeviceActivity.this.mBtAdapter.cancelDiscovery();
                }
                final String string = ((TextView)view).getText().toString();
                if (string.length() >= 17) {
                    Log.d("\u8fdb\u676517\u4e86\u5417", "\u8fdb\u676517\u4e86" + LoginBtDeviceActivity.this.rfid + "..." + LoginBtDeviceActivity.this.dayinji);
                    LoginBtDeviceActivity.access$3(LoginBtDeviceActivity.this, string.substring(string.length() - 17));
                    LoginBtDeviceActivity.access$4(LoginBtDeviceActivity.this, string.substring(0, string.length() - 17).trim());
                    LoginBtDeviceActivity.this.update_Default_Bluetooth();
                    LoginBtDeviceActivity.this.getAc();
                }
            }
        };
    }

    static /* synthetic */ void access$3(final LoginBtDeviceActivity loginBtDeviceActivity, final String theAddress) {
        loginBtDeviceActivity.theAddress = theAddress;
    }

    static /* synthetic */ void access$4(final LoginBtDeviceActivity loginBtDeviceActivity, final String theName) {
        loginBtDeviceActivity.theName = theName;
    }


    protected void getAc() {
        this.setResult(1);
        this.finish();
    }

    protected void init() {
        this.setTitle((CharSequence)"\u9009\u62e9\u8981\u8fde\u63a5\u7684\u84dd\u7259");
        this.initBlue();
    }

    protected void initBlue() {
        final Intent intent = this.getIntent();
        this.rfid = intent.getIntExtra("rfid", 0);
        this.dayinji = intent.getIntExtra("dayinji", 0);
        this.pairedListView.setOnItemClickListener(this.mDeviceClickListener);
        this.mBtAdapter = BluetoothAdapter.getDefaultAdapter();
        final Set<BluetoothDevice> bondedDevices = this.mBtAdapter.getBondedDevices();
        if (bondedDevices.size() > 0) {
            for (final BluetoothDevice bluetoothDevice : bondedDevices) {
                if (bluetoothDevice.getName() != null && !bluetoothDevice.getName().equals("null") && !bluetoothDevice.getName().equals("")) {
                    this.mPairedDevicesArrayAdapter.add(String.valueOf(bluetoothDevice.getName()) + "\n" + bluetoothDevice.getAddress());
                }

            }
            this.mPairedDevicesArrayAdapter.sort((Comparator)new Comparator<String>() {
                @Override
                public int compare(final String s, final String s2) {
                    return s.compareTo(s2);
                }
            });
            return;
        }
        this.mPairedDevicesArrayAdapter.add("\u6ca1\u6709\u5df2\u914d\u5bf9\u7684\u84dd\u7259\u8bbe\u5907");
    }

    protected void onCreate(final Bundle bundle) {
        super.onCreate(bundle);
        this.init();
    }

    protected void update_Default_Bluetooth() {
        Log.d("\u8fdb\u6765rfid\u4e86\u5417", "\u8fdb\u6765rfid\u4e86");
        ApplicationValue.rfid_default_bluetooth_name = this.theName;
        ApplicationValue.rfid_default_bluetooth_address = this.theAddress;
        final SharedPreferences.Editor edit = this.getSharedPreferences("SAVE_LOGINSET", 0).edit();
        edit.putString("SAVE_DEF_BLUETOOTH_NAME1", this.theName);
        edit.putString("SAVE_DEF_BLUETOOTH_ADDRESS1", this.theAddress);
        edit.commit();
    }
}
