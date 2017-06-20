package com.inspur.resources.utils;

import java.text.SimpleDateFormat;

/**
 * Created by lixu on 2017/5/23.
 */

public class MyTimeUtils {
    public static String getCurrentTime(){
        SimpleDateFormat sDateFormat = new  SimpleDateFormat("yyyy/MM/dd");
        String date = sDateFormat.format(new java.util.Date());
        return date;
    }
}
