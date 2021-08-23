package com.example.haha.customview.view.flexible.util;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.RelativeLayout;

import androidx.interpolator.view.animation.FastOutSlowInInterpolator;

/**
 * Created by haha on 2019/7/1.
 */

public class PullAnimatorUtil {

    /**
     * @param headerView 头部布局
     * @param headerHeight 头部高度
     * @param headerWidth 头部宽度
     * @param offsetY 手滑动的距离
     * @param maxHeight 头部局的最大高度
     * */
    public static void pullAnimator(View headerView,int headerHeight,int headerWidth,int offsetY,int maxHeight){
        if (headerView == null){
            return;
        }
        int pullOffset = (int)Math.pow(offsetY,0.8);
        int newHeight = Math.min(maxHeight + headerHeight,pullOffset+headerHeight);
        int newWidth = (int)((((float)newHeight / headerHeight))*headerWidth);
        headerView.getLayoutParams().height = newHeight;
        headerView.getLayoutParams().width = newWidth;
        int margin = (newWidth - headerWidth)/2;
        if (headerView.getParent() != null && headerView.getParent() instanceof RelativeLayout){
            margin = 0;
        }
        headerView.setTranslationX(-margin);
        headerView.requestLayout();
    }

    /**
     * 高度重置动画
     *
     * @param headerView 头部布局
     * @param headerHeight 头部高度
     * @param headerWidth 头部宽度
     * */
    public static void resetAnimator(final View headerView,final int headerHeight,int headerWidth){
        if (headerView == null){
            return;
        }
        ValueAnimator heightAnimator = ValueAnimator.ofInt(headerView.getLayoutParams().height,headerHeight); //动画的变化范围
        heightAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int height = (int)animation.getAnimatedValue();
                headerView.getLayoutParams().height = height;
            }
        });
        ValueAnimator widthAnimator = ValueAnimator.ofInt(headerView.getLayoutParams().width,headerWidth);
        widthAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int width = (int) animation.getAnimatedValue();
                headerView.getLayoutParams().width = width;
            }
        });
        ValueAnimator translationAnimator = ValueAnimator.ofInt((int)headerView.getTranslationX(),0);
        translationAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int translation = (int) animation.getAnimatedValue();
                headerView.setTranslationY(translation);
                headerView.requestLayout();
            }
        });
        AnimatorSet set = new AnimatorSet();
        set.setInterpolator(new FastOutSlowInInterpolator());
        set.setDuration(100);
        set.play(heightAnimator).with(widthAnimator).with(translationAnimator);
        set.start();
    }

    /**
     * 下拉时 刷新控件动画
     *
     * @param refreshView 下拉布局
     * @param offsetY 下拉高度
     * @param refreshViewHeight 下拉布局高度
     * @param maxRefreshPullHeight 最大下拉高度
     * */
    public static void pullRefreshAnimator(View refreshView,int offsetY,int refreshViewHeight,int maxRefreshPullHeight){
        if (refreshView == null){
            return;
        }
        int pullOffset = (int)Math.pow(offsetY,0.9);
        int newHeight = Math.min(maxRefreshPullHeight,pullOffset);
        refreshView.setTranslationY(-refreshViewHeight+newHeight);
        refreshView.setRotation(pullOffset);
        refreshView.requestLayout();
    }

    private static ObjectAnimator mRefreshingAnimator;

    /**
     * 刷新动画
     * 一直转圈圈
     *
     * @param refreshView
     * */
    public static void onRefreshing(View refreshView){
        float rotation = refreshView.getRotation();
        mRefreshingAnimator = ObjectAnimator.ofFloat(refreshView,"rotation",rotation,rotation+360);
        mRefreshingAnimator.setDuration(1000);
        mRefreshingAnimator.setInterpolator(new LinearInterpolator());
        mRefreshingAnimator.setRepeatMode(ValueAnimator.RESTART);
        mRefreshingAnimator.setRepeatCount(-1);
        mRefreshingAnimator.start();
    }

    public static void resetRefreshView(View refreshView, int refreshViewHeight, Animator.AnimatorListener animatorListener){
        if (mRefreshingAnimator != null){
            mRefreshingAnimator.cancel();
        }
        float translation = refreshView.getTranslationY();
        ObjectAnimator animator = ObjectAnimator.ofFloat(refreshView,"translationY",translation,-refreshViewHeight);
        animator.setDuration(500);
        animator.setInterpolator(new LinearInterpolator());
        animator.addListener(animatorListener);
        animator.start();
    }
}
