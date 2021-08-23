package com.example.haha.customview.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * Created by haha on 2019/5/16.
 */

public class PieData extends View {

    private int mWidth,mHeight;

    private Paint mPaint,redPaint,yellowPaint,bluePaint,grayPaint;

    private float mRadius;

    public PieData(Context context) {
        super(context);
        init();
    }
    public PieData(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    @Override
    public void onSizeChanged(int w,int h,int oldX,int oldY){
        super.onSizeChanged(w,h,oldX,oldY);
        mRadius = (float) (w/2*0.9);
        mWidth = w/2;
        mHeight = h/2;
    }

    private void init(){
        mPaint = new Paint();
        mPaint.setColor(Color.BLACK);
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mPaint.setStrokeWidth(5f);

        redPaint = new Paint();
        redPaint.setColor(Color.RED);
        redPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        redPaint.setStrokeWidth(5f);

        yellowPaint = new Paint();
        yellowPaint.setColor(Color.YELLOW);
        yellowPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        yellowPaint.setStrokeWidth(5f);

        bluePaint = new Paint();
        bluePaint.setColor(Color.BLUE);
        bluePaint.setStyle(Paint.Style.FILL_AND_STROKE);
        bluePaint.setStrokeWidth(5f);

        grayPaint = new Paint();
        grayPaint.setColor(Color.GRAY);
        grayPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        grayPaint.setStrokeWidth(5f);
    }

    @Override
    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);
        canvas.translate(mWidth,mHeight);

        canvas.drawCircle(0,0,mRadius,mPaint);

        RectF rectF = new RectF(-mRadius,-mRadius,mRadius,mRadius);
        canvas.drawArc(rectF,0,90,true,redPaint);

        canvas.drawArc(rectF,90,30,true,yellowPaint);

        canvas.drawArc(rectF,120,70,true,grayPaint);

        canvas.drawArc(rectF,190,80,true,bluePaint);
    }
}
