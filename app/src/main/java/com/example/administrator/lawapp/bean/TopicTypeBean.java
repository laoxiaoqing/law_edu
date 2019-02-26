package com.example.administrator.lawapp.bean;

import java.util.List;

/**
 * Created by 吴青晓 on 2019/2/25
 * Describe: 查找科目 和该科目下题目数量
 */
public class TopicTypeBean {

    /**
     * success : true
     * data : [{"topic_type_id":1,"topic_type_name":"高校网络安全法律意识教育系统","sort_order":1,"num":6},{"topic_type_id":2,"topic_type_name":"网络安全法律","sort_order":2,"num":3},{"topic_type_id":3,"topic_type_name":"网络安全基础","sort_order":3,"num":2},{"topic_type_id":4,"topic_type_name":"七五普法","sort_order":4,"num":2},{"topic_type_id":5,"topic_type_name":"5","sort_order":null,"num":0},{"topic_type_id":6,"topic_type_name":"6","sort_order":null,"num":0},{"topic_type_id":7,"topic_type_name":"7","sort_order":null,"num":0},{"topic_type_id":9,"topic_type_name":"移动应用","sort_order":0,"num":0}]
     */

    private boolean success;
    private List<DataBean> data;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * topic_type_id : 1
         * topic_type_name : 高校网络安全法律意识教育系统
         * sort_order : 1
         * num : 6
         */

        private int topic_type_id;
        private String topic_type_name;
        private int sort_order;
        private int num;

        public int getTopic_type_id() {
            return topic_type_id;
        }

        public void setTopic_type_id(int topic_type_id) {
            this.topic_type_id = topic_type_id;
        }

        public String getTopic_type_name() {
            return topic_type_name;
        }

        public void setTopic_type_name(String topic_type_name) {
            this.topic_type_name = topic_type_name;
        }

        public int getSort_order() {
            return sort_order;
        }

        public void setSort_order(int sort_order) {
            this.sort_order = sort_order;
        }

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }
    }
}
