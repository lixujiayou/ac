// 

// 

package com.inspur.resources.view.tube;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.inspur.easyresources.R;

public class ChooseTupianDialog extends Dialog
{
    private Button btn_1;
    private Button btn_2;
    private Button btn_3;
    private Button btn_4;
    private Button btn_5;
    private Button btn_6;
    private Button btn_7;
    private Button btn_8;
    private Handler mHandler;
    private Button[] thisBtn;
    
    public ChooseTupianDialog(final Context context, final Handler mHandler) {
        super(context);
        this.btn_1 = null;
        this.btn_2 = null;
        this.btn_3 = null;
        this.btn_4 = null;
        this.btn_5 = null;
        this.btn_6 = null;
        this.btn_7 = null;
        this.btn_8 = null;
        this.thisBtn = new Button[8];
        this.mHandler = mHandler;
    }
    
    private void DialogSize() {
        final Display defaultDisplay = this.getWindow().getWindowManager().getDefaultDisplay();
        final WindowManager.LayoutParams attributes = this.getWindow().getAttributes();
        attributes.height = (int)(defaultDisplay.getHeight() * 0.95);
        attributes.width = (int)(defaultDisplay.getWidth() * 0.5);
    }
    
    private void init() {
        this.btn_1.setOnClickListener((View.OnClickListener)new View.OnClickListener() {
            public void onClick(final View view) {
                ChooseTupianDialog.this.mHandler.obtainMessage(1).sendToTarget();
                ChooseTupianDialog.this.closeDialog();
            }
        });
        this.btn_2.setOnClickListener((View.OnClickListener)new View.OnClickListener() {
            public void onClick(final View view) {
                ChooseTupianDialog.this.mHandler.obtainMessage(2).sendToTarget();
                ChooseTupianDialog.this.closeDialog();
            }
        });
        this.btn_3.setOnClickListener((View.OnClickListener)new View.OnClickListener() {
            public void onClick(final View view) {
                ChooseTupianDialog.this.mHandler.obtainMessage(3).sendToTarget();
                ChooseTupianDialog.this.closeDialog();
            }
        });
        this.btn_4.setOnClickListener((View.OnClickListener)new View.OnClickListener() {
            public void onClick(final View view) {
                ChooseTupianDialog.this.mHandler.obtainMessage(4).sendToTarget();
                ChooseTupianDialog.this.closeDialog();
            }
        });
        this.btn_5.setOnClickListener((View.OnClickListener)new View.OnClickListener() {
            public void onClick(final View view) {
                ChooseTupianDialog.this.mHandler.obtainMessage(5).sendToTarget();
                ChooseTupianDialog.this.closeDialog();
            }
        });
        this.btn_6.setOnClickListener((View.OnClickListener)new View.OnClickListener() {
            public void onClick(final View view) {
                ChooseTupianDialog.this.mHandler.obtainMessage(6).sendToTarget();
                ChooseTupianDialog.this.closeDialog();
            }
        });
        this.btn_7.setOnClickListener((View.OnClickListener)new View.OnClickListener() {
            public void onClick(final View view) {
                ChooseTupianDialog.this.mHandler.obtainMessage(7).sendToTarget();
                ChooseTupianDialog.this.closeDialog();
            }
        });
        this.btn_8.setOnClickListener((View.OnClickListener)new View.OnClickListener() {
            public void onClick(final View view) {
                ChooseTupianDialog.this.mHandler.obtainMessage(8).sendToTarget();
                ChooseTupianDialog.this.closeDialog();
            }
        });
    }
    
    protected void closeDialog() {
        this.dismiss();
    }
    
    protected void onCreate(final Bundle bundle) {
        super.onCreate(bundle);
        this.requestWindowFeature(1);
        this.setContentView(R.layout.dialog_choosetupian);
        this.btn_1 = (Button)this.findViewById(R.id.btn_1);
        this.btn_2 = (Button)this.findViewById(R.id.btn_2);
        this.btn_3 = (Button)this.findViewById(R.id.btn_3);
        this.btn_4 = (Button)this.findViewById(R.id.btn_4);
        this.btn_5 = (Button)this.findViewById(R.id.btn_5);
        this.btn_6 = (Button)this.findViewById(R.id.btn_6);
        this.btn_7 = (Button)this.findViewById(R.id.btn_7);
        this.btn_8 = (Button)this.findViewById(R.id.btn_8);
        this.thisBtn[0] = this.btn_1;
        this.thisBtn[1] = this.btn_2;
        this.thisBtn[2] = this.btn_3;
        this.thisBtn[3] = this.btn_4;
        this.thisBtn[4] = this.btn_5;
        this.thisBtn[5] = this.btn_6;
        this.thisBtn[6] = this.btn_7;
        this.thisBtn[7] = this.btn_8;
        this.init();
        this.DialogSize();
    }
}
