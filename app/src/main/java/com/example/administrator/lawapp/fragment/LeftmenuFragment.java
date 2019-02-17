package com.example.administrator.lawapp.fragment;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.administrator.lawapp.R;
import com.example.administrator.lawapp.activity.MainActivity;
import com.example.administrator.lawapp.base.BaseFragment;
import com.example.administrator.lawapp.base.BasePager;
import com.example.administrator.lawapp.getJson.LawPagerBean;
import com.example.administrator.lawapp.utils.DensityUtil;
import com.example.administrator.lawapp.utils.LogUtil;

import org.w3c.dom.Text;

import java.util.List;

public class LeftmenuFragment extends BaseFragment {

    private List<LawPagerBean.DataBean> data;
    private ListView listView;
    private LeftmenuFragmentAdapter leftmenuFragmentAdapter;
    private int prePosition;
    @Override
    public View initView() {
        LogUtil.e("左侧菜单视图初始化了");
        listView = new ListView(context);
        listView.setPadding(0,DensityUtil.dip2px(context,40),0,0);
        listView.setDividerHeight(1);//分割线高度为0
        listView.setCacheColorHint(Color.TRANSPARENT);
        //设置按下listview的item不变色
        listView.setSelector(R.color.colorItem);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //1.记录点击的位置，变成红色
                prePosition = position;
                leftmenuFragmentAdapter.notifyDataSetChanged();//getCount
                //2.把左侧菜单关闭
                MainActivity mainActivity = (MainActivity) context;
                mainActivity.getSlidingMenu().toggle();//关开切换
                
                //3.切换到对应的详情页面：宪法，民事类。。

            }
        });


        return listView;
    }

    @Override
    public void initData() {
        super.initData();

    }

    /**
     * 接收数据
     * @param data
     */
    public void setData(List<LawPagerBean.DataBean> data){
        this.data = data;
        for (LawPagerBean.DataBean dataBean:data){
            LogUtil.e("名称"+dataBean.getLaw_category_name());
        }
        //设置适配器，这里肯定有数据
        leftmenuFragmentAdapter = new LeftmenuFragmentAdapter();
        listView.setAdapter(leftmenuFragmentAdapter);
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
            if (convertView==null){
                convertView = (TextView) View.inflate(context, R.layout.item_leftmenu,null);
            }
            TextView textView = convertView.findViewById(R.id.tv_title);
            textView.setText(data.get(position).getLaw_category_name());
            if (position==prePosition){
                textView.setEnabled(true);
            }else {
                textView.setEnabled(false);
            }
            return convertView;
        }
    }
}
