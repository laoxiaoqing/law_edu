package com.example.administrator.lawapp.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public abstract class BaseFragment extends Fragment {
    public Context context;//MainActivity

    /**
     * 当Fragment被创建的时候回调这个方法
     *
     * @param savedInstanceState
     */
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity();
    }

    /**
     * 当视图被创建的时候回调
     *
     * @param inflater
     * @param container
     * @param savedInstanceState 创建了视图
     * @return
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return initView();
    }

    /**
     * 让孩子实现自己的视图，达到自己特有的效果
     *
     * @return
     */
    public abstract View initView();

    /**
     * 当Activity被创建之后被回调
     *
     * @param savedInstanceState
     */
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
    }

    /**
     * 1.如果自页面没有数据，联网请求数据，并且绑定到initView初始的视图上
     * 2.绑定到initView初始化的视图上
     */
    public void initData() {
    }

    /*@Override
    public void setMenuVisibility(boolean menuVisible) {
        super.setMenuVisibility(menuVisible);
        if(this.getView()!=null){
            this.getView().setVisibility(menuVisible ? View.VISIBLE : View.GONE);
        }
    }*/
}
