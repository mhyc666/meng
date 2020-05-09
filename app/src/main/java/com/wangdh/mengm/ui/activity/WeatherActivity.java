package com.wangdh.mengm.ui.activity;

import android.Manifest;
import android.content.Intent;
import android.os.Handler;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.wangdh.mengm.R;
import com.wangdh.mengm.base.BaseActivity;
import com.wangdh.mengm.bean.WeatherAllData;
import com.wangdh.mengm.component.AppComponent;
import com.wangdh.mengm.component.DaggerActivityComponent;
import com.wangdh.mengm.manager.EventManager;
import com.wangdh.mengm.ui.Presenter.WeatherActivityPresenter;
import com.wangdh.mengm.ui.adapter.WeatherAdapter;
import com.wangdh.mengm.ui.contract.WeatherActivityContract;
import com.wangdh.mengm.utils.RecyclerViewUtil;
import com.wangdh.mengm.utils.StateBarTranslucentUtils;
import com.wangdh.mengm.utils.ToolbarUtils;
import com.yanzhenjie.alertdialog.AlertDialog;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.PermissionListener;
import com.yanzhenjie.permission.RationaleListener;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import java.util.List;
import javax.inject.Inject;
import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class WeatherActivity extends BaseActivity implements WeatherActivityContract.View {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.mRecyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.mSwipe)
    SwipeRefreshLayout mSwipe;
    @BindView(R.id.coord)
    CoordinatorLayout mCoord;
    @Inject
    WeatherActivityPresenter mPresenter;
    private WeatherAdapter adapter;
    private WeatherAllData mData = new WeatherAllData();
    private String city = "";
    public LocationClient mLocationClient = null;
    public MyLocationListener myListener = new MyLocationListener();
    private int code;
    private static final int REQUEST_CODE_PERMISSION = 100;
    private static final int REQUEST_CODE_SETTING = 300;

    public class MyLocationListener implements BDLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {
            //此处的BDLocation为定位结果信息类，通过它的各种get方法可获取定位相关的全部结果
            //以下只列举部分获取地址相关的结果信息
            //更多结果信息获取说明，请参照类参考中BDLocation类中的说明

            String addr = location.getAddrStr();    //获取详细地址信息
            String country = location.getCountry();    //获取国家
            String province = location.getProvince();    //获取省份
            city = location.getCity();    //获取城市
            Log.i("toast", "获取城市:" + city);
            String district = location.getDistrict();    //获取区县
            String street = location.getStreet();    //获取街道信息
            code = location.getLocType();
            Log.i("toast", "code:" + code);
            data();
        }
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerActivityComponent.builder()
                .appComponent(appComponent)
                .build()
                .inject(this);
    }

    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_weather;
    }

    @Override
    protected void initView() {
        mSwipe.setColorSchemeResources(R.color.colorPrimaryDark2, R.color.btn_blue, R.color.ywlogin_colorPrimaryDark);//设置进度动画的颜色
        mSwipe.setProgressViewOffset(true, 0, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources().getDisplayMetrics()));
        mSwipe.setOnRefreshListener(() -> {
            setDataRefresh(true);
            mPresenter.getWeatherData(city);
        });
        setDataRefresh(true);
    }

    @Override
    protected void initData() {
        initPermission();
        mLocationClient = new LocationClient(getApplicationContext());
        //声明LocationClient类
        mLocationClient.registerLocationListener(myListener);
        //注册监听函数
        LocationClientOption option = new LocationClientOption();
        option.setIsNeedAddress(true);
//可选，是否需要地址信息，默认为不需要，即参数为false
//如果开发者需要获得当前点的地址信息，此处必须为true
        mLocationClient.setLocOption(option);
//mLocationClient为第二步初始化过的LocationClient对象
//需将配置好的LocationClientOption对象，通过setLocOption方法传递给LocationClient对象使用
//更多LocationClientOption的配置，请参照类参考中LocationClientOption类的详细说明
        mLocationClient.start();
        StateBarTranslucentUtils.setStateBarColor(this);
        ToolbarUtils.initTitle(toolbar, R.mipmap.ab_back, city, this);
        EventBus.getDefault().register(this);
        mPresenter.attachView(this);
    }

    private void initPermission() {
        // 申请权限。
        AndPermission.with(this)
                .requestCode(REQUEST_CODE_PERMISSION)
                .permission(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.READ_PHONE_STATE,
                        Manifest.permission.READ_SMS, Manifest.permission.ACCESS_FINE_LOCATION)
                .callback(permissionListener)
                .rationale(rationaleListener)
                .start();
    }

    private void data() {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> s) throws Exception {
                Log.i("toast", " Observable");
                if (code == 61 || code == 161 || code == 66 || code == 68) {
                    Log.i("toast", "定位成功");
                    mLocationClient.stop();
                } else {
                    city = "杭州";
                    Log.i("toast", "定位失败");
                    toast("定位获取城市失败");
                }
                s.onNext(1);
                s.onComplete();
            }
        }).subscribe(new Observer<Integer>() {
            // Disposable 相当于RxJava1.x中的 Subscription，用于解除订阅
            private Disposable disposable;

            @Override
            public void onSubscribe(Disposable d) {
                disposable = d;
            }

            @Override
            public void onNext(Integer s) {
                mPresenter.getWeatherData(city);
            }

            @Override
            public void onError(Throwable e) {
                toast(e.getMessage());
            }

            @Override
            public void onComplete() {
                //解除订阅
                disposable.dispose();
            }
        });

    }

    @Override
    public void showError(String s) {
        setDataRefresh(false);
        toast(s);
    }

    private void setDataRefresh(boolean refresh) {
        if (mSwipe != null) {
            if (refresh) {
                mSwipe.setRefreshing(refresh);
            } else {
                new Handler().postDelayed(() -> mSwipe.setRefreshing(refresh), 800);//延时消失加载的loading
            }
        }
    }

    @Override
    public void complete() {
        setDataRefresh(false);
    }

    @Override
    public void showWeatherData(WeatherAllData data) {

        try {
            mData = data;
            ToolbarUtils.initTitle(toolbar, R.mipmap.ab_back, mData.getHeWeather5().get(0).getBasic().getCity(), this);
            adapter = new WeatherAdapter(mData);
            RecyclerViewUtil.initNoDecoration(this, mRecyclerView, adapter);
            int code = mData.getHeWeather5().get(0).getNow().getCond().getCode();

            if (code == 100) {
                mCoord.setBackgroundColor(ContextCompat.getColor(this, R.color.colorSendName6));
            } else if (code == 104) {
                mCoord.setBackgroundColor(ContextCompat.getColor(this, R.color.color_54698a));
            }

        } catch (NullPointerException e) {
            e.getMessage();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.searchmenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_search) {
            startActivity(new Intent(this, SearchWeatherActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void RefreshCollectionList(EventManager event) {
        mData.setHeWeather5(null);
        mSwipe.setRefreshing(true);
        city = event.message;
        ToolbarUtils.initTitle(toolbar, R.mipmap.ab_back, city, this);
        mPresenter.getWeatherData(event.message);
    }

    /**
     * 回调监听。
     */
    private PermissionListener permissionListener = new PermissionListener() {
        @Override
        public void onSucceed(int requestCode, List<String> grantPermissions) {
            switch (requestCode) {
                case REQUEST_CODE_PERMISSION: {
                    // Toast.makeText(LoginActivity.this, "获取权限成功", Toast.LENGTH_SHORT).show();
                    break;
                }
            }
        }

        @Override
        public void onFailed(int requestCode, List<String> deniedPermissions) {
            switch (requestCode) {
                case REQUEST_CODE_PERMISSION: {
                    Toast.makeText(WeatherActivity.this, "获取权限失败", Toast.LENGTH_SHORT).show();
                    break;
                }
            }
            // 用户否勾选了不再提示并且拒绝了权限，那么提示用户到设置中授权。
            if (AndPermission.hasAlwaysDeniedPermission(WeatherActivity.this, deniedPermissions)) {
                // 第一种：用默认的提示语。
                AndPermission.defaultSettingDialog(WeatherActivity.this, REQUEST_CODE_SETTING).show();
            }
        }
    };

    /**
     * Rationale支持，这里自定义对话框。
     */
    private RationaleListener rationaleListener = (requestCode, rationale) -> {

        AlertDialog.newBuilder(this)
                .setTitle("友好提醒")
                .setMessage("你已经拒绝了权限")
                .setPositiveButton("好，给你", (dialog, which) -> {
                    dialog.cancel();
                    rationale.resume();
                })
                .setNegativeButton("我拒绝", (dialog, which) -> {
                    dialog.cancel();
                    rationale.cancel();
                }).show();
    };

    @Override   //权限---用户在系统Setting中操作完成后
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        Fragment fragment=getSupportFragmentManager().findFragmentByTag("");
//        fragment.onActivityResult(requestCode,resultCode,data);
        switch (requestCode) {
            case REQUEST_CODE_SETTING: {
                //    Toast.makeText(LoginActivity.this, "系统设置中操作完成后", Toast.LENGTH_SHORT).show();
                break;
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        if (mPresenter != null) {
            mPresenter.detachView();
        }
    }
}
