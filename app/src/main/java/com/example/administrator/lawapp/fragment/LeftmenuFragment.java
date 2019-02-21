package com.example.administrator.lawapp.fragment;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.administrator.lawapp.R;
import com.example.administrator.lawapp.activity.MainActivity;
import com.example.administrator.lawapp.base.BaseFragment;
import com.example.administrator.lawapp.bean.LawPagerBean;
import com.example.administrator.lawapp.pager.LawPager;
import com.example.administrator.lawapp.utils.Constants;
import com.example.administrator.lawapp.utils.LogUtil;

import java.util.List;

public class LeftmenuFragment extends BaseFragment {

    private List<LawPagerBean.DataBean> data;
    private ListView listView;
    private LeftmenuFragmentAdapter leftmenuFragmentAdapter;
    private int prePosition;
    private ImageView leftmenu_iv;
    private TextView leftmenu_tv;
    private LinearLayout linearLayout;
    private TextView textView;
    private Boolean isData = false;

    @Override
    public View initView() {
        LogUtil.e("左侧菜单视图初始化了");
        //View view = View.inflate(context, R.layout.activity_leftmenu, null);
        listView = new ListView(context);
        listView.setCacheColorHint(1);
        listView.setSelector(R.color.colorItem);
        listView.setDividerHeight(1);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //1.记录点击的位置，变成红色
                Log.e("wwww", "wwww");
                prePosition = position;
                leftmenuFragmentAdapter.notifyDataSetChanged();//getCount
                //2.把左侧菜单关闭
                MainActivity mainActivity = (MainActivity) context;
                mainActivity.getSlidingMenu().toggle();//关开切换
                //3.切换到对应的详情页面：宪法。。
                switchPager2(prePosition);
            }
        });
        return listView;
    }

    private void switchPager2(int position) {
        MainActivity mainActivity = (MainActivity) context;
        ContentFragment contentFragment = mainActivity.getContentFragment();
        LawPager lawPager = contentFragment.getLawPager();
        lawPager.switchPager(position);
    }

    @Override
    public void initData() {
        super.initData();
        linearLayout = getActivity().findViewById(R.id.leftmenu_search);
        leftmenu_tv = getActivity().findViewById(R.id.leftmenu_tv);
        leftmenu_iv = getActivity().findViewById(R.id.leftmenu_iv);
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prePosition = Constants.CATEGORYPAGER;
                if (isData) {
                    leftmenuFragmentAdapter.notifyDataSetChanged();
                }
                MainActivity mainActivity = (MainActivity) context;
                mainActivity.getSlidingMenu().toggle();//关开切换
                //切换法律的两个页面  搜索，
                ContentFragment contentFragment = mainActivity.getContentFragment();
                LawPager lawPager = contentFragment.getLawPager();
                lawPager.switchPager(Constants.CATEGORYPAGER);
            }
        });
    }

    /**
     * 接收数据
     *
     * @param data
     */
    public void setData(List<LawPagerBean.DataBean> data) {
        this.data = data;
        isData = true;
        for (LawPagerBean.DataBean dataBean : data) {
            LogUtil.e("名称" + dataBean.getLaw_category_name());
        }
        //设置适配器，这里肯定有数据
        leftmenuFragmentAdapter = new LeftmenuFragmentAdapter();
        listView.setAdapter(leftmenuFragmentAdapter);
        //设置默认的页面
        switchPager2(prePosition);
    }

    class LeftmenuFragmentAdapter extends BaseAdapter {


        @Override
        public int getCount() {
            return data.size();
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
                convertView = (TextView) View.inflate(context, R.layout.item_leftmenu, null);
            }
            textView = convertView.findViewById(R.id.tv_title);
            textView.setText(data.get(position).getLaw_category_name());
            LogUtil.e("position==" + position);
            LogUtil.e("prePosition==" + prePosition);
            if (position == prePosition) {
                textView.setEnabled(true);
            } else {
                textView.setEnabled(false);
                leftmenu_iv.setEnabled(false);
                leftmenu_tv.setEnabled(false);
            }
            if (prePosition == -1) {
                leftmenu_iv.setEnabled(true);
                leftmenu_tv.setEnabled(true);
                textView.setEnabled(false);
            }
            return convertView;
        }
    }

}
