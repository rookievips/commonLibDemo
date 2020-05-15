package com.chen.api.widgets;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.azhon.appupdate.config.UpdateConfiguration;
import com.azhon.appupdate.listener.OnDownloadListener;
import com.azhon.appupdate.manager.DownloadManager;
import com.chen.api.R;

import java.io.File;


public class DownloadProgressDialog extends Dialog implements OnDownloadListener {
    private ProgressBar progressBar;
    private TextView percent;

    public DownloadProgressDialog(@NonNull Context context) {
        super(context,R.style.Theme_AppCompat_Dialog_Alert);
        init(context);
    }

    private void init(Context context) {
        UpdateConfiguration configuration = DownloadManager.getInstance().getConfiguration();
        configuration.setOnDownloadListener(this);
        View view = LayoutInflater.from(context).inflate(R.layout.download_progress_dialog, null);
        progressBar = view.findViewById(R.id.download_progress);
        percent = view.findViewById(R.id.download_present);
        setContentView(view);
    }

    @Override
    public void start() {

    }

    @Override
    public void downloading(int max, int progress) {
        int curr = (int) (progress / (double) max * 100.0);
        progressBar.setMax(100);
        progressBar.setProgress(curr);
        percent.setText(curr + "%");
    }

    @Override
    public void done(File apk) {
        dismiss();
    }

    @Override
    public void error(Exception e) {

    }
}
