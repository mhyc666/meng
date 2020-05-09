package com.wangdh.mengm.ui.activity;

import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.wangdh.mengm.R;
import com.wangdh.mengm.base.BaseActivity;
import com.wangdh.mengm.component.AppComponent;
import com.wangdh.mengm.manager.EventManager;
import com.wangdh.mengm.ui.adapter.SearchHistoryAdapter;
import com.wangdh.mengm.utils.SharedPreferencesMgr;
import com.wangdh.mengm.utils.StateBarTranslucentUtils;
import com.wangdh.mengm.utils.ToolbarUtils;
import com.wangdh.mengm.widget.TagColor;
import com.wangdh.mengm.widget.TagGroup;
import org.greenrobot.eventbus.EventBus;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class SearchWeatherActivity extends BaseActivity {

    @BindView(R.id.tvChangeWords)
    TextView mTvChangeWords;
    @BindView(R.id.tag_group)
    TagGroup mTagGroup;
    @BindView(R.id.rootLayout)
    LinearLayout mRootLayout;
    @BindView(R.id.layoutHotWord)
    RelativeLayout mLayoutHotWord;
    @BindView(R.id.rlHistory)
    RelativeLayout rlHistory;
    @BindView(R.id.tvClear)
    TextView tvClear;
    @BindView(R.id.lvSearchHistory)
    ListView lvSearchHistory;
    @BindView(R.id.common_toolbar)
    Toolbar mCommonToolbar;
    private MenuItem searchMenuItem;
    private SearchView searchView;
    private SearchHistoryAdapter mHisAdapter;
    private List<String> mHisList = new ArrayList<>();
    private String key;
    private int hotIndex = 0;
    private List<String> tagList = new ArrayList<>();
    @Override
    protected void setupActivityComponent(AppComponent appComponent) {

    }

    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_search;
    }

    @Override
    protected void initView() {
        ToolbarUtils.initTitle(mCommonToolbar, R.mipmap.ab_back, "城市查询", this);
        mTagGroup.setOnTagClickListener(tag -> search(tag));
    }

    @Override
    protected void initData() {
        mHisAdapter = new SearchHistoryAdapter(this, mHisList);
        lvSearchHistory.setAdapter(mHisAdapter);
        lvSearchHistory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                search(mHisList.get(position));
            }
        });
        initSearchHistory();

        showHotWord();
    }

    private void showHotWord() {
        tagList.add("北京");
        tagList.add("上海");
        tagList.add("广州");
        tagList.add("杭州");
        tagList.add("深圳");
        tagList.add("成都");
        tagList.add("南京");
        tagList.add("重庆");
        tagList.add("西安");
        tagList.add("郑州");
        int tagSize = 10;
        String[] tags = new String[tagSize];
        for (int j = 0; j < tagSize && j < tagList.size(); hotIndex++, j++) {
            tags[j] = tagList.get(hotIndex % tagList.size());
        }
        List<TagColor> colors = TagColor.getRandomColors(tagSize);
        mTagGroup.setTags(colors, tags);
    }

    @OnClick(R.id.tvClear)
    public void clearSearchHistory() {
        SaveSearchHistory(null);
        initSearchHistory();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);
        searchMenuItem = menu.findItem(R.id.action_search);//在菜单中找到对应控件的item
        searchView = (SearchView) MenuItemCompat.getActionView(searchMenuItem);
        searchView.setQueryHint("输入您需要查询的城市...");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                key = query;
                saveSearchHistory(query);
                EventBus.getDefault().post(new EventManager(query));
                SearchWeatherActivity.this.finish();
                overridePendingTransition(R.anim.fade_exit, R.anim.hold);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                return false;
            }
        });
        search(key); // 外部调用搜索，则打开页面立即进行搜索
        MenuItemCompat.setOnActionExpandListener(searchMenuItem,
                new MenuItemCompat.OnActionExpandListener() {//设置打开关闭动作监听
                    @Override
                    public boolean onMenuItemActionExpand(MenuItem item) {

                        return true;
                    }

                    @Override
                    public boolean onMenuItemActionCollapse(MenuItem item) {

                        return true;
                    }
                });
        return true;
    }

    /**
     * 展开SearchView进行查询
     *
     * @param key
     */
    private void search(String key) {
        MenuItemCompat.expandActionView(searchMenuItem);
        if (!TextUtils.isEmpty(key)) {
            searchView.setQuery(key, true);
            saveSearchHistory(key);
        }
    }

    /**
     * 保存搜索记录.不重复，最多保存20条
     *
     * @param query
     */
    private void saveSearchHistory(String query) {
        List<String> list = getSearchHistory();
        if (list == null) {
            list = new ArrayList<>();
            list.add(query);
        } else {
            Iterator<String> iterator = list.iterator();
            while (iterator.hasNext()) {
                String item = iterator.next();
                if (TextUtils.equals(query, item)) {
                    iterator.remove();
                }
            }
            list.add(0, query);
        }
        int size = list.size();
        if (size > 20) { // 最多保存20条
            for (int i = size - 1; i >= 20; i--) {
                list.remove(i);
            }
        }
        SaveSearchHistory(list);
        initSearchHistory();
    }

    private void initSearchHistory() {
        List<String> list = getSearchHistory();
        mHisAdapter.clear();
        if (list != null && list.size() > 0) {
            tvClear.setEnabled(true);
            mHisAdapter.addAll(list);
        } else {
            tvClear.setEnabled(false);
        }
        mHisAdapter.notifyDataSetChanged();
    }

    public List<String> getSearchHistory() { //查找历史
        return SharedPreferencesMgr.getInstance().getObject(getSearchHistoryKey(), List.class);
    }

    public void SaveSearchHistory(Object obj) {  //保存历史
        SharedPreferencesMgr.getInstance().putObject(getSearchHistoryKey(), obj);
    }

    private String getSearchHistoryKey() {
        return "searchWeather";
    }
}
