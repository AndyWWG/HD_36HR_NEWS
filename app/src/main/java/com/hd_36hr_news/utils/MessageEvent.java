package com.hd_36hr_news.utils;


import com.hd_36hr_news.utils.entity.CategoryBean;

/**
 * Auther Created by xzl on 2016/6/16 17:08.
 * E-mail zuliang_xie@sina.com
 *
 * 事件类
 */
public class MessageEvent {

    /**
     * 侧滑菜单的事件类
     */
        private  CategoryBean categoryBean;
        public MessageEvent(CategoryBean categoryBean) {
            this.categoryBean = categoryBean;
        }

        public CategoryBean getCategoryBean() {
            return categoryBean;
        }

        public void setCategoryBean(CategoryBean categoryBean) {
            this.categoryBean = categoryBean;
        }
}
