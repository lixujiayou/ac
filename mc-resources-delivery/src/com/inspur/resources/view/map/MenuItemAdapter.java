// 

// 

package com.inspur.resources.view.map;

import java.util.ArrayList;
import android.view.LayoutInflater;
import android.widget.TextView;
import android.view.ViewGroup;
import android.view.View;
import android.graphics.drawable.Drawable;
import android.view.View.OnClickListener;
import java.util.List;
import android.content.Context;
import android.widget.BaseAdapter;

import com.inspur.easyresources.R;

public class MenuItemAdapter extends BaseAdapter
{
    private Context mContext;
    private List<Area> mListData;
    private OnItemClickListener mOnItemClickListener;
    private int normalDrawbleId;
    private View.OnClickListener onClickListener;
    private Drawable selectedDrawble;
    private int selectedPos;
    private String selectedText;
    private float textSize;
    
    public MenuItemAdapter(final Context mContext, final List<Area> mListData, final int n, final int normalDrawbleId) {
        this.selectedPos = -1;
        this.selectedText = "";
        this.mContext = mContext;
        this.mListData = mListData;
        this.selectedDrawble = this.mContext.getResources().getDrawable(n);
        this.normalDrawbleId = normalDrawbleId;
        this.init();
    }
    
    static /* synthetic */ void access$0(final MenuItemAdapter menuItemAdapter, final int selectedPos) {
        menuItemAdapter.selectedPos = selectedPos;
    }
    
    private void init() {
        this.onClickListener = (View.OnClickListener)new View.OnClickListener() {
            public void onClick(final View view) {
                MenuItemAdapter.access$0(MenuItemAdapter.this, (Integer)view.getTag());
                MenuItemAdapter.this.setSelectedPosition(MenuItemAdapter.this.selectedPos);
                if (MenuItemAdapter.this.mOnItemClickListener != null) {
                    MenuItemAdapter.this.mOnItemClickListener.onItemClick(view, MenuItemAdapter.this.selectedPos);
                }
            }
        };
    }
    
    public int getCount() {
        return this.mListData.size();
    }
    
    public Object getItem(final int n) {
        return this.mListData.get(n);
    }
    
    public long getItemId(final int n) {
        return 0L;
    }
    
    public int getSelectedPosition() {
        if (this.mListData != null && this.selectedPos < this.mListData.size()) {
            return this.selectedPos;
        }
        return -1;
    }
    
    public View getView(final int n, final View view, final ViewGroup viewGroup) {
        TextView textView;
        if (view == null) {
            textView = (TextView)LayoutInflater.from(this.mContext).inflate(R.layout.design_navigation_menu_item, viewGroup, false);
        }
        else {
            textView = (TextView)view;
        }
        textView.setTag((Object)n);
        String name;
        final String s = name = "";
        if (this.mListData != null) {
            name = s;
            if (n < this.mListData.size()) {
                name = this.mListData.get(n).getName();
            }
        }
        if (name.contains("\u4e0d\u9650")) {
            textView.setText((CharSequence)"\u4e0d\u9650");
        }
        else {
            textView.setText((CharSequence)name);
        }
        textView.setTextSize(2, this.textSize);
        if (this.selectedText != null && this.selectedText.equals(name)) {
            textView.setBackgroundDrawable(this.selectedDrawble);
        }
        else {
            textView.setBackgroundDrawable(this.mContext.getResources().getDrawable(this.normalDrawbleId));
        }
        textView.setPadding(20, 0, 0, 0);
        textView.setOnClickListener(this.onClickListener);
        return (View)textView;
    }
    
    public void setOnItemClickListener(final OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }
    
    public void setSelectedPosition(final int selectedPos) {
        if (this.mListData != null && selectedPos < this.mListData.size()) {
            this.selectedPos = selectedPos;
            this.selectedText = this.mListData.get(selectedPos).getName();
            this.notifyDataSetChanged();
        }
    }
    
    public void setSelectedPositionNoNotify(final int selectedPos, final ArrayList<Area> mListData) {
        this.selectedPos = selectedPos;
        this.mListData = mListData;
        if (this.mListData != null && selectedPos < this.mListData.size()) {
            this.selectedText = this.mListData.get(selectedPos).getName();
        }
    }
    
    public void setTextSize(final float textSize) {
        this.textSize = textSize;
    }
    
    public interface OnItemClickListener
    {
        void onItemClick(final View p0, final int p1);
    }
}
