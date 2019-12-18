package com.lzk.statelayout.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.lzk.statelayout.R;
import com.lzk.statelayout.utils.DensityUtils;

/**
 * @author LiaoZhongKai
 * @date 2019/12/16.
 */
public class LoadingBar extends View {
    private static final int DEFAULT_BAR_BACKGROUND = Color.parseColor("#cccccc");
    private static final int DEFAULT_BAR_COLOR = Color.parseColor("#1ead7e");
    private static final int DEFAULT_BAR_HEIGHT = 4;//dp
    private static final int BAR_SPEED = 5;//bar移动的速度,值越大移动越快

    //底部颜色
    private int mBarBackground;
    //顶部颜色
    private int mBarColor;
    //高度
    private int mBarHeight;
    //Paint
    Paint mBgPaint;
    Paint mBarPaint;
    //左边宽度
    private float mLeftWidth = 0;
    //移动的bar的宽度
    private float mBarWidth = 0;
    //移动的bar的最大宽度
    private float mMaxBarWidth = 0;
    //除去padding之后的宽度
    private int mRealWidth;


    public LoadingBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAttrs(context, attrs);
        initPaint();
    }

    private void initAttrs(Context context, AttributeSet attrs){
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.LoadingBar,0,0);
        mBarBackground = typedArray.getColor(R.styleable.LoadingBar_bar_background,DEFAULT_BAR_BACKGROUND);
        mBarColor = typedArray.getColor(R.styleable.LoadingBar_bar_color,DEFAULT_BAR_COLOR);
        mBarHeight = typedArray.getDimensionPixelSize(R.styleable.LoadingBar_bar_height, DensityUtils.dip2px(context,DEFAULT_BAR_HEIGHT));
        typedArray.recycle();
    }

    private void initPaint(){
        mBgPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBgPaint.setStrokeWidth(mBarHeight*1.0f);
        mBgPaint.setColor(mBarBackground);
        mBarPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBarPaint.setStrokeWidth(mBarHeight*1.0f);
        mBarPaint.setColor(mBarColor);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = measureHeight(heightMeasureSpec);
        setMeasuredDimension(width,height);
        mRealWidth = getMeasuredWidth()-getPaddingLeft()-getPaddingRight();
        mMaxBarWidth = mRealWidth/4.0f;
    }

    private int measureHeight(int heightMeasureSpec){
        int result = 0;
        int specMode = MeasureSpec.getMode(heightMeasureSpec);
        int specSize = MeasureSpec.getSize(heightMeasureSpec);
        if (specMode == MeasureSpec.EXACTLY){
            result = specSize;
        }else {
            result = mBarHeight+getPaddingBottom()+getPaddingTop();
        }
        return result;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.save();
        canvas.translate(getPaddingLeft(),getHeight()/2.0f);
        //画左边
        if (mLeftWidth+mBarWidth < mRealWidth){
            if (mBarWidth < mMaxBarWidth){
                mBarWidth+=BAR_SPEED;
            }else {
                mBarWidth = mMaxBarWidth;
                mLeftWidth+=BAR_SPEED;
            }
        }else {
            if (mLeftWidth < mRealWidth){
                mLeftWidth+=BAR_SPEED;
                mBarWidth = mRealWidth - mLeftWidth;
            }else {
                mLeftWidth = 0;
                mBarWidth = 0;
            }
        }
        if (mLeftWidth > 0){
            mBgPaint.setColor(mBarBackground);
            canvas.drawLine(0,0,mLeftWidth,0,mBgPaint);
        }
        //画移动的bar
        if (mBarWidth > 0){
            mBarPaint.setColor(mBarColor);
            canvas.drawLine(mLeftWidth,0,mLeftWidth+mBarWidth,0,mBarPaint);
        }
        //画右边
        if (mLeftWidth+mBarWidth < mRealWidth){
            mBgPaint.setColor(mBarBackground);
            canvas.drawLine(mLeftWidth+mBarWidth,0,mRealWidth,0,mBgPaint);
        }
        canvas.restore();
        invalidate();
    }

    /**
     * 设置Bar的颜色
     * @param color
     */
    public void setBarColor(int color){
        mBarColor = color;
        invalidate();
    }
}
