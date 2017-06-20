package com.inspur.resources.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

public class CommonUtils {
	public static void AlertDialog(Context context, int titleId, int positiveBtnTextId,
			DialogInterface.OnClickListener positiveBtnOnClickListener, int negativeBtnTextId,
			DialogInterface.OnClickListener negativeBtnOnClickListener, String content) {
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setTitle(titleId);
		builder.setMessage(content);
		if (negativeBtnOnClickListener != null) {
			builder.setNegativeButton(negativeBtnTextId, negativeBtnOnClickListener);
		}
		if (positiveBtnOnClickListener != null) {
			builder.setPositiveButton(positiveBtnTextId, positiveBtnOnClickListener);
		}
		builder.setCancelable(false);
		AlertDialog dialog = builder.create();
		dialog.setCanceledOnTouchOutside(false);

		dialog.show();
	}

	public static Date StringToDate(final String s) {
		final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		try {
			return simpleDateFormat.parse(s);
		} catch (ParseException ex) {
			return null;
		}
	}

	public static void closeSoftKeyboard(final Activity activity) {
		if (activity == null) {
			return;
		}
		try {
			((InputMethodManager) activity.getSystemService("input_method"))
					.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
		} catch (Throwable t) {
		}
	}

	public static String datetoStringNoTime(final Date date) {
		final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		try {
			return simpleDateFormat.format(date);
		} catch (Exception ex) {
			return null;
		}
	}

	public static boolean getJPG(final Bitmap bitmap, final String path) {
		File file = new File(path);
		if (file.exists()) {

		}
		if (bitmap == null)
			return false;
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(file);
			bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
			fos.flush();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return false;
	}

	public static String getPhotoNumber(final Context context) {
		final TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService("phone");
		final String deviceId = telephonyManager.getDeviceId();
		final String subscriberId = telephonyManager.getSubscriberId();
		final String model = Build.MODEL;
		final String brand = Build.BRAND;
		String s2;
		final String s = s2 = telephonyManager.getLine1Number();
		if (s != null) {
			s2 = s;
			if (s.contains("+")) {
				s2 = s.substring(3, s.length());
			}
		}
		Log.i("text",
				"\u624b\u673aIMEI\u53f7\uff1a" + deviceId + "\u624b\u673aIMSI\u53f7\uff1a" + subscriberId
						+ "\u624b\u673a\u578b\u53f7\uff1a" + model + "\u624b\u673a\u54c1\u724c\uff1a" + brand
						+ "\u624b\u673a\u53f7\u7801" + s2);
		return s2;
	}

	public static String intRtoStr(final Context context, final int n) {
		return context.getString(n);
	}

	public static boolean isConnectInternet(final Context context) {
		final boolean b = false;
		final NetworkInfo networkInfo = ((ConnectivityManager) context.getSystemService("connectivity"))
				.getNetworkInfo(0);
		boolean b2 = b;
		if (networkInfo != null) {
			b2 = b;
			if (networkInfo.getState() == NetworkInfo.State.CONNECTED) {
				b2 = true;
			}
		}
		return b2;
	}

	public static boolean isEmpty(final String s) {
		return s == null || "".equals(s);
	}

	public static boolean isWifiEnabled(final Context context) {
		final ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
		final TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService("phone");
		return (connectivityManager.getActiveNetworkInfo() != null
				&& connectivityManager.getActiveNetworkInfo().getState() == NetworkInfo.State.CONNECTED)
				|| telephonyManager.getNetworkType() == 3;
	}

	public static void showToast(final Context context, final String s, final int n) {
		if (context == null || isEmpty(s)) {
			return;
		}
		Toast.makeText(context, (CharSequence) s, n).show();
	}

	public static void showToastLong(final Context context, final int n) {
		if (context == null) {
			return;
		}
		showToastLong(context, context.getResources().getString(n));
	}

	public static void showToastLong(final Context context, final String s) {
		if (context == null || isEmpty(s)) {
			return;
		}
		showToast(context, s, 1);
	}
}
