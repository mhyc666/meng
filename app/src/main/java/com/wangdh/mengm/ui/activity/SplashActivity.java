package com.wangdh.mengm.ui.activity;

import android.content.Intent;
import android.os.CountDownTimer;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.TextView;
import com.wangdh.mengm.R;
import com.wangdh.mengm.base.BaseActivity;
import com.wangdh.mengm.bean.CelebratedDictum;
import com.wangdh.mengm.bean.HeaderLayoutBean;
import com.wangdh.mengm.component.AppComponent;
import com.wangdh.mengm.component.DaggerActivityComponent;
import com.wangdh.mengm.ui.Presenter.SplashPresenter;
import com.wangdh.mengm.ui.contract.SplashContract;
import com.wangdh.mengm.utils.NetworkUtil;
import com.wangdh.mengm.utils.StorageData;
import com.wangdh.mengm.utils.StringUtils;
import javax.inject.Inject;
import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * wdh 启动页
 * Created by Administrator on 2017/8/22.
 */

public class SplashActivity extends BaseActivity implements SplashContract.View {
    @BindView(R.id.imageView)
    CircleImageView imageView;
    @BindView(R.id.textView)
    TextView textView;
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.textskip)
    TextView textskip;
    @BindView(R.id.tvtime)
    TextView tvtime;
    @BindView(R.id.frame_skip)
    FrameLayout frameSkip;
    private CountDownTimer time;
    @Inject
    SplashPresenter mPresenter;
    private Intent intent;
    private boolean b = false;

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerActivityComponent.builder()
                .appComponent(appComponent)
                .build()
                .inject(this);
    }

    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_splash;
    }

    @Override
    protected void initView() {
        if(!NetworkUtil.isConnected(this)){
            toast("当前网络未链接!");
        }
        time = new CountDownTimer(5000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                tvtime.setText(String.valueOf(millisUntilFinished / 1000));
            }

            @Override
            public void onFinish() {
                if (b) {
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_right_entry, R.anim.hold);
                } else {
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                    overridePendingTransition(R.anim.slide_right_entry, R.anim.hold);
                }
                finish();
            }
        };
        time.start();
    }

    @Override
    protected void initData() {
        StorageData.setHeadImage(imageView, "", getContext());  //头像
        mPresenter.attachView(this);
        mPresenter.getSplashDta();
        mPresenter.getHeaderData();
    }

    @OnClick(R.id.frame_skip)
    public void onViewClicked() {
        if (time != null) {
            time.cancel();
        }

        if (b) {
            startActivity(intent);
            overridePendingTransition(R.anim.slide_right_entry, R.anim.hold);
        } else {
            startActivity(new Intent(SplashActivity.this, MainActivity.class));
            overridePendingTransition(R.anim.slide_right_entry, R.anim.hold);
        }
        finish();
    }

    @Override
    public void showError(String s) {
    }

    @Override
    public void complete() {
    }

    @Override
    public void showSplashData(CelebratedDictum dictum) {
        textView.setText(dictum.getResult().getFamous_saying());
        name.setText(String.format("─%s", dictum.getResult().getFamous_name()));
    }

    @Override
    public void showHeaderData(HeaderLayoutBean headerLayoutBean) {
        String tit ,n ,txt;
        tit = headerLayoutBean.getResult().getBiaoti();
        n = headerLayoutBean.getResult().getZuozhe();
        txt = StringUtils.formatContent(headerLayoutBean.getResult().getNeirong());
        intent = new Intent(SplashActivity.this, MainActivity.class);
        intent.putExtra("tit", tit);
        intent.putExtra("n", n);
        intent.putExtra("txt", txt);
        b = true;
        Log.i("toast",tit+n+txt);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (time != null) {
            time.cancel();
        }
        if (mPresenter != null) {
            mPresenter.detachView();
        }
    }

}
