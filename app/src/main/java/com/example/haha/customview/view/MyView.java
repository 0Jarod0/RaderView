package com.example.haha.customview.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.haha.customview.R;

/**
 * Created by haha on 2019/4/11.
 */

public class MyView extends View {

    private Paint mPaint;
    public MyView(Context context) {
        super(context);
        init();
    }
    public MyView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }
    public MyView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        mPaint = new Paint();
        mPaint.setAntiAlias(true);//抗锯齿
        mPaint.setColor(getResources().getColor(R.color.colorAccent));//为画笔设置颜色
        mPaint.setStyle(Paint.Style.FILL);//画笔风格
        mPaint.setTextSize(36);//文字大小，单位为px
        mPaint.setStrokeWidth(5);//画笔粗细
    }

    @Override
    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);
        canvas.translate(canvas.getWidth()/2,200);//将位置移动画纸的坐标点：150，150
        canvas.drawCircle(0,0,100,mPaint);//画圆

        //使用path绘制路径文字
        canvas.save();
        canvas.translate(-75,-75);
        Path path = new Path();
        path.addArc(new RectF(0,0,150,150),-180,-180);
        Paint citePaint = new Paint(mPaint);
        citePaint.setTextSize(14);
        citePaint.setStrokeWidth(1);
        canvas.drawTextOnPath("绘制表盘",path,28,0,citePaint);
        canvas.restore();

        Paint tmpPaint = new Paint();
    }
}
