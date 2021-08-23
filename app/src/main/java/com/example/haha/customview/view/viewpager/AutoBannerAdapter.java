package com.example.haha.customview.view.viewpager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.viewpager.widget.PagerAdapter;

import com.example.haha.customview.R;

import java.util.List;

/**
 * Created by haha on 2019/5/24.
 */

public class AutoBannerAdapter extends PagerAdapter {

    private Context mContext;
    private List<Integer> mList;

    public AutoBannerAdapter(Context context, List<Integer> list){
        mContext = context;
        mList = list;
    }

    public int getStartPageIndex(){
        int index = getCount() / 2;     //取所有数量的中间值
        int remainder = index % mList.size();
        index = index - remainder;
        return index;
    }

    @Override
    public Object instantiateItem(final ViewGroup container,final int position){
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_viewpager, container, false);

        ImageView img = view.findViewById(R.id.img);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        img.setLayoutParams(params);
        img.setImageResource(mList.get(position%mList.size()));
        container.addView(view);
        return view;
    }

    //不要设置    Integer.MAX_VALUE,当我们返回的页数非常大的时候，比如10亿，调用setCurrentItem会引起ANR
    //ViewPager中的populate和calculatePageOffsets中,执行的for循环与getCount成正比

    //并且
    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    public int getBannerIndexOfPosition(int position){
        return position % mList.size();
    }
}
