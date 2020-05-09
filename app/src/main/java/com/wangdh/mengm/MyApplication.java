package com.wangdh.mengm;

import android.content.Context;
import android.support.multidex.MultiDex;

import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMOptions;
import com.mob.MobApplication;
import com.tencent.bugly.Bugly;
import com.wangdh.mengm.base.im.DemoHelper;
import com.wangdh.mengm.component.AppComponent;
import com.wangdh.mengm.component.DaggerAppComponent;
import com.wangdh.mengm.module.ApiModule;
import com.wangdh.mengm.module.AppModule;
import com.wangdh.mengm.utils.AppUtils;
import com.wangdh.mengm.utils.SharedPreferencesMgr;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.bmob.v3.Bmob;

public class MyApplication extends MobApplication {
    private static MyApplication sInstance;
    private AppComponent appComponent;

    @Override
    public void onCreate() {
        MultiDex.install(this);
        super.onCreate();
        initCompoent();
        sInstance = this;
        AppUtils.init(this);
        Bmob.initialize(this, "60394b1cd31efaf89ab9b0f586241788");
        Bugly.init(this, "47447146b2", false);
        SharedPreferencesMgr.init(this,"mengm");
        //init demo helper
        DemoHelper.getInstance().init(sInstance);
        EMOptions options = new EMOptions();
// 默认添加好友时，是不需要验证的，改成需要验证
        options.setAcceptInvitationAlways(false);
// 是否自动将消息附件上传到环信服务器，默认为True是使用环信服务器上传下载，如果设为 false，需要开发者自己处理附件消息的上传和下载
        options.setAutoTransferMessageAttachments(true);
// 是否自动下载附件类消息的缩略图等，默认为 true 这里和上边这个参数相关联
        options.setAutoDownloadThumbnail(true);
//初始化
        EMClient.getInstance().init(sInstance, options);
//在做打包混淆时，关闭debug模式，避免消耗不必要的资源
        EMClient.getInstance().setDebugMode(false);
    }

    private void initCompoent() {
        appComponent = DaggerAppComponent.builder()
                .apiModule(new ApiModule())
                .appModule(new AppModule(this))
                .build();
    }

    public static MyApplication getsInstance() {
        return sInstance;
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    public static boolean isPhoneNum(String phone) {  //手机号
        //   Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
        //"'|^1[34578]{1}\\d{9}$|'"
        Pattern p = Pattern.compile("^0{0,1}(13[0-9]|15[0-9]|18[0-9])[0-9]{8}$");
        Matcher m = p.matcher(phone);
        return m.matches();
    }

    public static boolean isPasswordCorrect(String pwd) { //密码由数字或字母组成
        Pattern p = Pattern.compile("^[A-Za-z0-9]+$");
        Matcher m = p.matcher(pwd);
        return m.matches();
    }
}
