package com.wangdh.mengm.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;

import com.wangdh.mengm.R;
import com.wangdh.mengm.base.BaseActivity;
import com.wangdh.mengm.bean.CookBooksData;
import com.wangdh.mengm.component.AppComponent;
import com.wangdh.mengm.ui.adapter.CookBooksListAdapter;
import com.wangdh.mengm.utils.RecyclerViewUtil;
import com.wangdh.mengm.utils.ToolbarUtils;
import butterknife.BindView;

public class CookBooksActivity extends BaseActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recycler)
    RecyclerView recycler;
    @BindView(R.id.swipe)
    SwipeRefreshLayout mSwipe;
    @BindView(R.id.fab)
    FloatingActionButton mFab;
    private CookBooksData.ResultBeanX.ResultBean itemdata;
    public static final String INTENT_BEAN = "cookbooksList";
    private CookBooksListAdapter adapter;
    private Handler handler=new Handler();
    public static void startActivity(Context context, CookBooksData.ResultBeanX.ResultBean cookbooksList) {
        context.startActivity(new Intent(context, CookBooksActivity.class)
                .putExtra(INTENT_BEAN, cookbooksList));
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {

    }
    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_recyclerview;
    }

    @Override
    protected void initView() {
        ToolbarUtils.initTitle(toolbar, R.mipmap.ab_back, "菜单分类", this);
        recycler.setBackgroundResource(R.mipmap.meis);
        mSwipe.setColorSchemeResources(R.color.colorPrimaryDark2, R.color.btn_blue, R.color.ywlogin_colorPrimaryDark);//设置进度动画的颜色
        mSwipe.setProgressViewOffset(true, 0, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources().getDisplayMetrics()));
        mSwipe.setOnRefreshListener(() ->
                handler.postDelayed(() -> mSwipe.setRefreshing(false), 1000));
        mFab.setOnClickListener(v -> recycler.scrollToPosition(0));
    }
    @Override
    protected void initData() {
        itemdata = (CookBooksData.ResultBeanX.ResultBean) getIntent().getSerializableExtra(INTENT_BEAN);
        adapter = new CookBooksListAdapter(itemdata.getList());
        if (itemdata == null) {
            toast("数据加载失败");
        } else {
            adapter.notifyDataSetChanged();
        }
        RecyclerViewUtil.Gridinit(this, recycler, adapter, 3);
        adapter.setOnItemChildClickListener((adapter1, view, position) -> {
            Intent intent=new Intent(this,CookBooksListActivity.class);
            intent.putExtra("classid",itemdata.getList().get(position).getClassid());
            startActivity(intent);
        });
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
