package com.inspur.common;
import com.yolanda.nohttp.Response;

/**
 * Created in Nov 4, 2015 12:54:50 PM.
 *
 * @author YOLANDA;
 */
public interface HttpListener<T> {

    void onSucceed(int what, Response<T> response);

    void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis);

}
