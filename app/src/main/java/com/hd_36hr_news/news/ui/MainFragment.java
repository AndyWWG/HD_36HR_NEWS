package com.hd_36hr_news.news.ui;

import android.os.Bundle;
import android.widget.TextView;

import com.hd_36hr_news.R;
import com.hd_36hr_news.utils.entity.CategoryBean;
import com.hd_36hr_news.utils.ui.BaseFragment;

/**
 * Auther Created by xzl on 2016/6/15 14:30.
 * E-mail zuliang_xie@sina.com
 */
public class MainFragment extends BaseFragment {

    private static final String KEY = "EXTRA";
    private CategoryBean mCategoryBean;
    private TextView textView;

    @Override
    protected int getLayout() {
        return R.layout.fragment_news_main_layout;
    }

    @Override
    protected void initView() {
        Bundle bundle = getArguments();
        if (bundle != null){
            mCategoryBean = (CategoryBean) bundle.getSerializable(KEY);
        }

        textView = (TextView) mView.findViewById(R.id.text_tv);
    }

    @Override
    protected void initVarible() {
        textView.setText(mCategoryBean.getmTitle());
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void bindData() {

    }
    /**
     *  用于新建CategoryBean对应的Fragment实例，实现Fragment的复用
     * @return
     */
    public static final MainFragment newInstance(CategoryBean categoryBean){
        Bundle bundle = new Bundle();
        bundle.putSerializable(KEY,categoryBean);
        MainFragment fragment = new MainFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

}
