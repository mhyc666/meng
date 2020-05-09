package com.wangdh.mengm.ui.fragment;

import android.content.DialogInterface;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wangdh.mengm.R;
import com.wangdh.mengm.base.BaseFragment;
import com.wangdh.mengm.base.ConstanceValue;
import com.wangdh.mengm.bean.Channel;
import com.wangdh.mengm.component.AppComponent;
import com.wangdh.mengm.dialog.ChannelDialogFragment;
import com.wangdh.mengm.listener.OnChannelListener;
import com.wangdh.mengm.utils.SharedPreferencesMgr;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;

/**
 * 新闻头条
 */

public class NewFragment extends BaseFragment implements OnChannelListener {
    private String TITLE_SELECTED = "explore_title_selected";
    private String TITLE_UNSELECTED = "explore_title_unselected";
    @BindView(R.id.tablayout)
    TabLayout tablayout;
    @BindView(R.id.add_btn)
    ImageView addBtn;
    @BindView(R.id.viewpage)
    ViewPager mViewPager;
    private List<Fragment> mFragments;
    public List<Channel> mSelectedDatas = new ArrayList<>();
    public List<Channel> mUnSelectedDatas = new ArrayList<>();
    private Gson mGson = new Gson();
    private ViewPagerAdapter adapter;

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {

    }
    @Override
    protected int setLayoutResourceID() {
        return R.layout.fragment_new;
    }

    @Override
    protected void initData() {
        String selectTitle = SharedPreferencesMgr.getString(TITLE_SELECTED, "");
        String unselectTitle = SharedPreferencesMgr.getString(TITLE_UNSELECTED, "");
        if (TextUtils.isEmpty(selectTitle) || TextUtils.isEmpty(unselectTitle)) {
            String[] titleStr = getResources().getStringArray(R.array.category_name);
            String[] titlesCode = getResources().getStringArray(R.array.category_type);
            //默认添加了全部频道
            for (int i = 0; i < titlesCode.length; i++) {
                String t = titleStr[i];
                String code = titlesCode[i];
                mSelectedDatas.add(new Channel(t, code));
            }
            String selectedStr = mGson.toJson(mSelectedDatas);
            SharedPreferencesMgr.setString(TITLE_SELECTED, selectedStr);
        } else {
            //之前添加过
            List<Channel> selecteData = mGson.fromJson(selectTitle, new TypeToken<List<Channel>>() {
            }.getType());
            List<Channel> unselecteData = mGson.fromJson(unselectTitle, new TypeToken<List<Channel>>() {
            }.getType());
            mSelectedDatas.addAll(selecteData);
            mUnSelectedDatas.addAll(unselecteData);
        }
    }

    @Override
    protected void initView() {
        mFragments = new ArrayList<>();
        for (int i = 0; i < mSelectedDatas.size(); i++) {
            mFragments.add(NewListFragment.newInstance(mSelectedDatas.get(i).Title, mSelectedDatas.get(i).TitleCode));
        }
        adapter=new ViewPagerAdapter(getFragmentManager());
        mViewPager.setAdapter(adapter);
        mViewPager.setOffscreenPageLimit(mFragments.size());
        tablayout.setupWithViewPager(mViewPager);/*使用ViewPager启动TabLayout*/
        /*一旦TabLayout的位置发生改变就会添加一个TabLayout上的页面选择监听器*/
        tablayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));
        /*无论页面发生变化或则反卷都会添加一个页面变化监听器*/
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tablayout));
        onclicked();
    }

    private void onclicked() {
        addBtn.setOnClickListener(v -> {
            ChannelDialogFragment dialogFragment = ChannelDialogFragment.newInstance(mSelectedDatas, mUnSelectedDatas);
            dialogFragment.setOnChannelListener(NewFragment.this);
            dialogFragment.show(getFragmentManager(), "CHANNEL");
            dialogFragment.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    adapter.notifyDataSetChanged();
                    mViewPager.setOffscreenPageLimit(mSelectedDatas.size());
                    //tab.setCurrentItem(tab.getSelectedTabPosition());

                    ViewGroup slidingTabStrip = (ViewGroup) tablayout.getChildAt(0);
                    //注意：因为最开始设置了最小宽度，所以重新测量宽度的时候一定要先将最小宽度设置为0
                    slidingTabStrip.setMinimumWidth(0);
                    slidingTabStrip.measure(0, 0);

                    //保存选中和未选中的channel
                    SharedPreferencesMgr.setString(ConstanceValue.TITLE_SELECTED, mGson.toJson(mSelectedDatas));
                    SharedPreferencesMgr.setString(ConstanceValue.TITLE_UNSELECTED, mGson.toJson(mUnSelectedDatas));
                }
            });
        });
    }

    @Override
    public void onItemMove(int starPos, int endPos) {
        listMove(mSelectedDatas, starPos, endPos);
        listMove(mFragments, starPos, endPos);
    }

    private void listMove(List datas, int starPos, int endPos) {
        Object o = datas.get(starPos);
        //先删除之前的位置
        datas.remove(starPos);
        //添加到现在的位置
        datas.add(endPos, o);
    }

    @Override
    public void onMoveToMyChannel(int starPos, int endPos) {
        //移动到我的频道
        Channel channel = mUnSelectedDatas.remove(starPos);
        mSelectedDatas.add(endPos, channel);
        mFragments.add(NewListFragment.newInstance(channel.Title, channel.TitleCode));
    }

    @Override
    public void onMoveToOtherChannel(int starPos, int endPos) {
        //移动到推荐频道
        mUnSelectedDatas.add(endPos, mSelectedDatas.remove(starPos));
        mFragments.remove(starPos);
    }


    public class ViewPagerAdapter extends FragmentStatePagerAdapter {

        ViewPagerAdapter(FragmentManager fm) {  //传值
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments == null ? 0 : mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mSelectedDatas == null ? "" : mSelectedDatas.get(position).Title;
        }
    }
}
