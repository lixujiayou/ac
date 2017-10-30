package com.inspur.widget;

/**
 * Created by lixu on 2017/8/22.
 */

import android.content.Context;
import android.os.Environment;



import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import okhttp3.ResponseBody;
import retrofit2.Response;

/** * @Description: 描述 * @AUTHOR 刘楠 Create By 2016/10/27 0027 15:56 */
public class FileUtils {
     public static File createFile(Context context,int type){

         File file=null;
         String FILE_SEPARATOR = "/";
         String FILE_NAME_line = "test.db";
         String FILE_NAME_ROUTE = "route.db";  //我的


         String FILE_PATH = Environment.getExternalStorageDirectory()+ FILE_SEPARATOR+"inspur" + FILE_SEPARATOR;

         if(type == 1) {
             String state = Environment.getExternalStorageState();
             File dir = new File(FILE_PATH);

             if (!dir.exists()) {
                 dir.mkdirs();
             }

             File file1 = new File(FILE_PATH, FILE_NAME_line);
             if (file1.exists()) {
                 file1.delete();
             }

             if (state.equals(Environment.MEDIA_MOUNTED)) {

                 file = new File(Environment.getExternalStorageDirectory() + FILE_SEPARATOR + "inspur" + FILE_SEPARATOR + "/test.db");

             } else {

                 //       file = new File(context.getCacheDir().getAbsolutePath()+"/test.db");
                 file = new File(Environment.getExternalStorageDirectory() + FILE_SEPARATOR + "inspur" + FILE_SEPARATOR + "/test.db");

             }

         }else{
             String state = Environment.getExternalStorageState();
             File dir = new File(FILE_PATH);

             if (!dir.exists()) {
                 dir.mkdirs();
             }

             File file1 = new File(FILE_PATH, FILE_NAME_ROUTE);
             if (file1.exists()) {
                 file1.delete();
             }

             if (state.equals(Environment.MEDIA_MOUNTED)) {

                 file = new File(Environment.getExternalStorageDirectory() + FILE_SEPARATOR + "inspur" + FILE_SEPARATOR + "/route.db");

             } else {

                 //       file = new File(context.getCacheDir().getAbsolutePath()+"/test.db");
                 file = new File(Environment.getExternalStorageDirectory() + FILE_SEPARATOR + "inspur" + FILE_SEPARATOR + "/route.db");

             }

         }

                return file;

            }

     public static void writeFile2Disk(Response<ResponseBody> response, File file, HttpCallBack httpCallBack){
                long currentLength = 0; OutputStream os =null; InputStream is = response.body().byteStream();
                long totalLength =response.body().contentLength();
                try {
                    os = new FileOutputStream(file);
                    int len ;
                    byte [] buff = new byte[1024];
                    while((len=is.read(buff))!=-1){
                        os.write(buff,0,len);
                        currentLength+=len;
                        httpCallBack.onLoading(currentLength,totalLength);
                    }
                      //   httpCallBack.onLoading(currentLength,totalLength,true);
                    } catch(FileNotFoundException e) { e.printStackTrace();
                    } catch(IOException e) { e.printStackTrace();
                    } finally { if(os!=null){ try { os.close();
                    } catch(IOException e) { e.printStackTrace();
                    }
                    } if(is!=null){
                        try { is.close();
                        } catch(IOException e) {
                            e.printStackTrace();
                        }
                    }
                    }
            }
        }
