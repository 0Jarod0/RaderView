package com.example.haha.customview.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.haha.customview.R;

import org.w3c.dom.ProcessingInstruction;

import java.util.Hashtable;

/**
 * Created by haha on 2019/6/24.
 */

public class WrapLinearLayout extends LinearLayout {
    private int mLeft,mRight,mTop,mBottom;
    Hashtable map = new Hashtable();
    private boolean isLeft = false;

    public WrapLinearLayout(Context context) {
        super(context);
    }

    public WrapLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.WrapLinearLayout);
        isLeft = typedArray.getBoolean(R.styleable.WrapLinearLayout_isLeft, false);
    }

    public WrapLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int mWidth = View.MeasureSpec.getSize(widthMeasureSpec);
        int mCount = getChildCount();
        int mX = 0;
        int mY = 0;
        mLeft = 0;
        mRight = 0;
        mTop = 5;
        mBottom = 0;
        int j=0;

        View lastview = null;
        for (int i=0;i<mCount;i++){
            final View child = getChildAt(i);

            child.measure(MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED);
            //此处增加onLayout中的换行判断，用于计算所需的高度
            int childw = child.getMeasuredWidth();
            int childh = child.getMeasuredHeight();

            mX += childw;       //将每次子控件宽度进行统计叠加，如果大于设定的宽度则需要换行，高度即Top坐标也需要重新设置
            Position position = new Position();
            mLeft = mWidth - getPosition(i-j,i)-child.getMeasuredWidth();           //从右向左排
            mRight = mLeft + child.getMeasuredWidth();

            if (mX >= mWidth){
                mX = childw;
                mY += childh;
                j = i;
                mLeft = mWidth - getPosition(i-j,i) - child.getMeasuredWidth();
                mRight = mLeft + child.getMeasuredWidth();
                mTop = mY + 5;
            }

            mBottom = mTop + child.getMeasuredHeight();
            mY = mTop;      //每次高度必须记录否则会叠加到一起
            position.left = mLeft;
            position.top = mTop + 3;
            position.right = mRight;
            position.bottom = mBottom;
            map.put(child,position);
        }
        setMeasuredDimension(mWidth,mBottom);
    }

    @Override
    protected LinearLayout.LayoutParams generateDefaultLayoutParams() {
        return new LinearLayout.LayoutParams(1,1);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int count = getChildCount();
        for (int i=0;i<count;i++){
            View child = getChildAt(i);
            Position position = (Position)map.get(child);
            if (position != null){
                child.layout(position.left,position.top,position.right,position.bottom);
            }else{
                Log.i("MyLayout","error");
            }
        }
    }

    private class Position{
        int left,top,right,bottom;
    }

    public int getPosition(int indexInRow,int childIndex){
        if (indexInRow > 0){
            return getPosition(indexInRow - 1,childIndex - 1) + getChildAt(childIndex-1).getMeasuredWidth() + 8;
        }
        return getPaddingLeft();
    }
}
