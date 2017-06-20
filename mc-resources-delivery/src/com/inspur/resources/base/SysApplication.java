// 

// 

package com.inspur.resources.base;

import java.util.Iterator;
import java.util.LinkedList;
import android.app.Activity;
import java.util.List;
import android.app.Application;

public class SysApplication extends Application
{
    private static final String TAG = "SysApplication";
    private static SysApplication instance;
    public static List<Activity> mList;
    
    static {
        SysApplication.mList = new LinkedList<Activity>();
    }
    
    public static SysApplication getInstance() {
        synchronized (SysApplication.class) {
            if (SysApplication.instance == null) {
                SysApplication.instance = new SysApplication();
            }
            return SysApplication.instance;
        }
    }
    
    public void addActivity(final Activity activity) {
        SysApplication.mList.add(activity);
    }
    
    public void exit() {
        try {
            for (final Activity activity : SysApplication.mList) {
                if (activity != null) {
                    activity.finish();
                }
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        finally {
            System.exit(0);
        }
    }
    
    public void onLowMemory() {
        super.onLowMemory();
        System.gc();
    }
}
