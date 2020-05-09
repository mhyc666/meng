package com.wangdh.mengm.ui.activity;

import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.webkit.WebView;
import android.widget.LinearLayout;

import com.just.library.AgentWeb;
import com.just.library.ChromeClientCallbackManager;
import com.wangdh.mengm.R;
import com.wangdh.mengm.base.BaseActivity;
import com.wangdh.mengm.component.AppComponent;
import com.wangdh.mengm.utils.ToolbarUtils;

import butterknife.BindView;

/**
 * 新闻及微信详情
 */

public class WebViewDetailsActivity extends BaseActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.linear)
    LinearLayout mLinearLayout;
    private AgentWeb mAgentWeb;

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {

    }

    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_wechatdetails;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        ToolbarUtils.init(toolbar, R.mipmap.ab_back, this);
        String url = getIntent().getStringExtra("wechaturl");
        showDialog();
        mAgentWeb = AgentWeb.with(this)//传入Activity
                .setAgentWebParent(mLinearLayout, new LinearLayout.LayoutParams(-1, -1))//传入AgentWeb 的父控件 ，如果父控件为 RelativeLayout ， 那么第二参数需要传入 RelativeLayout.LayoutParams ,第一个参数和第二个参数应该对应。
                .useDefaultIndicator()// 使用默认进度条
                .defaultProgressBarColor() // 使用默认进度条颜色
                .setReceivedTitleCallback(mCallback) //设置 Web 页面的 title 回调
                .createAgentWeb()//
                .ready()
                .go(url);
    }

    private ChromeClientCallbackManager.ReceivedTitleCallback mCallback = new ChromeClientCallbackManager.ReceivedTitleCallback() {
        @Override
        public void onReceivedTitle(WebView view, String title) {
            if (toolbar != null && !TextUtils.isEmpty(title))
                if (title.length() > 12)
                    title = title.substring(0, 12) + "...";
            hideDialog();
            toolbar.setTitle(title == null ? "" : title);

        }
    };

    @Override
    protected void onPause() {
        mAgentWeb.getWebLifeCycle().onPause();
        super.onPause();

    }

    @Override
    protected void onResume() {
        mAgentWeb.getWebLifeCycle().onResume();
        super.onResume();
    }
}
