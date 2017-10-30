package com.inspur.resources.view.delivery.offline;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import cn.trinea.android.common.util.PreferencesUtils;
import cn.trinea.android.common.util.ToastUtils;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.baidu.mapapi.map.offline.MKOLUpdateElement;
import com.baidu.mapapi.map.offline.MKOfflineMap;
import com.baidu.mapapi.map.offline.MKOfflineMapListener;
import com.inspur.common.RegionWheelDialog;
import com.inspur.easyresources.R;
import com.inspur.resources.base.BaseActivity;
import com.inspur.resources.http.MallRequest;
import com.inspur.resources.utils.ApplicationValue;
import com.inspur.widget.FileUtils;
import com.inspur.widget.HttpCallBack;
import com.inspur.widget.converter.ServiceGenerator;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import org.wlf.filedownloader.FileDownloader;

/* 此Demo用来演示离线地图的下载和显示 */
@SuppressLint("Override") public class MainOfflineActivity extends BaseActivity implements MKOfflineMapListener,OnClickListener {
    private final static int REQUESTCODE_GET_AREA = 1;
    private final static int REQUESTCODE_GET_RESOURCE = 2;
    private final static int REQUESTCODE_LOCATION = 3;
    private static final int REQUESTCODE_TAKE_PHOTO = 4;
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
    private String mDowznUrl_line = ApplicationValue.url+"pdaMainTask!getResourceDb.interface";
    private String mDowznUrl_r = ApplicationValue.url+"pdaMainTask!getRouteDb.interface";
    //private String mDowznUrl_line = "http://mapopen-pub-androidsdk.bj.bcebos.com/map/BaiduMap_AndroidSDK_v4.4.0_Sample_demo.zip";
    private String mDowznUrl_route = "";

    //文件分隔符
    public static final String FILE_SEPARATOR = "/";
    // 外存sdcard存放路径
    public static final String FILE_PATH = Environment.getExternalStorageDirectory()+ FILE_SEPARATOR+"inspur" + FILE_SEPARATOR;
    public static final String FILE_NAME_line = "test.db";
    public static final String FILE_NAME_ROUTE = "route.db";  //我的
    public static final String FILE_NAME_WANG = "wang.db";  //查询网元的数据库

    protected Button bt_start;

    private MKOfflineMap mOffline = null;
    private int cityNum = 12;//288济南  150石家庄  12河北省
    private String cityName = "石家庄";
    private boolean isDowning;//是否正在下载
    private boolean isExist;//是否已经下载

    private String  cCityId = "";
    private String cAreaId = "";
    public MallRequest userClient;
    /**
     * 已下载的离线地图信息列表f
     */
    private ArrayList<MKOLUpdateElement> localMapList = null;

    private LinearLayout ll_down;
    private TextView tv_pro;//进度

    private TextView tvWhere;
    private Button tv_remove;//
    private Button bt_download_re;//下载资源
    private Button btResRemove;//删除资源
    private Button btMap;

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

        try {
            userClient = ServiceGenerator.createService(MallRequest.class);
        } catch (Exception e) {
            Log.d("qqqqqqqq", "ServiceGenerator" + e.toString());
        }
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
        btMap = (Button) findViewById(R.id.bt_map);
        bt_start = (Button) findViewById(R.id.bt_start);
        tvWhere = (TextView) findViewById(R.id.tv_where);
        tv_pro = (TextView) findViewById(R.id.ratio);
        tv_remove = (Button) findViewById(R.id.remove);
        bt_download_re = (Button) findViewById(R.id.bt_download_re);

        ll_down = (LinearLayout) findViewById(R.id.ll_down);
        btResRemove = (Button) findViewById(R.id.bt_res_remove);

        bt_download_re.setOnClickListener(this);
        btResRemove.setOnClickListener(this);
        tvWhere.setOnClickListener(this);


        btMap.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isExist){
                    mOffline.remove(cityNum);
                }
                mOffline.start(cityNum);
            }
        });

        tv_remove.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if(isDowning){
                    mOffline.pause(cityNum);
                }
                if(mOffline.remove(cityNum)){
                    Toast.makeText(MainOfflineActivity.this, "删除离线地图成功", Toast.LENGTH_SHORT).show();

                }

            }
        });


        bt_start.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isExist){
                    File file1 = new File(FILE_PATH+FILE_NAME_line);
                    File file2 = new File(FILE_PATH+FILE_NAME_ROUTE);
                    if(file1.exists() && file2.exists()){
                        Intent gIntent = new Intent(MainOfflineActivity.this,ZSLOfflineActivity.class);
                        startActivity(gIntent);
                    }else{
                        Toast.makeText(MainOfflineActivity.this, "请下载完整离线资源", Toast.LENGTH_SHORT).show();

                    }
                }else{
                    Toast.makeText(MainOfflineActivity.this, "请下载离线地图", Toast.LENGTH_SHORT).show();

                }
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
                    if(update.cityName.equals("张家口市") && update.ratio == 100){
                        tv_pro.setText("下载完成");
                    }

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
            case R.id.tv_where:
                startActivityForResult(new Intent(this, RegionWheelDialog.class), REQUESTCODE_GET_AREA);

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
    private void downResource()  {
        String uid = PreferencesUtils.getString(MainOfflineActivity.this, "UID", "");
        showDownProgressDialog("正在连接...");
     //   String downloadUrl = "http://10.18.11.152:8080/InventoryManager/pdaMainTask!getResourceDb.interface";
        String json = "{\"UID\":\""+uid+"\""+","+"\"areaId\":\""+cAreaId+"\"}";
        Log.d("qqqqqqqq","json=="+json);
        Call<ResponseBody> responseBodyCall = userClient.downloadFile(mDowznUrl_line,json);
        responseBodyCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, final Response<ResponseBody> response) {
                if(response.raw().code() == 200){
                    Log.d("qqqqqqqq","下载完成");

                //建立一个文件
                final File file = FileUtils.createFile(MainOfflineActivity.this,1);

                new Thread(){
                    @Override
                    public void run() {
                        super.run();
                        // 保存到本地
                        FileUtils.writeFile2Disk(response, file, new HttpCallBack() {
                            @Override
                            public void onLoading(final long current, final long total) {
                                /** * 更新进度条 */
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {

                                        if(mProDialog != null){
                                            mProDialog.setMessage("正在下载...\n"+current+"/"+total+"\n"+"总进度0/2");
                                        }

                                        if(current == total){
                                            downResource2();
                                        }

                                     //   LogUtils.d("下载文件pro==="+total+"/"+current);
                                    }
                                });
                            }
                        });
                    } }.start();
                }else{
                    if(mProDialog != null) {
                        mProDialog.dismiss();
                    }
                    Log.d("qqqqqq","下载..."+response.raw().code());
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                if(mProDialog != null) {
                    mProDialog.dismiss();
                }
                Log.d("qqqqqq","下载异常"+t.toString());
            }
        });


    }   private void downResource2()  {
        String uid = PreferencesUtils.getString(MainOfflineActivity.this, "UID", "");

        showDownProgressDialog("正在连接...");
    //    String downloadUrl = "http://10.18.11.152:8080/InventoryManager/pdaMainTask!getResourceDb.interface";
        String json = "{\"UID\":\""+uid+"\""+","+"\"areaId\":\""+cAreaId+"\"}";
        Call<ResponseBody> responseBodyCall = userClient.downloadFile(mDowznUrl_r,json);
        responseBodyCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, final Response<ResponseBody> response) {
                if(response.raw().code() == 200){
                    Log.d("qqqqqqqq","下载完成");


                //建立一个文件
                final File file = FileUtils.createFile(MainOfflineActivity.this,2);

                new Thread(){
                    @Override
                    public void run() {
                        super.run();
                        // 保存到本地
                        FileUtils.writeFile2Disk(response, file, new HttpCallBack() {
                            @Override
                            public void onLoading(final long current, final long total) {
                                /** * 更新进度条 */
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        if(mProDialog != null){
                                            mProDialog.setMessage("正在下载...\n"+current+"/"+total+"\n"+"总进度：1/2");
                                        }
                                        if(current == total){
                                            if(mProDialog != null) {
                                                mProDialog.dismiss();
                                            }
                                            Toast.makeText(MainOfflineActivity.this,"下载完成",Toast.LENGTH_SHORT).show();
                                        }

                                     //   LogUtils.d("下载文件pro==="+total+"/"+current);
                                    }
                                });
                            }
                        });
                    } }.start();
                }else{
                    if(mProDialog != null) {
                        mProDialog.dismiss();
                    }
                    Log.d("qqqqqq","下载..."+response.raw().code());
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                if(mProDialog != null) {
                    mProDialog.dismiss();
                }
                Log.d("qqqqqq","下载异常"+t.toString());
            }
        });


    }


    private int totalSize = 0;
    private int haveSize = 0;
    private int allHavePro = 0;
    private String erroInfo = "网络异常";
    private OfficeHandler officeHandler = new OfficeHandler();
    class OfficeHandler extends Handler{
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                //下载test中
                case 0:
                    if(mProDialog != null && mProDialog.isShowing()){
                        mProDialog.setMessage(haveSize+"/"+totalSize+"\n\t\t总进度:"+allHavePro+"/2");
                    }
                    break;
                //下载test成功ss
                case 1:


                    if(mProDialog != null) {
                        mProDialog.dismiss();
                    }
                    break;
                //下载route成功ss
                case 4:
                    if(mProDialog != null) {
                        mProDialog.dismiss();
                    }

                    Toast.makeText(MainOfflineActivity.this,"全部下载成功",Toast.LENGTH_SHORT).show();

                    break;

                //下载test错误
                case 3:
                    Toast.makeText(MainOfflineActivity.this,erroInfo,Toast.LENGTH_SHORT).show();
                    if(mProDialog != null) {
                        mProDialog.dismiss();
                    }
                    break;
            }
        }
    }

    private ProgressDialog mProDialog = null;
    private void showDownProgressDialog(String message){
        if(mProDialog == null){
            mProDialog = new ProgressDialog(MainOfflineActivity.this);
            mProDialog.setCancelable(false);
            mProDialog.setCanceledOnTouchOutside(false);
        }
        mProDialog.setMessage(message);
        mProDialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUESTCODE_GET_AREA) {
                //company_edit.setText((CharSequence) data.getStringExtra("city"));//地市

                cCityId = data.getStringExtra("area");//区县
                cAreaId = data.getStringExtra("city");//地市
                tvWhere.setText(cAreaId);//
            }
        }
    }
}