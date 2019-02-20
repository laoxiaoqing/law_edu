package com.example.administrator.lawapp.pager;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.example.administrator.lawapp.activity.MainActivity;
import com.example.administrator.lawapp.base.BaseCategoryPager;
import com.example.administrator.lawapp.base.BasePager;
import com.example.administrator.lawapp.fragment.LeftmenuFragment;
import com.example.administrator.lawapp.bean.LawPagerBean;
import com.example.administrator.lawapp.utils.CacheUtils;
import com.example.administrator.lawapp.utils.Constants;
import com.example.administrator.lawapp.utils.LogUtil;
import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * 主页面
 */
public class LawPager extends BasePager {
    /**
     * 左侧菜单对应的集合
     */
    List<LawPagerBean.DataBean> data;
    public List<BaseCategoryPager> baseCategoryPagers;

    public LawPager(Context context) {
        super(context);
    }

    @Override
    public void initData() {
        super.initData();
        LogUtil.e("新闻页面数据初始化了");
        ib_menu.setVisibility(View.VISIBLE);//显示侧滑菜单按钮
        //1.设置标题
        //tv_title.setText("法律书库");
        //2.联网请求得到数据创建视图
        TextView textView = new TextView(context);
        textView.setGravity(Gravity.CENTER);
        textView.setTextColor(Color.RED);
        //3.把子视图添加到BasePager的FrameLayout
        fl_content.addView(textView);
        //4.绑定数据
        //textView.setText("新闻内容");

        //获取缓存数据
        String saveJson = CacheUtils.getString(context, Constants.LAW_PAGER_URL);
        if (!TextUtils.isEmpty(saveJson)) {
            manageData(saveJson);
        }
        getDataFromNet();

    }


    private void getDataFromNet() {
        RequestParams params = new RequestParams(Constants.LAW_PAGER_URL);//写url
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                LogUtil.e("请求成功" + result);
                //缓存数据
                CacheUtils.putString(context, Constants.LAW_PAGER_URL, result);
                //设置适配器
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

    /**
     * 解析json数据和显示数据
     */
    private void manageData(String json) {
        LawPagerBean lawPagerBean = parsedJson(json);
        data = lawPagerBean.getData();
        MainActivity mainActivity = (MainActivity) context;
        //得到左侧菜单
        LeftmenuFragment leftmenuFragment = mainActivity.getLeftmenuFragment();
        baseCategoryPagers = new ArrayList<>();
        baseCategoryPagers.add(new LawCategoryPager(context, data));//二级目录显示页面
        baseCategoryPagers.add(new LawSearchPager(context));//搜索页面
        //把数据传递给左侧菜单
        leftmenuFragment.setData(data);


    }

    /**
     * 解析json数据：
     *
     * @param json
     * @return
     */
    private LawPagerBean parsedJson(String json) {
        Gson gson = new Gson();
        LawPagerBean bean = gson.fromJson(json, LawPagerBean.class);
        return bean;
    }

    /**
     * 切换详情页面
     *
     * @param position
     */
    public void switchPager(int position) {

        if (position == Constants.CATEGORYPAGER) {
            tv_title.setText("搜索");
            isPosition(1, 1, 1);
        } else {
            tv_title.setText(data.get(position).getLaw_category_name());
            isPosition(0, data.get(position).getLaw_category_id(), position);
            /*//1.设置标题
            tv_title.setText(data.get(position).getLaw_category_name());
            //2.移除内容
            fl_content.removeAllViews();//移除之前的视图
            //3.添加新内容
            BaseCategoryPager baseCategoryPager;
            baseCategoryPager = baseCategoryPagers.get(0);
            View rootView = baseCategoryPager.rootView;
            //初始化数据,参数传category的id
            baseCategoryPager.initData(data.get(position).getLaw_category_id());
            fl_content.addView(rootView);*/

        }
    }

    public void isPosition(int num, int category, int position) {
        //1.设置标题

        //2.移除内容
        fl_content.removeAllViews();//移除之前的视图
        //3.添加新内容
        BaseCategoryPager baseCategoryPager;
        baseCategoryPager = baseCategoryPagers.get(num);
        View rootView = baseCategoryPager.rootView;
        //初始化数据,参数传category的id
        baseCategoryPager.initData(category, position);
        fl_content.addView(rootView);
    }

    //通过点击按钮切换页面
    public void switchSearchPager(int category_id, String name, int AllOrBtn) {
        tv_title.setText(name);
        /*fl_content.removeAllViews();
        BaseCategoryPager baseCategoryPager;
        baseCategoryPager=baseCategoryPagers.get(1);*/
        isPosition(1, category_id, AllOrBtn);
    }
}
