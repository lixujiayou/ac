package com.inspur;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.lang.Thread.UncaughtExceptionHandler;
import org.xmlpull.v1.XmlSerializer;

import android.content.Context;
import android.os.Environment;
import android.util.Log;
import android.util.Xml;

public class CatchExceptionLog {
	
	private final static String ERROR_FILENAME = "e.log";

	/****
	 * 创建文件在应用的internal目录下
	 * 
	 * @param filename
	 * @param context
	 * @return
	 */
	public File makeFile(String filename, Context context) {
		return new File(context.getFilesDir(), filename);
	}

	/****
	 * 写文件到internal目录下
	 * 
	 * @param filename
	 * @param content
	 * @param context
	 */
	public void writeFile(String filename, String content, Context context) {
		FileOutputStream outputStream;
		try {
			outputStream = context.openFileOutput(filename, Context.MODE_PRIVATE);
			outputStream.write(content.getBytes());
			outputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/****
	 * 缓存文件
	 * 
	 * @param filename
	 * @param context
	 * @return
	 */
	public File getTempFile(String filename, Context context) {
		File file = null;
		try {
			file = File.createTempFile(filename, null, context.getCacheDir());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return file;
	}

	/****
	 * 判断存储卡是否可读、写
	 * 
	 * @return
	 */
	public static boolean isExternalStorageWritable() {
		String state = Environment.getExternalStorageState();
		if (Environment.MEDIA_MOUNTED.equals(state)) {
			return true;
		}
		return false;
	}

	/****
	 * 判断存储卡是否可读
	 * 
	 * @return
	 */
	public boolean isExternalStorageReadable() {
		String state = Environment.getExternalStorageState();
		if (Environment.MEDIA_MOUNTED.equals(state) || Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
			return true;
		}
		return false;
	}

	/***
	 * 创建私有目录
	 * 
	 * @param dirName
	 * @param context
	 * @return
	 */
	public static File makePrivateDir(String dirName, Context context) {
		File file = new File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), dirName);
		// 判断目录是否存在
		if (!file.exists()) {
			file.mkdirs();
		}
		return file;
	}

	/**
	 * 创建公共目录
	 * 
	 * @param dirName
	 * @return
	 */
	public static File makePublicDir(String dirName) {
		File file = new File(Environment.getExternalStoragePublicDirectory(Environment.MEDIA_NOFS), dirName);
		// 判断目录是否存在
		if (!file.exists()) {
			file.mkdirs();
		}
		return file;
	}

	/****
	 * 创建文件到SDCARD
	 * 
	 * @param fileName
	 * @return
	 */
	public static File makeFile(String fileName) {
		String pathString = Const.LOG_PATH;
		File dirFile = new File(pathString);
		if (!dirFile.exists()) {
			dirFile.mkdirs();
		}
		File logFile = new File(pathString + fileName);
		if (!logFile.exists()) {
			try {
				logFile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return logFile;
	}

	/****
	 * 捕获异常信息
	 */
	public static void catchException(final Context context) {
		Thread.setDefaultUncaughtExceptionHandler(new UncaughtExceptionHandler() {
			@Override
			public void uncaughtException(Thread thread, Throwable ex) {
				Log.e("error", ex.getLocalizedMessage());
				try {
					if (isExternalStorageWritable()) {
						// PrintWriter print=new PrintWriter(new
						// FileWriter(makeFile("e.log"),true));

						FileOutputStream outstream = new FileOutputStream(makeFile(ERROR_FILENAME),
								true);
						OutputStreamWriter outStreamWriter = new OutputStreamWriter(outstream, "UTF-8");
						BufferedWriter writer = new BufferedWriter(outStreamWriter);

						XmlSerializer serializer = Xml.newSerializer();
						serializer.setOutput(writer);
						serializer.startDocument("UTF-8", true);

						// 应用
						serializer.startTag("", "app");
						serializer.text(
								context.getPackageManager().getPackageInfo(context.getPackageName(), 0).applicationInfo
										.loadLabel(context.getPackageManager()).toString());
						serializer.endTag("", "app");
						// 版本
						serializer.startTag("", "appversion");
						serializer.text(
								context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName);
						serializer.endTag("", "appversion");
						// 手机型号
						serializer.startTag("", "phonemodel");
						serializer.text(android.os.Build.MODEL);
						serializer.endTag("", "phonemodel");
						// 安卓版本号
						serializer.startTag("", "version");
						serializer.text(android.os.Build.VERSION.RELEASE);
						serializer.endTag("", "version");
						// 异常信息
						serializer.startTag("", "exception");
						serializer.text(getErrorStack(ex,0));
						serializer.endTag("", "exception");

						serializer.endDocument();

						writer.write("\n");

						writer.flush();
						writer.close();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * 获取Exception的堆栈新息。用于显示出错来源时使用。
	 * 
	 * @param e
	 *            Exception对象
	 * @param length
	 *            需要的信息长度，如果 <=0,表示全部信息
	 * @return String 返回该Exception的堆栈新息
	 */
	public String getErrorStack(Exception e, int length) {
		String error = null;
		if (e != null) {
			try {
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				PrintStream ps = new PrintStream(baos);
				e.printStackTrace(ps);
				error = baos.toString();
				if (length > 0) {
					if (length > error.length()) {
						length = error.length();
					}
					error = error.substring(0, length);
				}
				baos.close();
				ps.close();
			} catch (Exception e1) {
				error = e.toString();
			}
		}

		return error;
	}
	
	public static String getErrorStack(Throwable e, int length) {
		String error = null;
		if (e != null) {
			try {
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				PrintStream ps = new PrintStream(baos);
				e.printStackTrace(ps);
				error = baos.toString();
				if (length > 0) {
					if (length > error.length()) {
						length = error.length();
					}
					error = error.substring(0, length);
				}
				baos.close();
				ps.close();
			} catch (Exception e1) {
				error = e.toString();
			}
		}

		return error;
	}

}
