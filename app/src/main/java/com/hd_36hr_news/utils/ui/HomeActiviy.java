package com.hd_36hr_news.utils.ui;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Gravity;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.hd_36hr_news.R;
import com.hd_36hr_news.equity.EquityFragment;
import com.hd_36hr_news.find.FindFragment;
import com.hd_36hr_news.my.MyFragment;
import com.hd_36hr_news.news.NewsFragment;
import com.hd_36hr_news.utils.adapter.MyViewPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Auther Created by xzl on 2016/6/14 10:14.
 * E-mail zuliang_xie@sina.com
 *
 * 主界面
 */
public class HomeActiviy extends BaseActivity implements RadioGroup.OnCheckedChangeListener,ViewPager.OnPageChangeListener{

    private RadioGroup mHomeTabItems;
    private ViewPager mViewPager;
    private MyViewPagerAdapter myViewPagerAdapter;
    private List<Fragment> mFragmentList;
    private DrawerLayout mDrawerLayout;
    @Override
    protected int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.main_container);
        mHomeTabItems = (RadioGroup) findViewById(R.id.home_tab);
        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        //设置ViewPager的缓存个数
        mViewPager.setOffscreenPageLimit(3);
        //获取适配器对象
        myViewPagerAdapter = new MyViewPagerAdapter(getSupportFragmentManager());
        //获取Fragment集合对象
        mFragmentList = new ArrayList<Fragment>();
        mFragmentList.add(new NewsFragment());
        mFragmentList.add(new EquityFragment());
        mFragmentList.add(new FindFragment());
        mFragmentList.add(new MyFragment());
        //将Fragment集合传入给Adapter
        myViewPagerAdapter.setmFragmentList(mFragmentList);
        //设置适配器
        mViewPager.setAdapter(myViewPagerAdapter);
    }

    @Override
    protected void initVarible() {
        mHomeTabItems.check(R.id.home_tab_news);
    }

    @Override
    protected void initListener() {
        mHomeTabItems.setOnCheckedChangeListener(this);
        mViewPager.setOnPageChangeListener(this);
    }

    /**
     * 关闭侧滑菜单
     */
    public void closeDrawerLayout(){
        mDrawerLayout.closeDrawer(Gravity.LEFT);
    }

    /**
     * 打开侧滑菜单
     */
    public void openDrawerLayout(){
        mDrawerLayout.openDrawer(Gravity.LEFT);
    }

    @Override
    protected void bindData() {
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId){
            case R.id.home_tab_news:
                mViewPager.setCurrentItem(0,true);
                break;
            case R.id.home_tab_equity:
                mViewPager.setCurrentItem(1,true);
                break;
            case R.id.home_tab_find:
                mViewPager.setCurrentItem(2,true);
                break;
            case R.id.home_tab_my:
                mViewPager.setCurrentItem(3,true);
                break;
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        switch (position){
            case 0:
                mHomeTabItems.check(R.id.home_tab_news);
                break;
            case 1:
                mHomeTabItems.check(R.id.home_tab_equity);
                break;
            case 2:
                mHomeTabItems.check(R.id.home_tab_find);
                break;
            case 3:
                mHomeTabItems.check(R.id.home_tab_my);
                break;
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
