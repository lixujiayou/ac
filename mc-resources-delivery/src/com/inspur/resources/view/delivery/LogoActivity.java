package com.inspur.resources.view.delivery;


import com.inspur.StringUtils;
import com.inspur.easyresources.R;
import com.inspur.resources.utils.ApplicationValue;
import com.inspur.zsyw.platform.Platform;
import com.inspur.zsyw.platform.Platform.PlatformCallback;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import cn.trinea.android.common.util.PreferencesUtils;
import cn.trinea.android.common.util.ToastUtils;

public class LogoActivity extends Activity {
	private static String action;

	private Platform platform;

	private ImageView logo;
	private TextView name;
	public AnimatorSet animator(View view, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6, long paramLong1, long paramLong2)
	{
		ObjectAnimator localObjectAnimator1 = ObjectAnimator.ofFloat(view, "alpha", new float[] { paramFloat1, paramFloat2 });
		ObjectAnimator localObjectAnimator2 = ObjectAnimator.ofFloat(view, "scaleX", new float[] { paramFloat3, paramFloat4 });
		ObjectAnimator localObjectAnimator3 = ObjectAnimator.ofFloat(view, "scaleY", new float[] { paramFloat5, paramFloat6 });
		AnimatorSet localAnimatorSet = new AnimatorSet();
		localAnimatorSet.play(localObjectAnimator1).with(localObjectAnimator2).with(localObjectAnimator3);
		localAnimatorSet.setDuration(paramLong2);
		localAnimatorSet.setStartDelay(paramLong1);
		localAnimatorSet.start();
		return localAnimatorSet;
	}

	/*PlatformCallback callback = new PlatformCallback() {
		@Override
		public void onSuccess(String response) {

			//			name.setText("action="+action);
			//			ToastUtils.show(LogoActivity.this, "action:"+action);
			if("getBaseUrl".equalsIgnoreCase(action)){

				//				new AlertDialog.Builder(LogoActivity.this).setTitle("提示1").setMessage(response.substring(0, response.lastIndexOf(":"))).show();
				//				name.setText("action="+action+"--ip="+response.substring(0, response.lastIndexOf(":")));
				//				ToastUtils.show(LogoActivity.this, "ip:"+response.substring(0, response.lastIndexOf(":")));

				ApplicationValue.url = response.substring(0, response.lastIndexOf(":"))+":7014/InventoryManager/";


				//mProgress.setTitle("URL=="+ApplicationValue.url);


				if(StringUtils.isEmpty(ApplicationValue.url)||!ApplicationValue.url.toLowerCase().startsWith("http")){
					ToastUtils.show(LogoActivity.this, "无法加载服务信息!");
					finish();
					return;
				}
				action = "getUserName";
				platform.getUserName(getPackageName());

			}else if("getUserName".equalsIgnoreCase(action)){
				//	 mProgress.setTitle("UID"+response);
				ApplicationValue.UID = response;
				PreferencesUtils.putString(LogoActivity.this, "UID", response);

				if (mProgress != null) {
					mProgress.dismiss();
				}
				//	 name.setText(ApplicationValue.url+"只挑action="+action);
				startActivity(new Intent(LogoActivity.this,com.inspur.resources.view.delivery.MainActivity.class));
				finish();
			}
		}
		@Override
		public void onFailure(String response) {
			//	mProgress.setTitle("加载失败"+response);
			Toast.makeText(LogoActivity.this, "无法加载用户信息!"+response, Toast.LENGTH_SHORT).show();
			if (mProgress != null) {
				mProgress.dismiss();
			}
			finish();
		}
		@Override
		public void onServiceConnected() {
			//	mProgress.setTitle("链接成功");

			// 连接成功,调用接口
			ToastUtils.show(LogoActivity.this, "链接成功");
			//	name.setText("链接成功");
			action = "getBaseUrl";
			platform.getBaseUrl(getPackageName());

		}

	};*/


	private ProgressDialog mProgress;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_delivery_logo);

		logo = (ImageView)findViewById(R.id.logo);
		name = (TextView)findViewById(R.id.title);

		//测试包注释掉下面五行
//		Intent intent = getIntent();
//		String mUrl = intent.getExtras().getString("baseUrl");
//		ApplicationValue.url = mUrl.substring(0, mUrl.lastIndexOf(":"))+":7014/InventoryManager/";
//		ApplicationValue.UID = intent.getExtras().getString("username");
//		PreferencesUtils.putString(LogoActivity.this, "UID", ApplicationValue.UID);

		//正式平台应该注释掉下面两行
		ApplicationValue.UID = "ChoasTest";
		PreferencesUtils.putString(LogoActivity.this, "UID", "ChoasTest");


		startActivity(new Intent(LogoActivity.this, com.inspur.resources.view.delivery.MainActivity.class));
	}

	@Override
	protected void onDestroy() {
		if (null != platform)

			platform.unbindService();// 注销服务
			super.onDestroy();

	}
}