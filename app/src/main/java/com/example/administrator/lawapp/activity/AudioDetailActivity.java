package com.example.administrator.lawapp.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.lawapp.R;

public class AudioDetailActivity extends Activity implements View.OnClickListener {
    private ImageView ivBack;
    private TextView tvTitle;
    private ImageView ivShare;
    private String dataId;
    private WebView webview;
    private ProgressBar pbLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_audio_detail);
        findViews();
        getDataId();
    }

    private void findViews() {
        ivBack = (ImageView) findViewById(R.id.iv_back);
        tvTitle = (TextView) findViewById(R.id.tv_title);
        ivShare = (ImageView) findViewById(R.id.iv_share);
        webview = (WebView) findViewById(R.id.webview);
        pbLoading = (ProgressBar) findViewById(R.id.pb_loading);
        ivBack.setVisibility(View.VISIBLE);
        ivShare.setVisibility(View.VISIBLE);
        tvTitle.setVisibility(View.GONE);
        ivBack.setOnClickListener(this);
        ivShare.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == ivBack) {
            finish();
        } else if (v == ivShare) {
            Toast.makeText(AudioDetailActivity.this, "分享", Toast.LENGTH_SHORT).show();
        }
    }

    private void getDataId() {
        dataId = getIntent().getStringExtra("dataId");
        //设置支持js
        WebSettings webSettings = webview.getSettings();
        webSettings.setJavaScriptEnabled(true);
        //设置双击变大变小
        webSettings.setUseWideViewPort(true);
        //设置正价缩放按钮
        webSettings.setBuiltInZoomControls(true);
        //不让当前网页跳转到手机的浏览器中
        webview.setWebViewClient(new WebViewClient() {
            //当加载页面完成回调
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                pbLoading.setVisibility(View.GONE);
            }
        });

        webview.loadUrl("https://www.58pic.com/tupian/shuzitupiandaquan.html");
    }

}
