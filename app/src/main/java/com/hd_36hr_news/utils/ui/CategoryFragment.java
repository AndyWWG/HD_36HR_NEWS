package com.hd_36hr_news.utils.ui;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.hd_36hr_news.R;
import com.hd_36hr_news.utils.Contants;
import com.hd_36hr_news.utils.Diver;
import com.hd_36hr_news.utils.MessageEvent;
import com.hd_36hr_news.utils.OkHttpUtils;
import com.hd_36hr_news.utils.adapter.FixedPagerAdapter;
import com.hd_36hr_news.utils.entity.CategoryBean;
import com.squareup.okhttp.Request;

import org.greenrobot.eventbus.EventBus;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.List;

/**
 * Auther Created by xzl on 2016/6/14 10:44.
 * E-mail zuliang_xie@sina.com
 * <p/>
 * 侧滑菜单Fragment
 */
public class CategoryFragment extends BaseFragment {

    private static final String TAG = "CategoryFragment";
    private ImageView mBack;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private FixedPagerAdapter mAdapter;
    private List<CategoryBean> mCategoryList;

    @Override
    protected int getLayout() {
        return R.layout.fragment_category_tab;
    }

    @Override
    protected void initView() {
        mBack = (ImageView) mView.findViewById(R.id.category_back);
        mRecyclerView = (RecyclerView) mView.findViewById(R.id.recyclerview);
    }

    @Override
    protected void initVarible() {
        //设置布局管理器
        mLayoutManager = new LinearLayoutManager(getActivity(), OrientationHelper.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);
        //设置RecyclerView的固定大小
        mRecyclerView.setHasFixedSize(true);
        //设置适配器
        mAdapter = new FixedPagerAdapter(getActivity(), 0);

        mRecyclerView.addItemDecoration(new Diver(getActivity()));

        OkHttpUtils.getAsync(Contants.URL, new OkHttpUtils.DataCallBack() {
            @Override
            public void requestFailure(Request request, IOException e) {
                Log.e("TAG", "网络请求失败！！！");
            }

            @Override
            public void requestSuccess(String result) {
                Document document = Jsoup.parse(result, Contants.URL);
                mCategoryList = CategoryBean.getCategoryBeanData(document);
                mAdapter.setCategoryList(mCategoryList);
                mRecyclerView.setAdapter(mAdapter);
                //刷新适配器
                mAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    protected void initListener() {
        //实现RecyclerView的ItemView的点击事件
        mAdapter.setOnRecyclerViewClickListener(new FixedPagerAdapter.OnRecyclerViewClickListener() {
            @Override
            public void onItemClick(View view, CategoryBean categoryBean) {
                //发送给新闻界面
                EventBus.getDefault().post(new MessageEvent(categoryBean));
                ((HomeActiviy) getActivity()).closeDrawerLayout();
            }
        });
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((HomeActiviy) getActivity()).closeDrawerLayout();

            }
        });
    }

    @Override
    protected void bindData() {

    }
}