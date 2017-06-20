package com.inspur.common;
import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yolanda.nohttp.Headers;
import com.yolanda.nohttp.JsonObjectRequest;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.RestRequest;
import com.yolanda.nohttp.StringRequest;

/**
 * Created in Feb 1, 2016 8:53:17 AM.
 *
 * @author YOLANDA;
 */
public class FastJsonRequest extends RestRequest<JSONObject> {

    public FastJsonRequest(String url) {
        super(url);
    }

    public FastJsonRequest(String url, RequestMethod requestMethod) {
        super(url, requestMethod);
    }

    @Override
    public JSONObject parseResponse(String url, Headers responseHeaders, byte[] responseBody) {
        String result = StringRequest.parseResponseString(url, responseHeaders, responseBody);
        JSONObject jsonObject;
        try {
            jsonObject = JSON.parseObject(result);
        } catch (Throwable e) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("error", -1);
            map.put("url", url());
            map.put("data", "");
            map.put("method", getRequestMethod().toString());
            jsonObject = (JSONObject) JSON.toJSON(map);
        }
        return jsonObject;
    }

    @Override
    public String getAccept() {
        return JsonObjectRequest.ACCEPT;
    }

}