package com.inspur;

import java.io.UnsupportedEncodingException;
import java.util.Map;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.util.Base64;
public class PerferenceModel {

	public static PerferenceModel pm = null;
	public static Context context;
	public static final String SETTING_FILE = "preference_setting";

	public static PerferenceModel getPM(Context context) {
		PerferenceModel.context = context;
		if (pm != null) {
			return pm;
		} else {
			pm = new PerferenceModel();
			SharedPreferences perference = null;
			int sdk = VERSION.SDK_INT;
			if(sdk >VERSION_CODES.GINGERBREAD_MR1){
				perference = context.getSharedPreferences(SETTING_FILE,
						Context.MODE_MULTI_PROCESS);
			}else{
				perference = context.getSharedPreferences(SETTING_FILE, Context.MODE_PRIVATE);
			}
			encrypt(perference);
		}

		return pm;

	}

	private static void encrypt(SharedPreferences perference) {
		Map<String, ?> allContent = perference.getAll();
		if (allContent.containsKey("encrypted")) {
			return;
		}

		for (Map.Entry<String, ?> entry : allContent.entrySet()) {
			// entry.getKey()+entry.getValue());
			if(entry.getValue()==null||((entry.getValue() instanceof String)&&StringUtils.isEmpty(String.valueOf(entry.getValue())))){
				continue;
			}
			try {
				perference
						.edit()
						.putString(
								entry.getKey(),
								new String(Base64.encode(Des3.des3EncodeCBC(
										"YWJj1ZSyw2hpamtsbW5vcHFyc3R1dnd4"
												.getBytes(), "25439768"
												.getBytes(),
										String.valueOf(entry.getValue())
												.getBytes()), Base64.DEFAULT),
										"UTF-8")).commit();
			} catch (Exception e) {
				e.printStackTrace();
				perference.edit().remove(entry.getKey()).commit();
				continue;
			}
		}

		perference.edit().putBoolean("encrypted", true).commit();
		return;
	}


	/**
	 * 保存数据 SharedPreferences
	 * @param key
	 * @param value
	 */
	public void insertPreference(String key, String value) {
		SharedPreferences perference = null;
		int sdk = VERSION.SDK_INT;
		if(sdk >VERSION_CODES.GINGERBREAD_MR1){
			perference = context.getSharedPreferences(SETTING_FILE,
					Context.MODE_MULTI_PROCESS);
		}else{
			perference = context.getSharedPreferences(SETTING_FILE, Context.MODE_PRIVATE);
		}
		try {
			perference.edit().putString(key, new String(Base64.encode(Des3.des3EncodeCBC(
					"YWJj1ZSyw2hpamtsbW5vcHFyc3R1dnd4"
							.getBytes(), "25439768"
							.getBytes(),
					String.valueOf(value)
							.getBytes()), Base64.DEFAULT),
					"UTF-8")).commit();
		} catch (UnsupportedEncodingException e) {
			perference.edit().remove(key).commit();
			e.printStackTrace();
		} catch (Exception e) {
			perference.edit().remove(key).commit();
			e.printStackTrace();
		}
	}

	/**
	 * 取数据
	 * @param key
	 * @return
	 */
	public String getValue(String key, String def) {
		String value = null;
		SharedPreferences perference = null;
		int sdk = VERSION.SDK_INT;
		if(sdk >VERSION_CODES.GINGERBREAD_MR1){
			perference = context.getSharedPreferences(SETTING_FILE,
					Context.MODE_MULTI_PROCESS);
		}else{
			perference = context.getSharedPreferences(SETTING_FILE, Context.MODE_PRIVATE);
		}
		value = perference.getString(key, "");
		if(StringUtils.isEmpty(value)){
			return def;
		}
		try {
			return new String(Des3.des3DecodeCBC(
					"YWJj1ZSyw2hpamtsbW5vcHFyc3R1dnd4".getBytes(),
					"25439768".getBytes(), Base64.decode(value,Base64.DEFAULT)), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			perference.edit().remove(key).commit();
			e.printStackTrace();
		} catch (Exception e) {
			perference.edit().remove(key).commit();
			e.printStackTrace();
		}
		return def;
	}

	public boolean getValue(String key, boolean def) {
		SharedPreferences perference = null;
		int sdk = VERSION.SDK_INT;
		if(sdk >VERSION_CODES.GINGERBREAD_MR1){
			perference = context.getSharedPreferences(SETTING_FILE,
					Context.MODE_MULTI_PROCESS);
		}else{
			perference = context.getSharedPreferences(SETTING_FILE, Context.MODE_PRIVATE);
		}
		String value = perference.getString(key, "");
		if(StringUtils.isEmpty(value)){
			return def;
		}
		try {
			value = new String(Des3.des3DecodeCBC(
					"YWJj1ZSyw2hpamtsbW5vcHFyc3R1dnd4".getBytes(),
					"25439768".getBytes(), Base64.decode(value,Base64.DEFAULT)), "UTF-8");
			return Boolean.parseBoolean(value);
		} catch (UnsupportedEncodingException e) {
			perference.edit().remove(key).commit();
			e.printStackTrace();
		} catch (Exception e) {
			perference.edit().remove(key).commit();
			e.printStackTrace();
		}
		return def;
	}

	public void insertPreference(String key, boolean value) {
		SharedPreferences perference = null;
		int sdk = VERSION.SDK_INT;
		if(sdk >VERSION_CODES.GINGERBREAD_MR1){
			perference = context.getSharedPreferences(SETTING_FILE,
					Context.MODE_MULTI_PROCESS);
		}else{
			perference = context.getSharedPreferences(SETTING_FILE, Context.MODE_PRIVATE);
		}
		try {
			perference.edit().putString(key, new String(Base64.encode(Des3.des3EncodeCBC(
					"YWJj1ZSyw2hpamtsbW5vcHFyc3R1dnd4"
							.getBytes(), "25439768"
							.getBytes(),
					String.valueOf(value)
							.getBytes()), Base64.DEFAULT),
					"UTF-8")).commit();
		} catch (UnsupportedEncodingException e) {
			perference.edit().remove(key).commit();
			e.printStackTrace();
		} catch (Exception e) {
			perference.edit().remove(key).commit();
			e.printStackTrace();
		}
	}

	public int getValue(String key, int def) {
		SharedPreferences perference = null;
		int sdk = VERSION.SDK_INT;
		if(sdk >VERSION_CODES.GINGERBREAD_MR1){
			perference = context.getSharedPreferences(SETTING_FILE,
					Context.MODE_MULTI_PROCESS);
		}else{
			perference = context.getSharedPreferences(SETTING_FILE, Context.MODE_PRIVATE);
		}
		String value = perference.getString(key, "");
		if(StringUtils.isEmpty(value)){
			return def;
		}
		try {
			value = new String(Des3.des3DecodeCBC(
					"YWJj1ZSyw2hpamtsbW5vcHFyc3R1dnd4".getBytes(),
					"25439768".getBytes(), Base64.decode(value,Base64.DEFAULT)), "UTF-8");
			return Integer.parseInt(value);
		} catch (UnsupportedEncodingException e) {
			perference.edit().remove(key).commit();
			e.printStackTrace();
		} catch (Exception e) {
			perference.edit().remove(key).commit();
			e.printStackTrace();
		}
		return def;
	}

	public void insertPreference(String key, int value) {
		SharedPreferences perference = null;
		int sdk = VERSION.SDK_INT;
		if(sdk >VERSION_CODES.GINGERBREAD_MR1){
			perference = context.getSharedPreferences(SETTING_FILE,
					Context.MODE_MULTI_PROCESS);
		}else{
			perference = context.getSharedPreferences(SETTING_FILE, Context.MODE_PRIVATE);
		}
		try {
			perference.edit().putString(key, new String(Base64.encode(Des3.des3EncodeCBC(
					"YWJj1ZSyw2hpamtsbW5vcHFyc3R1dnd4"
							.getBytes(), "25439768"
							.getBytes(),
					String.valueOf(value)
							.getBytes()), Base64.DEFAULT),
					"UTF-8")).commit();
		} catch (UnsupportedEncodingException e) {
			perference.edit().remove(key).commit();
			e.printStackTrace();
		} catch (Exception e) {
			perference.edit().remove(key).commit();
			e.printStackTrace();
		}
	}

	public boolean remove(final String key) {
		SharedPreferences perference = null;
		int sdk = VERSION.SDK_INT;
		if(sdk >VERSION_CODES.GINGERBREAD_MR1){
			perference = context.getSharedPreferences(SETTING_FILE,
					Context.MODE_MULTI_PROCESS);
		}else{
			perference = context.getSharedPreferences(SETTING_FILE, Context.MODE_PRIVATE);
		}
		return perference.edit().remove(key).commit();
	}

	public boolean contains(String key) {
		SharedPreferences perference = null;
		int sdk = VERSION.SDK_INT;
		if(sdk >VERSION_CODES.GINGERBREAD_MR1){
			perference = context.getSharedPreferences(SETTING_FILE,
					Context.MODE_MULTI_PROCESS);
		}else{
			perference = context.getSharedPreferences(SETTING_FILE, Context.MODE_PRIVATE);
		}
		return perference.contains(key);
	}
}
