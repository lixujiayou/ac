package com.inspur.resources.view.delivery.offline;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;
import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;
import com.baidu.location.Poi;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BaiduMap.OnMapClickListener;
import com.baidu.mapapi.map.BaiduMap.OnMarkerClickListener;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.PolylineOptions;
import com.baidu.mapapi.map.UiSettings;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.CoordinateConverter;
import com.baidu.mapapi.utils.CoordinateConverter.CoordType;
import com.baidu.mapapi.utils.DistanceUtil;
import com.inspur.StringUtils;
import com.inspur.common.CacheHelper;
import com.inspur.easyresources.R;
import com.inspur.resources.base.BaseActivity;
import com.inspur.resources.gpsservice.Gps;
import com.inspur.resources.gpsservice.UtilTool;
import com.inspur.resources.http.httpconnect;
import com.inspur.resources.utils.ApplicationValue;
import com.inspur.resources.utils.MyLog;
import com.inspur.resources.view.delivery.guang.MapTemp;
import com.inspur.resources.view.delivery.guang.PhotoShopeActivityGuang;
import com.inspur.resources.view.delivery.transroute.AroundResourceLineSearchTask;
import com.inspur.resources.view.delivery.transroute.ZSLConst;
import com.inspur.resources.view.delivery.transroute.bean.ErrorInfoBean;
import com.inspur.resources.view.delivery.transroute.bean.LocusPoint;
import com.inspur.resources.view.delivery.transroute.bean.PhotoInfoBean;
import com.inspur.resources.view.delivery.transroute.bean.PointlikeResourceInfoBean;
import com.inspur.resources.view.delivery.transroute.bean.ResourceInfoBean;
import com.inspur.resources.view.delivery.transroute.bean.ResourceLineBean;
import com.inspur.resources.view.delivery.transroute.bean.RouteInfoBean;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.location.Location;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import cn.trinea.android.common.util.ScreenUtils;

/**
 * @author Dell- 传输线路
 */
public class OfficeJingSelectActivity extends BaseActivity {
	/**
	 * 数据库相关
	 */
	private SQLiteDatabase db_line;  //线

	String pathString_line = MainOfflineActivity.FILE_PATH;
	private File f_line = new File(MainOfflineActivity.FILE_PATH+MainOfflineActivity.FILE_NAME_line); //数据库文件

	private ProgressDialog mProgress;
	private MapView mapview;
	private BaiduMap mBaiduMap;
	private View viewGPS;
	// 与定位有关======================================================
	private LocationClient mLocationClient = null;
	private BDLocationListener myListener = new MyLocationListener();
	private BDLocation lastLocation;// 保存最后一次定位到的位置信息
	// 存储当前展示的一定范围内的资源点
	private ArrayList<ResourceInfoBean> resourceInfoBeanList = new ArrayList<ResourceInfoBean>();
	// marker和点状资源映射表
	private HashMap<Marker, ResourceInfoBean> markerMap = new HashMap<Marker, ResourceInfoBean>();

	// 当前路由实体类
	RouteInfoBean mRouteInfoBean;

	//存储Gps数据,主要是为了减少对坐标转换接口的请求次数,以百度经纬度形成key，如果是同样的经纬度就不再请求转换接口了
	private Map<String,Location> gpaCacheMap = new HashMap<String,Location>();

	private BitmapDescriptor mCurrentMarker = BitmapDescriptorFactory
			.fromResource(R.mipmap.location_marker);

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.zsl_activity_transmission_line2);
		setTitle("选择资源");

		EventBus.getDefault().register(this);
		// 地图初始化
		mapview = (MapView) findViewById(R.id.zsl_mapview1);

		mBaiduMap = mapview.getMap();
		// 开启定位图层
		mBaiduMap.setMyLocationEnabled(true);

		try{
			db_line = SQLiteDatabase.openOrCreateDatabase(f_line, null);
			//getResourceLineBeanList();
		}catch(Exception e){
			Log.d("lixu","数据库报错了=="+e.toString());
		}

		// 定位初始化
		mLocationClient = new LocationClient(this);
		mLocationClient.registerLocationListener(myListener);
		LocationClientOption option = new LocationClientOption();

		option.setLocationMode(LocationMode.Hight_Accuracy);// 可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
		option.setCoorType("bd09ll");// 可选，默认gcj02，设置返回的定位结果坐标系

		option.setScanSpan(2000);// 可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
		option.setOpenGps(true);// 可选，默认false,设置是否使用gps
		option.setLocationNotify(true);

		mLocationClient.setLocOption(option);
		mLocationClient.start();




		viewGPS = findViewById(R.id.zsl_gps);
		viewGPS.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				if(ZSLConst.curGpsLocation==null){
					Toast.makeText(getApplicationContext(), "定位中，请稍后...", Toast.LENGTH_SHORT).show();
					return;

				}
				getResourceLineBeanList(ZSLConst.curGpsLocation.getLatitude(),ZSLConst.curGpsLocation.getLongitude());

			}
		});


		mBaiduMap.setOnMarkerClickListener(new OnMarkerClickListener() {

			@Override
			public boolean onMarkerClick(Marker arg0) {
				ResourceInfoBean clickBean = markerMap.get(arg0);
				Bundle bundle = new Bundle();
				bundle.putSerializable("gd", clickBean);
				Intent gIntent = new Intent();
				gIntent.putExtras(bundle);
				setResult(12,gIntent);
				finish();
				return true;
			}
		});
	}


	private boolean isFirstLoc = true;
	public class MyLocationListener implements BDLocationListener {
		@Override
		public void onReceiveLocation(BDLocation location) {
			if (location == null || mapview == null) {
				return;
			}

			double lat = location.getLatitude();
			double lng = location.getLongitude();
			MapStatusUpdate update = MapStatusUpdateFactory.newLatLng(new LatLng(lat, lng));
			mBaiduMap.animateMapStatus(update);
			//	ZSLConst.curLocation = lastLocation;

			MyLocationData locData = new MyLocationData.Builder()
					.accuracy(location.getRadius())
					//此处设置开发者获取到的方向信息，顺时针0-360
					.direction(100).latitude(location.getLatitude())
					.longitude(location.getLongitude()).build()
					;

			mBaiduMap.setMyLocationData(locData);



			MyLocationConfiguration config = new
					MyLocationConfiguration(com.baidu.mapapi.map.MyLocationConfiguration.LocationMode.COMPASS,
					true, mCurrentMarker);
			mBaiduMap.setMyLocationConfigeration(config);

			if (isFirstLoc) {
				isFirstLoc = false;
				LatLng ll = new LatLng(location.getLatitude(),
						location.getLongitude());
				MapStatus.Builder builder = new MapStatus.Builder();
				builder.target(ll).zoom(18.0f);
				mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
			}


		}
	}



	@Override
	protected void onResume() {
		mapview.onResume();
		super.onResume();
	}

	@Override
	protected void onPause() {
		mapview.onPause();
		super.onPause();
	}

	@Override
	protected void onDestroy() {
		mLocationClient.stop();
		mapview.onDestroy();
		//	mBaiduMap = null;
		EventBus.getDefault().unregister(this);
		//	ZSLConst.curGpsLocation = null;
		super.onDestroy();
	}

	private void displayAroundResourcesOnMap(boolean append,List<ResourceInfoBean> mResourceInfoBeanList) {
		if (append == false) // 覆盖展示
		{
			// 隐藏气泡，置选中的marker为空
			mBaiduMap.hideInfoWindow();

			// 移除掉旧marker
			Iterator<Entry<Marker, ResourceInfoBean>> it = markerMap.entrySet().iterator();
			while (it.hasNext()) {
				Entry<Marker, ResourceInfoBean> entry = it.next();
				Marker mk = entry.getKey();
				mk.remove();
			}

		}


		// 清空相关变量
		markerMap.clear();
		resourceInfoBeanList.clear();



		BaiduMap bm = mBaiduMap;
		resourceInfoBeanList.addAll(mResourceInfoBeanList);
		for (ResourceInfoBean bean : resourceInfoBeanList) {
			Marker mk = (Marker) bm.addOverlay(
					new MarkerOptions()
							.icon(getResourceTypeIco(bean.getResourceType()))
							.position(new LatLng(bean.getLatitude(), bean.getLongitude())));
			markerMap.put(mk, bean);
		}
	}

	/*//标识当前界面是否已经有一个小段的两个端点，如果有就不需要重复绘制了
	private boolean containTwoPoint = true;*/
	private void displayAroundResourcesLineOnMap(boolean append, List<ResourceLineBean> mResourceLineBeanList) {
		if (!append) // 覆盖展示
		{
			// 隐藏气泡，置选中的marker为空
			mBaiduMap.hideInfoWindow();

			// 移除掉旧marker
			Iterator<Entry<Marker, ResourceInfoBean>> it = markerMap.entrySet().iterator();
			while (it.hasNext()) {
				Entry<Marker, ResourceInfoBean> entry = it.next();
				Marker mk = entry.getKey();
				mk.remove();
			}

			// 清空相关变量
			markerMap.clear();
			resourceInfoBeanList.clear();
		}

		Marker mk;
		ArrayList<LatLng> line;
		LatLng startLatLng = null,endLatLng,tempLatLng;
		CoordinateConverter converter  = new CoordinateConverter();
		converter.from(CoordType.GPS);

		/*LatLng	latLng = new LatLng(38.042753, 114.621667);
		System.out.println("石药："+latLng);

		// sourceLatLng待转换坐标
		converter.coord(latLng);
		latLng = converter.convert();
		System.out.println("石药："+latLng);*/

		int color = 0x7f070002;

		for (ResourceLineBean bean : mResourceLineBeanList) {
			if(bean==null){
				continue;
			}

			line = new ArrayList<LatLng>();
			if(bean.getStart()!=null){
				// sourceLatLng待转换坐标
				tempLatLng = converter.coord(new LatLng(bean.getStart().getLatitude(), bean.getStart().getLongitude())).convert();

				startLatLng = new LatLng(tempLatLng.latitude, tempLatLng.longitude);
				if(!markerMap.containsValue(bean.getStart())){
//					containTwoPoint = false;

					mk = (Marker) mBaiduMap.addOverlay(new MarkerOptions()
							.icon(getResourceTypeIco(bean.getStart()
									.getResourceType()))
							.position(startLatLng))
					;
					mk.setToTop();
					markerMap.put(mk, bean.getStart());
				}
				line.add(startLatLng);
			}

			if(bean.getEnd()!=null){

				// sourceLatLng待转换坐标
				tempLatLng = converter.coord(new LatLng(bean.getEnd().getLatitude(), bean.getEnd().getLongitude())).convert();

				endLatLng = new LatLng(tempLatLng.latitude, tempLatLng.longitude);
				if(!markerMap.containsValue(bean.getEnd())){
//					containTwoPoint = false;
					mk = (Marker) mBaiduMap.addOverlay(new MarkerOptions()
							.icon(getResourceTypeIco(bean.getEnd()
									.getResourceType()))
							.position(endLatLng));
					markerMap.put(mk, bean.getEnd());
				}
				line.add(endLatLng);

			}

			if(line.size()>=2/*&&!containTwoPoint*/){
				color = ("1".equals(bean.getStart().getIsPass())&&"1".equals(bean.getEnd().getIsPass()))?0x7f0085d0:0x7f070002;
				mBaiduMap.addOverlay((new PolylineOptions()).width(10).color(color).points(line));
			}
		}
	}

	@Subscriber(tag = ZSLConst.tag_onResourceLineBeanList_get_ok)
	public void onResourceLineBeanListSearchOK(
			List<ResourceLineBean> mResourceLineBeanList) {

		displayAroundResourcesLineOnMap(true, mResourceLineBeanList);
	}

	@Subscriber(tag = ZSLConst.tag_onResourceInfoBeanList_get_ok)
	public void onResourceInfoBeanListSearchOK(
			List<ResourceInfoBean> mResourceInfoBeanList) {
		displayAroundResourcesOnMap(false, mResourceInfoBeanList);
	}


	BitmapDescriptor bdgenerator;
	BitmapDescriptor bdocc;
	BitmapDescriptor bdpole;
	BitmapDescriptor bdwell;
	BitmapDescriptor bdstation;
	BitmapDescriptor bdsuppPoint;
	BitmapDescriptor bdA;
	private BitmapDescriptor getResourceTypeIco(String type){
		if("站点".equals(type)){
			if(bdgenerator==null){
				bdgenerator = BitmapDescriptorFactory.fromResource(R.drawable.icon_gcoding_generator);
			}
			return bdgenerator;
		}else if("资源点".equals(type)){
			if(bdocc==null){
				bdocc = BitmapDescriptorFactory.fromResource(R.drawable.icon_gcoding_occ);
			}
			return bdocc;
		}else if("电杆".equals(type)){
			if(bdpole==null){
				bdpole = BitmapDescriptorFactory.fromResource(R.drawable.icon_gcoding_pole);
			}
			return bdpole;
		}else if("管井".equals(type)){
			if(bdwell==null){
				bdwell = BitmapDescriptorFactory.fromResource(R.drawable.icon_gcoding_well);
			}
			return bdwell;
		}else if("标石".equals(type)){
			if(bdstation==null){
				bdstation = BitmapDescriptorFactory.fromResource(R.drawable.icon_gcoding_stone);
			}
			return bdstation;
		}else if("撑点".equals(type)){
			if(bdsuppPoint==null){
				bdsuppPoint = BitmapDescriptorFactory.fromResource(R.drawable.icon_gcoding_supp_point);
			}
			return bdsuppPoint;
		}else {
			if(bdA==null){
				bdA = BitmapDescriptorFactory.fromResource(R.drawable.icon_gcoding);
			}
			return bdA;
		}
	}


	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		mLocationClient.stop();

	}


	ResourceInfoBean mTestBean;
	private Location location = null;
	private void getResourceLineBeanList(final double lat,final double lon){

		if (this.mProgress == null) {
			this.mProgress = ProgressDialog.show(this, "系统提示", "正在查询周边资源...");
		} else {
			this.mProgress.setMessage("正在查询周边资源...");
			this.mProgress.show();
		}

		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub

				Log.d("lixu","查询资源中");

				List<ResourceInfoBean> resourceInfoBeanList= new ArrayList<ResourceInfoBean>();
				List<ResourceLineBean> resourceLineBeanList = new ArrayList<ResourceLineBean>();

//    Double xMax = lon+(10000.0/33/3600);
//	Double xMin = lon-(10000.0/33/3600);
//	Double yMax = lat+(10000.0/33/3600);
//	Double yMin = lat-(10000.0/33/3600);

				Double xMax = lon+(300.0/33/3600);
				Double xMin = lon-(300.0/33/3600);
				Double yMax = lat+(300.0/33/3600);
				Double yMin = lat-(300.0/33/3600);


				String ids = "";
				Map<Integer,ResourceInfoBean> map = new HashMap<Integer,ResourceInfoBean>();

				//查询周围点资源
				String mSq = "select int_id as resourceID,zh_label as resourceName,resource_type as resourceType,longitude,latitude, (case when is_pass='已巡检通过' then 0 when is_pass='已巡检未通过' then 1 else 0 end) isPass from RES_RESOURCE_POINT where longitude > "+xMin+" and longitude < "+xMax+" and latitude > "+yMin+" and latitude < "+yMax;
				Cursor cur = db_line.rawQuery(mSq, null);
				while (cur.moveToNext()) {
					int mId = cur.getInt(cur.getColumnIndex("resourceID"));
					String mName = cur.getString(cur.getColumnIndex("resourceName"));
					String mType = cur.getString(cur.getColumnIndex("resourceType"));
					double mLon = cur.getDouble(cur.getColumnIndex("longitude"));
					double mLat = cur.getDouble(cur.getColumnIndex("latitude"));
					String mIsPass = cur.getString(cur.getColumnIndex("isPass"));

					mTestBean = new ResourceInfoBean();
					mTestBean.setResourceID(mId);
					mTestBean.setResourceName(mName);
					mTestBean.setResourceType(mType);
					mTestBean.setLongitude(mLon);
					mTestBean.setLatitude(mLat);
					mTestBean.setIsPass(mIsPass);

					resourceInfoBeanList.add(mTestBean);

					//将int_id和对应的bean对象放入map 后面封装用到
					ids += mId+",";
					map.put(mId, mTestBean);

					mTestBean = null;
				}



				if(ids!=""){
					ids = ids.substring(0, ids.length()-1);
				}else{
					return;
				}
				//点对应的段信息
				String mSql_2 = "select id,zh_label,a_object_id,z_object_id,related_branch,type,c_length from wx_daiwei_jiaoge where 1 = 1 and a_object_id in ("+ids+") "
						+" union all "
						+" select id,zh_label,a_object_id,z_object_id,related_branch,type,c_length from wx_daiwei_jiaoge where 1 = 1 and z_object_id in ("+ids+") ";
				cur = db_line.rawQuery(mSql_2, null);
				ResourceLineBean lineBean = null;

				while (cur.moveToNext()) {
					lineBean = new ResourceLineBean();
					int related_branch = cur.getInt(cur.getColumnIndex("related_branch"));
					int a_object_id = cur.getInt(cur.getColumnIndex("a_object_id"));
					int z_object_id = cur.getInt(cur.getColumnIndex("z_object_id"));

					lineBean.setRelatedBranch(related_branch);
					//封装ResourceInfoBean start 直接取之前map中放入的bean对象
					ResourceInfoBean startBean = map.get(a_object_id);
					//封装ResourceInfoBean end 直接取之前map中放入的bean对象
					ResourceInfoBean endBean = map.get(z_object_id);
					lineBean.setStart(startBean);
					lineBean.setEnd(endBean);

					resourceLineBeanList.add(lineBean);
					lineBean = null;
				}


				if (mProgress != null) {
					mProgress.dismiss();
				}

				EventBus.getDefault().post(resourceLineBeanList, ZSLConst.tag_onResourceLineBeanList_get_ok);
			}
		}).start();
	}

}
