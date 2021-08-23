package com.example.haha.customview.view;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

import androidx.annotation.Nullable;

import com.example.haha.customview.R;

/**
 * Created by haha on 2020/7/6.
 */

public class BlowLoadingView extends View {

    private float mCurrentRotationAngle = 0f;
    private int[] mCircleColors;        //小圆得颜色列表
    private float mRotationRadius;
    private float mCircleRadius;
    private Paint mPaint;
    private int mCenterX,mCenterY;
    //代表当前状态所画动画
    private LoadingState mLoadingState;
    public BlowLoadingView(Context context) {
        this(context,null);
    }

    public BlowLoadingView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public BlowLoadingView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mCircleColors = getContext().getResources().getIntArray(R.array.splash_circle_colors);
    }

    private boolean mInitParams = false;
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (!mInitParams)
            initParams();
        //怎么让圆旋转起来
        if (mLoadingState == null){
            mLoadingState = new Rotation();
        }
        mLoadingState.draw(canvas);
    }

    private void initParams() {
        mInitParams = true;
        mRotationRadius = getMeasuredWidth()/4;
        mCircleRadius = mRotationRadius/8;
        mPaint = new Paint();
        mPaint.setDither(true);
        mPaint.setAntiAlias(true);
        mCenterX = getMeasuredWidth()/2;
        mCenterY = getMeasuredHeight()/2;
    }

    ValueAnimator animator;
    private void drawRotationAnimator(Canvas canvas) {
        //一个变量不断去改变，打算采用属性动画 旋转得是0-360
        if (animator == null){
            animator = ObjectAnimator.ofFloat(0,2*(float)Math.PI);
            animator.setDuration(3000);
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    mCurrentRotationAngle = (float) animation.getAnimatedValue();
                    //重新绘制

                    invalidate();
                }
            });
            //不断反复执行
            animator.setInterpolator(new LinearInterpolator());
            animator.setRepeatCount(-1);
            animator.start();
        }


        canvas.drawColor(Color.WHITE);
        //画六个圆
        double initAngle = Math.PI*2/mCircleColors.length;
        for (int i = 0;i<mCircleColors.length;i++) {
            mPaint.setColor(mCircleColors[i]);
            //当前得角度 = 初始角度 +  旋转角度
            double angle = initAngle*i + mCurrentRotationAngle;
            int cx = (int) (mCenterX + (mRotationRadius*Math.cos(angle)));
            int cy = (int) (mCenterY + (mRotationRadius*Math.sin(angle)));
            canvas.drawCircle(cx,cy,mCircleRadius,mPaint);
        }
    }
    public void disappear(){
        //开始聚合
        animator.cancel();

        invalidate();
    }

    public abstract class LoadingState{
        public abstract void draw(Canvas canvas);
    }
    public class Rotation extends LoadingState{

        @Override
        public void draw(Canvas canvas) {
            //一个变量不断去改变，打算采用属性动画 旋转得是0-360
            if (animator == null){
                animator = ObjectAnimator.ofFloat(0,2*(float)Math.PI);
                animator.setDuration(3000);
                animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        mCurrentRotationAngle = (float) animation.getAnimatedValue();
                        //重新绘制

                        invalidate();
                    }
                });
                //不断反复执行
                animator.setInterpolator(new LinearInterpolator());
                animator.setRepeatCount(-1);
                animator.start();
            }


            canvas.drawColor(Color.WHITE);
            //画六个圆
            double initAngle = Math.PI*2/mCircleColors.length;
            for (int i = 0;i<mCircleColors.length;i++) {
                mPaint.setColor(mCircleColors[i]);
                //当前得角度 = 初始角度 +  旋转角度
                double angle = initAngle*i + mCurrentRotationAngle;
                int cx = (int) (mCenterX + (mRotationRadius*Math.cos(angle)));
                int cy = (int) (mCenterY + (mRotationRadius*Math.sin(angle)));
                canvas.drawCircle(cx,cy,mCircleRadius,mPaint);
            }
        }
    }

    public class Merge extends LoadingState{

        @Override
        public void draw(Canvas canvas) {
        }
    }

    public class Gather extends LoadingState{

        @Override
        public void draw(Canvas canvas) {
        }
    }
}
