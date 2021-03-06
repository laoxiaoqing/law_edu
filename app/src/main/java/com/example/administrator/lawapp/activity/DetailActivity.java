package com.example.administrator.lawapp.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.lawapp.R;
import com.example.administrator.lawapp.bean.AuditoriumBean;
import com.example.administrator.lawapp.bean.CasesBean;
import com.example.administrator.lawapp.bean.LawBean;
import com.example.administrator.lawapp.bean.LawMoreBean;
import com.example.administrator.lawapp.bean.VideoBean;
import com.example.administrator.lawapp.utils.Constants;
import com.example.administrator.lawapp.utils.LogUtil;
import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import cn.sharesdk.onekeyshare.OnekeyShare;

public class DetailActivity extends Activity implements View.OnClickListener {
    private ImageView ivBack;
    private TextView tvTitle;
    private ImageView ivShare;
    private String dataId;
    private WebView webview;
    private ImageView ivFont;
    private ProgressBar pbLoading;
    private WebSettings webSettings;
    private String type;
    private AuditoriumBean auditoriumBean;
    private VideoBean videoBean;
    private CasesBean casesBean;
    private LawBean lawBean;
    private LinearLayout llLawHead;
    private TextView tvLaw1;
    private TextView tvLaw2;
    private String title;
    private String content;
    private String picture="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_audio_detail);
        findViews();
        getType();

    }

    private void getType() {
        type = getIntent().getStringExtra("type");
        switch (type) {
            case "audio":
                getAudioDataId();
                break;
            case "video":
                getVideoDataId();
                break;
            case "cases":
                getCasesDataId();
                break;
            case "law":
                getLawDataId();
            case "banner":
                getBannerId();
        }
    }

    private void findViews() {
        ivBack = (ImageView) findViewById(R.id.iv_back);
        tvTitle = (TextView) findViewById(R.id.tv_title);
        ivShare = (ImageView) findViewById(R.id.iv_share);
        webview = (WebView) findViewById(R.id.webview);
        pbLoading = (ProgressBar) findViewById(R.id.pb_loading);
        ivFont = (ImageView) findViewById(R.id.iv_font);
        llLawHead = (LinearLayout) findViewById(R.id.ll_law_head);
        tvLaw1 = (TextView) findViewById(R.id.tv_law1);
        tvLaw2 = (TextView) findViewById(R.id.tv_law2);
        ivBack.setVisibility(View.VISIBLE);
        ivShare.setVisibility(View.VISIBLE);
        tvTitle.setVisibility(View.GONE);
        ivFont.setVisibility(View.VISIBLE);
        ivBack.setOnClickListener(this);
        ivShare.setOnClickListener(this);
        ivFont.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == ivBack) {
            finish();
        } else if (v == ivShare) {
            Toast.makeText(DetailActivity.this, "分享", Toast.LENGTH_SHORT).show();
            showShare();
        } else if (v == ivFont) {
            changeFontSizeDialog();
            Toast.makeText(DetailActivity.this, "ziti", Toast.LENGTH_SHORT).show();
        }
    }

    private void showShare() {
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();

        // title标题，微信、QQ和QQ空间等平台使用
        oks.setTitle(title);
        // titleUrl QQ和QQ空间跳转链接
        oks.setTitleUrl("http://news.eastday.com/c/20180730/u1a14119005_K26843.html");
        // text是分享文本，所有平台都需要这个字段
        oks.setText(content);
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        oks.setImagePath(picture);//确保SDcard下面存在此张图片
        // url在微信、微博，Facebook等平台中使用
        oks.setUrl("http://news.eastday.com/c/20180730/u1a14119005_K26843.html");
        // comment是我对这条分享的评论，仅在人人网使用
        oks.setComment("我是测试评论文本");
        // 启动分享GUI
        oks.show(this);
    }
    private int tempsize = 2;
    private int realsize = tempsize;

    private void changeFontSizeDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("选择文字大小");
        String[] str = new String[4];
        str[0] = "超大字体";
        str[1] = "大字体";
        str[2] = "正常字体";
        str[3] = "小字体";
        builder.setSingleChoiceItems(str, realsize, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                tempsize = which;
            }
        });
        builder.setNegativeButton("取消", null);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                realsize = tempsize;
                changeText(realsize);
            }
        });
        builder.show();
    }

    private void changeText(int real) {
        switch (real) {
            case 0://超大字体
                webSettings.setTextZoom(200);
                break;
            case 1:
                webSettings.setTextZoom(150);
                break;
            case 2:
                webSettings.setTextZoom(100);
                break;
            case 3:
                webSettings.setTextZoom(75);
                break;
        }
    }

    /**
     * 普法视听页面
     */
    private void getAudioDataId() {
        dataId = getIntent().getStringExtra("dataId");
        LogUtil.e("getAudioDataId====" + dataId);
        getAudioFromNet();
        webSettings = webview.getSettings();
        //设置支持js
        webSettings.setJavaScriptEnabled(true);
        //设置双击变大变小
        //webSettings.setUseWideViewPort(true);
        //设置正价缩放按钮
        //webSettings.setBuiltInZoomControls(true);
        //不让当前网页跳转到手机的浏览器中
    }

    //2
    private void getAudioFromNet() {
        RequestParams params = new RequestParams(Constants.AUDITORIUM_URL + "/" + dataId);//写url
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                LogUtil.e("请求成功" + result);
                manageAudio(result);
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

    //3
    private void manageAudio(String json) {
        auditoriumBean = parsedJsonAudio(json);
        title=auditoriumBean.getData().getAuditorium_title();
        content=auditoriumBean.getData().getAuditorium_content();
        picture=auditoriumBean.getData().getAuditorium_picture();
        webview.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            //当加载页面完成回调
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                pbLoading.setVisibility(View.GONE);
                webview.loadUrl("javascript:titleFunc(" + "'" + auditoriumBean.getData().getAuditorium_title() + "'" + ")");
                webview.loadUrl("javascript:imgFunc(" + "'" + auditoriumBean.getData().getAuditorium_picture() + "'" + ")");
                webview.loadUrl("javascript:audioFunc(" + "'" + auditoriumBean.getData().getAuditorium_video() + "'" + ")");
                webview.loadUrl("javascript:textFunc(" + "'" + auditoriumBean.getData().getAuditorium_content() + "'" + ")");
            }
        });
        webview.loadUrl(Constants.BASE_URL + "/audio.html");
    }

    //4
    private AuditoriumBean parsedJsonAudio(String json) {
        Gson gson = new Gson();
        AuditoriumBean auditoriumBean = gson.fromJson(json, AuditoriumBean.class);
        return auditoriumBean;
    }

    /**
     * 看法视频页面
     */
    private void getVideoDataId() {
        dataId = getIntent().getStringExtra("dataId");
        LogUtil.e("getAudioDataId====" + dataId);
        getVideoFromNet();
        webSettings = webview.getSettings();
        //设置支持js
        webSettings.setJavaScriptEnabled(true);
    }

    //2
    private void getVideoFromNet() {
        RequestParams params = new RequestParams(Constants.VIDEO_URL + "/" + dataId);//写url
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                LogUtil.e("请求成功" + result);
                manageVideo(result);
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

    //3
    private void manageVideo(String json) {
        videoBean = parsedJsonVideo(json);
        title=videoBean.getData().getVideo_title();
        content=videoBean.getData().getVideo_content();
        picture =videoBean.getData().getVideo_picture();
        webview.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            //当加载页面完成回调
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                pbLoading.setVisibility(View.GONE);
                webview.loadUrl("javascript:titleFunc(" + "'" + videoBean.getData().getVideo_title() + "'" + ")");
                webview.loadUrl("javascript:imgFunc(" + "'" + videoBean.getData().getVideo_picture() + "'" + ")");
                webview.loadUrl("javascript:videoFunc(" + "'" + videoBean.getData().getVideo_url() + "'" + ")");
                webview.loadUrl("javascript:textFunc(" + "'" + videoBean.getData().getVideo_content() + "'" + ")");
            }
        });
        webview.loadUrl(Constants.BASE_URL + "/video.html");
    }

    //4
    private VideoBean parsedJsonVideo(String json) {
        Gson gson = new Gson();
        VideoBean videoBean = gson.fromJson(json, VideoBean.class);
        return videoBean;
    }

    /**
     * 案例页面
     */
    private void getCasesDataId() {
        dataId = getIntent().getStringExtra("dataId");
        LogUtil.e("getCasesDataId====" + dataId);
        getCasesFromNet();
        webSettings = webview.getSettings();
        //设置支持js
        webSettings.setJavaScriptEnabled(true);
        //设置是否开启DOM存储API权限，默认false
        webSettings.setDomStorageEnabled(true);
    }

    //2
    private void getCasesFromNet() {
        RequestParams params = new RequestParams(Constants.HOME_PAGER_CASES_URL + "/" + dataId);//写url
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                LogUtil.e("请求成功" + result);
                manageCases(result);
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

    //3
    private void manageCases(String json) {
        casesBean = parsedJsonCases(json);
        title=casesBean.getData().getCase_name();
        content=casesBean.getData().getCase_content();
        picture=casesBean.getData().getCase_picture();
        webview.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            //当加载页面完成回调
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                pbLoading.setVisibility(View.GONE);
                webview.loadUrl("javascript:titleFunc(" + "'" + casesBean.getData().getCase_name() + "'" + ")");
                webview.loadUrl("javascript:emFunc(" + "'" + casesBean.getData().getCase_date() + "'" + ")");
                webview.loadUrl("javascript:textFunc(" + "'" + casesBean.getData().getCase_content() + "'" + ")");
            }
        });
        webview.loadUrl(Constants.BASE_URL + "/cases.html");
    }

    //4
    private CasesBean parsedJsonCases(String json) {
        Gson gson = new Gson();
        CasesBean casesBean = gson.fromJson(json, CasesBean.class);
        return casesBean;
    }

    /**
     * 法律书库
     */
    private void getLawDataId() {
        llLawHead.setVisibility(View.VISIBLE);
        dataId = getIntent().getStringExtra("dataId");
        LogUtil.e("getCasesDataId====" + dataId);
        getLawFromNet();
        webSettings = webview.getSettings();
        //设置支持js
        webSettings.setJavaScriptEnabled(true);
    }

    //2
    private void getLawFromNet() {
        RequestParams params = new RequestParams(Constants.LAW_CATEGORY_URL + "/lawid/" + dataId);//写url
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                LogUtil.e("请求成功" + result);
                manageLaw(result);
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

    //3
    private void manageLaw(String json) {
        lawBean = parsedJsonLaw(json);
        title=lawBean.getData().getTwoName();
        tvLaw1.setText(lawBean.getData().getLaw().getLaw_name());
        tvLaw2.setText(lawBean.getData().getOneName()+" -> "+lawBean.getData().getTwoName());
        webview.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            //当加载页面完成回调
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                pbLoading.setVisibility(View.GONE);
                webview.loadUrl("javascript:textFunc(" + "'" + lawBean.getData().getLaw().getLaw_content() + "'" + ")");
                LogUtil.e("lawname" + lawBean.getData().getLaw().getLaw_name());
            }
        });
        webview.loadUrl(Constants.BASE_URL + "/law.html");
    }

    //4
    private LawBean parsedJsonLaw(String json) {
        Gson gson = new Gson();
        LawBean lawBean = gson.fromJson(json, LawBean.class);
        return lawBean;
    }

    /**
     * 轮播图事件
     */
    private void getBannerId() {
        title=getIntent().getStringExtra("title");
        content = getIntent().getStringExtra("content");
        picture=getIntent().getStringExtra("picture");
        String bannerUrl = getIntent().getStringExtra("bannerUrl");
        webSettings = webview.getSettings();
        //设置支持js
        webSettings.setJavaScriptEnabled(true);
        webview.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                pbLoading.setVisibility(View.GONE);
            }
        });
        webview.loadUrl(bannerUrl);
    }


    @Override
    public void onPause() {
        super.onPause();
        webview.onPause();
        webview.pauseTimers();
    }

    @Override
    public void onResume() {
        super.onResume();
        webview.resumeTimers();
        webview.onResume();
    }


    @Override
    protected void onDestroy() {
        webview.destroy();
        webview = null;
        super.onDestroy();
    }
}
