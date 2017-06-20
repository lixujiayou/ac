package com.inspur.resources.view.delivery.offline;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import cn.trinea.android.common.util.ToastUtils;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.offline.MKOLSearchRecord;
import com.baidu.mapapi.map.offline.MKOLUpdateElement;
import com.baidu.mapapi.map.offline.MKOfflineMap;
import com.baidu.mapapi.map.offline.MKOfflineMapListener;
import com.baidu.mapapi.model.LatLng;
import com.inspur.easyresources.R;
import com.inspur.resources.base.BaseActivity;
import com.inspur.resources.view.delivery.transroute.AroundResourceLineSearchTask;
import com.inspur.resources.view.delivery.transroute.ZSLConst;
import com.inspur.resources.view.delivery.transroute.ZSLTransmissionLine;
import com.inspur.resources.view.delivery.transroute.bean.ResourceLineBean;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.simple.eventbus.Subscriber;

/* 此Demo用来演示离线地图的下载和显示 */
@SuppressLint("Override") public class MainOfflineActivity extends BaseActivity implements MKOfflineMapListener,OnClickListener {

    /**
     * 下载资源相关
     */
    private DownLoadCompleteReceiver ompleteReceiver;
    private static int REQUESTPERMISSION = 110 ;
    private final static int WRITE_EXTERNAL_STORAGE_CODE = 102;
    private static final int UPDATECODE = 41;
    private static final int UPDATEPROGRESS = 42;
    private static final int UPDATEINSTALL = 44;
    private static final int NOUPDATECODE = 99;

    //资源下载路径
    private String mDowznUrl_line = "";
    //   private String mDowznUrl_route = "";
    //文件分隔符
    public static final String FILE_SEPARATOR = "/";
    // 外存sdcard存放路径
    public static final String FILE_PATH = Environment.getExternalStorageDirectory()+ FILE_SEPARATOR+"inspur" + FILE_SEPARATOR;
    public static final String FILE_NAME_line = "test.db";
    public static final String FILE_NAME_ROUTE = "route.db";  //我的
    public static final String FILE_NAME_WANG = "wang.db";  //查询网元的数据库


    private MKOfflineMap mOffline = null;
    private int cityNum = 288;//288济南  150石家庄
    private String cityName = "济南";
    private boolean isDowning;//是否正在下载
    private boolean isExist;//是否已经下载

    /**
     * 已下载的离线地图信息列表f
     */
    private ArrayList<MKOLUpdateElement> localMapList = null;

    private LinearLayout ll_down;
    private TextView tv_pro;//进度
    private TextView tv_remove;//
    private Button bt_download_re;//下载资源
    private ProgressBar mPb;//
    private EditText etName;
    private Button btResRemove;//删除资源

    /**
     * 济南浪潮经纬度
     * 测试
     */
    private double mLat = 36.66166108356194;
    private double mLon = 117.12358688641461;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resource_download);
        // 获取已下过的离线地图信息
        initView();
        mOffline = new MKOfflineMap();
        mOffline.init(this);

        localMapList = mOffline.getAllUpdateInfo();
        if (localMapList == null) {
            localMapList = new ArrayList<MKOLUpdateElement>();
            isExist = false;
            tv_pro.setText("点击下载");
        }else{
            isExist = true;
            tv_pro.setText("已下载");
        }



        ompleteReceiver = new DownLoadCompleteReceiver();
        /** register download success broadcast **/
        registerReceiver(ompleteReceiver,
                new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));


        File dir = new File(FILE_PATH);

        if (!dir.exists()) {
            dir.mkdirs();
        }


        /*File file = new File(FILE_PATH,FILE_NAME_line);
        if (!file.exists()) {
        	btResRemove.setEnabled(false);
        }else{
        	btResRemove.setEnabled(true);
        }*/
    }

    private void initView() {
        setTitle("离线资源下载");
        tv_pro = (TextView) findViewById(R.id.ratio);
        tv_remove = (TextView) findViewById(R.id.remove);
        bt_download_re = (Button) findViewById(R.id.bt_download_re);

        ll_down = (LinearLayout) findViewById(R.id.ll_down);
        mPb = (ProgressBar) findViewById(R.id.pb_download);
        etName = (EditText) findViewById(R.id.et_name);
        btResRemove = (Button) findViewById(R.id.bt_res_remove);

        bt_download_re.setOnClickListener(this);
        btResRemove.setOnClickListener(this);

        ll_down.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isExist){
                    if(isDowning){
                        mOffline.pause(cityNum);
                    }else{
                        mOffline.start(cityNum);
                    }
                }else{
                    Intent gIntent = new Intent(MainOfflineActivity.this,ZSLOfflineActivity.class);
                    startActivity(gIntent);
                }
            }
        });

        tv_remove.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if(isDowning){
                    mOffline.pause(cityNum);
                }
                mOffline.remove(cityNum);
                Toast.makeText(MainOfflineActivity.this, "删除离线地图成功", Toast.LENGTH_SHORT).show();

            }
        });

    }

    /**
     * 开始下载
     *
     */
    /*public void start(View view) {
        int cityid = Integer.parseInt(cidView.getText().toString());
        mOffline.start(cityid);
        clickLocalMapListButton(null);
        Toast.makeText(this, "开始下载离线地图. cityid: " + cityid, Toast.LENGTH_SHORT)
                .show();
        updateView();
    }*/


    @Override
    protected void onPause() {
        MKOLUpdateElement temp = mOffline.getUpdateInfo(cityNum);
        if (temp != null && temp.status == MKOLUpdateElement.DOWNLOADING) {
            mOffline.pause(cityNum);
        }
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public String formatDataSize(int size) {
        String ret = "";
        if (size < (1024 * 1024)) {
            ret = String.format("%dK", size / 1024);
        } else {
            ret = String.format("%.1fM", size / (1024 * 1024.0));
        }
        return ret;
    }

    @Override
    protected void onDestroy() {
        /**
         * 退出时，销毁离线地图模块
         */
        mOffline.destroy();
        super.onDestroy();
    }

    @Override
    public void onGetOfflineMapState(int type, int state) {
        switch (type) {
            case MKOfflineMap.TYPE_DOWNLOAD_UPDATE: {
                MKOLUpdateElement update = mOffline.getUpdateInfo(state);
                // 处理下载进度更新提示
                if (update != null) {
                    tv_pro.setText(String.format("%s : %d%%", update.cityName,
                            update.ratio));

                    if(update.ratio == 100){
                        isDowning = false;
                        isExist = true;
                    }

                }
            }
            break;
            case MKOfflineMap.TYPE_NEW_OFFLINE:
                // 有新离线地图安装
                Log.d("OfflineDemo", String.format("add offlinemap num:%d", state));
                break;
            case MKOfflineMap.TYPE_VER_UPDATE:
                // 版本更新提示
                // MKOLUpdateElement e = mOffline.getUpdateInfo(state);
                break;
            default:
                break;
        }

    }

    /**
     * 离线地图管理列表适配器
     */
    public class LocalMapAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return localMapList.size();
        }

        @Override
        public Object getItem(int index) {
            return localMapList.get(index);
        }

        @Override
        public long getItemId(int index) {
            return index;
        }

        @Override
        public View getView(int index, View view, ViewGroup arg2) {
            MKOLUpdateElement e = (MKOLUpdateElement) getItem(index);
            view = View.inflate(MainOfflineActivity.this,
                    R.layout.offline_localmap_list, null);
            initViewItem(view, e);
            return view;
        }

        void initViewItem(View view, final MKOLUpdateElement e) {
            Button remove = (Button) view.findViewById(R.id.remove);
            TextView title = (TextView) view.findViewById(R.id.title);
            TextView update = (TextView) view.findViewById(R.id.update);
            TextView ratio = (TextView) view.findViewById(R.id.ratio);
            ratio.setText(e.ratio + "%");
            title.setText(e.cityName);
            if (e.update) {
                update.setText("可更新");
            } else {
                update.setText("最新");
            }

            remove.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View arg0) {
                    mOffline.remove(e.cityID);
                    //      updateView();
                }
            });
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //下载区县资源
            case R.id.bt_download_re:
                File file1 = new File(FILE_PATH,FILE_NAME_line);
                if(file1.exists()){
                    file1.delete();
                }


                downResource();

                break;

            case R.id.bt_res_remove:

                File file = new File(FILE_PATH,FILE_NAME_line);
                file.delete();



                btResRemove.setEnabled(false);
                ToastUtils.show(MainOfflineActivity.this, "删除成功");
                break;
            default:
                break;
        }
    }


    /**
     * 下载广播接收
     */
    private class DownLoadCompleteReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getAction().equals(DownloadManager.ACTION_DOWNLOAD_COMPLETE)){
                //long id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
                //下载完成
                btResRemove.setEnabled(true);

            }else if(intent.getAction().equals(DownloadManager.ACTION_NOTIFICATION_CLICKED)){
            }
        }
    }
    /**
     * 权限回调
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUESTPERMISSION) {
            if (permissions[0].equals(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    if(ContextCompat.checkSelfPermission(MainOfflineActivity.this,Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
                        ActivityCompat.requestPermissions(MainOfflineActivity.this
                                , new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}
                                , WRITE_EXTERNAL_STORAGE_CODE);
                        Toast.makeText(MainOfflineActivity.this, "同意权限才能进行以下操作", Toast.LENGTH_SHORT).show();
                    }else{
                        downResource();

                    }
                } else {
                    //拒绝权限
                    Toast.makeText(MainOfflineActivity.this, "无权限，下载失败", Toast.LENGTH_SHORT).show();
                }
            }
        }

        if (requestCode == WRITE_EXTERNAL_STORAGE_CODE) {
            if (permissions[0].equals(Manifest.permission.READ_EXTERNAL_STORAGE)) {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(MainOfflineActivity.this, "已同意权限，开始安装", Toast.LENGTH_SHORT).show();
                    downResource();

                } else {
                    Toast.makeText(MainOfflineActivity.this, "无权限，下载失败", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }



    /**
     * 下载区县资源
     */
    private void downResource(){
        Toast.makeText(this, "开始下载，请注意通知栏", Toast.LENGTH_SHORT).show();
        File dir = new File(FILE_PATH);

        if (!dir.exists()) {
            dir.mkdirs();
        }

        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(mDowznUrl_line));
        //设置在什么网络情况下进行下载
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI);
        //设置通知栏标题

        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE);
        request.setTitle("代维交割");
        request.setAllowedNetworkTypes((DownloadManager.Request.NETWORK_MOBILE | DownloadManager.Request.NETWORK_WIFI));
        request.setAllowedOverRoaming(true);
        request.setDescription("");
        request.setAllowedOverRoaming(false);
        //设置文件存放目录
        request.setDestinationInExternalPublicDir("inspur", FILE_SEPARATOR + FILE_NAME_line);
        DownloadManager downManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
        downManager.enqueue(request);
    }


}