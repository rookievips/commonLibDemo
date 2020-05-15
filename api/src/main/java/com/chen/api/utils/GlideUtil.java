package com.chen.api.utils;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.chen.api.R;

import java.io.File;


public class GlideUtil {

    /**
     * @param context
     * @param imageView
     * @param url         可以为一个文件路径、uri 或者 url
     * @param placeholder 加载过程中显示的占位图
     * @param error       加载失败显示的占位图
     */
    public static void display(Context context, ImageView imageView, String url, int placeholder, int error) {
        if (imageView == null) {
            throw new IllegalArgumentException("argument error");
        }
        Glide.with(context).load(url)
                .apply(RequestOptions.errorOf(error))//加载失败显示的占位图
                .apply(RequestOptions.placeholderOf(placeholder))//加载过程中显示的占位图
                .apply(RequestOptions.priorityOf(Priority.HIGH))//图片请求的优先级
                .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.AUTOMATIC))//缓存策略
                .transition(DrawableTransitionOptions.withCrossFade())//图片淡入淡出,默认动画
                .into(imageView);
    }

    /**
     * Activity(生命周期同于Activity)
     *
     * @param activity
     * @param imageView
     * @param url
     * @param placeholder
     * @param error
     */
    public static void display(Activity activity, ImageView imageView, String url, int placeholder, int error) {
        if (imageView == null) {
            throw new IllegalArgumentException("argument error");
        }
        Glide.with(activity).load(url)
                .apply(RequestOptions.errorOf(error))//加载失败显示的占位图
                .apply(RequestOptions.placeholderOf(placeholder))//加载过程中显示的占位图
                .apply(RequestOptions.priorityOf(Priority.HIGH))//图片请求的优先级
                .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.AUTOMATIC))//缓存策略
                .transition(DrawableTransitionOptions.withCrossFade())//图片淡入淡出,默认动画
                .into(imageView);
    }

    /**
     * V4 Fragment(生命周期同于Fragment)
     *
     * @param fragment
     * @param imageView
     * @param url
     * @param placeholder
     * @param error
     */
    public static void display(Fragment fragment, ImageView imageView, String url, int placeholder, int error) {
        if (imageView == null) {
            throw new IllegalArgumentException("argument error");
        }
        Glide.with(fragment).load(url)
                .apply(RequestOptions.errorOf(error))//加载失败显示的占位图
                .apply(RequestOptions.placeholderOf(placeholder))//加载过程中显示的占位图
                .apply(RequestOptions.priorityOf(Priority.HIGH))//图片请求的优先级
                .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.AUTOMATIC))//缓存策略
                .transition(DrawableTransitionOptions.withCrossFade())//图片淡入淡出,默认动画
                .into(imageView);
    }

    /**
     * 没有加载过程中显示的占位图,有默认加载失败的占位图
     *
     * @param context
     * @param imageView
     * @param url
     */
    public static void display(Context context, ImageView imageView, String url) {
        if (imageView == null) {
            throw new IllegalArgumentException("argument error");
        }
        Glide.with(context).load(url)
                .apply(RequestOptions.errorOf(R.drawable.pic_load_error))
                .apply(RequestOptions.priorityOf(Priority.HIGH))
                .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.AUTOMATIC))
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(imageView);
    }

    /**
     * 没有加载过程中显示的占位图,有默认加载失败的占位图
     *
     * @param activity  同于Activity的生命周期
     * @param imageView
     * @param url
     */
    public static void display(Activity activity, ImageView imageView, String url) {
        if (imageView == null) {
            throw new IllegalArgumentException("argument error");
        }
        Glide.with(activity).load(url)
                .apply(RequestOptions.errorOf(R.drawable.pic_load_error))
                .apply(RequestOptions.priorityOf(Priority.HIGH))
                .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.AUTOMATIC))
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(imageView);
    }

    /**
     * 没有加载过程中显示的占位图,有默认加载失败的占位图
     *
     * @param fragment  同于Fragment的生命周期
     * @param imageView
     * @param url
     */
    public static void display(Fragment fragment, ImageView imageView, String url) {
        if (imageView == null) {
            throw new IllegalArgumentException("argument error");
        }
        Glide.with(fragment).load(url)
                .apply(RequestOptions.errorOf(R.drawable.pic_load_error))
                .apply(RequestOptions.priorityOf(Priority.HIGH))
                .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.AUTOMATIC))
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(imageView);
    }

    /**
     * 通过文件加载图片
     *
     * @param context
     * @param imageView
     * @param file
     */
    public static void display(Context context, ImageView imageView, File file) {
        if (imageView == null) {
            throw new IllegalArgumentException("argument error");
        }
        Glide.with(context).load(file)
                .apply(RequestOptions.errorOf(R.drawable.pic_load_error))
                .apply(RequestOptions.priorityOf(Priority.HIGH))
                .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE))
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(imageView);
    }

    /**
     * 加载Gif  "file:///android_asset/f003.gif"
     *
     * @param context
     * @param imageView
     * @param gif_url
     */
    public static void displayGif(Context context, ImageView imageView, String gif_url) {
        if (imageView == null) {
            throw new IllegalArgumentException("argument error");
        }
        Glide.with(context).asGif().load(gif_url)
                .apply(RequestOptions.errorOf(R.drawable.pic_load_error))
                .apply(RequestOptions.priorityOf(Priority.HIGH))
                .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.RESOURCE))
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(imageView);
    }

    /**
     * 加载缩略图
     *
     * @param context
     * @param imageView
     * @param url
     */
    public static void displayThumbnailPic(Context context, ImageView imageView, String url) {
        if (imageView == null) {
            throw new IllegalArgumentException("argument error");
        }
        Glide.with(context).asBitmap().load(url)
                .apply(RequestOptions.errorOf(R.drawable.pic_load_error))
                .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.ALL))
                .thumbnail(0.5f)//1/2尺寸的缩略图
                .into(imageView);
    }

    /**
     * 加载 ARGB_8888 高质量图片
     *
     * @param context
     * @param imageView
     * @param url
     */
    public static void displayHighQualityPic(Context context, ImageView imageView, String url) {
        if (imageView == null) {
            throw new IllegalArgumentException("argument error");
        }
        Glide.with(context).asBitmap().load(url)
                .apply(RequestOptions.errorOf(R.drawable.pic_load_error))
                .apply(RequestOptions.priorityOf(Priority.HIGH))
                .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.RESOURCE))
                .apply(RequestOptions.formatOf(DecodeFormat.PREFER_ARGB_8888))
                .into(imageView);

    }

    /**
     * 加载圆形图片
     *
     * @param context
     * @param imageView
     * @param url
     */
    public static void displayCircle(Context context, ImageView imageView, String url) {
        if (imageView == null) {
            throw new IllegalArgumentException("argument error");
        }
        Glide.with(context).load(url)
                .apply(RequestOptions.errorOf(R.drawable.pic_load_error))
                .apply(RequestOptions.priorityOf(Priority.HIGH))
                .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.AUTOMATIC))
                .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(imageView);
    }

    /**
     * 加载圆角图片
     *
     * @param context
     * @param imageView
     * @param url
     */
    public static void displayRound(Context context, ImageView imageView, String url, int radius) {
        if (imageView == null) {
            throw new IllegalArgumentException("argument error");
        }
        Glide.with(context).load(url)
                .apply(RequestOptions.errorOf(R.drawable.pic_load_error))
                .apply(RequestOptions.priorityOf(Priority.HIGH))
                .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.AUTOMATIC))
                .apply(RequestOptions.bitmapTransform(new RoundedCorners(radius)))
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(imageView);
    }
}
