package com.wangdh.mengm.ui.activity;

import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.wangdh.mengm.MyApplication;
import com.wangdh.mengm.R;
import com.wangdh.mengm.base.BaseActivity;
import com.wangdh.mengm.bean.bmob.AccountBean;
import com.wangdh.mengm.component.AppComponent;
import com.wangdh.mengm.utils.KeyboardUtils;
import com.wangdh.mengm.widget.FilterImageView;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import butterknife.BindView;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import cn.smssdk.gui.RegisterPage;


public class PassWordActivity extends BaseActivity {
    @BindView(R.id.b_iv)
    ImageView imageView;
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.sc)
    FilterImageView sc;
    @BindView(R.id.et_yzm)
    TextView etYzm;
    @BindView(R.id.yzm)
    Button yzm;
    @BindView(R.id.et_mm)
    EditText etMm;
    @BindView(R.id.login)
    Button login;
    private String phone, password;
    private boolean flag = false;
    private EventHandler handler;

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {

    }

    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_password;
    }

    @Override
    protected void initView() {
        imageView.setOnClickListener(v -> this.finish());
        sc.setOnClickListener(v -> etPhone.setText(""));
        login.setOnClickListener(v -> {
            if(flag){ etYzm.setText("手机验证成功");
            }else {
                etYzm.setText("手机验证失败") ;
            }
            phone = etPhone.getText().toString().trim();
            if (!phone.equals("")) {
                showDialog();
                BmobQuery<AccountBean> query = new BmobQuery<>();
                query.addWhereEqualTo("phone", phone);
                query.findObjects(new FindListener<AccountBean>() {
                    @Override
                    public void done(List<AccountBean> list, BmobException e) {
                        if (e == null) {
                                if (list.get(0).getPhone().equals(phone) && flag) {
                                    password = etMm.getText().toString().trim();
                                    if (MyApplication.isPasswordCorrect(password)) {
                                        register(password, list.get(0).getObjectId());
                                    } else {
                                        toast("密码由数字或字母组成");
                                    }
                                } else {
                                    toast("请先通过手机验证");
                            }
                        } else {
                            toast("获取信息失败");
                        }
                        hideDialog();
                    }
                });
            }else {
                toast("手机号不能为空");
            }
        });
    }

    private void register(String password, String id) {
        AccountBean bean = new AccountBean();
        bean.setPassword(password);
        bean.update(id, new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    toast("修改成功");
                } else {
                    toast("修改失败");
                    Log.i("bmob", "更新失败：" + e.getMessage() + "," + e.getErrorCode());
                }
            }
        });
    }

    @Override
    protected void initData() {
        // 创建EventHandler对象
        // 处理你自己的逻辑
        handler = new EventHandler() {
            public void afterEvent(int event, int result, Object data) {
                if (data instanceof Throwable) {
                    Throwable throwable = (Throwable) data;
                    String msg = throwable.getMessage();
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
        SMSSDK.registerEventHandler(handler);
        VerificationCode();
    }

    private void VerificationCode() {
        yzm.setOnClickListener(v -> {
            KeyboardUtils.hideSoftInput(this);
            phone = etPhone.getText().toString().trim();
            if (checkEdit()) {
                login.setBackgroundColor(ContextCompat.getColor(PassWordActivity.this, R.color.primary));
                login.setTextColor(ContextCompat.getColor(PassWordActivity.this, R.color.white));
                yzm.setBackgroundColor(ContextCompat.getColor(PassWordActivity.this, R.color.tabGray));
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        SMSSDK.unregisterEventHandler(handler);
    }
}