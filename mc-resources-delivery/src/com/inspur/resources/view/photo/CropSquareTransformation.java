// 

// 

package com.inspur.resources.view.photo;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.squareup.picasso.Transformation;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.text.Layout.Alignment;
import android.text.StaticLayout;
import android.text.TextPaint;

public class CropSquareTransformation implements Transformation
{
    @Override
    public String key() {
        return "square()";
    }
    
    @Override
    public Bitmap transform(final Bitmap bitmap) {
        final int width = bitmap.getWidth();
        final Bitmap bitmap2 = Bitmap.createBitmap(width, bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        final Canvas canvas = new Canvas(bitmap2);
        canvas.drawBitmap(bitmap, 0.0f, 0.0f, (Paint)null);
        final Typeface create = Typeface.create("\u5b8b\u4f53", 1);
        final TextPaint textPaint = new TextPaint();
       // textPaint.setColor(-65536);
        textPaint.setTypeface(create);
        textPaint.setTextSize(22.0f);
        new StaticLayout((CharSequence)("123.123/45.45\n" + new SimpleDateFormat("yyyy-MM-dd").format(new Date(System.currentTimeMillis()))), textPaint, width, Alignment.ALIGN_NORMAL, 1.0f, 0.0f, true).draw(canvas);
      //  canvas.save(31);
        canvas.restore();
        if (bitmap2 != bitmap) {
            bitmap.recycle();
        }
        return bitmap2;
    }
}
