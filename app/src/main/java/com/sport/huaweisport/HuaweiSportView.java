package com.sport.huaweisport;


import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * 姓名: JinYa
 * 时间： 2020-05-31
 * 描述：
 */
public class HuaweiSportView extends View {

    private int mInColor = Color.RED;
    private int mOutColor = Color.GRAY;
    private float mSportSize = 22;
    private String mSportText = "";
    private int mSportNum = 0;
    private int mMaxSportNum = 10000;
    private Paint mOutPaint;
    private float mStrokeWidth = 0;
    private Paint mInPaint;
    private Paint mTextPaint;

    public HuaweiSportView(Context context) {
        this(context, null);
    }

    public HuaweiSportView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HuaweiSportView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {

        if (attrs != null) {
            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.HuaweiSportView);

            mInColor = typedArray.getColor(R.styleable.HuaweiSportView_intColor, Color.RED);
            mOutColor = typedArray.getColor(R.styleable.HuaweiSportView_outColor, Color.GRAY);
            mSportSize = typedArray.getDimension(R.styleable.HuaweiSportView_sportSize, TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 22,
                    context.getResources().getDisplayMetrics()));
            mSportText = typedArray.getString(R.styleable.HuaweiSportView_sportText);
            mSportNum = typedArray.getInteger(R.styleable.HuaweiSportView_sportNum, 0);
            mMaxSportNum = typedArray.getInteger(R.styleable.HuaweiSportView_maxSportNum, 10000);
            mStrokeWidth = typedArray.getDimension(R.styleable.HuaweiSportView_strokeWidth, TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                    22, context.getResources().getDisplayMetrics()));

            typedArray.recycle();
        }

        mOutPaint = new Paint();
        mOutPaint.setColor(mOutColor);
        mOutPaint.setAntiAlias(true);
        mOutPaint.setStrokeCap(Paint.Cap.ROUND);
        mOutPaint.setStyle(Paint.Style.STROKE);
        mOutPaint.setStrokeWidth(mStrokeWidth);


        mInPaint = new Paint();
        mInPaint.setColor(mInColor);
        mInPaint.setAntiAlias(true);
        mInPaint.setStrokeCap(Paint.Cap.ROUND);
        mInPaint.setStyle(Paint.Style.STROKE);
        mInPaint.setStrokeWidth(mStrokeWidth);

        mTextPaint = new Paint();
        mTextPaint.setColor(mInColor);
        mTextPaint.setAntiAlias(true);
        mTextPaint.setTextSize(mSportSize);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //外圆弧
        RectF rectF = new RectF(mStrokeWidth / 2, mStrokeWidth / 2
                , getWidth() - mStrokeWidth / 2, getHeight() - mStrokeWidth / 2);
        canvas.drawArc(rectF, 0, 360, false, mOutPaint);

        //内圆弧
        int sweepAngle = mSportNum * 360 / mMaxSportNum;
        canvas.drawArc(rectF, 270, sweepAngle, false, mInPaint);

        //写文字

//        Paint.FontMetricsInt fontMetricsInt = mOutPaint.getFontMetricsInt();
//
//        int dy = (fontMetricsInt.bottom - fontMetricsInt.top) / 2 - fontMetricsInt.bottom;
//        int baseLine = getHeight() / 2 + dy;

        Rect r = new Rect();
        String bs = "步数";
        mTextPaint.setTextSize(mSportSize / 2);
        mTextPaint.getTextBounds(bs, 0, bs.length(), r);
        mTextPaint.setAlpha(160);
        int x1 = getWidth() / 2 - r.width() / 2;
        canvas.drawText(bs, x1, getHeight() / 2 - mSportSize, mTextPaint);


        mTextPaint.setAlpha(255);
        mTextPaint.setTextSize(mSportSize);
        mSportText = mSportNum + "";
        mTextPaint.getTextBounds(mSportText, 0, mSportText.length(), r);
        int x = (int) (getWidth() / 2 - r.width() / 2);

        canvas.drawText(mSportText, x, getHeight() / 2 + mSportSize / 2, mTextPaint);

        String b = "步";
        mTextPaint.setTextSize(mSportSize / 2);
        mTextPaint.getTextBounds(b, 0, b.length(), r);
        mTextPaint.setAlpha(160);
        int x2 = getWidth() / 2 - r.width() / 2;

        canvas.drawText(b, x2, getHeight() / 2 + mSportSize * 3 / 2, mTextPaint);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        int min = Math.min(width, height);

        setMeasuredDimension(min, min);
    }


    public synchronized void setMaxSportNum(int maxSportNum) {
        this.mMaxSportNum = maxSportNum;
    }

    public synchronized void setSportNum(int sportNum) {
        this.mSportNum = sportNum;
        invalidate();
    }
}
