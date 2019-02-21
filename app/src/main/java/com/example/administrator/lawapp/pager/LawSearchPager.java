package com.example.administrator.lawapp.pager;

import android.content.Context;
import android.graphics.Color;
import android.text.Editable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.administrator.lawapp.R;
import com.example.administrator.lawapp.base.BaseCategoryPager;
import com.example.administrator.lawapp.bean.LawBean;
import com.example.administrator.lawapp.bean.LawPagerBean;
import com.example.administrator.lawapp.utils.CacheUtils;
import com.example.administrator.lawapp.utils.Constants;
import com.example.administrator.lawapp.utils.LogUtil;
import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.List;

/**
 * Created by 吴青晓 on 2019/2/18
 * Describe
 */
public class LawSearchPager extends BaseCategoryPager {
    //public TextView textView;
    List<LawBean.Law> lawData;
    @ViewInject(R.id.lv_search)
    private ListView lv_search;
    private LvSearchAdapter lvSearchAdapter;

    @ViewInject(R.id.tv_cateName)
    private TextView tv_cateName;
    @ViewInject(R.id.tv_cateId)
    private TextView tv_cateId;
    //    @ViewInject(R.id.lv_search)
//    private ImageView iv_search;
    @ViewInject(R.id.et_search)
    private EditText et_search;
    private ImageView imageView;

    public LawSearchPager(Context context) {
        super(context);
    }

    @Override
    public View initView() {
        /*textView = new TextView(context);
        textView.setGravity(Gravity.CENTER);
        textView.setTextColor(Color.RED);

        return textView;*/
        View view = View.inflate(context, R.layout.law_search_pager, null);
        x.view().inject(LawSearchPager.this, view);
        imageView = view.findViewById(R.id.iv_search);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lvSearchAdapter.notifyDataSetChanged();
                getDataFromNetByName(et_search.getText().toString());

            }
        });
        return view;
    }


    @Override
    public void initData(int categoryId, int position) {
        super.initData(categoryId, position);
        LogUtil.e("搜索");
        //lv_search.setAdapter(new LvSearchAdapter());
        //textView.setText("搜索");

        getDataFromNetById(categoryId, position);
    }

    private void getDataFromNetByName(final String cateName) {
        RequestParams params = new RequestParams(Constants.LAW_CATEGORY_URL + "keyword/" + cateName);
        LogUtil.e(Constants.LAW_CATEGORY_URL + "keyword/" + cateName);//写url
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                LogUtil.e("LawSearchPager请求成功" + result);
                //缓存数据
                CacheUtils.putString(context, Constants.LAW_CATEGORY_URL + "keyword/" + cateName, result);
                //设置适配器
                manageData(result);
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

    private void getDataFromNetById(final int cate_id, int AllOrBtn) {
        RequestParams params;
        if (AllOrBtn == 0) {
            params = new RequestParams(Constants.LAW_CATEGORY_URL + "/parentid/" + cate_id);
        } else {
            params = new RequestParams(Constants.LAW_CATEGORY_URL + cate_id);
        }

        LogUtil.e(Constants.LAW_CATEGORY_URL + cate_id);//写url
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                LogUtil.e("LawSearchPager请求成功" + result);
                //缓存数据
                CacheUtils.putString(context, Constants.LAW_CATEGORY_URL + cate_id, result);
                //设置适配器
                manageData(result);
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

    private void manageData(String json) {
        LogUtil.e(json);
        LawBean lawBean = parsedJson(json);
        lawData = lawBean.getData();
        LogUtil.e("这里显示大小" + lawData.size());
        lvSearchAdapter = new LvSearchAdapter();
        lv_search.setAdapter(lvSearchAdapter);
        lv_search.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String tv = tv_cateId.getText().toString().trim();
                /**
                 * 跳转到
                 */
            }
        });
    }

    /**
     * 解析JSON数据
     *
     * @param json
     * @return
     */
    private LawBean parsedJson(String json) {
        Gson gson = new Gson();
        LawBean bean = gson.fromJson(json, LawBean.class);

        return bean;
    }

    private class LvSearchAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return lawData.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = View.inflate(context, R.layout.item_law, null);
            }
            tv_cateId = convertView.findViewById(R.id.tv_cateId);
            tv_cateId.setText(lawData.get(position).getLaw_id() + "");//可以不赋值
            tv_cateName = convertView.findViewById(R.id.tv_cateName);
            tv_cateName.setText(lawData.get(position).getLaw_name());
            return convertView;
        }
    }
}

