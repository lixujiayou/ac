// 

// 

package com.inspur.resources.view.point;

import com.inspur.resources.bean.PointInfoBean;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import cn.trinea.android.common.util.ResourceUtils;

public class Point extends FrameLayout
{
    private boolean isLvkuang;
    private Context mContext;
    private Handler mHandler;
    private String pid;
    public PointInfoBean point;
    private ImageView pointImage;
    private TextView pointNumber;
    private int pointSize;
    private int pstate;
    
    public Point(final Context mContext, final Handler mHandler) {
        super(mContext);
        this.pointImage = null;
        this.pointNumber = null;
        this.pstate = 0;
        this.pid = "";
        this.pointSize = 0;
        this.isLvkuang = false;
        this.point = null;
        this.mContext = null;
        this.mHandler = null;
        this.mContext = mContext;
        this.mHandler = mHandler;
        this.pointImage = new ImageView(this.getContext());
        (this.pointNumber = new TextView(this.getContext())).setTextSize(10.0f);
        this.pointNumber.setTextColor(-1);
        this.pointNumber.setGravity(17);
        final FrameLayout.LayoutParams frameLayout$LayoutParams = new FrameLayout.LayoutParams(-1, -1);
        frameLayout$LayoutParams.setMargins(0, 0, 0, 0);
        this.addView((View)this.pointImage, (ViewGroup.LayoutParams)frameLayout$LayoutParams);
        this.addView((View)this.pointNumber, (ViewGroup.LayoutParams)frameLayout$LayoutParams);
    }
    
    public String getPid() {
        return this.pid;
    }
    
    public int getPointSize() {
        return this.pointSize;
    }
    
    public int getPstate() {
        return this.pstate;
    }
    
    public void setLvkuang(final boolean isLvkuang) {
        this.isLvkuang = isLvkuang;
        this.pointImage.setImageResource(getResources().getIdentifier("lvkuangk", "drawable", getContext().getPackageName()));
    }
    
    public void setPid(final String s) {
        this.pid = s;
        this.pointNumber.setText((CharSequence)s);
    }
    
    public void setPointInfo(final PointInfoBean point) {
        this.point = point;
    }
    
    public void setPointSize(final int pointSize) {
        this.pointSize = pointSize;
        this.pointImage.setLayoutParams((ViewGroup.LayoutParams)new FrameLayout.LayoutParams(pointSize, pointSize));
    }
    
    public void setPstate(final int pstate) {
        this.pstate = pstate;
        if (pstate == 1) {
            this.pointImage.setImageResource(this.getResources().getIdentifier("duanzi_1", "drawable", getContext().getPackageName()));
        }
        else if (pstate == 2) {
            this.pointImage.setImageResource(this.getResources().getIdentifier("duanzi_2", "drawable", getContext().getPackageName()));
        }
        else if (pstate == 4) {
            this.pointImage.setImageResource(this.getResources().getIdentifier("duanzi_4", "drawable", getContext().getPackageName()));
        }
        else {
            this.pointImage.setImageResource(this.getResources().getIdentifier("duanzi_1", "drawable", getContext().getPackageName()));
        }
        this.pointImage.setOnClickListener((View.OnClickListener)new View.OnClickListener() {
            public void onClick(final View view) {
                final Message message = new Message();
                message.what = 3;
                message.obj = Point.this.point;
                Point.this.mHandler.sendMessage(message);
            }
        });
    }
}
