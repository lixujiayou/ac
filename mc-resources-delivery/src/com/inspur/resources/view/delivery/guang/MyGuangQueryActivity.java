package com.inspur.resources.view.delivery.guang;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.inspur.component.swipyrefreshlayout.SwipyRefreshLayout;
import com.inspur.component.swipyrefreshlayout.SwipyRefreshLayoutDirection;
import com.inspur.easyresources.R;
import com.inspur.resources.bean.QueryMyBean;
import com.inspur.resources.http.httpconnect;
import com.inspur.resources.view.delivery.transroute.RouteSubmitActivity;
import com.inspur.resources.view.delivery.transroute.bean.GuangBean;
import com.inspur.resources.view.delivery.transroute.bean.RouteInfoBean;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by lixu on 2017/6/20.
 */

public class MyGuangQueryActivity extends Activity implements View.OnClickListener{

    private EditText editText;
    private TextView tvTime;
    private Spinner spStatus;
    private SwipyRefreshLayout mSwipyRefreshLayout;
    private ListView listview;
    private DeliveryRouteAdapter mAdapter;
    private List<GuangBean> deliveryRouteList = new ArrayList<GuangBean>();
    private ProgressDialog mProgress;
    private Button btQuery;
    private ImageView ivCancle;

    private final static int LOAD_DATA = 1;
    private final static int LOAD_MORE = 2;
    private int mStatus = 0;//查询交割状态 0:通过  1：未通过  2：未交割
    private final static int REQUEST_CODE_FOREDIT = 1;
    private int curPage = 1;
    private List<String> strList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_query);
        initView();

        getData(LOAD_DATA,1,mStatus);
    }

    private void initView(){
        editText  = (EditText) findViewById(R.id.et_name);
        tvTime = (TextView) findViewById(R.id.tv_time);
        spStatus = (Spinner) findViewById(R.id.sp_query);
        mSwipyRefreshLayout = (SwipyRefreshLayout) findViewById(R.id.swipyrefreshlayout);
        listview = (ListView) findViewById(R.id.listView);
        btQuery = (Button) findViewById(R.id.bt_query);
        ivCancle = (ImageView) findViewById(R.id.iv_cancle);
        btQuery.setOnClickListener(this);
        ivCancle.setOnClickListener(this);



        mAdapter = new DeliveryRouteAdapter(MyGuangQueryActivity.this, deliveryRouteList);
        listview.setAdapter(mAdapter);
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


        strList.add("通过");
        strList.add("未通过");
        strList.add("未交割");


        ArrayAdapter<CharSequence> jjcdAdapter2 = ArrayAdapter.createFromResource(MyGuangQueryActivity.this, R.array.query2,
                android.R.layout.simple_dropdown_item_1line);
        spStatus.setAdapter(jjcdAdapter2);
        spStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d("qqqqq","ii===="+i);
                mStatus= i ;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        tvTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar c = Calendar.getInstance();
                // 直接创建一个DatePickerDialog对话框实例，并将它显示出来
                new DatePickerDialog(MyGuangQueryActivity.this,
                        // 绑定监听器
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                int mMonth = monthOfYear + 1;
                                int mDay = dayOfMonth;
                                if(mMonth < 10){
                                    if(mDay < 10){
                                        tvTime.setText(year+"-0"+mMonth+"-0"+dayOfMonth);

                                    }else{
                                        tvTime.setText(year+"-0"+mMonth+"-"+dayOfMonth);

                                    }

                                }else{
                                    if(mDay < 10) {
                                        tvTime.setText(year + "-" + mMonth + "-0" + dayOfMonth);
                                    }else{
                                        tvTime.setText(year + "-" + mMonth + "-" + dayOfMonth);

                                    }
                                }
                            }
                        }
                        // 设置初始日期
                        , c.get(Calendar.YEAR), c.get(Calendar.MONTH), c
                        .get(Calendar.DAY_OF_MONTH)).show();
            }
        });

    }


    private String str;
    private String mTime;
    private void getData(final int action, final int page, final int status) {
        if (this.mProgress == null) {
            this.mProgress = ProgressDialog.show(this, "系统提示", "正在加载数据...");
            this.mProgress.setCanceledOnTouchOutside(true);
        } else {
            this.mProgress.setMessage("正在加载数据...");
            this.mProgress.show();
        }

        str = editText.getText().toString();
        mTime = tvTime.getText().toString();
        if(mTime==null || mTime.equals("点击选择")){
            mTime = "";
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
                        MyGuangQueryActivity.this);


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
                    Toast.makeText(MyGuangQueryActivity.this, msg.obj.toString(), Toast.LENGTH_SHORT).show();
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
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.bt_query:
                curPage = 1;
                getData(LOAD_DATA,curPage,mStatus);
                break;
            case R.id.iv_cancle:
                finish();
                break;

        }
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


                tag.iv_info.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(MyGuangQueryActivity.this, GuangSubmitActivity.class);
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
}
