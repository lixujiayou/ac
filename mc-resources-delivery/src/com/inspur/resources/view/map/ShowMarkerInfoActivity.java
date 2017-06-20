// 

// 

package com.inspur.resources.view.map;

import com.inspur.easyresources.R;
import com.inspur.resources.base.BaseDialog;
import com.inspur.resources.bean.PoleInfoBean;

import android.content.Context;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class ShowMarkerInfoActivity extends BaseDialog
{
    private int i;
    private ImageView iv_right;
    PoleInfoBean pole;
    private TextView show_test_1;
    private TextView show_test_2;
    private TextView show_test_3;
    private TextView show_test_4;
    private TextView show_test_5;
    private TextView show_test_6;
    
    public ShowMarkerInfoActivity(final Context context) {
        super(context);
        this.iv_right = null;
        this.show_test_1 = null;
        this.show_test_2 = null;
        this.show_test_3 = null;
        this.show_test_4 = null;
        this.show_test_5 = null;
        this.show_test_6 = null;
        this.pole = null;
    }
    
    public ShowMarkerInfoActivity(final Context context, final int i) {
        super(context);
        this.iv_right = null;
        this.show_test_1 = null;
        this.show_test_2 = null;
        this.show_test_3 = null;
        this.show_test_4 = null;
        this.show_test_5 = null;
        this.show_test_6 = null;
        this.pole = null;
        this.i = i;
    }
    
    public ShowMarkerInfoActivity(final Context context, final PoleInfoBean pole) {
        super(context);
        this.iv_right = null;
        this.show_test_1 = null;
        this.show_test_2 = null;
        this.show_test_3 = null;
        this.show_test_4 = null;
        this.show_test_5 = null;
        this.show_test_6 = null;
        this.pole = null;
        this.pole = pole;
    }
    
    protected void closeDialog() {
        this.dismiss();
    }
    
    protected void onCreate(final Bundle bundle) {
        super.onCreate(bundle);
        this.requestWindowFeature(1);
        this.setContentView(R.layout.show_marker_info_dialog);
        final Display defaultDisplay = this.getWindow().getWindowManager().getDefaultDisplay();
        this.getWindow().setLayout((int)(defaultDisplay.getWidth() * 0.9), (int)(defaultDisplay.getHeight() * 0.8));
        (this.iv_right = (ImageView)this.findViewById(R.id.iv_right)).setOnClickListener(new CloseOnClick());
        this.show_test_1 = (TextView)this.findViewById(R.id.show_test_1);
        this.show_test_2 = (TextView)this.findViewById(R.id.show_test_2);
        this.show_test_3 = (TextView)this.findViewById(R.id.show_test_3);
        this.show_test_4 = (TextView)this.findViewById(R.id.show_test_4);
        this.show_test_5 = (TextView)this.findViewById(R.id.show_test_5);
        this.show_test_6 = (TextView)this.findViewById(R.id.show_test_6);
        this.show_test_1.setText((CharSequence)this.pole.getPoleNameSub());
        this.show_test_2.setText((CharSequence)this.pole.getPoleCode());
        this.show_test_3.setText(getContext().getResources().getStringArray(R.array.make_share_org)[pole.getSubordinateUnits()]);
        this.show_test_4.setText((CharSequence)this.pole.getAreaName());
        this.show_test_5.setText((CharSequence)this.pole.getPoleAddress());
        this.show_test_6.setText((CharSequence)(String.valueOf(this.pole.getPoleLongitude()) + "/" + this.pole.getPoleLatitude()));
    }
    
    class CloseOnClick implements View.OnClickListener
    {
        public void onClick(final View view) {
            ShowMarkerInfoActivity.this.closeDialog();
        }
    }
}
