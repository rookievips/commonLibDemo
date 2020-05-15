package com.chen.api.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.SweepGradient;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.chen.api.R;


/**
 * Desc: 圆形进度条
 */
@SuppressWarnings("all")
public class RoundProgressBar extends View{

    /**
     * 画笔对象的引用
     */
    private Paint paint;

    /**
     * 圆环的颜色
     */
    private int roundColor;

    /**
     * 圆环进度的颜色
     */
    private int roundProgressColor;

    /**
     * 中间进度百分比的字符串的颜色
     */
    private int textColor;

    /**
     * 中间进度百分比的字符串的字体
     */
    private float textSize;

    /**
     * 圆环的宽度
     */
    private float roundWidth;

    /**
     * 最大进度
     */
    private int maxProgress;

    /**
     * 当前进度
     */
    private int progress;
    /**
     * 是否显示中间的进度
     */
    private boolean textIsDisplayable;

    private SweepGradient mSweepGradient;

    private float mCenterXPointX, mCenterXPointY;

    /**
     * 进度的风格，实心或者空心
     */
    private int style;

    public static final int STROKE = 0;
    public static final int FILL = 1;

    public RoundProgressBar(Context context) {
        this(context, null);
    }

    public RoundProgressBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RoundProgressBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        paint = new Paint();


        TypedArray mTypedArray = context.obtainStyledAttributes(attrs,
                R.styleable.RoundProgressBar);

        //获取自定义属性和默认值
        roundColor = mTypedArray.getColor(R.styleable.RoundProgressBar_roundColor, Color.RED);
        roundProgressColor = mTypedArray.getColor(R.styleable.RoundProgressBar_roundProgressColor, Color.GREEN);
        textColor = mTypedArray.getColor(R.styleable.RoundProgressBar_textColor, Color.GREEN);
        textSize = mTypedArray.getDimension(R.styleable.RoundProgressBar_textSize, 15);
        roundWidth = mTypedArray.getDimension(R.styleable.RoundProgressBar_roundWidth, 5);
        maxProgress = mTypedArray.getInteger(R.styleable.RoundProgressBar_maxProgress, 100);
        textIsDisplayable = mTypedArray.getBoolean(R.styleable.RoundProgressBar_textIsDisplayable, true);
        style = mTypedArray.getInt(R.styleable.RoundProgressBar_style, 0);

        mTypedArray.recycle();
        sweepGradientInit();
    }

    /**
     * 初始化渐变
     */
    public void sweepGradientInit() {
        int[] mGradientColors = {Color.GREEN, Color.YELLOW, Color.RED};
        //渐变颜色
        mSweepGradient = new SweepGradient(mCenterXPointX, mCenterXPointY, mGradientColors, null);
        //旋转 不然是从0度开始渐变
        Matrix matrix = new Matrix();
        matrix.setRotate(-90, mCenterXPointX, mCenterXPointY);
        mSweepGradient.setLocalMatrix(matrix);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        mCenterXPointX = w / 2;
        mCenterXPointY = h / 2;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        /**
         * 画最外层的大圆环
         */
        //获取圆心的x坐标
        int centerX = getWidth() / 2;
        //圆环的半径
        int radius = (int) (centerX - roundWidth / 2);
        paint.setShader(null);
        //设置圆环的颜色
        paint.setColor(roundColor);
        //设置空心
        paint.setStyle(Paint.Style.STROKE);
        //设置圆环的宽度
        paint.setStrokeWidth(roundWidth);
        //消除锯齿
        paint.setAntiAlias(true);
        //画出圆环
        canvas.drawCircle(
                centerX,
                centerX,
                radius,
                paint);

        /**
         * 画进度百分比
         */
        paint.setShader(null);
        paint.setStrokeWidth(0);
        paint.setColor(textColor);
        paint.setTextSize(textSize);
        //设置字体
        paint.setTypeface(Typeface.DEFAULT_BOLD);
        //中间的进度百分比，先转换成float在进行除法运算，不然都为0
        int percent = (int) (((float) progress / (float) maxProgress) * 100);
        //测量字体宽度，我们需要根据字体的宽度设置在圆环中间
        float textWidth = paint.measureText(percent + "%");

        if (textIsDisplayable && style == STROKE) {
            //画出进度百分比
            canvas.drawText(
                    percent + "%",
                    centerX - textWidth / 2,
                    centerX + textSize / 2,
                    paint);
        }

        /**
         * 画圆弧,画圆环的进度
         */
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setShader(mSweepGradient);
        //设置圆环的宽度
        paint.setStrokeWidth(roundWidth);
        //设置进度的颜色
        paint.setColor(roundProgressColor);
        //用于定义的圆弧的形状和大小的界限
        RectF oval = new RectF(
                centerX - radius,
                centerX - radius,
                centerX + radius,
                centerX + radius);

        //设置进度是实心还是空心
        switch (style) {
            case STROKE: {
                paint.setStyle(Paint.Style.STROKE);
                if (progress != 0)
                    canvas.drawArc(oval, startAngle, 360 * progress / maxProgress, false, paint);  //根据进度画圆弧
                break;
            }
            case FILL: {
                paint.setStyle(Paint.Style.FILL_AND_STROKE);
                if (progress != 0)
                    canvas.drawArc(oval, startAngle, 360 * progress / maxProgress, true, paint);  //根据进度画实心圆弧
                break;
            }
        }

        /**
         * 画小圆点
         */
        float centerXYs = roundWidth / 2;
        paint.setShader(null);
        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeWidth(1f);
        paint.setColor(Color.parseColor("#d0d0d0"));
        canvas.drawCircle(
                (float) (centerX + radius * Math.sin(Math.toRadians(offsetRotate))),
                (float) (centerX - radius * Math.cos(Math.toRadians(offsetRotate))),
                centerXYs,
                paint);

        //根据前一次的进度设置下次画弧开始的角度
        if (progress == curTotalProgress) {
            startAngle = progress / maxProgress * 360f - 90f;
        }

    }


    public synchronized int getMaxProgress() {
        return maxProgress;
    }

    /**
     * 设置进度的最大值
     *
     * @param maxProgress
     */
    public synchronized void setMaxProgress(int maxProgress) {
        if (maxProgress < 0) {
            throw new IllegalArgumentException("maxProgress not less than 0");
        }
        this.maxProgress = maxProgress;
    }

    /**
     * 获取进度.需要同步
     *
     * @return
     */
    public synchronized int getProgress() {
        return progress;
    }

    /**
     * 设置进度，此为线程安全控件，由于考虑多线的问题，需要同步
     * 刷新界面调用postInvalidate()能在非UI线程刷新
     *
     * @param progress
     */
    public synchronized void setProgress(int progress) {
        if (this.progress < maxProgress) {
            if (progress < 0) {
                throw new IllegalArgumentException("progress not less than 0");
            }
            if (progress > maxProgress) {
                progress = maxProgress;
            }
//            if (progress <= maxProgress) {
//                this.progress = progress;
//                postInvalidate();
//            }
            if (progress <= maxProgress) {
                if (progress <= curTotalProgress) {
                    this.progress += 1;
                    Log.d("当前的总进度----->", this.progress + "");
                    postInvalidate();
                    offsetRotate += 3.6f;
                    Log.d("当前的总角度----->", offsetRotate + "");
                }

            }
        }

    }

    /**
     * 弧所对应的角度
     */
    private float offsetRotate;
    /**
     * 当前要设置的总进度
     */
    private int curTotalProgress;
    /**
     * 画进度开始的角度
     */
    private float startAngle = -90f;

    /**
     * 设置当前的总进度
     *
     * @param curTotalProgress
     */
    public void setCurTotalProgress(int curTotalProgress) {
        this.curTotalProgress = curTotalProgress;
        Log.d("当前要设置的总角度----->", this.curTotalProgress + "");
    }

    public int getRoundColor() {
        return roundColor;
    }

    public void setRoundColor(int roundColor) {
        this.roundColor = roundColor;
    }

    public int getRoundProgressColor() {
        return roundProgressColor;
    }

    public void setRoundProgressColor(int roundProgressColor) {
        this.roundProgressColor = roundProgressColor;
    }

    public int getTextColor() {
        return textColor;
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
    }

    public float getTextSize() {
        return textSize;
    }

    public void setTextSize(float textSize) {
        this.textSize = textSize;
    }

    public float getRoundWidth() {
        return roundWidth;
    }

    public void setRoundWidth(float roundWidth) {
        this.roundWidth = roundWidth;
    }
}
