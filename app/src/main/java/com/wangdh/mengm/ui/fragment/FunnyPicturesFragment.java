package com.wangdh.mengm.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.wangdh.mengm.R;
import com.wangdh.mengm.base.BaseFragment;
import com.wangdh.mengm.bean.FunnyPicturesData;
import com.wangdh.mengm.component.AppComponent;
import com.wangdh.mengm.component.DaggerFragmentComponent;
import com.wangdh.mengm.ui.Presenter.FunnyPicturesPresenter;
import com.wangdh.mengm.ui.activity.PicturesActivity;
import com.wangdh.mengm.ui.adapter.FunnyPicturesApapter;
import com.wangdh.mengm.ui.contract.FunnyPicturesFragmentContract;
import com.wangdh.mengm.utils.NetworkUtil;
import com.wangdh.mengm.utils.RecyclerViewUtil;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import butterknife.BindView;

public class FunnyPicturesFragment extends BaseFragment implements FunnyPicturesFragmentContract.View, BaseQuickAdapter.RequestLoadMoreListener {
    private static final String ARG_PARAM1 = "param";
    @BindView(R.id.recycler)
    RecyclerView recycler;
    @BindView(R.id.swipe)
    SwipeRefreshLayout mSwipe;
    private String mParam;
    private FunnyPicturesApapter adapter;
    private List<FunnyPicturesData.ShowapiResBodyBean.ContentlistBean> mData = new ArrayList<>();
    private int page = 1;
    @Inject
    FunnyPicturesPresenter mPresenter;

    public static FunnyPicturesFragment newInstance(String param) {
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param);
        FunnyPicturesFragment fragment = new FunnyPicturesFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam = getArguments().getString(ARG_PARAM1);
        }
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerFragmentComponent.builder()
                .appComponent(appComponent)
                .build()
                .inject(this);
    }

    @Override
    protected int setLayoutResourceID() {
        return R.layout.fragment_recyclerview;
    }

    @Override
    protected void initData() {
        mPresenter.attachView(this);
        showDialog();
        mPresenter.getFunnyPicturesData(mParam, String.valueOf(page));
    }

    @Override
    protected void initView() {
        mSwipe.setColorSchemeResources(R.color.colorPrimaryDark2, R.color.btn_blue, R.color.ywlogin_colorPrimaryDark);//设置进度动画的颜色
        mSwipe.setProgressViewOffset(true, 0, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources().getDisplayMetrics()));
        mSwipe.setOnRefreshListener(() -> {
            mData.clear();
            page = 1;
            mPresenter.getFunnyPicturesData(mParam, String.valueOf(page));
        });
        adapter = new FunnyPicturesApapter(mData);
        RecyclerViewUtil.StaggeredGridinit(recycler, adapter);
        adapter.setOnLoadMoreListener(this, recycler);
        adapter.setOnItemChildClickListener((adapter1, view, position) -> {
            FunnyPicturesData.ShowapiResBodyBean.ContentlistBean bean = mData.get(position);
            Intent intent = new Intent(getActivity(), PicturesActivity.class);
            intent.putExtra("IMG_NAME", bean.getTitle());
            intent.putExtra("IMG_URL", bean.getImg());
            ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(
                    getActivity(),
                    view.findViewById(R.id.iv_view),
                    getString(R.string.transition_pinchimageview)
            );
            ActivityCompat.startActivity(getContext(), intent, optionsCompat.toBundle());
        });
    }

    @Override
    public void showError(String s) {
        hideDialog();
        if(mSwipe!=null){
            mSwipe.setRefreshing(false);
        }else {
            throw new NullPointerException("swipeRefreshLayout not null");
        }
        adapter.loadMoreEnd();
        toast(s);
    }

    @Override
    public void complete() {
        mSwipe.setRefreshing(false);
        dismissDialog();
        adapter.loadMoreComplete();
    }

    @Override
    public void showFunnyPicturesData(FunnyPicturesData data) {
        mData.addAll(data.getShowapi_res_body().getContentlist());
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    @Override
    public void onLoadMoreRequested() {
        if (mData.size() >= 20) {
            recycler.postDelayed(() -> {
                if (page>=1) {
                    page=page+1;
                    mPresenter.getFunnyPicturesData(mParam, String.valueOf(page++));
                } else {
                    //获取更多数据失败
                    adapter.loadMoreFail();
                }
            }, 1000);
        } else {
            adapter.loadMoreEnd();
        }
    }
}
