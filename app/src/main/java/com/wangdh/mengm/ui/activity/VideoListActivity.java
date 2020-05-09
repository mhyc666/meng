package com.wangdh.mengm.ui.activity;

import android.content.Intent;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.wangdh.mengm.R;
import com.wangdh.mengm.base.BaseActivity;
import com.wangdh.mengm.bean.VideoData;
import com.wangdh.mengm.component.AppComponent;
import com.wangdh.mengm.component.DaggerActivityComponent;
import com.wangdh.mengm.ui.Presenter.VideoListPresenter;
import com.wangdh.mengm.ui.adapter.VideoAdapter;
import com.wangdh.mengm.ui.contract.VideoListContract;
import com.wangdh.mengm.utils.NetworkUtil;
import com.wangdh.mengm.utils.RecyclerViewUtil;
import com.wangdh.mengm.utils.ToolbarUtils;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;


public class VideoListActivity extends BaseActivity implements VideoListContract.View, BaseQuickAdapter.RequestLoadMoreListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recycler)
    RecyclerView recycler;
    @BindView(R.id.swipe)
    SwipeRefreshLayout mSwipe;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    private List<VideoData.ShowapiResBodyBean.PagebeanBean.ContentlistBean> mData = new ArrayList<>();
    @Inject
    VideoListPresenter mPresenter;
    private VideoAdapter adapter;
    private int page = 1;
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
        mSwipe.setColorSchemeResources(R.color.colorPrimaryDark2, R.color.btn_blue, R.color.ywlogin_colorPrimaryDark);//设置进度动画的颜色
        mSwipe.setProgressViewOffset(true, 0, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources().getDisplayMetrics()));
        mSwipe.setOnRefreshListener(() -> {
            mData.clear();
            page = 1;
            mPresenter.getVideolistData(String.valueOf(page));
        });
        setDataRefresh(true);
        ToolbarUtils.initTitle(toolbar, R.mipmap.ab_back, getResources().getString(R.string.sping), this);
        adapter = new VideoAdapter(mData);
        adapter.setOnLoadMoreListener(this, recycler);
        RecyclerViewUtil.initNoDecoration(this, recycler, adapter);
        adapter.setOnItemChildClickListener((adapter1, view, position) -> {
            String url = mData.get(position).getVideo_uri();
            Intent intent = new Intent(this, VideoActivity.class);
            intent.putExtra("videourl", url);
            startActivity(intent);
            overridePendingTransition(R.anim.fade_entry, R.anim.hold);
        });
        errorView = LayoutInflater.from(getContext()).inflate(R.layout.error_view, (ViewGroup) recycler.getParent(), false);
        errorView.setOnClickListener(v -> {
            mSwipe.setRefreshing(true);
            mPresenter.getVideolistData(String.valueOf(page));
        });
        fab.setOnClickListener(v -> recycler.scrollToPosition(0));
    }

    @Override
    protected void initData() {
        mPresenter.attachView(this);
        mPresenter.getVideolistData(String.valueOf(page));

    }

    @Override
    public void showError(String s) {
        setDataRefresh(false);
        adapter.loadMoreEnd();
        toast(s);
        adapter.setEmptyView(getErrorView());
    }

    @Override
    public void complete() {
        setDataRefresh(false);
    }

    @Override
    public void showVideolistData(VideoData data) {
        mData.addAll(data.getShowapi_res_body().getPagebean().getContentlist());
        adapter.notifyDataSetChanged();
        adapter.loadMoreComplete();
    }


    @Override
    public void onLoadMoreRequested() {
        if (mData.size() >= 20) {
            recycler.postDelayed(() -> {
                if (NetworkUtil.isAvailable(getContext())) {
                    page = page + 1;
                    mPresenter.getVideolistData(String.valueOf(page));
                } else {
                    //获取更多数据失败
                    adapter.loadMoreFail();
                }
            }, 1000);
        } else {
            adapter.loadMoreEnd();
        }

    }

    private void setDataRefresh(boolean refresh) {
        if (refresh) {
            mSwipe.setRefreshing(refresh);
        } else {
            try {
                new Handler().postDelayed(() -> mSwipe.setRefreshing(refresh), 1000);//延时消失加载的loading
            }catch (NullPointerException e){
                e.getMessage();
            }

        }
    }

    public View getErrorView() {
        return errorView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
//        if (mPresenter != null) {
//            mPresenter.detachView();
//        }
    }
}
