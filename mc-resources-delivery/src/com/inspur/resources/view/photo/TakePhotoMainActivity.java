// 

// 

package com.inspur.resources.view.photo;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.inspur.StringUtils;
import com.inspur.component.photoselect.CommonExtras;
import com.inspur.component.photoselect.photoselector.model.PhotoModel;
import com.inspur.component.photoselect.photoselector.ui.PhotoSelectorActivity;
import com.inspur.easyresources.R;
import com.inspur.resources.base.BaseActivity;
import com.inspur.resources.bean.ResourceImage;
import com.inspur.resources.bean.StationBaseInfoBean;
import com.inspur.resources.http.httpconnect;
import com.inspur.resources.utils.ApplicationValue;
import com.inspur.resources.utils.CommonUtils;
import com.inspur.resources.utils.StrUtil;
//import com.inspur.resources.view.station.StationSearchListActivity;
import com.squareup.picasso.Picasso;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.Toast;
import cn.trinea.android.common.util.ImageUtils;
import cn.trinea.android.common.util.TimeUtils;

public class TakePhotoMainActivity extends BaseActivity
{
    public static String imageNames;
    private final int SELECT_ZM_PHOTO_RESULT;
    private final int TAKE_PHOTO_RESULT;
    int fillscreenWidth;
    private String jingduIn;
    @SuppressLint({ "HandlerLeak" })
    private Handler mHandler;
    private ProgressDialog mProgress;
    private String nowPhotoName;
    private Uri outputPhotoFileUri;
    private MyAdapter photoAdatpter;
    //    private int photoIndex;
//    private List<Bitmap> photoList;
    private GridView photo_GridView;
    private String picNameIn;
    private String picPath;
    Bitmap tempBitmap;
    private String weiduIn;
    List<ResourceImage> resourceImageList = new ArrayList<ResourceImage>();

    static {
        TakePhotoMainActivity.imageNames = null;
    }

    public TakePhotoMainActivity() {
        this.photo_GridView = null;
        this.photoAdatpter = null;
        this.nowPhotoName = "";
//        this.photoList = new ArrayList<Bitmap>();
//        this.photoIndex = 1;
        this.picNameIn = null;
        this.jingduIn = null;
        this.weiduIn = null;
        this.outputPhotoFileUri = null;
        this.TAKE_PHOTO_RESULT = 10;
        this.SELECT_ZM_PHOTO_RESULT = 11;
        this.mHandler = new Handler() {
            public void handleMessage(final Message message) {
                super.handleMessage(message);
                if (TakePhotoMainActivity.this.mProgress != null) {
                    TakePhotoMainActivity.this.mProgress.dismiss();
                }
                switch (message.what) {
                    case 1: {
                        /*TakePhotoMainActivity.this.photoList.add(TakePhotoMainActivity.this.tempBitmap);
                        Log.d("000000000", String.valueOf(TakePhotoMainActivity.imageNames) + "-");
                        if (TakePhotoMainActivity.imageNames != null && !TakePhotoMainActivity.imageNames.equals("")) {
                            TakePhotoMainActivity.imageNames = String.valueOf(TakePhotoMainActivity.imageNames) + "," + TakePhotoMainActivity.this.nowPhotoName;
                        }
                        else {
                            TakePhotoMainActivity.imageNames = TakePhotoMainActivity.this.nowPhotoName;
                        }
                        Log.d("111111111", TakePhotoMainActivity.imageNames);*/
                        resourceImageList.add((ResourceImage)message.obj);
                        photoAdatpter.notifyDataSetChanged();
//                        TakePhotoMainActivity.access$5(TakePhotoMainActivity.this, photoIndex + 1);
                        break;
                    }
                    case 2: {
                        break;
                    }
                }
            }
        };
    }

    static /* synthetic */ void access$12(final TakePhotoMainActivity takePhotoMainActivity, final String nowPhotoName) {
        takePhotoMainActivity.nowPhotoName = nowPhotoName;
    }

    private void init() {
        this.photoAdatpter = new MyAdapter(this,resourceImageList);
        this.photo_GridView.setAdapter((ListAdapter)this.photoAdatpter);
        this.photo_GridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(final AdapterView<?> adapterView, final View view, final int n, final long n2) {
                final String string = String.valueOf(ApplicationValue.url.replace("service/", "")) + "upload/" + resourceImageList.get(n).getImagePath();
                Log.d("urlStr+++++++++++", string);
                new ShowPhoto((Context)TakePhotoMainActivity.this, string).show();
            }
        });
    }

    protected void onActivityResult(int columnIndexOrThrow, final int n, final Intent intent) {
        super.onActivityResult(columnIndexOrThrow, n, intent);

        this.nowPhotoName = String.valueOf(this.picNameIn) + "_" + new Date().getTime() + ".jpg";

        if (n != RESULT_OK)
            return;
        if (columnIndexOrThrow == 9) {// selected image
            if (intent != null && intent.getExtras() != null) {
                @SuppressWarnings("unchecked")
                final List<PhotoModel> photos = (List<PhotoModel>) intent.getExtras().getSerializable(CommonExtras.photos);
                int size = 0;
                if (photos != null) size = photos.size();
                if (photos == null || photos.isEmpty()) {
                    Toast.makeText(this, "没有选择图片", Toast.LENGTH_SHORT).show();
                } else {
//                        Toast.makeText(this, "选择了" + size, Toast.LENGTH_SHORT).show();


                    final AlertDialog.Builder alertDialog$Builder = new AlertDialog.Builder((Context)this);
                    alertDialog$Builder.setMessage((CharSequence)"是否确认并上传？");
                    alertDialog$Builder.setPositiveButton((CharSequence)"是", (DialogInterface.OnClickListener)new DialogInterface.OnClickListener() {
                        public void onClick(final DialogInterface dialogInterface, int inSampleSize) {
                            String path = photos.get(0).getOriginalPath();
                            List<String> waterMarker = new ArrayList<String>(2);
                            waterMarker.add("经纬度:"+jingduIn+"//"+weiduIn);
                            waterMarker.add("时间:"+TimeUtils.getCurrentTimeInString());

                            Log.d("-----", "path="+path);
                            new HttpMultipartPost(TakePhotoMainActivity.this, path,waterMarker, "正在进行图片上传操作……", TakePhotoMainActivity.this.mHandler, TakePhotoMainActivity.this.nowPhotoName).execute(new String[0]);
                        }
                    });
                    alertDialog$Builder.setNeutralButton("否", (DialogInterface.OnClickListener)new DialogInterface.OnClickListener() {
                        public void onClick(final DialogInterface dialogInterface, final int n) {
                            dialogInterface.dismiss();
                        }
                    });
                    alertDialog$Builder.show();
                }
            }
        }
        return;

    }

    @Override
    protected void onCreate(final Bundle bundle) {
        super.onCreate(bundle);
        this.setContentView(R.layout.take_photo_main);
        new DisplayMetrics();
        final DisplayMetrics displayMetrics = this.getResources().getDisplayMetrics();
        this.fillscreenWidth = (Math.min(displayMetrics.widthPixels, displayMetrics.heightPixels) - 10) / 3;
        final Intent intent = this.getIntent();
        this.picNameIn = intent.getStringExtra("photoName");
        this.jingduIn = intent.getStringExtra("lon");
        this.weiduIn = intent.getStringExtra("lat");
        final String stringExtra = intent.getStringExtra("imageNames");
        if (stringExtra != null && !stringExtra.equals("") && stringExtra.charAt(stringExtra.length() - 1) == ',') {
            Log.d("\u9017\u53f7", "\u9017\u53f7");
            TakePhotoMainActivity.imageNames = stringExtra.substring(0, stringExtra.lastIndexOf(","));
        }
        else {
            TakePhotoMainActivity.imageNames = stringExtra;
        }

        String resourceId = intent.getStringExtra("resourceId");

        this.init();
        loadImages(resourceId);
    }

    private void loadImages(final String resourceId){
        if(StringUtils.isEmpty(resourceId)){
            Toast.makeText(TakePhotoMainActivity.this, "无法获取资源信息", Toast.LENGTH_SHORT).show();
            return;
        }
        if (this.mProgress == null) {
            this.mProgress = ProgressDialog.show(this, (CharSequence)"系统提示", "正在加载资源图片...");
        }
        else {
            this.mProgress.setMessage("正在加载资源图片...");
            this.mProgress.show();
        }
        final Handler handler = new Handler(new Handler.Callback() {

            @Override
            public boolean handleMessage(Message msg) {
                if (mProgress != null) {
                    mProgress.dismiss();
                }
                switch (msg.what) {
                    case 1:
                        resourceImageList.clear();
                        resourceImageList.addAll((List<ResourceImage>) msg.obj);
                        photoAdatpter.notifyDataSetChanged();
                        break;
                    case 2:
                        break;
                }
                return true;
            }
        });

        new Thread() {
            @Override
            public void run() {
                ResourceImage ri = new ResourceImage();
                ri.setResourceId(resourceId);
                final String json = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss Z").create().toJson(ri);
                Log.d("ResultStr", json);
                final ArrayList<NameValuePair> list = new ArrayList<NameValuePair>();
                list.add((NameValuePair)new BasicNameValuePair("jsonRequest", json));
                final String httpGetData = new httpconnect().httpGetData("pdaImage!getImage.interface", list, TakePhotoMainActivity.this);
                Log.d("ImageList==>", "result==>" + httpGetData);
                System.out.print(httpGetData);
                if (httpGetData == null) {
                    if ("".equals(httpGetData)) {
                        return;
                    }
                }
                final Message message = new Message();
                try {

                    final JSONObject jsonObject = new JSONObject(httpGetData.toString());
                    final String string = jsonObject.getString("info");
                    if (jsonObject.getString("result").equals("0")) {
                        message.what = 1;
                        message.obj = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss Z").create().fromJson(string, new TypeToken<List<ResourceImage>>() {}.getType());;
                        handler.sendMessage(message);
                        return;
                    }
                    message.what = 2;
                    message.obj = string;
                    handler.sendMessage(message);
                }
                catch (JSONException ex) {
                    message.what = 2;
                    message.obj = "数据加载失败!";
                    handler.sendMessage(message);
                }
            }
        }.start();

    }
    
    /*public void select_photo(final View view) {
        final Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction("android.intent.action.GET_CONTENT");
        this.startActivityForResult(intent, 11);
    }
    
    public void take_photo(final View view) {
        final Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        this.nowPhotoName = String.valueOf(this.picNameIn) + "_" + this.photoIndex + ".jpg";
        Log.d("nowPhotoName", nowPhotoName);
        intent.putExtra("output", (Parcelable)(this.outputPhotoFileUri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(), this.nowPhotoName))));
        this.startActivityForResult(intent, 10);
    }*/

    public void photoSelect(final View view){
        Intent intent = new Intent(TakePhotoMainActivity.this, PhotoSelectorActivity.class);
        intent.putExtra(CommonExtras.key_max, 1);
        intent.putExtra(CommonExtras.IS_SHOWCAMERA, true);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivityForResult(intent, 9);
    }

    class MyAdapter extends BaseAdapter
    {
        private Context context;
        private List<ResourceImage> resourceImageList;

        MyAdapter(final Context context,List<ResourceImage> imgList) {
            this.context = context;
            resourceImageList = imgList;
        }

        public int getCount() {
            return resourceImageList==null?0:resourceImageList.size();
        }

        public Object getItem(final int n) {
            return n;
        }

        public long getItemId(final int n) {
            return n;
        }

        public View getView(final int n, final View view, final ViewGroup viewGroup) {
            ResourceImage rImg = resourceImageList.get(n);
            ImageView imageView;
            if (view == null) {
                imageView = new ImageView(this.context);
                imageView.setLayoutParams((ViewGroup.LayoutParams)new AbsListView.LayoutParams(TakePhotoMainActivity.this.fillscreenWidth, TakePhotoMainActivity.this.fillscreenWidth));
                imageView.setAdjustViewBounds(false);
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                imageView.setPadding(8, 8, 8, 8);
            }
            else {
                imageView = (ImageView)view;
            }
            System.out.println("\u4e3a\u4ec0\u4e48\u5462" + ApplicationValue.url.replace("service/", "") + "upload/" + rImg.getImagePath());
            Log.d("\u7167\u7247\u83b7\u53d6\u4e0d\u5230\u5462", "\u4e3a\u4ec0\u4e48\u5462" + ApplicationValue.url.replace("service/", "") + "upload/" + rImg.getImagePath());
            Picasso.with((Context)TakePhotoMainActivity.this).load(String.valueOf(ApplicationValue.url.replace("service/", "")) + "upload/" + rImg.getImagePath()).resize(50, 50).centerCrop().placeholder(R.mipmap.ic_launcher).error(R.mipmap.ic_launcher).into(imageView);
            return imageView;
        }
    }
}
