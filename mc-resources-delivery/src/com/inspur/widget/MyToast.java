package com.inspur.widget;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by lixu on 2017/8/24.
 */

public class MyToast {
    private static Toast toast = null;
    public static void show(Context context,String message){
        if(toast == null){
            toast = Toast.makeText(context,message,Toast.LENGTH_SHORT);
        }else{
            toast.cancel();
            toast.setText(message);
        }
        toast.show();
    }
}
