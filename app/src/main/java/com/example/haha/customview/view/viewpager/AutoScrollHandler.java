package com.example.haha.customview.view.viewpager;

import android.os.Handler;
import android.os.Message;

import androidx.viewpager.widget.ViewPager;

/**
 * Created by haha on 2020/3/13.
 */

public class AutoScrollHandler extends Handler {

    public boolean pause = false;
    private ViewPager mViewPager;

    public AutoScrollHandler(ViewPager viewPager){
        mViewPager = viewPager;
    }

    @Override
    public void handleMessage(Message msg) {
        if (!pause){
            mViewPager.setCurrentItem(mViewPager.getCurrentItem()+1);
        }
        sendEmptyMessageDelayed(msg.what,3000);
    }

    public void startLoop(){
        pause = false;
        removeCallbacksAndMessages(null);
        sendEmptyMessageDelayed(1,3000);
    }

    public void stopLoop(){
        removeCallbacksAndMessages(null);
    }
}
