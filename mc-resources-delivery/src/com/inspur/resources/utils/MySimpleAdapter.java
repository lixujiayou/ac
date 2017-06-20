// 

// 

package com.inspur.resources.utils;

import android.app.Activity;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.ImageView;
import android.text.style.ForegroundColorSpan;
import android.text.SpannableString;
import android.widget.TextView;
import android.widget.Checkable;
import android.view.View;
import android.content.Context;
import android.widget.SimpleAdapter.ViewBinder;
import android.view.LayoutInflater;
import java.util.Map;
import java.util.List;
import android.widget.SimpleAdapter;

public class MySimpleAdapter extends SimpleAdapter
{
    private List<? extends Map<String, ?>> mData;
    private String[] mFrom;
    private LayoutInflater mInflater;
    private int mResource;
    private int[] mTo;
    private SimpleAdapter.ViewBinder mViewBinder;
    
    public MySimpleAdapter(final Context context, final List<? extends Map<String, ?>> mData, final int mResource, final String[] mFrom, final int[] mTo) {
        super(context, (List)mData, mResource, mFrom, mTo);
        this.mData = mData;
        this.mResource = mResource;
        this.mFrom = mFrom;
        this.mTo = mTo;
        this.mInflater = (LayoutInflater)context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
    }
    
    private void bindView(int i, final View view) {
        final Map map = (Map)this.mData.get(i);
        if (map != null) {
            final SimpleAdapter.ViewBinder mViewBinder = this.mViewBinder;
            final View[] array = (View[])view.getTag();
            final String[] mFrom = this.mFrom;
            int length;
            View view2;
            Object value;
            String string;
            String text;
            boolean setViewValue;
            SpannableString text2;
            boolean b;
            for (length = this.mTo.length, i = 0; i < length; ++i) {
                view2 = array[i];
                if (view2 != null) {
                    value = map.get(mFrom[i]);
                    if (value == null) {
                        string = "";
                    }
                    else {
                        string = value.toString();
                    }
                    text = string;
                    if (string == null) {
                        text = "";
                    }
                    setViewValue = false;
                    if (mViewBinder != null) {
                        setViewValue = mViewBinder.setViewValue(view2, (Object)value, text);
                    }
                    if (!setViewValue) {
                        if (view2 instanceof Checkable) {
                            if (!(value instanceof Boolean)) {
                                throw new IllegalStateException(String.valueOf(view2.getClass().getName()) + " should be bound to a Boolean, not a " + ((Boolean)(Object)value).getClass());
                            }
                            ((Checkable)view2).setChecked((boolean)(Boolean)(Object)value);
                        }
                        else if (view2 instanceof TextView) {
                            ((TextView)view2).setText((CharSequence)text);
                            if (((TextView)view2).getText().toString().equals("MRM")) {
                                text2 = new SpannableString((CharSequence)"MRM");
                              //  text2.setSpan((Object)new ForegroundColorSpan(-65536), 0, 1, 33);
                                ((TextView)view2).setText((CharSequence)text2);
                            }
                        }
                        else if (view2 instanceof ImageView) {
                            if (value instanceof Integer) {
                                this.setViewImage((ImageView)view2, (Integer)value);
                            }
                            else {
                                b = (value instanceof byte[]);
                            }
                        }
                        else {
                            if (!(view2 instanceof RatingBar)) {
                                throw new IllegalStateException(String.valueOf(((RatingBar)view2).getClass().getName()) + " is not a " + " view that can be bounds by this SimpleAdapter");
                            }
                            ((RatingBar)view2).setRating(Float.parseFloat(value.toString()));
                        }
                    }
                }
            }
        }
    }
    
    private View createViewFromResource(final int n, View inflate, final ViewGroup viewGroup, int i) {
        if (inflate == null) {
            inflate = this.mInflater.inflate(i, viewGroup, false);
            final int[] mTo = this.mTo;
            final int length = mTo.length;
            final View[] tag = new View[length];
            for (i = 0; i < length; ++i) {
                tag[i] = inflate.findViewById(mTo[i]);
                if (tag[i] != null) {
                    tag[i].setVisibility(View.VISIBLE);
                }
            }
            inflate.setTag((Object)tag);
        }
        this.bindView(n, inflate);
        return inflate;
    }
    
    public int getCount() {
        return this.mData.size();
    }
    
    public View getView(final int n, final View view, final ViewGroup viewGroup) {
        return this.createViewFromResource(n, view, viewGroup, this.mResource);
    }
    
    public void setListData(final List<? extends Map<String, ?>> mData) {
        this.mData = mData;
    }
    
    public void setViewImage(final ImageView imageView, final int imageResource) {
        imageView.setImageResource(imageResource);
    }
}
