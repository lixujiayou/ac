package com.inspur.resources.utils.sq;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.inspur.resources.utils.ApplicationValue;
import com.inspur.resources.utils.PictureUtil;
import com.inspur.resources.view.delivery.guang.GuangDeliveryActivity;
import com.inspur.resources.view.delivery.guang.bean.PhotoBean;
import com.inspur.resources.view.delivery.offline.PhotoOfficeActivity;
import com.inspur.resources.view.delivery.transroute.bean.ErrorInfoBean;
import com.inspur.resources.view.delivery.transroute.bean.GuangBean;
import com.inspur.resources.view.delivery.transroute.bean.PhotoInfoBean;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

public class MySQLHelper {

	private static SQLiteDatabase mDateBase;
	private static ErroSQLiteHelper mErroHelper; //隐患库
	private static PhotoSQLiteHelper mPhotoHelper; //图片库

	/**
	 * 隐患数据离线保存
	 * @param mContext
	 * @param routeId
	 * @param jsonContent
	 * @param mType
	 */
	public static boolean saveErroForZYYH(Context mContext,int routeId,String jsonContent,String mType){
		mDateBase = null;
		try{
			if(mType.equals(ApplicationValue.single_list[0])){
				mErroHelper = new ErroSQLiteHelper(mContext, ApplicationValue.ERRO_ZYYH, null, 1);
				mDateBase = mErroHelper.getWritableDatabase();
				ContentValues mContent = new ContentValues();
				mContent.put(ApplicationValue.ERRO_ROUTEID,routeId);
				mContent.put(ApplicationValue.ERRO_CONTENT,jsonContent);
				//mContent.put(ApplicationValue.ERRO_CONTENT,"--");
				mDateBase.insert(ApplicationValue.ERRO_ZYYH, null, mContent);

			}else if(mType.equals(ApplicationValue.single_list[1])){
				mErroHelper = new ErroSQLiteHelper(mContext, ApplicationValue.ERRO_ZZSJ, null, 1);
				mDateBase = mErroHelper.getWritableDatabase();
				ContentValues mContent = new ContentValues();
				mContent.put(ApplicationValue.ERRO_ROUTEID,routeId);
				mContent.put(ApplicationValue.ERRO_CONTENT,jsonContent);
				mDateBase.insert(ApplicationValue.ERRO_ZZSJ, null, mContent);
			}else{
				mErroHelper = new ErroSQLiteHelper(mContext, ApplicationValue.ERRO_GDGZ, null, 1);
				mDateBase = mErroHelper.getWritableDatabase();
				ContentValues mContent = new ContentValues();
				mContent.put(ApplicationValue.ERRO_ROUTEID,routeId);
				mContent.put(ApplicationValue.ERRO_CONTENT,jsonContent);
				mDateBase.insert(ApplicationValue.ERRO_GDGZ, null, mContent);
			}

			return true;
		}catch(Exception e){
			Log.d("lixu", "保存隐患异常"+e.toString());
			return false;
		}
	}

	/**
	 * 得到隐患的bean.toJson
	 * @param mContext
	 * @param mRouteId
	 * @param mType
	 * @return
	 */
	public static List<String> getErros(Context mContext,int mRouteId,String mType){

		Cursor cursor;
		try{
			List<String> erroInfoList = new ArrayList<String>();
			String mSQL = "";
			if(mType.equals(ApplicationValue.single_list[0])){
				mErroHelper = new ErroSQLiteHelper(mContext, ApplicationValue.ERRO_ZYYH, null, 1);
				mDateBase = mErroHelper.getWritableDatabase();
				//mSQL = "select "+ApplicationValue.ERRO_CONTENT+" from "+ApplicationValue.ERRO_ZYYH+" where "+ApplicationValue.ERRO_ROUTEID+"="+mRouteId;
				cursor = mDateBase.query(ApplicationValue.ERRO_ZYYH, new String[]{ApplicationValue.ERRO_CONTENT}, ApplicationValue.ERRO_ROUTEID+"=?", new String[]{mRouteId+""}, null, null, null);
			}else if(mType.equals(ApplicationValue.single_list[1])){
				mErroHelper = new ErroSQLiteHelper(mContext, ApplicationValue.ERRO_ZZSJ, null, 1);
				mDateBase = mErroHelper.getWritableDatabase();
				//mSQL = "select "+ApplicationValue.ERRO_CONTENT+" from "+ApplicationValue.ERRO_ZZSJ+" where "+ApplicationValue.ERRO_ROUTEID+"="+mRouteId;
				cursor = mDateBase.query(ApplicationValue.ERRO_ZZSJ, new String[]{ApplicationValue.ERRO_CONTENT}, ApplicationValue.ERRO_ROUTEID+"=?", new String[]{mRouteId+""}, null, null, null);
			}else{
				mErroHelper = new ErroSQLiteHelper(mContext, ApplicationValue.ERRO_GDGZ, null, 1);
				mDateBase = mErroHelper.getWritableDatabase();
				//mSQL = "select "+ApplicationValue.ERRO_CONTENT+" from "+ApplicationValue.ERRO_GDGZ+" where "+ApplicationValue.ERRO_ROUTEID+"="+mRouteId;
				cursor = mDateBase.query(ApplicationValue.ERRO_GDGZ, new String[]{ApplicationValue.ERRO_CONTENT}, ApplicationValue.ERRO_ROUTEID+"=?", new String[]{mRouteId+""}, null, null, null);
			}
			//	cursor = mDateBase.rawQuery(mSQL, null);
			while (cursor.moveToNext()) {
				erroInfoList.add(cursor.getString(cursor.getColumnIndex(ApplicationValue.ERRO_CONTENT)));
			}
			if(erroInfoList == null || erroInfoList.size() == 0){
				return null;
			}
			return erroInfoList;
		}catch(Exception e){
			Log.d("lixu", "查询隐患异常"+e.toString());
			return null;
		}
	}




	/**
	 * 提交离线图片
	 * @param mContext
	 * @param mPhotoBean
	 */
	public static boolean savePhoto(Context mContext,PhotoInfoBean mPhotoBean){
		try{
			mPhotoHelper = new PhotoSQLiteHelper(mContext, ApplicationValue.PHOTOTABLENAME, null, 1);
			mDateBase = mPhotoHelper.getWritableDatabase();
			ContentValues mContent = new ContentValues();
			mContent.put(ApplicationValue.PHOTOTABLE_ROUTEID,mPhotoBean.getRouteID());
			mContent.put(ApplicationValue.PHOTOTABLE_USERID,mPhotoBean.getUserId());
			mContent.put(ApplicationValue.PHOTOTABLE_PHOTOTYPE,mPhotoBean.getPhotoType());
			mContent.put(ApplicationValue.PHOTOTABLE_RESOURCETYPE,mPhotoBean.getResourceType());
			mContent.put(ApplicationValue.PHOTOTABLE_LAT,mPhotoBean.getLatitude());
			mContent.put(ApplicationValue.PHOTOTABLE_LON,mPhotoBean.getLongitude());
			mContent.put(ApplicationValue.PHOTOTABLE_POHOTO,mPhotoBean.getPhotoString());

			mContent.put(ApplicationValue.PHOTOTABLE_RELATEDID,mPhotoBean.getRelatedID());
			if(mPhotoBean.getRelatedID()!=null || mPhotoBean.getRelatedID() != 0){

				mContent.put(ApplicationValue.PHOTOTABLE_RELATEDID, mPhotoBean.getRelatedID());
				mDateBase.insert(ApplicationValue.PHOTOTABLENAME, null, mContent);

			}
		}catch(Exception e){
			Log.d("lixu", "提交利息按图片异常"+e.toString());
			Toast.makeText(mContext, "数据异常，请退出重试", Toast.LENGTH_SHORT).show();
			return false;
		}
		return true;
	}

	/**
	 * 得到相关离线图片
	 * @param mContext
	 * @param routeId
	 * @return
	 */
	public static List<PhotoInfoBean> getPhotos(Context mContext, int routeId){
		try{
			List<PhotoInfoBean> photoList = new ArrayList<PhotoInfoBean>();
			mPhotoHelper = new PhotoSQLiteHelper(mContext, ApplicationValue.PHOTOTABLENAME, null, 1);
			mDateBase = mPhotoHelper.getWritableDatabase();
//		String mSQL = "select "+ApplicationValue.PHOTOTABLE_ROUTEID+","+ApplicationValue.PHOTOTABLE_USERID+","+ApplicationValue.PHOTOTABLE_PHOTOTYPE+","+ApplicationValue.PHOTOTABLE_LAT+","+ApplicationValue.PHOTOTABLE_LON+","+ApplicationValue.PHOTOTABLE_POHOTO+","+ApplicationValue.PHOTOTABLE_RELATEDID+","+ApplicationValue.PHOTOTABLE_RESOURCETYPE+" from "+ApplicationValue.PHOTOTABLENAME+" where "+ApplicationValue.PHOTOTABLE_ROUTEID+"="+routeId;
			//Cursor cursor = mDateBase.rawQuery(mSQL, null);
			Cursor cursor = mDateBase.query(ApplicationValue.PHOTOTABLENAME, new String[]{ApplicationValue.PHOTOTABLE_ROUTEID,ApplicationValue.PHOTOTABLE_USERID,ApplicationValue.PHOTOTABLE_PHOTOTYPE,ApplicationValue.PHOTOTABLE_LAT,ApplicationValue.PHOTOTABLE_LON,ApplicationValue.PHOTOTABLE_POHOTO,ApplicationValue.PHOTOTABLE_RELATEDID,ApplicationValue.PHOTOTABLE_RESOURCETYPE}, ApplicationValue.PHOTOTABLE_ROUTEID+"=?",new String[]{routeId+""}, null, null, null);
			Log.d("lixu","-========"+cursor.getCount());
			while (cursor.moveToNext()) {
				PhotoInfoBean photoInfoBean = new PhotoInfoBean();
				photoInfoBean.setRouteID(cursor.getInt(cursor.getColumnIndex(ApplicationValue.PHOTOTABLE_ROUTEID)));
				photoInfoBean.setUserId(cursor.getString(cursor.getColumnIndex(ApplicationValue.PHOTOTABLE_USERID)));
				photoInfoBean.setPhotoType(cursor.getString(cursor.getColumnIndex(ApplicationValue.PHOTOTABLE_PHOTOTYPE)));
				photoInfoBean.setResourceType(cursor.getString(cursor.getColumnIndex(ApplicationValue.PHOTOTABLE_RESOURCETYPE)));
				photoInfoBean.setLatitude(cursor.getDouble(cursor.getColumnIndex(ApplicationValue.PHOTOTABLE_LAT)));
				photoInfoBean.setLongitude(cursor.getDouble(cursor.getColumnIndex(ApplicationValue.PHOTOTABLE_LON)));
				photoInfoBean.setRelatedID(cursor.getInt(cursor.getColumnIndex(ApplicationValue.PHOTOTABLE_RELATEDID)));
				photoInfoBean.setPhotoString(cursor.getString(cursor.getColumnIndex(ApplicationValue.PHOTOTABLE_POHOTO)));

				photoList.add(photoInfoBean);

				photoInfoBean = null;
			}
			if(photoList == null || photoList.size() == 0){
				return null;
			}



			return photoList;

		}catch(Exception e){
			Log.d("lixu", "查询离线按图片异常"+e.toString());
			return null;
		}
	}

}
