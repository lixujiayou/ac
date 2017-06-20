// 

// 

package com.inspur.resources.database.helper;

import java.util.ArrayList;
import java.util.List;

import com.inspur.resources.bean.PoleSendInfoBean;
import com.inspur.resources.utils.ApplicationValue;

import android.database.Cursor;

public class poleInfoHelper
{
    public static final String CREATE_ADDPOLE_TABLE = "create table IF NOT EXISTS poleInfoTable(id integer primary key autoincrement,user varchar(200),poleId varchar(400),sendXML varchar(3999),dotype varchar(3999))";
    public static final String POLE_INFO_TABLE_NAME = "poleInfoTable";
    private static final String TAG = "poleInfoHelper";
    
    public static List<PoleSendInfoBean> PraseForPole(final Cursor cursor) {
        final ArrayList<PoleSendInfoBean> list = new ArrayList<PoleSendInfoBean>();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            final int columnIndex = cursor.getColumnIndex("id");
            final int columnIndex2 = cursor.getColumnIndex("user");
            final int columnIndex3 = cursor.getColumnIndex("poleId");
            final int columnIndex4 = cursor.getColumnIndex("sendXML");
            final int columnIndex5 = cursor.getColumnIndex("dotype");
            final String string = cursor.getString(columnIndex);
            final String string2 = cursor.getString(columnIndex2);
            final String string3 = cursor.getString(columnIndex3);
            final String string4 = cursor.getString(columnIndex4);
            final String string5 = cursor.getString(columnIndex5);
            final PoleSendInfoBean poleSendInfoBean = new PoleSendInfoBean();
            poleSendInfoBean.setId(string);
            poleSendInfoBean.setUsername(string2);
            poleSendInfoBean.setPoleId(string3);
            poleSendInfoBean.setSendXML(string4);
            poleSendInfoBean.setDotype(string5);
            list.add(poleSendInfoBean);
            cursor.moveToNext();
        }
        return list;
    }
    
    public static String deleteOnePole(final String s) {
        return "delete from poleInfoTable where id='" + s + "'";
    }
    
    public static String getPoleInfoById(final String s) {
        return "select * from poleInfoTable where id='" + s + "'";
    }
    
    public static List<String> insertPoleByObj(final String s, final String s2) {
        final ArrayList<String> list = new ArrayList<String>();
        list.add("insert into poleInfoTable(user,sendXML,dotype) values('" + ApplicationValue.mUser + "','" + s + "','" + s2 + "')");
        return list;
    }
    
    public static String searchNowPoleDbId() {
        return "select * from poleInfoTable order by id desc";
    }
    
    public static String selectPoleAll() {
        return "select * from poleInfoTable where user='" + ApplicationValue.mUser + "'";
    }
    
    public static String updatePoleInfo(final String s, final String s2, final String s3) {
        return "update poleInfoTable set dotype='" + s2 + "', sendXML='" + s3 + "' where id='" + s + "' and user='" + ApplicationValue.mUser + "'";
    }
    
    public static String updatedoType(final String s, final String s2) {
        return "update poleInfoTable set dotype='" + s2 + "_' where id='" + s + "' and user='" + ApplicationValue.mUser + "'";
    }
}
