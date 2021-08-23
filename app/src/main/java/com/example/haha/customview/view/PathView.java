package com.example.haha.customview.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * Created by haha on 2019/5/15.
 */

public class PathView extends View {

    private Paint mPaint;
    private int mWidth,mHeight;

    public PathView(Context context) {
        super(context);
        init();
    }
    public PathView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public void init(){
        mPaint = new Paint();
        mPaint.setColor(Color.BLACK);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(10);
    }

    @Override
    protected void onSizeChanged(int x,int y,int oldX,int oldY){
        super.onSizeChanged(x,y,oldX,oldY);
        mWidth = x;
        mHeight = y;
    }

    @Override
    public void onDraw(Canvas canvas){
        super.onDraw(canvas);
        canvas.translate(mWidth/2,mHeight/2);
        canvas.scale(1,-1);

        Path path = new Path();
        Path src = new Path();

        path.addRect(-200,-200,200,200, Path.Direction.CW);
        src.addCircle(0,0,100, Path.Direction.CW);

        path.addPath(src,0,200);
        mPaint.setColor(Color.BLACK);

        canvas.drawPath(path, mPaint);
    }
}
