package com.inspur.resources.utils.sq;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
/**
 *
 * @author Administrator
 *
 */
public class DataBaseHelperOffLine extends SQLiteOpenHelper {

    //类没有实例化,是不能用作父类构造器的参数,必须声明为静态


    public DataBaseHelperOffLine(Context context, String name, CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table user2(_id integer primary key autoincrement,json text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }


}  