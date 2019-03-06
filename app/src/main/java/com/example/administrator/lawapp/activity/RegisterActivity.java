package com.example.administrator.lawapp.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.lawapp.R;
import com.example.administrator.lawapp.bean.IsSuccessBean;
import com.example.administrator.lawapp.utils.CacheUtils;
import com.example.administrator.lawapp.utils.Constants;
import com.example.administrator.lawapp.utils.LogUtil;
import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

public class RegisterActivity extends Activity implements View.OnClickListener {
    private EditText etUserName;
    private EditText etPsw;
    private EditText etPswAgain;
    private RadioGroup sexRadio;
    private RadioButton mainRegisterRdBtnFemale;
    private RadioButton mainRegisterRdBtnMale;
    private Button btnRegister;
    private TextView tvTips;
    private EditText tv_name;
    private EditText etTel;
    private EditText etEmail;
    private String sex;
    private IsSuccessBean isSuccessBean;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        findViews();
    }

    private void findViews() {
        etUserName = (EditText) findViewById(R.id.et_user_name);
        etPsw = (EditText) findViewById(R.id.et_psw);
        etPswAgain = (EditText) findViewById(R.id.et_psw_again);
        sexRadio = (RadioGroup) findViewById(R.id.SexRadio);
        mainRegisterRdBtnFemale = (RadioButton) findViewById(R.id.mainRegisterRdBtnFemale);
        mainRegisterRdBtnMale = (RadioButton) findViewById(R.id.mainRegisterRdBtnMale);
        btnRegister = (Button) findViewById(R.id.btn_register);
        tvTips = (TextView) findViewById(R.id.tv_tips);
        tv_name = (EditText) findViewById(R.id.tv_name);
        etTel = (EditText) findViewById(R.id.et_tel);
        etEmail = (EditText) findViewById(R.id.et_email);
        mainRegisterRdBtnFemale.setOnClickListener(this);
        mainRegisterRdBtnMale.setOnClickListener(this);
        btnRegister.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == mainRegisterRdBtnFemale) {
            // Handle clicks for mainRegisterRdBtnFemale
        } else if (v == mainRegisterRdBtnMale) {
            // Handle clicks for mainRegisterRdBtnMale
        } else if (v == btnRegister) {
            if (TextUtils.isEmpty(etUserName.getText())){
                tvTips.setText("请输入帐号");
            }else if (TextUtils.isEmpty(etPsw.getText())){
                tvTips.setText("请输入密码");
            }else if (TextUtils.isEmpty(etPswAgain.getText())){
                tvTips.setText("请输入确认密码");
            }else if (TextUtils.isEmpty(tv_name.getText())){
                tvTips.setText("请输入名称");
            }else if (etPsw.getText().equals(etPswAgain.getText())){
                tvTips.setText("两次输入的密码不一样");
            }else {
                int sexChoseId = sexRadio.getCheckedRadioButtonId();
                switch (sexChoseId) {
                    case R.id.mainRegisterRdBtnFemale:
                        sex = "女";
                        break;
                    case R.id.mainRegisterRdBtnMale:
                        sex =   "男";
                        break;
                    default:
                        sex = "";
                        break;
                }
                getFromNetData();
            }

        }
    }

    private void getFromNetData() {
        RequestParams params = new RequestParams(Constants.REGISTER_URL);//写url
        params.setBodyContent("{\"username\":\"" + etUserName.getText().toString()
                + "\",\"password\":\"" + etPswAgain.getText().toString()
                + "\",\"user_name\":\"" + tv_name.getText().toString()
                + "\",\"user_email\":\"" + etEmail.getText().toString()
                + "\",\"user_sex\":\"" + sex.toString()
                + "\",\"user_tel\":\"" + etTel.getText().toString()
                + "\"}");
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
        if (isSuccessBean.getData().getIsSuccess().equals("注册成功")) {
            CacheUtils.putString(this,"user_id",isSuccessBean.getData().getUser().getUser_id()+"");
            CacheUtils.putString(this,"user_head",isSuccessBean.getData().getUser().getUser_head());
            CacheUtils.putString(this,"user_tel",isSuccessBean.getData().getUser().getUser_tel());
            CacheUtils.putString(this,"user_name",isSuccessBean.getData().getUser().getUser_name());
            CacheUtils.putString(this,"user_email",isSuccessBean.getData().getUser().getUser_email());
            CacheUtils.putString(this, "username", isSuccessBean.getData().getUser().getUsername());
            CacheUtils.putString(this, "password", etPswAgain.getText().toString().trim());
            Toast.makeText(RegisterActivity.this,"注册成功",Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }else if (isSuccessBean.getData().getIsSuccess().equals("以已存在用户名")){
            Toast.makeText(RegisterActivity.this,"该用户名已注册",Toast.LENGTH_SHORT).show();
        }
    }

}
