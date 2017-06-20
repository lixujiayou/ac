package com.inspur.resources.base;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.inspur.component.androidlifecyle.app.AppCompatActivity;
import com.inspur.easyresources.R;
import com.inspur.resources.bean.TubeInfoBean;
import com.inspur.resources.http.httpconnect;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.StaggeredGridLayoutManager.LayoutParams;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class BaseActivity extends AppCompatActivity {
	public static final int MESSAGE_CHUFA_READ = 110;
	public static final int MESSAGE_DEVICE_NAME = 4;
	public static final int MESSAGE_READ = 2;
	public static final int MESSAGE_STATE_CHANGE = 1;
	public static final int MESSAGE_TOAST = 5;
	private static final UUID MY_UUID;
	public static final int STATE_CONNECTED = 3;
	public static final int STATE_CONNECTING = 2;
	public static final int STATE_LISTEN = 1;
	public static final int STATE_NONE = 0;
	public static final String TOAST = "toast";
	public static int dsrcvflag;
	private BluetoothReceiver bluetoothReceiver;
	private DevStateReceiver devStateReceiver;
	private String dx;
	public boolean geted;
	Gson gson;
	private Button image;
	IntentFilter intentfilter;
	private BluetoothAdapter mBluetoothAdapter;
	private ProgressDialog mProgress;
	public Handler mhandler;
	String msgStr;
	public Handler myHandler;
	private myconnectThread n;
	Thread rd;
	private TextView title;
	OnClickListener ts_ok;
	OnClickListener ts_ok11;
	private LinearLayout rootLayout;
	public Toolbar toolBar;

	static {
		BaseActivity.dsrcvflag = 0;
		MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
	}

	public BaseActivity() {
		this.gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss Z").create();
		this.dx = "";
		this.mBluetoothAdapter = null;
		this.bluetoothReceiver = null;
		this.msgStr = "";
		this.intentfilter = null;
		this.ts_ok11 = new OnClickListener() {
			public void onClick(final DialogInterface dialogInterface, final int n) {
				dialogInterface.cancel();
			}
		};
		/*
		 * this.ts_ok = new OnClickListener() { public void onClick(final
		 * DialogInterface dialogInterface, final int n) {
		 * BaseActivity.this.startActivity(new
		 * Intent(BaseActivity.this.getApplicationContext(),
		 * (Class)LoginBtDeviceActivity.class)); dialogInterface.cancel(); } };
		 */
		this.geted = false;
		this.devStateReceiver = null;
		this.myHandler = new Handler() {
			public void handleMessage(final Message message) {
				if (BaseActivity.this.mProgress != null) {
					BaseActivity.this.mProgress.dismiss();
				}
				switch (message.what) {
				case 14: {
					new ArrayList();
					final List<TubeInfoBean> list = BaseActivity.this.gson.fromJson(message.obj.toString(),
							new TypeToken<List<TubeInfoBean>>() {
					}.getType());
					if (list.size() > 0) {
						final TubeInfoBean tubeInfoBean = list.get(0);

						return;
					}
					final String className = ((ActivityManager) BaseActivity.this.getSystemService(Activity.ACTIVITY_SERVICE))
							.getRunningTasks(1).get(0).topActivity.getClassName();
					if (!className.substring(className.lastIndexOf(".") + 1, className.length())
							.equals("TubeRFIDInfoActivity")) {
						Toast.makeText(BaseActivity.this.getApplicationContext(),
								(CharSequence) "\u6ca1\u6709\u5bf9\u5e94\u7684\u7ba1\u5b54\u4fe1\u606f", Toast.LENGTH_SHORT).show();
						return;
					}
					break;
				}
				case 12: {
					Toast.makeText(BaseActivity.this.getApplicationContext(), (CharSequence) message.obj.toString(), Toast.LENGTH_SHORT)
							.show();
					break;
				}
				}
			}
		};
		this.mhandler = new Handler() {
			public void handleMessage(final Message message) {
				switch (message.what) {
				default: {
				}
				case 1: {
					switch (message.arg1) {
					default: {
						return;
					}
					case 1: {
						BaseActivity.this.title.setText((CharSequence) "\u8fde\u63a5\u84dd\u7259\u5931\u8d25");
						BaseActivity.this.setRightDownView();
						break;
					}
					case 0: {
						BaseActivity.this.title.setText((CharSequence) "\u8fde\u63a5\u84dd\u7259\u5931\u8d25");
						BaseActivity.this.setRightDownView();
						return;
					}
					case 3: {
						BaseActivity.this.title.setText((CharSequence) "\u8fde\u63a5\u84dd\u7259\u6210\u529f");
						BaseActivity.this.setRightView();
						// BaseActivity.this.read_data();
						return;
					}
					case 2: {
						BaseActivity.this.title.setText((CharSequence) "\u6b63\u5728\u8fde\u63a5\u84dd\u7259...");
						BaseActivity.this.setRightView3();
						return;
					}
					case 4: {
						BaseActivity.this.setRightView2();
						return;
					}
					}
				}
				case 5: {
					BaseActivity.this.setRightDownView();
					BaseActivity.this.title.setText((CharSequence) "\u8fde\u63a5\u84dd\u7259\u5931\u8d25");
					break;
				}
				}
			}
		};
	}

	/*
	 * private void dsrcvdata(int rlen) { dx = ""; try { Function.mmInStream =
	 * DemoActivity.mmSocket.getInputStream(); } catch(IOException
	 * localIOException1) { } int t = 0x0; if(!Function.readflag) { return; }
	 * if(Function.endflag) { int bytes = 0x0; try { bytes =
	 * Function.mmInStream.available(); } catch(IOException localIOException2) {
	 * } if(bytes > 0) { t = 0x0; byte[] b = new byte[bytes]; try {
	 * Function.mmInStream.read(b); } catch(IOException localIOException3) { }
	 * dx = Function_2.bytestohexcode(b); if((dx.startsWith("55")) &&
	 * (dx.endsWith("AA"))) { Log.d("dxdxdxdxdxdxdx", dx); switch(dsrcvflag) {
	 * case 38120661: { String s1 = dx.substring(0x0, 0x8);
	 * if((s1.equalsIgnoreCase("550E2000")) && (dx.length() == (rlen * 0x2))) {
	 * String uid = dx.substring(0x8, 0x10); Log.d("uid\u662f\u591a\u5c11",
	 * uid); dx = ""; Function.endflag = false; Function.readflag = false;
	 * if(uid.length() == 0x8) { Intent intent3 = new Intent("mygb");
	 * intent3.putExtra("keyBroadcastState", "TEST_VI");
	 * intent3.putExtra("rfid", uid); sendBroadcast(intent3); }
	 * Toast.makeText(getApplicationContext(),
	 * "\u8bfb\u53d6\u6807\u7b7e\u51fa\u73b0\u5f02\u5e38\uff0c\u8bf7\u91cd\u8bd5",
	 * Toast.LENGTH_SHORT).show();; } dx = ""; Function.readflag = false; } case
	 * 38120662: { dx = ""; Function.endflag = false; Function.readflag = false;
	 * } } t = t + 0x1; if(t > 0x2710) { Function.readflag = false;
	 * Function.endflag = false; dx = ""; } } } // Parsing error may occure here
	 * :( } // Parsing error may occure here :( }
	 */

	private void getTubeData(final TubeInfoBean tubeInfoBean) {
		if (this.mProgress == null) {
			this.mProgress = ProgressDialog.show((Context) this, "ϵͳ��ʾ", "���ڻ�ȡ��Ӧ�Ŀ���Ϣ����");
		} else {
			this.mProgress.setMessage((CharSequence) "���ڻ�ȡ��Ӧ�Ŀ���Ϣ����");
			this.mProgress.show();
		}
		new Thread() {
			@Override
			public void run() {
				final String json = BaseActivity.this.gson.toJson(tubeInfoBean);
				final ArrayList<NameValuePair> list = new ArrayList<NameValuePair>();
				list.add((NameValuePair) new BasicNameValuePair("jsonRequest", json));
				final String httpGetData = new httpconnect().httpGetData("pdaPipe!getTube.interface", list,
						(Context) BaseActivity.this);
				Log.d("AddrList==>", String.valueOf(json) + "<==result==>" + httpGetData);
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
						message.what = 14;
						message.obj = string;
						BaseActivity.this.myHandler.sendMessage(message);
						return;
					}
					message.what = 12;
					message.obj = string;
					BaseActivity.this.myHandler.sendMessage(message);
				} catch (JSONException ex) {
				}
			}
		}.start();
	}

	protected void closeReceiverBlue() {
		if (this.bluetoothReceiver != null) {
			this.stopService(new Intent((Context) this, (Class) BluetoothReceiver.class));
			this.unregisterReceiver((BroadcastReceiver) this.bluetoothReceiver);
			this.bluetoothReceiver = null;
		}
	}

	public void connect(final BluetoothDevice bluetoothDevice) {
		synchronized (this) {
			if (this.n != null) {
				this.n.cancel();
				this.n = null;
			}
			(this.n = new myconnectThread(bluetoothDevice)).start();
			this.setState(2);
		}
	}

	public void connectionFailed() {
		this.setState(1);
		final Message obtainMessage = this.mhandler.obtainMessage(5);
		final Bundle data = new Bundle();
		data.putString("toast", "\u8fde\u63a5\u5931\u8d25\uff0c\u8bf7\u91cd\u8bd5");
		obtainMessage.setData(data);
		this.mhandler.sendMessage(obtainMessage);
	}

	protected void keyballDissmiss() {
		this.getWindow().getDecorView().setOnTouchListener(new View.OnTouchListener() {
			public boolean onTouch(final View view, final MotionEvent motionEvent) {
				((InputMethodManager) BaseActivity.this.getSystemService(Activity.INPUT_METHOD_SERVICE))//input_method
						.hideSoftInputFromWindow(view.getWindowToken(), 2);
				return false;
			}
		});
	}

	protected void onCreate(final Bundle bundle) {
		super.onCreate(bundle);
		super.setContentView(R.layout.activity_base);
		SysApplication.getInstance().addActivity(this);
		
		if (Build.VERSION.SDK_INT >= 21) {
			WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
			localLayoutParams.flags = (0x4000000 | localLayoutParams.flags);
		}
		
		if (true) {
			return;
		}
		
		this.requestWindowFeature(7);
		this.setContentView(R.layout.activity_main);
		this.getWindow().setFeatureInt(7, 2130903114);
		this.keyballDissmiss();
		this.image = (Button) this.findViewById(R.id.button_qingcha);
		this.title = (TextView) this.findViewById(R.id.tips_text);
		final String className = ((ActivityManager) this.getSystemService(Activity.ACTIVITY_SERVICE)).getRunningTasks(1)
				.get(0).topActivity.getClassName();
		// if (className.substring(className.lastIndexOf(".") + 1,
		// className.length()).equals("LoginActivity")) {

		this.image.setVisibility(View.GONE);
		// }
		/*this.mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
		Log.d("\u8d70\u7684\u8fd9\u91cc", "\u662f\u4ec0\u4e48" + DemoActivity.connectedflag);
		if (DemoActivity.connectedflag) {
			this.setRightView();
		} else {
			this.setRightDownView();
		}
		this.openReceiverBlue();
		this.image.setOnClickListener(new View.OnClickListener() {
			@TargetApi(Build.VERSION_CODES.HONEYCOMB)
			public void onClick(final View view) {
				if (BaseActivity.this.mBluetoothAdapter == null) {
					Toast.makeText(BaseActivity.this.getApplicationContext(),
							(CharSequence) "\u65e0\u6cd5\u6253\u5f00\u624b\u673a\u84dd\u7259", 0).show();
					return;
				}
				if (!BaseActivity.this.mBluetoothAdapter.isEnabled()) {
					Toast.makeText(BaseActivity.this.getApplicationContext(),
							(CharSequence) "\u6b63\u5728\u5f00\u542f\u84dd\u7259,\u8bf7\u5728\u5f00\u542f\u540e\u91cd\u8bd5",
							0).show();
					BaseActivity.this.mBluetoothAdapter.enable();
					return;
				}
				if (ApplicationValue.rfid_default_bluetooth_address == null) {
					final AlertDialog create = new AlertDialog.Builder(BaseActivity.this.getApplicationContext(), 3)
							.create();
					create.setTitle((CharSequence) "\u63d0\u793a\uff1a");
					create.setIcon(2130837600);
					create.setMessage(
							(CharSequence) "\u672a\u9009\u62e9\u8fde\u63a5\u7684\u84dd\u7259\uff0c\u73b0\u5728\u53bb\u9009\u62e9\u5417\uff1f");
					create.setButton(-1, (CharSequence) "ȷ��", BaseActivity.this.ts_ok);
					create.setButton(-2, (CharSequence) "ȡ��", BaseActivity.this.ts_ok11);
					create.getWindow().setType(2003);
					create.show();
					return;
				}
				BaseActivity.this.connect(BaseActivity.this.mBluetoothAdapter
						.getRemoteDevice(ApplicationValue.rfid_default_bluetooth_address));
			}
		});*/
	}

	protected void onDestroy() {
		super.onDestroy();
		if (this.devStateReceiver != null) {
			this.stopService(new Intent((Context) this, (Class) DevStateReceiver.class));
			this.unregisterReceiver((BroadcastReceiver) this.devStateReceiver);
			this.devStateReceiver = null;
		}
		this.closeReceiverBlue();
	}

	protected void onPause() {
		super.onPause();
		if (this.devStateReceiver != null) {
			this.stopService(new Intent((Context) this, (Class) DevStateReceiver.class));
			this.unregisterReceiver((BroadcastReceiver) this.devStateReceiver);
			this.devStateReceiver = null;
		}
		this.closeReceiverBlue();
	}

	protected void onRestart() {
		super.onRestart();
		this.openReceiverVI();
		this.openReceiverBlue();
	}

	protected void onResume() {
		super.onResume();
		/*
		 * if (DemoActivity.connectedflag) { this.setRightView(); } else {
		 * this.setRightDownView(); } this.openReceiverVI();
		 * this.openReceiverBlue();
		 */
	}

	protected boolean isUseDefaultToolBar() {
		return true;
	}

	public void setContentView(int paramInt) {
		setContentView(View.inflate(this, paramInt, null));
	}
private View contentView;
	public void setContentView(View paramView) {
		contentView = paramView;
		rootLayout = ((LinearLayout) findViewById(R.id.root_layout));
		if (rootLayout == null) {
			return;
		}
		if (!isUseDefaultToolBar()) {
			rootLayout.removeAllViews();
			rootLayout.addView(paramView,
					new ViewGroup.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
			return;
		}
		rootLayout.addView(paramView, new ViewGroup.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		toolBar = (Toolbar) findViewById(R.id.toolbar);
		if (toolBar != null) {
			setSupportActionBar(toolBar);
	        ActionBar actionBar = getSupportActionBar();
	        actionBar.setDisplayHomeAsUpEnabled(true);
			((InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(paramView.getWindowToken(),
					0);
			getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_UNSPECIFIED);
		}
	}

	protected void onStop() {
		super.onStop();
		if (this.devStateReceiver != null) {
			this.stopService(new Intent((Context) this, (Class) DevStateReceiver.class));
			this.unregisterReceiver((BroadcastReceiver) this.devStateReceiver);
			this.devStateReceiver = null;
		}
		this.closeReceiverBlue();
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			onBackPressed();
			return true;
		}

		return super.onOptionsItemSelected(item);
	}
	
	protected final void addNoticeView(View view) {
		for (int i = 0; i < rootLayout.getChildCount(); i++)
			if (rootLayout.getChildAt(i).equals(contentView)) {
				rootLayout.addView(view, i,
						new ViewGroup.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
				break;
			}
	}

	protected void openReceiverBlue() {
		final IntentFilter intentFilter = new IntentFilter("android.bluetooth.device.action.ACL_CONNECTED");
		final IntentFilter intentFilter2 = new IntentFilter("android.bluetooth.device.action.ACL_DISCONNECTED");
		if (this.bluetoothReceiver == null) {
			this.registerReceiver((BroadcastReceiver) (this.bluetoothReceiver = new BluetoothReceiver()), intentFilter);
			this.registerReceiver((BroadcastReceiver) this.bluetoothReceiver, intentFilter2);
		}
	}

	protected void openReceiverVI() {
		if (this.devStateReceiver == null) {
			this.registerReceiver((BroadcastReceiver) (this.devStateReceiver = new DevStateReceiver()),
					new IntentFilter("mygb"));
		}
	}

	/*
	 * public void read_data() { BaseActivity.dsrcvflag = 1; this.geted = false;
	 * Function.modeflag = true; if (Function.modeflag) { (this.rd = new
	 * Thread(new myrunnablethread())).start(); } }
	 */

	public void setRightDownView() {
	//	this.image.setBackgroundResource(2130837538);
		this.image.setEnabled(true);
	}

	public void setRightView() {
	//	this.image.setBackgroundResource(2130837537);
		this.image.setEnabled(false);
	}

	public void setRightView2() {
		//this.image.setBackgroundResource(2130837539);
		this.image.setEnabled(false);
	}

	public void setRightView3() {
		//this.image.setBackgroundResource(2130837540);
		this.image.setEnabled(false);
	}

	public void setState(final int n) {
		synchronized (this) {
			this.mhandler.obtainMessage(1, n, -1).sendToTarget();
		}
	}

	private class BluetoothReceiver extends BroadcastReceiver {
		public void onReceive(final Context context, final Intent intent) {
			Log.d("\u8d70\u8fd9\u91cc\u4e86", "\u8d70\u4e86222222222222222");
			if ("android.bluetooth.device.action.ACL_CONNECTED".equals(intent.getAction())) {
				Log.d("\u8d70\u8fd9\u91cc\u4e86", "\u84dd\u7259\u8fde\u63a5\u4e86");
			} else if ("android.bluetooth.device.action.ACL_DISCONNECTED".equals(intent.getAction())) {
				Log.d("\u8d70\u8fd9\u91cc\u4e86", "\u84dd\u7259\u65ad\u5f00\u4e86");
				BaseActivity.this.title.setText((CharSequence) "\u84dd\u7259\u8fde\u63a5\u5df2\u65ad\u5f00");
				BaseActivity.this.setRightDownView();
			}
		}
	}

	private class DevStateReceiver extends BroadcastReceiver {
		public void onReceive(final Context context, final Intent intent) {
			if ("TEST_VI".equals(intent.getStringExtra("keyBroadcastState"))) {
				final String className = ((ActivityManager) BaseActivity.this.getSystemService(Activity.ACTIVITY_SERVICE))
						.getRunningTasks(1).get(0).topActivity.getClassName();
				if (!className.substring(className.lastIndexOf(".") + 1, className.length()).equals("LoginActivity")) {
					final TubeInfoBean tubeInfoBean = new TubeInfoBean();
					tubeInfoBean.setRfid(intent.getStringExtra("rfid"));
					Log.d("rfid\u662f\u591a\u5c11", "\u662f\uff1a" + intent.getStringExtra("rfid"));
					BaseActivity.this.getTubeData(tubeInfoBean);
				}
			}
		}
	}

	class myconnectThread extends Thread {
		int x;

		public myconnectThread(final BluetoothDevice bluetoothDevice) {
			this.x = 0;
		}

		public void cancel() {
		}

		public boolean delayxtime(final int n) {
			boolean b = false;
			if (n > 0) {
				b = true;
			}
			return b;
		}

		@Override
		public void run() {
			this.x = 10000;
		}
	}

	/*
	 * class myrunnablethread implements Runnable {
	 * 
	 * @Override public void run() { while (BaseActivity.dsrcvflag == 1) { if
	 * (!BaseActivity.this.geted) { Function.endflag = true; Function.readflag =
	 * true; BaseActivity.this.dsrcvdata(16); try { Thread.sleep(300L); } catch
	 * (InterruptedException ex) {} } } } }
	 */
}
