package com.example.administrator.lawapp.bean;

/**
 * Created by 吴青晓 on 2019/2/24
 * Describe
 */
public class AuditoriumBean {

    /**
     * success : true
     * data : {"auditorium_id":4,"auditorium_title":"欠钱不还还打人 执行法官巧解决","auditorium_content":"乐清市法院大荆法庭通过布控，成功将一名失信被执行人方某抓获。","auditorium_video":"/mp3/money.mp3","auditorium_picture":"/images/money.png","auditorium_time":"2019-02-13 21:48:54","watch_count":1,"praise_count":1}
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
         * auditorium_id : 4
         * auditorium_title : 欠钱不还还打人 执行法官巧解决
         * auditorium_content : 乐清市法院大荆法庭通过布控，成功将一名失信被执行人方某抓获。
         * auditorium_video : /mp3/money.mp3
         * auditorium_picture : /images/money.png
         * auditorium_time : 2019-02-13 21:48:54
         * watch_count : 1
         * praise_count : 1
         */

        private int auditorium_id;
        private String auditorium_title;
        private String auditorium_content;
        private String auditorium_video;
        private String auditorium_picture;
        private String auditorium_time;
        private int watch_count;
        private int praise_count;

        public int getAuditorium_id() {
            return auditorium_id;
        }

        public void setAuditorium_id(int auditorium_id) {
            this.auditorium_id = auditorium_id;
        }

        public String getAuditorium_title() {
            return auditorium_title;
        }

        public void setAuditorium_title(String auditorium_title) {
            this.auditorium_title = auditorium_title;
        }

        public String getAuditorium_content() {
            return auditorium_content;
        }

        public void setAuditorium_content(String auditorium_content) {
            this.auditorium_content = auditorium_content;
        }

        public String getAuditorium_video() {
            return auditorium_video;
        }

        public void setAuditorium_video(String auditorium_video) {
            this.auditorium_video = auditorium_video;
        }

        public String getAuditorium_picture() {
            return auditorium_picture;
        }

        public void setAuditorium_picture(String auditorium_picture) {
            this.auditorium_picture = auditorium_picture;
        }

        public String getAuditorium_time() {
            return auditorium_time;
        }

        public void setAuditorium_time(String auditorium_time) {
            this.auditorium_time = auditorium_time;
        }

        public int getWatch_count() {
            return watch_count;
        }

        public void setWatch_count(int watch_count) {
            this.watch_count = watch_count;
        }

        public int getPraise_count() {
            return praise_count;
        }

        public void setPraise_count(int praise_count) {
            this.praise_count = praise_count;
        }
    }
}
