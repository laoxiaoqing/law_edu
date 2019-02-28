package com.example.administrator.lawapp.bean;

import java.util.List;

/**
 * Created by 吴青晓 on 2019/2/25
 * Describe:查找题目 十条
 */
public class TopicBean {

    /**
     * success : true
     * data : [{"topic_id":22,"topic_type_id":1,"topic_content":"大萨达撒大所","topic_picture":null,"topic_score":null,"a_option":"wrong1","b_option":"wrong1","c_option":"ok","d_option":"wrong1","right_key":"c"},{"topic_id":6,"topic_type_id":1,"topic_content":"我是题目","topic_picture":null,"topic_score":null,"a_option":"wrong1","b_option":"wrong1","c_option":"ok","d_option":"wrong1","right_key":"c"},{"topic_id":14,"topic_type_id":1,"topic_content":"气味儿二二二二二二二二气味儿二二二二二二","topic_picture":null,"topic_score":null,"a_option":"wrong1","b_option":"ok","c_option":"wrong1","d_option":"wrong1","right_key":"c"},{"topic_id":25,"topic_type_id":1,"topic_content":"XCZXC","topic_picture":null,"topic_score":null,"a_option":"wrong1","b_option":"wrong1","c_option":"wrong1","d_option":"ok","right_key":"d"},{"topic_id":1,"topic_type_id":1,"topic_content":"2010年7月，某网站在网上公开数十万分有关阿富汗战争、伊拉克战争、美国外交相关文件、引起轩然大波，称为","topic_picture":"/images","topic_score":5,"a_option":"wrong1","b_option":"wrong1","c_option":"wrong1","d_option":"ok","right_key":"d"},{"topic_id":3,"topic_type_id":1,"topic_content":"高校网络安全法律意识教育系","topic_picture":null,"topic_score":null,"a_option":"ok","b_option":"wrong1","c_option":"wrong1","d_option":"wrong1","right_key":"a"},{"topic_id":19,"topic_type_id":1,"topic_content":"发过火规范化","topic_picture":null,"topic_score":null,"a_option":"wrong1","b_option":"wrong1","c_option":"wrong1","d_option":"ok","right_key":"d"},{"topic_id":26,"topic_type_id":1,"topic_content":"SADSADSA","topic_picture":null,"topic_score":null,"a_option":"wrong1","b_option":"wrong1","c_option":"wrong1","d_option":"ok","right_key":"d"},{"topic_id":21,"topic_type_id":1,"topic_content":"的点点滴滴","topic_picture":null,"topic_score":null,"a_option":"ok","b_option":"wrong1","c_option":"wrong1","d_option":"wrong1","right_key":"a"},{"topic_id":15,"topic_type_id":1,"topic_content":"额我大所多撒多撒大所","topic_picture":null,"topic_score":null,"a_option":"ok","b_option":"wrong1","c_option":"wrong1","d_option":"wrong1","right_key":"a"}]
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
         * topic_id : 22
         * topic_type_id : 1
         * topic_content : 大萨达撒大所
         * topic_picture : null
         * topic_score : null
         * a_option : wrong1
         * b_option : wrong1
         * c_option : ok
         * d_option : wrong1
         * right_key : c
         */

        private int topic_id;
        private int topic_type_id;
        private String topic_content;
        private Object topic_picture;
        private Object topic_score;
        private String a_option;
        private String b_option;
        private String c_option;
        private String d_option;
        private String right_key;
        private String describe;
        private Integer papers_id;

        public Integer getPapers_id() {
            return papers_id;
        }

        public void setPapers_id(Integer papers_id) {
            this.papers_id = papers_id;
        }

        public String getDescribe() {
            return describe;
        }

        public void setDescribe(String describe) {
            this.describe = describe;
        }

        public int getTopic_id() {
            return topic_id;
        }

        public void setTopic_id(int topic_id) {
            this.topic_id = topic_id;
        }

        public int getTopic_type_id() {
            return topic_type_id;
        }

        public void setTopic_type_id(int topic_type_id) {
            this.topic_type_id = topic_type_id;
        }

        public String getTopic_content() {
            return topic_content;
        }

        public void setTopic_content(String topic_content) {
            this.topic_content = topic_content;
        }

        public Object getTopic_picture() {
            return topic_picture;
        }

        public void setTopic_picture(Object topic_picture) {
            this.topic_picture = topic_picture;
        }

        public Object getTopic_score() {
            return topic_score;
        }

        public void setTopic_score(Object topic_score) {
            this.topic_score = topic_score;
        }

        public String getA_option() {
            return a_option;
        }

        public void setA_option(String a_option) {
            this.a_option = a_option;
        }

        public String getB_option() {
            return b_option;
        }

        public void setB_option(String b_option) {
            this.b_option = b_option;
        }

        public String getC_option() {
            return c_option;
        }

        public void setC_option(String c_option) {
            this.c_option = c_option;
        }

        public String getD_option() {
            return d_option;
        }

        public void setD_option(String d_option) {
            this.d_option = d_option;
        }

        public String getRight_key() {
            return right_key;
        }

        public void setRight_key(String right_key) {
            this.right_key = right_key;
        }
    }
}
