package com.example.administrator.lawapp.bean;

import java.util.List;

/**
 * Created by 吴青晓 on 2019/2/22
 * Describe
 */
public class VideoMoreBean {


    /**
     * success : true
     * data : [{"video_id":13,"video_title":"当事人涉嫌犯了哪些法？","video_content":null,"video_url":"/mp4/naxie.mp4","video_picture":"/images/naxie.png","add_time":"2019-02-23 14:56:31","watch_count":null,"praise_count":null,"video_type_id":null,"sort_order":null},{"video_id":12,"video_title":"揭开非法集资诱人面纱，守护你的钱袋子","video_content":null,"video_url":"/mp4/jiekai.mp4","video_picture":"/images/jiekai.png","add_time":"2019-02-23 14:56:28","watch_count":null,"praise_count":null,"video_type_id":null,"sort_order":null},{"video_id":11,"video_title":"公积金有啥用怎么用","video_content":null,"video_url":"/mp4/gongjijin.mp4","video_picture":"/images/gongjijin.png","add_time":"2019-02-23 14:49:15","watch_count":null,"praise_count":null,"video_type_id":null,"sort_order":null},{"video_id":10,"video_title":"邻居装修太难抗","video_content":null,"video_url":"/mp4/zhuangxiu.mp4","video_picture":"/images/zhuangxiu.png","add_time":"2019-02-23 14:48:09","watch_count":null,"praise_count":null,"video_type_id":null,"sort_order":null},{"video_id":9,"video_title":"人民法院的裁定结果不可避免","video_content":null,"video_url":"/mp4/renm.mp4","video_picture":"/images/renmin.png","add_time":"2019-02-23 14:47:07","watch_count":null,"praise_count":null,"video_type_id":null,"sort_order":null},{"video_id":8,"video_title":"防治职业病，爱护劳动者","video_content":null,"video_url":"/mp4/laodong.mp4","video_picture":"/images/laodong.png","add_time":"2019-02-23 14:45:56","watch_count":null,"praise_count":null,"video_type_id":null,"sort_order":null},{"video_id":7,"video_title":"什么是犯罪中止","video_content":null,"video_url":"/mp4/fanzui.mp4","video_picture":"/images/fanzui.png","add_time":"2019-02-23 14:45:08","watch_count":null,"praise_count":null,"video_type_id":null,"sort_order":null},{"video_id":6,"video_title":"\u201c米涂\u201d遇邪知返记","video_content":null,"video_url":"/mp4/mitu.mp4","video_picture":"/images/mitu.png","add_time":"2019-02-23 14:43:59","watch_count":null,"praise_count":null,"video_type_id":null,"sort_order":null}]
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
         * video_id : 13
         * video_title : 当事人涉嫌犯了哪些法？
         * video_content : null
         * video_url : /mp4/naxie.mp4
         * video_picture : /images/naxie.png
         * add_time : 2019-02-23 14:56:31
         * watch_count : null
         * praise_count : null
         * video_type_id : null
         * sort_order : null
         */

        private int video_id;
        private String video_title;
        private Object video_content;
        private String video_url;
        private String video_picture;
        private String add_time;
        private Object watch_count;
        private Object praise_count;
        private Object video_type_id;
        private Object sort_order;

        public int getVideo_id() {
            return video_id;
        }

        public void setVideo_id(int video_id) {
            this.video_id = video_id;
        }

        public String getVideo_title() {
            return video_title;
        }

        public void setVideo_title(String video_title) {
            this.video_title = video_title;
        }

        public Object getVideo_content() {
            return video_content;
        }

        public void setVideo_content(Object video_content) {
            this.video_content = video_content;
        }

        public String getVideo_url() {
            return video_url;
        }

        public void setVideo_url(String video_url) {
            this.video_url = video_url;
        }

        public String getVideo_picture() {
            return video_picture;
        }

        public void setVideo_picture(String video_picture) {
            this.video_picture = video_picture;
        }

        public String getAdd_time() {
            return add_time;
        }

        public void setAdd_time(String add_time) {
            this.add_time = add_time;
        }

        public Object getWatch_count() {
            return watch_count;
        }

        public void setWatch_count(Object watch_count) {
            this.watch_count = watch_count;
        }

        public Object getPraise_count() {
            return praise_count;
        }

        public void setPraise_count(Object praise_count) {
            this.praise_count = praise_count;
        }

        public Object getVideo_type_id() {
            return video_type_id;
        }

        public void setVideo_type_id(Object video_type_id) {
            this.video_type_id = video_type_id;
        }

        public Object getSort_order() {
            return sort_order;
        }

        public void setSort_order(Object sort_order) {
            this.sort_order = sort_order;
        }
    }
}
