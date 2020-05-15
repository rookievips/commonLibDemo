package com.chen.commonlib;

import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;

import com.chen.api.helper.AppUpdateHelper;
import com.chen.api.utils.ToastUtil;
import com.chen.api.widgets.donutchart.Donut;
import com.chen.api.widgets.donutchart.DonutChart;
import com.chen.commonlib.app.AbsActivity;
import com.chen.commonlib.app.option.RequestEntity;
import com.chen.commonlib.app.option.ResponseEntity;
import com.chen.commonlib.app.option.volley.OnRequestListener;
import com.chen.commonlib.bean.VersionNew;
import com.chen.commonlib.ui.login.view.LoginActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Map;

import butterknife.BindView;

public class MainActivity extends AbsActivity {
    private DonutChart donutChart;
    private long exitTime = 0;

    @BindView(R.id.ll_parent)
    ViewGroup ll_parent;


    @Override
    protected int layoutResId() {
        return R.layout.activity_main;
    }

    @Override
    protected void attachPresenter() {

    }

    @Override
    protected void initView() {
        findViewById(R.id.request).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtil.showShort("喝杯茶吧");
//                login();
            }
        });
        findViewById(R.id.jump).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mActivity, LoginActivity.class));
            }
        });
    }

    @Override
    protected void afterCreated() {
        initDonutChart();
        checkVersionUpdate();
    }

    private void login() {
        RequestEntity requestEntity = new RequestEntity();
        requestEntity.setCmd("wr-account/merchant/isAuthNeedBind");
        requestEntity.putParameter("phone", "13900010003");

        request(requestEntity, true, new OnRequestListener<Map<String, Object>>() {

            @Override
            public Map<String, Object> jsonToObj(String responseStr) {
                return new Gson().fromJson(responseStr, new TypeToken<Map<String, Object>>() {
                }.getType());
            }

            @Override
            public void onStart() {

            }

            @Override
            public void onFail(int failCode, String msg) {

            }

            @Override
            public void onResponseError(int failCode, String msg) {

            }


            @Override
            public void onResponse(ResponseEntity<Map<String, Object>> responseEntity) {
                System.out.println();
            }
        });
    }

    private void initDonutChart() {
        int colors[] = {Color.parseColor("#9c8dd0"), Color.parseColor("#fc6817"), Color.parseColor("#fec004")};
        ArrayList<Donut> donutList = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            if (i == 0) {
                Donut donut = new Donut();
                donut.setName("苹果");
                donut.setRatio("0.5");
                donut.setColor(colors[i]);
                donutList.add(donut);
            }
            if (i == 1) {
                Donut donut = new Donut();
                donut.setName("谷歌");
                donut.setRatio("0.3");
                donut.setColor(colors[i]);
                donutList.add(donut);
            }
            if (i == 2) {
                Donut donut = new Donut();
                donut.setName("华为");
                donut.setRatio("0.2");
                donut.setColor(colors[i]);
                donutList.add(donut);
            }
        }

        if (donutChart == null) {
            donutChart = new DonutChart(this, formatDecimal("100"), 0xffffffff, calculateDegree(donutList), "手机销量(台)");
            ll_parent.addView(donutChart);
        } else {
            donutChart.startDonut(formatDecimal("100"), calculateDegree(donutList), "手机销量(台)");
        }
    }

    public static String formatDecimal(String s1) {
        DecimalFormat df = new DecimalFormat("#0.00");
        return df.format(getDouble(s1));
    }

    public static double getDouble(String s) {
        try {

            return Double.valueOf(s);
        } catch (Exception e) {

            return 0;
        }
    }

    private ArrayList<Donut> calculateDegree(ArrayList<Donut> data) {
        float total = 0;
        for (int i = 0; i < data.size(); i++) {
            Donut donut = data.get(i);
            total += donut.getRatioFloat();
        }
        for (int i = 0; i < data.size(); i++) {
            Donut donut = data.get(i);
            donut.setDegree(360 * (donut.getRatioFloat() / total));
        }

        return data;
    }


    private void checkVersionUpdate() {
        RequestEntity re = new RequestEntity();
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
            public void onResponseError(int failCode, String msg) {
                System.out.println();
            }

            @Override
            public void onResponse(ResponseEntity<VersionNew> responseEntity) {
                String updates = responseEntity.getResponse().getUpdates();
                String url = responseEntity.getResponse().getUrl();
                String version = responseEntity.getResponse().getVersion();
                boolean hasNewVersion = responseEntity.getResponse().isHasNewVersion();
                if (hasNewVersion) {
                    AppUpdateHelper.newInstance().showUpdateNoticeDialog(mActivity,updates,url,version,true,R.mipmap.ic_launcher);
                }

            }
        });
    }


    @Override
    public void onBackPressed() {
        if (System.currentTimeMillis() - exitTime > 2000) {
            ToastUtil.showShort("再按一次返回键，退出程序");
            exitTime = System.currentTimeMillis();
        } else {
            super.onBackPressed();
        }
    }
}
