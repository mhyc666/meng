package com.wangdh.mengm.ui.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import com.flyco.tablayout.SegmentTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.wangdh.mengm.R;
import com.wangdh.mengm.base.BaseFragment;
import com.wangdh.mengm.component.AppComponent;
import com.wangdh.mengm.utils.StateBarTranslucentUtils;

import java.util.ArrayList;
import butterknife.BindView;


/**
 * 生活健康
 */

public class LiveFragment extends BaseFragment {
    @BindView(R.id.segmenttab)
    SegmentTabLayout segmenttab;
    @BindView(R.id.viewpager)
    ViewPager viewpager;
    private String[] mTitles = {"生活", "健康"};
    private ArrayList<Fragment> mFragments = new ArrayList<>();

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {

    }

    @Override
    protected int setLayoutResourceID() {
        return R.layout.fragment_live;
    }

    @Override
    protected void initData() {
    }

    @Override
    protected void initView() {
        mFragments.add(new CookBooksFragment());
        mFragments.add(new HealthFragment());
        segmenttab.setTabData(mTitles);
        viewpager.setAdapter(new MyPagerAdapter(getChildFragmentManager()));
        segmenttab.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                viewpager.setCurrentItem(position);
            }

            @Override
            public void onTabReselect(int position) {
            }
        });

        viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                segmenttab.setCurrentTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private class MyPagerAdapter extends FragmentPagerAdapter {
        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles[position];
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }
    }
}
