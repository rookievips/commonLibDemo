package com.chen.api.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.chen.api.R;
import com.chen.api.ucrop.UCrop;

import java.io.File;
import java.io.FileDescriptor;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Author: Chen
 * Date: 2018/7/11
 * Desc:
 */
public class PhotoCore implements View.OnClickListener {

    //调用拍照裁剪Code
    private static final int REQUEST_CAMERA_CROP_CODE = 0x01;
    //调用拍照不裁剪Code
    private static final int REQUEST_CAMERA_CODE = 0x03;
    //调用系统相册裁剪Code
    private static final int REQUEST_ALBUM_CROP_CODE = 0x02;
    //调用系统相册不裁剪Code
    private static final int REQUEST_ALBUM_CODE = 0x04;
    //调用系统裁剪Code
    private static final int REQUEST_CROP_CODE = 0x05;

    private int resultWidth = 200;
    private int resultHeight = 200;
    private int ratioX = 1;
    private int ratioY = 1;

    //获取照片的回调
    private PhotoResult photoResult;
    //所在的activity
    private Activity activity;
    private String fileProvider;
    private File imgFile;
    private Uri imgUri;
    private String curImgPath;
    private Dialog dialog;
    private View reUpload, sepLine;
    //是否需要裁剪
    private boolean isCrop = true;

    public PhotoCore(PhotoResult photoResult, Activity activity) {
        this.photoResult = photoResult;
        this.activity = activity;
        this.fileProvider = FileUtil.getFileProvider();
    }

    public void showPhotoDialog(File file, boolean isShowReUpload) {
        imgFile = file;
        imgUri = Uri.fromFile(file);

        if (dialog == null) {
            View contentView = LayoutInflater.from(activity).inflate(R.layout.dialog_photo, null);
            reUpload = contentView.findViewById(R.id.txt_reUpload);
            sepLine = contentView.findViewById(R.id.v_line);
            reUpload.setOnClickListener(this);
            contentView.findViewById(R.id.txt_take_photo).setOnClickListener(this);
            contentView.findViewById(R.id.txt_get_photo).setOnClickListener(this);
            contentView.findViewById(R.id.txt_cancel).setOnClickListener(this);

            dialog = new Dialog(activity, R.style.PhotoDialogStyle);
            dialog.setContentView(contentView);
            Window dialogWindow = dialog.getWindow();
            if (dialogWindow != null) {
                dialogWindow.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM);
                WindowManager.LayoutParams lp = dialogWindow.getAttributes();
                lp.width = DisplayUtil.getScreenWidth(activity);
                dialogWindow.setAttributes(lp);
            }
        }
        if (isShowReUpload) {
            reUpload.setVisibility(View.VISIBLE);
            sepLine.setVisibility(View.VISIBLE);
        } else {
            reUpload.setVisibility(View.GONE);
            sepLine.setVisibility(View.GONE);
        }
        dialog.show();
    }


    @Override
    public void onClick(View v) {
        if (dialog != null)
            dialog.dismiss();

        int id = v.getId();

        if (id == R.id.txt_reUpload) {//重新上传
            photoResult.onReUpload(curImgPath);

        } else if (id == R.id.txt_take_photo) {//拍照
            if (isCrop) {
                getPhotoFromCameraCrop();
            } else {
                getPhotoFromCamera();
            }

        } else if (id == R.id.txt_get_photo) {//从相册获取
            if (isCrop) {
                getPhotoFromAlbumCrop();
            } else {
                getPhotoFromAlbum();
            }
        }
    }

    /**
     * 拍照后裁剪
     */
    private void getPhotoFromCameraCrop() {
        activity.startActivityForResult(takePhotoIntent(), REQUEST_CAMERA_CROP_CODE);
    }

    /**
     * 拍照后不裁剪
     */
    private void getPhotoFromCamera() {
        activity.startActivityForResult(takePhotoIntent(), REQUEST_CAMERA_CODE);
    }

    /**
     * 调用系统相机对Intent参数进行封装
     *
     * @return
     */
    private Intent takePhotoIntent() {
        //适配Android7.0以上私有文件目录被限制访问
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Uri contentUri = FileProvider.getUriForFile(activity, fileProvider, imgFile);
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, contentUri);
            // 授予目录临时共享权限
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            return intent;
        } else {
            Intent intent = new Intent();
            intent.putExtra("camerasensortype", 2);//调用前置摄像头
            intent.putExtra("autofocus", true);//自动对焦
            intent.putExtra("fullScreen", false);//全屏
            intent.putExtra("showActionIcons", false);
            intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);//设置Action为拍照
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imgUri);//将拍取的照片保存到指定Uri
            return intent;
        }
    }

    /**
     * 获取系统相册图片后裁剪
     */
    private void getPhotoFromAlbumCrop() {
        activity.startActivityForResult(getPhotoIntent(), REQUEST_ALBUM_CROP_CODE);
    }

    /**
     * 获取系统相册图片后不裁剪
     */
    private void getPhotoFromAlbum() {
        activity.startActivityForResult(getPhotoIntent(), REQUEST_ALBUM_CODE);
    }

    /**
     * 调用系统相册对Intent进行封装
     *
     * @return
     */
    private Intent getPhotoIntent() {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_PICK);
        intent.setType("image/*");//从所有图片中进行选择
        return intent;
    }

    public void onResult(int requestCode, int resultCode, Intent intent) {
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CAMERA_CROP_CODE:
                    uCropPic(imgUri, imgUri);
                    break;

                case REQUEST_CAMERA_CODE:
                    try {
                        scaleZoomImage(imgUri, imgUri);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;

                case REQUEST_ALBUM_CROP_CODE:
                    if (intent != null) {
                        Uri uri = intent.getData();
                        uCropPic(uri, imgUri);
                    }
                    break;

                case REQUEST_ALBUM_CODE:
                    if (intent != null) {
                        Uri uri = intent.getData();
                        try {
                            scaleZoomImage(uri, imgUri);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    break;

                case REQUEST_CROP_CODE:
                    curImgPath = imgFile.getPath();
                    photoResult.onSuccess(imgFile.getPath());
                    break;

                case UCrop.REQUEST_CROP:
                    LogUtil.i("imageUri", imgUri.toString());
                    curImgPath = imgFile.getPath();
                    photoResult.onSuccess(imgFile.getPath());
                    break;
            }
        }
    }

    /**
     * 系统自带裁剪
     */
    private void cropPic(Uri sourceUri, Uri outputUri) {
        Intent intent = new Intent("com.android.camera.action.CROP");//发送剪裁信号
        intent.setDataAndType(sourceUri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", ratioX);//X方向上的比例
        intent.putExtra("aspectY", ratioY);//Y方向上的比例
        intent.putExtra("outputX", resultWidth);//剪裁区的宽
        intent.putExtra("outputY", resultHeight);//剪裁区的高
        intent.putExtra("scale", true);//是否保留比例
        intent.putExtra("scaleUpIfNeeded", true);//去黑边
        intent.putExtra(MediaStore.EXTRA_OUTPUT, outputUri);
        intent.putExtra("return-data", false);//是否将数据保留在BitMap中返回
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("onFaceDetection", true);//人脸识别
        activity.startActivityForResult(intent, REQUEST_CROP_CODE);

    }

    /**
     * uCrop裁剪
     *
     * @param sourceUri
     * @param outputUri
     */
    private void uCropPic(Uri sourceUri, Uri outputUri) {
        UCrop uCrop = UCrop.of(sourceUri, outputUri);
        uCrop.withAspectRatio(ratioX, ratioY);
        uCrop.withMaxResultSize(resultWidth, resultHeight);
        UCrop.Options options = new UCrop.Options();
        options.setToolbarColor(ContextCompat.getColor(activity, R.color.black));
        options.setStatusBarColor(ContextCompat.getColor(activity, R.color.black));
        options.setToolbarTitle("裁剪");
        options.setToolbarWidgetColor(Color.parseColor("#ffffff"));
        options.setCompressionFormat(Bitmap.CompressFormat.JPEG);
        options.setCompressionQuality(100);
        options.setHideBottomControls(true);
        uCrop.withOptions(options);
        uCrop.start(activity);
    }

    public interface PhotoResult {
        void onSuccess(String newImgPath);

        void onReUpload(String curImgPath);
    }

    public void setResultSize(int resultWidth, int resultHeight) {
        this.resultWidth = resultWidth;
        this.resultHeight = resultHeight;
    }

    public void setRatio(int ratioX, int ratioY) {
        this.ratioX = ratioX;
        this.ratioY = ratioY;
    }

    public void setCrop(boolean crop) {
        isCrop = crop;
    }


    /**
     * 等比缩放原始图片
     * @param sourceUri 原图的uri
     * @param outputUri 用于存放缩放后的uri
     * @throws Exception
     */
    private void scaleZoomImage(Uri sourceUri, Uri outputUri) throws Exception {
        if (sourceUri == null || outputUri == null) {
            throw new NullPointerException("Uri cannot be null");
        }

        ParcelFileDescriptor parcelFileDescriptor = activity.getContentResolver().openFileDescriptor(sourceUri, "r");
        FileDescriptor fileDescriptor;
        if (parcelFileDescriptor != null) {
            fileDescriptor = parcelFileDescriptor.getFileDescriptor();
        } else {
            throw new NullPointerException("ParcelFileDescriptor was null for given Uri");
        }

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFileDescriptor(fileDescriptor, null, options);
        options.inSampleSize = calculateInSampleSize(options, resultWidth, resultHeight);
        options.inJustDecodeBounds = false;
        options.inPurgeable = true;
        Bitmap bitmap = BitmapFactory.decodeFileDescriptor(fileDescriptor, null, options);

        OutputStream outputStream = null;
        try {
            outputStream = activity.getContentResolver().openOutputStream(outputUri);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
            if (!bitmap.isRecycled())
                bitmap.recycle();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (outputStream != null) {
                    outputStream.flush();
                    outputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        curImgPath = imgFile.getPath();
        photoResult.onSuccess(imgFile.getPath());
    }

    private int calculateInSampleSize(@NonNull BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            while ((height / inSampleSize) > reqHeight || (width / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }
        return inSampleSize;
    }
}
