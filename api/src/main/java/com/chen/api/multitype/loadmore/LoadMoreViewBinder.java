package com.chen.api.multitype.loadmore;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chen.api.R;
import com.chen.api.multitype.ItemViewBinder;


public class LoadMoreViewBinder extends ItemViewBinder<LoadMoreItem, LoadMoreViewBinder.ViewHolder> {

    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View itemView = inflater.inflate(R.layout.load_more_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull LoadMoreItem bean) {
        holder.switchLoadStatus(bean);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private View loading, load_no_more;

        ViewHolder(View itemView) {
            super(itemView);
            loading = itemView.findViewById(R.id.load_more_loading);
            load_no_more = itemView.findViewById(R.id.load_more_no_more);
        }

        void switchLoadStatus(@NonNull LoadMoreItem bean) {
            int loadStatus = bean.getLoadStatus();
            if (loadStatus == LoadMoreItem.LOADING || loadStatus == LoadMoreItem.LOAD_NORMAL) {
                load_no_more.setVisibility(View.GONE);
                loading.setVisibility(View.VISIBLE);
            }
            if (loadStatus == LoadMoreItem.NO_MORE) {
                loading.setVisibility(View.GONE);
                load_no_more.setVisibility(View.VISIBLE);
            }
        }
    }
}
