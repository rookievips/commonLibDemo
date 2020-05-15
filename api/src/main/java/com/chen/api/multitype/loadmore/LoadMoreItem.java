package com.chen.api.multitype.loadmore;


public class LoadMoreItem {

    public static final int LOADING = 0x001;//正在加载中
    public static final int NO_MORE = 0x002;//没有更多了
    public static final int LOAD_NORMAL = 0x003;

    private int loadStatus = LOAD_NORMAL;

    public boolean isLoading() {
        return loadStatus == LOADING;
    }

    public boolean isNoMore() {
        return loadStatus == NO_MORE;
    }

    public int getLoadStatus() {
        return loadStatus;
    }

    public void setLoadStatus(int loadStatus) {
        this.loadStatus = loadStatus;
    }
}
