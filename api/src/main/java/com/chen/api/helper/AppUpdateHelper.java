package com.chen.api.helper;


import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;

import com.azhon.appupdate.config.UpdateConfiguration;
import com.azhon.appupdate.manager.DownloadManager;
import com.chen.api.widgets.DownloadProgressDialog;

public class AppUpdateHelper {
    private static AppUpdateHelper instance;

    private AppUpdateHelper() {
    }

    public static AppUpdateHelper newInstance() {
        if (instance == null) {
            synchronized (AppUpdateHelper.class) {
                if (instance == null) {
                    instance = new AppUpdateHelper();
                }
            }
        }
        return instance;
    }

    /**
     *
     * @param activity
     * @param updateMsg 更新内容信息
     * @param downloadUrl apk下载的url
     * @param version 版本
     * @param forceUpdate 是否强制更新
     * @param smallIcon 通知栏图标
     */
    public void showUpdateNoticeDialog(Activity activity, String updateMsg, String downloadUrl, String version, boolean forceUpdate, int smallIcon) {

        if (forceUpdate) {
            AlertDialog dialog = new AlertDialog.Builder(activity)
                    .setTitle("版本更新：V" + version)
                    .setMessage(updateMsg)
                    .setCancelable(false)
                    .setPositiveButton("立即更新", (dialog1, which) -> {
                        downloadApk(activity, downloadUrl, smallIcon);
                        dialog1.dismiss();
                    })
                    .setNeutralButton("去网页更新", (dialog12, which) -> webDownloadApk(activity,downloadUrl))
                    .create();
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();

        } else {
            AlertDialog dialog = new AlertDialog.Builder(activity)
                    .setTitle("版本更新:V" + version)
                    .setMessage(updateMsg)
                    .setPositiveButton("立即更新", (dialog13, which) -> {
                        downloadApk(activity, downloadUrl, smallIcon);
                        dialog13.dismiss();
                    })
                    .setNeutralButton("去网页更新", (dialog14, which) -> webDownloadApk(activity,downloadUrl))
                    .setNegativeButton("暂不更新", (dialog15, which) -> dialog15.dismiss())
                    .create();
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();
        }


    }

    private void downloadApk(Activity activity, String downloadUrl, int smallIcon) {
        DownloadManager manager = DownloadManager.getInstance(activity);
        manager.setApkName("latest.apk")
                .setApkUrl(downloadUrl)
                .setConfiguration(new UpdateConfiguration())
                .setSmallIcon(smallIcon);
        Dialog downloadProgressDialog = new DownloadProgressDialog(activity);
        downloadProgressDialog.show();
        manager.download();
    }

    private void webDownloadApk(Activity activity,String downloadUrl){
        Intent intent= new Intent();

        intent.setAction(Intent.ACTION_VIEW);
        Uri content_url = Uri.parse(downloadUrl);
        intent.setData(content_url);

        activity.startActivity(Intent.createChooser(intent, "下载app"));
    }

}
