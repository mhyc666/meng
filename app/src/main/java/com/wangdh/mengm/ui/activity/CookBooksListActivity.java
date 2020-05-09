package com.wangdh.mengm.ui.activity;

import android.content.Intent;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.wangdh.mengm.R;
import com.wangdh.mengm.base.BaseActivity;
import com.wangdh.mengm.bean.CookBookslistData;
import com.wangdh.mengm.component.AppComponent;
import com.wangdh.mengm.component.DaggerActivityComponent;
import com.wangdh.mengm.ui.Presenter.CookBooksListPresenter;
import com.wangdh.mengm.ui.adapter.CookBooksItemAdapter;
import com.wangdh.mengm.ui.contract.CookBooksListContract;
import com.wangdh.mengm.utils.NetworkUtil;
import com.wangdh.mengm.utils.RecyclerViewUtil;
import com.wangdh.mengm.utils.ToolbarUtils;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import butterknife.BindView;

public class CookBooksListActivity extends BaseActivity implements CookBooksListContract.View, BaseQuickAdapter.RequestLoadMoreListener {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recycler)
    RecyclerView recycler;
    @BindView(R.id.swipe)
    SwipeRefreshLayout mSwipe;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    private List<CookBookslistData.ResultBeanX.ResultBean.ListBean> itemdata = new ArrayList<>();
    private CookBooksItemAdapter adapter;
    @Inject
    CookBooksListPresenter mPresenter;
    private int start = 0, num = 20;
    private String classid;
    private View errorView;
    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerActivityComponent.builder()
                .appComponent(appComponent)
                .build()
                .inject(this);
    }

    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_recyclerview;
    }

    @Override
    protected void initView() {
        ToolbarUtils.initTitle(toolbar, R.mipmap.ab_back, "菜单列表", this);
        mSwipe.setColorSchemeResources(R.color.colorPrimaryDark2, R.color.btn_blue, R.color.ywlogin_colorPrimaryDark);//设置进度动画的颜色
        mSwipe.setProgressViewOffset(true, 0, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources().getDisplayMetrics()));
        mSwipe.setOnRefreshListener(() -> {
            itemdata.clear();
            start = 0;
            mPresenter.getCookBooksListData(classid, String.valueOf(num), String.valueOf(start));
        });
        setDataRefresh(true);
        adapter = new CookBooksItemAdapter(itemdata);
        adapter.setOnLoadMoreListener(this, recycler);
        adapter.openLoadAnimation();
        RecyclerViewUtil.init(this, recycler, adapter);
        fab.setOnClickListener(v -> recycler.scrollToPosition(0));
        adapter.setOnItemChildClickListener((adapter1, view, position) ->
                CookBooksDetails.startActivity(this, adapter.getItem(position))
        );

        errorView = LayoutInflater.from(getContext()).inflate(R.layout.error_view, (ViewGroup) recycler.getParent(), false);
        errorView.setOnClickListener(v -> {setDataRefresh(true);
            mPresenter.getCookBooksListData(classid, String.valueOf(num), String.valueOf(start));});
    }

    @Override
    protected void initData() {
        classid = getIntent().getStringExtra("classid");
        mPresenter.attachView(this);
        mPresenter.getCookBooksListData(classid, String.valueOf(num), String.valueOf(start));
    }

    private void setDataRefresh(boolean refresh) {
        if (refresh) {
            mSwipe.setRefreshing(refresh);
        } else {
            new Handler().postDelayed(() -> {
                try {
                    mSwipe.setRefreshing(refresh);
                } catch (NullPointerException e) {
                    e.getMessage();
                }
            }, 1000);//延时消失加载的loading
        }
    }

    @Override
    public void showError(String s) {
        toast(s);
        setDataRefresh(false);
        adapter.loadMoreFail();
        adapter.setEmptyView(getErrorView());
    }

    @Override
    public void complete() {
        setDataRefresh(false);
        adapter.loadMoreComplete();
    }

    @Override
    public void showCookBooksListData(CookBookslistData data) {
        if (data.getResult().getResult().getList().size() != 0) {
            itemdata.addAll(data.getResult().getResult().getList());
            adapter.notifyDataSetChanged();
        } else {
            toast("数据加载失败");
        }

    }

    @Override
    public void onLoadMoreRequested() {
        if (itemdata.size() >= 20) {
            recycler.postDelayed(() -> {
                if (NetworkUtil.isAvailable(getContext())) {
                    start = start + 20;
                    mPresenter.getCookBooksListData(classid, String.valueOf(num), String.valueOf(start));
                } else {
                    //获取更多数据失败
                    adapter.loadMoreFail();
                }
            }, 1000);
        } else {
            adapter.loadMoreEnd();
        }
    }
    public View getErrorView() {
        return errorView;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.searchmenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_search) {
            startActivity(new Intent(this, SearchCookBooksActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }
}
