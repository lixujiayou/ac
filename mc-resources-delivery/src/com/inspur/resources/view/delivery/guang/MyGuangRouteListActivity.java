package com.inspur.resources.view.delivery.guang;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

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
import com.inspur.resources.bean.QueryMyBean;
import com.inspur.resources.http.httpconnect;
import com.inspur.resources.view.delivery.transroute.MyDeliveryRouteListActivity;
import com.inspur.resources.view.delivery.transroute.MyQueryActivity;
import com.inspur.resources.view.delivery.transroute.bean.ErrorInfoBean;
import com.inspur.resources.view.delivery.transroute.bean.GuangBean;
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

public class MyGuangRouteListActivity extends BaseActivity implements OnMarkerClickListener, OnMapClickListener {

	private final static int REQUEST_CODE_FOREDIT = 1;


	private TextView tvSearch;
	private EditText etSearch;
	private LinearLayout llSearch;

	private String mTime;

	private SwipyRefreshLayout mSwipyRefreshLayout;
	private ListView listview;
	private DeliveryRouteAdapter mAdapter;
	private List<GuangBean> deliveryRouteList = new ArrayList<GuangBean>();
	private ProgressDialog mProgress;
	private GuangBean curRouteInfoBean;
	private boolean firstLoc = true;
	private int mStatus = 0;//查询交割状态 0:通过  1：未通过  2：未交割

	private final static int LOAD_DATA = 1;
	private final static int LOAD_MORE = 2;

	private int curPage = 1;

	private Handler handler = new Handler(new Handler.Callback() {

		public boolean handleMessage(Message msg) {
			if (mProgress != null) {
				mProgress.dismiss();
			}
			switch (msg.what) {
				case LOAD_DATA:
					deliveryRouteList.clear();
					List<GuangBean> list = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss Z").create()
							.fromJson(msg.obj.toString(), new TypeToken<List<GuangBean>>() {
							}.getType());
					deliveryRouteList.addAll(list);
					mAdapter.notifyDataSetChanged();
					mSwipyRefreshLayout.setRefreshing(false);
					break;
				case LOAD_MORE:
					List<GuangBean> list2 = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss Z").create()
							.fromJson(msg.obj.toString(), new TypeToken<List<GuangBean>>() {
							}.getType());
					for (GuangBean routeInfoBean : list2) {
						if (!deliveryRouteList.contains(routeInfoBean))
							deliveryRouteList.add(routeInfoBean);
					}
					mAdapter.notifyDataSetChanged();
					mSwipyRefreshLayout.setRefreshing(false);
					break;
				case -1:
					deliveryRouteList.clear();
					mAdapter.notifyDataSetChanged();
					mSwipyRefreshLayout.setRefreshing(false);
					Toast.makeText(MyGuangRouteListActivity.this, msg.obj.toString(), Toast.LENGTH_SHORT).show();
					break;
				default:
					deliveryRouteList.clear();
					mAdapter.notifyDataSetChanged();
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

		mapview = (MapView) findViewById(R.id.mapview);
		mapview.showZoomControls(false);
		mapview.getMap().setOnMarkerClickListener(this);
		mapview.getMap().setOnMapClickListener(this);


		tvSearch.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				getData(LOAD_DATA,curPage,mStatus);
			}
		});

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
		mAdapter = new DeliveryRouteAdapter(MyGuangRouteListActivity.this, deliveryRouteList);
		listview.setAdapter(mAdapter);
		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				mapview.getMap().clear();
				Log.d("lixu", "开始大点");
				curRouteInfoBean = deliveryRouteList.get(position);
				// 在地图展示轨迹和隐患点、起始点、终点
				try{
					Marker mk = (Marker) mapview.getMap().addOverlay(
							new MarkerOptions().icon(getResourceTypeIco(curRouteInfoBean.getYhType())).position(new LatLng(Double.valueOf(curRouteInfoBean.getLatitude()), Double.valueOf(curRouteInfoBean.getLongitude()))));
					MapStatusUpdate update = MapStatusUpdateFactory.newLatLngZoom(new LatLng(Double.valueOf(curRouteInfoBean.getLatitude()), Double.valueOf(curRouteInfoBean.getLongitude())),18f);
					mapview.getMap().animateMapStatus(update);

				}catch(Exception e){

					Log.d("lixu", "大点失败"+e.toString());
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
	BitmapDescriptor bdgenerator;
	BitmapDescriptor bdocc;
	BitmapDescriptor bdpole;
	BitmapDescriptor bdwell;
	BitmapDescriptor bdstation;
	BitmapDescriptor bdsuppPoint;
	BitmapDescriptor bdA;
	private BitmapDescriptor getResourceTypeIco(String type){
		if("否".equals(type)){
			if(bdgenerator==null){
				bdgenerator = BitmapDescriptorFactory.fromResource(R.drawable.icon_gcoding_generator);
			}
			return bdgenerator;
		}else if("是".equals(type)){
			if(bdpole==null){
				bdpole = BitmapDescriptorFactory.fromResource(R.drawable.icon_gcoding_pole);
			}
			return bdpole;
		}else{
			if(bdwell==null){
				bdwell = BitmapDescriptorFactory.fromResource(R.drawable.icon_gcoding_well);
			}
			return bdwell;
		}
	}
	private void initLocation() {
		LocationClientOption option = new LocationClientOption();
		option.setLocationMode(LocationMode.Hight_Accuracy);// 可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
		option.setCoorType("bd09ll");// 可选，默认gcj02，设置返回的定位结果坐标系
		option.setIsNeedAddress(true);// 可选，设置是否需要地址信息，默认不需要
		option.setOpenGps(true);// 可选，默认false,设置是否使用gps
		option.setLocationNotify(true);// 可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
		option.setIsNeedLocationDescribe(true);// 可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
		option.setIsNeedLocationPoiList(true);// 可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
		option.setIgnoreKillProcess(false);// 可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
		option.SetIgnoreCacheException(false);// 可选，默认false，设置是否收集CRASH信息，默认收集
		option.setEnableSimulateGps(false);// 可选，默认false，设置是否需要过滤gps仿真结果，默认需要
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
			mapview.getMap().addOverlay((new PolylineOptions()).width(10).color(color).points(arraylist));}
	}
	private String str;
	private void getData(final int action, final int page, final int status) {
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

			//	String json = "{\"page\":"+page+",\"pageSize\":"+10+",\"gjName\":\""+str+"\"}";

				QueryMyBean queryMyBean = new QueryMyBean();
				queryMyBean.setName(str);
				queryMyBean.setcTime(mTime);
				queryMyBean.setStatus(status);
				queryMyBean.setPage(page);
				queryMyBean.setPageSize(10);
				Gson gson = new Gson();
				String json = gson.toJson(queryMyBean);


				list.add((NameValuePair)new BasicNameValuePair("jsonRequest", json));

				final String httpGetData = new httpconnect().httpGetData("pdaMainTask!getMyGjInfo.interface", list,
						MyGuangRouteListActivity.this);


				Log.d("AddrList==>", "result==>" + httpGetData);
				System.out.println("result==>" + httpGetData);
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
						message.what = action;
						message.obj = string;
						handler.sendMessage(message);
						return;
					}
					message.what = -1;
					message.obj = string;
					handler.sendMessage(message);
				} catch (JSONException ex) {
				}
			}
		}.start();
	}


	public class DeliveryRouteAdapter extends BaseAdapter {
		private Context mContext;
		private List<GuangBean> mList;

		private DeliveryRouteAdapter(Context context, List<GuangBean> list) {
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
				final GuangBean routeInfoBean = mList.get(n);

				tag.name.setText(routeInfoBean.getGjName());

				if (routeInfoBean.getJgStatus() != null) {

					tag.state.setText(routeInfoBean.getJgStatus());
				}else{
					tag.name.setText("");
				}
				tag.time.setText(routeInfoBean.getCzDate());


				tag.iv_info.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						Intent intent = new Intent(MyGuangRouteListActivity.this, GuangSubmitActivity.class);
						intent.putExtra(GuangSubmitActivity.INTENT_DATA_FLAG, routeInfoBean);
						intent.putExtra("canEdit", true);
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
				//	RouteInfoBean routeInfoBean = (RouteInfoBean) data.getSerializableExtra("route");
				/*if(routeInfoBean==null){
					return;
				}*/
				RouteInfoBean route;
				/*for(int i=deliveryRouteList.size()-1;i>=0;i--){
					route = deliveryRouteList.get(i);
					if(route.getRouteID().compareTo(routeInfoBean.getRouteID())==0){
						deliveryRouteList.get(i).setRouteState(routeInfoBean.getRouteState());
						deliveryRouteList.get(i).setMatchScores(routeInfoBean.getMatchScores());
						deliveryRouteList.get(i).setDeliveryState(routeInfoBean.getDeliveryState());
						mAdapter.notifyDataSetChanged();
						break;
					}
				}*/
			}
		}
	}

	@Override
	public void onMapClick(LatLng arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean onMapPoiClick(MapPoi arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onMarkerClick(Marker marker) {
		/*if (pointMarkerMap != null && pointMarkerMap.containsKey(marker)) {
			PointlikeResourceInfoBean point = pointMarkerMap.get(marker);
		} else if (errorMarkerMap != null && errorMarkerMap.containsKey(marker)) {
			ErrorInfoBean error = errorMarkerMap.get(marker);
			Intent intent = new Intent(MyGuangRouteListActivity.this, HiddenTroubleReportActivity2.class);
			intent.putExtra("isUpdate", true);
			intent.putExtra("routeID", error.getRouteID());
			intent.putExtra(HiddenTroubleReportActivity2.INTENT_DATA_FLAG, error);
			startActivity(intent);
		}*/
		return false;
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
		//mLocationClient.stop();
		mapview.onDestroy();
		super.onDestroy();
	}




	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_type2, menu);
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

			case R.id.action_location:
				Intent gIntent = new Intent(MyGuangRouteListActivity.this,MyGuangQueryActivity.class);
				startActivity(gIntent);
				/*if(llSearch.getVisibility() == View.VISIBLE){
					llSearch.setVisibility(View.GONE);
				}else{
					llSearch.setVisibility(View.VISIBLE);
				}*/
				break;
		}
		return super.onOptionsItemSelected(item);
	}



}
