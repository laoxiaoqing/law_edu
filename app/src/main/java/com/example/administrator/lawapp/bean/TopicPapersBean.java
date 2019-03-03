package com.example.administrator.lawapp.bean;

import java.util.List;

/**
 * Created by 吴青晓 on 2019/3/1
 * Describe:http://localhost:8088/api/v1/papers/86     PapePager.java
 */
public class TopicPapersBean {

    /**
     * success : true
     * data : [{"papers_topic_id":567,"topic_id":19,"papers_id":86,"user_key":"d","right_key":"d","topic":{"topic_id":19,"topic_type_id":1,"topic_content":"发过火规范化","topic_picture":null,"topic_score":10,"a_option":"wrong1","b_option":"wrong1","c_option":"wrong1","d_option":"ok","right_key":"d","describe":null,"papers_id":86}},{"papers_topic_id":566,"topic_id":27,"papers_id":86,"user_key":"d","right_key":"d","topic":{"topic_id":27,"topic_type_id":1,"topic_content":"DASSAD","topic_picture":null,"topic_score":10,"a_option":"wrong1","b_option":"wrong1","c_option":"wrong1","d_option":"ok","right_key":"d","describe":null,"papers_id":86}},{"papers_topic_id":565,"topic_id":21,"papers_id":86,"user_key":"a","right_key":"a","topic":{"topic_id":21,"topic_type_id":1,"topic_content":"的点点滴滴","topic_picture":null,"topic_score":10,"a_option":"ok","b_option":"wrong1","c_option":"wrong1","d_option":"wrong1","right_key":"a","describe":null,"papers_id":86}},{"papers_topic_id":564,"topic_id":14,"papers_id":86,"user_key":"b","right_key":"c","topic":{"topic_id":14,"topic_type_id":1,"topic_content":"气味儿二二二二二二二二气味儿二二二二二二","topic_picture":null,"topic_score":10,"a_option":"wrong1","b_option":"ok","c_option":"wrong1","d_option":"wrong1","right_key":"c","describe":"正确答案是c","papers_id":86}},{"papers_topic_id":563,"topic_id":23,"papers_id":86,"user_key":"d","right_key":"d","topic":{"topic_id":23,"topic_type_id":1,"topic_content":"ZVXV","topic_picture":null,"topic_score":10,"a_option":"wrong1","b_option":"wrong1","c_option":"wrong1","d_option":"ok","right_key":"d","describe":null,"papers_id":86}},{"papers_topic_id":562,"topic_id":17,"papers_id":86,"user_key":"b","right_key":"a","topic":{"topic_id":17,"topic_type_id":1,"topic_content":"梵蒂冈和低功耗","topic_picture":null,"topic_score":10,"a_option":"ok","b_option":"wrong1","c_option":"wrong1","d_option":"wrong1","right_key":"a","describe":null,"papers_id":86}},{"papers_topic_id":561,"topic_id":6,"papers_id":86,"user_key":"c","right_key":"c","topic":{"topic_id":6,"topic_type_id":1,"topic_content":"我是题目","topic_picture":null,"topic_score":10,"a_option":"wrong1","b_option":"wrong1","c_option":"ok","d_option":"wrong1","right_key":"c","describe":"正确答案是c","papers_id":86}},{"papers_topic_id":560,"topic_id":20,"papers_id":86,"user_key":"d","right_key":"d","topic":{"topic_id":20,"topic_type_id":1,"topic_content":"韩国锦湖纠纷","topic_picture":null,"topic_score":10,"a_option":"wrong1","b_option":"wrong1","c_option":"wrong1","d_option":"ok","right_key":"d","describe":null,"papers_id":86}},{"papers_topic_id":559,"topic_id":3,"papers_id":86,"user_key":"a","right_key":"a","topic":{"topic_id":3,"topic_type_id":1,"topic_content":"高校网络安全法律意识教育系","topic_picture":null,"topic_score":10,"a_option":"ok","b_option":"wrong1","c_option":"wrong1","d_option":"wrong1","right_key":"a","describe":"正确答案是a","papers_id":86}},{"papers_topic_id":558,"topic_id":4,"papers_id":86,"user_key":"a","right_key":"a","topic":{"topic_id":4,"topic_type_id":1,"topic_content":"高woshi题目","topic_picture":null,"topic_score":10,"a_option":"ok","b_option":"wrong1","c_option":"wrong1","d_option":"wrong1","right_key":"a","describe":"正确答案是a","papers_id":86}}]
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
         * papers_topic_id : 567
         * topic_id : 19
         * papers_id : 86
         * user_key : d
         * right_key : d
         * topic : {"topic_id":19,"topic_type_id":1,"topic_content":"发过火规范化","topic_picture":null,"topic_score":10,"a_option":"wrong1","b_option":"wrong1","c_option":"wrong1","d_option":"ok","right_key":"d","describe":null,"papers_id":86}
         */

        private int papers_topic_id;
        private int topic_id;
        private int papers_id;
        private String user_key;
        private String right_key;
        private TopicBean topic;

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

        public TopicBean getTopic() {
            return topic;
        }

        public void setTopic(TopicBean topic) {
            this.topic = topic;
        }

        public static class TopicBean {
            /**
             * topic_id : 19
             * topic_type_id : 1
             * topic_content : 发过火规范化
             * topic_picture : null
             * topic_score : 10
             * a_option : wrong1
             * b_option : wrong1
             * c_option : wrong1
             * d_option : ok
             * right_key : d
             * describe : null
             * papers_id : 86
             */

            private int topic_id;
            private int topic_type_id;
            private String topic_content;
            private String topic_picture;
            private int topic_score;
            private String a_option;
            private String b_option;
            private String c_option;
            private String d_option;
            private String right_key;
            private String describe;
            private int papers_id;

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

            public void setTopic_picture(String topic_picture) {
                this.topic_picture = topic_picture;
            }

            public int getTopic_score() {
                return topic_score;
            }

            public void setTopic_score(int topic_score) {
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

            public Object getDescribe() {
                return describe;
            }

            public void setDescribe(String describe) {
                this.describe = describe;
            }

            public int getPapers_id() {
                return papers_id;
            }

            public void setPapers_id(int papers_id) {
                this.papers_id = papers_id;
            }
        }
    }
}
