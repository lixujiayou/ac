package com.inspur;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.Locale;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.pm.PackageManager;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

/**
 * This class provides several static methods to retrieve information about the
 * current device and operating environment.
 *
 * It is important to call setDeviceID early, before logging any session or
 * custom event data.
 */
public class DeviceInfo {
	/**
	 * Returns the display name of the current operating system.
	 */
	static String getOS() {
		return "Android";
	}

	/**
	 * Returns the current operating system version as a displayable string.
	 */
	static String getOSVersion() {
		return android.os.Build.VERSION.RELEASE;
	}

	/**
	 * Returns the current device model.
	 */
	static String getDevice() {
		return android.os.Build.MODEL;
	}

	/**
	 * Returns the non-scaled pixel resolution of the current default display
	 * being used by the WindowManager in the specified context.
	 * 
	 * @param context
	 *            context to use to retrieve the current WindowManager
	 * @return a string in the format "WxH", or the empty string "" if
	 *         resolution cannot be determined
	 */
	static String getResolution(final Context context) {
		// user reported NPE in this method; that means either getSystemService
		// or getDefaultDisplay
		// were returning null, even though the documentation doesn't say they
		// should do so; so now
		// we catch Throwable and return empty string if that happens
		String resolution = "";
		try {
			final WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
			final Display display = wm.getDefaultDisplay();
			final DisplayMetrics metrics = new DisplayMetrics();
			display.getMetrics(metrics);
			resolution = metrics.widthPixels + "x" + metrics.heightPixels;
		} catch (Throwable t) {
			
		}
		return resolution;
	}

	/**
	 * Maps the current display density to a string constant.
	 * 
	 * @param context
	 *            context to use to retrieve the current display metrics
	 * @return a string constant representing the current display density, or
	 *         the empty string if the density is unknown
	 */
	static String getDensity(final Context context) {
		String densityStr = "";
		final int density = context.getResources().getDisplayMetrics().densityDpi;
		switch (density) {
		case DisplayMetrics.DENSITY_LOW:
			densityStr = "LDPI";
			break;
		case DisplayMetrics.DENSITY_MEDIUM:
			densityStr = "MDPI";
			break;
		case DisplayMetrics.DENSITY_TV:
			densityStr = "TVDPI";
			break;
		case DisplayMetrics.DENSITY_HIGH:
			densityStr = "HDPI";
			break;
		case DisplayMetrics.DENSITY_XHIGH:
			densityStr = "XHDPI";
			break;
		case DisplayMetrics.DENSITY_400:
			densityStr = "XMHDPI";
			break;
		case DisplayMetrics.DENSITY_XXHIGH:
			densityStr = "XXHDPI";
			break;
		case DisplayMetrics.DENSITY_XXXHIGH:
			densityStr = "XXXHDPI";
			break;
		}
		return densityStr;
	}

	/**
	 * Returns the display name of the current network operator from the
	 * TelephonyManager from the specified context.
	 * 
	 * @param context
	 *            context to use to retrieve the TelephonyManager from
	 * @return the display name of the current network operator, or the empty
	 *         string if it cannot be accessed or determined
	 */
	static String getCarrier(final Context context) {
		String carrier = "";
		final TelephonyManager manager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		if (manager != null) {
			carrier = manager.getNetworkOperatorName();
		}
		if (carrier == null || carrier.length() == 0) {
			carrier = "";
		}
		return carrier;
	}

	/**
	 * Returns the current locale (ex. "en_US").
	 */
	static String getLocale() {
		final Locale locale = Locale.getDefault();
		return locale.getLanguage() + "_" + locale.getCountry();
	}

	/**
	 * Returns the application version string stored in the specified context's
	 * package info versionName field, or "1.0" if versionName is not present.
	 */
	public static String getAppVersion(final Context context) {
		String result = "";
		try {
			result = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
		} catch (PackageManager.NameNotFoundException e) {
			
		}
		return result;
	}

	/**
	 * Returns a URL-encoded JSON string containing the device metrics to be
	 * associated with a begin session event. 
	 */
	public static String getMetrics(final Context context) {
		final JSONObject json = new JSONObject();

		fillJSONIfValuesNotEmpty(json, "_device", getDevice(), "_os", getOS(), "_os_version", getOSVersion(),
				"_carrier", getCarrier(context), "_resolution", getResolution(context), "_density", getDensity(context),
				"_locale", getLocale(), "_app_version", getAppVersion(context));

		String result = json.toString();

		/*try {
			result = java.net.URLEncoder.encode(result, "UTF-8");
		} catch (UnsupportedEncodingException ignored) {
			// should never happen because Android guarantees UTF-8 support
		}*/

		return result;
	}

	public static String getUDID(Context context) {
		return Installation.getID(context);
		/*// Try to get the ANDROID_ID
		String UDID = Secure.getString(context.getContentResolver(), Secure.ANDROID_ID);
		if (UDID == null || UDID.equals("9774d56d682e549c") || UDID.length() < 15) {
			// if ANDROID_ID is null, or it's equals to the GalaxyTab generic
			// ANDROID_ID or bad, generates a new one
			final SecureRandom random = new SecureRandom();
			UDID = new BigInteger(64, random).toString(16);
		}
		return UDID;*/
	}

	/**
	 * Utility method to fill JSONObject with supplied objects for supplied
	 * keys. Fills json only with non-null and non-empty key/value pairs.
	 * 
	 * @param json
	 *            JSONObject to fill
	 * @param objects
	 *            varargs of this kind: key1, value1, key2, value2, ...
	 */
	static void fillJSONIfValuesNotEmpty(final JSONObject json, final String... objects) {
		try {
			if (objects.length > 0 && objects.length % 2 == 0) {
				for (int i = 0; i < objects.length; i += 2) {
					final String key = objects[i];
					final String value = objects[i + 1];
					if (value != null && value.length() > 0) {
						json.put(key, value);
					}
				}
			}
		} catch (JSONException ignored) {
			// shouldn't ever happen when putting String objects into a
			// JSONObject,
			// it can only happen when putting NaN or INFINITE doubles or floats
			// into it
		}
	}
	public static String getApkSHA(Context context) {
		String apkPath = context.getPackageCodePath();

		String sha = "";
		MessageDigest msgDigest = null;
		FileInputStream fis = null;
		try {

			msgDigest = MessageDigest.getInstance("SHA-1");
			byte[] bytes = new byte[1024];
			int byteCount;
			fis = new FileInputStream(new File(apkPath));
			while ((byteCount = fis.read(bytes)) > 0){
				msgDigest.update(bytes, 0, byteCount);
			}
			BigInteger bi = new BigInteger(1, msgDigest.digest());
			sha = bi.toString(16);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				fis.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return sha;
	}
}
