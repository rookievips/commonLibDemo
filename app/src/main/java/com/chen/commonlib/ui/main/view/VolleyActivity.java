package com.chen.commonlib.ui.main.view;

import com.chen.api.http.OnRequestListener;
import com.chen.api.http.ResponseEntity;
import com.chen.api.utils.ToastUtil;
import com.chen.commonlib.R;
import com.chen.commonlib.app.AbsActivity;
import com.chen.commonlib.app.RequestEntityMap;
import com.chen.commonlib.bean.VersionNew;
import com.google.gson.Gson;

import butterknife.OnClick;

public class VolleyActivity extends AbsActivity {

    @Override
    protected int layoutResId() {
        return R.layout.activity_volley;
    }

    @Override
    protected void initView() {
        commonTitleBarStyle("VolleyActivity");
    }

    @Override
    protected void afterCreated() {
//        checkVersionUpdate(false);
    }

    @OnClick(R.id.checkUpdate)
    public void checkUpdate() {
        checkVersionUpdate(true);
    }

    private void checkVersionUpdate(boolean showTip) {
        RequestEntityMap re = new RequestEntityMap();
        re.setCmd("wr-account/app/version");
        request(re, false, new OnRequestListener<VersionNew>() {

            @Override
            public void onStart() {

            }

            @Override
            public VersionNew jsonToObj(String responseStr) {
                return new Gson().fromJson(responseStr, VersionNew.class);
            }

            @Override
            public void onFail(int failCode, String msg) {
                System.out.println();
            }

            @Override
            public void onResponseError(int errorCode, String msg) {
                System.out.println();
            }

            @Override
            public void onResponse(ResponseEntity<VersionNew> responseEntity) {
                String updates = responseEntity.getResponse().getUpdates();
                String url = responseEntity.getResponse().getUrl();
                String version = responseEntity.getResponse().getVersion();
                boolean hasNewVersion = responseEntity.getResponse().isHasNewVersion();
                if (hasNewVersion) {
//                    AppUpdateHelper.newInstance().showUpdateNoticeDialog(mActivity, updates, url, version, false, R.mipmap.ic_launcher);
                } else {
                    if (showTip)
                        ToastUtil.showShort("当前已是最新版本");
                }

            }
        });
    }
}
