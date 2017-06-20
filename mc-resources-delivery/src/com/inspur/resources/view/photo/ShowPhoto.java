// 

// 

package com.inspur.resources.view.photo;

import com.squareup.picasso.Picasso;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

public class ShowPhoto extends Dialog
{
    private Button close_btn;
    private ImageView image;
    Context mContext;
    String urlStr;
    
    public ShowPhoto(final Context mContext, final Bitmap bitmap, final int n) {
        super(mContext, n);
        this.image = null;
        this.close_btn = null;
        this.mContext = mContext;
    }
    
    public ShowPhoto(final Context mContext, final String urlStr) {
        super(mContext);
        this.image = null;
        this.close_btn = null;
        this.mContext = mContext;
        this.urlStr = urlStr;
    }
    
    protected void onCreate(final Bundle bundle) {
        super.onCreate(bundle);
        this.requestWindowFeature(1);
        final Display defaultDisplay = this.getWindow().getWindowManager().getDefaultDisplay();
        final WindowManager.LayoutParams attributes = this.getWindow().getAttributes();
        attributes.height = (int)(defaultDisplay.getHeight() * 0.8);
        attributes.width = (int)(defaultDisplay.getWidth() * 0.9);
                ShowPhoto.this.dismiss();
    }
}
