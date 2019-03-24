package com.example.administrator.lawapp.bean;

/**
 * Created by 吴青晓 on 2019/2/24
 * Describe
 */
public class VideoBean {

    /**
     * success : true
     * data : {"video_id":1,"video_title":"快递的完成状态，到底由谁来确定？","video_content":null,"video_url":"/mp4/kuaidi.mp4","video_picture":"/images/kuaidi.png","add_time":"2019-02-21 14:21:06","watch_count":1,"praise_count":null,"video_type_id":null,"sort_order":1}
     */

    private boolean success;
    private DataBean data;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * video_id : 1
         * video_title : 快递的完成状态，到底由谁来确定？
         * video_content : null
         * video_url : /mp4/kuaidi.mp4
         * video_picture : /images/kuaidi.png
         * add_time : 2019-02-21 14:21:06
         * watch_count : 1
         * praise_count : null
         * video_type_id : null
         * sort_order : 1
         */

        private int video_id;
        private String video_title;
        private String video_content;
        private String video_url;
        private String video_picture;
        private String add_time;
        private int watch_count;
        private Object praise_count;
        private Object video_type_id;
        private int sort_order;

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

        public String getVideo_content() {
            return video_content;
        }

        public void setVideo_content(String video_content) {
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

        public int getWatch_count() {
            return watch_count;
        }

        public void setWatch_count(int watch_count) {
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

        public int getSort_order() {
            return sort_order;
        }

        public void setSort_order(int sort_order) {
            this.sort_order = sort_order;
        }
    }
}
