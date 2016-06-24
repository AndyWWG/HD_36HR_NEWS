package com.hd_36hr_news.utils.entity;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Auther Created by xzl on 2016/6/14 16:31.
 * E-mail zuliang_xie@sina.com
 */
public class CategoryBean implements Serializable{
    private String mTitle;
    private String mHref;
    private String mType;

    public CategoryBean(String mTitle, String mHref, String mType) {
        this.mTitle = mTitle;
        this.mHref = mHref;
        this.mType = mType;
    }

    public String getmTitle() {
        return mTitle;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getmHref() {
        return mHref;
    }

    public void setmHref(String mHref) {
        this.mHref = mHref;
    }

    public String getmType() {
        return mType;
    }

    public void setmType(String mType) {
        this.mType = mType;
    }

    @Override
    public String toString() {
        return "CategoryBean{" +
                "mTitle='" + mTitle + '\'' +
                ", mHref='" + mHref + '\'' +
                ", mType='" + mType + '\'' +
                '}';
    }

    /**
     * 获取侧滑菜单 新闻分类数据
     * @param document
     * @return
     */
    public static List<CategoryBean> getCategoryBeanData(Document document){
        List<CategoryBean> categoriesBeans=new ArrayList<CategoryBean>();
        Elements elements=document.select("div.categories").first().select("a");
        for (Element element : elements) {
            String typeString =element.attr("data-type");
            String links = "http://www.36kr.com";
            String herfString = links + element.attr("href");
            String textString = element.text();
            categoriesBeans.add(new CategoryBean(textString,herfString,typeString));
        }
        return categoriesBeans;
    }

}
