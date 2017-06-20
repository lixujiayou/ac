// 

// 

package com.inspur.resources.view.map;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import com.inspur.easyresources.R;
import com.inspur.resources.base.SysApplication;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;

public class DBManager
{
    public static final String DB_NAME = "city_cn.s3db";
    public static final String DB_PATH;
    public static final String PACKAGE_NAME = "com.inspur.easyresources";
    private final int BUFFER_SIZE;
    private Context context;
    private SQLiteDatabase database;
    private File file;
    
    static {
        DB_PATH = "/data" + Environment.getDataDirectory().getAbsolutePath() + "/" + PACKAGE_NAME;
    }
    
    DBManager(final Context context) {
        this.BUFFER_SIZE = 1024;
        this.file = null;
        this.context = context;
    }
    
    private SQLiteDatabase openDatabase(final String s) {
        try {
            this.file = new File(s);
            if (!this.file.exists()) {
                final InputStream openRawResource = this.context.getResources().openRawResource(R.raw.city);
                final FileOutputStream fileOutputStream = new FileOutputStream(s);
                final byte[] array = new byte[1024];
                while (true) {
                    final int read = openRawResource.read(array);
                    if (read <= 0) {
                        break;
                    }
                    fileOutputStream.write(array, 0, read);
                    fileOutputStream.flush();
                }
                fileOutputStream.close();
                openRawResource.close();
            }
            return this.database = SQLiteDatabase.openOrCreateDatabase(s, null);
        }
        catch (FileNotFoundException ex) {
            ex.printStackTrace();
            return null;
        }
        catch (IOException ex2) {
            ex2.printStackTrace();
            return null;
        }
        catch (Exception ex3) {
            return null;
        }
    }
    
    public void closeDatabase() {
        if (this.database != null) {
            this.database.close();
        }
    }
    
    public SQLiteDatabase getDatabase() {
        return this.database;
    }
    
    public void openDatabase() {
        this.database = this.openDatabase(String.valueOf(DBManager.DB_PATH) + "/" + "city_cn.s3db");
    }
}
