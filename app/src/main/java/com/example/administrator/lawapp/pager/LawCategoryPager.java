package com.example.administrator.lawapp.pager;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.lawapp.R;
import com.example.administrator.lawapp.activity.MainActivity;
import com.example.administrator.lawapp.base.BaseCategoryPager;
import com.example.administrator.lawapp.bean.LawPagerBean;
import com.example.administrator.lawapp.fragment.ContentFragment;
import com.example.administrator.lawapp.utils.CacheUtils;
import com.example.administrator.lawapp.utils.Constants;
import com.example.administrator.lawapp.utils.LogUtil;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.List;

import static android.widget.LinearLayout.VERTICAL;

/**
 * Created by 吴青晓 on 2019/2/17
 * Describe
 */
public class LawCategoryPager extends BaseCategoryPager {
    /* public TextView textView;*/
    private List<LawPagerBean.DataBean> dataBeanList;
    @ViewInject(R.id.law_category_pager_id)
    private LinearLayout linearLayout;

    @ViewInject(R.id.child_ll)
    private LinearLayout child_ll;

    private int law_category_id;
    private String law_category_name;

    public LawCategoryPager(Context context) {
        super(context);
    }

    public LawCategoryPager(Context context, List<LawPagerBean.DataBean> dataBeans) {
        super(context);
        dataBeanList = dataBeans;
    }

    @Override
    public View initView() {
        View view = View.inflate(context, R.layout.law_category_pager, null);
        x.view().inject(LawCategoryPager.this, view);
        Button button = view.findViewById(R.id.btnAll);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "这里点击全部按钮" + law_category_id, Toast.LENGTH_SHORT).show();
                //通过传回law_category_id的值，获取当前下所有法律书。
                //getDataFromNetByParentId(law_category_id);
                MainActivity mainActivity = (MainActivity) context;
                //mainActivity.getSlidingMenu().toggle();//关开切换
                //切换法律的两个页面  搜索，
                ContentFragment contentFragment = mainActivity.getContentFragment();
                LawPager lawPager = contentFragment.getLawPager();
                lawPager.switchSearchPager(law_category_id, law_category_name, 0);
            }
        });
        return view;
    }

    private void getDataFromNetByParentId(final int law_category_id) {
        RequestParams params = new RequestParams(Constants.LAW_CATEGORY_URL + "parentid/" + law_category_id);
        LogUtil.e(Constants.LAW_CATEGORY_URL + "parentid/" + law_category_id);//写url
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                LogUtil.e("LawSearchPager请求成功" + result);
                //缓存数据
                CacheUtils.putString(context, Constants.LAW_CATEGORY_URL + "parentid/" + law_category_id, result);

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                LogUtil.e("LawSearchPager请求失败" + ex.getMessage());

            }

            @Override
            public void onCancelled(CancelledException cex) {
                LogUtil.e("LawSearchPager请求onCancelled" + cex.getMessage());

            }

            @Override
            public void onFinished() {
                LogUtil.e("LawSearchPager请求结束");

            }
        });
    }

    private int anInt = 1001;
    private LinearLayout create_ll;

    @Override
    public void initData(int categoryId, int position) {
        super.initData(categoryId, position);
        LogUtil.e("二级目录数据被初始化了");
        law_category_id = dataBeanList.get(position).getLaw_category_id();
        law_category_name = dataBeanList.get(position).getLaw_category_name();
        int a = 0;
        //linearLayout.removeAllViews();
        //linearLayout.removeViewAt(1);

        //int b = linearLayout.getChildCount();
        //LogUtil.e(b + "-----------------");
        //linearLayout.removeViews(1,b);
        child_ll.removeAllViews();
        //创建多个LinearLayout
        while (a < (dataBeanList.get(position).getList().size() / 4) + 1) {
            a = a + 1;
            LogUtil.e("一共有多少条数据" + dataBeanList.get(position).getList().size());
            createLinearLayout(a, position);
            child_ll.addView(create_ll);
        }


        //dataBeanList.get(position).getLaw_category_name();
        /*textView.setText("二级目录内容");*/
    }

    private void createButton() {

    }

    private View createLinearLayout(final int a, final int position) {

        create_ll = new LinearLayout(context);
        create_ll.setId(anInt++);

        LogUtil.e(create_ll.getId() + "LinearLayout");
        create_ll.setOrientation(LinearLayout.HORIZONTAL);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 4);
        create_ll.setLayoutParams(layoutParams);

        for (int i = 0; i < 4; i++) {
            if (i + 4 * (a - 1) < dataBeanList.get(position).getList().size()) {
                Log.e("if(TextUtils", "这里循环出现");
                final String name = dataBeanList.get(position).getList().get(i + 4 * (a - 1)).getLaw_category_name();
                final int id = dataBeanList.get(position).getList().get(i + 4 * (a - 1)).getLaw_category_id();
                LogUtil.e(dataBeanList.get(position).getList().get(i + 4 * (a - 1)).getLaw_category_name());
                //TextView tv = new TextView(context);
                Button button = new Button(context);
                LinearLayout.LayoutParams btnParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1);
                btnParams.gravity = Gravity.DISPLAY_CLIP_HORIZONTAL;
                button.setLayoutParams(btnParams);
                button.setTextSize(10);
                button.setText(dataBeanList.get(position).getList().get(i + 4 * (a - 1)).getLaw_category_name());
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        Intent intent = new Intent();
//                        intent.putExtra("category_id", name);
                        Toast.makeText(context, name, Toast.LENGTH_SHORT).show();
                        MainActivity mainActivity = (MainActivity) context;
                        //mainActivity.getSlidingMenu().toggle();//关开切换
                        //切换法律的两个页面  搜索，
                        ContentFragment contentFragment = mainActivity.getContentFragment();
                        LawPager lawPager = contentFragment.getLawPager();
                        lawPager.switchSearchPager(id, name, 1);
                    }
                });
                create_ll.addView(button);

            }

        }

        return create_ll;
    }
}
