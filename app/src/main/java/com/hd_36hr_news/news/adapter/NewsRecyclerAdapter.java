package com.hd_36hr_news.news.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hd_36hr_news.R;
import com.hd_36hr_news.news.entity.ArticleBean;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Auther Created by xzl on 2016/6/16 09:27.
 * E-mail zuliang_xie@sina.com
 *
 * 新闻列表适配器
 */
public class NewsRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int NORMAL = 0;//新闻列表视图
    private static final int OTHER = 1;
    private static final int FOOTER = 2; //上拉加载更多布局
    private int mType = 0;  //默认是新闻列表视图
    private LayoutInflater mInflater;
    private Context mContext;
    private OnRecyclerViewClickListener mOnRecyclerViewClickListener;
    /**
     * 获取新闻数据
     */
    private List<ArticleBean> mArticleBeanList;

    public void setArticleBeanList(List<ArticleBean> mArticleBeanList) {
        this.mArticleBeanList = mArticleBeanList;
    }

    public NewsRecyclerAdapter(int mType, Context mContext) {
        this.mType = mType;
        this.mContext = mContext;
        mInflater = LayoutInflater.from(mContext);
    }

    /**
     * 是根据viewType去加载不同的ViewHolder
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = null;
        if (viewType == NORMAL){
            //新闻视图
            if (itemView == null){
                itemView = mInflater.inflate(R.layout.items_news_layout,parent,false);
                return new NewsNormalViewHoler(itemView);
            }
        } else if (viewType == FOOTER){
            //上拉加载更多布局
            View footItemView=mInflater.inflate(R.layout.recycler_load_more_layout,parent,false);
            FooterItemViewHolder footItemViewHolder=new FooterItemViewHolder(footItemView);
            return footItemViewHolder;
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof NewsNormalViewHoler){
            ArticleBean articleBean = mArticleBeanList.get(position);
            holder.itemView.setTag(articleBean);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnRecyclerViewClickListener != null){
                        mOnRecyclerViewClickListener.onItemClick(v, (ArticleBean) v.getTag(),position);
                    }
                }
            });
            Picasso.with(mContext).load(articleBean.getmImgUrl()).into(((NewsNormalViewHoler) holder).mNewLogo);
            ((NewsNormalViewHoler)holder).mNewsTitle.setText(articleBean.getmTitle());
            ((NewsNormalViewHoler) holder).mNewsType.setText(articleBean.getmMask());
            ((NewsNormalViewHoler) holder).mNewsAuther.setText(articleBean.getmAuthor().getmName());
            ((NewsNormalViewHoler) holder).mTime.setText(articleBean.getmDateText());
        } else if (holder instanceof FooterItemViewHolder){
            //上拉加载更多布局
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnRecyclerViewClickListener != null){
                        mOnRecyclerViewClickListener.onItemClick(v, (ArticleBean) v.getTag(),position);
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mArticleBeanList.size();
    }

    /**
     * 是判断RecyclerView所要加载的是哪个视图
     * @param position
     * @return
     */
    @Override
    public int getItemViewType(int position) {
        if (position + 1 == getItemCount()){
            return FOOTER;
        } else {
            return NORMAL;
        }
    }

    /**
     * 新闻列表的视图
     */
    private class NewsNormalViewHoler extends RecyclerView.ViewHolder{
        private TextView mNewsTitle,mNewsType,mNewsAuther,mTime;
        private ImageView mNewLogo;
        public NewsNormalViewHoler(View itemView) {
            super(itemView);
            mNewsTitle = (TextView) itemView.findViewById(R.id.news_items_title);
            mNewsType = (TextView) itemView.findViewById(R.id.news_items_type);
            mNewsAuther = (TextView) itemView.findViewById(R.id.news_items_auther);
            mTime = (TextView) itemView.findViewById(R.id.news_items_time);
            mNewLogo = (ImageView) itemView.findViewById(R.id.news_items_logo);
        }
    }

    /**
     * 上拉刷新加载更多布局
     */
    private class FooterItemViewHolder extends RecyclerView.ViewHolder{
        public FooterItemViewHolder(View itemView) {
            super(itemView);
        }
    }

    /**
     * 提供给外部调用者的一个监听接口
     */
    public interface OnRecyclerViewClickListener{
        void onItemClick(View view, ArticleBean articleBean,int position);
    }

    /**
     * 将这个监听接口暴露给外部调用者
     * @param mOnRecyclerViewClickListener
     */
    public void setmOnRecyclerViewClickListener(OnRecyclerViewClickListener mOnRecyclerViewClickListener) {
        this.mOnRecyclerViewClickListener = mOnRecyclerViewClickListener;
    }
}