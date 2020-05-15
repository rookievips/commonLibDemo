package com.chen.api.widgets.flowlayout;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.chen.api.R;

import java.util.ArrayList;
import java.util.List;

public class TagAdapter<T> extends BaseAdapter implements OnInitSelectedPosition {

    private final Context mContext;
    private final List<T> mDataList;

    public TagAdapter(Context context) {
        this.mContext = context;
        mDataList = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return mDataList.size();
    }

    @Override
    public Object getItem(int position) {
        return mDataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.flow_layout_item, null);
        TextView textView = view.findViewById(R.id.flow_layout_txt_tag);
        T t = mDataList.get(position);
        if (t instanceof String) {
            textView.setText((String) t);
        }
        return view;
    }

    public void onlyAddAll(List<T> data) {
        mDataList.addAll(data);
        notifyDataSetChanged();
    }

    public void clearAndAddAll(List<T> data) {
        mDataList.clear();
        onlyAddAll(data);
    }

    private int selectPosition = -1;
    @Override
    public boolean setSelectedPosition(int position) {
        selectPosition = position;
        notifyDataSetChanged();
        return true;
    }

    @Override
    public int getSelectedPosition() {
        return selectPosition;
    }
}
