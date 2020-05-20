package com.chen.commonlib;


import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chen.api.multitype.MultiTypeAdapter;
import com.chen.api.multitype.loadmore.LoadMoreDelegate;
import com.chen.api.multitype.loadmore.LoadMoreItem;
import com.chen.api.ptr.PtrDefaultHandler;
import com.chen.api.ptr.PtrFrameLayout;
import com.chen.api.ptr.PtrHandler;
import com.chen.api.widgets.MultiStateView;
import com.chen.commonlib.adapter.viewbinder.MessageViewBinder;
import com.chen.commonlib.app.AbsActivity;
import com.chen.api.http.RequestEntity;
import com.chen.api.http.ResponseEntity;
import com.chen.api.http.OnRequestListener;
import com.chen.commonlib.bean.MessageBean;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class ListActivity extends AbsActivity {
    private MultiStateView msv;
    private PtrFrameLayout ptr;
    private RecyclerView rv;
    private MultiTypeAdapter multiAdapter;
    private List<Object> items = new ArrayList<>();
    private LoadMoreDelegate loadMoreDelegate = new LoadMoreDelegate();

    private int pageNum = 0;
    private boolean isRefresh;
    private boolean isFirst = true;

    @Override
    protected int layoutResId() {
        return R.layout.activity_list;
    }

    @Override
    protected void initView() {
        msv = findViewById(R.id.msv);
        ptr = findViewById(R.id.ptr);
        rv = findViewById(R.id.rv);
        multiAdapter = new MultiTypeAdapter();

        ptr.setPtrHandler(ptrHandler);
        loadMoreDelegate.attach(rv, loadMoreHandler);
        msv.setViewForState(R.layout.loading_view, MultiStateView.VIEW_STATE_LOADING);
        msv.setViewForState(R.layout.empty_view, MultiStateView.VIEW_STATE_EMPTY);

        MessageViewBinder messageViewBinder = new MessageViewBinder();
        multiAdapter.register(MessageBean.RowsBean.class, messageViewBinder);
        multiAdapter.register(LoadMoreItem.class, loadMoreDelegate.getLoadMoreViewBinder());
        multiAdapter.setItems(items);

        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(multiAdapter);
    }

    @Override
    protected void afterCreated() {
        refresh();
    }

    /**
     * 刷新
     */
    private PtrHandler ptrHandler = new PtrHandler() {
        @Override
        public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
            return PtrDefaultHandler.checkContentCanBePulledDown(frame, rv, header);
        }

        @Override
        public void onRefreshBegin(PtrFrameLayout frame) {
            refresh();
        }
    };

    /**
     * 加载更多
     */
    private LoadMoreDelegate.LoadMoreHandler loadMoreHandler = new LoadMoreDelegate.LoadMoreHandler() {
        @Override
        public boolean checkCanLoadMore() {
            return !ptr.isRefreshing();
        }

        @Override
        public void onLoadMore() {
            loadMore();
        }
    };

    private void refresh() {
        isRefresh = true;
        pageNum = 0;
        reqMsgList();
    }

    private void loadMore() {
        isRefresh = false;
        pageNum += 1;
        reqMsgList();
    }

    private void reqMsgList() {
        RequestEntity re = new RequestEntity();
        re.setCmd("wr-account/message/purchase/page");
        re.putParameter("pageNumber", pageNum);
        re.putParameter("pageSize", 15);

        request(re, false, new OnRequestListener<MessageBean>() {

            @Override
            public void onStart() {
                if (isFirst) {
                    msv.setViewState(MultiStateView.VIEW_STATE_LOADING);
                    isFirst = false;
                }
            }

            @Override
            public MessageBean jsonToObj(String responseStr) {
                return new Gson().fromJson(responseStr, MessageBean.class);
            }

            @Override
            public void onFail(int failCode, String msg) {
                ptr.refreshComplete();
                msv.setViewState(MultiStateView.VIEW_STATE_CONTENT);
            }

            @Override
            public void onResponseError(int errorCode, String msg) {
                ptr.refreshComplete();
                msv.setViewState(MultiStateView.VIEW_STATE_CONTENT);
            }

            @Override
            public void onResponse(ResponseEntity<MessageBean> responseEntity) {
                ptr.refreshComplete();
                if (isRefresh) {
                    items.clear();
                }

                MessageBean messageBean = responseEntity.getResponse();
                if (messageBean != null && messageBean.getRows() != null && messageBean.getRows().size() > 0) {
                    msv.setViewState(MultiStateView.VIEW_STATE_CONTENT);
                    items.addAll(messageBean.getRows());
                    loadMoreDelegate.addLoadItem(items);

                    if (messageBean.isLast()) {
                        loadMoreDelegate.setStatusNoMore();
                    } else {
                        loadMoreDelegate.setStatusNormal();
                    }

                    multiAdapter.notifyDataSetChanged();
                } else {
                    msv.setViewState(MultiStateView.VIEW_STATE_EMPTY);
                }
            }
        });
    }
}
