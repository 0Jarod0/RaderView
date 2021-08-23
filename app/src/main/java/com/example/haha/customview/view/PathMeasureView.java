package com.example.haha.customview.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * Created by haha on 2019/5/20.
 */

public class PathMeasureView extends View {
    private final static String TAG = PathMeasureView.class.getSimpleName();

    private Paint mPaint;

    private int mWidth,mHeight;

    public PathMeasureView(Context context) {
        super(context);
        init();
    }
    public PathMeasureView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }
    public void init(){
        mPaint = new Paint();
        mPaint.setColor(Color.BLACK);
        mPaint.setStrokeWidth(5f);
        mPaint.setStyle(Paint.Style.STROKE);
    }

    @Override
    protected void onSizeChanged(int x,int y,int oldX,int oldY){
        super.onSizeChanged(x,y,oldX,oldY);
        mWidth = x/2;
        mHeight = y/2;
    }

    @Override
    public void onDraw(Canvas canvas){
        super.onDraw(canvas);
        canvas.translate(mWidth,mHeight);       //平移坐标系

        Path path = new Path();

        path.addRect(-100,-100,100,100, Path.Direction.CW);
        path.addRect(-200,-200,200,200, Path.Direction.CW);     //创建path并添加了一个矩形

        canvas.drawPath(path, mPaint);

        PathMeasure measure = new PathMeasure(path,false);

        float len1 = measure.getLength();

        measure.nextContour();

        float len2 = measure.getLength();

        Log.e(TAG,"len1="+len1);
        Log.e(TAG,"len2="+len2);
//        Path dst = new Path();

//        dst.lineTo(-300,-300);
//        PathMeasure measure = new PathMeasure(path,false);
//
//        measure.getSegment(200,600,dst,true); //截取一部分存入dst中，并使用moveto保持截取得到的Path第一个点的位置不变
//
//        canvas.drawPath(dst, mPaint);
//        path.lineTo(0,200);
//        path.lineTo(200,200);
//        path.lineTo(200,0);
//
//        PathMeasure measure1 = new PathMeasure(path,false);
//        PathMeasure measure2 = new PathMeasure(path,true);
//
//        Log.e("TAG","forceClosed=false---->"+measure1.getLength());
//        Log.e("TAG","forceClosed=true---->"+measure2.getLength());
//
//        canvas.drawPath(path, mPaint);
    }
}
