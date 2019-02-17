package com.example.administrator.lawapp.pager;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.example.administrator.lawapp.activity.MainActivity;
import com.example.administrator.lawapp.base.BasePager;
import com.example.administrator.lawapp.fragment.LeftmenuFragment;
import com.example.administrator.lawapp.getJson.LawPagerBean;
import com.example.administrator.lawapp.utils.Constants;
import com.example.administrator.lawapp.utils.LogUtil;
import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.List;

/**
 * 主页面
 */
public class LawPager extends BasePager {
    /**
     * 左侧菜单对应的集合
     */
    List<LawPagerBean.DataBean> data;
    public LawPager(Context context) {
        super(context);
    }

    @Override
    public void initData() {
        super.initData();
        LogUtil.e("新闻页面数据初始化了");
        ib_menu.setVisibility(View.VISIBLE);
        //1.设置标题
        tv_title.setText("新闻");
        //2.联网请求得到数据创建视图
        TextView textView = new TextView(context);
        textView.setGravity(Gravity.CENTER);
        textView.setTextColor(Color.RED);
        //3.把子视图添加到BasePager的FrameLayout
        fl_content.addView(textView);
        //4.绑定数据
        textView.setText("新闻内容");
        getDataFromNet();

    }



    private void getDataFromNet() {
        RequestParams params = new RequestParams(Constants.LAW_PAGER_URL);//写url
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                LogUtil.e("请求成功"+result);
                //设置适配器
                manageData(result);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                LogUtil.e("请求失败"+ex.getMessage());

            }

            @Override
            public void onCancelled(CancelledException cex) {
                LogUtil.e("请求onCancelled"+cex.getMessage());

            }

            @Override
            public void onFinished() {
                LogUtil.e("请求结束");

            }
        });
    }
    /**
     * 解析json数据和显示数据
     */
    private void manageData(String json) {
        LawPagerBean lawPagerBean = parsedJson(json);
        data=lawPagerBean.getData();
        LogUtil.e("获得一条数据"+lawPagerBean.isSuccess());
        LogUtil.e("获得一条数据"+data.get(0).getLaw_category_id());
        LogUtil.e("获得一条数据"+data.get(0).getList());
        LogUtil.e("获得一条数据"+data.get(0).getLaw_category_name());
        MainActivity mainActivity = (MainActivity) context;
        //得到左侧菜单
        LeftmenuFragment leftmenuFragment=mainActivity.getLeftmenuFragment();
        //把数据传递给左侧菜单
        leftmenuFragment.setData(data);

    }

    /**
     * 解析json数据：
     * @param json
     * @return
     */
    private LawPagerBean parsedJson(String json){
        Gson gson = new Gson();
        LawPagerBean bean=gson.fromJson(json,LawPagerBean.class);
        return  bean;
    }
}
