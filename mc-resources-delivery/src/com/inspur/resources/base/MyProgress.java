// 

// 

package com.inspur.resources.base;

import android.graphics.Rect;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.content.Context;
import android.graphics.Paint;
import android.widget.ProgressBar;

public class MyProgress extends ProgressBar
{
    Paint mPaint;
    String text;
    
    public MyProgress(final Context context) {
        super(context);
        System.out.println("1");
        this.initText();
    }
    
    public MyProgress(final Context context, final AttributeSet set) {
        super(context, set);
        System.out.println("3");
        this.initText();
    }
    
    public MyProgress(final Context context, final AttributeSet set, final int n) {
        super(context, set, n);
        System.out.println("2");
        this.initText();
    }
    
    private void initText() {
      //  (this.mPaint = new Paint()).setColor(-16777216);
    }
    
    private void setText() {
        this.setText(this.getProgress());
    }
    
    private void setText(final int n) {
        this.text = String.valueOf(String.valueOf(n * 100 / this.getMax())) + "%";
    }
    
    protected void onDraw(final Canvas canvas) {
        synchronized (this) {
            super.onDraw(canvas);
            final Rect rect = new Rect();
            this.mPaint.getTextBounds(this.text, 0, this.text.length(), rect);
            canvas.drawText(this.text, (float)(this.getWidth() / 2 - rect.centerX()), (float)(this.getHeight() / 2 - rect.centerY()), this.mPaint);
        }
    }
    
    public void setProgress(final int n) {
        synchronized (this) {
            this.setText(n);
            super.setProgress(n);
        }
    }
}
