package com.example.administrator.lawapp.bean;

/**
 * Created by 吴青晓 on 2019/2/24
 * Describe
 */
public class LawBean {

    /**
     * success : true
     * data : {"law":{"law_id":2,"law_name":"新婚姻法司法解释（三）全文","law_content":"新婚姻法司法解释（三）全文新婚姻法司法解释（三）全文新婚姻法司法解释（三）全文","law_description":"123","law_category_id":10},"twoName":"婚姻法","oneName":"民事类"}
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
         * law : {"law_id":2,"law_name":"新婚姻法司法解释（三）全文","law_content":"新婚姻法司法解释（三）全文新婚姻法司法解释（三）全文新婚姻法司法解释（三）全文","law_description":"123","law_category_id":10}
         * twoName : 婚姻法
         * oneName : 民事类
         */

        private LawM law;
        private String twoName;
        private String oneName;

        public LawM getLaw() {
            return law;
        }

        public void setLaw(LawM law) {
            this.law = law;
        }

        public String getTwoName() {
            return twoName;
        }

        public void setTwoName(String twoName) {
            this.twoName = twoName;
        }

        public String getOneName() {
            return oneName;
        }

        public void setOneName(String oneName) {
            this.oneName = oneName;
        }

        public static class LawM {
            /**
             * law_id : 2
             * law_name : 新婚姻法司法解释（三）全文
             * law_content : 新婚姻法司法解释（三）全文新婚姻法司法解释（三）全文新婚姻法司法解释（三）全文
             * law_description : 123
             * law_category_id : 10
             */

            private int law_id;
            private String law_name;
            private String law_content;
            private String law_description;
            private int law_category_id;

            public int getLaw_id() {
                return law_id;
            }

            public void setLaw_id(int law_id) {
                this.law_id = law_id;
            }

            public String getLaw_name() {
                return law_name;
            }

            public void setLaw_name(String law_name) {
                this.law_name = law_name;
            }

            public String getLaw_content() {
                return law_content;
            }

            public void setLaw_content(String law_content) {
                this.law_content = law_content;
            }

            public String getLaw_description() {
                return law_description;
            }

            public void setLaw_description(String law_description) {
                this.law_description = law_description;
            }

            public int getLaw_category_id() {
                return law_category_id;
            }

            public void setLaw_category_id(int law_category_id) {
                this.law_category_id = law_category_id;
            }
        }
    }
}
