package com.example.administrator.lawapp.bean;

import java.util.List;

/**
 * Created by 吴青晓 on 2019/2/20
 * Describe
 */
public class LawBean {

    /**
     * success : true
     * data : [{"law_id":2,"law_name":"新婚姻法司法解释（三）全文","law_content":null,"law_description":null,"law_category_id":10},{"law_id":3,"law_name":"新婚姻法司法解释（二）全文","law_content":null,"law_description":null,"law_category_id":10},{"law_id":4,"law_name":"新婚姻法司法解释（一）全文","law_content":null,"law_description":null,"law_category_id":10}]
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
         * law_id : 2
         * law_name : 新婚姻法司法解释（三）全文
         * law_content : null
         * law_description : null
         * law_category_id : 10
         */

        private int law_id;
        private String law_name;
        private Object law_content;
        private Object law_description;
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

        public Object getLaw_content() {
            return law_content;
        }

        public void setLaw_content(Object law_content) {
            this.law_content = law_content;
        }

        public Object getLaw_description() {
            return law_description;
        }

        public void setLaw_description(Object law_description) {
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
