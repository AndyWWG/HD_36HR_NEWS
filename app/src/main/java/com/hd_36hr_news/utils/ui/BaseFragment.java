package com.hd_36hr_news.utils.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Auther Created by xzl on 2016/6/14 09:40.
 * E-mail zuliang_xie@sina.com
 *
 * 基类BaseFragment
 */
public abstract class BaseFragment extends Fragment {

    protected View mView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mView == null){
            mView = inflater.inflate(getLayout(),container,false);
        }
        return mView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        initVarible();
        initListener();
        //bindData();
    }

    /**
     * 获取子类的布局文件
     * @return
     */
    protected abstract int getLayout();
    /**
     * 初始化控件
     */
    protected abstract void initView();

    /**
     * 初始化变量
     */
    protected abstract void initVarible();
    /**
     * 初始化事件处理
     */
    protected abstract void initListener();
    /**
     * 绑定数据
     */
    protected abstract void bindData();
}
