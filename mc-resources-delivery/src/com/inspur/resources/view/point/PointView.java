// 

// 

package com.inspur.resources.view.point;

import android.view.View;
import android.util.Log;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import java.lang.reflect.Array;
import android.content.Context;

import java.util.List;

import com.inspur.resources.bean.PointInfoBean;

import android.os.Handler;
import android.annotation.SuppressLint;
import android.widget.GridLayout;

@SuppressLint({ "NewApi" })
public class PointView extends GridLayout
{
    public static boolean isChange;
    public static PointView pointView;
    public String FirstPanelPos;
    private Handler mHandler;
    public int odm;
    public int panshu;
    private List<PointInfoBean> pointListTest;
    public int pointWidth;
    private Point[][] pointsMap;
    public int pointsize;
    public int pointsize_2;
    private int screenHeight;
    private int screenWidth;
    
    static {
        PointView.pointView = null;
        PointView.isChange = false;
    }
    
    public PointView(final Context context, final Handler mHandler) {
        super(context);
        this.panshu = 0;
        this.pointsize = 0;
        this.pointsize_2 = this.pointsize + 2;
        this.FirstPanelPos = null;
        this.odm = 0;
        this.pointsMap = (Point[][])Array.newInstance(Point.class, this.panshu, this.pointsize_2);
        this.mHandler = null;
        this.pointWidth = 0;
        this.mHandler = mHandler;
        PointView.pointView = this;
        new DisplayMetrics();
        final DisplayMetrics displayMetrics = this.getResources().getDisplayMetrics();
        this.screenWidth = displayMetrics.widthPixels;
        this.screenHeight = displayMetrics.heightPixels;
    }
    
    public PointView(final Context context, final AttributeSet set) {
        super(context, set);
        this.panshu = 0;
        this.pointsize = 0;
        this.pointsize_2 = this.pointsize + 2;
        this.FirstPanelPos = null;
        this.odm = 0;
        this.pointsMap = (Point[][])Array.newInstance(Point.class, this.panshu, this.pointsize_2);
        this.mHandler = null;
        this.pointWidth = 0;
        PointView.pointView = this;
        new DisplayMetrics();
        final DisplayMetrics displayMetrics = this.getResources().getDisplayMetrics();
        this.screenWidth = displayMetrics.widthPixels;
        this.screenHeight = displayMetrics.heightPixels;
    }
    
    public PointView(final Context context, final AttributeSet set, final int n) {
        super(context, set, n);
        this.panshu = 0;
        this.pointsize = 0;
        this.pointsize_2 = this.pointsize + 2;
        this.FirstPanelPos = null;
        this.odm = 0;
        this.pointsMap = (Point[][])Array.newInstance(Point.class, this.panshu, this.pointsize_2);
        this.mHandler = null;
        this.pointWidth = 0;
        PointView.pointView = this;
        new DisplayMetrics();
        final DisplayMetrics displayMetrics = this.getResources().getDisplayMetrics();
        this.screenWidth = displayMetrics.widthPixels;
        this.screenHeight = displayMetrics.heightPixels;
    }
    
    private void changePointSize(final int pointSize) {
        Log.d("change", "change");
        for (int i = 0; i < this.panshu; ++i) {
            for (int j = 0; j < this.pointsize_2; ++j) {
                this.pointsMap[i][j].setPointSize(pointSize);
            }
        }
    }
    
    private PointInfoBean getP(final String s) {
        for (int i = 0; i < this.pointListTest.size(); ++i) {
            final PointInfoBean pointInfoBean;
            if ((pointInfoBean = this.pointListTest.get(i)).getPID().equals(s)) {
                return pointInfoBean;
            }
        }
        return null;
    }
    
    public static PointView getPointView() {
        return PointView.pointView;
    }
    
    public void ChangeBig() {
        this.pointWidth += 3;
        if (this.pointWidth * this.pointsize > this.screenWidth) {
            return;
        }
        this.changePointSize(this.pointWidth);
        PointMainActivity.getMainActivity().ShuzeshuomingSizeChange(PointMainActivity.getMainActivity().getShuzishuomingSize().get("width") + this.pointsize * 3, PointMainActivity.getMainActivity().getShuzishuomingSize().get("height") + 3);
    }
    
    public void addPoints(final int n, final int n2) {
        Log.d("add", "add");
        if (this.FirstPanelPos != null) {
            if (this.FirstPanelPos.equals("0")) {
                int n3 = 0;
                for (int i = 0; i < this.panshu; ++i) {
                    for (int j = 0; j < this.pointsize_2; ++j) {
                        final Point point = new Point(this.getContext(), this.mHandler);
                        if (j == 0 || j == this.pointsize_2 - 1) {
                            point.setLvkuang(true);
                            point.setPid(new StringBuilder(String.valueOf(i + 1)).toString());
                        }
                        else {
                            final PointInfoBean p2 = this.getP(this.getPID(this.odm, i + 1, j));
                            if(p2==null){
                            	continue;
                            }
                            point.setLvkuang(false);
                            point.setPid(p2.getPID().substring(2, 6));
                            point.setPstate(p2.getPSTAT());
                            point.setPointInfo(p2);
                            ++n3;
                        }
                        this.addView((View)point, n, n2);
                        this.pointsMap[i][j] = point;
                    }
                }
            }
            else {
                int n4 = this.pointListTest.size() - 1;
                for (int k = this.panshu - 1; k >= 0; --k) {
                    for (int l = 0; l < this.pointsize_2; ++l) {
                        final Point point2 = new Point(this.getContext(), this.mHandler);
                        if (l == 0 || l == this.pointsize_2 - 1) {
                            point2.setLvkuang(true);
                            point2.setPid(new StringBuilder(String.valueOf(k + 1)).toString());
                        }
                        else {
                            final PointInfoBean p3 = this.getP(this.getPID(this.odm, k + 1, l));
                            point2.setLvkuang(false);
                            point2.setPid(p3.getPID().substring(2, 6));
                            point2.setPstate(p3.getPSTAT());
                            point2.setPointInfo(p3);
                            --n4;
                        }
                        this.addView((View)point2, n, n2);
                        this.pointsMap[k][l] = point2;
                    }
                }
            }
        }
    }
    
    protected String getPID(final int n, final int n2, final int n3) {
        String s;
        if (n < 10) {
            s = "0" + n;
        }
        else {
            s = new StringBuilder().append(n).toString();
        }
        String s2;
        if (n2 < 10) {
            s2 = "0" + n2;
        }
        else {
            s2 = new StringBuilder().append(n2).toString();
        }
        String s3;
        if (n3 < 10) {
            s3 = "0" + n3;
        }
        else {
            s3 = new StringBuilder().append(n3).toString();
        }
        Log.d("111111111111111111111", "11==>" + s + s2 + s3);
        return String.valueOf(s) + s2 + s3;
    }
    
    public void initPointView() {
        this.setColumnCount(this.pointsize_2);
        this.setBackgroundColor(0);
    }
    
    protected void onSizeChanged(final int n, final int n2, final int n3, final int n4) {
        super.onSizeChanged(n, n2, n3, n4);
        Log.d("isChange", new StringBuilder(String.valueOf(PointView.isChange)).toString());
        this.addPoints(this.pointWidth, this.pointWidth);
    }
    
    public void setFirstPanelPos(final String firstPanelPos) {
        this.FirstPanelPos = firstPanelPos;
    }
    
    public void setMap() {
        this.pointsMap = (Point[][])Array.newInstance(Point.class, this.panshu, this.pointsize_2);
    }
    
    public void setODM(final int odm) {
        this.odm = odm;
    }
    
    public void setPanshu(final int panshu) {
        this.panshu = panshu;
    }
    
    public void setPointList(final List<PointInfoBean> pointListTest) {
        this.pointListTest = pointListTest;
    }
    
    public void setPointsize(final int pointsize) {
        this.pointsize = pointsize;
        this.pointsize_2 = pointsize + 2;
    }
}
