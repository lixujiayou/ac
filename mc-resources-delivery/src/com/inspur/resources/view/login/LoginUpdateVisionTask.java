// 

// 

package com.inspur.resources.view.login;

import android.os.Message;
import android.os.Handler;

import com.inspur.resources.bean.NewVisionInfoBean;
import com.inspur.resources.http.WebObjectResult;
import com.inspur.resources.utils.ApplicationValue;
import com.inspur.resources.utils.BizFactory;

import android.content.Context;
import android.os.AsyncTask;

public class LoginUpdateVisionTask extends AsyncTask<String, Void, Integer>
{
    private Context mContext;
    private Handler mHandler;
    private NewVision newVision;
    
    public LoginUpdateVisionTask(final Context mContext, final Handler mHandler) {
        this.mContext = null;
        this.mHandler = null;
        this.newVision = BizFactory.getNewVision();
        this.mContext = mContext;
        this.mHandler = mHandler;
    }
    
    protected Integer doInBackground(final String... array) {
        while (true) {
            try {
                Thread.sleep(3000L);
                final WebObjectResult vision = this.newVision.getVision();
                if (vision.RESULT == 0) {
                    ApplicationValue.newVisionInfoBean = (NewVisionInfoBean)vision.obj;
                    return 0;
                }
            }
            catch (InterruptedException ex) {
                ex.printStackTrace();
                continue;
            }
            break;
        }
        return 1;
    }
    
    protected void onPostExecute(final Integer n) {
        super.onPostExecute(n);
        final Message message = new Message();
        message.what = n;
        this.mHandler.sendMessage(message);
    }
}
