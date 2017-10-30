package com.inspur.resources.view.delivery.transroute;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

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
import com.inspur.resources.view.delivery.transroute.bean.ErrorInfoBean;
import com.inspur.resources.view.delivery.transroute.bean.GdErrorInfoBean;
import com.inspur.resources.view.delivery.transroute.bean.LocusPoint;
import com.inspur.resources.view.delivery.transroute.bean.PointlikeResourceInfoBean;
import com.inspur.resources.view.delivery.transroute.bean.RouteInfoBean;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MyDeliveryRouteListActivity extends BaseActivity implements OnMarkerClickListener, OnMapClickListener {

	private final static int REQUEST_CODE_FOREDIT = 1;
	private TextView tvSearch;
	private EditText etSearch;
	private LinearLayout llSearch;
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


						Log.i("lixu", "-----"+msg.obj.toString());

						Log.i("lixu", "开始解析");
						Type type = new TypeToken<List<RouteInfoBean>>() {}.getType();
						Log.i("lixu", "开始解析2");
						Gson gson = new Gson();
						Log.i("lixu", "开始解析3");
						list = gson.fromJson(msg.obj.toString(), type);
						Log.i("lixu", "开始解析4");
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
					Toast.makeText(MyDeliveryRouteListActivity.this, msg.obj.toString(), Toast.LENGTH_SHORT).show();
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
		tvSearch = (TextView) findViewById(R.id.tv_search);
		etSearch = (EditText) findViewById(R.id.et_search);
		llSearch = (LinearLayout) findViewById(R.id.ll_search);

		tvSearch.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				getData(LOAD_DATA,curPage,mStatus);
			}
		});


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
		mAdapter = new DeliveryRouteAdapter(MyDeliveryRouteListActivity.this, deliveryRouteList);
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

//				//管道故障点
//				if(curRouteInfoBean.getErrors() != null && curRouteInfoBean.getErrors().size() > 0){
//					displayGdLineOnMap(curRouteInfoBean.getErrors());
//				}
//
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

		if(arraylist!=null&&arraylist.size()>0){
			mapview.getMap().addOverlay((new PolylineOptions()).width(10).color(color).points(arraylist));
		}
	}
	private String str;
	private void getData(final int action,final int page,final int status) {
		if (this.mProgress == null) {
			this.mProgress = ProgressDialog.show(this, "系统提示", "正在加载数据...");
			this.mProgress.setCanceledOnTouchOutside(true);
		} else {
			this.mProgress.setMessage("正在加载数据...");
			this.mProgress.show();
		}

		str = etSearch.getText().toString();
		if(llSearch.getVisibility() == View.GONE){
			str = "";
		}
		new Thread() {
			@Override
			public void run() {
				final ArrayList<NameValuePair> list = new ArrayList<NameValuePair>();

				String json = "{\"page\":"+page+",\"pageSize\":"+10+",\"status\":"+status+",\"name\":\""+str+"\"}";
				list.add((NameValuePair)new BasicNameValuePair("jsonRequest", json));

				final String httpGetData = new httpconnect().httpGetData("pdaMainTask!getMyRoute.interface", list,
						MyDeliveryRouteListActivity.this);

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
						Intent intent = new Intent(MyDeliveryRouteListActivity.this, RouteSubmitActivity.class);
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
		if(requestCode == 21){

			mapview.getMap().clear();
			getData(LOAD_DATA,curPage,mStatus);

		}else{

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
			}else if(resultCode == 66){
				if(data == null){
					return;
				}
				mapview.getMap().clear();
				curRouteInfoBean = (RouteInfoBean) data.getSerializableExtra("bean");
				// 在地图展示轨迹和隐患点、起始点、终点
				if(curRouteInfoBean.getStartPosition() != null){
					displayResourcesOnMap(curRouteInfoBean.getStartPosition(), curRouteInfoBean.getEndPosition());

				}
				if (curRouteInfoBean.getErrors() != null && curRouteInfoBean.getErrors().size() > 0) {
					displayErrorResourcesOnMap(curRouteInfoBean.getErrors());
				}

//				//管道故障点
//				if(curRouteInfoBean.getErrors() != null && curRouteInfoBean.getErrors().size() > 0){
//					displayGdLineOnMap(curRouteInfoBean.getErrors());
//				}
//
				if(curRouteInfoBean.getStartPosition() != null){
					displayLineOnMap(curRouteInfoBean.getStartPosition(),curRouteInfoBean.getEndPosition(),curRouteInfoBean.getLocusPoints(), curRouteInfoBean.getDeliveryState());
				}


				String type = data.getExtras().getString("type");
				//0:通过  1：未通过  2：未交割

				if(type.equals("通过")){
					mStatus = 0;
				}else if(type.equals("不通过")){
					mStatus = 1;
				}else{
					mStatus = 2;
				}
				getData(LOAD_DATA,1,mStatus);

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
			Intent intent = new Intent(MyDeliveryRouteListActivity.this, HiddenTroubleReportActivity2.class);
			intent.putExtra("isUpdate", true);
			intent.putExtra("type", ApplicationValue.single_list[0]);
			intent.putExtra("routeID", error.getRouteID());
			intent.putExtra(HiddenTroubleReportActivity2.INTENT_DATA_FLAG, error);
			startActivityForResult(intent, 21);

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
			case R.id.action_location:
				Intent gIntent = new Intent(MyDeliveryRouteListActivity.this,MyQueryActivity.class);
				startActivityForResult(gIntent,1);
				/*if(llSearch.getVisibility() == View.VISIBLE){
					llSearch.setVisibility(View.GONE);
				}else{
					llSearch.setVisibility(View.VISIBLE);
				}*/
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
	protected void onStop() {
		super.onStop();
		mLocationClient.stop();

	}

	@Override
	protected void onDestroy() {
		mLocationClient.stop();
		mapview.onDestroy();
		super.onDestroy();
	}



}
