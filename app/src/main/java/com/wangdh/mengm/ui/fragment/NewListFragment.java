package com.wangdh.mengm.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.wangdh.mengm.R;
import com.wangdh.mengm.base.BaseFragment;
import com.wangdh.mengm.bean.NewListData;
import com.wangdh.mengm.component.AppComponent;
import com.wangdh.mengm.component.DaggerFragmentComponent;
import com.wangdh.mengm.ui.Presenter.NewListPresenter;
import com.wangdh.mengm.ui.activity.WebViewDetailsActivity;
import com.wangdh.mengm.ui.adapter.NewItemAdapter;
import com.wangdh.mengm.ui.contract.NewListContract;
import com.wangdh.mengm.utils.NetworkUtil;
import com.wangdh.mengm.utils.RecyclerViewUtil;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import butterknife.BindView;

public class NewListFragment extends BaseFragment implements NewListContract.View, BaseQuickAdapter.RequestLoadMoreListener {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    @BindView(R.id.recycler)
    RecyclerView recycler;
    @BindView(R.id.swipe)
    SwipeRefreshLayout mSwipe;
    @BindView(R.id.fab)
    FloatingActionButton mFab;
    private String mParam1;
    private String mParam2;
    private List<NewListData.ResultBeanX.ResultBean.ListBean> itemdata = new ArrayList<>();
    private NewItemAdapter adapter;
    private int start = 0, num = 20;
    @Inject
    NewListPresenter mPresenter;
    private View errorView;

    public static NewListFragment newInstance(String param1, String param2) {
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        NewListFragment fragment = new NewListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
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
        return R.layout.fragment_newlist;
    }

    @Override
    protected void initData() {
        mPresenter.attachView(this);
        mPresenter.getNewlistData(mParam1, String.valueOf(num), String.valueOf(start));
    }

    @Override
    protected void initView() {
        mSwipe.setColorSchemeResources(R.color.colorPrimaryDark2, R.color.btn_blue, R.color.ywlogin_colorPrimaryDark);//设置进度动画的颜色
        mSwipe.setProgressViewOffset(true, 0, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources().getDisplayMetrics()));
        mSwipe.setOnRefreshListener(() -> {
            itemdata.clear();
            mPresenter.getNewlistData(mParam1, String.valueOf(num), String.valueOf(start));
        });
        setDataRefresh(true);
        adapter = new NewItemAdapter(itemdata);
        RecyclerViewUtil.init(getContext(), recycler, adapter);
        adapter.setOnLoadMoreListener(this, recycler);
        adapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_BOTTOM);
        adapter.setOnItemChildClickListener((adapter1, view, position) -> {
            String url = itemdata.get(position).getUrl();
            Intent intent = new Intent(getContext(), WebViewDetailsActivity.class);
            intent.putExtra("wechaturl", url);
            startActivity(intent);
        });
        errorView = LayoutInflater.from(getContext()).inflate(R.layout.error_view, (ViewGroup) recycler.getParent(), false);
        errorView.setOnClickListener(v -> {setDataRefresh(true);
                mPresenter.getNewlistData(mParam1, String.valueOf(num), String.valueOf(start));});
        mFab.setOnClickListener(v -> recycler.scrollToPosition(0));
    }

    private void setDataRefresh(boolean refresh) {
        if (refresh) {
            mSwipe.setRefreshing(refresh);
        } else {
            try{
                new Handler().postDelayed(() -> mSwipe.setRefreshing(refresh), 600);//延时消失加载的loading
            }catch (NullPointerException e){
                e.getMessage();
            }
        }
    }

    @Override
    public void showError(String s) {
        setDataRefresh(false);
        adapter.loadMoreEnd();
        adapter.setEmptyView(getErrorView());
       // toast(s);
    }

    @Override
    public void complete() {
        setDataRefresh(false);
    }

    @Override
    public void showNewlistData(NewListData data) {
        itemdata.addAll(data.getResult().getResult().getList());
        adapter.notifyDataSetChanged();
        adapter.loadMoreComplete();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    @Override
    public void onLoadMoreRequested() {
        if (itemdata.size() >= 20) {
            recycler.postDelayed(() -> {
                if (NetworkUtil.isAvailable(getContext())) {
                    start=start+20;
                    mPresenter.getNewlistData(mParam1, String.valueOf(num), String.valueOf(start));
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
}
