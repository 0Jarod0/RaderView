package com.example.haha.customview.view.viewpager;

import android.os.Build;
import android.view.View;

import androidx.viewpager.widget.ViewPager;

/**
 * Created by haha on 2020/3/13.
 */

public class ScaleTransformer implements ViewPager.PageTransformer {

    public static final float MAX_SCALE = 1.0f;
    public static final float MIN_SCALE  = 0.9f;

    public static final float MAX_ALPHA = 1f;
    public static final float MIN_ALPHA = 1f;

    @Override
    public void transformPage(View page, float position) {
        if (position < -1){
            position = -1;
        }else if (position > 1){
            position = 1;
        }

        float temScale = position < 0 ? 1 + position : 1 - position;

        float scaleSlope = (MAX_SCALE - MIN_SCALE)/1;
        float alphaSlope = (MAX_ALPHA - MIN_ALPHA)/1;
        float scaleValue = MIN_SCALE + temScale * scaleSlope;
        float alphaValue = MIN_ALPHA + temScale * alphaSlope;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB){
            page.setScaleY(scaleValue);
            page.setAlpha(alphaValue);
        }
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT){
            page.getParent().requestLayout();
        }
    }
}
