package com.chen.commonlib.ui.main.view;

import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;

import com.chen.api.http.ResponseEntity;
import com.chen.api.utils.ToastUtil;
import com.chen.api.widgets.donutchart.Donut;
import com.chen.api.widgets.donutchart.DonutChart;
import com.chen.commonlib.R;
import com.chen.commonlib.app.AbsMvpActivity;
import com.chen.commonlib.app.Constant;
import com.chen.commonlib.app.RequestEntityMap;
import com.chen.commonlib.app.retrofit.Http;
import com.chen.commonlib.app.retrofit.HttpCallback;
import com.chen.commonlib.app.retrofit.ObserverCallback;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AbsMvpActivity {
    private DonutChart donutChart;
    private long exitTime = 0;

    @BindView(R.id.ll_parent)
    ViewGroup ll_parent;


    @Override
    protected int layoutResId() {
        return R.layout.activity_main;
    }

    @Override
    protected void attachPresenter() {}


    @Override
    protected void initView() {
        commonTitleBarStyle("主页");
        findViewById(R.id.request).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                ToastUtil.showShort("喝杯水吧");
                http();
//                test();
//                test1();
            }
        });
        findViewById(R.id.jump).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startActivity(new Intent(mActivity, LoginActivity.class));
            }
        });
    }

    @Override
    protected void afterCreated() {
        initDonutChart();
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


    @Override
    public void onBackPressed() {
        if (System.currentTimeMillis() - exitTime > 2000) {
            ToastUtil.showShort("再按一次返回键，退出程序");
            exitTime = System.currentTimeMillis();
        } else {
            super.onBackPressed();
        }
    }

    private void http() {
        RequestEntityMap re = new RequestEntityMap();
        re.setCmd("wr-store/store/homepage/pages");
        re.putParameter("cityId",6401);
        Http.getDefault().getData(re,Constant.HOST_ONE + Constant.token)
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

    private void test() {
        Map<String,String> parms = new HashMap<>();
        parms.put("appPackageName","xld.jzdk.com");
        parms.put("appid","20181019");
        parms.put("appversion","1.5.6");
        parms.put("channelid","app");
        parms.put("clienttype","android");
        parms.put("devicecode","Redmi 6A");
        parms.put("deviceid","356616205686247");
        parms.put("locaddress","中国上海市浦东新区塘桥街道微山路1号甲");
        parms.put("locgps","121.518625|31.214102");
        parms.put("md5sign","f1e430feb158f1aa0950ec931905aa2a");
        parms.put("merchantcode","PZxkSS");
        parms.put("metaTag","SDKJ");
        parms.put("mobileno","13917777777");
        parms.put("reqtype","register");
        parms.put("verifycode","445555");

        Http.getDefault().test(Constant.HOST_TWO,parms)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ObserverCallback<>(new HttpCallback<Map<String, Object>>() {
                    @Override
                    public void onStart() {

                    }

                    @Override
                    public void onSuccess(Map<String, Object> responseEntity) {
                        System.out.println();
                    }

                    @Override
                    public void onFail(int code, String msg) {
                        System.out.println();
                    }

                    @Override
                    public void onComplete() {

                    }
                }));
    }

    private void test1() {
        Map<String,String> parms = new HashMap<>();
        parms.put("username","12345678961");
        parms.put("password","123456");
        Http.getDefault().test1(Constant.HOST_THREE,parms)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ObserverCallback<>(new HttpCallback<Map<String, Object>>() {
                    @Override
                    public void onStart() {

                    }

                    @Override
                    public void onSuccess(Map<String, Object> responseEntity) {
                        System.out.println();
                    }

                    @Override
                    public void onFail(int code, String msg) {
                        System.out.println();
                    }

                    @Override
                    public void onComplete() {

                    }
                }));
    }
}
