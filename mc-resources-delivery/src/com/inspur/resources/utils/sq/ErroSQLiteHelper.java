package com.inspur.resources.utils.sq;

import com.inspur.resources.utils.ApplicationValue;

import android.app.Application;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
/**
 * 隐患离线库
 * @author Administrator
 *
 */
public class ErroSQLiteHelper extends SQLiteOpenHelper {

    //类没有实例化,是不能用作父类构造器的参数,必须声明为静态

    public ErroSQLiteHelper(Context context, String name, CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create table "+ApplicationValue.ERRO_ZYYH+"(_id integer primary key autoincrement,"+ApplicationValue.ERRO_ROUTEID+" integer,"+ApplicationValue.ERRO_CONTENT+" text)");
        db.execSQL("create table "+ApplicationValue.ERRO_ZZSJ+"(_id integer primary key autoincrement,"+ApplicationValue.ERRO_ROUTEID+" integer,"+ApplicationValue.ERRO_CONTENT+" text)");
        db.execSQL("create table "+ApplicationValue.ERRO_GDGZ+"(_id integer primary key autoincrement,"+ApplicationValue.ERRO_ROUTEID+" integer,"+ApplicationValue.ERRO_CONTENT+" text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }


}  