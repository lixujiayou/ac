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
import java.util.Random;
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
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BaiduMap.OnMapClickListener;
import com.baidu.mapapi.map.BaiduMap.OnMapStatusChangeListener;
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
import com.inspur.resources.view.delivery.transroute.DeliveryStatus;
import com.inspur.resources.view.delivery.transroute.HiddenTroubleReportActivity2;
import com.inspur.resources.view.delivery.transroute.MyDeliveryRouteListActivity;
import com.inspur.resources.view.delivery.transroute.OfflineDeliveryActivity;
import com.inspur.resources.view.delivery.transroute.RouteSubmitActivity;
import com.inspur.resources.view.delivery.transroute.ZSLConst;
import com.inspur.resources.view.delivery.transroute.bean.ErrorInfoBean;
import com.inspur.resources.view.delivery.transroute.bean.LocusPoint;
import com.inspur.resources.view.delivery.transroute.bean.PhotoInfoBean;
import com.inspur.resources.view.delivery.transroute.bean.PointlikeResourceInfoBean;
import com.inspur.resources.view.delivery.transroute.bean.ResourceInfoBean;
import com.inspur.resources.view.delivery.transroute.bean.ResourceLineBean;
import com.inspur.resources.view.delivery.transroute.bean.RouteInfoBean;

import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
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
import android.os.Environment;
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
import cn.trinea.android.common.util.ToastUtils;

/**
 * @author Dell-离线 传输线路
 */
public class ZSLOfflineActivity extends BaseActivity implements OnMarkerClickListener, OnMapClickListener {

	/**
	 * 数据库相关
	 */
	private SQLiteDatabase db_line;  //线

	String pathString_line = MainOfflineActivity.FILE_PATH;
	private File f_line = new File(MainOfflineActivity.FILE_PATH + MainOfflineActivity.FILE_NAME_line); //数据库文件
	//

	private MediaPlayer mediaPlayer = new MediaPlayer();
	private boolean isSelect = false;

	//自动加载周围资源的时间间隔
	private final int AUTO_LOADAROUNDRESOURCE_INTERVAL = 60;

	private final Context context = this;
	// Views------------------------
	private MapView mapview;
	private BaiduMap mBaiduMap;
	private Button buGo;
	private Button buTakephoto;
	private Button buErrorReport;
	private View viewGPS;
	private TextView tv_temp;
	// -------------------------------

	// 与定位有关======================================================
	private LocationClient mLocationClient = null;
	private BDLocationListener myListener = new MyLocationListener();
	private BDLocation lastLocation;// 保存最后一次定位到的位置信息
	private boolean firstLocationFlg = true;// 是否为首次定位的标识
	// ==============================================================

	// 与请求当前位置周围一定范围内的资源点有关---------------------------------------
	// 存储当前展示的一定范围内的资源点
	private ArrayList<ResourceInfoBean> resourceInfoBeanList = new ArrayList<ResourceInfoBean>();
	// marker和点状资源映射表
	private HashMap<Marker, ResourceInfoBean> markerMap = new HashMap<Marker, ResourceInfoBean>();

	private ResourceInfoBean resourceInfoBean;
	// 当前选中的marker
	private Marker curSelectedMarker = null;
	// ----------------------------------------------------------------------

	// 定义交割状态，默认尚未开始交割
	DeliveryStatus curStatus = DeliveryStatus.NOT_START;

	// 当前路由实体类
	RouteInfoBean mRouteInfoBean;

	private final static int REQUESTCODE_ERRORREPORT = 1;
	private final static int REQUESTCODE_TAKE_PHOTO = 2;
	private final static int REQUESTCODE_SUBMIT_ROUTE = 3;//OVER
	private ProgressDialog mProgress;
	private int routeID=0;
	//标识是否在当前界面，当在当前界面时，接收到定位数据时才存储到轨迹列表中，否则直接丢弃定位数据
	private boolean inThisFace = true;
	//当前有效的轨迹点
	private LocusPoint curPoint;
	//当前有效轨迹点转换为标准gps坐标
	private boolean routeOperated = true;
	private double totalDistance = 0;
	private double distance = 0;
	//定位请求的间隔
	private	int span = 5000;//2000
	//当前已累计的请求周边资源的时间间隔
	private int curInterval = 0;
	private Gps gps = null;
	private double mDis;
	//存储Gps数据,主要是为了减少对坐标转换接口的请求次数,以百度经纬度形成key，如果是同样的经纬度就不再请求转换接口了
	private Map<String,Location> gpaCacheMap = new HashMap<String,Location>();

	private BitmapDescriptor mCurrentMarker = BitmapDescriptorFactory
			.fromResource(R.mipmap.location_marker);
	private Vibrator vibrator;

	private Handler handler = new Handler(new Handler.Callback() {

		public boolean handleMessage(Message msg) {
			if (mProgress != null) {
				mProgress.dismiss();
			}
			switch (msg.what) {
				case 1:
					PointlikeResourceInfoBean start = PointlikeResourceInfoBean.generateStartOrEndPoint((ResourceInfoBean)msg.obj, routeID) ;
					start(start);
					break;
				case 2:
					Toast.makeText(ZSLOfflineActivity.this, msg.obj.toString(), Toast.LENGTH_SHORT).show();
					break;
			}
			return true;
		}
	});

	private TextView tvinfo;


	private void start(ResourceInfoBean startRes) {
		PointlikeResourceInfoBean start = PointlikeResourceInfoBean.generateStartOrEndPoint(startRes, routeID) ;
		curStatus = DeliveryStatus.START;
		buGo.setText("结束");
		// 生成路由实体类
		mRouteInfoBean = new RouteInfoBean();
		mRouteInfoBean.setRouteID(routeID);
		mRouteInfoBean.setStartPosition(start);

		System.out.println("startRes.getResourceID()="+startRes.getResourceID());
		Log.d("lixu", "--------startRes.getResourceID()="+startRes.getResourceID());
		takePhoto(ZSLConst.PHOTO_TYPE_START,startRes.getResourceID(),startRes.getResourceType());
		routeOperated = false;
	}

	private void takePhoto(String photo_type,int relateId,String resourceType) {
		// 弹出拍照，拍摄起始点照片
		Intent intent = new Intent(context, PhotoOfficeActivity.class);
		intent.putExtra("mPhotoType", photo_type);
		intent.putExtra("routeId", mRouteInfoBean.getRouteID());
		intent.putExtra("relatedId", relateId+"");
		intent.putExtra("resourceType", resourceType);

		intent.putExtra("lon",ZSLConst.curGpsLocation.getLongitude());
		intent.putExtra("lat", ZSLConst.curGpsLocation.getLatitude());

		startActivityForResult(intent, REQUESTCODE_TAKE_PHOTO);
		// 设置标记，不再记录轨迹点
		inThisFace = false;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//SDKInitializer.initialize(this);
		setContentView(R.layout.zsl_activity_transmission_line);
		//	Intent gIntent = getIntent();

		setTitle("离线");
		try{
			File dir = new File(pathString_line);
			if (!dir.exists()) {
				ToastUtils.show(ZSLOfflineActivity.this, "资源文件不存在，请重新下载");
				finish();
				return;
			}
		}catch(Exception e){
			Log.d("lixu","数据库报错了===-"+e.toString());
		}
		/*File file1 = new File(pathString_line,MainOfflineActivity.FILE_NAME_line);
		if(!file1.exists()){

        	ToastUtils.show(ZSLOfflineActivity.this, "资源文件不存在，请重新下载");
        	finish();
		}*/




		vibrator = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
		setVolumeControlStream(AudioManager.STREAM_MUSIC);

		initViews();

		EventBus.getDefault().register(this);

		callLocationProvider();

		//初始化定位，并开启定位
		mLocationClient = new LocationClient(this);// 声明LocationClient类
		mLocationClient.registerLocationListener(myListener);// 注册监听函数
		initLocation();
		mLocationClient.start();

		//启动服务
		//startService(new Intent(this, GpsService.class));

		if (savedInstanceState != null) {
			restoreState(savedInstanceState);
			System.out.println("restoreState");
		}


		try{
			db_line = SQLiteDatabase.openOrCreateDatabase(f_line, null);
			//getResourceLineBeanList();
		}catch(Exception e){
			Log.d("lixu","数据库报错了=="+e.toString());
		}
	}

	private void initViews() {
		tvinfo = (TextView) findViewById(R.id.tv_info);
		mapview = (MapView) findViewById(R.id.zsl_mapview);
		tv_temp = (TextView) findViewById(R.id.tv_temp);
		mapview.showZoomControls(false);
		mBaiduMap = mapview.getMap();
		// 开启定位图层
		mBaiduMap.setMyLocationEnabled(true);

		mBaiduMap.setOnMarkerClickListener(this);


//		mBaiduMap.setOnMapClickListener(this);
		UiSettings mapS = mBaiduMap.getUiSettings();
		mapS.setZoomGesturesEnabled(true);//是否启用缩放手势
		mapS.setScrollGesturesEnabled(true);//是否启用平移手势
		mapS.setRotateGesturesEnabled(true);//是否启用旋转手势
		mapS.setOverlookingGesturesEnabled(true);
		mapS.setCompassEnabled(true);
		mapS.setAllGesturesEnabled(true);

		mBaiduMap.setMapStatus(MapStatusUpdateFactory.newMapStatus(new MapStatus.Builder().zoom(21).build()));

		buGo = (Button) findViewById(R.id.zsl_go);
		buGo.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (curStatus.equals(DeliveryStatus.NOT_START)) {
					onButtonGoClick();
				} else {
					String msg = "是否结束交割?";
					if(isSelect){
						msg = "是否结束选择井?";
					}


					AlertDialog ad = new AlertDialog.Builder(context).setTitle("温馨提示").setMessage(msg)
							.setPositiveButton("结束", new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog, int which) {
									dialog.dismiss();
									if(!isSelect){
										curStatus = DeliveryStatus.OVER;
										onButtonFinishClick();
									}
								}
							}).setNegativeButton("否", new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog, int which) {
									dialog.dismiss();
								}
							}).create();
					ad.show();

				}

			}
		});
		buTakephoto = (Button) findViewById(R.id.zsl_takephoto);
		buTakephoto.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (mRouteInfoBean == null) {
					Toast.makeText(ZSLOfflineActivity.this, "请先开始传输交割!", Toast.LENGTH_SHORT).show();
					return;
				}
				if(totalDistance>10000){
					//总距离大于10km强制结束
					forceSubmit();
					return;
				}
				if(locusSelectedResource==null){
					Toast.makeText(ZSLOfflineActivity.this, "请先选择资源点再拍照!", Toast.LENGTH_SHORT).show();
					return;
				}

			}
		});
		buErrorReport = (Button) findViewById(R.id.zsl_error_report);
		buErrorReport.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (mRouteInfoBean == null) {
					Toast.makeText(ZSLOfflineActivity.this, "请先开始传输交割!", Toast.LENGTH_SHORT).show();
					return;
				}
				if(totalDistance>10000){
					//总距离大于10km强制结束
					forceSubmit();
					return;
				}
				showSingleChoiceDialog();

			}
		});

		viewGPS = findViewById(R.id.zsl_gps);
		viewGPS.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				if(totalDistance>10000){
					//总距离大于10km强制结束
					forceSubmit();
					return;
				}

				animateMyToLocation();
				if(ZSLConst.curGpsLocation == null){
					Toast.makeText(getApplicationContext(), "定位中，请稍后...", Toast.LENGTH_SHORT).show();
					return;
				}
				curInterval = 0;

				try{
					getResourceLineBeanList(ZSLConst.curGpsLocation.getLatitude(),ZSLConst.curGpsLocation.getLongitude());
					//getResourceLineBeanList(38.04022825689031,114.61610688070446);

				}catch(Exception e){
					Log.d("lixu", "查询资源报错"+e.toString());
				}
			}
		});
	}


	private void showSingleChoiceDialog() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("请选择隐患类型");
		builder.setIcon(R.mipmap.ic_launcher);
		builder.setSingleChoiceItems(ApplicationValue.single_list, 0, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				Bundle bundle = new Bundle();
				bundle.putSerializable("bean",resourceInfoBean );
				Intent intent = new Intent(ZSLOfflineActivity.this, OfficeHiddenTroubleActivity.class);
				intent.putExtra("routeID", mRouteInfoBean.getRouteID());


				String str = ApplicationValue.single_list[which];
				intent.putExtra("type", str);

				if(which == 0){

				}else if(which == 1){

				}else if(which == 2){

				}
				intent.putExtras(bundle);
				// 调用隐患上报，当隐患未提交或未提交成功时回传加入路由实体的隐患列表中，最后和路由一起提交
				startActivityForResult(intent, REQUESTCODE_ERRORREPORT);

				// 设置标记，不再记录轨迹点
				inThisFace = false;
				dialog.dismiss();
			}
		});

		AlertDialog dialog = builder.create();
		dialog.show();
	}



	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (resultCode == RESULT_OK) {
			if (requestCode == REQUESTCODE_ERRORREPORT) {

			} else if (requestCode == REQUESTCODE_TAKE_PHOTO) {
				String pType = data.getStringExtra("mPhotoType");
//				ArrayList<PhotoInfoBean> photoList = (ArrayList<PhotoInfoBean>) data.getSerializableExtra("photos");
			//	拍照回调时会传递本次拍照的类型,进入此逻辑为RESULT_OK，则拍照成功过，当为起点或终点拍照时需要切换交割状态
				Log.d("lixu","-----返回"+pType);
				if(ZSLConst.PHOTO_TYPE_START.equalsIgnoreCase(pType)){
					curStatus = DeliveryStatus.ONGOING;
				}else if(ZSLConst.PHOTO_TYPE_END.equalsIgnoreCase(pType)){
					curStatus = DeliveryStatus.OVER;
					//	end();
				}
				if (ZSLConst.PHOTO_TYPE_START.equalsIgnoreCase(pType)) {
					if (mRouteInfoBean.getStartPosition() == null) {
						mRouteInfoBean.setStartPosition(new PointlikeResourceInfoBean());
					}
					mBaiduMap.hideInfoWindow();
					tvinfo.setVisibility(View.GONE);
					curSelectedMarker = null;
				} else if (ZSLConst.PHOTO_TYPE_END.equalsIgnoreCase(pType)) {
					if (mRouteInfoBean.getEndPosition() == null) {
						mRouteInfoBean.setEndPosition(new PointlikeResourceInfoBean());
					}
					end();
				} else if (ZSLConst.PHOTO_TYPE_WAY.equalsIgnoreCase(pType)) {
//					if (locusSelectedResource == null) {
//						Toast.makeText(getApplicationContext(), "请先选择资源后再拍照", Toast.LENGTH_SHORT).show();
//						return;
//					}
					if(mRouteInfoBean.getLocusResourcePosition() == null){
						mRouteInfoBean.setLocusResourcePosition(new ArrayList<PointlikeResourceInfoBean>());
					}
					mRouteInfoBean.getLocusResourcePosition().add(locusSelectedResource);//路径点
				}


			}else if(requestCode == REQUESTCODE_SUBMIT_ROUTE){
				//成功处理了路由（保存本地或提交成功）
				mBaiduMap.hideInfoWindow();
				tvinfo.setVisibility(View.GONE);
				curSelectedMarker = null;
				routeOperated = true;
				curStatus = DeliveryStatus.NOT_START;
				buGo.setText("开始");
				routeID=0;
				mBaiduMap.clear();
				totalDistance = 0;
				distance = 0;
				markerMap.clear();
				curInterval = 0;
				mRouteInfoBean = null ;
			}
		}
		//else{
			//刚开始，尚未完成起点的拍照就放弃，则直接重置本次交割过程
			// 在这只是把状态恢复为未开始状态，不清空routeid，尽量避免后台产生空数据
				/*curStatus = DeliveryStatus.NOT_START;
				buGo.setText("开始");
				mBaiduMap.clear();
				reset();*/
		//}
		 else {
			if (resultCode == RESULT_CANCELED) {
				if (requestCode == REQUESTCODE_TAKE_PHOTO) {
					//根据不同的状态来处理，当已经成功开始（完成了起点的标记和照片）应该提示拍照，如果是起点放弃了拍照则放弃整个交割过程
				/*	if (curStatus.equals(DeliveryStatus.ONGOING)) {
						String msg = "按照要求必须拍照!是否重新拍照?";
						AlertDialog ad = new AlertDialog.Builder(context).setTitle("温馨提示").setMessage(msg)
								.setPositiveButton("拍照", new DialogInterface.OnClickListener() {
									@Override
									public void onClick(DialogInterface dialog, int which) {
										dialog.dismiss();
										takePhoto(ZSLConst.PHOTO_TYPE_START, mRouteInfoBean.getStartPosition().getResourceID(), mRouteInfoBean.getStartPosition().getResourceType());
									}
								}).setNegativeButton("否", new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog, int which) {
										//TODO 视为放弃交割过程,清除本地和服务端的数据
										CacheHelper.getInstance(getApplicationContext()).deleteObject(ZSLConst.PREFIX_OF_OFFLINE_ROUTE + routeID);
										clearRoute();
										mBaiduMap.clear();
										reset();
									}
								}).create();
						ad.show();
					} else*/
				if (curStatus.equals(DeliveryStatus.START)) {
						//刚开始，尚未完成起点的拍照就放弃，则直接重置本次交割过程
						// 在这只是把状态恢复为未开始状态，不清空routeid，尽量避免后台产生空数据
						curStatus = DeliveryStatus.NOT_START;
						buGo.setText("开始");
						mBaiduMap.clear();
						reset();
					}
				}
			}
		}
		/*	else if(requestCode == REQUESTCODE_SUBMIT_ROUTE){
				curStatus = DeliveryStatus.ONGOING;
				reset();*/
//			}
//			}
//		}
	}

	private void reset(){
		CacheHelper.getInstance(getApplicationContext()).deleteObject(ZSLConst.PREFIX_OF_OFFLINE_ROUTE+routeID);
		clearRoute();
		mBaiduMap.hideInfoWindow();
		tvinfo.setVisibility(View.GONE);
		curSelectedMarker = null;
		routeOperated = true;
		curStatus = DeliveryStatus.NOT_START;
		buGo.setText("开始");
		mBaiduMap.clear();
		totalDistance = 0;
		distance = 0;
		markerMap.clear();
		curInterval = 0;
		mRouteInfoBean = null;
	}

	private void initLocation() {
		LocationClientOption option = new LocationClientOption();
		option.setLocationMode(LocationMode.Hight_Accuracy);// 可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
		option.setCoorType("bd09ll");// 可选，默认gcj02，设置返回的定位结果坐标系

		option.setScanSpan(span);// 可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
//		option.setIsNeedAddress(true);// 可选，设置是否需要地址信息，默认不需要
		option.setOpenGps(true);// 可选，默认false,设置是否使用gps
		option.setLocationNotify(true);// 可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
//		option.setIsNeedLocationDescribe(true);// 可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
//		option.setIsNeedLocationPoiList(true);// 可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
//		option.setIgnoreKillProcess(false);// 可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
//		option.SetIgnoreCacheException(false);// 可选，默认false，设置是否收集CRASH信息，默认收集
//		option.setEnableSimulateGps(false);// 可选，默认false，设置是否需要过滤gps仿真结果，默认需要

		mLocationClient.setLocOption(option);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.zsl_menu_transmission_line, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if(totalDistance>10000){
			//总距离大于10km强制结束
			forceSubmit();
			return true;
		}
		switch (item.getItemId()) {
			case R.id.zsl_my:// 我的
				startActivity(new Intent(ZSLOfflineActivity.this, MyOfflineRouteListActivity.class));
				// 设置标记，不再记录轨迹点
				inThisFace = false;
				break;
			case R.id.zsl_report_offline_data:// 提交离线数据
				//离线数据存储在程序内置文件目录，命名格式为：ROUTE_路由id
				startActivity(new Intent(ZSLOfflineActivity.this,OfflineRouteActivity.class));
			/*File dir = getFilesDir();
			File[] offlineFiles = dir.listFiles(new OfflineFilter());
			if(offlineFiles.length == 0){
				Toast.makeText(ZSLOfflineActivity.this, "暂无离线数据可提交。",Toast.LENGTH_SHORT).show();
				return super.onOptionsItemSelected(item);
			}*/

				break;
		}
		return super.onOptionsItemSelected(item);
	}

	class OfflineFilter implements FilenameFilter {

		public boolean accept(File dir, String name) {
			return name.startsWith(ZSLConst.PREFIX_OF_OFFLINE_ROUTE);
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
	double dis;
	double dis2 = 0;
	public class MyLocationListener implements BDLocationListener {
		private BDLocation preLocation;
		private BDLocation preLocation2;

		private void recordAndPaintTrack(BDLocation location){

//			location.setLatitude(38.04022825689031);
//			location.setLongitude(114.61610688070446);

			ArrayList<LatLng> arraylist = new ArrayList<LatLng>();

			if(mRouteInfoBean.getLocusPoints().size() == 0){
				arraylist.add(coverteToBaidu(mRouteInfoBean.getStartPosition().getLatitude(),mRouteInfoBean.getStartPosition().getLongitude()));
			}else{
				//arraylist.add(new LatLng(preLocation.getLatitude(), preLocation.getLongitude()));
				arraylist.add(new LatLng(preLocation2.getLatitude(), preLocation2.getLongitude()));
			}

			arraylist.add(new LatLng(location.getLatitude(), location.getLongitude()));

			mBaiduMap.addOverlay((new PolylineOptions()).width(10).color(0xaaff0000).points(arraylist));
			//Toast.makeText(ZSLOfflineActivity.this, "划线", Toast.LENGTH_SHORT).show();

			curPoint = new LocusPoint();
			curPoint.setRouteID(routeID);
			curPoint.setLatitude(ZSLConst.curGpsLocation.getLatitude());
			curPoint.setLongitude(ZSLConst.curGpsLocation.getLongitude());

			if(!mRouteInfoBean.getLocusPoints().contains(curPoint)){
				mRouteInfoBean.getLocusPoints().add(curPoint);
			}
		//	mRouteInfoBean.getLocusPoints().add(curPoint);
		}

		@Override
		public void onReceiveLocation(BDLocation location) {
//			location.setLatitude(38.04022825689031);
//			location.setLongitude(114.61610688070446);

			if (location == null || mapview == null) {
				Toast.makeText(ZSLOfflineActivity.this,"定位失败", Toast.LENGTH_SHORT).show();
				return;
			}

			callLocationProvider();
			//保存上次的位置信息
			preLocation = lastLocation;
			// 保存最新的位置信息
			lastLocation = location;
			ZSLConst.curLocation = lastLocation;


			MyLocationData locData = new MyLocationData.Builder()
					// 此处设置开发者获取到的方向信息，顺时针0-360
					.latitude(location.getLatitude())
					.longitude(location.getLongitude())
					.build();
			// 设置定位数据
			mBaiduMap.setMyLocationData(locData);


			if (firstLocationFlg == true) // 如果是首次定位，则跳到定位的位置
			{
				preLocation2 = lastLocation;
				//保存上次的位置信息
				preLocation = lastLocation;
				// 保存最新的位置信息
				lastLocation = location;

				double lat = location.getLatitude();
				double lng = location.getLongitude();

				MapStatusUpdate update = MapStatusUpdateFactory.newLatLng(new LatLng(lat, lng));
				mBaiduMap.animateMapStatus(update);
				firstLocationFlg = false;
			}

		//	animateMyToLocation();
			Log.d("qqqqqqqq","地图比例=="+mBaiduMap.getMapStatus().zoom);
			mBaiduMap.setMapStatus(MapStatusUpdateFactory.newMapStatus(new MapStatus.Builder().zoom(mBaiduMap.getMapStatus().zoom).build()));


			//在当前界面并且不是“未开始”和“已结束”状态，则记录轨迹点
			if(!curStatus.equals(DeliveryStatus.NOT_START)&&!curStatus.equals(DeliveryStatus.OVER)&&inThisFace){
				//记录轨迹点并在地图上绘制轨迹。绘制轨迹时使用百度的坐标，保存轨迹点使用标准gps坐标
				if(mRouteInfoBean != null){
					if(mRouteInfoBean.getLocusPoints() == null){
						mRouteInfoBean.setLocusPoints(new ArrayList<LocusPoint>());
					}



					//计算距离
					if(mRouteInfoBean.getLocusPoints().size()==0){	//如果无轨迹点，则计算起点与当前点的距离
						dis = DistanceUtil.getDistance(coverteToBaidu(mRouteInfoBean.getStartPosition().getLatitude(),mRouteInfoBean.getStartPosition().getLongitude()) , new LatLng(location.getLatitude(),location.getLongitude()));
						//全局路程变量
						//	mDis = DistanceUtil.getDistance(coverteToBaidu(mRouteInfoBean.getStartPosition().getLatitude(),mRouteInfoBean.getStartPosition().getLongitude()) , new LatLng(location.getLatitude(),location.getLongitude()));
					}else{
						//取最后一个轨迹点进行计算
						LocusPoint lastLocus = mRouteInfoBean.getLocusPoints().get(mRouteInfoBean.getLocusPoints().size()-1);
						dis = DistanceUtil.getDistance(coverteToBaidu(lastLocus.getLatitude(),lastLocus.getLongitude()) , new LatLng(location.getLatitude(),location.getLongitude()));
						//	mDis += DistanceUtil.getDistance(coverteToBaidu(lastLocus.getLatitude(),lastLocus.getLongitude()) , new LatLng(location.getLatitude(),location.getLongitude()));
					}

					if(totalDistance > 10000){//10000
						//总距离大于10km强制结束
						forceSubmit();
						return;  //直接返回 不再计算距离
					}

					if(distance > 2000){
						//总距离大于2km为拍照强制结束
						vibrateAndBeep();
						Toast.makeText(ZSLOfflineActivity.this, "由于您累计2KM未选择路径点，请结束本次交割并选择最近资源点！", Toast.LENGTH_SHORT).show();
						return;
					}
					dis2 += dis;
					//10
					if(dis2 < 15){
						//当两次定位距离小于10米时，丢弃本次数据do nothing,但是需要计算如总距离

					}else{
						if(dis > 50){
							dis2 = 0;
							//当在一个采集周期（2s）两点之间距离超过50米时应该是定位出现了大的偏移，所以丢弃掉这个点
						}else{
							distance += dis;
							totalDistance += dis;
							//
							recordAndPaintTrack(location);

							preLocation2 = lastLocation;
							//保存上次的位置信息
							preLocation = lastLocation;
							// 保存最新的位置信息
							lastLocation = location;
							dis = 0;
							dis2 = 0;
						}
					}

					if(distance>1000){//1000
						vibrateAndBeep();
						Toast.makeText(ZSLOfflineActivity.this, "请选择最近资源作为路径点！", Toast.LENGTH_SHORT).show();
					}

				}
				//	autoLoadAroundResource();
				//  return;
			}

		}
	}

	private void forceSubmit() {
		// 总距离大于10km强制结束
		vibrateAndBeep();
		Toast.makeText(ZSLOfflineActivity.this, "总距离已超过10KM,需要提交本次交割数据!", Toast.LENGTH_SHORT).show();
		//	onButtonFinishClick();
	}

	private void autoLoadAroundResource() {
		if (ZSLConst.curGpsLocation == null) {
			return;
		}
		if (curInterval >= AUTO_LOADAROUNDRESOURCE_INTERVAL) {
			curInterval=0;
			// 查询周边一定范围内的所有资源点
			new AroundResourceLineSearchTask(ZSLOfflineActivity.this,
					new LatLng(lastLocation.getLatitude(), lastLocation.getLongitude())).execute("");
		}else{
			curInterval += (span / 1000);
		}
	}

	/**
	 * 振动提醒选择附近资源
	 */
	private void vibrateAndBeep() {


		try{
			vibrator.vibrate(new long[] { 300, 500}, -1);//只振动一次，不重复振动

			NotificationManager mgr = (NotificationManager) context
					.getSystemService(Context.NOTIFICATION_SERVICE);
			Notification nt = new Notification();
			nt.defaults = Notification.DEFAULT_SOUND;
			int soundId = new Random(System.currentTimeMillis())
					.nextInt(Integer.MAX_VALUE);
			mgr.notify(soundId, nt);

		/*AudioManager audio = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
		int ringerMode = audio.getRingerMode();
		mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
		mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
			@Override
			public void onCompletion(MediaPlayer mp) {
				mp.seekTo(0);
			}
		});

		AssetFileDescriptor file = getResources().openRawResourceFd(R.raw.osmium);
		try {
			mediaPlayer.setDataSource(file.getFileDescriptor(),
			file.getStartOffset(), file.getLength());
			file.close();
//			mediaPlayer.setVolume(0.5f, 0.5f);
			mediaPlayer.prepare();
		} catch (IOException ioe) {
			mediaPlayer.release();
			mediaPlayer = null;
		}
		mediaPlayer.start();*/
		}catch(Exception e){

		}
	}

	private void animateMyToLocation() {
		if (lastLocation != null) {
			double lat = lastLocation.getLatitude();
			double lng = lastLocation.getLongitude();

			/*double lat =36.538765;
			double lng = 117.38041;*/

			MapStatusUpdate update = MapStatusUpdateFactory.newLatLngZoom(new LatLng(lat, lng),18f);
			mBaiduMap.animateMapStatus(update);
		}
	}

	@Override
	protected void onResume() {
		//TODO 设置标记，记录轨迹点
		inThisFace = true;
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
		EventBus.getDefault().unregister(this);
		ZSLConst.curGpsLocation = null;


		if(mediaPlayer != null){
			if(mediaPlayer.isPlaying()){
				mediaPlayer.stop();
			}
		}
		if (gps != null) {
			gps.closeLocation();
			gps = null;
		}
		super.onDestroy();
	}

	private void displayAroundResourcesOnMap(boolean append,
											 List<ResourceInfoBean> mResourceInfoBeanList) {
		if (append == false) // 覆盖展示
		{
			// 隐藏气泡，置选中的marker为空
			mBaiduMap.hideInfoWindow();
			curSelectedMarker = null;

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

//		BitmapDescriptor bdA = BitmapDescriptorFactory.fromResource(R.drawable.icon_gcoding);
		BaiduMap bm = mBaiduMap;
		resourceInfoBeanList.addAll(mResourceInfoBeanList);
		for (ResourceInfoBean bean : resourceInfoBeanList) {
			Marker mk = (Marker) bm.addOverlay(
					new MarkerOptions().icon(getResourceTypeIco(bean.getResourceType())).position(new LatLng(bean.getLatitude(), bean.getLongitude())));
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
			curSelectedMarker = null;

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

					mk = (Marker) mBaiduMap.addOverlay(new MarkerOptions().icon(getResourceTypeIco(bean.getStart().getResourceType()))
							.position(startLatLng));
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
					mk = (Marker) mBaiduMap.addOverlay(new MarkerOptions().icon(getResourceTypeIco(bean.getEnd().getResourceType()))
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
		/*if(mResourceLineBeanList.size()==0){
			ResourceLineBean re = new ResourceLineBean();
			ResourceInfoBean inf = new ResourceInfoBean();
			inf.setLatitude(36.661712809907145);
			inf.setLongitude(117.12291192629123);
			inf.setResourceName("测试");
			inf.setResourceType("管井");
			inf.setResourceID(1111);
			re.setStart(inf);
			mResourceLineBeanList.add(re);
		}*/
		displayAroundResourcesLineOnMap(true, mResourceLineBeanList);
	}

	@Subscriber(tag = ZSLConst.tag_onResourceInfoBeanList_get_ok)
	public void onResourceInfoBeanListSearchOK(
			List<ResourceInfoBean> mResourceInfoBeanList) {
		displayAroundResourcesOnMap(false, mResourceInfoBeanList);
		/*if(curStatus.equals(DeliveryStatus.ONGOING)){
			Toast.makeText(ZSLOfflineActivity.this, "请在地图上选择资源作为结束点", Toast.LENGTH_SHORT).show();
		}*/
	}

	@Override
	public boolean onMarkerClick(Marker arg0) {
		// 注意，当点击了某个marker时，不能直接认为该marker已被选中，只有弹出了小气泡时才可认为
		// 被选中了。

		Log.d("lixu", "---点击marker");
		ResourceInfoBean clickBean = markerMap.get(arg0);
		if(!isSelect){

//		Toast.makeText(getApplicationContext(), "curStatus="+curStatus+",arg0="+arg0+",clickBean="+clickBean.getResourceName(), Toast.LENGTH_SHORT).show();
			switch (curStatus) {
				case NOT_START:// 仅查看marker信息
					showInfoWindow(clickBean);
					curSelectedMarker = arg0;
					if(curSelectedMarker != null){
						resourceInfoBean = markerMap.get(curSelectedMarker);
					}
					break;
				case START:// 仅查看marker信息
					showInfoWindow(clickBean);
					curSelectedMarker = arg0;
					if(curSelectedMarker != null){
						resourceInfoBean = markerMap.get(curSelectedMarker);
					}
					break;
				case ONGOING:
					showInfoWindow(clickBean);
					curSelectedMarker = arg0;

					if (curSelectedMarker != null) {
						resourceInfoBean = markerMap.get(curSelectedMarker);
						ResourceInfoBean bean = markerMap.get(curSelectedMarker);
						showLocusSelectedDialog(bean);
					} else {

					}
					break;
				case OVER:
					showInfoWindow(clickBean);
					curSelectedMarker = arg0;
					if (curSelectedMarker != null) {
						resourceInfoBean = markerMap.get(curSelectedMarker);
						ResourceInfoBean bean = markerMap.get(curSelectedMarker);
						showEndDialog(bean);
					} else {

					}
					break;
				case ON_REPORT_ERROR:// 该种情况认为不可能在隐患上报界面点击某个marker
					break;
			}

		}else{
			Bundle bundle = new Bundle();
			bundle.putSerializable("gd", clickBean);
			Intent gIntent = new Intent();
			gIntent.putExtras(bundle);
			setResult(12,gIntent);
			finish();
		}
		// mBaiduMap.showInfoWindow(arg0);
		return true;
	}


	private void showEndDialog(final ResourceInfoBean bean) {
		String msg = "是否将 " + bean.getResourceName() + " 作为结束点？";
		AlertDialog ad;
		ad = new AlertDialog.Builder(context).setTitle("温馨提示").setMessage(msg)
				.setPositiveButton("是", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						PointlikeResourceInfoBean end = PointlikeResourceInfoBean.generateStartOrEndPoint(bean, routeID) ;
						if(mRouteInfoBean.getStartPosition().getResourceID().compareTo(end.getResourceID())==0){
							Toast.makeText(getApplicationContext(), "不允许选择同一个资源作为起点和终点,请重新选择", Toast.LENGTH_SHORT).show();
							mBaiduMap.hideInfoWindow();
							tvinfo.setVisibility(View.GONE);
							curSelectedMarker = null;
							return;
						}
						LatLng selectMarkerLocation = coverteToBaidu(bean.getLatitude(),bean.getLongitude());
						LatLng lastLocationLatLng = new LatLng(lastLocation.getLatitude(),lastLocation.getLongitude());
						if(DistanceUtil.getDistance(lastLocationLatLng, selectMarkerLocation)>190){
							Toast.makeText(getApplicationContext(), "无效的结束点!请选择80米范围内的资源作为结束点!", Toast.LENGTH_SHORT).show();
							mBaiduMap.hideInfoWindow();
							tvinfo.setVisibility(View.GONE);
							curSelectedMarker = null;
							return;
						}
						mRouteInfoBean.setEndPosition(end);
						takePhoto(ZSLConst.PHOTO_TYPE_END,bean.getResourceID(),bean.getResourceType());
					}
				}).setNegativeButton("否",  new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						mBaiduMap.hideInfoWindow();
						tvinfo.setVisibility(View.GONE);
						curSelectedMarker = null;
					}
				}).create();
		ad.show();
	}
	private void end(){
		Intent intent = new Intent(ZSLOfflineActivity.this,OfficeRouteSubmitActivity.class);
		intent.putExtra(RouteSubmitActivity.INTENT_DATA_FLAG, mRouteInfoBean);
		startActivityForResult(intent, REQUESTCODE_SUBMIT_ROUTE);
		// 设置标记，不再记录轨迹点
		inThisFace = false;


		if(mediaPlayer != null){
			if(mediaPlayer.isPlaying()){
				mediaPlayer.stop();
			}
		}

	}

	private void onButtonFinishClick() {
		animateMyToLocation();
		// 查询周边一定范围内的所有资源点
		/*new AroundResourceSearchTask(context, mapview,
				new LatLng(lastLocation.getLatitude(), lastLocation.getLongitude())).execute("");*/

		/*new AroundResourceLineSearchTask(ZSLOfflineActivity.this,
				new LatLng(lastLocation.getLatitude(), lastLocation.getLongitude())).execute("");*/


		getResourceLineBeanList(lastLocation.getLatitude(),lastLocation.getLongitude());
		//getResourceLineBeanList(38.04022825689031,114.61610688070446);

		Toast.makeText(ZSLOfflineActivity.this, "数据加载完成后请在地图上选择资源作为结束点", Toast.LENGTH_LONG).show();
	}

	/**
	 * 当点击了出发按钮时
	 */
	private void onButtonGoClick() {
		// 如果当前已存在选中的marker，则提示是否将该marker作为起始点
		// 否则，则提示先选择一个marker作为起始点
		if (curSelectedMarker != null) {
			ResourceInfoBean bean = markerMap.get(curSelectedMarker);
//			System.out.println("id="+bean.getResourceID());
			showStartGoDialog(bean);
		} else {
			new AlertDialog.Builder(context).setTitle("温馨提示").setMessage("请先点击定位加载资源并在地图上选择一个资源，再点击“开始”")
					.setPositiveButton("知道了", null).create().show();
		}
	}
	private PointlikeResourceInfoBean locusSelectedResource;
	/**
	 * 弹框确认是否将选中的点作为起始点
	 *
	 * @param bean
	 */
	private void showLocusSelectedDialog(final ResourceInfoBean bean) {
		String msg = "是否将 " + bean.getResourceName() + " 作为路径点？";
		AlertDialog ad;
		ad = new AlertDialog.Builder(context).setTitle("温馨提示").setMessage(msg)
				.setPositiveButton("是", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						if(mRouteInfoBean.getStartPosition().getResourceID().compareTo(bean.getResourceID())==0){
							Toast.makeText(getApplicationContext(), "已将此资源选定为起点,请重新选择", Toast.LENGTH_SHORT).show();
							mBaiduMap.hideInfoWindow();
							tvinfo.setVisibility(View.GONE);
							curSelectedMarker = null;
							return;
						}
						LatLng selectMarkerLocation = coverteToBaidu(bean.getLatitude(),bean.getLongitude());
						LatLng lastLocationLatLng = new LatLng(lastLocation.getLatitude(),lastLocation.getLongitude());
						if(DistanceUtil.getDistance(lastLocationLatLng, selectMarkerLocation)>190){
							Toast.makeText(getApplicationContext(), "无效的路径点!请选择80米范围内的资源作为路径点!", Toast.LENGTH_SHORT).show();
							mBaiduMap.hideInfoWindow();
							tvinfo.setVisibility(View.GONE);
							curSelectedMarker = null;
							return;
						}
						distance=0;
						locusSelectedResource = PointlikeResourceInfoBean.generatePointlikeResource(bean, routeID);
						takePhoto(ZSLConst.PHOTO_TYPE_WAY,bean.getResourceID(),bean.getResourceType());

					}
				}).setNegativeButton("否", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						mBaiduMap.hideInfoWindow();
						tvinfo.setVisibility(View.GONE);
						curSelectedMarker = null;
					}
				}).create();
		ad.show();
	}

	/**
	 * 弹框确认是否将选中的点作为起始点
	 *
	 * @param bean
	 */
	private void showStartGoDialog(final ResourceInfoBean bean) {
		String msg = "是否将 " + bean.getResourceName() + " 作为起始点？";
		AlertDialog ad;
		ad = new AlertDialog.Builder(context).setTitle("温馨提示").setMessage(msg)
				.setPositiveButton("是", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// 如果已申请过routeid则直接开始，不再重复申请（这种情况一般发生在申请了routeid但是没有成功开始，比如没有拍照并且在提示拍照时也没有拍照）
						/*if (ZSLConst.useFalseData) {
							routeID = 1;
						}*/
						LatLng selectMarkerLocation = coverteToBaidu(bean.getLatitude(),bean.getLongitude());
						LatLng lastLocationLatLng = new LatLng(lastLocation.getLatitude(),lastLocation.getLongitude());
						if(DistanceUtil.getDistance(lastLocationLatLng, selectMarkerLocation)>190){
							Toast.makeText(getApplicationContext(), "无效的起始点!请选择80米范围内的资源作为起始点!", Toast.LENGTH_SHORT).show();
							mBaiduMap.hideInfoWindow();
							tvinfo.setVisibility(View.GONE);
							curSelectedMarker = null;
							return;
						}
						if (routeID != 0) {
							start(bean);
							return;
						}
						requestRoute(bean);
					}
				}).setNegativeButton("否", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						mBaiduMap.hideInfoWindow();
						tvinfo.setVisibility(View.GONE);
						curSelectedMarker = null;
					}
				}).create();
		ad.show();
	}

	private void requestRoute(final ResourceInfoBean bean) {
		Message message = Message.obtain();
		int n=6;
		String cTime = String.valueOf(System.currentTimeMillis());
		String cTime_6 =cTime.substring(cTime.length()-n,cTime.length());

		routeID = Integer.parseInt(cTime_6);
		message.what = 1;
		message.obj = bean;
		handler.sendMessage(message);
		/*if (this.mProgress == null) {
			this.mProgress = ProgressDialog.show(this, "系统提示", "正在初始化...");
		} else {
			this.mProgress.setMessage("正在初始化...");
			this.mProgress.show();
		}
		new Thread() {
			@Override
			public void run() {

				final ArrayList<NameValuePair> list = new ArrayList<NameValuePair>();
				list.add((NameValuePair) new BasicNameValuePair("jsonRequest", ""));
				final String httpGetData = new httpconnect().httpGetData("pdaMainTask!getRouteInfo.interface", list, ZSLOfflineActivity.this);
				Log.d("AddrList==>", "result==>" + httpGetData);
				System.out.println("httpGetData="+httpGetData);
				if (httpGetData == null) {
					if ("".equals(httpGetData)) {
						return;
					}
				}
				try {
					final Message message = new Message();
					final JSONObject jsonObject = new JSONObject(httpGetData.toString());
					final String string = jsonObject.getString("info");
					if (jsonObject.getString("result").equals("0")) {
						message.what = 1;
						message.obj = bean;
						routeID = StringUtils.getInt(string);
						handler.sendMessage(message);
						return;
					}
					message.what = 2;
					message.obj = string;
					handler.sendMessage(message);
				} catch (JSONException ex) {
				}
			}
		}.start();*/
	}

	private void showInfoWindow(ResourceInfoBean bean) {
		tvinfo.setVisibility(View.VISIBLE);
		tvinfo.setText("当前资源: "+bean.getResourceName());
		LatLng latlng = new LatLng(bean.getLatitude(), bean.getLongitude());

		int padding = (int) ScreenUtils.dpToPx(context, 8f);
		TextView tv = new TextView(context);
		tv.setBackgroundResource(R.drawable.popup);
		// tv.setBackgroundColor(Color.BLACK);
		tv.setPadding(padding, padding, padding, (padding + 5));
		tv.setTextColor(Color.BLACK);
		tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 17f);
		tv.setText(bean.getResourceName());
		InfoWindow infoWindow = new InfoWindow(BitmapDescriptorFactory.fromView((View) tv), latlng, -50,
				new InfoWindow.OnInfoWindowClickListener() {
					@Override
					public void onInfoWindowClick() {
					}
				});
		mBaiduMap.showInfoWindow(infoWindow);
	}

	@Override
	public void onMapClick(LatLng arg0) {
		mBaiduMap.hideInfoWindow();
		tvinfo.setVisibility(View.GONE);
		curSelectedMarker = null;
	}

	@Override
	public boolean onMapPoiClick(MapPoi arg0) {
		return false;
	}


	public void onBackPressed() {
		if(!routeOperated){
		String msg = "传输交割尚未提交, 请先处理!";
		AlertDialog ad = new AlertDialog.Builder(context).setTitle("温馨提示").setMessage(msg)
				.setPositiveButton("处理", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				}).setNegativeButton("放弃", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
						// 清理本地和服务端生成的数据（本地缓存的数据和服务端生成的初始化数据如routeid），发起一个删除routeid的请求
						clearRoute();
						finish();
						reset();
					}
				}).create();
		ad.show();
		}else{
	        	super.onBackPressed();
		}

	}


	private void clearRoute() {
		/*if (this.mProgress == null) {
			this.mProgress = ProgressDialog.show(this, "系统提示", "正在初始化...");
		} else {
			this.mProgress.setMessage("正在初始化...");
			this.mProgress.show();
		}*/
		if(routeID==0){
			return;
		}
		new Thread() {

			@Override
			public void run() {
				final ArrayList<NameValuePair> list = new ArrayList<NameValuePair>();
				list.add((NameValuePair) new BasicNameValuePair("jsonRequest", "{\"routeid\":"+routeID+"}"));
				final String httpGetData = new httpconnect().httpGetData("pdaMainTask!clearRoute.interface", list, getApplicationContext());
				Log.d("AddrList==>", "result==>" + httpGetData);
				System.out.println("httpGetData="+httpGetData);
				if (httpGetData == null) {
					if ("".equals(httpGetData)) {
						return;
					}
				}
				routeID = 0;
				try {
					JSONObject jsonObject = new JSONObject(httpGetData);

					if (jsonObject != null) {

						if ("0".equals(jsonObject.getString("result"))) {

							deletePhotos(mRouteInfoBean);
//							finish();
						} else {
						}
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		}.start();
	}

	private void deletePhotos(RouteInfoBean routeInfoBean) {

		File file = null;
		if (routeInfoBean.getStartPosition() != null && routeInfoBean.getStartPosition().getPhotos() != null) {
			for (PhotoInfoBean photo : routeInfoBean.getStartPosition().getPhotos()) {
				file = new File(ZSLConst.photodir, photo.getPhotoName());
				if (file != null) {
					if (file.exists()) {
						file.delete();
					}
				}
			}
		}
		if (routeInfoBean.getEndPosition() != null && routeInfoBean.getEndPosition().getPhotos() != null) {
			for (PhotoInfoBean photo : routeInfoBean.getEndPosition().getPhotos()) {
				file = new File(ZSLConst.photodir, photo.getPhotoName());
				if (file != null) {
					if (file.exists()) {
						file.delete();
					}
				}
			}
		}

		if (routeInfoBean.getLocusResourcePosition() != null) {
			for (PointlikeResourceInfoBean pointlikeResourceInfoBean : routeInfoBean.getLocusResourcePosition()) {
				if (pointlikeResourceInfoBean == null) {
					continue;
				}
				for (PhotoInfoBean photo : pointlikeResourceInfoBean.getPhotos()) {
					file = new File(ZSLConst.photodir, photo.getPhotoName());
					if (file != null) {
						if (file.exists()) {
							file.delete();
						}
					}
				}
			}
		}
		/*if (routeInfoBean.getErrors() != null) {
			for (ErrorInfoBean error : routeInfoBean.getErrors()) {
				if (error.getErrorsPhotos() == null || error.getErrorsPhotos().size() == 0) {
					break;
				}
				for (PhotoInfoBean photo : error.getErrorsPhotos()) {
					file = new File(ZSLConst.photodir, photo.getPhotoName());
					if (file != null) {
						if (file.exists()) {
							file.delete();
						}
					}
				}
			}
		}*/
		mRouteInfoBean = null;
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

	// 将数据保存到outState对象中, 该对象会在重建activity时传递给onCreate方法
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putSerializable("state_route", mRouteInfoBean);
		outState.putBoolean("state_routeOperated", routeOperated);
		outState.putDouble("state_totalDistance", totalDistance);
		outState.putDouble("state_distance", distance);
		outState.putString("state_curStatus", curStatus.toString());
		outState.putSerializable("state_resourceInfoBeanList", resourceInfoBeanList);
//	    outState.putSerializable("state_markerMap", markerMap);
	}

	private void restoreState(Bundle state){
		mRouteInfoBean = (RouteInfoBean) state.getSerializable("state_route");
		routeOperated = state.getBoolean("state_routeOperated", routeOperated);
		totalDistance = state.getDouble("state_totalDistance", totalDistance);
		distance = state.getDouble("state_distance", distance);
		curStatus = DeliveryStatus.valueOf(state.getString("state_curStatus"));
		resourceInfoBeanList= (ArrayList<ResourceInfoBean>) state.getSerializable("state_resourceInfoBeanList" );
//		markerMap = (HashMap<Marker, ResourceInfoBean>) state.getSerializable("state_markerMap" );

		for (ResourceInfoBean bean : resourceInfoBeanList) {
			Marker mk = (Marker) mBaiduMap.addOverlay(
					new MarkerOptions().icon(getResourceTypeIco(bean.getResourceType())).position(new LatLng(bean.getLatitude(), bean.getLongitude())));
			markerMap.put(mk, bean);
		}

		displayLineOnMap(mRouteInfoBean.getStartPosition(),mRouteInfoBean.getEndPosition(),mRouteInfoBean.getLocusPoints());
	}

	private void displayLineOnMap(PointlikeResourceInfoBean start,PointlikeResourceInfoBean end,List<LocusPoint> mLocusPointList) {
		List<LatLng> arraylist = new ArrayList<LatLng>(mLocusPointList.size());
		arraylist.add(coverteToBaidu(start.getLatitude(), start.getLongitude()));
		for (LocusPoint locus : mLocusPointList) {
			arraylist.add(coverteToBaidu(locus.getLatitude(), locus.getLongitude()));
		}
		if(end!=null&&end.getLatitude()!=0&&end.getLongitude()!=0){
			arraylist.add(coverteToBaidu(end.getLatitude(), end.getLongitude()));
		}

		mBaiduMap.addOverlay((new PolylineOptions()).width(10).color(0xaa008000).points(arraylist));
	}


	/**
	 * 纠偏数据库(将当前百度坐标转换为GPS)
	 */
	private void callLocationProvider(){
		if(gps ==null){
			gps = new Gps(ZSLOfflineActivity.this);
		}
		new Thread(new Runnable() {
			@Override
			public void run() {
				if (gps != null) { // 当结束服务时gps为空
					// 获取经纬度
					if(ZSLConst.useGPS){
						location = gps.getLocation();
					}
//						System.out.println("gps获取到经纬度："+location);
						/*

						MyLog.i("loc", "gps获取到经纬度："+location);*/


					// 如果gps无法获取经纬度，改用百度坐标转换而来
					if (ZSLConst.curLocation != null) {

						// 2.把百度坐标通过纠偏数据库转换为gps坐标
						String key = ZSLConst.curLocation.getLatitude()+"-"+ZSLConst.curLocation.getLongitude();
						if(gpaCacheMap.containsKey(key)){
							location = gpaCacheMap.get(key);
							ZSLConst.curGpsLocation = location;
							return;
						}

						try {
							location = UtilTool.callGear(ZSLOfflineActivity.this, ZSLConst.curLocation.getLatitude(),
									ZSLConst.curLocation.getLongitude());
						} catch (Exception e) {
							location = null;
							e.printStackTrace();
						}
						if(location!=null){
							gpaCacheMap.put(key, location);
						}
					}

					if (location != null) {
						ZSLConst.curGpsLocation = location;
					}
				}
				return;
			}

		}).start();
	}
	ResourceInfoBean mTestBean;
	private Location location = null;
	private void getResourceLineBeanList(final double lat,final double lon){

		if (this.mProgress == null) {
			this.mProgress = ProgressDialog.show(this, "系统提示", "正在查询周边资源...");
			this.mProgress.setCanceledOnTouchOutside(true);
			this.mProgress.setCancelable(true);
		} else {
			this.mProgress.setMessage("正在查询周边资源...");
			this.mProgress.show();
		}
try {
	new Thread(new Runnable() {

		@Override
		public void run() {
			// TODO Auto-generated method stub

			Log.d("lixu", "查询资源中");

			List<ResourceInfoBean> resourceInfoBeanList = new ArrayList<ResourceInfoBean>();
			List<ResourceLineBean> resourceLineBeanList = new ArrayList<ResourceLineBean>();


			Double xMax = lon + (300.0 / 33 / 3600);
			Double xMin = lon - (300.0 / 33 / 3600);
			Double yMax = lat + (300.0 / 33 / 3600);
			Double yMin = lat - (300.0 / 33 / 3600);

			String ids = "";
			Map<Integer, ResourceInfoBean> map = new HashMap<Integer, ResourceInfoBean>();

			//查询周围点资源
			String mSq = "select int_id as resourceID,zh_label as resourceName,resource_type as resourceType,longitude,latitude, (case when is_pass='已巡检通过' then 0 when is_pass='已巡检未通过' then 1 else 0 end) isPass from RES_RESOURCE_POINT where longitude > " + xMin + " and longitude < " + xMax + " and latitude > " + yMin + " and latitude < " + yMax;
			Log.d("qqqqqqqq", "mSq=" + mSq);
			Cursor cur = db_line.rawQuery(mSq, null);
			while (cur.moveToNext()) {
				int mId = cur.getInt(cur.getColumnIndex("resourceID"));
				String mName = cur.getString(cur.getColumnIndex("resourceName"));
				String mType = cur.getString(cur.getColumnIndex("resourceType"));
				double mLon = cur.getDouble(cur.getColumnIndex("longitude"));
				double mLat = cur.getDouble(cur.getColumnIndex("latitude"));
				String mIsPass = cur.getString(cur.getColumnIndex("isPass"));

				Log.d("lixu", mLon + "查询经纬度==" + mLat);

				mTestBean = new ResourceInfoBean();
				mTestBean.setResourceID(mId);
				mTestBean.setResourceName(mName);
				mTestBean.setResourceType(mType);
				mTestBean.setLongitude(mLon);
				mTestBean.setLatitude(mLat);
				mTestBean.setIsPass(mIsPass);

				resourceInfoBeanList.add(mTestBean);

				//将int_id和对应的bean对象放入map 后面封装用到
				ids += mId + ",";
				map.put(mId, mTestBean);

				mTestBean = null;
			}

			if (ids != "") {
				ids = ids.substring(0, ids.length() - 1);
			} else {
				return;
			}
			//点对应的段信息
			String mSql_2 = "select id,zh_label,a_object_id,z_object_id,related_branch,type,c_length from wx_daiwei_jiaoge where 1 = 1 and a_object_id in (" + ids + ") "
					+ " union all "
					+ " select id,zh_label,a_object_id,z_object_id,related_branch,type,c_length from wx_daiwei_jiaoge where 1 = 1 and z_object_id in (" + ids + ") ";
			Log.d("qqqqq", "点对饮==" + mSql_2);
			cur = db_line.rawQuery(mSql_2, null);
			ResourceLineBean lineBean = null;

			int testNum = 1;
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
			Log.d("qqqqqqqq", "线个数==" + resourceLineBeanList.size());
			EventBus.getDefault().post(resourceLineBeanList, ZSLConst.tag_onResourceLineBeanList_get_ok);
		}
	}).start();


}catch (Exception e){
	Toast.makeText(ZSLOfflineActivity.this,"查询异常"+e.toString(),Toast.LENGTH_SHORT).show();
}

	}
	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		mLocationClient.stop();
	}
	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
		mLocationClient.start();
	}
}
