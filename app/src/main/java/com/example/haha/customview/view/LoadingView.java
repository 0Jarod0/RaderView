package com.example.haha.customview.view;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.haha.customview.R;


/**
 * Created by haha on 2020/7/5.
 */

public class LoadingView extends View {

    private Paint mPaint;
    private int mCircleWidth = dip2px(35);
    private float mSwipArc = 2;
    private float mCirclRingRoateAle=225;

    private float line1_x = 0;
    private float line1_y = 0;
    private float line2_x = 0;
    private float line2_y = 0;

    public LoadingView(Context context) {
        this(context,null);
    }

    public LoadingView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public LoadingView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();

    }

    private void init() {
        mPaint = new Paint();
        mPaint.setColor(getResources().getColor(R.color.color_ff345a));
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(dip2px(2));
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
//        startDelay();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);

        setMeasuredDimension(width > height ? height : width,width > height ? height : width);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int centerX = getWidth() / 2;
        int center1X = centerX - mCircleWidth/5;

        //圆弧半径
        int radius = mCircleWidth;
        mSwipArc +=10;
        RectF rectF = new RectF(getWidth()/2-mCircleWidth/2,getHeight()/2-mCircleWidth/2,getWidth()/2+mCircleWidth/2,getHeight()/2+mCircleWidth/2);
        canvas.drawArc(rectF,mCirclRingRoateAle,mSwipArc,false,mPaint);

        if (mSwipArc >= 360){

            if (line1_y < mCircleWidth/6){
                line1_x +=mCircleWidth/100;
                line1_y +=mCircleWidth/100;
            }
            canvas.drawLine(center1X,centerX,center1X+line1_x,centerX+line1_y,mPaint);
            if (line1_y == mCircleWidth/6){
                line2_x = line1_x;
                line2_y = line1_y;
                line1_x++;
                line1_y++;
            }
            if (line1_y >= mCircleWidth/6 && line2_x < radius*3/7){
                line2_x+=radius*3/70;
                line2_y-=radius*3/70;
            }
            canvas.drawLine(center1X+line1_x-1,centerX+line1_y,center1X+line2_x,centerX+line2_y,mPaint);
        }
        postInvalidate();
    }



    private int dip2px(int mDragRadius) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,mDragRadius,getResources().getDisplayMetrics());
    }


    public interface AnimationCompleteListener{
        void complete();
    }
}
