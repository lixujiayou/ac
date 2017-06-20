// 

// 

package com.inspur.resources.base;

import android.widget.CompoundButton;
import android.view.MotionEvent;
import android.graphics.Xfermode;
import android.graphics.Canvas;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.view.ViewConfiguration;
import android.util.AttributeSet;
import android.content.Context;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.view.ViewParent;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.Bitmap;
import android.widget.CheckBox;

public class SwitchButton extends CheckBox
{
    private final float EXTENDED_OFFSET_Y;
    private final int MAX_ALPHA;
    private final float VELOCITY;
    private int mAlpha;
    private float mAnimatedVelocity;
    private boolean mAnimating;
    private float mAnimationPosition;
    private Bitmap mBottom;
    private boolean mBroadcasting;
    private float mBtnInitPos;
    private Bitmap mBtnNormal;
    private float mBtnOffPos;
    private float mBtnOnPos;
    private float mBtnPos;
    private Bitmap mBtnPressed;
    private float mBtnWidth;
    private boolean mChecked;
    private int mClickTimeout;
    private Bitmap mCurBtnPic;
    private float mExtendOffsetY;
    private float mFirstDownX;
    private float mFirstDownY;
    private Bitmap mFrame;
    private Bitmap mMask;
    private float mMaskHeight;
    private float mMaskWidth;
    private OnCheckedChangeListener mOnCheckedChangeListener;
    private OnCheckedChangeListener mOnCheckedChangeWidgetListener;
    private Paint mPaint;
    private ViewParent mParent;
    private PerformClick mPerformClick;
    private float mRealPos;
    private RectF mSaveLayerRectF;
    private int mTouchSlop;
    private boolean mTurningOn;
    private float mVelocity;
    private PorterDuffXfermode mXfermode;
    
    public SwitchButton(final Context context) {
        this(context, null);
    }
    
    public SwitchButton(final Context context, final AttributeSet set) {
        this(context, set, 16842860);
    }
    
    public SwitchButton(final Context context, final AttributeSet set, final int n) {
        super(context, set, n);
        this.MAX_ALPHA = 255;
        this.mAlpha = 255;
        this.mChecked = false;
        this.VELOCITY = 350.0f;
        this.EXTENDED_OFFSET_Y = 15.0f;
        this.initView(context);
    }
    
    private void attemptClaimDrag() {
        this.mParent = this.getParent();
        if (this.mParent != null) {
            this.mParent.requestDisallowInterceptTouchEvent(true);
        }
    }
    
    private void doAnimation() {
        this.mAnimationPosition += this.mAnimatedVelocity * 16.0f / 1000.0f;
        if (this.mAnimationPosition <= this.mBtnOnPos) {
            this.stopAnimation();
            this.mAnimationPosition = this.mBtnOnPos;
            this.setCheckedDelayed(true);
        }
        else if (this.mAnimationPosition >= this.mBtnOffPos) {
            this.stopAnimation();
            this.mAnimationPosition = this.mBtnOffPos;
            this.setCheckedDelayed(false);
        }
        this.moveView(this.mAnimationPosition);
    }
    
    private float getRealPos(final float n) {
        return n - this.mBtnWidth / 2.0f;
    }
    
    private void initView(final Context context) {
        (this.mPaint = new Paint()).setColor(-1);
        final Resources resources = context.getResources();
        this.mClickTimeout = ViewConfiguration.getPressedStateDuration() + ViewConfiguration.getTapTimeout();
        this.mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        this.mBottom = BitmapFactory.decodeResource(resources, 2130837543);
        this.mBtnPressed = BitmapFactory.decodeResource(resources, 2130837545);
        this.mBtnNormal = BitmapFactory.decodeResource(resources, 2130837553);
        this.mFrame = BitmapFactory.decodeResource(resources, 2130837582);
        this.mMask = BitmapFactory.decodeResource(resources, 2130837636);
        this.mCurBtnPic = this.mBtnNormal;
        this.mBtnWidth = this.mBtnPressed.getWidth();
        this.mMaskWidth = this.mMask.getWidth();
        this.mMaskHeight = this.mMask.getHeight();
        this.mBtnOffPos = this.mBtnWidth / 2.0f;
        this.mBtnOnPos = this.mMaskWidth - this.mBtnWidth / 2.0f;
        float mBtnPos;
        if (this.mChecked) {
            mBtnPos = this.mBtnOnPos;
        }
        else {
            mBtnPos = this.mBtnOffPos;
        }
        this.mBtnPos = mBtnPos;
        this.mRealPos = this.getRealPos(this.mBtnPos);
        final float density = this.getResources().getDisplayMetrics().density;
        this.mVelocity = (int)(350.0f * density + 0.5f);
        this.mExtendOffsetY = (int)(15.0f * density + 0.5f);
        this.mSaveLayerRectF = new RectF(0.0f, this.mExtendOffsetY, (float)this.mMask.getWidth(), this.mMask.getHeight() + this.mExtendOffsetY);
        this.mXfermode = new PorterDuffXfermode(Mode.SRC_IN);
    }
    
    private void moveView(final float mBtnPos) {
        this.mBtnPos = mBtnPos;
        this.mRealPos = this.getRealPos(this.mBtnPos);
        this.invalidate();
    }
    
    private void setCheckedDelayed(final boolean b) {
        this.postDelayed((Runnable)new Runnable() {
            @Override
            public void run() {
                SwitchButton.this.setChecked(b);
            }
        }, 10L);
    }
    
    private void startAnimation(final boolean b) {
        this.mAnimating = true;
        float mVelocity;
        if (b) {
            mVelocity = -this.mVelocity;
        }
        else {
            mVelocity = this.mVelocity;
        }
        this.mAnimatedVelocity = mVelocity;
        this.mAnimationPosition = this.mBtnPos;
        new SwitchAnimation().run();
    }
    
    private void stopAnimation() {
        this.mAnimating = false;
    }
    
    public boolean isChecked() {
        return this.mChecked;
    }
    
    protected void onDraw(final Canvas canvas) {
        canvas.saveLayerAlpha(this.mSaveLayerRectF, this.mAlpha, 31);
        canvas.drawBitmap(this.mMask, 0.0f, this.mExtendOffsetY, this.mPaint);
        this.mPaint.setXfermode((Xfermode)this.mXfermode);
        canvas.drawBitmap(this.mBottom, this.mRealPos, this.mExtendOffsetY, this.mPaint);
        this.mPaint.setXfermode((Xfermode)null);
        canvas.drawBitmap(this.mFrame, 0.0f, this.mExtendOffsetY, this.mPaint);
        canvas.drawBitmap(this.mCurBtnPic, this.mRealPos, this.mExtendOffsetY, this.mPaint);
        canvas.restore();
    }
    
    protected void onMeasure(final int n, final int n2) {
        this.setMeasuredDimension((int)this.mMaskWidth, (int)(this.mMaskHeight + 2.0f * this.mExtendOffsetY));
    }
    
    public boolean onTouchEvent(final MotionEvent motionEvent) {
        boolean mTurningOn = true;
        final boolean b = false;
        final int action = motionEvent.getAction();
        final float x = motionEvent.getX();
        final float y = motionEvent.getY();
        final float abs = Math.abs(x - this.mFirstDownX);
        final float abs2 = Math.abs(y - this.mFirstDownY);
        switch (action) {
            case 0: {
                this.attemptClaimDrag();
                this.mFirstDownX = x;
                this.mFirstDownY = y;
                this.mCurBtnPic = this.mBtnPressed;
                float mBtnInitPos;
                if (this.mChecked) {
                    mBtnInitPos = this.mBtnOnPos;
                }
                else {
                    mBtnInitPos = this.mBtnOffPos;
                }
                this.mBtnInitPos = mBtnInitPos;
                break;
            }
            case 2: {
                final float n = motionEvent.getEventTime() - motionEvent.getDownTime();
                this.mBtnPos = this.mBtnInitPos + motionEvent.getX() - this.mFirstDownX;
                if (this.mBtnPos >= this.mBtnOffPos) {
                    this.mBtnPos = this.mBtnOffPos;
                }
                if (this.mBtnPos <= this.mBtnOnPos) {
                    this.mBtnPos = this.mBtnOnPos;
                }
                if (this.mBtnPos <= (this.mBtnOffPos - this.mBtnOnPos) / 2.0f + this.mBtnOnPos) {
                    mTurningOn = false;
                }
                this.mTurningOn = mTurningOn;
                this.mRealPos = this.getRealPos(this.mBtnPos);
                break;
            }
            case 1: {
                this.mCurBtnPic = this.mBtnNormal;
                final float n2 = motionEvent.getEventTime() - motionEvent.getDownTime();
                if (abs2 >= this.mTouchSlop || abs >= this.mTouchSlop || n2 >= this.mClickTimeout) {
                    this.startAnimation(!this.mTurningOn || b);
                    break;
                }
                if (this.mPerformClick == null) {
                    this.mPerformClick = new PerformClick();
                }
                if (!this.post((Runnable)this.mPerformClick)) {
                    this.performClick();
                    break;
                }
                break;
            }
        }
        this.invalidate();
        return this.isEnabled();
    }
    
    public boolean performClick() {
        this.startAnimation(!this.mChecked);
        return true;
    }
    
    public void setChecked(final boolean mChecked) {
        if (this.mChecked != mChecked) {
            this.mChecked = mChecked;
            float mBtnPos;
            if (mChecked) {
                mBtnPos = this.mBtnOnPos;
            }
            else {
                mBtnPos = this.mBtnOffPos;
            }
            this.mBtnPos = mBtnPos;
            this.mRealPos = this.getRealPos(this.mBtnPos);
            this.invalidate();
            if (!this.mBroadcasting) {
                this.mBroadcasting = true;
                if (this.mOnCheckedChangeListener != null) {
                    this.mOnCheckedChangeListener.onCheckedChanged((CompoundButton)this, this.mChecked);
                }
                if (this.mOnCheckedChangeWidgetListener != null) {
                    this.mOnCheckedChangeWidgetListener.onCheckedChanged((CompoundButton)this, this.mChecked);
                }
                this.mBroadcasting = false;
            }
        }
    }
    
    public void setEnabled(final boolean enabled) {
        int mAlpha;
        if (enabled) {
            mAlpha = 255;
        }
        else {
            mAlpha = 127;
        }
        this.mAlpha = mAlpha;
        super.setEnabled(enabled);
    }
    
    public void setOnCheckedChangeListener(final OnCheckedChangeListener mOnCheckedChangeListener) {
        this.mOnCheckedChangeListener = mOnCheckedChangeListener;
    }
    
    void setOnCheckedChangeWidgetListener(final OnCheckedChangeListener mOnCheckedChangeWidgetListener) {
        this.mOnCheckedChangeWidgetListener = mOnCheckedChangeWidgetListener;
    }
    
    public void toggle() {
        this.setChecked(!this.mChecked);
    }
    
    private final class PerformClick implements Runnable
    {
        @Override
        public void run() {
            SwitchButton.this.performClick();
        }
    }
    
    private final class SwitchAnimation implements Runnable
    {
        @Override
        public void run() {
            if (!SwitchButton.this.mAnimating) {
                return;
            }
            SwitchButton.this.doAnimation();
            FrameAnimationController.requestAnimationFrame(this);
        }
    }
}
