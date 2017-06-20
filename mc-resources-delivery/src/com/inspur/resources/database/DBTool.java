// 

// 

package com.inspur.resources.database;

import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.Cursor;
import java.util.Iterator;
import java.util.List;
import android.util.Log;
import android.content.ContentValues;
import android.database.SQLException;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class DBTool
{
    protected static final String CREATE_CONFIG_TABLE = "create table res(id integer,name varchar(60))";
    public static final String DATABASE_NAME = "testDB2";
    protected static final int DATABASE_VERSION = 3;
    public static final String KEY_STRING_Column = "keyString";
    public static final String TABLE_ID = "id";
    public static final String TABLE_NAME = "res";
    public static final String VALUE_STRING_Column = "valueString";
    private SQLiteDatabase db;
    private DatabaseHelper dbHelper;
    private Context mCtx;
    
    public DBTool(final Context context) throws SQLException {
        this.mCtx = null;
        this.dbHelper = null;
        this.db = null;
        try {
            this.dbHelper = new DatabaseHelper(context);
            this.db = this.dbHelper.getWritableDatabase();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public void close() {
        this.dbHelper.close();
    }
    
    public long create(final String s, final ContentValues contentValues) {
        Log.d("\u63d2\u5165\u5355\u4e2a\u6570\u636e", s);
        return this.db.insert(s, (String)null, contentValues);
    }
    
    public void delete(final List<String> list) {
        this.db.beginTransaction();
        try {
            final Iterator<String> iterator = list.iterator();
            while (iterator.hasNext()) {
                this.db.execSQL((String)iterator.next());
            }
            this.db.setTransactionSuccessful();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        finally {
            this.db.endTransaction();
            this.db.close();
        }
    }
    
    public boolean delete(final String s) {
        return this.db.delete(s, (String)null, (String[])null) > 0;
    }
    
    public boolean delete(final String s, final String s2, final String s3) {
        Log.d("\u6309\u6761\u4ef6\u5220\u9664\u6570\u636e", "\u8868\u540d:" + s2 + ",\u5173\u952e\u5b57:" + s3 + "\uff0c\u6761\u4ef6:" + s);
        return this.db.delete(s2, String.valueOf(s3) + "=" + s, (String[])null) > 0;
    }
    
    public void deleteSome(final String s) {
        Log.d("\u5220\u9664\u591a\u6761\u4ef6\u6570\u636e", s);
        this.db.execSQL(s);
    }
    
    public Cursor get(final String s) {
        Log.d("\u4f20\u5165\u7684sql\u8bed\u53e5", s);
        final Cursor rawQuery = this.db.rawQuery(s, (String[])null);
        if (rawQuery != null) {
            rawQuery.moveToFirst();
        }
        return rawQuery;
    }
    
    public Cursor get(final String s, final String s2, final String s3, final String[] array, final String s4, final String s5, final String s6) {
        final Cursor query = this.db.query(s2, array, String.valueOf(s3) + "=" + s, (String[])null, s5, (String)null, s6);
        if (query != null) {
            query.moveToFirst();
        }
        return query;
    }
    
    public Cursor getAll(final String s) {
        Log.d("\u5217\u8868\u67e5\u8be2", s);
        return this.db.rawQuery(s, (String[])null);
    }
    
    public Cursor getAll(final String s, final int n, final int n2) {
        Log.d("\u5206\u9875\u5217\u8868\u67e5\u8be2", s);
        return this.db.rawQuery(s, new String[] { String.valueOf(n), String.valueOf(n2) });
    }
    
    public Cursor getAll(final String s, final String[] array, final String s2, final String s3, final String s4) {
        return this.db.query(s, array, s2, (String[])null, s3, (String)null, s4);
    }
    
    public void insert(final List<String> list) {
        this.db.beginTransaction();
        try {
            final Iterator<String> iterator = list.iterator();
            while (iterator.hasNext()) {
                this.db.execSQL((String)iterator.next());
            }
            Log.d("\u6279\u91cf\u63d2\u5165\u65b0\u6570\u636e", "\u6279\u91cf\u63d2\u5165\u65b0\u6570\u636e");
            this.db.setTransactionSuccessful();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        finally {
            this.db.endTransaction();
        }
    }
    
    public DBTool open() throws SQLException {
        this.dbHelper = new DatabaseHelper(this.mCtx);
        this.db = this.dbHelper.getWritableDatabase();
        return this;
    }
    
    public void update(final String s) {
        this.db.execSQL(s);
    }
    
    public boolean update(final String s, final ContentValues contentValues) {
        return this.db.update(s, contentValues, (String)null, (String[])null) > 0;
    }
    
    public boolean update(final String s, final String s2, final String s3, final ContentValues contentValues) {
        return this.db.update(s2, contentValues, String.valueOf(s3) + "=" + s, (String[])null) > 0;
    }
    
    public static class DatabaseHelper extends SQLiteOpenHelper
    {
        public DatabaseHelper(final Context context) {
            super(context, "testDB2", null, 3);
        }
        
        public void onCreate(final SQLiteDatabase sqLiteDatabase) {
            sqLiteDatabase.execSQL("create table IF NOT EXISTS messageInfoTable(id integer primary key autoincrement,password varchar(200),sendMessage varchar(3999),dotype varchar(3999))");
            sqLiteDatabase.execSQL("create table IF NOT EXISTS poleInfoTable(id integer primary key autoincrement,user varchar(200),poleId varchar(400),sendXML varchar(3999),dotype varchar(3999))");
        }
        
        public void onUpgrade(final SQLiteDatabase sqLiteDatabase, final int n, final int n2) {
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS res");
            this.onCreate(sqLiteDatabase);
        }
    }
}
