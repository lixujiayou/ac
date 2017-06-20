// 

// 

package com.inspur.resources.view.map;

import android.view.View;
import android.content.Context;
import java.util.ArrayList;
import android.widget.PopupWindow;

public class CascadingMenuPopWindow extends PopupWindow
{
    private ArrayList<Area> areas;
    private CascadingMenuView cascadingMenuView;
    private Context context;
    private CascadingMenuViewOnSelectListener menuViewOnSelectListener;
    
    public CascadingMenuPopWindow(final Context context, final ArrayList<Area> areas) {
        super(context);
        this.areas = null;
        this.context = context;
        this.areas = areas;
        this.init();
    }
    
    public void init() {
        this.setContentView((View)(this.cascadingMenuView = new CascadingMenuView(this.context, this.areas)));
        this.setWidth(-1);
        this.setHeight(-1);
        this.cascadingMenuView.setCascadingMenuViewOnSelectListener(new MCascadingMenuViewOnSelectListener());
    }
    
    public void setMenuItems(final ArrayList<Area> areas) {
        this.areas = areas;
    }
    
    public void setMenuViewOnSelectListener(final CascadingMenuViewOnSelectListener menuViewOnSelectListener) {
        this.menuViewOnSelectListener = menuViewOnSelectListener;
    }
    
    class MCascadingMenuViewOnSelectListener implements CascadingMenuViewOnSelectListener
    {
        @Override
        public void getValue(final Area area) {
            if (CascadingMenuPopWindow.this.menuViewOnSelectListener != null) {
                CascadingMenuPopWindow.this.menuViewOnSelectListener.getValue(area);
                CascadingMenuPopWindow.this.dismiss();
            }
        }
    }
}
