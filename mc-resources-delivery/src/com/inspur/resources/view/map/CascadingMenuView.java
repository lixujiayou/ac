// 

// 

package com.inspur.resources.view.map;

import android.util.Log;
import android.view.View;
import android.widget.ListAdapter;
import java.util.List;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.util.AttributeSet;
import java.util.ArrayList;
import android.widget.ListView;
import android.content.Context;
import android.widget.LinearLayout;

public class CascadingMenuView extends LinearLayout
{
    private static final String TAG;
    public static String selectAll;
    public static String selectFirst;
    private Context context;
    private DBhelper dBhelper;
    private ListView firstMenuListView;
    private MenuItemAdapter firstMenuListViewAdapter;
    private int firstPosition;
    private CascadingMenuViewOnSelectListener mOnSelectListener;
    private ArrayList<Area> menuItem;
    private ArrayList<Area> secondItem;
    private ListView secondMenuListView;
    private MenuItemAdapter secondMenuListViewAdapter;
    private int secondPosition;
    private String selectSecond;
    private String selectThird;
    private ArrayList<Area> thirdItem;
    private ListView thirdMenuListView;
    private MenuItemAdapter thirdMenuListViewAdapter;
    private int thirdPosition;
    
    static {
        TAG = CascadingMenuView.class.getSimpleName();
    }
    
    public CascadingMenuView(final Context context, final AttributeSet set) {
        super(context, set);
        this.thirdItem = new ArrayList<Area>();
        this.secondItem = new ArrayList<Area>();
        this.firstPosition = 0;
        this.secondPosition = 0;
        this.thirdPosition = 0;
        this.context = context;
        this.dBhelper = new DBhelper(context);
        this.init(context);
    }
    
    public CascadingMenuView(final Context context, final ArrayList<Area> menuItem) {
        super(context);
        this.thirdItem = new ArrayList<Area>();
        this.secondItem = new ArrayList<Area>();
        this.firstPosition = 0;
        this.secondPosition = 0;
        this.thirdPosition = 0;
        this.menuItem = menuItem;
        this.context = context;
        this.dBhelper = new DBhelper(context);
        this.init(context);
    }
    
    static /* synthetic */ void access$2(final CascadingMenuView cascadingMenuView, final ArrayList secondItem) {
        cascadingMenuView.secondItem = (ArrayList<Area>)secondItem;
    }
    
    static /* synthetic */ void access$5(final CascadingMenuView cascadingMenuView, final ArrayList thirdItem) {
        cascadingMenuView.thirdItem = (ArrayList<Area>)thirdItem;
    }
    
    static /* synthetic */ void access$7(final CascadingMenuView cascadingMenuView, final String selectSecond) {
        cascadingMenuView.selectSecond = selectSecond;
    }
    
    static /* synthetic */ void access$8(final CascadingMenuView cascadingMenuView, final String selectThird) {
        cascadingMenuView.selectThird = selectThird;
    }
    
    private void init(final Context context) {
      //  ((LayoutInflater)context.getSystemService("layout_inflater")).inflate(2130903173, (ViewGroup)this, true);
     ///   this.firstMenuListView = (ListView)this.findViewById(2131297591);
      //  this.secondMenuListView = (ListView)this.findViewById(2131297454);
      //  this.thirdMenuListView = (ListView)this.findViewById(2131296714);
        (this.firstMenuListViewAdapter = new MenuItemAdapter(context, this.menuItem, 2130837560, 2130837558)).setTextSize(17.0f);
        this.firstMenuListViewAdapter.setSelectedPositionNoNotify(this.firstPosition, this.menuItem);
        this.firstMenuListView.setAdapter((ListAdapter)this.firstMenuListViewAdapter);
        this.firstMenuListViewAdapter.setOnItemClickListener((MenuItemAdapter.OnItemClickListener)new MenuItemAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(final View view, final int n) {
                CascadingMenuView.this.secondItem.clear();
                CascadingMenuView.access$2(CascadingMenuView.this, CascadingMenuView.this.getSecondItem(CascadingMenuView.this.menuItem.get(n).getCode()));
                if (CascadingMenuView.this.secondItem != null) {
                    Log.i("wer", new StringBuilder().append(CascadingMenuView.this.secondItem.size()).toString());
                }
                CascadingMenuView.this.secondMenuListViewAdapter.notifyDataSetChanged();
                CascadingMenuView.this.secondMenuListViewAdapter.setSelectedPositionNoNotify(0, CascadingMenuView.this.secondItem);
                CascadingMenuView.this.thirdItem.clear();
                CascadingMenuView.access$5(CascadingMenuView.this, CascadingMenuView.this.getThirdItem(CascadingMenuView.this.secondItem.get(0).getCode()));
                CascadingMenuView.this.thirdMenuListViewAdapter.notifyDataSetChanged();
                CascadingMenuView.this.thirdMenuListViewAdapter.setSelectedPositionNoNotify(0, CascadingMenuView.this.thirdItem);
                CascadingMenuView.selectFirst = CascadingMenuView.this.menuItem.get(n).getName().trim();
            }
        });
        this.secondItem = this.getSecondItem(this.menuItem.get(this.firstPosition).getCode());
        Log.i("wer", this.secondItem.get(this.secondPosition).toString());
        this.thirdItem = this.getThirdItem(this.secondItem.get(this.secondPosition).getCode());
        (this.secondMenuListViewAdapter = new MenuItemAdapter(context, this.secondItem, 2130837560, 2130837558)).setTextSize(15.0f);
        this.secondMenuListViewAdapter.setSelectedPositionNoNotify(this.secondPosition, this.secondItem);
        this.secondMenuListView.setAdapter((ListAdapter)this.secondMenuListViewAdapter);
        this.secondMenuListViewAdapter.setOnItemClickListener((MenuItemAdapter.OnItemClickListener)new MenuItemAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(final View view, final int n) {
                CascadingMenuView.this.thirdItem.clear();
                CascadingMenuView.access$5(CascadingMenuView.this, CascadingMenuView.this.getThirdItem(CascadingMenuView.this.secondItem.get(n).getCode()));
                CascadingMenuView.this.thirdMenuListViewAdapter.notifyDataSetChanged();
                CascadingMenuView.this.thirdMenuListViewAdapter.setSelectedPositionNoNotify(0, CascadingMenuView.this.thirdItem);
                CascadingMenuView.access$7(CascadingMenuView.this, CascadingMenuView.this.secondItem.get(n).getName().trim());
            }
        });
        this.thirdItem = this.getThirdItem(this.secondItem.get(this.secondPosition).getCode());
        (this.thirdMenuListViewAdapter = new MenuItemAdapter(context, this.thirdItem, 2130837559, 2130837561)).setTextSize(13.0f);
        this.thirdMenuListViewAdapter.setSelectedPositionNoNotify(this.thirdPosition, this.thirdItem);
        this.thirdMenuListView.setAdapter((ListAdapter)this.thirdMenuListViewAdapter);
        this.thirdMenuListViewAdapter.setOnItemClickListener((MenuItemAdapter.OnItemClickListener)new MenuItemAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(final View view, final int n) {
                final Area area = CascadingMenuView.this.thirdItem.get(n);
                CascadingMenuView.access$8(CascadingMenuView.this, CascadingMenuView.this.thirdItem.get(n).getName().trim());
                if (CascadingMenuView.selectFirst == null) {
                    CascadingMenuView.selectFirst = "\u5317\u4eac\u5e02";
                    CascadingMenuView.access$7(CascadingMenuView.this, "");
                }
                else if (CascadingMenuView.this.selectSecond == null) {
                    if (CascadingMenuView.selectFirst.equals("\u5317\u4eac\u5e02")) {
                        CascadingMenuView.access$7(CascadingMenuView.this, "");
                    }
                    else if (CascadingMenuView.selectFirst.equals("\u5929\u6d25\u5e02")) {
                        CascadingMenuView.access$7(CascadingMenuView.this, "");
                    }
                    else if (CascadingMenuView.selectFirst.equals("\u6cb3\u5317\u7701")) {
                        CascadingMenuView.access$7(CascadingMenuView.this, "\u77f3\u5bb6\u5e84");
                    }
                    else if (CascadingMenuView.selectFirst.equals("\u5c71\u897f\u7701")) {
                        CascadingMenuView.access$7(CascadingMenuView.this, "\u592a\u539f\u5e02");
                    }
                    else if (CascadingMenuView.selectFirst.equals("\u5185\u8499\u53e4")) {
                        CascadingMenuView.access$7(CascadingMenuView.this, "\u547c\u548c\u6d69\u7279\u5e02");
                    }
                    else if (CascadingMenuView.selectFirst.equals("\u8fbd\u5b81\u7701")) {
                        CascadingMenuView.access$7(CascadingMenuView.this, "\u6c88\u9633\u5e02");
                    }
                    else if (CascadingMenuView.selectFirst.equals("\u5409\u6797\u7701")) {
                        CascadingMenuView.access$7(CascadingMenuView.this, "\u957f\u6625\u5e02");
                    }
                    else if (CascadingMenuView.selectFirst.equals("\u9ed1\u9f99\u6c5f")) {
                        CascadingMenuView.access$7(CascadingMenuView.this, "\u54c8\u5c14\u6ee8\u5e02");
                    }
                    else if (CascadingMenuView.selectFirst.equals("\u4e0a\u6d77\u5e02")) {
                        CascadingMenuView.access$7(CascadingMenuView.this, "");
                    }
                    else if (CascadingMenuView.selectFirst.equals("\u6c5f\u82cf\u7701")) {
                        CascadingMenuView.access$7(CascadingMenuView.this, "\u5357\u4eac\u5e02");
                    }
                    else if (CascadingMenuView.selectFirst.equals("\u6d59\u6c5f\u7701")) {
                        CascadingMenuView.access$7(CascadingMenuView.this, "\u676d\u5dde\u5e02");
                    }
                    else if (CascadingMenuView.selectFirst.equals("\u5b89\u5fbd\u7701")) {
                        CascadingMenuView.access$7(CascadingMenuView.this, "\u5408\u80a5\u5e02");
                    }
                    else if (CascadingMenuView.selectFirst.equals("\u798f\u5efa\u7701")) {
                        CascadingMenuView.access$7(CascadingMenuView.this, "\u798f\u5dde\u5e02");
                    }
                    else if (CascadingMenuView.selectFirst.equals("\u6c5f\u897f\u7701")) {
                        CascadingMenuView.access$7(CascadingMenuView.this, "\u5357\u660c\u5e02");
                    }
                    else if (CascadingMenuView.selectFirst.equals("\u5c71\u4e1c\u7701")) {
                        CascadingMenuView.access$7(CascadingMenuView.this, "\u6d4e\u5357\u5e02");
                    }
                    else if (CascadingMenuView.selectFirst.equals("\u6cb3\u5357\u7701")) {
                        CascadingMenuView.access$7(CascadingMenuView.this, "\u90d1\u5dde\u5e02");
                    }
                    else if (CascadingMenuView.selectFirst.equals("\u6e56\u5317\u7701")) {
                        CascadingMenuView.access$7(CascadingMenuView.this, "\u6b66\u6c49\u5e02");
                    }
                    else if (CascadingMenuView.selectFirst.equals("\u6e56\u5357\u7701")) {
                        CascadingMenuView.access$7(CascadingMenuView.this, "\u957f\u6c99\u5e02");
                    }
                    else if (CascadingMenuView.selectFirst.equals("\u5e7f\u4e1c\u7701")) {
                        CascadingMenuView.access$7(CascadingMenuView.this, "\u5e7f\u5dde\u5e02");
                    }
                    else if (CascadingMenuView.selectFirst.equals("\u5e7f\u897f")) {
                        CascadingMenuView.access$7(CascadingMenuView.this, "\u5357\u5b81\u5e02");
                    }
                    else if (CascadingMenuView.selectFirst.equals("\u6d77\u5357\u7701")) {
                        CascadingMenuView.access$7(CascadingMenuView.this, "\u6d77\u53e3\u5e02");
                        if (CascadingMenuView.this.selectSecond.equals("\u7701\u76f4\u8f96\u53bf\u7ea7\u884c\u653f\u5355\u4f4d")) {
                            CascadingMenuView.access$7(CascadingMenuView.this, "");
                        }
                    }
                    else if (CascadingMenuView.selectFirst.equals("\u91cd\u5e86\u5e02")) {
                        CascadingMenuView.access$7(CascadingMenuView.this, "");
                    }
                    else if (CascadingMenuView.selectFirst.equals("\u56db\u5ddd\u7701")) {
                        CascadingMenuView.access$7(CascadingMenuView.this, "\u6210\u90fd\u5e02");
                    }
                    else if (CascadingMenuView.selectFirst.equals("\u8d35\u5dde\u7701")) {
                        CascadingMenuView.access$7(CascadingMenuView.this, "\u8d35\u9633\u5e02");
                    }
                    else if (CascadingMenuView.selectFirst.equals("\u4e91\u5357\u7701")) {
                        CascadingMenuView.access$7(CascadingMenuView.this, "\u6606\u660e\u5e02");
                    }
                    else if (CascadingMenuView.selectFirst.equals("\u897f\u85cf")) {
                        CascadingMenuView.access$7(CascadingMenuView.this, "\u62c9\u8428\u5e02");
                    }
                    else if (CascadingMenuView.selectFirst.equals("\u9655\u897f\u7701")) {
                        CascadingMenuView.access$7(CascadingMenuView.this, "\u897f\u5b89\u5e02");
                    }
                    else if (CascadingMenuView.selectFirst.equals("\u7518\u8083\u7701")) {
                        CascadingMenuView.access$7(CascadingMenuView.this, "\u5170\u5dde\u5e02");
                    }
                    else if (CascadingMenuView.selectFirst.equals("\u9752\u6d77\u7701")) {
                        CascadingMenuView.access$7(CascadingMenuView.this, "\u897f\u5b81\u5e02");
                    }
                    else if (CascadingMenuView.selectFirst.equals("\u5b81\u590f")) {
                        CascadingMenuView.access$7(CascadingMenuView.this, "\u94f6\u5ddd\u5e02\u5e02");
                    }
                    else if (CascadingMenuView.selectFirst.equals("\u65b0\u7586")) {
                        CascadingMenuView.access$7(CascadingMenuView.this, "\u4e4c\u9c81\u6728\u9f50\u5e02");
                    }
                    else if (CascadingMenuView.selectFirst.equals("\u53f0\u6e7e\u7701")) {
                        CascadingMenuView.selectFirst = "";
                        CascadingMenuView.access$7(CascadingMenuView.this, "");
                    }
                    else if (CascadingMenuView.selectFirst.equals("\u9999\u6e2f")) {
                        CascadingMenuView.selectFirst = "";
                        CascadingMenuView.access$7(CascadingMenuView.this, "");
                    }
                    else if (CascadingMenuView.selectFirst.equals("\u6fb3\u95e8")) {
                        CascadingMenuView.selectFirst = "";
                        CascadingMenuView.access$7(CascadingMenuView.this, "");
                    }
                }
                if (CascadingMenuView.this.selectThird.equals("\u5e02\u8f96\u533a")) {
                    CascadingMenuView.access$8(CascadingMenuView.this, "");
                }
                if (CascadingMenuView.selectFirst.equals("\u5317\u4eac\u5e02") || CascadingMenuView.selectFirst.equals("\u5929\u6d25\u5e02") || CascadingMenuView.selectFirst.equals("\u4e0a\u6d77\u5e02") || CascadingMenuView.selectFirst.equals("\u91cd\u5e86\u5e02")) {
                    CascadingMenuView.access$7(CascadingMenuView.this, "");
                }
                CascadingMenuView.selectAll = String.valueOf(CascadingMenuView.selectFirst) + CascadingMenuView.this.selectSecond + CascadingMenuView.this.selectThird;
                if (CascadingMenuView.this.mOnSelectListener != null) {
                    CascadingMenuView.this.mOnSelectListener.getValue(area);
                }
                Log.e(CascadingMenuView.TAG, area.toString());
            }
        });
        this.setDefaultSelect();
    }
    
    public ArrayList<Area> getSecondItem(final String s) {
        return this.dBhelper.getCity(s);
    }
    
    public ArrayList<Area> getThirdItem(final String s) {
        return this.dBhelper.getDistrict(s);
    }
    
    public void setCascadingMenuViewOnSelectListener(final CascadingMenuViewOnSelectListener mOnSelectListener) {
        this.mOnSelectListener = mOnSelectListener;
    }
    
    public void setDefaultSelect() {
        this.firstMenuListView.setSelection(this.firstPosition);
        this.secondMenuListView.setSelection(this.secondPosition);
        this.thirdMenuListView.setSelection(this.thirdPosition);
    }
}
