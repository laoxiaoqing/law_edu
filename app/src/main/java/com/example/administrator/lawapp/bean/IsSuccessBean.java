package com.example.administrator.lawapp.bean;

/**
 * Created by 吴青晓 on 2019/2/25
 * Describe：判断帐号密码回调的信息
 */
public class IsSuccessBean {

    /**
     * success : true
     * data : {"user":{"user_id":0,"username":"wqx","password":"wqx","user_name":"wqx","user_email":"string","user_sex":"string","user_tel":"string","user_age":0,"user_head":"string"},"isSuccess":"登录成功"}
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
         * user : {"user_id":0,"username":"wqx","password":"wqx","user_name":"wqx","user_email":"string","user_sex":"string","user_tel":"string","user_age":0,"user_head":"string"}
         * isSuccess : 登录成功
         */

        private UserBean user;
        private String isSuccess;

        public UserBean getUser() {
            return user;
        }

        public void setUser(UserBean user) {
            this.user = user;
        }

        public String getIsSuccess() {
            return isSuccess;
        }

        public void setIsSuccess(String isSuccess) {
            this.isSuccess = isSuccess;
        }

        public static class UserBean {
            /**
             * user_id : 0
             * username : wqx
             * password : wqx
             * user_name : wqx
             * user_email : string
             * user_sex : string
             * user_tel : string
             * user_age : 0
             * user_head : string
             */

            private int user_id;
            private String username;
            private String password;
            private String user_name;
            private String user_email;
            private String user_sex;
            private String user_tel;
            private int user_age;
            private String user_head;

            public int getUser_id() {
                return user_id;
            }

            public void setUser_id(int user_id) {
                this.user_id = user_id;
            }

            public String getUsername() {
                return username;
            }

            public void setUsername(String username) {
                this.username = username;
            }

            public String getPassword() {
                return password;
            }

            public void setPassword(String password) {
                this.password = password;
            }

            public String getUser_name() {
                return user_name;
            }

            public void setUser_name(String user_name) {
                this.user_name = user_name;
            }

            public String getUser_email() {
                return user_email;
            }

            public void setUser_email(String user_email) {
                this.user_email = user_email;
            }

            public String getUser_sex() {
                return user_sex;
            }

            public void setUser_sex(String user_sex) {
                this.user_sex = user_sex;
            }

            public String getUser_tel() {
                return user_tel;
            }

            public void setUser_tel(String user_tel) {
                this.user_tel = user_tel;
            }

            public int getUser_age() {
                return user_age;
            }

            public void setUser_age(int user_age) {
                this.user_age = user_age;
            }

            public String getUser_head() {
                return user_head;
            }

            public void setUser_head(String user_head) {
                this.user_head = user_head;
            }
        }
    }
}
