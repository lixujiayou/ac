package com.inspur.resources.view.delivery.transroute;

import java.io.File;

import com.baidu.location.BDLocation;

import android.location.Location;
import android.os.Environment;

public class ZSLConst
{
	// 是否使用假数据
	public static final boolean useFalseData = false;
	//获取位置是否使用GPS定位,如果不使用则将百度定位坐标通过坐标转换接口进行转换，如果使用则优先使用GPS，gps获取不到时才使用转换接口
	public static final boolean useGPS = false;
	// 当查询周边资源点获取成功时
	public static final String tag_onResourceInfoBeanList_get_ok = "tag_onResourceInfoBeanList_get_ok";
	// 当查询周边资源点线获取成功时
	public static final String tag_onResourceLineBeanList_get_ok = "tag_onResourceLineBeanList_get_ok";

	public static final String photodir = Environment
			.getExternalStorageDirectory().getPath() + File.separator+"yc"+File.separator+"photos"+File.separator;

	public final static String PHOTO_TYPE_START = "START";
	public final static String PHOTO_TYPE_END = "END";
	public final static String PHOTO_TYPE_WAY = "WAY";
	public final static String PHOTO_TYPE_ERROR = "ERROR";
	public final static String PHOTO_TYPE_GD = "GDGZ";
	public final static String PHOTO_TYPE_ZZ = "ZZSJ";
	public final static String PHOTO_TYPE_GJ = "GJJG";

	public final static String PREFIX_OF_OFFLINE_ROUTE = "ROUTE_";

	public static BDLocation curLocation;
	public static Location curGpsLocation;

}
