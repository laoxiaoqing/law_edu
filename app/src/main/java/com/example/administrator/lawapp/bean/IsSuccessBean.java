package com.example.administrator.lawapp.bean;

/**
 * Created by 吴青晓 on 2019/2/25
 * Describe：判断帐号密码回调的信息
 */
public class IsSuccessBean {

    /**
     * success : true
     * data : 登录失败
     */

    private boolean success;
    private String data;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
