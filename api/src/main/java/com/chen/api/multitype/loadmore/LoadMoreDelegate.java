package com.chen.api.multitype.loadmore;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.List;


public class LoadMoreDelegate {
    private LoadMoreItem loadMoreItem = new LoadMoreItem();
    private LoadMoreViewBinder loadMoreViewBinder = new LoadMoreViewBinder();

    public LoadMoreViewBinder getLoadMoreViewBinder() {
        return loadMoreViewBinder;
    }

    public void setStatusNoMore() {
        loadMoreItem.setLoadStatus(LoadMoreItem.NO_MORE);
    }

    public void setStatusNormal() {
        loadMoreItem.setLoadStatus(LoadMoreItem.LOAD_NORMAL);
    }

    public void addLoadItem(List<Object> items) {
        if (items != null) {
            int s = items.size();
            if (s > 0) {
                s--;
                Object o = items.get(s);
                if (!loadMoreItem.equals(o)) {
                    items.remove(loadMoreItem);
                    items.add(loadMoreItem);
                }
            }
        }
    }

    /**
     * 当有固定的headerView的时候
     *
     * @param items
     */
    public void addLoadItemWithHeaderView(List<Object> items) {
        if (items != null) {
            int s = items.size() - 1;
            if (s > 0) {
                s--;
                Object o = items.get(s);
                if (!loadMoreItem.equals(o)) {
                    items.remove(loadMoreItem);
                    items.add(loadMoreItem);
                }
            }
        }
    }

    public void attach(RecyclerView recyclerView, LoadMoreHandler loadMoreHandler) {
        recyclerView.addOnScrollListener(new EndlessScrollListener(loadMoreHandler));
    }

    private class EndlessScrollListener extends RecyclerView.OnScrollListener {
        private LoadMoreHandler loadMoreHandler;

        private EndlessScrollListener(LoadMoreHandler loadMoreHandler) {
            this.loadMoreHandler = loadMoreHandler;
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            if (!loadMoreHandler.checkCanLoadMore() || dy < 0 || loadMoreItem.isLoading() || loadMoreItem.isNoMore()) {
                return;
            }
            LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
            int itemCount = layoutManager.getItemCount();
            int visibleItemCount = recyclerView.getChildCount();
            int firstVisibleItem = layoutManager.findFirstVisibleItemPosition();
            boolean isBottom = (itemCount - visibleItemCount) <= firstVisibleItem;

            if (isBottom) {
                loadMoreItem.setLoadStatus(LoadMoreItem.LOADING);
                loadMoreHandler.onLoadMore();
            }
        }
    }

    public interface LoadMoreHandler {
        boolean checkCanLoadMore();

        void onLoadMore();
    }

}
