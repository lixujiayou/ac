// 

// 

package com.inspur.resources.database.helper;

import android.content.ContentValues;
import java.util.ArrayList;
import java.util.List;

import com.inspur.resources.database.databasebeans.SendInfoBean;

import android.database.Cursor;

public class MessageInfoHelper
{
    public static final String CREATE_MESSAGE_INFO_TABLE = "create table IF NOT EXISTS messageInfoTable(id integer primary key autoincrement,password varchar(200),sendMessage varchar(3999),dotype varchar(3999))";
    public static final String MESSAGE_INFO_TABLE_NAME = "messageInfoTable";
    private static final String TAG = "MessageInfoHelper";
    
    public static List<SendInfoBean> PraseForMessage(final Cursor cursor) {
        final ArrayList<SendInfoBean> list = new ArrayList<SendInfoBean>();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            final int columnIndex = cursor.getColumnIndex("id");
            final int columnIndex2 = cursor.getColumnIndex("password");
            final int columnIndex3 = cursor.getColumnIndex("sendMessage");
            final int columnIndex4 = cursor.getColumnIndex("dotype");
            final String string = cursor.getString(columnIndex);
            final String string2 = cursor.getString(columnIndex2);
            final String string3 = cursor.getString(columnIndex3);
            final String string4 = cursor.getString(columnIndex4);
            final SendInfoBean sendInfoBean = new SendInfoBean();
            sendInfoBean.setId(string);
            sendInfoBean.setPassword(string2);
            sendInfoBean.setSendMessage(string3);
            sendInfoBean.setDotype(string4);
            list.add(sendInfoBean);
            cursor.moveToNext();
        }
        return list;
    }
    
    public static String UpdateMessageByObj(final String s, final String s2, final String s3) {
        return "update messageInfoTable set sendMessage='" + s + "' where password='" + s2 + "' and dotype='" + s3 + "'";
    }
    
    public static String deleteOneMessage(final String s, final String s2) {
        return "delete from messageInfoTable where password='" + s + "' and dotype='" + s2 + "'";
    }
    
    public static ContentValues insertMessageByObj(final SendInfoBean sendInfoBean) {
        final ContentValues contentValues = new ContentValues();
        contentValues.put("password", sendInfoBean.getPassword());
        contentValues.put("sendMessage", sendInfoBean.getSendMessage());
        contentValues.put("dotype", sendInfoBean.getDotype());
        return contentValues;
    }
    
    public static List<String> insertMessageByObjAll(final List<SendInfoBean> list) {
        final ArrayList<String> list2 = new ArrayList<String>();
        for (int i = 0; i < list.size(); ++i) {
            final SendInfoBean sendInfoBean = list.get(i);
            list2.add("insert into messageInfoTable(password,sendMessage,dotype) values('" + sendInfoBean.getPassword() + "','" + sendInfoBean.getSendMessage() + "','" + sendInfoBean.getDotype() + "')");
        }
        return list2;
    }
    
    public static String selectMessageAll(final String s, final String s2) {
        return "select * from messageInfoTable where password='" + s + "' and dotype='" + s2 + "'";
    }
    
    public static String selectMessageAllTOO(final String s) {
        return "select * from messageInfoTable where dotype='" + s + "'";
    }
    
    public static String selectPwd(final String s) {
        return "select * from messageInfoTable where password='" + s + "'";
    }
}
