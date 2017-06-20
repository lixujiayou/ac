// 

// 

package com.inspur.resources.base;

import android.content.DialogInterface;
import android.view.View;
import android.app.AlertDialog;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface.OnCancelListener;
import android.content.Context;
import android.app.Dialog;

import com.inspur.easyresources.R;

public class BaseDialog extends Dialog
{
    private Context mContext;
    private OnClickListener mNegativeListener;
    private OnCancelListener mOnCancelListener;
    private OnClickListener mPositiveListener;
    
    public BaseDialog(final Context mContext) {
        super(mContext);
        this.mPositiveListener = null;
        this.mNegativeListener = null;
        this.mOnCancelListener = null;
        this.mContext = mContext;
    }
    
    public BaseDialog(final Context mContext, final int n) {
        super(mContext, n);
        this.mPositiveListener = null;
        this.mNegativeListener = null;
        this.mOnCancelListener = null;
        this.mContext = mContext;
    }
    
    protected void showAlertDialog(final int n, final int text, final int text2, final OnClickListener mPositiveListener, final int text3, final OnClickListener mNegativeListener, final String text4, final OnCancelListener mOnCancelListener, final Object... array) {
        final AlertDialog create = new AlertDialog.Builder(this.mContext).create();
        create.show();
        create.setContentView(R.layout.n_alertdialog);
        this.mPositiveListener = mPositiveListener;
        this.mNegativeListener = mNegativeListener;
        this.mOnCancelListener = mOnCancelListener;
        final TextView textView = (TextView)create.findViewById(R.id.title);
        final TextView textView2 = (TextView)create.findViewById(R.id.text_body);
        final Button button = (Button)create.findViewById(R.id.left_button);
        button.setVisibility(View.GONE);
        final Button button2 = (Button)create.findViewById(R.id.right_button);
        button2.setVisibility(View.GONE);
        textView.setText(text);
        textView2.setText((CharSequence)text4);
        button.setText(text2);
        if (text3 >= 0) {
            button2.setText(text3);
        }
        if (mPositiveListener != null) {
            button.setVisibility(View.VISIBLE);
            button.setOnClickListener(new View.OnClickListener() {
                public void onClick(final View view) {
                    if (BaseDialog.this.mPositiveListener != null) {
                        BaseDialog.this.mPositiveListener.onClick((DialogInterface)create, 0);
                    }
                }
            });
            if (mNegativeListener != null) {
                button2.setVisibility(View.VISIBLE);
                button2.setOnClickListener(new View.OnClickListener() {
                    public void onClick(final View view) {
                        if (BaseDialog.this.mNegativeListener != null) {
                            BaseDialog.this.mNegativeListener.onClick((DialogInterface)create, 1);
                        }
                    }
                });
            }
            if (this.mOnCancelListener != null) {
                create.setOnCancelListener((DialogInterface.OnCancelListener)new DialogInterface.OnCancelListener() {
                    public void onCancel(final DialogInterface dialogInterface) {
                        BaseDialog.this.mOnCancelListener.onCancel(dialogInterface);
                    }
                });
            }
            create.setCancelable(false);
            create.setCanceledOnTouchOutside(false);
        }
    }
}
