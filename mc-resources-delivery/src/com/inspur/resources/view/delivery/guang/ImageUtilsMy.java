package com.inspur.resources.view.delivery.guang;

	/*     */

/*     */

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;



import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
	/*     */

/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */ public class ImageUtilsMy
/*     */ {
/*  54 */   private static final String tag = ImageUtilsMy.class.toString();
/*     */   private static final int STANDARD_IMAGE_WIDTH = 720;
/*     */
/*  57 */   private ImageUtilsMy() { throw new AssertionError(); }
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */   public static byte[] bitmapToByte(Bitmap b)
/*     */   {
/*  67 */     if (b == null) {
/*  68 */       return null;
/*     */     }
/*     */
/*  71 */     ByteArrayOutputStream o = new ByteArrayOutputStream();
/*  72 */     b.compress(CompressFormat.PNG, 100, o);
/*  73 */     return o.toByteArray();
/*     */   }
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */   public static Bitmap byteToBitmap(byte[] b)
/*     */   {
/*  83 */     return (b == null) || (b.length == 0) ? null : BitmapFactory.decodeByteArray(b, 0, b.length);
/*     */   }
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */   public static Bitmap drawableToBitmap(Drawable d)
/*     */   {
/*  93 */     return d == null ? null : ((BitmapDrawable)d).getBitmap();
/*     */   }
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */   public static Drawable bitmapToDrawable(Bitmap b)
/*     */   {
/* 103 */     return b == null ? null : new BitmapDrawable(b);
/*     */   }
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */   public static byte[] drawableToByte(Drawable d)
/*     */   {
/* 113 */     return bitmapToByte(drawableToBitmap(d));
/*     */   }
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */   public static Drawable byteToDrawable(byte[] b)
/*     */   {
/* 123 */     return bitmapToDrawable(byteToBitmap(b));
/*     */   }
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */

/*     */
/*     */
/*     */   private static final int STANDARD_IMAGE_HEIGHT = 960;
/*     */


/*     */
/*     */
/*     */   public static Bitmap scaleImageTo(Bitmap org, int newWidth, int newHeight)
/*     */   {
/* 232 */     return scaleImage(org, newWidth / org.getWidth(), newHeight / org.getHeight());
/*     */   }
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */   public static Bitmap scaleImage(Bitmap org, float scaleWidth, float scaleHeight)
/*     */   {
/* 244 */     if (org == null) {
/* 245 */       return null;
/*     */     }
/*     */
/* 248 */     Matrix matrix = new Matrix();
/* 249 */     matrix.postScale(scaleWidth, scaleHeight);
/* 250 */     return Bitmap.createBitmap(org, 0, 0, org.getWidth(), org.getHeight(), matrix, true);
/*     */   }
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */   public static File scaleImageAndDeleteSrcImage(String imageFilePath, float scaleWidth, float scaleHeight)
/*     */   {
/* 262 */     File srcImageFile = new File(imageFilePath);
/* 263 */     if (!srcImageFile.exists())
/*     */     {
/* 265 */       return null;
/*     */     }
/*     */
/* 268 */     File scaledImageFile = scaleImage(imageFilePath, scaleWidth, scaleHeight);
/* 269 */     if (scaledImageFile == null)
/*     */     {
/* 271 */       return null;
/*     */     }
/*     */
/* 274 */     srcImageFile.delete();
/* 275 */     boolean boo = scaledImageFile.renameTo(srcImageFile);
/* 276 */     return boo ? srcImageFile : null;
/*     */   }
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */   public static File scaleImage(String imageFilePath, float scaleWidth, float scaleHeight)
/*     */   {
/* 288 */     File srcImageFile = new File(imageFilePath);
/* 289 */     if (!srcImageFile.exists())
/*     */     {
return null;
/*     */     }
/*     */
/* 294 */     String imageFileName = srcImageFile.getName();
/* 295 */     Bitmap srcBitmap = BitmapFactory.decodeFile(imageFilePath);
/* 296 */     Bitmap dstBitmap = scaleImage(srcBitmap, scaleWidth, scaleHeight);
/*     */
/*     */
/* 299 */     File parentDirFile = srcImageFile.getParentFile();
/* 300 */     File dstImageFile = null;
/* 301 */     int i = 0;
/*     */     do
/*     */     {
/* 304 */       dstImageFile = new File(parentDirFile, "ѹ��_" + i + "_" + imageFileName);
/* 305 */       i++;
/* 306 */     } while (dstImageFile.exists());
/*     */
/* 308 */     FileOutputStream fos = null;
/*     */     try {
/* 310 */       dstImageFile.createNewFile();
/* 311 */       fos = new FileOutputStream(dstImageFile);
/* 312 */       dstBitmap.compress(CompressFormat.JPEG, 50, fos);
/*     */
/* 314 */       fos.close();
/* 315 */       srcBitmap.recycle();
/* 316 */       dstBitmap.recycle();
/* 317 */       return dstImageFile;
/*     */     } catch (IOException e) {
/* 319 */       e.printStackTrace();
/*     */     } finally {
/* 321 */       if (fos != null) {
/*     */         try
/*     */         {
/* 324 */           fos.close();
/*     */         } catch (IOException e) {
/* 326 */           e.printStackTrace();
/*     */         }
/*     */       }
/*     */     }
/*     */
/* 331 */     return null;
/*     */   }
/*     */
/*     */
/*     */
/*     */
/*     */   private static final float STANDARD_WATERMARKER_TEXT_SIZE = 29.0F;
/*     */
/*     */
/*     */
/*     */   private static final float STANDARD_WATERMARKER_TEXT_MARGIN = 34.0F;
/*     */
/*     */
/*     */
/*     */   public static File addWaterMarker(String imageFilePath,String name, List<String> waterMarkerList)
/*     */   {
/* 347 */     File srcImageFile = new File(imageFilePath);
/* 348 */     if (!srcImageFile.exists())
/*     */     {
/* 350 */       return null;
/*     */     }
/*     */
/* 353 */     String imageFileName = srcImageFile.getName();
				Options op = new Options();
/* 354 */     Bitmap srcBitmap = BitmapFactory.decodeFile(imageFilePath,op);//.copy(Config.ARGB_8888, true);

//                srcBitmap =  yaSuoImage(srcBitmap);

                srcBitmap.copy(Config.ARGB_8888, true);

/* 355 */     int srcWidth = srcBitmap.getWidth();
/* 356 */     int srcHeight = srcBitmap.getHeight();
/*     */     float realTextMargin;
/*     */     float realTextSize;
/* 360 */     if (srcHeight > srcWidth)
/*     */     {
/* 362 */       realTextSize = (float)(srcWidth * 1.0D / 720.0D * 29.0D);
/* 363 */       realTextMargin = (float)(srcWidth * 1.0D / 720.0D * 34.0D);
/*     */     }
/*     */     else {
/* 366 */       realTextSize = (float)(srcHeight * 1.0D / 960.0D * 29.0D);
/* 367 */       realTextMargin = (float)(srcHeight * 1.0D / 960.0D * 34.0D);
/*     */     }
/*     */
/*     */
/* 371 */     Canvas canvas = new Canvas(srcBitmap);
/* 372 */     Paint paint = new Paint();
/* 373 */     Typeface font = Typeface.create(Typeface.SERIF, 1);
/* 374 */     paint.setTypeface(font);
/* 375 */     paint.setTextSize(realTextSize);
/* 376 */     paint.setColor(Color.RED);
/* 377 */     for (int i = 0; i < waterMarkerList.size(); i++)
/*     */     {
/* 379 */       canvas.drawText((String)waterMarkerList.get(i), 5.0F, srcHeight - (waterMarkerList.size() - i) * realTextMargin, paint);
/*     */     }
/*     */
/* 382 */     File parentDirFile = srcImageFile.getParentFile();


/* 383 */     File dstImageFile = null;
/* 384 */     int i = 0;
/*     */     do
/*     */     {
        Log.i("qzzzzzzz","循环");
/* 387 */       dstImageFile = new File(parentDirFile,  imageFileName);
/* 388 */       i++;
/* 389 */     } while (dstImageFile.exists());
/*     */
/* 391 */     FileOutputStream fos = null;
/*     */     try {
/* 393 */       dstImageFile.createNewFile();
/* 394 */       fos = new FileOutputStream(dstImageFile);
/*     */
/* 396 */       fos.write(bitmapToByte(srcBitmap));
/* 397 */       fos.close();
/* 398 */       srcBitmap.recycle();
/* 399 */       return dstImageFile;
/*     */     } catch (IOException e) {
/* 401 */       e.printStackTrace();
/*     */     } finally {
/* 403 */       if (fos != null) {
/*     */         try
/*     */         {
/* 406 */           fos.close();
/*     */         } catch (IOException e) {
/* 408 */           e.printStackTrace();
/*     */         }
/*     */       }
/*     */     }
/*     */
/* 413 */     return null;
/*     */   }
/*     */
/*     */   public static File addWaterMarkerAndDeleteSrcImage(String imageFilePath, List<String> waterMarkerList)
/*     */   {
/* 418 */     File srcImageFile = new File(imageFilePath);
/* 419 */     if (!srcImageFile.exists())
/*     */     {
/* 421 */       return null;
/*     */     }
/*     */
/* 424 */     File waterImageFile = addWaterMarker(imageFilePath,"waterMarker"+srcImageFile.getName(), waterMarkerList);
/* 425 */     if (waterImageFile == null)
/*     */     {
/* 427 */       return null;
/*     */     }
/*     */
/* 430 */     srcImageFile.delete();
/* 431 */     boolean boo = waterImageFile.renameTo(srcImageFile);
/* 432 */     return boo ? srcImageFile : null;
/*     */   }
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */   private static void closeInputStream(InputStream s)
/*     */   {
/* 692 */     if (s == null) {
/* 693 */       return;
/*     */     }
/*     */     try
/*     */     {
/* 697 */       s.close();
/*     */     } catch (IOException e) {
/* 699 */       throw new RuntimeException("IOException occurred. ", e);
/*     */     }
/*     */   }
    /**
     * 压缩图片
     */
    private static Bitmap yaSuoImage(Bitmap image){
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        if(image == null){
            return null;
        }
        image.compress(Bitmap.CompressFormat.JPEG, 30, out);

        float zoom = (float)Math.sqrt(10 * 1024 / (float)out.toByteArray().length);

        Matrix matrix = new Matrix();
        matrix.setScale(zoom, zoom);

        Bitmap result = Bitmap.createBitmap(image, 0, 0, image.getWidth(), image.getHeight(), matrix, true);

        out.reset();
        result.compress(Bitmap.CompressFormat.JPEG, 30, out);

        while(out.toByteArray().length > 10 * 1024){
            System.out.println(out.toByteArray().length);
            matrix.setScale(0.9f, 0.9f);
            result = Bitmap.createBitmap(result, 0, 0, result.getWidth(), result.getHeight(), matrix, true);
            out.reset();
            result.compress(Bitmap.CompressFormat.JPEG, 30, out);
        }
        return result;
    }

/*     */ }

