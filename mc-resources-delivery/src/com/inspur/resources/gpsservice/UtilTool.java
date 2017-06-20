package com.inspur.resources.gpsservice;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.util.Calendar;
import java.util.Locale;

import org.json.JSONObject;

import com.inspur.StringUtils;
import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.Request;
import com.yolanda.nohttp.Response;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.widget.Toast;

public class UtilTool {
	public final static double a = 6378245.0;  
    public final static double ee = 0.00669342162296594323;  
    
	public static boolean isGpsEnabled(LocationManager locationManager) {
		boolean isOpenGPS = locationManager.isProviderEnabled(android.location.LocationManager.GPS_PROVIDER);
		boolean isOpenNetwork = locationManager.isProviderEnabled(android.location.LocationManager.NETWORK_PROVIDER);
		if (isOpenGPS || isOpenNetwork) {
			return true;
		}
		return false;
	}

	/**
	 * 
	 * 原理向http://api.zdoz.net/bd2wgs.aspx?lat=34.121&lng=115.21212发送http的get请求，
	 * 将百度坐标转换为gps坐标
	 * 
	 * @param cellIds
	 * @return
	 * @throws Exception
	 */
	/*public static Location callGear(Context ctx, double lat, double lng) throws Exception {
		String result = "";
		JSONObject data = null;
System.out.println("callGear");
		try {
			result = UtilTool.getResponseResult(ctx, "http://api.zdoz.net/bd2wgs.aspx?lat=" + lat + "&lng=" + lng);
			System.out.println(result);
			if (StringUtils.isEmpty(result))
				return null;
			data = new JSONObject(result);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

		Location loc = new Location(LocationManager.NETWORK_PROVIDER);
		loc.setLatitude(data.getDouble("Lat"));
		loc.setLongitude(data.getDouble("Lng"));
		return loc;
	}*/
	
	public static Location loca = new Location(LocationManager.NETWORK_PROVIDER);
	public static Location loca2 = new Location(LocationManager.NETWORK_PROVIDER);
	
	public static Location callGear(Context ctx, double lat, double lng) throws Exception {
		Location location=BAIDU_to_WGS84(lng,lat);
		return location;
	}
	
	
	 // 判断坐标是否在中国  
    public static boolean outOfChina(Location bdLocation) {  
        double lat = bdLocation.getLatitude();  
        double lon = bdLocation.getLongitude();  
        if (lon < 72.004 || lon > 137.8347)  
            return true;  
        if (lat < 0.8293 || lat > 55.8271)  
            return true;  
        if ((119.962 < lon && lon < 121.750) && (21.586 < lat && lat < 25.463))  
            return true;  
  
        return false;  
    }  
  
    public final static double x_pi = 3.14159265358979324 * 3000.0 / 180.0;  
  
    public static Location BAIDU_to_WGS84(double lon,double lat) {  
       /* if (outOfChina(bdLocation)) {  
            return bdLocation;  
        }  */
        double x = lon - 0.0065;  
        double y = lat - 0.006;  
        double z = Math.sqrt(x * x + y * y) - 0.00002 * Math.sin(y * x_pi);  
        double theta = Math.atan2(y, x) - 0.000003 * Math.cos(x * x_pi);  
//        bdLocation.setLongitude(z * Math.cos(theta));  
//        bdLocation.setLatitude(z * Math.sin(theta));  
        return GCJ02_to_WGS84(z * Math.cos(theta),z * Math.sin(theta));  
    }  
  
    public static Location GCJ02_to_WGS84(double lon,double lat) {  
       /* if (outOfChina(bdLocation)) {  
            return bdLocation;  
        }  */
//        Location tmpLocation = new Location();  
//        tmpLocation.setLatitude(bdLocation.getLatitude());  
//        tmpLocation.setLongitude(bdLocation.getLongitude());  
        
        
        Location tmpLatLng = WGS84_to_GCJ02(lon,lat);  
        double tmpLat = 2 * lat - tmpLatLng.getLatitude();  
        double tmpLng = 2 * lon  
                - tmpLatLng.getLongitude();  
        for (int i = 0; i < 0; ++i) {  
            tmpLatLng = WGS84_to_GCJ02(lon,lat);  
            tmpLat = 2 * tmpLat - tmpLatLng.getLatitude();  
            tmpLng = 2 * tmpLng - tmpLatLng.getLongitude();  
        }  
        loca2.setLatitude(tmpLat);  
        loca2.setLongitude(tmpLng);  
        return loca2;  
    }  
  
    public static Location WGS84_to_GCJ02(double lon,double lat) {  
        /*if (outOfChina(bdLocation)) {  
            return bdLocation;  
        } */ 
        double dLat = transformLat(lon - 105.0,  
                lat - 35.0);  
        double dLon = transformLon(lon - 105.0,  
                lat - 35.0);  
        double radLat = lat / 180.0 * Math.PI;  
        double magic = Math.sin(radLat);  
        magic = 1 - ee * magic * magic;  
        double sqrtMagic = Math.sqrt(magic);  
        dLat = (dLat * 180.0)  
                / ((a * (1 - ee)) / (magic * sqrtMagic) * Math.PI);  
        dLon = (dLon * 180.0) / (a / sqrtMagic * Math.cos(radLat) * Math.PI);  
      
    
        loca.setLatitude(lat + dLat);  
        loca.setLongitude(lon + dLon);  
        
        return loca;  
    }  
  
    public static double transformLat(double x, double y) {  
        double ret = -100.0 + 2.0 * x + 3.0 * y + 0.2 * y * y + 0.1 * x * y  
                + 0.2 * Math.sqrt(Math.abs(x));  
        ret += (20.0 * Math.sin(6.0 * x * Math.PI) + 20.0 * Math.sin(2.0 * x  
                * Math.PI)) * 2.0 / 3.0;  
        ret += (20.0 * Math.sin(y * Math.PI) + 40.0 * Math.sin(y / 3.0  
                * Math.PI)) * 2.0 / 3.0;  
        ret += (160.0 * Math.sin(y / 12.0 * Math.PI) + 320 * Math.sin(y  
                * Math.PI / 30.0)) * 2.0 / 3.0;  
        return ret;  
    }  
  
    public static double transformLon(double x, double y) {  
        double ret = 300.0 + x + 2.0 * y + 0.1 * x * x + 0.1 * x * y + 0.1  
                * Math.sqrt(Math.abs(x));  
        ret += (20.0 * Math.sin(6.0 * x * Math.PI) + 20.0 * Math.sin(2.0 * x  
                * Math.PI)) * 2.0 / 3.0;  
        ret += (20.0 * Math.sin(x * Math.PI) + 40.0 * Math.sin(x / 3.0  
                * Math.PI)) * 2.0 / 3.0;  
        ret += (150.0 * Math.sin(x / 12.0 * Math.PI) + 300.0 * Math.sin(x  
                / 30.0 * Math.PI)) * 2.0 / 3.0;  
        return ret;  
    }  
    
    
    public static double getDistanceFromXtoY(double lat_a, double lng_a,
    		   double lat_b, double lng_b)
		 {
		  double pk = (double) (180 / 3.14169);

		  double a1 = lat_a / pk;
		  double a2 = lng_a / pk;
		  double b1 = lat_b / pk;
		  double b2 = lng_b / pk;

		  double t1 = Math.cos(a1) * Math.cos(a2) * Math.cos(b1) * Math.cos(b2);
		  double t2 = Math.cos(a1) * Math.sin(a2) * Math.cos(b1) * Math.sin(b2);
		  double t3 = Math.sin(a1) * Math.sin(b1);
		  double tt = Math.acos(t1 + t2 + t3);

		  return 6366000 * tt;
		 }



	
	
	
	
	
	
	
	
	
	

	public static String getResponseResult(Context ctx, String path)
			throws UnsupportedEncodingException, MalformedURLException, IOException, ProtocolException, Exception {
		Request<String> request = NoHttp.createStringRequest(path);
		Response<String> response = NoHttp.startRequestSync(request);
		if (response.isSucceed()) {
			// 请求成功
			return response.get();
		} else {
			// 请求失败
			return "";
		}

	}

	/**
	 * 获取UTC时间
	 * 
	 * UTC + 时区差 ＝ 本地时间(北京为东八区)
	 * 
	 * @return
	 */
	public static long getUTCTime() {
		// 取得本地时间
		Calendar cal = Calendar.getInstance(Locale.CHINA);
		// 取得时间偏移量
		int zoneOffset = cal.get(java.util.Calendar.ZONE_OFFSET);
		// 取得夏令时差
		int dstOffset = cal.get(java.util.Calendar.DST_OFFSET);
		// 从本地时间里扣除这些差量，即可以取得UTC时间
		cal.add(java.util.Calendar.MILLISECOND, -(zoneOffset + dstOffset));
		return cal.getTimeInMillis();
	}

	/**
	 * 提示
	 * 
	 * @param ctx
	 * @param msg
	 */
	public static void alert(Context ctx, String msg) {
		Toast.makeText(ctx, msg, Toast.LENGTH_LONG).show();
	}

}