package com.wangdh.mengm.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;
import com.wangdh.mengm.R;
import com.wangdh.mengm.base.BaseActivity;
import com.wangdh.mengm.bean.CookBookslistData;
import com.wangdh.mengm.component.AppComponent;
import com.wangdh.mengm.ui.adapter.CookBooksDetailsAdapter;
import com.wangdh.mengm.ui.adapter.CookBooksDetailsAdapter2;
import com.wangdh.mengm.utils.MyGlideImageLoader;
import com.wangdh.mengm.utils.RecyclerViewUtil;
import com.wangdh.mengm.utils.ToolbarUtils;

import butterknife.BindView;

public class CookBooksDetails extends BaseActivity {
    public static final String INTENT_BEAN = "listBean";
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.image)
    ImageView image;
    @BindView(R.id.text)
    TextView text;
    @BindView(R.id.coll_layout)
    CollapsingToolbarLayout collLayout;
    @BindView(R.id.recyclerView1)
    RecyclerView recyclerView1;
    @BindView(R.id.recyclerView2)
    RecyclerView recyclerView2;
    private CookBooksDetailsAdapter adapter;
    private CookBooksDetailsAdapter2 adapter2;
    private CookBookslistData.ResultBeanX.ResultBean.ListBean data;

    public static void startActivity(Context context, CookBookslistData.ResultBeanX.ResultBean.ListBean listBean) {
        context.startActivity(new Intent(context, CookBooksDetails.class)
                .putExtra(INTENT_BEAN, listBean));
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {

    }

    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_cookbooksdetails;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        data = (CookBookslistData.ResultBeanX.ResultBean.ListBean) getIntent().getSerializableExtra(INTENT_BEAN);
        if (data == null) {
            toast("数据加载失败");
            ToolbarUtils.initTitle(toolbar, R.mipmap.ab_back, "菜谱详情", this);
        } else {
            ToolbarUtils.initTitle(toolbar, R.mipmap.ab_back, data.getName(), this);
            MyGlideImageLoader.displayImage(data.getPic(),image);
            text.setText(data.getContent());
            adapter=new CookBooksDetailsAdapter(data.getMaterial());
            adapter2=new CookBooksDetailsAdapter2(data.getProcess());
            RecyclerViewUtil.initHorizontal(this,recyclerView1,adapter);
            RecyclerViewUtil.init(this,recyclerView2,adapter2);
        }
    }

}
