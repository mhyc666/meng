package com.wangdh.mengm.ui.fragment;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import com.wangdh.mengm.R;
import com.wangdh.mengm.base.BaseFragment;
import com.wangdh.mengm.bean.CookBooksData;
import com.wangdh.mengm.component.AppComponent;
import com.wangdh.mengm.component.DaggerFragmentComponent;
import com.wangdh.mengm.ui.Presenter.CookBooksPresenter;
import com.wangdh.mengm.ui.activity.CookBooksActivity;
import com.wangdh.mengm.ui.activity.FunnyPicturesActivity;
import com.wangdh.mengm.ui.activity.JokeActivity;
import com.wangdh.mengm.ui.activity.SearchCookBooksActivity;
import com.wangdh.mengm.ui.adapter.CookBooksAdapter;
import com.wangdh.mengm.ui.contract.CookBooksContract;
import com.wangdh.mengm.utils.RecyclerViewUtil;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import butterknife.BindView;

public class CookBooksFragment extends BaseFragment implements CookBooksContract.View {
    @BindView(R.id.recycler)
    RecyclerView recycler;
    @BindView(R.id.swipe)
    SwipeRefreshLayout mSwipe;
    @Inject
    CookBooksPresenter mPresenter;
    private CookBooksAdapter adapter;
    private List<CookBooksData.ResultBeanX.ResultBean> itemdata = new ArrayList<>();
    private View headerView;
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
        showDialog();
        mPresenter.attachView(this);
        mPresenter.getCookBooksData();
    }

    @Override
    protected void initView() {
        mSwipe.setColorSchemeResources(R.color.colorPrimaryDark2, R.color.btn_blue, R.color.ywlogin_colorPrimaryDark);//设置进度动画的颜色
        mSwipe.setProgressViewOffset(true, 0, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources().getDisplayMetrics()));
        mSwipe.setOnRefreshListener(() -> {
            itemdata.clear();
            mPresenter.getCookBooksData();
        });
        adapter = new CookBooksAdapter(itemdata);
        adapter.setHeaderView(getHeaderView());
        RecyclerViewUtil.initNoDecoration(getContext(), recycler, adapter);
        LinearLayout mll= (LinearLayout) headerView.findViewById(R.id.xiaoh);
        LinearLayout mll2= (LinearLayout) headerView.findViewById(R.id.qutu);
        EditText mEt= (EditText) headerView.findViewById(R.id.edit);
        mll.setOnClickListener(v -> startActivity(new Intent(getActivity(),JokeActivity.class)));
        mll2.setOnClickListener(v -> startActivity(new Intent(getActivity(),FunnyPicturesActivity.class)));
        mEt.setOnClickListener(v -> startActivity(new Intent(getActivity(),SearchCookBooksActivity.class)));
        adapter.setOnItemChildClickListener((adapter1, view, position) ->
           CookBooksActivity.startActivity(getActivity(), adapter.getItem(position))
        );
    }

    @Override
    public void showError(String s) {
        mSwipe.setRefreshing(false);
        hideDialog();
        toast(s);

    }

    @Override
    public void complete() {
        mSwipe.setRefreshing(false);
       dismissDialog();
    }

    @Override
    public void showCookBooksData(CookBooksData data) {
        itemdata.addAll(data.getResult().getResult());
        adapter.notifyDataSetChanged();
    }
    public View getHeaderView() {
        headerView = LayoutInflater.from(getContext()).inflate(R.layout.cookbook_headerview,(ViewGroup)recycler.getParent(),false);
        return headerView;
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        if(mPresenter!=null){
            mPresenter.detachView();
        }
    }
}
