package com.wangdh.mengm.ui.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;

import com.flyco.tablayout.SlidingTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.wangdh.mengm.R;
import com.wangdh.mengm.base.BaseActivity;
import com.wangdh.mengm.component.AppComponent;
import com.wangdh.mengm.ui.fragment.FunnyPicturesFragment;
import com.wangdh.mengm.utils.StateBarTranslucentUtils;
import com.wangdh.mengm.utils.ToolbarUtils;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;


public class FunnyPicturesActivity extends BaseActivity implements OnTabSelectListener {
    @BindView(R.id.toolbar_fp)
    Toolbar toolbarFp;
    @BindView(R.id.sliding_tab)
    SlidingTabLayout tab;
    @BindView(R.id.fragment_viewpager)
    ViewPager viewpager;
    private List<Fragment> mFragments;
    private final String[] mTitles = {"图片", "动图"};
    private List<String> type = new ArrayList<>();

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {

    }

    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_funnypictures;
    }

    @Override
    protected void initView() {
        ToolbarUtils.initTitle(toolbarFp, R.mipmap.ab_back, "趣图", this);
        StateBarTranslucentUtils.setStateBarColor(this);
        tab.setOnTabSelectListener(this);
        initFrameLayout();
    }

    private void initFrameLayout() {
        mFragments = new ArrayList<>();
        type.add("341-2");
        type.add("341-3");
        for (int i = 0; i < type.size(); i++)
            mFragments.add(FunnyPicturesFragment.newInstance(type.get(i)));
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewpager.setOffscreenPageLimit(mFragments.size());
        viewpager.setAdapter(adapter);
        tab.setViewPager(viewpager);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onTabSelect(int position) {

    }

    @Override
    public void onTabReselect(int position) {

    }

    public class ViewPagerAdapter extends FragmentStatePagerAdapter {

        ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles[position];
        }
    }
}
