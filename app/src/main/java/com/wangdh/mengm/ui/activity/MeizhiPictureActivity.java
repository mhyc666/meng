package com.wangdh.mengm.ui.activity;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
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
import com.wangdh.mengm.bean.MeizhiData;
import com.wangdh.mengm.component.AppComponent;
import com.wangdh.mengm.component.DaggerActivityComponent;
import com.wangdh.mengm.ui.Presenter.MeizhiPresenter;
import com.wangdh.mengm.ui.adapter.MeizhiAdapter;
import com.wangdh.mengm.ui.contract.MeizhiContract;
import com.wangdh.mengm.utils.NetworkUtil;
import com.wangdh.mengm.utils.RecyclerViewUtil;
import com.wangdh.mengm.utils.ToolbarUtils;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import butterknife.BindView;

public class MeizhiPictureActivity extends BaseActivity implements MeizhiContract.View, BaseQuickAdapter.RequestLoadMoreListener {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recycler)
    RecyclerView recycler;
    @BindView(R.id.swipe)
    SwipeRefreshLayout mSwipe;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    private List<MeizhiData.ResultsBean> mData = new ArrayList<>();
    @Inject
    MeizhiPresenter mPresenter;
    private MeizhiAdapter adapter;
    private int page = 1;
    private View errorView;
    private boolean isRefresh;

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
            isRefresh=true;
            page = 1;
            mPresenter.getMeiZhiData("福利", page);
        });
        mSwipe.setRefreshing(true);
        ToolbarUtils.initTitle(toolbar, R.mipmap.ab_back, "妹子图片", this);
        adapter = new MeizhiAdapter(mData);
        adapter.setOnLoadMoreListener(this, recycler);
        RecyclerViewUtil.StaggeredGridinit(recycler, adapter);
        adapter.setOnItemChildClickListener((adapter1, view, position) -> {
            Intent intent = new Intent(this, PicturesActivity.class);
            intent.putExtra("IMG_NAME", mData.get(position).get_id());
            intent.putExtra("IMG_URL", mData.get(position).getUrl());
            ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(
                    this,
                    view.findViewById(R.id.iv_view),
                    getString(R.string.transition_pinchimageview)
            );
            ActivityCompat.startActivity(getContext(), intent, optionsCompat.toBundle());
        });
        errorView = LayoutInflater.from(getContext()).inflate(R.layout.error_view, (ViewGroup) recycler.getParent(), false);
        errorView.setOnClickListener(v -> {
            mSwipe.setRefreshing(true);
            mPresenter.getMeiZhiData("福利", page);
        });
        fab.setOnClickListener(v -> recycler.scrollToPosition(0));
    }

    @Override
    protected void initData() {
        mPresenter.attachView(this);
        mPresenter.getMeiZhiData("福利", page);
    }



    @Override
    public void showError(String s) {
        toast(s);
        mSwipe.setRefreshing(false);
        adapter.loadMoreEnd();
        adapter.setEmptyView(getErrorView());
    }

    @Override
    public void complete() {
        try {
            mSwipe.setRefreshing(false);
        }catch (NullPointerException e){
            e.getMessage();
        }
    }

    @Override
    public void showMeizhiData(MeizhiData data) {
        if(isRefresh){
            mData.clear();
        }
        isRefresh=false;
        mData.addAll(data.getResults());
        adapter.notifyDataSetChanged();
        adapter.loadMoreComplete();
    }
    public View getErrorView() {
        isRefresh=false;
        return errorView;
    }
//    private void setDataRefresh(boolean refresh) {
//        if (refresh) {
//            mSwipe.setRefreshing(refresh);
//        } else {
//                runnable = () -> mSwipe.setRefreshing(refresh);
//                mSwipe.postDelayed(runnable, 1000);//延时消失加载的loading
//        }
//    }

    @Override
    public void onLoadMoreRequested() {
        if (mData.size() >= 20) {
            recycler.postDelayed(() -> {
                if (NetworkUtil.isAvailable(this)) {
                    page=page+1;
                    mPresenter.getMeiZhiData("福利", page);
                } else {
                    //获取更多数据失败
                    adapter.loadMoreFail();
                }
            }, 1000);
        } else {
            adapter.loadMoreEnd();
        }
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
//        if (mPresenter != null) {
//            mPresenter.detachView();
//        }

    }
}
