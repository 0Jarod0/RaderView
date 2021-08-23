package com.example.haha.customview.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.haha.customview.R;

/**
 * Created by haha on 2019/5/20.
 */

public class PosTanView extends View {

    private float currentValue = 0; //用于记录当前的位置，取值范围[0,1]映射Path的整个长度

    private float[] pos;            //当前点的实际位置
    private float[] tan;            //当前点的tangent值，用于计算图片所需旋转的角度
    private Bitmap mBitmap;         //箭头图片
    private Matrix mMatrix;         //矩阵，用于对图片进行一些操作

    private Paint mPaint;

    private int mWidth,mHeight;

    public PosTanView(Context context) {
        super(context);
        init(context);
    }
    public PosTanView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public void init(Context context){
        pos = new float[2];
        tan = new float[2];
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 2;       //缩放图片
        mBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.direction,options);
        mMatrix = new Matrix();
        mPaint = new Paint();
        mPaint.setColor(Color.RED);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(5f);
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
        canvas.translate(mWidth,mHeight);

        Path path = new Path();

        path.addCircle(0,0,200, Path.Direction.CW);     //添加一个圆形

        PathMeasure measure = new PathMeasure(path,false);      //创建PathMeasure

        currentValue += 0.005;                              //计算当前的位置在总长度上的比例[0,1]
        if (currentValue >= 1){
            currentValue = 0;
        }

        measure.getPosTan(measure.getLength() * currentValue,pos,tan);          //获取当前位置的坐标以及趋势

        mMatrix.reset();
        float degress = (float)(Math.atan2(tan[1],tan[0] * 180.0/Math.PI));         //计算图片旋转角度

        mMatrix.postRotate(degress,mBitmap.getWidth()/2,mBitmap.getHeight()/2);
        mMatrix.postTranslate(pos[0]-mBitmap.getWidth()/2,pos[1]-mBitmap.getHeight()/2);        //将图片绘制中心调整到与当前点重合

        canvas.drawPath(path,mPaint);
        canvas.drawBitmap(mBitmap,mMatrix,mPaint);

        invalidate();
    }
}
