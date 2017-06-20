// 

// 

package com.inspur.resources.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.security.MessageDigest;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.text.Layout.Alignment;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.Log;

public class StrUtil
{
    public static final SimpleDateFormat dateFormat;
    public static final DecimalFormat decimalFormat;
    public static final DecimalFormat moneyFormat;
    public static final DecimalFormat numberFormat;
    public static final SimpleDateFormat timeFormat;
    public static final SimpleDateFormat timemiFormat;
    
    static {
        dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        timeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        timemiFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm ");
        moneyFormat = new DecimalFormat("#,##0.00");
        numberFormat = new DecimalFormat("#,##0");
        decimalFormat = new DecimalFormat("#,##0.00");
    }
    
    public static void CopyStream(final InputStream inputStream, final OutputStream outputStream) {
        try {
            final byte[] array = new byte[1024];
            while (true) {
                final int read = inputStream.read(array, 0, 1024);
                if (read == -1) {
                    break;
                }
                outputStream.write(array, 0, read);
                inputStream.close();
                outputStream.close();
            }
        }
        catch (Exception ex) {}
    }
    
    public static String GetPhotoRealName(String s, final int n) {
        s = s.split(",")[n];
        return String.valueOf(s.substring(0, s.indexOf("_temp"))) + ".jpg";
    }
    
    public static String[] StrForArray(final String s) {
        final int length = s.length();
        final String[] array = new String[length];
        for (int i = 0; i < length; ++i) {
            array[i] = s.substring(i, i + 1);
        }
        return array;
    }
    
    public static String balanceFormat(final double n) {
        if (n >= 0.0) {
            return StrUtil.decimalFormat.format(Math.abs(n));
        }
        return "<font color=\"red\">(" + StrUtil.decimalFormat.format(-n) + ")</font>";
    }
    
    public static final String bytesToHexString(final byte[] array) {
        if (array == null) {
            return "";
        }
        final StringBuffer sb = new StringBuffer(array.length);
        for (int i = 0; i < array.length; ++i) {
            final String hexString = Integer.toHexString(array[i] & 0xFF);
            if (hexString.length() < 2) {
                sb.append(0);
            }
            sb.append(hexString.toUpperCase());
        }
        return sb.toString();
    }
    
    public static String dateFormat(final Date date) {
        if (date == null) {
            return StrUtil.dateFormat.format(new Date());
        }
        return StrUtil.dateFormat.format(date);
    }
    
    public static String dateFormat(final Date date, final String s) {
        if (date == null) {
            return new SimpleDateFormat(s).format(new Date());
        }
        return new SimpleDateFormat(s).format(date);
    }
    
    public static Date dateFormat(final String s) {
        if (s == null || "".equals(s)) {
            return new Date();
        }
        try {
            return StrUtil.dateFormat.parse(s);
        }
        catch (Exception ex) {
            return new Date();
        }
    }
    
    public static String dateFormatForNull(final Date date) {
        if (date == null) {
            return "";
        }
        return StrUtil.timeFormat.format(date);
    }
    
    public static String dateOfBirthFormat(final String s) {
        if (s == null || "".equals(s)) {
            return null;
        }
        final StringBuffer sb = new StringBuffer(s);
        sb.insert(2, "/");
        return sb.toString();
    }
    
    public static Date dateTimeFormat(final String s) {
        if (s == null || "".equals(s)) {
            return new Date();
        }
        try {
            return StrUtil.timeFormat.parse(s);
        }
        catch (Exception ex) {
            return new Date();
        }
    }
    
    public static String decimalFormat(final double n) {
        return StrUtil.decimalFormat.format(n);
    }
    
    public static String decimalFormat(final long n) {
        return StrUtil.decimalFormat.format(n);
    }
    
    public static String decimalFormat(final Object o) {
        if (o == null) {
            return StrUtil.decimalFormat.format(0L);
        }
        return StrUtil.decimalFormat.format(o);
    }
    
    public static Bitmap decodeFile(final File file) {
        try {
            final BitmapFactory.Options bitmapFactory$Options = new BitmapFactory.Options();
            bitmapFactory$Options.inJustDecodeBounds = true;
            BitmapFactory.decodeStream((InputStream)new FileInputStream(file), (Rect)null, bitmapFactory$Options);
            int outWidth;
            int outHeight;
            int inSampleSize;
            for (outWidth = bitmapFactory$Options.outWidth, outHeight = bitmapFactory$Options.outHeight, inSampleSize = 1; outWidth / 2 >= 70 && outHeight / 2 >= 70; outWidth /= 2, outHeight /= 2, inSampleSize *= 2) {}
            final BitmapFactory.Options bitmapFactory$Options2 = new BitmapFactory.Options();
            bitmapFactory$Options2.inSampleSize = inSampleSize;
            return BitmapFactory.decodeStream((InputStream)new FileInputStream(file), (Rect)null, bitmapFactory$Options2);
        }
        catch (FileNotFoundException ex) {
            return null;
        }
    }
    
    public static String faceTagToFaceIndex(final String s) {
        String s2 = "0";
        if (s.equals("\u5317")) {
            s2 = "1";
        }
        else {
            if (s.equals("\u4e1c\u5317")) {
                return "2";
            }
            if (s.equals("\u4e1c")) {
                return "3";
            }
            if (s.equals("\u4e1c\u5357")) {
                return "4";
            }
            if (s.equals("\u5357")) {
                return "5";
            }
            if (s.equals("\u897f\u5357")) {
                return "6";
            }
            if (s.equals("\u897f")) {
                return "7";
            }
            if (s.equals("\u897f\u5317")) {
                return "8";
            }
        }
        return s2;
    }
    
    public static double getDistance(double hypot, final double n, final double n2, final double n3) {
        final double n4 = 0.0;
        final double n5 = (n + n3) / 2.0 * 3.141592653589793 / 180.0;
        try {
            hypot = Math.hypot((n2 - hypot) * 3.141592653589793 * 6371229.0 * Math.cos(n5) / 180.0, (n3 - n) * 3.141592653589793 * 6371229.0 / 180.0);
            return hypot / 1000.0;
        }
        catch (Exception ex) {
            hypot = n4;
            return hypot / 1000.0;
        }
    }
    
    public static String getLocalIpAddress() {
        try {
            final Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
            while (networkInterfaces.hasMoreElements()) {
                final Enumeration<InetAddress> inetAddresses = networkInterfaces.nextElement().getInetAddresses();
                while (inetAddresses.hasMoreElements()) {
                    final InetAddress inetAddress = inetAddresses.nextElement();
                    if (!inetAddress.isLoopbackAddress()) {
                        return inetAddress.getHostAddress().toString();
                    }
                }
            }
        }
        catch (SocketException ex) {
            ex.printStackTrace();
        }
        return null;
    }
    
    public static String getNDayBeforeCurrentDay(final int n) {
        final Calendar instance = Calendar.getInstance();
      //  instance.add(5, -n);
        return new SimpleDateFormat("yyyy-MM-dd").format(instance.getTime());
    }
    
    public static String moneyFormat(final double n) {
        return StrUtil.moneyFormat.format(n);
    }
    
    public static String moneyFormat(final long n) {
        return StrUtil.moneyFormat.format(n);
    }
    
    public static String moneyFormat(final Object o) {
        if (o == null) {
            return StrUtil.moneyFormat.format(0L);
        }
        return StrUtil.moneyFormat.format(o);
    }
    
    public static String negativeFormat(final double n) {
        if (n >= 0.0) {
            return StrUtil.decimalFormat.format(Math.abs(n));
        }
        return StrUtil.decimalFormat.format(-n);
    }
    
    public static String numberFormat(final double n) {
        return StrUtil.numberFormat.format(n);
    }
    
    public static String numberFormat(final double n, final String s) {
        return new DecimalFormat(s).format(n);
    }
    
    public static String numberFormat(final long n) {
        return StrUtil.numberFormat.format(n);
    }
    
    public static String numberFormat(final long n, final String s) {
        return new DecimalFormat(s).format(n);
    }
    
    public static String numberFormat(final Object o) {
        if (o == null) {
            return StrUtil.numberFormat.format(0L);
        }
        return StrUtil.numberFormat.format(o);
    }
    
    public static String numberFormat(final Object o, final String s) {
        if (o == null) {
            return new DecimalFormat(s).format(new Long(0L));
        }
        return new DecimalFormat(s).format(o);
    }
    
    public static String strToMD5(final String s) {
        final char[] array2;
        final char[] array = array2 = new char[16];
        array2[0] = '0';
        array2[1] = '1';
        array2[2] = '2';
        array2[3] = '3';
        array2[4] = '4';
        array2[5] = '5';
        array2[6] = '6';
        array2[7] = '7';
        array2[8] = '8';
        array2[9] = '9';
        array2[10] = 'A';
        array2[11] = 'B';
        array2[12] = 'C';
        array2[13] = 'D';
        array2[14] = 'E';
        array2[15] = 'F';
        try {
            final byte[] bytes = s.getBytes();
            final MessageDigest instance = MessageDigest.getInstance("MD5");
            instance.update(bytes);
            final byte[] digest = instance.digest();
            final int length = digest.length;
            final char[] array3 = new char[length * 2];
            int i = 0;
            int n = 0;
            while (i < length) {
                final byte b = digest[i];
                final int n2 = n + 1;
                array3[n] = array[b >>> 4 & 0xF];
                n = n2 + 1;
                array3[n2] = array[b & 0xF];
                ++i;
            }
            return new String(array3).substring(8, 24);
        }
        catch (Exception ex) {
            return null;
        }
    }
    
    public static String stringFormat(final Object o) {
        if (o == null) {
            return "";
        }
        return o.toString();
    }
    
    public static String timeFormat(final Date date) {
        if (date == null) {
            return StrUtil.timeFormat.format(new Date());
        }
        return StrUtil.timeFormat.format(date);
    }
    
    public static String timemiFormat(final String s) {
        if (s == null || "".equals(s)) {
            return "";
        }
        try {
            return StrUtil.timemiFormat.format(StrUtil.timeFormat.parse(s));
        }
        catch (Exception ex) {
            return s;
        }
    }
    
    public static Bitmap watermarkBitmap(final Bitmap bitmap, String string, final String s, final int n) {
        Bitmap bitmap2;
        if (bitmap == null) {
            bitmap2 = null;
        }
        else {
            Log.d("\u56fe\u7247\u6dfb\u52a0\u6c34\u5370\u65b9\u6cd5", "\u56fe\u7247\u6dfb\u52a0\u6c34\u5370\u65b9\u6cd5");
            final int width = bitmap.getWidth();
            final Bitmap bitmap3 = Bitmap.createBitmap(width, bitmap.getHeight(), Bitmap.Config.ARGB_8888);
            final Canvas canvas = new Canvas(bitmap3);
            canvas.drawBitmap(bitmap, 0.0f, 0.0f, (Paint)null);
            final Typeface create = Typeface.create("\u5b8b\u4f53", 0);
            final TextPaint textPaint = new TextPaint();
          //  textPaint.setColor(-65536);
            textPaint.setTypeface(create);
            Log.d("textSize", new StringBuilder(String.valueOf(n)).toString());
            textPaint.setTextSize((float)n);
            final String format = new SimpleDateFormat("yyyy-MM-dd").format(new Date(System.currentTimeMillis()));
            String string2 = null;
            Label_0178: {
                if (string != null) {
                    string2 = string;
                    if (!string.equals("")) {
                        break Label_0178;
                    }
                }
                string2 = new StringBuilder(String.valueOf(ApplicationValue.lon)).toString();
            }
            Label_0210: {
                if (s != null) {
                    string = s;
                    if (!s.equals("")) {
                        break Label_0210;
                    }
                }
                string = new StringBuilder(String.valueOf(ApplicationValue.lat)).toString();
            }
            new StaticLayout((CharSequence)("\u7ecf\u7eac\u5ea6\uff1a" + string2 + "/" + string + "\n" + "\u65f6\u95f4\uff1a" + format), textPaint, width, Alignment.ALIGN_NORMAL, 1.0f, 0.0f, true).draw(canvas);
           // canvas.save(31);
            canvas.restore();
            if ((bitmap2 = bitmap3) != bitmap) {
                bitmap.recycle();
                return bitmap3;
            }
        }
        return bitmap2;
    }
}
