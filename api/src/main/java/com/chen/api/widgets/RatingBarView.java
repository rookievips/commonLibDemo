package com.chen.api.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.chen.api.R;


public class RatingBarView extends LinearLayout {
    public interface OnRatingListener {
        void onRating(Object bindObject, int RatingScore);
    }

    private OnRatingListener onRatingListener;
    private Object bindObject;
    private float starImageSize;
    private int starCount;
    private Drawable starEmptyDrawable;
    private Drawable starFillDrawable;
    private int mStarCount;
    private boolean isIndicator;
    private int ratingCount;
    private boolean canNoStar;
    private boolean oneStar = false;
    private boolean clickSed;

    public RatingBarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOrientation(LinearLayout.HORIZONTAL);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.RatingBarView);
        starImageSize = ta.getDimension(R.styleable.RatingBarView_starImageSize, 20);
        starCount = ta.getInteger(R.styleable.RatingBarView_starCount, 5);
        starEmptyDrawable = ta.getDrawable(R.styleable.RatingBarView_starEmpty);
        starFillDrawable = ta.getDrawable(R.styleable.RatingBarView_starFill);
        isIndicator = ta.getBoolean(R.styleable.RatingBarView_indicator, false);
        ratingCount = ta.getInteger(R.styleable.RatingBarView_ratingCount, 0);
        canNoStar = ta.getBoolean(R.styleable.RatingBarView_canNoStar, false);
        ta.recycle();

        for (int i = 0; i < starCount; ++i) {
            ImageView imageView = getStarImageView(context);
            imageView.setOnClickListener(v -> {
                if (!isIndicator) {
                    mStarCount = indexOfChild(v) + 1;
                    if (canNoStar) {
                        if (mStarCount == 1) {
                            if (oneStar && !clickSed) {
                                setStar(0, false);
                                oneStar = false;
                                return;
                            }
                            oneStar = true;
                            clickSed = false;
                        } else {
                            clickSed = true;
                        }
                    }
                    setStar(mStarCount, false);
                    if (onRatingListener != null) {
                        onRatingListener.onRating(bindObject, mStarCount);
                    }
                }
            });
            addView(imageView);
        }
        setStar(ratingCount, false);
    }

    private ImageView getStarImageView(Context context) {
        ImageView imageView = new ImageView(context);
        ViewGroup.LayoutParams para = new ViewGroup.LayoutParams(Math.round(starImageSize), Math.round(starImageSize));
        imageView.setLayoutParams(para);
        // TODO:you can change gap between two stars use the padding
        imageView.setPadding(0, 0, 15, 0);
        imageView.setImageDrawable(starEmptyDrawable);
        imageView.setMaxWidth(10);
        imageView.setMaxHeight(10);
        return imageView;
    }

    public void setStar(int starCount, boolean animation) {
        starCount = starCount > this.starCount ? this.starCount : starCount;
        starCount = starCount < 0 ? 0 : starCount;
        for (int i = 0; i < starCount; ++i) {
            ((ImageView) getChildAt(i)).setImageDrawable(starFillDrawable);
            if (animation) {
                ScaleAnimation sa = new ScaleAnimation(0, 0, 1, 1);
                getChildAt(i).startAnimation(sa);
            }
        }
        for (int i = this.starCount - 1; i >= starCount; --i) {
            ((ImageView) getChildAt(i)).setImageDrawable(starEmptyDrawable);
        }
    }

    public int getStarCount() {
        return mStarCount;
    }

    public void setStarFillDrawable(Drawable starFillDrawable) {
        this.starFillDrawable = starFillDrawable;
    }

    public void setStarEmptyDrawable(Drawable starEmptyDrawable) {
        this.starEmptyDrawable = starEmptyDrawable;
    }

    public void setStarCount(int startCount) {
        this.starCount = startCount;
    }

    public void setStarImageSize(float starImageSize) {
        this.starImageSize = starImageSize;
    }

    public void setBindObject(Object bindObject) {
        this.bindObject = bindObject;
    }

    public int getRatingCount() {
        return ratingCount;
    }

    public void setRatingCount(int ratingCount) {
        this.ratingCount = ratingCount;
        setStar(ratingCount, false);
    }

    /**
     * 这个回调，可以获取到用户评价给出的星星等级
     */
    public void setOnRatingListener(OnRatingListener onRatingListener) {
        this.onRatingListener = onRatingListener;
    }
}
