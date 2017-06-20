// 

// 

package com.inspur.resources.base;

import android.os.Message;
import android.os.Handler;

public class FrameAnimationController
{
    public static final int ANIMATION_FRAME_DURATION = 16;
    private static final int MSG_ANIMATE = 1000;
    private static final Handler mHandler;
    
    static {
        mHandler = new AnimationHandler();
    }
    
    private FrameAnimationController() {
        throw new UnsupportedOperationException();
    }
    
    public static void requestAnimationFrame(final Runnable obj) {
        final Message message = new Message();
        message.what = 1000;
        message.obj = obj;
        FrameAnimationController.mHandler.sendMessageDelayed(message, 16L);
    }
    
    public static void requestFrameDelay(final Runnable obj, final long n) {
        final Message message = new Message();
        message.what = 1000;
        message.obj = obj;
        FrameAnimationController.mHandler.sendMessageDelayed(message, n);
    }
    
    private static class AnimationHandler extends Handler
    {
        public void handleMessage(final Message message) {
            switch (message.what) {
                case 1000: {
                    if (message.obj != null) {
                        ((Runnable)message.obj).run();
                        return;
                    }
                    break;
                }
            }
        }
    }
}
