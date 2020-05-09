package com.wangdh.mengm.ui.fragment;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wangdh.mengm.R;
import com.wangdh.mengm.base.BaseFragment;
import com.wangdh.mengm.bean.HealthitemData;
import com.wangdh.mengm.component.AppComponent;
import com.wangdh.mengm.component.DaggerFragmentComponent;
import com.wangdh.mengm.ui.Presenter.HealthFragmentPresenter;
import com.wangdh.mengm.ui.activity.HealthListActivity;
import com.wangdh.mengm.ui.adapter.HealthFragmentAdapter;
import com.wangdh.mengm.ui.contract.HealthFragmentContract;
import com.wangdh.mengm.utils.RecyclerViewUtil;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import butterknife.BindView;


public class HealthFragment extends BaseFragment implements HealthFragmentContract.View {
    @BindView(R.id.recycler_health)
    RecyclerView recycler;
    @Inject
    HealthFragmentPresenter mPresenter;
    private HealthFragmentAdapter adapter;
    private List<HealthitemData.ShowapiResBodyBean.ListBean> itemdata = new ArrayList<>();
    private View errorView;
    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerFragmentComponent.builder()
                .appComponent(appComponent)
                .build()
                .inject(this);
    }

    @Override
    protected int setLayoutResourceID() {
        return R.layout.fragment_health;
    }

    @Override
    protected void initData() {
        showDialog();
        mPresenter.attachView(this);
        mPresenter.getHealthData();
    }

    @Override
    protected void initView() {
        adapter=new HealthFragmentAdapter(itemdata);
        RecyclerViewUtil.Gridinit(getContext(),recycler,adapter,2);
        adapter.setOnItemChildClickListener((adapter1, view, position) -> {
            Intent intent=new Intent(getActivity(),HealthListActivity.class);
            intent.putExtra("tid",adapter.getItem(position).getId());
            intent.putExtra("name",adapter.getItem(position).getName());
            startActivity(intent);
        });
        errorView = LayoutInflater.from(getContext()).inflate(R.layout.error_view, (ViewGroup) recycler.getParent(), false);
        errorView.setOnClickListener(v -> mPresenter.getHealthData());
    }

    @Override
    public void showError(String s) {
        hideDialog();
        toast(s);
        adapter.setEmptyView(getErrorView());
    }

    @Override
    public void complete() {
        dismissDialog();
    }

    @Override
    public void showHealthData(HealthitemData data) {
        itemdata.addAll(data.getShowapi_res_body().getList());
        adapter.notifyDataSetChanged();
    }
    public View getErrorView() {
        return errorView;
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        if(mPresenter!=null){
            mPresenter.detachView();
        }
    }
}
