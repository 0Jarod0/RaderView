package com.example.haha.customview.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.inputmethod.InputMethodManager;

import androidx.appcompat.widget.AppCompatEditText;

import com.example.haha.customview.R;


/**
 * Created by haha on 2020/6/29.
 */

public class PasswordEditText extends AppCompatEditText {

    private Paint mSelectPaint,mUnSelectPaint,mTextPaint;

    private int mBottomWidth = dip2px(33);
    private int mBottomHeight = dip2px(1);

    private int mTextSize = sp2px(getContext(),30);

    private int textLength;
    private String mText;

    public PasswordEditText(Context context) {
        this(context,null);
    }

    public PasswordEditText(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public PasswordEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initPaint();
    }

    private void initPaint(){
        //自动弹出软键盘
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                setFocusable(true);
                setFocusableInTouchMode(true);
                requestFocus();
                InputMethodManager inputManager =
                        (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                inputManager.showSoftInput(PasswordEditText.this, 0);
            }
        }, 400);
        mSelectPaint = new Paint();
        mSelectPaint.setColor(getResources().getColor(R.color.color_ff345a));
        mSelectPaint.setAntiAlias(true);
        mSelectPaint.setStyle(Paint.Style.FILL);

        mUnSelectPaint = new Paint();
        mUnSelectPaint.setColor(getResources().getColor(R.color.color_e5e5e5));
        mUnSelectPaint.setAntiAlias(true);
        mUnSelectPaint.setStyle(Paint.Style.FILL);

        mTextPaint = new Paint();
        mTextPaint.setColor(getResources().getColor(R.color.color_000000));
        mTextPaint.setTextSize(sp2px(getContext(),30));
        mTextPaint.setStyle(Paint.Style.FILL);
        mTextPaint.setAntiAlias(true);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int height = 0;
        switch (heightMode){
            case MeasureSpec.UNSPECIFIED:       //如果指定了这两种模式，就需要自己去指定高度，
            case MeasureSpec.AT_MOST:           //因为宽度是不会变化的， 至于为什么高度会变化，绘制的时候我们会讲这个
                height = mTextSize + dip2px(1) + 10;
                break;
            case MeasureSpec.EXACTLY:
                height = MeasureSpec.getSize(heightMeasureSpec);
                break;
        }

        setMeasuredDimension(width,height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        mBottomWidth = getWidth()/4;        //底部线的长度
        for (int i=0;i<4;i++){
            int startX;
            int endX;
            //绘制底部横线的逻辑，输入第一个亮起下一个，主要是更换画笔的颜色
            startX = mBottomWidth*(i)+dip2px(5);
            endX = mBottomWidth *(1+i)-dip2px(5);
            Rect rectF = new Rect(startX,getHeight()-mBottomHeight,endX,getHeight());
            canvas.drawRect(rectF,i <= textLength ? mSelectPaint : mUnSelectPaint);
        }
        //绘制文字
        for (int i=0;i<textLength;i++){
            String child = String.valueOf(mText.charAt(i));//
            Rect rect = new Rect(mBottomWidth*i,0,mBottomWidth*(i+1),getHeight()-mBottomHeight);
            drawText(canvas,child,rect);
        }
    }

    private void drawText(Canvas canvas,String str,Rect rect){
        Rect mTextRect = new Rect();
        mTextPaint.getTextBounds(str,0,1,mTextRect);
        Paint.FontMetricsInt fontMetricsInt = mTextPaint.getFontMetricsInt();
        int baseline = rect.centerY()- (fontMetricsInt.top+fontMetricsInt.bottom)/2 - 10;
        canvas.drawText(str,rect.centerX()-mTextRect.width()/2,baseline,mTextPaint);
    }

    private int dip2px(int mDragRadius) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,mDragRadius,getResources().getDisplayMetrics());
    }

    /**
     * 将sp值转换为px值，保证文字大小不变
     *            （DisplayMetrics类中属性scaledDensity）
     * @return
     */
    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        InputMethodManager inputManager = (InputMethodManager)getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.showSoftInput(this, 0);
        return super.onTouchEvent(event);
    }

    @Override
    protected void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        this.textLength = text.length();
        this.mText = text.toString();
        invalidate();
    }
}
