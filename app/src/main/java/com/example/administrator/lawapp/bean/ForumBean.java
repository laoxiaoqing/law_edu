package com.example.administrator.lawapp.bean;

import java.util.List;

/**
 * Created by 吴青晓 on 2019/3/6
 * Describe
 */
public class ForumBean {

    /**
     * success : true
     * data : [{"forum_id":1,"user_id":1,"forum_date":"2019-03-06 11:01:47","forum_content":"我是一位大哥，怎么样你看看","reply_num":0,"forum_key":0,"forum_picture_1":"http://192.168.43.47:8088/upload/images/money.png","forum_picture_2":"http://192.168.43.47:8088/upload/images/money.png","forum_picture_3":"http://192.168.43.47:8088/upload/images/money.png","forum_picture_4":"http://192.168.43.47:8088/upload/images/money.png","forum_picture_5":"http://192.168.43.47:8088/upload/images/money.png","forum_picture_6":"http://192.168.43.47:8088/upload/images/money.png","forum_picture_7":"http://192.168.43.47:8088/upload/images/money.png","forum_picture_8":"http://192.168.43.47:8088/upload/images/money.png","forum_picture_9":"http://192.168.43.47:8088/upload/images/money.png","forum_type_id":1,"user_name":"吴青晓","username":"wqx","commentList":[{"comment_id":1,"topic_id":1,"topic_type":1,"content":"我是一条评论","comment_date":"2019-03-06 15:11:43","comment_user_id":1,"user_name":"吴青晓","to_user_id":1,"user_name1":"吴青晓"},{"comment_id":7,"topic_id":1,"topic_type":1,"content":"wqewqeqweqrfew","comment_date":"2019-03-06 19:52:39","comment_user_id":1,"user_name":"吴青晓","to_user_id":6,"user_name1":"张长城"},{"comment_id":2,"topic_id":1,"topic_type":1,"content":"我评论了第一条","comment_date":"2019-03-06 15:11:46","comment_user_id":6,"user_name":"张长城","to_user_id":1,"user_name1":"吴青晓"},{"comment_id":6,"topic_id":1,"topic_type":1,"content":"dadsfdfdf","comment_date":"2019-03-06 19:52:01","comment_user_id":6,"user_name":"张长城","to_user_id":1,"user_name1":"吴青晓"}]},{"forum_id":2,"user_id":1,"forum_date":"2019-03-06 11:02:36","forum_content":"我是一位大哥阿斯达斯大所大啊看","reply_num":0,"forum_key":0,"forum_picture_1":null,"forum_picture_2":null,"forum_picture_3":null,"forum_picture_4":null,"forum_picture_5":null,"forum_picture_6":null,"forum_picture_7":null,"forum_picture_8":null,"forum_picture_9":null,"forum_type_id":null,"user_name":"吴青晓","username":"wqx","commentList":[{"comment_id":3,"topic_id":2,"topic_type":1,"content":"我评论了第二条","comment_date":"2019-03-06 15:11:50","comment_user_id":1,"user_name":"吴青晓","to_user_id":null,"user_name1":null},{"comment_id":4,"topic_id":2,"topic_type":1,"content":"我是第二条评论","comment_date":"2019-03-06 15:11:53","comment_user_id":1,"user_name":"吴青晓","to_user_id":null,"user_name1":null}]},{"forum_id":3,"user_id":1,"forum_date":"2019-03-06 11:03:25","forum_content":"我是萨克雷达萨罗开放男","reply_num":0,"forum_key":0,"forum_picture_1":null,"forum_picture_2":null,"forum_picture_3":null,"forum_picture_4":null,"forum_picture_5":null,"forum_picture_6":null,"forum_picture_7":null,"forum_picture_8":null,"forum_picture_9":null,"forum_type_id":null,"user_name":"吴青晓","username":"wqx","commentList":[{"comment_id":5,"topic_id":3,"topic_type":1,"content":"wdwe","comment_date":"2019-03-06 19:51:35","comment_user_id":6,"user_name":"张长城","to_user_id":1,"user_name1":"吴青晓"}]}]
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
         * forum_id : 1
         * user_id : 1
         * forum_date : 2019-03-06 11:01:47
         * forum_content : 我是一位大哥，怎么样你看看
         * reply_num : 0
         * forum_key : 0
         * forum_picture_1 : http://192.168.43.47:8088/upload/images/money.png
         * forum_picture_2 : http://192.168.43.47:8088/upload/images/money.png
         * forum_picture_3 : http://192.168.43.47:8088/upload/images/money.png
         * forum_picture_4 : http://192.168.43.47:8088/upload/images/money.png
         * forum_picture_5 : http://192.168.43.47:8088/upload/images/money.png
         * forum_picture_6 : http://192.168.43.47:8088/upload/images/money.png
         * forum_picture_7 : http://192.168.43.47:8088/upload/images/money.png
         * forum_picture_8 : http://192.168.43.47:8088/upload/images/money.png
         * forum_picture_9 : http://192.168.43.47:8088/upload/images/money.png
         * forum_type_id : 1
         * user_name : 吴青晓
         * username : wqx
         * commentList : [{"comment_id":1,"topic_id":1,"topic_type":1,"content":"我是一条评论","comment_date":"2019-03-06 15:11:43","comment_user_id":1,"user_name":"吴青晓","to_user_id":1,"user_name1":"吴青晓"},{"comment_id":7,"topic_id":1,"topic_type":1,"content":"wqewqeqweqrfew","comment_date":"2019-03-06 19:52:39","comment_user_id":1,"user_name":"吴青晓","to_user_id":6,"user_name1":"张长城"},{"comment_id":2,"topic_id":1,"topic_type":1,"content":"我评论了第一条","comment_date":"2019-03-06 15:11:46","comment_user_id":6,"user_name":"张长城","to_user_id":1,"user_name1":"吴青晓"},{"comment_id":6,"topic_id":1,"topic_type":1,"content":"dadsfdfdf","comment_date":"2019-03-06 19:52:01","comment_user_id":6,"user_name":"张长城","to_user_id":1,"user_name1":"吴青晓"}]
         */

        private int forum_id;
        private int user_id;
        private String forum_date;
        private String forum_content;
        private int reply_num;
        private int forum_key;
        private String forum_picture_1;
        private String forum_picture_2;
        private String forum_picture_3;
        private String forum_picture_4;
        private String forum_picture_5;
        private String forum_picture_6;
        private String forum_picture_7;
        private String forum_picture_8;
        private String forum_picture_9;
        private int forum_type_id;
        private String user_name;
        private String username;
        private String user_head;
        private List<CommentListBean> commentList;

        public String getUser_head() {
            return user_head;
        }

        public void setUser_head(String user_head) {
            this.user_head = user_head;
        }

        public int getForum_id() {
            return forum_id;
        }

        public void setForum_id(int forum_id) {
            this.forum_id = forum_id;
        }

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }

        public String getForum_date() {
            return forum_date;
        }

        public void setForum_date(String forum_date) {
            this.forum_date = forum_date;
        }

        public String getForum_content() {
            return forum_content;
        }

        public void setForum_content(String forum_content) {
            this.forum_content = forum_content;
        }

        public int getReply_num() {
            return reply_num;
        }

        public void setReply_num(int reply_num) {
            this.reply_num = reply_num;
        }

        public int getForum_key() {
            return forum_key;
        }

        public void setForum_key(int forum_key) {
            this.forum_key = forum_key;
        }

        public String getForum_picture_1() {
            return forum_picture_1;
        }

        public void setForum_picture_1(String forum_picture_1) {
            this.forum_picture_1 = forum_picture_1;
        }

        public String getForum_picture_2() {
            return forum_picture_2;
        }

        public void setForum_picture_2(String forum_picture_2) {
            this.forum_picture_2 = forum_picture_2;
        }

        public String getForum_picture_3() {
            return forum_picture_3;
        }

        public void setForum_picture_3(String forum_picture_3) {
            this.forum_picture_3 = forum_picture_3;
        }

        public String getForum_picture_4() {
            return forum_picture_4;
        }

        public void setForum_picture_4(String forum_picture_4) {
            this.forum_picture_4 = forum_picture_4;
        }

        public String getForum_picture_5() {
            return forum_picture_5;
        }

        public void setForum_picture_5(String forum_picture_5) {
            this.forum_picture_5 = forum_picture_5;
        }

        public String getForum_picture_6() {
            return forum_picture_6;
        }

        public void setForum_picture_6(String forum_picture_6) {
            this.forum_picture_6 = forum_picture_6;
        }

        public String getForum_picture_7() {
            return forum_picture_7;
        }

        public void setForum_picture_7(String forum_picture_7) {
            this.forum_picture_7 = forum_picture_7;
        }

        public String getForum_picture_8() {
            return forum_picture_8;
        }

        public void setForum_picture_8(String forum_picture_8) {
            this.forum_picture_8 = forum_picture_8;
        }

        public String getForum_picture_9() {
            return forum_picture_9;
        }

        public void setForum_picture_9(String forum_picture_9) {
            this.forum_picture_9 = forum_picture_9;
        }

        public int getForum_type_id() {
            return forum_type_id;
        }

        public void setForum_type_id(int forum_type_id) {
            this.forum_type_id = forum_type_id;
        }

        public String getUser_name() {
            return user_name;
        }

        public void setUser_name(String user_name) {
            this.user_name = user_name;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public List<CommentListBean> getCommentList() {
            return commentList;
        }

        public void setCommentList(List<CommentListBean> commentList) {
            this.commentList = commentList;
        }

        public static class CommentListBean {
            /**
             * comment_id : 1
             * topic_id : 1
             * topic_type : 1
             * content : 我是一条评论
             * comment_date : 2019-03-06 15:11:43
             * comment_user_id : 1
             * user_name : 吴青晓
             * to_user_id : 1
             * user_name1 : 吴青晓
             */

            private int comment_id;
            private int topic_id;
            private int topic_type;
            private String content;
            private String comment_date;
            private int comment_user_id;
            private String user_name;
            private int to_user_id;
            private String user_name1;

            public int getComment_id() {
                return comment_id;
            }

            public void setComment_id(int comment_id) {
                this.comment_id = comment_id;
            }

            public int getTopic_id() {
                return topic_id;
            }

            public void setTopic_id(int topic_id) {
                this.topic_id = topic_id;
            }

            public int getTopic_type() {
                return topic_type;
            }

            public void setTopic_type(int topic_type) {
                this.topic_type = topic_type;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public String getComment_date() {
                return comment_date;
            }

            public void setComment_date(String comment_date) {
                this.comment_date = comment_date;
            }

            public int getComment_user_id() {
                return comment_user_id;
            }

            public void setComment_user_id(int comment_user_id) {
                this.comment_user_id = comment_user_id;
            }

            public String getUser_name() {
                return user_name;
            }

            public void setUser_name(String user_name) {
                this.user_name = user_name;
            }

            public int getTo_user_id() {
                return to_user_id;
            }

            public void setTo_user_id(int to_user_id) {
                this.to_user_id = to_user_id;
            }

            public String getUser_name1() {
                return user_name1;
            }

            public void setUser_name1(String user_name1) {
                this.user_name1 = user_name1;
            }
        }
    }
}
