package com.example.haha.customview.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * Created by haha on 2019/5/15.
 */

public class RadarView extends View {

    private int count = 6;          //数据个数
    private float mAngle;           //单个区域所占角度
    private float mRadius;           //网格最大半径
    private int total = 10;          //需要多边形的数目

    private int centerX;            //中心X
    private int centerY;            //中心Y

    private double x,y;                //每次画多边形时需要的坐标

    private String[] titles = {"a","b","c","d","e","f"};
    private double[] data = {100,60,60,50,10,20};//各维度分值
    private Paint mainPaint;         //雷达区画笔
    private Paint valuePaint;       //数据区画笔
    private Paint textPaint;        //文本画笔

    private Path linePath;          //连接多边形
    private Path anglePath;         //连接多边形的对角

    private float internal;

    public RadarView(Context context) {
        super(context);
        init();
    }
    public RadarView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public void init(){
        mainPaint = new Paint();
        valuePaint = new Paint();
        textPaint = new Paint();
        mainPaint.setColor(Color.BLACK);
        mainPaint.setStyle(Paint.Style.STROKE);
        mainPaint.setStrokeWidth(10f);
        valuePaint.setColor(Color.BLUE);
        valuePaint.setStyle(Paint.Style.FILL_AND_STROKE);
        valuePaint.setStrokeWidth(3f);
        textPaint.setColor(Color.LTGRAY);
        textPaint.setStrokeWidth(2f);
        textPaint.setStyle(Paint.Style.STROKE);
        textPaint.setTextSize(40f);

        mAngle = 360 / count;          //单个区域所占的角度

    }

    @Override
    protected void onSizeChanged(int x,int y,int oldX,int oldY){
        mRadius = Math.min(x,y)/2*0.9f;
        //中心坐标
        centerX = x/2;
        centerY = y/2;
        internal = mRadius/total;

        linePath = new Path();
        anglePath = new Path();
        postInvalidate();
        super.onSizeChanged(x,y,oldX,oldY);
    }

    @Override
    public void onDraw(Canvas canvas){
        super.onDraw(canvas);
        canvas.translate(centerX,centerY);      //中心点移动到屏幕中央

        for (int i=0;i<total;i++){      //total代表一共有多少个多边形
            float radius = mRadius - internal*i;    //每个多边形的外接圆的半径
            drawPolygon(canvas,mAngle,radius,i);
        }


        for (int i=0;i<data.length;i++){
            float radius = mRadius - internal*i;
            Double persent = (data[i]/100);
            x = getPointX(mAngle*i,radius);
            y = getPointY(mAngle*i,radius);
            if (i == 0){
                anglePath.moveTo((float) x,(float) y);
                canvas.drawCircle((float) x,(float) y,10,valuePaint);
            }
            if (i > 0){
                anglePath.lineTo((float) x,(float) y);
                canvas.drawCircle((float) x,(float) y,10,valuePaint);
            }
            if (i == count-1){
                anglePath.close();
            }

            canvas.drawPath(anglePath, valuePaint);
        }

        //在画这个图之前 可以先把所有的定点都写死 这样便于思考逻辑
//        Path mPath = new Path();
//        for (int i=0;i<8;i++){
//            float radius = mRadius - internal*i;
//            mPath.moveTo(0,-radius);
//            canvas.drawLine(0,0,0,-radius,mainPaint);
//            mPath.lineTo((float) (Math.sqrt(3f)/2*radius),-radius/2);
//            canvas.drawLine(0,0,(float) (Math.sqrt(3f)/2*radius),-radius/2,mainPaint);
//            mPath.lineTo((float) (Math.sqrt(3f)/2*radius),radius/2);
//            canvas.drawLine(0,0,(float) (Math.sqrt(3f)/2*radius),radius/2,mainPaint);
//            mPath.lineTo(0,radius);
//            canvas.drawLine(0,0,0,radius,mainPaint);
//            mPath.lineTo(-((float) (Math.sqrt(3f)/2*radius)),radius/2);
//            canvas.drawLine(0,0,-((float) (Math.sqrt(3f)/2*radius)),radius/2,mainPaint);
//            mPath.lineTo(-((float) (Math.sqrt(3f)/2*radius)),-radius/2);
//            canvas.drawLine(0,0,-((float) (Math.sqrt(3f)/2*radius)),-radius/2,mainPaint);
//            mPath.close();
//            canvas.drawPath(mPath, mainPaint);
//        }
//
//        Path valuePath = new Path();
//        valuePath.moveTo(0,-(mRadius-internal*4));
//        valuePath.lineTo((float) (Math.sqrt(3f)/2*(mRadius-internal*6)),-(mRadius-internal*6)/2);
//        valuePath.lineTo((float) (Math.sqrt(3f)/2*(mRadius-internal*3)),(mRadius-internal*3)/2);
//        valuePath.lineTo(0,mRadius-internal*4);
//        valuePath.lineTo(-((float) (Math.sqrt(3f)/2*(mRadius-internal*2))),(mRadius-internal*2)/2);
//        valuePath.lineTo(-((float) (Math.sqrt(3f)/2*(mRadius-internal*1))),-(mRadius-internal*1)/2);
//        valuePath.close();
//        canvas.drawPath(valuePath, valuePaint);
    }

    public double getPointX(double angle,float radius){
        double res = 0;
        res = radius * Math.cos(angle/180*Math.PI);
        return res;
    }
    public double getPointY(double angle,float radius){
        double res = 0;
        res = radius * Math.sin(angle/180*Math.PI);
        return res;
    }
    /**
     * 画每一个多边形
     * @param canvas 画布
     * @param angle 多边形平均每一块扇形所占的角度
     * @param radius 当前多边形外接圆的半径
     * @param index 用于多边形各顶点的连线
     * */

    public void drawPolygon(Canvas canvas,double angle,float radius,int index){

        for (int i=0;i < count;i++){        //绘制多边形
            x = getPointX(angle*i,radius);
            y = getPointY(angle*i,radius);
            if (index == 0){
                canvas.drawLine(0,0,(float)x,(float)y,mainPaint);
                drawText(canvas,angle*i,x,y,i);
            }
            if (i == 0){
                linePath.moveTo((float) x,(float) y);
            }
            if (i > 0){
                linePath.lineTo((float) x,(float) y);
            }
            if (i == count-1){
                linePath.close();
            }
        }
        canvas.drawPath(linePath, mainPaint);
    }

    private void drawText(Canvas canvas,double angle,double x,double y,int i){
        if (angle >=0 && angle < 90){
            canvas.drawText(titles[i],(float)(x+20),(float)(y+20),textPaint);
        }else if (angle >= 90 && angle < 180){
            canvas.drawText(titles[i],(float)(x-20),(float)(y+20),textPaint);
        }else if (angle >= 180 && angle < 270){
            canvas.drawText(titles[i],(float)(x-20),(float)(y-20),textPaint);
        }else if (angle >= 270 && angle < 360){
            canvas.drawText(titles[i],(float)(x+20),(float)(y-20),textPaint);
        }
    }

    /**
     * 对外开发设置数据数量方法
     * @param count 数据数量 即多边形的边的数目
     * */
    public void setCount(int count){
        this.count = count;
    }

    /**
     *  设置内部多边形数量
     *  @param total 多边形数量
     *  */
    public void setTotal(int total){
        this.total = total;
    }
}
