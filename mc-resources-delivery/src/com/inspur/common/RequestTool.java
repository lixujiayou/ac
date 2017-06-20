package com.inspur.common;

import java.io.File;
import java.util.Map;

import com.yolanda.nohttp.FileBinary;
import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.OnUploadListener;
import com.yolanda.nohttp.Request;
import com.yolanda.nohttp.RequestMethod;

import android.content.Context;

public class RequestTool {

    /**
     * 用NoHtt默认实现上传文件.
     */
    public static void uploadFileNoHttp(Context context,String url,Map<String,String> paramMap,Map<String,File> fileMap,OnUploadListener uploadListener,HttpListener<String> httpListener) {

        Request<String> request = NoHttp.createStringRequest(url, RequestMethod.POST);
        request.setConnectTimeout(180*1000);
        request.setReadTimeout(300*1000);
        for (String key : paramMap.keySet()) {
            request.add(key, paramMap.get(key));

        }
        // 上传文件需要实现NoHttp的Binary接口，NoHttp默认实现了一个FileBinary
        FileBinary fileBinary;
        int i = 0;
        for (String key : fileMap.keySet()) {
            fileBinary = new FileBinary(fileMap.get(key));
            fileBinary.setUploadListener(i++, uploadListener);
            request.add(key, fileBinary);

        }

        CallServer.getRequestInstance().add(context, 0, request, httpListener, false, true);
    }

}
