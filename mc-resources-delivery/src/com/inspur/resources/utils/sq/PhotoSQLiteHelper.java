package com.inspur.resources.utils.sq;

import com.inspur.resources.utils.ApplicationValue;

import android.app.Application;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
/**
 * 图片离线库
 * @author Administrator
 *
 */
public class PhotoSQLiteHelper extends SQLiteOpenHelper {

    //类没有实例化,是不能用作父类构造器的参数,必须声明为静态

    public PhotoSQLiteHelper(Context context, String name, CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create table "+ApplicationValue.PHOTOTABLENAME+"(_id integer primary key autoincrement,"+ApplicationValue.PHOTOTABLE_USERID+" text,"+ApplicationValue.PHOTOTABLE_POHOTO+" text,"+ApplicationValue.PHOTOTABLE_PHOTOTYPE+" text,"+ApplicationValue.PHOTOTABLE_RESOURCETYPE+" text,"+ApplicationValue.PHOTOTABLE_LON+" double,"+ApplicationValue.PHOTOTABLE_LAT+" double,"+ApplicationValue.PHOTOTABLE_RELATEDID+" integer,"+ApplicationValue.PHOTOTABLE_ROUTEID+" integer)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }


}  