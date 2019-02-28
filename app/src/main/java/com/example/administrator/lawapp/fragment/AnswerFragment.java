package com.example.administrator.lawapp.fragment;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.GridView;
import android.widget.TextView;

import com.example.administrator.lawapp.R;
import com.example.administrator.lawapp.base.BaseTrainFragment;
import com.example.administrator.lawapp.bean.PapersTopicBean;
import com.example.administrator.lawapp.trainpager.TestContentPager;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * Created by 吴青晓 on 2019/2/26
 * Describe
 */
public class AnswerFragment extends BaseTrainFragment {
    private  PapersTopicBean paperTopicBean;
    private Context context;
    @ViewInject(R.id.paperGrid)
    private GridView paperGrid;
    @ViewInject(R.id.done)
    private TextView done;


    /*public AnswerFragment(Context context){

    }*/
    @Override
    public View initView() {
        View view = View.inflate(context, R.layout.answer_content, null);
        x.view().inject(AnswerFragment.this, view);

        return view;
    }

    @Override
    protected void initData() {
        getDataFromNet();
        super.initData();
    }

    private void getDataFromNet() {

    }
}
