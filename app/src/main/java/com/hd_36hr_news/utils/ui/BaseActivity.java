package com.hd_36hr_news.utils.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Auther Created by xzl on 2016/6/14 09:32.
 * E-mail zuliang_xie@sina.com
 *
 * 基类Activity
 */
public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayout());
        initView();
        initVarible();
        initListener();
    }

    /**
     * 获取子类布局文件
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
