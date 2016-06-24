package com.hd_36hr_news.news;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.IntegerRes;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.hd_36hr_news.R;
import com.hd_36hr_news.news.adapter.NewsRecyclerAdapter;
import com.hd_36hr_news.news.entity.ArticleBean;
import com.hd_36hr_news.utils.Contants;
import com.hd_36hr_news.utils.Diver;
import com.hd_36hr_news.utils.MessageEvent;
import com.hd_36hr_news.utils.OkHttpUtils;
import com.hd_36hr_news.utils.entity.CategoryBean;
import com.hd_36hr_news.utils.ui.BaseFragment;
import com.hd_36hr_news.utils.ui.HomeActiviy;
import com.squareup.okhttp.Request;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.List;

/**
 * Auther Created by xzl on 2016/6/15 11:06.
 * E-mail zuliang_xie@sina.com
 */
public class NewsFragment extends BaseFragment {
    private ImageView mTopBar;
    private RecyclerView mNewsRecyclerView;
    private NewsRecyclerAdapter mNewsAdapter;
    private LinearLayoutManager mLayoutManager;
    private TextView mTopBarTitle;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private CategoryBean mCategoryBean = null;
    private List<ArticleBean> articleBeanList; //新闻数据集合

    private int lastItem;//最后一条数据position
    private boolean isMore = true;//解决上拉重复加载的bug


    @Override
    protected int getLayout() {
        return R.layout.fragment_news_layout;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //注册EventBus
        EventBus.getDefault().register(this);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void receiveMessage(MessageEvent event){
        mCategoryBean = event.getCategoryBean();
        mTopBarTitle.setText(mCategoryBean.getmTitle());
        if (mCategoryBean.getmType().equals("all")){
            updateNewsData(Contants.URL);
        }else{
            updateNewsData(mCategoryBean.getmHref());
        }
    }

    @Override
    protected void initView() {
        mNewsRecyclerView = (RecyclerView) mView.findViewById(R.id.news_list_recyclerview);
        mTopBar = (ImageView) mView.findViewById(R.id.top_bar_menu);
        mTopBarTitle = (TextView) mView.findViewById(R.id.top_bar_title);
        mSwipeRefreshLayout = (SwipeRefreshLayout) mView.findViewById(R.id.news_refresh);
    }

    /**
     * 获取颜色
     * @param color
     * @return
     */
    private int getC(int color){
        return getResources().getColor(color);
    }

    @Override
    protected void initVarible() {
        //设置进度条的背景颜色
        mSwipeRefreshLayout.setProgressBackgroundColorSchemeColor(getResources().getColor(R.color.white));
        //设置进度条的颜色
        mSwipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.red));
        //设置进度条的偏移量
        //mSwipeRefreshLayout.setProgressViewOffset(false,0,50);
        // 这句话是为了，第一次进入页面的时候显示加载进度条
        mSwipeRefreshLayout.setProgressViewOffset(false, 0, (int) TypedValue
                .applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources()
                        .getDisplayMetrics()));
        //设置RecyclerView固定大小
        mNewsRecyclerView.setHasFixedSize(true);
        //设置布局管理器
        mLayoutManager = new LinearLayoutManager(getActivity(), OrientationHelper.VERTICAL,false);
        mNewsRecyclerView.setLayoutManager(mLayoutManager);
        //设置分割线
        mNewsRecyclerView.addItemDecoration(new Diver(getActivity(),getResources().getColor(R.color.red),3));
        //设置动画
        mNewsRecyclerView.setItemAnimator(new DefaultItemAnimator());
        if (mCategoryBean != null && !mCategoryBean.getmType().equals("all")){
            mNewsAdapter = new NewsRecyclerAdapter(0,getActivity());
            updateNewsData(mCategoryBean.getmHref());
        }else{
            mNewsAdapter = new NewsRecyclerAdapter(0,getActivity());
            updateNewsData(Contants.URL);
        }
    }

    @Override
    protected void initListener() {
        mTopBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((HomeActiviy)getActivity()).openDrawerLayout();
            }
        });
        mNewsAdapter.setmOnRecyclerViewClickListener(new NewsRecyclerAdapter.OnRecyclerViewClickListener() {
            @Override
            public void onItemClick(View view, ArticleBean articleBean, int position) {
                Toast.makeText(getActivity(),"position = "+position + ","+articleBean.getmTitle(),Toast.LENGTH_SHORT).show();
            }
        });
        //刷新监听
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (mCategoryBean == null){
                    updateNewsData(Contants.URL);
                } else {
                    updateNewsData(mCategoryBean.getmHref());
                }
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (mSwipeRefreshLayout.isRefreshing()){
                            mSwipeRefreshLayout.setRefreshing(false);
                        }
                    }
                }, 5000);
            }
        });

        /**
         * 监听RecyclerView滑动事件,上拉加载
         */
        mNewsRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE&&lastItem +1 == mLayoutManager.getItemCount()){
                    if (isMore) {
                        isMore = false;
                        String loadMoreURL = null;
                        if (mCategoryBean != null) {
                            //构造请求URL
                            loadMoreURL = mCategoryBean.getmHref() + "?b_url_code=" + articleBeanList.get(articleBeanList.size() - 1).getmId() + "&d=next";
                        }else {
                            loadMoreURL = Contants.URL + "?b_url_code=" + articleBeanList.get(articleBeanList.size() - 1).getmId() + "&d=next";
                            Log.d("zuliang", loadMoreURL);
                        }
                            OkHttpUtils.getAsync(loadMoreURL, new OkHttpUtils.DataCallBack() {
                                @Override
                                public void requestFailure(Request request, IOException e) {
                                    Log.e("zuliang", "新闻数据加载失败！！！");
                                }

                                @Override
                                public void requestSuccess(String result) {
                                    Document document = Jsoup.parse(result, Contants.URL);
                                    List<ArticleBean> temp = ArticleBean.getArticleBeans(document);
                                    articleBeanList.addAll(temp);
                                    mNewsAdapter.notifyDataSetChanged();
                                    isMore = true;
                                }
                            });
                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                //找到最后一个选项的position
                lastItem = mLayoutManager.findLastCompletelyVisibleItemPosition();
            }
        });
    }

    @Override
    protected void bindData() {
    }

    /**
     * 更新数据
     * @param url
     */
    private void updateNewsData(String url){
        OkHttpUtils.getAsync(url, new OkHttpUtils.DataCallBack() {
            @Override
            public void requestFailure(Request request, IOException e) {
                Log.e("zuliang","新闻数据加载失败！！！");
            }

            @Override
            public void requestSuccess(String result) {
                Document document = Jsoup.parse(result, Contants.URL);
                articleBeanList = ArticleBean.getArticleBeans(document);
                mNewsAdapter.setArticleBeanList(articleBeanList);
                mNewsRecyclerView.setAdapter(mNewsAdapter);
                mNewsAdapter.notifyDataSetChanged();
            }
        });
    }
}
