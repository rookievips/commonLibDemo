package com.chen.commonlib.bean;

import java.util.List;

public class MessageBean {
    private boolean isLast;

    private List<RowsBean> rows;

    public boolean isLast() {
        return isLast;
    }

    public void setLast(boolean last) {
        isLast = last;
    }

    public List<RowsBean> getRows() {
        return rows;
    }

    public void setRows(List<RowsBean> rows) {
        this.rows = rows;
    }

    public static class RowsBean {
        /**
         * createTime : 2019-08-21 09:29:18
         * typeName : 订单消息
         * id : 1
         * type : 1
         * content : 您的 1190819536995 已经发货，【广州公交集团冷链运输】正在为您配送。
         */

        private String createTime;
        private String typeName;
        private int id;
        private int type;
        private String content;

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getTypeName() {
            return typeName;
        }

        public void setTypeName(String typeName) {
            this.typeName = typeName;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }
}
