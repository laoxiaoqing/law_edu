package com.example.administrator.lawapp.bean;

import java.util.List;

/**
 * Created by 吴青晓 on 2019/2/28
 * Describe
 */
public class PapersTopicBean {

    /**
     * success : true
     * data : [{"papers_topic_id":5,"topic_id":4,"papers_id":1,"user_key":"a","right_key":"a"},{"papers_topic_id":6,"topic_id":5,"papers_id":1,"user_key":null,"right_key":"c"},{"papers_topic_id":7,"topic_id":22,"papers_id":1,"user_key":null,"right_key":"c"},{"papers_topic_id":8,"topic_id":15,"papers_id":1,"user_key":null,"right_key":"a"},{"papers_topic_id":9,"topic_id":19,"papers_id":1,"user_key":null,"right_key":"d"},{"papers_topic_id":10,"topic_id":6,"papers_id":1,"user_key":null,"right_key":"c"},{"papers_topic_id":11,"topic_id":17,"papers_id":1,"user_key":null,"right_key":"a"},{"papers_topic_id":12,"topic_id":18,"papers_id":1,"user_key":null,"right_key":"d"},{"papers_topic_id":13,"topic_id":2,"papers_id":1,"user_key":null,"right_key":"d"},{"papers_topic_id":14,"topic_id":1,"papers_id":1,"user_key":null,"right_key":"d"}]
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
         * papers_topic_id : 5
         * topic_id : 4
         * papers_id : 1
         * user_key : a
         * right_key : a
         */

        private int papers_topic_id;
        private int topic_id;
        private int papers_id;
        private String user_key;
        private String right_key;

        public int getPapers_topic_id() {
            return papers_topic_id;
        }

        public void setPapers_topic_id(int papers_topic_id) {
            this.papers_topic_id = papers_topic_id;
        }

        public int getTopic_id() {
            return topic_id;
        }

        public void setTopic_id(int topic_id) {
            this.topic_id = topic_id;
        }

        public int getPapers_id() {
            return papers_id;
        }

        public void setPapers_id(int papers_id) {
            this.papers_id = papers_id;
        }

        public String getUser_key() {
            return user_key;
        }

        public void setUser_key(String user_key) {
            this.user_key = user_key;
        }

        public String getRight_key() {
            return right_key;
        }

        public void setRight_key(String right_key) {
            this.right_key = right_key;
        }
    }
}
