package com.example.administrator.lawapp.bean;

import java.util.List;

/**
 * Created by 吴青晓 on 2019/2/22
 * Describe
 */
public class CasesMoreBean {

    /**
     * success : true
     * data : [{"case_id":1,"case_name":"【七五普法】劳动者伪造入职材料，劳动合同无效？","case_date":"2019-02-21 20:55:16","case_content":"123","case_type_id":1,"case_pircture":"/images/qiwu.png"},{"case_id":2,"case_name":"【骗局解密】女子遭遇投资陷阱！\u201c沃尔克\u201d涉嫌传销被警方立案调查","case_date":"2019-02-21 20:55:20","case_content":"123","case_type_id":2,"case_pircture":"/images/nvzi.png"},{"case_id":3,"case_name":"【村民日报】太危险！熊孩子往引擎盖内扔鞭炮引燃轿车 路人忙打电话求助","case_date":"2019-02-21 20:55:22","case_content":"123","case_type_id":3,"case_pircture":"/images/weixian.png"}]
     */

    private boolean success;
    private List<CasesBean> data;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public List<CasesBean> getData() {
        return data;
    }

    public void setData(List<CasesBean> data) {
        this.data = data;
    }

    public static class CasesBean {
        /**
         * case_id : 1
         * case_name : 【七五普法】劳动者伪造入职材料，劳动合同无效？
         * case_date : 2019-02-21 20:55:16
         * case_content : 123
         * case_type_id : 1
         * case_pircture : /images/qiwu.png
         */

        private int case_id;
        private String case_name;
        private String case_date;
        private String case_content;
        private int case_type_id;
        private String case_pircture;

        public int getCase_id() {
            return case_id;
        }

        public void setCase_id(int case_id) {
            this.case_id = case_id;
        }

        public String getCase_name() {
            return case_name;
        }

        public void setCase_name(String case_name) {
            this.case_name = case_name;
        }

        public String getCase_date() {
            return case_date;
        }

        public void setCase_date(String case_date) {
            this.case_date = case_date;
        }

        public String getCase_content() {
            return case_content;
        }

        public void setCase_content(String case_content) {
            this.case_content = case_content;
        }

        public int getCase_type_id() {
            return case_type_id;
        }

        public void setCase_type_id(int case_type_id) {
            this.case_type_id = case_type_id;
        }

        public String getCase_pircture() {
            return case_pircture;
        }

        public void setCase_pircture(String case_pircture) {
            this.case_pircture = case_pircture;
        }
    }
}
