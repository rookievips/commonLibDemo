package com.chen.commonlib.ui.login.view;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;

import com.chen.api.http.ResponseEntity;
import com.chen.api.utils.FileUtil;
import com.chen.api.utils.PhotoCore;
import com.chen.commonlib.R;
import com.chen.commonlib.app.AbsMvpActivity;
import com.chen.commonlib.app.Cmd;
import com.chen.commonlib.app.RequestEntityMap;
import com.chen.commonlib.app.retrofit.Http;
import com.chen.commonlib.app.retrofit.HttpCallback;
import com.chen.commonlib.app.retrofit.ObserverCallback;
import com.chen.commonlib.ui.login.contact.LoginContact;
import com.chen.commonlib.ui.login.presenter.LoginPresenterImp;
import com.chen.commonlib.ui.main.view.ListActivity;
import com.makeramen.roundedimageview.RoundedImageView;

import java.io.File;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class LoginActivity extends AbsMvpActivity<LoginPresenterImp> implements LoginContact.LoginView, PhotoCore.PhotoResult {
    private PhotoCore photoCore;
    private String imgFilePath = FileUtil.getImageCacheDir() + File.separator + "avatar.jpg";
    @BindView(R.id.request)
    Button request;
    @BindView(R.id.img_head)
    RoundedImageView img_head;

    @Override
    protected int layoutResId() {
        return R.layout.activity_login;
    }

    @Override
    protected void attachPresenter() {
        mPresenter.attachView(this);
    }

    @Override
    protected void initView() {
        commonTitleBarStyle("登录页");
        photoCore = new PhotoCore(this, this);

        request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                RequestEntityMap re = new RequestEntityMap();
//                re.setCmd(Cmd.CMD_LOGIN);
//                re.putParameter("domain","APP");
//                re.putParameter("part","PURCHASE");
//                re.putParameter("businessName","100000362");
//                re.putParameter("password","Ab123456");
//                re.putParameter("username","15021998606");
//                mPresenter.login(re);
                login();
            }
        });
        img_head.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                photoCore.setCrop(false);
//                photoCore.setRatio(2, 1);
//                photoCore.setResultSize(1000, 1000);
                photoCore.showPhotoDialog(new File(imgFilePath), false);
            }
        });

        if (!checkPermission(Manifest.permission.CAMERA) || !checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            requestPermission(1, Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        } else {
//            ToastUtil.showShort("相机权限已打开");
        }

//        ToastUtil.showShort(getIntent().getStringExtra("msg"));
    }


    @Override
    protected void afterCreated() {

    }


    private void login() {
        RequestEntityMap re = new RequestEntityMap();
        re.setCmd(Cmd.CMD_LOGIN);
        re.putParameter("domain","APP");
        re.putParameter("part","PURCHASE");
        re.putParameter("businessName","100000362");
        re.putParameter("password","Ab123456");
        re.putParameter("username","15021998603");

        Http.getDefault().login(re)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ObserverCallback<>(new HttpCallback<ResponseEntity<Map<String, Object>>>() {
                    @Override
                    public void onStart() {
                        showLoading();
                    }

                    @Override
                    public void onSuccess(ResponseEntity<Map<String, Object>> responseEntity) {
                        System.out.println();
                    }

                    @Override
                    public void onFail(int code, String msg) {
                        hideLoading();
                    }

                    @Override
                    public void onComplete() {
                        hideLoading();
                    }
                }));
    }

    @Override
    public void loginSuccess(Map<String, Object> loginEntity) {
        System.out.println();
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1:
                if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    setupPermission(mActivity, "相机", "相机功能");
                } else {
//                    ToastUtil.showShort("相机权限已打开");
                }

                if (grantResults[1] != PackageManager.PERMISSION_GRANTED) {
                    setupPermission(mActivity, "媒体文件", "存储");
                } else {
//                    ToastUtil.showShort("文件存储权限已打开");
                }
                break;
        }
    }

    @OnClick(R.id.jump_h5)
    public void jumpToH5() {
//        H5Open.open(
//                this,
//                true,
//                H5Links.HTML_CLASS_ROOM,
//                "课堂");

        startActivity(new Intent(this, ListActivity.class));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_CANCELED) {
            return;
        }
        if (photoCore != null) {
            photoCore.onResult(requestCode, resultCode, data);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onSuccess(String newImgPath) {
//        try {
//            String s = FileUtil.encodeBase64File(newImgPath);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        System.out.println();
        //    /storage/emulated/0/Android/data/com.chen.commonlib/cache/images/avatar.jpg
        //    /storage/emulated/0/Android/data/com.chen.commonlib/cache/images/avatar.jpg

        Bitmap bitmap = BitmapFactory.decodeFile(newImgPath);
        img_head.setImageBitmap(bitmap);
    }

    @Override
    public void onReUpload(String curImgPath) {

    }

}
