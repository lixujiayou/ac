// 

// 

package com.inspur.resources.service;

import android.app.Activity;
import android.util.Log;
import android.content.Intent;
import android.content.Context;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;

public class RealworkReceiver extends BroadcastReceiver
{
    public static PendingIntent pi;
    private AlarmManager am;
    
    public void onReceive(final Context context, final Intent intent) {
        if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {
            Log.d("\u6211\u662f\u5f00\u542f\u542f\u52a8", "\u6211\u8fd0\u884c\u5f00\u673a\u542f\u52a8\u4e86\u4e48");
            this.am = (AlarmManager)context.getSystemService(Activity.ALARM_SERVICE);
          //  RealworkReceiver.pi = PendingIntent.getService(context, 0, new Intent("com.inspur.resources.TheService"), 134217728);
            this.am.setRepeating(0, System.currentTimeMillis() + 20000L, 30000L, RealworkReceiver.pi);
        }
        if (intent.getAction().equals("android.intent.action.TIME_TICK")) {
            Log.d("\u6211\u662f\u91cd\u542f\u670d\u52a1", "\u6211\u8fd0\u884c\u4e86\u4e48");
            Intent intentTheService =  new Intent("com.inspur.resources.TheService");
            intentTheService.setPackage(context.getPackageName());
            context.startService(intentTheService);
        }
        Log.d("\u8fdb\u5165\u4e86\u5e7f\u64ad\u76d1\u542c", "\u55ef\u55ef \u8fdb\u5165\u4e86\u5462");
        if (intent.getAction().equals("great1")) {
            Log.d("\u8fdb\u5165\u4e86\u5e7f\u64ad\u76d1\u542c", "\u5c3d\u5165\u4e86great1\u65b9\u6cd5");
           Intent intentTheService =  new Intent("com.inspur.resources.TheService");
           intentTheService.setPackage(context.getPackageName());
            context.startService(intentTheService);
        }
    }
}
