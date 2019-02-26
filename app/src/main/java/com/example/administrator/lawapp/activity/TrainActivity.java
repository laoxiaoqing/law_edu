package com.example.administrator.lawapp.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Window;

import com.example.administrator.lawapp.R;
import com.example.administrator.lawapp.base.BaseTrainFragment;
import com.example.administrator.lawapp.fragment.AnswerFragment;
import com.example.administrator.lawapp.fragment.PagerFragment;
import com.example.administrator.lawapp.fragment.TestFragment;
import com.example.administrator.lawapp.fragment.WrongFragment;

import java.util.ArrayList;
import java.util.List;

public class TrainActivity extends FragmentActivity {
    private List<BaseTrainFragment> baseTrainFragments;
    private static final String  TESTTAG="test";

    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_train);
        //初始化Fragment
        initFragement();
        //根据位置得到对应 的Fragment
        BaseTrainFragment baseTrainFragment = getFragment();
        //替换
        switchFrament(baseTrainFragment);
    }

    public void switchFrament(BaseTrainFragment baseTrainFragment) {
        //1.得到FragmentManger
        FragmentManager fm = getSupportFragmentManager();
        //2.开启事务
        FragmentTransaction transaction =fm.beginTransaction();
        //3.替换
        transaction.replace(R.id.fl_content,baseTrainFragment,TESTTAG);
        //4.提交事务
        transaction.commit();
    }

    /**
     * 根据位置得到对应的Fragment
     *
     * @return
     */
    private BaseTrainFragment getFragment() {
        switch (getIntent().getStringExtra("position")) {
            case "test":
                position = 0;
                break;
            case "wrong":
                position = 1;
                break;
            case "page":
                position = 2;
                break;
            case "answer":
                position = 3;
                break;
            default:
                position = 0;
                break;
        }
        BaseTrainFragment fragment = baseTrainFragments.get(position);
        return fragment;
    }

    private void initFragement() {
        baseTrainFragments = new ArrayList<>();
        baseTrainFragments.add(new TestFragment());
        baseTrainFragments.add(new WrongFragment());
        baseTrainFragments.add(new PagerFragment());
        baseTrainFragments.add(new AnswerFragment());
    }

    public BaseTrainFragment getTextFragement() {
        BaseTrainFragment fragment=baseTrainFragments.get(0);
        return fragment;
    }
}
