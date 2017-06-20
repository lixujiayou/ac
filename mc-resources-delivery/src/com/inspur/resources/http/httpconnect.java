// 

// 

package com.inspur.resources.http;

import org.apache.http.client.HttpClient;
import android.net.NetworkInfo;
import cn.trinea.android.common.util.PreferencesUtils;
import android.net.ConnectivityManager;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;

import com.inspur.resources.utils.ApplicationValue;

import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.HttpEntity;
import java.util.List;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.message.BasicNameValuePair;

import android.content.Context;
import org.apache.http.NameValuePair;
import java.util.ArrayList;

public class httpconnect
{
    public String httpGetData(final String s, final ArrayList<NameValuePair> list, final Context context) {
        String httpOnPostData = null;
        try {

            String uid = PreferencesUtils.getString(context, "UID", "");
            list.add(new BasicNameValuePair("UID", uid));
            final String s2 = httpOnPostData = this.httpOnPostData(String.valueOf(ApplicationValue.url) + s, list, context);
            return s2;
        }
        catch (Exception ex) {
            return httpOnPostData;
        }
    }

    public String httpOnPostData(String s, final ArrayList<NameValuePair> list, final Context context) {
        try {
            final BasicHttpParams basicHttpParams = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout((HttpParams)basicHttpParams, 60000);
            HttpConnectionParams.setSoTimeout((HttpParams)basicHttpParams, 300000);
            final DefaultHttpClient defaultHttpClient = new DefaultHttpClient((HttpParams)basicHttpParams);
            final HttpPost httpPost = new HttpPost(s);
            httpPost.setEntity((HttpEntity)new UrlEncodedFormEntity((List)list, "UTF-8"));
            final HttpResponse execute = ((HttpClient)defaultHttpClient).execute((HttpUriRequest)httpPost);
            Log.d("lixu", "查询==CODE"+execute.getStatusLine().getStatusCode());
            if (execute == null || execute.getStatusLine().getStatusCode() != 200) {

                return "{\"result\":\"1\",\"info\":\"亲，网络不给力请求超时了。\"}";
            }

            final String string = EntityUtils.toString(execute.getEntity(), "UTF-8");
            Log.d("lixu", "查询==info=="+string);
            final int index = string.indexOf("{");
            final int index2 = string.indexOf("[");
            if (index > index2) {
                if (index2 >= 0) {
                    return string.substring(index2);
                }
                s = string;
                if (index >= 0) {
                    return string.substring(index);
                }
            }
            else {
                if (index >= 0) {
                    return string.substring(index);
                }
                s = string;
                if (index2 >= 0) {
                    return string.substring(index2);
                }
            }
        }
        catch (Exception ex) {
            Log.d("lixu", "查询==Exception=="+ex.toString());
            s = "{\"result\":\"1\",\"info\":\"亲，网络请求出错了。\"}";
            ex.printStackTrace();
        }
        return s;
    }


}
