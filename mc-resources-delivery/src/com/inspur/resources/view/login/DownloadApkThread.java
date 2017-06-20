// 

// 

package com.inspur.resources.view.login;

import java.io.InputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.io.FileOutputStream;
import java.io.File;
import android.util.Log;
import java.net.URL;
import java.net.HttpURLConnection;
import android.os.Environment;
import android.os.Handler;
import android.content.Context;

public class DownloadApkThread extends Thread
{
    private static final int DOWNLOAD = 1;
    private static final int DOWNLOAD_FINISH = 2;
    private static final int EXCEPTION = -2;
    private Context mContext;
    private Handler mHandler;
    private String mURL;
    
    public DownloadApkThread(final Context mContext, final Handler mHandler, final String murl) {
        this.mContext = null;
        this.mHandler = null;
        this.mURL = null;
        this.mContext = mContext;
        this.mHandler = mHandler;
        this.mURL = murl;
    }
    
    @Override
    public void run() {
        try {
            LoginAutoUpdateActivity.mSavePath = String.valueOf(new StringBuilder().append(Environment.getExternalStorageDirectory()).append("/").toString()) + "download";
            final HttpURLConnection httpURLConnection = (HttpURLConnection)new URL(this.mURL).openConnection();
            httpURLConnection.connect();
            LoginAutoUpdateActivity.length = httpURLConnection.getContentLength();
            Log.d("\u6211\u83b7\u53d6\u7684\u6587\u4ef6\u5927\u5c0f", String.valueOf(LoginAutoUpdateActivity.mSavePath) + "``````" + LoginAutoUpdateActivity.length);
            final InputStream inputStream = httpURLConnection.getInputStream();
            final File file = new File(LoginAutoUpdateActivity.mSavePath);
            if (!file.exists()) {
                file.mkdir();
            }
            final File file2 = new File(LoginAutoUpdateActivity.mSavePath, "com.inspur.resources.apk");
            if (file2.exists()) {
                file2.delete();
            }
            final FileOutputStream fileOutputStream = new FileOutputStream(file2);
            int n = 0;
            final byte[] array = new byte[1024];
            while (true) {
                do {
                    final int read = inputStream.read(array);
                    n = (LoginAutoUpdateActivity.progress = n + read);
                    this.mHandler.sendEmptyMessage(1);
                    if (read <= 0) {
                        this.mHandler.sendEmptyMessage(2);
                        fileOutputStream.close();
                        inputStream.close();
                        return;
                    }
                    fileOutputStream.write(array, 0, read);
                } while (!LoginAutoUpdateActivity.cancelUpdate);
                continue;
            }
        }
        catch (MalformedURLException ex) {
            this.mHandler.sendEmptyMessage(-2);
            ex.printStackTrace();
        }
        catch (IOException ex2) {
            this.mHandler.sendEmptyMessage(-2);
            ex2.printStackTrace();
        }
    }
}
