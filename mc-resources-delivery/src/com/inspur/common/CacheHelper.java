package com.inspur.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InvalidClassException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import android.content.Context;

public class CacheHelper {
	private static CacheHelper helper;
	private static Context context;


	public static CacheHelper getInstance(Context c){
		if(helper == null){
			synchronized (CacheHelper.class){
				if (helper == null){
					context = c.getApplicationContext();
					helper = new CacheHelper(c);
				}
			}
		}
		return helper;
	}



	private CacheHelper(Context context){
		this.context = context;
	}

	/**
	 * 保存对象
	 * @param ser
	 * @param file
	 * @throws IOException
	 */
	public boolean saveObject(Serializable ser, String file) {
		FileOutputStream fos = null;
		ObjectOutputStream oos = null;
		try{
			fos = context.openFileOutput(file, context.MODE_PRIVATE);
			oos = new ObjectOutputStream(fos);
			oos.writeObject(ser);
			oos.flush();
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}finally{
			try {
				oos.close();
			} catch (Exception e) {}
			try {
				fos.close();
			} catch (Exception e) {}
		}
	}

	/**
	 * 读取对象
	 * @param file
	 * @return
	 * @throws IOException
	 */
	public Serializable readObject(String file){
		if(!isExistDataCache(file))
			return null;
		FileInputStream fis = null;
		ObjectInputStream ois = null;
		try{
			fis = context.openFileInput(file);
			ois = new ObjectInputStream(fis);
			return (Serializable)ois.readObject();
		}catch(FileNotFoundException e){
		}catch(Exception e){
			e.printStackTrace();
			//反序列化失败 - 删除缓存文件
			if(e instanceof InvalidClassException){
				File data = context.getFileStreamPath(file);
				data.delete();
			}
		}finally{
			try {
				ois.close();
			} catch (Exception e) {}
			try {
				fis.close();
			} catch (Exception e) {}
		}
		return null;
	}

	/**
	 * 删除缓存文件
	 * @param file 需要删除的缓存文件名
	 * @return
	 * @throws IOException
	 */
	public boolean deleteObject(String file){
		if(!isExistDataCache(file))
			return true;
		File data = context.getFileStreamPath(file);
		return data.delete();
	}
	/**
	 * 判断缓存是否存在
	 * @param cachefile
	 * @return
	 */
	private boolean isExistDataCache(String cachefile)
	{
		boolean exist = false;
		File data = context.getFileStreamPath(cachefile);
		if(data.exists())
			exist = true;
		return exist;
	}
	/**
	 * 判断缓存数据是否可读
	 * @param cachefile
	 * @return
	 */
	private boolean isReadDataCache(String cachefile)
	{
		return readObject(cachefile) != null;
	}
}