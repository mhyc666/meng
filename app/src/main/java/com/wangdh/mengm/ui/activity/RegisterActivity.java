package com.wangdh.mengm.ui.activity;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.wangdh.mengm.MyApplication;
import com.wangdh.mengm.R;
import com.wangdh.mengm.base.BaseActivity;
import com.wangdh.mengm.base.im.DemoHelper;
import com.wangdh.mengm.component.AppComponent;
import com.wangdh.mengm.ui.Presenter.RegisterPresenter;
import com.wangdh.mengm.ui.contract.RegisterContract;
import com.wangdh.mengm.utils.KeyboardUtils;
import com.wangdh.mengm.utils.SharedPreferencesMgr;
import com.wangdh.mengm.widget.FilterImageView;
import java.util.HashMap;
import java.util.Random;
import butterknife.BindView;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import cn.smssdk.gui.RegisterPage;

public class RegisterActivity extends BaseActivity implements RegisterContract.View {
    @BindView(R.id.b_iv)
    ImageView imageView;
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.et_mm)
    EditText etMm;
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.sc)
    FilterImageView sc;
    @BindView(R.id.et_yzm)
    TextView etYzm;
    @BindView(R.id.yzm)
    Button yzm;
    @BindView(R.id.login)
    Button login;
    private String phone = "", password, name;
    private boolean flag = false;
    private RegisterPresenter mPresenter;
    private EventHandler eventHandler;

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {

    }

    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_register;
    }

    @Override
    protected void initView() {
        imageView.setOnClickListener(v -> this.finish());
        sc.setOnClickListener(v -> etPhone.setText(""));
    }

    @Override
    protected void initData() {
        // 创建EventHandler对象
        eventHandler = new EventHandler() {
            public void afterEvent(int event, int result, Object data) {
                if (data instanceof Throwable) {
                    Throwable throwable = (Throwable) data;
                    String msg = throwable.getMessage();
                    etYzm.setText("手机验证失败");
                    Log.i("toast", "msg:" + msg);
                } else {
                    if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                        // 处理你自己的逻辑
                        flag = true;
                    }
                }
            }
        };
        // 注册监听器
        SMSSDK.registerEventHandler(eventHandler);

        mPresenter = new RegisterPresenter();
        mPresenter.attachView(this);
        VerificationCode();
        register();
    }

    private void VerificationCode() {
        yzm.setOnClickListener(v -> {
            KeyboardUtils.hideSoftInput(this);
            phone = etPhone.getText().toString().trim();
            if (checkEdit()) {
                login.setBackgroundColor(ContextCompat.getColor(RegisterActivity.this, R.color.primary));
                login.setTextColor(ContextCompat.getColor(RegisterActivity.this, R.color.white));
                yzm.setBackgroundColor(ContextCompat.getColor(RegisterActivity.this, R.color.tabGray));
                RegisterPage registerPage = new RegisterPage();
                registerPage.setRegisterCallback(new EventHandler() {
                    public void afterEvent(int event, int result, Object data) {
                        // 解析注册结果
                        if (result == SMSSDK.RESULT_COMPLETE) {
                            @SuppressWarnings("unchecked")
                            HashMap<String, Object> phoneMap = (HashMap<String, Object>) data;
                            String country = (String) phoneMap.get("country");
                            String phone = (String) phoneMap.get("phone");
                            // 提交用户信息
                            registerUser(country, phone);
                        }
                    }
                });
                registerPage.show(this);

//                CountDownTimer time = new CountDownTimer(60000, 1000) {
//                    @Override
//                    public void onTick(long millisUntilFinished) {
//                        yzm.setEnabled(false);
//                        mTimeText.setText(String.valueOf(millisUntilFinished / 1000));
//                    }
//
//                    @Override
//                    public void onFinish() {
//                        yzm.setEnabled(true);
//                        mTimeText.setText("0");
//                        yzm.setBackgroundColor(ContextCompat.getColor(RegisterActivity.this, R.color.loginyzm));
//                    }
//                };
//                time.start();


//                ApiManager.getApiInstance().getMyService().RegisteryzmRxjava(phone)
//                        .subscribeOn(Schedulers.io())
//                        .observeOn(AndroidSchedulers.mainThread())
//                        .subscribe(new Observer<RegisterBean>() {
//                            @Override
//                            public void onCompleted() {
//
//                            }
//
//                            @Override
//                            public void onError(Throwable e) {
//                                Log.i("toast", e.toString());
//                            }
//
//                            @Override
//                            public void onNext(RegisterBean registerBean) {
//                                if (registerBean.getFlag().equals("false")) {
//                                    toast(registerBean.getMessage());
//                                } else {
//                                    flag = true;
//                                    toast("获取验证码成功！请查看短信，输入验证码");
//                                }
//                            }
//                        });
            }
        });
    }

    // 提交用户信息
    private void registerUser(String country, String phone) {
        Random rnd = new Random();
        int id = Math.abs(rnd.nextInt());
        String uid = String.valueOf(id);
        String nickName = "SmsSDK_User_" + uid;
        String avatar = "";
        SMSSDK.submitUserInfo(uid, nickName, avatar, country, phone);
    }

    private boolean checkEdit() {
        if (phone.equals("") && !MyApplication.isPhoneNum(phone)) {
            toast("手机号码格式错误");
            return false;
        } else {
            return true;
        }
    }

    private void register() {
        login.setOnClickListener(v -> {
            password = etMm.getText().toString().trim();
            name = etName.getText().toString().trim();
            phone = etPhone.getText().toString().trim();
            if (phone.equals("")) {
                showDialog();
                if (MyApplication.isPasswordCorrect(password) && !name.equals("")) {
                    mPresenter.getRegister(name, password, phone);
                } else {
                    toast("密码由数字或字母组成,昵称不能为空");
                }
            } else {
                if (flag) {
                    etYzm.setText("手机验证成功");
                    showDialog();
                    if (MyApplication.isPasswordCorrect(password) && !name.equals("")) {
                        mPresenter.getRegister(name, password, phone);
                    } else {
                        toast("密码由数字或字母组成,昵称不能为空");
                    }
                } else {
                    toast("请先获取验证码");
                }
            }
        });
    }

    @Override
    public void showError(String s) {
        hideDialog();
        toast(s);
    }

    @Override
    public void complete() {
        hideDialog();
        toast("注册成功");
        // 保存当前用户
        DemoHelper.getInstance().setCurrentUserName(name);
        SharedPreferencesMgr.setString("name", name);
        SharedPreferencesMgr.setString("password", password);
        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
        finish();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        SMSSDK.unregisterEventHandler(eventHandler);
        if (mPresenter != null) {
            mPresenter.detachView();
        }
    }
}
