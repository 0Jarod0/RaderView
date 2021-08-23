package com.example.haha.customview.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.haha.customview.R;
import com.example.haha.customview.view.viewpager.AutoBannerAdapter;
import com.example.haha.customview.view.viewpager.AutoScrollHandler;
import com.example.haha.customview.view.viewpager.ScaleTransformer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by haha on 2019/6/24.
 */

public class AutoBannerActivity extends AppCompatActivity {

    private ViewPager mViewPager;
    private AutoBannerAdapter mAdapter;

    private int currentPosition = 0;

    private Button mButton;
    private int count = 0;
    private AutoScrollHandler mHandler;

    private List<Integer> mList = new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auto_banner);

        //轮播图
        mList.add(R.drawable.dog);
        mList.add(R.drawable.dog1);
        mList.add(R.drawable.dog2);
        mList.add(R.drawable.dog3);
        assignView();
    }

    private void assignView(){
        mViewPager = findViewById(R.id.viewpager);
        mButton = findViewById(R.id.button);

        mHandler = new AutoScrollHandler(mViewPager);
        mAdapter = new AutoBannerAdapter(AutoBannerActivity.this,mList);
        initViewPager();

        //主要用于测试setCurrentItem的跨度大于1时，是否会引起卡顿
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count += 3;
                mViewPager.setCurrentItem(count);
            }
        });
    }

    private void initViewPager(){
        mViewPager.setCurrentItem(mAdapter.getStartPageIndex());
        mViewPager.setOffscreenPageLimit(3);
        mViewPager.setPageTransformer(true,new ScaleTransformer());
        mViewPager.setAdapter(mAdapter);

        mHandler.sendEmptyMessageDelayed(0,3000);

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            //当getCount不是一个很大的值的时候，ViewPager很快就会到达左右边界，就无法继续滑动了
            //解决办法时在viewpager快要临界的时候，使用setcurrentItem把它重置回中间位置
            @Override
            public void onPageSelected(int position) {
                currentPosition = position;
//                if (position <= 2 || position >= mAdapter.getCount()-3){
//                    //重置页面
//                    int page = mAdapter.getBannerIndexOfPosition(position);
//                    int newPosition = mAdapter.getStartPageIndex() + page;
//                    mViewPager.setCurrentItem(newPosition);
//                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {
                //验证当前的滑动是否结束
                switch (state){
                    case ViewPager.SCROLL_STATE_DRAGGING:
                        mHandler.pause = true;
                        break;
                    case ViewPager.SCROLL_STATE_IDLE:
                        mHandler.pause = false;
                        break;
                }
            }
        });
    }
}
