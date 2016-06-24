package com.hd_36hr_news.utils;

import com.hd_36hr_news.utils.entity.CategoryBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Auther Created by xzl on 2016/6/14 09:29.
 * E-mail zuliang_xie@sina.com
 *
 * 系统里常量配置
 */
public class Contants {

    public static final String URL = "http://36kr.com/columns/starding";

    public static List<CategoryBean> getCategoryBean(){
        List<CategoryBean> categoryBeanList = new ArrayList<CategoryBean>();
        categoryBeanList.add(new CategoryBean("全部","http://www.36kr.com/columns/starding","all"));
        categoryBeanList.add(new CategoryBean("早期项目","http://www.36kr.com/columns/starding","starding"));
        categoryBeanList.add(new CategoryBean("B轮后","http://www.36kr.com/columns/bplus","bplus"));
        categoryBeanList.add(new CategoryBean("大公司","http://www.36kr.com/columns/company","company"));
        categoryBeanList.add(new CategoryBean("资本","http://www.36kr.com/columns/deep","deep"));
        categoryBeanList.add(new CategoryBean("深度","http://www.36kr.com/columns/deep","deep"));
        categoryBeanList.add(new CategoryBean("研究","http://www.36kr.com/columns/research","research"));
        return categoryBeanList;
    }
    
}
