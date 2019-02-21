package com.example.administrator.lawapp.bean;

import java.util.List;

/**
 * Created by 吴青晓 on 2019/2/21
 * Describe:轮播图的数据
 */
public class HomePagerBean {

    /**
     * success : true
     * data : {"auditorium":{"auditorium_id":4,"auditorium_title":"欠钱不还还打人 执行法官巧解决","auditorium_content":"乐清市法院大荆法庭通过布控，成功将一名失信被执行人方某抓获。","auditorium_video":"http://mcdn.zhanyaa.com/svideo/2019/02/b22518db-1e58-4dc0-8fe7-be6d85ac68e9.mp3","auditorium_picture":"http://pic.zhanyaa.com/pic/2019/02/22e3ec2e-d105-4953-a960-37d5347429b6.jpg@!750x440","auditorium_time":"2019-02-13 21:48:54","watch_count":1,"praise_count":1},"banner":[{"banner_id":1,"banner_title":"这是第一张轮播图","banner_url":"www.baidu.com","banner_image_url":"/static/images/image1.png","add_time":"2019-02-21 11:03:40","sort_order":1,"banner_content":"sdasdsadasdasdsa"},{"banner_id":2,"banner_title":"这是第二张轮播图","banner_url":"www.jd.com","banner_image_url":"/static/images/lunbo1.jpg","add_time":"2019-02-20 11:05:02","sort_order":2,"banner_content":"aaaaaaaaaaaa"}],"video":{"video_id":1,"video_title":"快递的完成状态，到底由谁来确定？","video_content":null,"video_url":"http://mcdn.zhanyaa.com/svideo/2019/01/24b0907f-a5a4-4550-906b-1c852f3f5ef7.mp4","video_picture":"http://pic.zhanyaa.com/pic/2019/01/73f26291-ba09-4070-b509-d4316c7c51d8.png","add_time":"2019-02-21 14:21:06","watch_count":1,"praise_count":null,"video_type_id":null,"sort_order":1}}
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
         * auditorium : {"auditorium_id":4,"auditorium_title":"欠钱不还还打人 执行法官巧解决","auditorium_content":"乐清市法院大荆法庭通过布控，成功将一名失信被执行人方某抓获。","auditorium_video":"http://mcdn.zhanyaa.com/svideo/2019/02/b22518db-1e58-4dc0-8fe7-be6d85ac68e9.mp3","auditorium_picture":"http://pic.zhanyaa.com/pic/2019/02/22e3ec2e-d105-4953-a960-37d5347429b6.jpg@!750x440","auditorium_time":"2019-02-13 21:48:54","watch_count":1,"praise_count":1}
         * banner : [{"banner_id":1,"banner_title":"这是第一张轮播图","banner_url":"www.baidu.com","banner_image_url":"/static/images/image1.png","add_time":"2019-02-21 11:03:40","sort_order":1,"banner_content":"sdasdsadasdasdsa"},{"banner_id":2,"banner_title":"这是第二张轮播图","banner_url":"www.jd.com","banner_image_url":"/static/images/lunbo1.jpg","add_time":"2019-02-20 11:05:02","sort_order":2,"banner_content":"aaaaaaaaaaaa"}]
         * video : {"video_id":1,"video_title":"快递的完成状态，到底由谁来确定？","video_content":null,"video_url":"http://mcdn.zhanyaa.com/svideo/2019/01/24b0907f-a5a4-4550-906b-1c852f3f5ef7.mp4","video_picture":"http://pic.zhanyaa.com/pic/2019/01/73f26291-ba09-4070-b509-d4316c7c51d8.png","add_time":"2019-02-21 14:21:06","watch_count":1,"praise_count":null,"video_type_id":null,"sort_order":1}
         */

        private AuditoriumBean auditorium;
        private VideoBean video;
        private List<BannerBean> banner;

        public AuditoriumBean getAuditorium() {
            return auditorium;
        }

        public void setAuditorium(AuditoriumBean auditorium) {
            this.auditorium = auditorium;
        }

        public VideoBean getVideo() {
            return video;
        }

        public void setVideo(VideoBean video) {
            this.video = video;
        }

        public List<BannerBean> getBanner() {
            return banner;
        }

        public void setBanner(List<BannerBean> banner) {
            this.banner = banner;
        }

        public static class AuditoriumBean {
            /**
             * auditorium_id : 4
             * auditorium_title : 欠钱不还还打人 执行法官巧解决
             * auditorium_content : 乐清市法院大荆法庭通过布控，成功将一名失信被执行人方某抓获。
             * auditorium_video : http://mcdn.zhanyaa.com/svideo/2019/02/b22518db-1e58-4dc0-8fe7-be6d85ac68e9.mp3
             * auditorium_picture : http://pic.zhanyaa.com/pic/2019/02/22e3ec2e-d105-4953-a960-37d5347429b6.jpg@!750x440
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

        public static class VideoBean {
            /**
             * video_id : 1
             * video_title : 快递的完成状态，到底由谁来确定？
             * video_content : null
             * video_url : http://mcdn.zhanyaa.com/svideo/2019/01/24b0907f-a5a4-4550-906b-1c852f3f5ef7.mp4
             * video_picture : http://pic.zhanyaa.com/pic/2019/01/73f26291-ba09-4070-b509-d4316c7c51d8.png
             * add_time : 2019-02-21 14:21:06
             * watch_count : 1
             * praise_count : null
             * video_type_id : null
             * sort_order : 1
             */

            private int video_id;
            private String video_title;
            private Object video_content;
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

        public static class BannerBean {
            /**
             * banner_id : 1
             * banner_title : 这是第一张轮播图
             * banner_url : www.baidu.com
             * banner_image_url : /static/images/image1.png
             * add_time : 2019-02-21 11:03:40
             * sort_order : 1
             * banner_content : sdasdsadasdasdsa
             */

            private int banner_id;
            private String banner_title;
            private String banner_url;
            private String banner_image_url;
            private String add_time;
            private int sort_order;
            private String banner_content;

            public int getBanner_id() {
                return banner_id;
            }

            public void setBanner_id(int banner_id) {
                this.banner_id = banner_id;
            }

            public String getBanner_title() {
                return banner_title;
            }

            public void setBanner_title(String banner_title) {
                this.banner_title = banner_title;
            }

            public String getBanner_url() {
                return banner_url;
            }

            public void setBanner_url(String banner_url) {
                this.banner_url = banner_url;
            }

            public String getBanner_image_url() {
                return banner_image_url;
            }

            public void setBanner_image_url(String banner_image_url) {
                this.banner_image_url = banner_image_url;
            }

            public String getAdd_time() {
                return add_time;
            }

            public void setAdd_time(String add_time) {
                this.add_time = add_time;
            }

            public int getSort_order() {
                return sort_order;
            }

            public void setSort_order(int sort_order) {
                this.sort_order = sort_order;
            }

            public String getBanner_content() {
                return banner_content;
            }

            public void setBanner_content(String banner_content) {
                this.banner_content = banner_content;
            }
        }
    }
}
