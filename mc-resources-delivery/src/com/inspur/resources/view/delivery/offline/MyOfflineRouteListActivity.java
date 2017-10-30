package com.inspur.resources.view.delivery.offline;

import java.io.File;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import cn.trinea.android.common.util.PreferencesUtils;

import com.alibaba.fastjson.JSONArray;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BaiduMap.OnMapClickListener;
import com.baidu.mapapi.map.BaiduMap.OnMarkerClickListener;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.PolylineOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.CoordinateConverter;
import com.baidu.mapapi.utils.CoordinateConverter.CoordType;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.inspur.component.swipyrefreshlayout.SwipyRefreshLayout;
import com.inspur.component.swipyrefreshlayout.SwipyRefreshLayoutDirection;
import com.inspur.easyresources.R;
import com.inspur.resources.base.BaseActivity;
import com.inspur.resources.http.httpconnect;
import com.inspur.resources.utils.ApplicationValue;
import com.inspur.resources.utils.NetWorkUtils;
import com.inspur.resources.view.delivery.transroute.HiddenTroubleReportActivity2;
import com.inspur.resources.view.delivery.transroute.RouteSubmitActivity;
import com.inspur.resources.view.delivery.transroute.bean.ErrorInfoBean;
import com.inspur.resources.view.delivery.transroute.bean.GdErrorInfoBean;
import com.inspur.resources.view.delivery.transroute.bean.LocusPoint;
import com.inspur.resources.view.delivery.transroute.bean.PointlikeResourceInfoBean;
import com.inspur.resources.view.delivery.transroute.bean.RouteInfoBean;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MyOfflineRouteListActivity extends BaseActivity implements OnMarkerClickListener, OnMapClickListener {


	private SQLiteDatabase db_line;  //线

	String pathString_line = MainOfflineActivity.FILE_PATH;
	private File f_line = new File(MainOfflineActivity.FILE_PATH + MainOfflineActivity.FILE_NAME_ROUTE); //数据库文件

	private final static int REQUEST_CODE_FOREDIT = 1;
	private SwipyRefreshLayout mSwipyRefreshLayout;
	private ListView listview;
	private DeliveryRouteAdapter mAdapter;
	private List<RouteInfoBean> deliveryRouteList = new ArrayList<RouteInfoBean>();
	private ProgressDialog mProgress;
	private RouteInfoBean curRouteInfoBean;
	private boolean firstLoc = true;

	private final static int LOAD_DATA = 1;
	private final static int LOAD_MORE = 2;
	private int mStatus = 0;//查询交割状态 0:通过  1：未通过  2：未交割

	//管道故障相关
	private List<Marker> gds = new ArrayList<Marker>();
	private List<List<Marker>> gdMarkers = new ArrayList<List<Marker>>();
	private HashMap<List<List<Marker>>, GdErrorInfoBean> gdList;// = new HashMap<List<List<Marker>>, GdErrorInfoBean>();

	private int curPage = 1;

	private Handler handler = new Handler(new Handler.Callback() {

		public boolean handleMessage(Message msg) {
			if (mProgress != null) {
				mProgress.dismiss();
			}
			switch (msg.what) {
				case LOAD_DATA:
					deliveryRouteList.clear();
					List<RouteInfoBean> list  = new ArrayList<RouteInfoBean>();
					try{

				/*list = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss Z").create()
						.fromJson(msg.obj.toString(), new TypeToken<List<RouteInfoBean>>() {
				}.getType());*/


						Type type = new TypeToken<List<RouteInfoBean>>() {}.getType();

						Gson gson = new Gson();

						list = gson.fromJson(msg.obj.toString(), type);

					}catch(Exception e){
						Log.i("lixu", "异常"+e.toString());
					}

					Log.i("lixu", "解析完");
					deliveryRouteList.addAll(list);
					Log.i("lixu", "开始更新适配器");
					mAdapter.notifyDataSetChanged();
					mSwipyRefreshLayout.setRefreshing(false);
					break;
				case LOAD_MORE:
					List<RouteInfoBean> list2 = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss Z").create()
							.fromJson(msg.obj.toString(), new TypeToken<List<RouteInfoBean>>() {
							}.getType());
					for (RouteInfoBean routeInfoBean : list2) {
						if (!deliveryRouteList.contains(routeInfoBean))
							deliveryRouteList.add(routeInfoBean);
					}
					mAdapter.notifyDataSetChanged();
					mSwipyRefreshLayout.setRefreshing(false);
					break;
				case -1:
					mSwipyRefreshLayout.setRefreshing(false);
					Toast.makeText(MyOfflineRouteListActivity.this, msg.obj.toString(), Toast.LENGTH_SHORT).show();
					break;
				case 96:

					mAdapter.notifyDataSetChanged();
					mSwipyRefreshLayout.setRefreshing(false);

					break;

				default:
					mSwipyRefreshLayout.setRefreshing(false);
					break;
			}
			return true;
		}

	});
	private MapView mapview;
	private Map<Marker, PointlikeResourceInfoBean> pointMarkerMap;
	private Map<Marker, ErrorInfoBean> errorMarkerMap;
	private LocationClient mLocationClient;

	@Override
	protected void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		setContentView(R.layout.activty_my_delivery_route);
		setTitle("我的传输交割");
		init();



		try{
			db_line = SQLiteDatabase.openOrCreateDatabase(f_line, null);
			//getResourceLineBeanList();
		}catch(Exception e){
			Log.d("lixu","数据库报错了=="+e.toString());
		}

		getData(LOAD_DATA,1,mStatus);

	}

	private void animateMyToLocation(BDLocation lastLocation) {
		if (lastLocation != null) {
			double lat = lastLocation.getLatitude();
			double lng = lastLocation.getLongitude();

			MapStatusUpdate update = MapStatusUpdateFactory.newLatLngZoom(new LatLng(lat, lng),18f);
			mapview.getMap().animateMapStatus(update);
		}
	}

	private void init() {
		mapview = (MapView) findViewById(R.id.mapview);
		mapview.showZoomControls(false);
		mapview.getMap().setOnMarkerClickListener(this);
		mapview.getMap().setOnMapClickListener(this);

		// 初始化定位，并开启定位
		mLocationClient = new LocationClient(getApplicationContext());// 声明LocationClient类
		mLocationClient.registerLocationListener(new BDLocationListener() {

			@Override
			public void onReceiveLocation(BDLocation lastLocation) {
				if(firstLoc){
					animateMyToLocation(lastLocation);
				}
				firstLoc = false;
			}
		});// 注册监听函数
		initLocation();
		mLocationClient.start();

		mSwipyRefreshLayout = (SwipyRefreshLayout) findViewById(R.id.swipyrefreshlayout);
		listview = (ListView) findViewById(R.id.listView);
		mAdapter = new DeliveryRouteAdapter(MyOfflineRouteListActivity.this, deliveryRouteList);
		listview.setAdapter(mAdapter);
		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				mapview.getMap().clear();
				curRouteInfoBean = deliveryRouteList.get(position);
				// 在地图展示轨迹和隐患点、起始点、终点
				if(curRouteInfoBean.getStartPosition() != null){
					displayResourcesOnMap(curRouteInfoBean.getStartPosition(), curRouteInfoBean.getEndPosition());

				}
				if (curRouteInfoBean.getErrors() != null && curRouteInfoBean.getErrors().size() > 0) {
					displayErrorResourcesOnMap(curRouteInfoBean.getErrors());
				}


				if(curRouteInfoBean.getStartPosition() != null){
					displayLineOnMap(curRouteInfoBean.getStartPosition(),curRouteInfoBean.getEndPosition(),curRouteInfoBean.getLocusPoints(), curRouteInfoBean.getDeliveryState());
				}
			}
		});

		mSwipyRefreshLayout.setOnRefreshListener(new SwipyRefreshLayout.OnRefreshListener() {
			@Override
			public void onRefresh(SwipyRefreshLayoutDirection direction) {
				if(direction == SwipyRefreshLayoutDirection.TOP){
					curPage=1;
					getData(LOAD_DATA,curPage,mStatus);
				}else{
					curPage++;
					getData(LOAD_MORE,curPage,mStatus);
				}
				Log.d("MainActivity",
						"Refresh triggered at " + (direction == SwipyRefreshLayoutDirection.TOP ? "top" : "bottom"));
			}
		});


	}

	private void initLocation() {
		LocationClientOption option = new LocationClientOption();
		option.setLocationMode(LocationMode.Hight_Accuracy);// 可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
		option.setCoorType("bd09ll");// 可选，默认gcj02，设置返回的定位结果坐标系
		option.setScanSpan(2000);// 可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
		option.setOpenGps(true);// 可选，默认false,设置是否使用gps
		option.setLocationNotify(true);// 可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果

		mLocationClient.setLocOption(option);
	}

	private void displayResourcesOnMap(PointlikeResourceInfoBean start, PointlikeResourceInfoBean end) {
		pointMarkerMap = new HashMap<Marker, PointlikeResourceInfoBean>();
		BaiduMap bm = mapview.getMap();
		BitmapDescriptor startDes = BitmapDescriptorFactory.fromResource(R.drawable.icon_st);
		BitmapDescriptor endDes = BitmapDescriptorFactory.fromResource(R.drawable.icon_en);
		LatLng startLatLng = null;
		if(start!=null){
			startLatLng= coverteToBaidu(start.getLatitude(), start.getLongitude());
			Marker mk = (Marker) bm.addOverlay(
					new MarkerOptions().icon(startDes).position(startLatLng));
			pointMarkerMap.put(mk, start);
		}
		if(end!=null){
			Marker mkEnd = (Marker) bm.addOverlay(
					new MarkerOptions().icon(endDes).position(coverteToBaidu(end.getLatitude(), end.getLongitude())));
			pointMarkerMap.put(mkEnd, end);
		}
		if(startLatLng!=null){
			MapStatusUpdate update = MapStatusUpdateFactory.newLatLngZoom(startLatLng,18);
			mapview.getMap().animateMapStatus(update);
		}
	}


	//打资源隐患点
	private void displayErrorResourcesOnMap(List<ErrorInfoBean> mErrorInfoBeanList) {
		if(mErrorInfoBeanList==null||mErrorInfoBeanList.size()==0){
			return;
		}
		errorMarkerMap = new HashMap<Marker, ErrorInfoBean>();
		BitmapDescriptor bdA = BitmapDescriptorFactory.fromResource(R.drawable.icon_gcoding_1);
		BaiduMap bm = mapview.getMap();
		for (ErrorInfoBean bean : mErrorInfoBeanList) {
			Marker mk = (Marker) bm.addOverlay(
					new MarkerOptions().icon(bdA).position(coverteToBaidu(bean.getLatitude(), bean.getLongitude())));
			errorMarkerMap.put(mk, bean);
		}
	}

	//管道故障点
	private void displayGdLineOnMap(List<GdErrorInfoBean> mErrorInfoBeanList) {

		if(mErrorInfoBeanList==null||mErrorInfoBeanList.size()==0){
			return;
		}

		gdList = new HashMap<List<List<Marker>>, GdErrorInfoBean>();
		BitmapDescriptor bdA = BitmapDescriptorFactory.fromResource(R.drawable.icon_gcoding_1);
		BaiduMap bm = mapview.getMap();

		gdMarkers.clear();
		gdList.clear();
		gds.clear();

		for (GdErrorInfoBean bean : mErrorInfoBeanList) {
			gds.clear();
			Marker mk = (Marker) bm.addOverlay(
					new MarkerOptions().icon(bdA).position(coverteToBaidu(bean.getLatitudeStart(), bean.getLongitudeEnd())));
			Marker mk2 = (Marker) bm.addOverlay(
					new MarkerOptions().icon(bdA).position(coverteToBaidu(bean.getLatitudeStart(), bean.getLongitudeEnd())));

			gds.add(mk);
			gds.add(mk2);
			gdMarkers.add(gds);
			gdList.put(gdMarkers, bean);
		}
	}

	private LatLng coverteToBaidu(double lat,double lng){
		// 将GPS设备采集的原始GPS坐标转换成百度坐标
		CoordinateConverter converter  = new CoordinateConverter();
		converter.from(CoordType.GPS);
		// sourceLatLng待转换坐标
		converter.coord(new LatLng(lat,lng));
		return converter.convert();
	}

	private void displayLineOnMap(PointlikeResourceInfoBean start,PointlikeResourceInfoBean end,List<LocusPoint> mLocusPointList, int deliveryState) {
		if(mLocusPointList==null||mLocusPointList.size()==0){
			return;
		}
		int color;
		List<LatLng> arraylist = new ArrayList<LatLng>(mLocusPointList.size());
//		arraylist.add(new LatLng(start.getLatitude(), start.getLongitude()));
		if(start!=null){
			arraylist.add(coverteToBaidu(start.getLatitude(), start.getLongitude()));
		}
		if(mLocusPointList!=null&&mLocusPointList.size()>0){
			for (LocusPoint locus : mLocusPointList) {

				arraylist.add(coverteToBaidu(locus.getLatitude(), locus.getLongitude()));
			}
		}
		if(end!=null){
			arraylist.add(coverteToBaidu(end.getLatitude(), end.getLongitude()));
		}
		if (deliveryState == 0) {
			color = 0xaa008000;
		} else if (deliveryState == 1) {
			color = 0xaaFFFF00;
		} else {
			color = 0xaa808080;
		}

		if(arraylist!=null && arraylist.size() > 0){
			mapview.getMap().addOverlay((new PolylineOptions()).width(10).color(color).points(arraylist));
		}
	}

	private void getData(final int action,final int page,final int status) {

		if (this.mProgress == null) {
			this.mProgress = ProgressDialog.show(this, "系统提示", "正在加载数据...");
		} else {
			this.mProgress.setMessage("正在加载数据...");
			this.mProgress.show();
		}

		if(NetWorkUtils.isNetworkConnected(MyOfflineRouteListActivity.this)){
			new Thread() {
				@Override
				public void run() {
					final ArrayList<NameValuePair> list = new ArrayList<NameValuePair>();
					String json = "{\"page\":"+page+",\"pageSize\":"+10+",\"status\":"+status+"}";
					list.add((NameValuePair)new BasicNameValuePair("jsonRequest", json));

					final String httpGetData = new httpconnect().httpGetData("pdaMainTask!getMyRoute.interface", list,
							MyOfflineRouteListActivity.this);

					Log.d("AddrList==>", "result==>" + httpGetData);
					System.out.println("result==>" + httpGetData);
					if (httpGetData == null) {
						if ("".equals(httpGetData)) {
							return;
						}
					}
					try {
						Log.d("AddrList==>", "result==>2111" );
						final Message message = new Message();
						final JSONObject jsonObject = new JSONObject(httpGetData.toString());
						final String string = jsonObject.getString("info");
						if (jsonObject.getString("result").equals("0")) {
							message.what = action;
							message.obj = string;
							handler.sendMessage(message);
							Log.d("AddrList==>", "result==>22222" + message.obj);
							return;
						}
						Log.d("AddrList==>", "result==>错了");
						message.what = -1;
						message.obj = string;
						handler.sendMessage(message);
					} catch (JSONException ex) {
						Log.d("AddrList==>", "result==>异常"+ex.toString());
					}
				}
			}.start();

		}else{

			deliveryRouteList.clear();
			try{
				String uid = PreferencesUtils.getString(MyOfflineRouteListActivity.this, "UID", "");
				int cRouteId ;
				deliveryRouteList.clear();
				String mSQL = "select * from (select a.* from (select id as routeID,name,state as routeState,datetime(create_time,'localtime'),deal_person as dealPerson,DEAL_STATE as deliveryState,MATE_RATE as matchScores,flag from HEBEI_ROUTE where 1 = 1 and USER_ID = '"+uid+"' and DEAL_STATE = "+status+" order by create_time desc) a ) where 1=1";
				Cursor cur = db_line.rawQuery(mSQL, null);
				while(cur.moveToNext()){
					int mRouteId2 = cur.getInt(cur.getColumnIndex("routeID"));  			//路由ID
					String mName = cur.getString(cur.getColumnIndex("name"));  				//光交名称
					String mMatchScores = cur.getString(cur.getColumnIndex("matchScores"));  //匹配率
					int mRouteState = cur.getInt(cur.getColumnIndex("routeState"));   //路由状态
					String mDeliveryDate = cur.getString(cur.getColumnIndex("datetime(create_time,'localtime')"));  //交割时间（可能报错）
					String mDealPerson = cur.getString(cur.getColumnIndex("dealPerson"));		//交割人
					//int mUserId = cur.getInt(cur.getColumnIndex(""));
					Log.d("lixu", "查询1");
					SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
					Date date = format.parse(mDeliveryDate);
					RouteInfoBean cRouteInfoBean = new RouteInfoBean();
					cRouteInfoBean.setRouteID(mRouteId2);
					cRouteInfoBean.setMatchScores(mMatchScores);//点资源匹配率
					cRouteInfoBean.setRouteState(mRouteState);//路由状态
					cRouteInfoBean.setDeliveryState(mRouteState);//交割状态
					cRouteInfoBean.setDeliveryDate(date);//交割时间
					cRouteInfoBean.setDealPerson(mDealPerson);//交割人
					cRouteInfoBean.setName(mName);//路由段名称
					cRouteInfoBean.setUserId(uid);

					cRouteId = cRouteInfoBean.getRouteID();

					Log.d("lixu", "查询0000000000000000000000000");

					//查询隐患点
					List<ErrorInfoBean> erros = new ArrayList<ErrorInfoBean>();
					String mSQL_2 = "select ID,ROUTE_ID as routeID,CITY as city,COUNTY as county,LONGITUDE as lon,LATITUDE as lat,RESOURCE_ID as resourceID,RESOURCE_TYPE as resourceType,ERROR_OBJ as errorObjectName,ERROR_LOCATE_DES as errorLocateDes,ERROR_BIG as errorBig,ERROR_SMALL as errorSmall,ERROR_DESCRIPTION as errorDescribe,IS_ZG as isZg,ZG_RQ as zgRq,datetime(CREATE_TIME,'localtime'),USER_ID as userId from HEBEI_ERROR where route_id = "+cRouteId;
					Cursor cur_2 = db_line.rawQuery(mSQL_2, null);
					while(cur_2.moveToNext()){

						ErrorInfoBean cErrorInfoBean = new ErrorInfoBean();
						int mRouteId = cur_2.getInt(cur_2.getColumnIndex("routeID"));
						double mLon = cur_2.getDouble(cur_2.getColumnIndex("lon"));
						double mLat = cur_2.getDouble(cur_2.getColumnIndex("lat"));
						String mCity = cur_2.getString(cur_2.getColumnIndex("city"));
						String mCounty = cur_2.getString(cur_2.getColumnIndex("county"));
						int mResourceID = cur_2.getInt(cur_2.getColumnIndex("resourceID"));
						String mResourceType = cur_2.getString(cur_2.getColumnIndex("resourceType"));
						String mErroName = cur_2.getString(cur_2.getColumnIndex("errorObjectName"));
						String mErroDes = cur_2.getString(cur_2.getColumnIndex("errorLocateDes"));
						String mErrorBig = cur_2.getString(cur_2.getColumnIndex("errorBig"));
						String mErrorSmall = cur_2.getString(cur_2.getColumnIndex("errorSmall"));
						String mErrorDescribe = cur_2.getString(cur_2.getColumnIndex("errorDescribe"));
						String mIsZg = cur_2.getString(cur_2.getColumnIndex("isZg"));
						String mZgRq = cur_2.getString(cur_2.getColumnIndex("zgRq"));
						String mCreateTime = cur_2.getString(cur_2.getColumnIndex("datetime(CREATE_TIME,'localtime')"));
						String mUserId = cur_2.getString(cur_2.getColumnIndex("userId"));

						SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
						Date date2 = format2.parse(mCreateTime);

						cErrorInfoBean.setRouteID(mRouteId);
						cErrorInfoBean.setLongitude(mLon);
						cErrorInfoBean.setLatitude(mLat);
						cErrorInfoBean.setCity(mCity);
						cErrorInfoBean.setCounty(mCounty);
						cErrorInfoBean.setResourceID(mResourceID);
						cErrorInfoBean.setResourceType(mResourceType);
						cErrorInfoBean.setErrorObjectName(mErroName);
						cErrorInfoBean.setErrorLocateDes(mErroDes);
						cErrorInfoBean.setErrorBig(mErrorBig);
						cErrorInfoBean.setErrorSmall(mErrorSmall);
						cErrorInfoBean.setErrorDescribe(mErrorDescribe);
						cErrorInfoBean.setIsZg(mIsZg);
						cErrorInfoBean.setZgRq(mZgRq);
						cErrorInfoBean.setCreateTime(date2);
						cErrorInfoBean.setUserId(mUserId);


						erros.add(cErrorInfoBean);
						cur_2.close();
						cErrorInfoBean = null;
					}
					cRouteInfoBean.setErrors(erros);
					Log.d("lixu", "查询1111111111111111");
					//查询轨迹点
					List<LocusPoint> locusPoints = new ArrayList<LocusPoint>();
					String mSQL_3 = "select ROUTE_ID as routeID,LONGITUDE as lon,LATITUDE as lat from HEBEI_TRAIL where route_id = "+cRouteId;
					Cursor cur_3 = db_line.rawQuery(mSQL_3, null);
					while(cur_3.moveToNext()){

						LocusPoint cLocusPoint = new LocusPoint();

						int mRouteId = cur_3.getInt(cur_3.getColumnIndex("routeID"));



						double mLon = cur_3.getDouble(cur_3.getColumnIndex("lon"));
						Log.d("lixu", "查询==8");

						double mLat = cur_3.getDouble(cur_3.getColumnIndex("lat"));
						Log.d("lixu", "查询==9");

						cLocusPoint.setLongitude(mLon);
						cLocusPoint.setLatitude(mLat);

						cLocusPoint.setRouteID(mRouteId);

						locusPoints.add(cLocusPoint);

						cLocusPoint = null;
					}
					cRouteInfoBean.setLocusPoints(locusPoints);
					cur_3.close();
					Log.d("lixu", "查询222222222222222222222");
					//查询起点
					PointlikeResourceInfoBean startPosition = new PointlikeResourceInfoBean();// 起始点
					String mSQL_4 = "select route_id as routeID,longitude as lon,latitude as lat,resource_id as resourceID,resource_name as resourceName,resource_type as resourceType from HEBEI_POINT where 1 = 1 and ROUTE_ID = "+cRouteId+" and TYPE = 0";
					Cursor cur_4 = db_line.rawQuery(mSQL_4, null);
					while(cur_4.moveToNext()){
						int mRouteId = cur_4.getInt(cur_4.getColumnIndex("routeID"));
						Log.d("lixu", "查询===8");
						double mLon = cur_4.getDouble(cur_4.getColumnIndex("lon"));
						Log.d("lixu", "查询===9");
						double mLat = cur_4.getDouble(cur_4.getColumnIndex("lat"));
						int mResourceID = cur_4.getInt(cur_4.getColumnIndex("resourceID"));
						String mResourceName = cur_4.getString(cur_4.getColumnIndex("resourceName"));
						String mResourceType = cur_4.getString(cur_4.getColumnIndex("resourceType"));

						startPosition.setRouteID(mRouteId);
						startPosition.setLongitude(mLon);
						startPosition.setLatitude(mLat);
						startPosition.setResourceID(mResourceID);
						startPosition.setResourceName(mResourceName);
						startPosition.setResourceType(mResourceType);

					}
					cRouteInfoBean.setStartPosition(startPosition);
					cur_4.close();
					Log.d("lixu", "查询33333333333333333333333");
					//查询终点
					PointlikeResourceInfoBean endPosition = new PointlikeResourceInfoBean();// 终止点
					String mSQL_5 = "select route_id as routeID,longitude,latitude,resource_id as resourceID,resource_name as resourceName,resource_type as resourceType from HEBEI_POINT where 1 = 1 and ROUTE_ID = "+cRouteId+" and TYPE = 1";
					Cursor cur_5 = db_line.rawQuery(mSQL_5, null);
					while(cur_5.moveToNext()){

						int mRouteId = cur_5.getInt(cur_5.getColumnIndex("routeID"));
						double mLon = cur_5.getDouble(cur_5.getColumnIndex("longitude"));
						double mLat = cur_5.getDouble(cur_5.getColumnIndex("latitude"));
						int mResourceID = cur_5.getInt(cur_5.getColumnIndex("resourceID"));
						String mResourceName = cur_5.getString(cur_5.getColumnIndex("resourceName"));
						String mResourceType = cur_5.getString(cur_5.getColumnIndex("resourceType"));

						endPosition.setRouteID(mRouteId);
						endPosition.setLongitude(mLon);
						endPosition.setLatitude(mLat);
						endPosition.setResourceID(mResourceID);
						endPosition.setResourceName(mResourceName);
						endPosition.setResourceType(mResourceType);
					}
					cRouteInfoBean.setEndPosition(endPosition);

					deliveryRouteList.add(cRouteInfoBean);
					cRouteInfoBean = null;
					cur_5.close();
					Log.d("lixu", "查询444444444444444444444444");
				}

				Message message = Message.obtain();
				message.what = 96;
				handler.sendMessage(message);

			}catch(Exception e){
				Message message = Message.obtain();
				message.what = -1;
				message.obj = "查询失败，请稍候重试!";
				Log.d("lixu", "查询失败===="+e.toString());
				handler.sendMessage(message);
			}

		}

	}

	public class DeliveryRouteAdapter extends BaseAdapter {
		private Context mContext;
		private List<RouteInfoBean> mList;

		private DeliveryRouteAdapter(Context context, List<RouteInfoBean> list) {
			super();
			this.mContext = context;
			this.mList = list;
		}

		public int getCount() {
			return mList.size();
		}

		public Object getItem(final int n) {
			return mList.get(n);
		}

		public long getItemId(final int n) {
			return n;
		}

		@SuppressLint({ "NewApi" })
		public View getView(final int n, View inflate, final ViewGroup viewGroup) {
			ViewHolder tag;
			if (inflate == null) {
				inflate = LayoutInflater.from(mContext).inflate(R.layout.deliveryroute_list_item, null);
				tag = new ViewHolder();
				tag.name = (TextView) inflate.findViewById(R.id.name);
				tag.state = (TextView) inflate.findViewById(R.id.state);
				tag.time = (TextView) inflate.findViewById(R.id.time);
				tag.iv_info = (ImageView) inflate.findViewById(R.id.iv_info);
				inflate.setTag((Object) tag);
			} else {
				tag = (ViewHolder) inflate.getTag();
			}
			if (mList.size() > 0) {
				final RouteInfoBean routeInfoBean = mList.get(n);

				if (routeInfoBean.getStartPosition() != null && routeInfoBean.getEndPosition() != null) {
					tag.name.setText(routeInfoBean.getStartPosition().getResourceName() + "-"
							+ routeInfoBean.getEndPosition().getResourceName());
				}else{
					tag.name.setText("未知路由");
				}

				if (routeInfoBean.getDeliveryState() != null) {

					tag.state.setText(
							getResources().getStringArray(R.array.jiaogezhuangtai)[routeInfoBean.getRouteState()==null?2:(routeInfoBean.getDeliveryState()==null?2:routeInfoBean.getDeliveryState())]);
				}else{
					tag.name.setText("计算中..");
				}

				if(routeInfoBean.getDeliveryDate() != null){
					try{
						SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
						String str=sdf.format(routeInfoBean.getDeliveryDate());
						tag.time.setText(str);
					}catch(Exception e){

					}
				}


				tag.iv_info.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						Intent intent = new Intent(MyOfflineRouteListActivity.this, RouteSubmitActivity.class);
						intent.putExtra(RouteSubmitActivity.INTENT_DATA_FLAG, routeInfoBean);
						intent.putExtra("canEdit", false);
						startActivityForResult(intent, REQUEST_CODE_FOREDIT);
					}
				});
			}
			return inflate;
		}

		public class ViewHolder {
			public TextView name;
			public TextView state;
			public TextView time;
			public ImageView iv_info;
		}
	}



	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK) {
			if (requestCode == REQUEST_CODE_FOREDIT) {
				RouteInfoBean routeInfoBean = (RouteInfoBean) data.getSerializableExtra("route");
				if(routeInfoBean==null){
					return;
				}
				RouteInfoBean route;
				for(int i=deliveryRouteList.size()-1;i>=0;i--){
					route = deliveryRouteList.get(i);
					if(route.getRouteID().compareTo(routeInfoBean.getRouteID())==0){
						deliveryRouteList.get(i).setRouteState(routeInfoBean.getRouteState());
						deliveryRouteList.get(i).setMatchScores(routeInfoBean.getMatchScores());
						deliveryRouteList.get(i).setDeliveryState(routeInfoBean.getDeliveryState());
						mAdapter.notifyDataSetChanged();
						break;
					}
				}
			}
		}
	}

	@Override
	public void onMapClick(LatLng arg0) {

	}

	@Override
	public boolean onMapPoiClick(MapPoi arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onMarkerClick(Marker marker) {

		if (pointMarkerMap != null && pointMarkerMap.containsKey(marker)) {
			PointlikeResourceInfoBean point = pointMarkerMap.get(marker);
		} else if (errorMarkerMap != null && errorMarkerMap.containsKey(marker)) {

			ErrorInfoBean error = errorMarkerMap.get(marker);
			Intent intent = new Intent(MyOfflineRouteListActivity.this, HiddenTroubleReportActivity2.class);
			intent.putExtra("isUpdate", true);
			intent.putExtra("type", ApplicationValue.single_list[0]);
			intent.putExtra("routeID", error.getRouteID());
			intent.putExtra(HiddenTroubleReportActivity2.INTENT_DATA_FLAG, error);
			startActivity(intent);
		}



		if(gdMarkers != null){
			for(int  i = 0;i<gdMarkers.size();i++){
				for(int ii = 0;ii<gdMarkers.get(i).size();ii++){
					if(gdMarkers.get(i).get(ii).equals(marker)){

						//确定到管道故障点

					}
				}
			}
		}

		return false;
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_type, menu);
		return super.onCreateOptionsMenu(menu);
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.jiaoge_1://通过
				mStatus = 0;
				getData(LOAD_DATA,1,mStatus);
				break;
			case R.id.jiaoge_2://未通过
				mStatus = 1;
				getData(LOAD_DATA,1,mStatus);
				break;
			case R.id.jiaoge_no://未交割
				mStatus = 2;
				getData(LOAD_DATA,1,mStatus);
				break;
		}
		return super.onOptionsItemSelected(item);
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
		super.onDestroy();
	}

}
