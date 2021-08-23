package com.example.haha.customview.view.flexible.callback;

/**
 * Created by haha on 2019/7/1.
 */

public interface OnPullListener {

    /**
     * 下拉
     * @param offset
     * */
    void onPull(int offset);

    //松开
    void onRelease();
}
