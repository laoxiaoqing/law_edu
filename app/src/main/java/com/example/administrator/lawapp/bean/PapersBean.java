package com.example.administrator.lawapp.bean;

import java.util.List;

/**
 * Created by 吴青晓 on 2019/2/28
 * Describe:Constants.PAPERSTOIC_URL + "?pagenum=1&pagesize=10&user_id="+user_id
 */
public class PapersBean {

    /**
     * success : true
     * data : [{"papers_id":98,"user_id":1,"papers_score":"未完成","add_time":"2019-02-28 22:32:28","topic_type_id":1,"topic_type_name":"高校网络安全法律意识教育系统"},{"papers_id":97,"user_id":1,"papers_score":"40","add_time":"2019-02-28 22:31:35","topic_type_id":1,"topic_type_name":"高校网络安全法律意识教育系统"},{"papers_id":96,"user_id":1,"papers_score":"70","add_time":"2019-02-28 22:27:59","topic_type_id":1,"topic_type_name":"高校网络安全法律意识教育系统"},{"papers_id":95,"user_id":1,"papers_score":null,"add_time":"2019-02-28 22:26:01","topic_type_id":2,"topic_type_name":"网络安全法律"},{"papers_id":94,"user_id":1,"papers_score":"20","add_time":"2019-02-28 22:18:36","topic_type_id":2,"topic_type_name":"网络安全法律"},{"papers_id":93,"user_id":1,"papers_score":"100","add_time":"2019-02-28 21:26:14","topic_type_id":1,"topic_type_name":"高校网络安全法律意识教育系统"},{"papers_id":92,"user_id":1,"papers_score":"80","add_time":"2019-02-28 21:25:56","topic_type_id":1,"topic_type_name":"高校网络安全法律意识教育系统"},{"papers_id":91,"user_id":1,"papers_score":"20","add_time":"2019-02-28 21:25:47","topic_type_id":4,"topic_type_name":"七五普法"},{"papers_id":90,"user_id":1,"papers_score":null,"add_time":"2019-02-28 21:24:28","topic_type_id":4,"topic_type_name":"七五普法"},{"papers_id":89,"user_id":1,"papers_score":null,"add_time":"2019-02-28 21:23:32","topic_type_id":3,"topic_type_name":"网络安全基础"}]
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
         * papers_id : 98
         * user_id : 1
         * papers_score : 未完成
         * add_time : 2019-02-28 22:32:28
         * topic_type_id : 1
         * topic_type_name : 高校网络安全法律意识教育系统
         */

        private int papers_id;
        private int user_id;
        private String papers_score;
        private String add_time;
        private int topic_type_id;
        private String topic_type_name;

        public int getPapers_id() {
            return papers_id;
        }

        public void setPapers_id(int papers_id) {
            this.papers_id = papers_id;
        }

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }

        public String getPapers_score() {
            return papers_score;
        }

        public void setPapers_score(String papers_score) {
            this.papers_score = papers_score;
        }

        public String getAdd_time() {
            return add_time;
        }

        public void setAdd_time(String add_time) {
            this.add_time = add_time;
        }

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
    }
}
