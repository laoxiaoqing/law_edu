package com.example.administrator.lawapp.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.lawapp.R;
import com.example.administrator.lawapp.bean.IsSuccessBean;
import com.example.administrator.lawapp.utils.CacheUtils;
import com.example.administrator.lawapp.utils.Constants;
import com.example.administrator.lawapp.utils.LogUtil;
import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

public class LoginActivity extends Activity implements View.OnClickListener {
    private TextView loginbutton;
    private LinearLayout input;
    private EditText userId;
    private Button buttonBar;
    private EditText pass;
    private Button loginBtn;
    private Button register;
    private TextView promptText;
    private String userid;
    private String pwdmd;
    private String pwd;
    private IsSuccessBean isSuccessBean;
    private CheckBox cbAuto;
    private CheckBox cbSave;
    private LinearLayout tv_mian;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login);
        findViews();
        isSaveLogin();
    }

    private void isSaveLogin() {
        String isSave = CacheUtils.getString(LoginActivity.this, "savelogin");
        String isAuto = CacheUtils.getString(LoginActivity.this, "autologin");
        if (isSave.equals("true")) {
            cbSave.setChecked(true);
            String username = CacheUtils.getString(LoginActivity.this, "username");
            String password = CacheUtils.getString(LoginActivity.this, "password");
            userId.setText(username);
            pass.setText(password);
        }
        if (isAuto.equals("true")) {
            cbAuto.setChecked(true);
        }

    }

    private void findViews() {
        loginbutton = (TextView) findViewById(R.id.loginbutton);
        input = (LinearLayout) findViewById(R.id.input);
        userId = (EditText) findViewById(R.id.userId);//帐号

        pass = (EditText) findViewById(R.id.pass);//密码
        loginBtn = (Button) findViewById(R.id.loginBtn);
        register = (Button) findViewById(R.id.register);
        promptText = (TextView) findViewById(R.id.promptText);
        cbAuto = (CheckBox) findViewById(R.id.cb_auto);
        cbSave = (CheckBox) findViewById(R.id.cb_save);
        tv_mian=(LinearLayout) findViewById(R.id.tv_mian);
        tv_mian.setOnClickListener(this);
        loginBtn.setOnClickListener(this);
        register.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == loginBtn) {
            manageLogin();
        } else if (v == register) {
            Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
            startActivity(intent);
        }else if(v == tv_mian){
            Intent intent = new Intent(LoginActivity.this,MainActivity.class);
            startActivity(intent);
        }
    }

    private void manageLogin() {
        if (TextUtils.isEmpty(userId.getText()) || TextUtils.isEmpty(pass.getText())) {
            promptText.setText("帐号密码不能为空");
        } else {
            userid = userId.getText().toString().trim();
            pwd = pass.getText().toString().trim();
            //MD5Utilpwdmd = MD5Util.string2MD5(pwd);
            getLoginFromNet();
        }
    }

    private void getLoginFromNet() {
        RequestParams params = new RequestParams(Constants.USER_URL);//写url
        params.setBodyContent("{\"username\":\"" + userid
                + "\",\"password\":\"" + pwd + "\"}");
        params.setConnectTimeout(3000);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                LogUtil.e("result" + result);
                manageData(result);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                LogUtil.e("请求失败" + ex.getMessage());
            }

            @Override
            public void onCancelled(CancelledException cex) {
                LogUtil.e("请求onCancelled" + cex.getMessage());
            }

            @Override
            public void onFinished() {
                LogUtil.e("请求结束");
            }
        });
    }

    private void manageData(String result) {
        Gson gson = new Gson();
        isSuccessBean = gson.fromJson(result, IsSuccessBean.class);
        if (isSuccessBean.getData().getIsSuccess().equals("登录成功")) {
            CacheUtils.putString(this,"user_id",isSuccessBean.getData().getUser().getUser_id()+"");
            CacheUtils.putString(this,"user_head",isSuccessBean.getData().getUser().getUser_head());
            CacheUtils.putString(this,"user_tel",isSuccessBean.getData().getUser().getUser_tel());
            CacheUtils.putString(this,"user_name",isSuccessBean.getData().getUser().getUser_name());
            CacheUtils.putString(this,"user_email",isSuccessBean.getData().getUser().getUser_email());
            CacheUtils.putString(this, "username", userid);
            CacheUtils.putString(this, "password", pwd);
            boolean isCheakedAuto = cbAuto.isChecked();
            boolean isCheakedSave = cbSave.isChecked();
            if (isCheakedAuto || isCheakedSave) {
                if (isCheakedAuto) {
                    CacheUtils.putString(this, "autologin", "true");
                    CacheUtils.putString(this, "savelogin", "true");
                } else {
                    CacheUtils.putString(this, "autologin", "false");
                }
                if (isCheakedSave) {
                    CacheUtils.putString(this, "savelogin", "true");
                } else {
                    CacheUtils.putString(this, "savelogin", "false");
                }

            }
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            //CacheUtils.putString(this, "userManage", userid);
            startActivity(intent);
            finish();
        }else {
            promptText.setText("帐号密码有误！");
        }
    }
}
