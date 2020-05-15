package com.chen.commonlib.adapter.viewbinder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.chen.api.multitype.ItemViewBinder;
import com.chen.commonlib.R;
import com.chen.commonlib.bean.MessageBean;


/**
 * @Des
 * @Author DELL
 * @Date 2019/8/29 11:12
 */
public class MessageViewBinder extends ItemViewBinder<MessageBean.RowsBean, MessageViewBinder.ViewHolder> {

    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View itemView = inflater.inflate(R.layout.item_message, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull MessageBean.RowsBean item) {
        switch (item.getType()) {
            case 1://订单消息
                holder.img_msg_type.setImageResource(R.mipmap.icon_msg_order);
                holder.txt_msg_type.setText("订单消息");
                break;
            case 2://减免退消息
                holder.img_msg_type.setImageResource(R.mipmap.icon_msg_reducing);
                holder.txt_msg_type.setText("减免退消息");
                break;
            case 3://需求消息
                holder.img_msg_type.setImageResource(R.mipmap.icon_msg_demand);
                holder.txt_msg_type.setText("需求消息");
                break;
            case 4://资金消息
                holder.img_msg_type.setImageResource(R.mipmap.icon_msg_fund);
                holder.txt_msg_type.setText("资金消息");
                break;
            default:
                break;
        }

        holder.txt_time.setText(item.getCreateTime());
        holder.txt_msg_content.setText(item.getContent());
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView img_msg_type;
        private TextView txt_msg_type, txt_time, txt_msg_content;

        ViewHolder(View itemView) {
            super(itemView);

            img_msg_type = itemView.findViewById(R.id.img_msg_type);
            txt_msg_type = itemView.findViewById(R.id.txt_msg_type);
            txt_time = itemView.findViewById(R.id.txt_time);
            txt_msg_content = itemView.findViewById(R.id.txt_msg_content);
        }
    }

}
