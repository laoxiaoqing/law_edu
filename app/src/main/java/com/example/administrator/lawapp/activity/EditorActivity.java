package com.example.administrator.lawapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.administrator.lawapp.R;
import com.example.administrator.lawapp.utils.CacheUtils;
import com.example.administrator.lawapp.utils.Constants;
import com.example.administrator.lawapp.utils.LogUtil;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

public class EditorActivity extends Activity {
    private ImageView iv_commit;
    private ImageView back;
    private EditText et_text;
    private String user_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_editor);
        back = findViewById(R.id.back);
        iv_commit = findViewById(R.id.iv_commit);
        et_text = findViewById(R.id.et_text);
        Intent intent= getIntent();
         user_id=intent.getStringExtra("user_id");
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        iv_commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDataFromNet();
            }
        });
    }

    private void getDataFromNet() {
        RequestParams params = new RequestParams(Constants.FORUM_PAGER_URL);//写url
        params.setBodyContent("{\"user_id\":\"" + user_id
                + "\",\"forum_content\":\"" + et_text.getText().toString().trim()
                + "\"}");
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                LogUtil.e("请求成功" + result);
                if (result.indexOf("true")==-1){
                    Toast.makeText(EditorActivity.this,"存在非法字眼"+result,Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(EditorActivity.this,"发表成功",Toast.LENGTH_LONG).show();
                    finish();
                }

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

}
