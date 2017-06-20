// 

// 

package com.inspur.resources.view.map;

import android.util.Log;
import android.database.Cursor;
import java.util.ArrayList;
import android.database.sqlite.SQLiteDatabase;
import android.content.Context;

public class DBhelper
{
    private Context context;
    private SQLiteDatabase db;
    private DBManager dbm;
    
    public DBhelper(final Context context) {
        this.context = context;
        this.dbm = new DBManager(context);
    }
    
    public ArrayList<Area> getCity(final String s) {
        this.dbm.openDatabase();
        this.db = this.dbm.getDatabase();
        final ArrayList<Area> list = new ArrayList<Area>();
        try {
            final Cursor rawQuery = this.db.rawQuery("select * from city where pcode='" + s + "'", (String[])null);
            rawQuery.moveToFirst();
            while (!rawQuery.isLast()) {
                final String string = rawQuery.getString(rawQuery.getColumnIndex("code"));
                final String name = new String(rawQuery.getBlob(2), "gbk");
                final Area area = new Area();
                area.setName(name);
                area.setCode(string);
                area.setPcode(s);
                list.add(area);
                rawQuery.moveToNext();
            }
            final String string2 = rawQuery.getString(rawQuery.getColumnIndex("code"));
            final String name2 = new String(rawQuery.getBlob(2), "gbk");
            final Area area2 = new Area();
            area2.setName(name2);
            area2.setCode(string2);
            area2.setPcode(s);
            list.add(area2);
            this.dbm.closeDatabase();
            this.db.close();
            return list;
        }
        catch (Exception ex) {
            return null;
        }
    }
    
    public ArrayList<Area> getDistrict(String string) {
        this.dbm.openDatabase();
        this.db = this.dbm.getDatabase();
        final ArrayList<Area> list = new ArrayList<Area>();
        while (true) {
            try {
                string = "select * from district where pcode='" + string + "'";
                final Cursor rawQuery = this.db.rawQuery(string, (String[])null);
                if (rawQuery.moveToFirst()) {
                    while (!rawQuery.isLast()) {
                        final String string2 = rawQuery.getString(rawQuery.getColumnIndex("code"));
                        final String name = new String(rawQuery.getBlob(2), "gbk");
                        final Area area = new Area();
                        area.setName(name);
                        area.setPcode(string2);
                        list.add(area);
                        rawQuery.moveToNext();
                    }
                    final String string3 = rawQuery.getString(rawQuery.getColumnIndex("code"));
                    final String name2 = new String(rawQuery.getBlob(2), "gbk");
                    final Area area2 = new Area();
                    area2.setName(name2);
                    area2.setPcode(string3);
                    list.add(area2);
                }
                this.dbm.closeDatabase();
                this.db.close();
                return list;
            }
            catch (Exception ex) {
                Log.i("wer", ex.toString());
                continue;
            }
        }
    }
    
    public ArrayList<Area> getProvince() {
        this.dbm.openDatabase();
        this.db = this.dbm.getDatabase();
        final ArrayList<Area> list = new ArrayList<Area>();
        try {
            final Cursor rawQuery = this.db.rawQuery("select * from province", (String[])null);
            rawQuery.moveToFirst();
            while (!rawQuery.isLast()) {
                final String string = rawQuery.getString(rawQuery.getColumnIndex("code"));
                final String name = new String(rawQuery.getBlob(2), "gbk");
                final Area area = new Area();
                area.setName(name);
                area.setCode(string);
                list.add(area);
                rawQuery.moveToNext();
            }
            final String string2 = rawQuery.getString(rawQuery.getColumnIndex("code"));
            final String name2 = new String(rawQuery.getBlob(2), "gbk");
            final Area area2 = new Area();
            area2.setName(name2);
            area2.setCode(string2);
            list.add(area2);
            this.dbm.closeDatabase();
            this.db.close();
            return list;
        }
        catch (Exception ex) {
            return null;
        }
    }
}
