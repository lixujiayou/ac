// 

// 

package com.inspur.resources.view.photo;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.GsonBuilder;
import com.inspur.resources.bean.ResourceImage;
import com.inspur.resources.utils.ApplicationValue;
import com.inspur.resources.utils.ImageUtils;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import cn.trinea.android.common.util.TimeUtils;

public class HttpMultipartPost extends AsyncTask<String, Integer, ResourceImage>
{
    private static final int EXCEPTION = 2;
    private static final int UPLOAD_FINISH = 1;
    private Context context;
    int count;
    private String filePath;
    private String imagename;
    private Handler mHandler;
    private String message;
    private ProgressDialog pd;
    private List<String> waterMarkerList;
    private int uploadedCount = 0;;

    public HttpMultipartPost(final Context context, final String filePath,List<String> waterMarkerList, final String message, final Handler mHandler, final String imagename) {
        this.count = 0;
        this.context = context;
        this.mHandler = mHandler;
        this.filePath = filePath;
        this.imagename = imagename;
        this.message = message;
        this.waterMarkerList = waterMarkerList;

    }

    protected ResourceImage doInBackground(final String... array) {
        System.out.println("imagename="+imagename);
        File file = ImageUtils.addWaterMarker(filePath,imagename, waterMarkerList);
//    	File file = new File(filePath);
        System.out.println("file="+file.getName());
        try {
            final HttpURLConnection httpURLConnection = (HttpURLConnection)new URL(String.valueOf(ApplicationValue.url) + ApplicationValue.webUrlUpImage).openConnection();
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setUseCaches(false);
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setRequestProperty("Connection", "Keep-Alive");
            httpURLConnection.setRequestProperty("Charset", "UTF-8");
            httpURLConnection.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + "******");
            final DataOutputStream dataOutputStream = new DataOutputStream(httpURLConnection.getOutputStream());
            dataOutputStream.writeBytes(String.valueOf("--") + "******" + "\r\n");

            dataOutputStream.writeBytes("Content-Disposition: form-data; name=\"file\"; filename=\"" + imagename + "\"" + "\r\n");
            dataOutputStream.writeBytes("\r\n");
            final FileInputStream fileInputStream = new FileInputStream(file.getAbsoluteFile());
            final byte[] array2 = new byte[8192];
            while ((this.count = fileInputStream.read(array2)) != -1) {
                dataOutputStream.write(array2, 0, this.count);
                if (ApplicationValue.isUpLoad) {
                    this.publishProgress(new Integer[] { (int) ((this.count/file.length())*100) });
                }
            }
            fileInputStream.close();
            dataOutputStream.writeBytes("\r\n");
            dataOutputStream.writeBytes(String.valueOf("--") + "******" + "--" + "\r\n");
            dataOutputStream.flush();
            final InputStream inputStream = httpURLConnection.getInputStream();
            String outString = new BufferedReader(new InputStreamReader(inputStream, "utf-8")).readLine();
            System.out.println("outString="+outString);
            dataOutputStream.close();
            inputStream.close();
            httpURLConnection.disconnect();
            return new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss Z").create().fromJson(outString, ResourceImage.class);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    protected void onCancelled() {
        System.out.println("cancle");
    }

    protected void onPostExecute(final ResourceImage img) {
        if (img!=null) {
            Message msg = mHandler.obtainMessage();
            msg.what = 1;
            msg.obj = img;
            System.out.println("====" + ApplicationValue.url.replace("service/", "") + "upload/" + img.getImagePath());

            this.mHandler.sendMessage(msg);
        }
        else {
            this.mHandler.sendEmptyMessage(2);
        }
        this.pd.dismiss();
    }

    protected void onPreExecute() {
        (this.pd = new ProgressDialog(this.context)).setProgressStyle(1);
        this.pd.setTitle((CharSequence)"系统提示");
        this.pd.setMessage((CharSequence)this.message);
        this.pd.setCancelable(false);
        this.pd.setButton((CharSequence)"取消", new DialogInterface.OnClickListener() {
            public void onClick(final DialogInterface dialogInterface, final int n) {
                ApplicationValue.isUpLoad = false;
                HttpMultipartPost.this.pd.dismiss();
            }
        });
        this.pd.show();
    }

    protected void onProgressUpdate(final Integer... array) {
        uploadedCount+=array[0];
        this.pd.setProgress(uploadedCount);
    }
}
