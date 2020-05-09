package com.wangdh.mengm.ui.activity;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.PowerManager;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.hyphenate.EMClientListener;
import com.hyphenate.EMConnectionListener;
import com.hyphenate.EMContactListener;
import com.hyphenate.EMError;
import com.hyphenate.EMMessageListener;
import com.hyphenate.EMMultiDeviceListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMCmdMessageBody;
import com.hyphenate.chat.EMMessage;
import com.wangdh.mengm.R;
import com.wangdh.mengm.base.BaseActivity;
import com.wangdh.mengm.base.Constant;
import com.wangdh.mengm.base.TabEntity;
import com.wangdh.mengm.base.im.DemoHelper;
import com.wangdh.mengm.component.AppComponent;
import com.wangdh.mengm.db.InviteMessgeDao;
import com.wangdh.mengm.db.UserDao;
import com.wangdh.mengm.ui.fragment.LiveFragment;
import com.wangdh.mengm.ui.fragment.MessageFragment;
import com.wangdh.mengm.ui.fragment.MyFragment;
import com.wangdh.mengm.ui.fragment.NewFragment;
import com.wangdh.mengm.ui.fragment.WeChatFragment;
import com.wangdh.mengm.utils.NetworkUtil;
import com.wangdh.mengm.utils.SharedPreferencesMgr;
import com.wangdh.mengm.utils.StateBarTranslucentUtils;
import com.wangdh.mengm.utils.StorageData;
import com.yanzhenjie.alertdialog.AlertDialog;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.PermissionListener;
import com.yanzhenjie.permission.RationaleListener;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends BaseActivity {
    private static final int REQUEST_CODE_PERMISSION = 100;
    private static final int REQUEST_CODE_SETTING = 300;
    @BindView(R.id.nav_view)
    NavigationView navView;
    @BindView(R.id.drawer)
    DrawerLayout drawer;
    @BindView(R.id.main_toolbar)
    Toolbar toolbar;
    @BindView(R.id.commontab)
    CommonTabLayout mainTab;
    private FrameLayout headerLayout;
    private CircleImageView mIv;
    private TextView mTv1, mTv2, mTv3;
    private ArrayList<Fragment> mFragments;
    private int[] mIconUnselectIds = {
            R.mipmap.icon_news_un, R.mipmap.icon_wechat_un, R.mipmap.icon_in,
            R.mipmap.icon_find_un, R.mipmap.icon_my_un};
    private int[] mIconSelectIds = {
            R.mipmap.icon_news, R.mipmap.icon_wechat, R.mipmap.icom_im,
            R.mipmap.icon_find, R.mipmap.icon_my};
    private String[] mTitles = {"新闻头条", "微信精选", "消息", "生活健康", "我的"};
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
    private BroadcastReceiver broadcastReceiver;
    private InviteMessgeDao inviteMessgeDao;
    private LocalBroadcastManager broadcastManager;
    private android.app.AlertDialog.Builder exceptionBuilder;
    private boolean isExceptionDialogShow = false;
    private int currentTabIndex;
    // 用户登录到另一个设备
    public boolean isConflict = false;
    //用户帐户被删除
    private boolean isCurrentAccountRemoved = false;
    private MessageFragment messageFragment;

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {

    }

    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        initToolbar();
        showExceptionDialogFromIntent(getIntent());
        inviteMessgeDao = new InviteMessgeDao(this);
        UserDao userDao = new UserDao(this);
        //注册广播接收器接收来自DemoHelper的组的更改
        registerBroadcastReceiver();
        NavigationItemSelectedListener();
        initTab();

       //注册一个监听连接状态的listener
        if (!SharedPreferencesMgr.getString("password", "")
                .equals("") && !SharedPreferencesMgr.getString("name", "").equals("")) {  //表示登陆状态
            EMClient.getInstance().addConnectionListener(new MyConnectionListener());
        }
        EMClient.getInstance().contactManager().setContactListener(new MyContactListener());
        EMClient.getInstance().addClientListener(clientListener);
        EMClient.getInstance().addMultiDeviceListener(new MyMultiDeviceListener());
    }

    //实现ConnectionListener接口
    private class MyConnectionListener implements EMConnectionListener {
        @Override
        public void onConnected() {
        }

        @Override
        public void onDisconnected(final int error) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (error == EMError.USER_REMOVED) {
                        // 显示帐号已经被移除
                        toast("显示帐号已经被移除");
                    } else if (error == EMError.USER_LOGIN_ANOTHER_DEVICE) {
                        // 显示帐号在其他设备登录
                        toast("显示帐号在其他设备登录");
                    } else {
                        if (NetworkUtil.isAvailable(MainActivity.this)) {
                            //连接不到聊天服务器
                            toast("连接不到聊天服务器");
                        } else {
                            toast("当前网络不可用，请检查网络设置");
                            //当前网络不可用，请检查网络设置
                        }
                    }
                }
            });
        }
    }


    private void initToolbar() {
        toolbar.setTitle("MyApp");
        setSupportActionBar(toolbar);
    }

    private void initTab() {
        mFragments = new ArrayList<>();
        messageFragment = new MessageFragment();
        mFragments.add(new NewFragment());
        mFragments.add(new WeChatFragment());
        mFragments.add(messageFragment);
        mFragments.add(new LiveFragment());
        mFragments.add(new MyFragment());
        for (int i = 0; i < mTitles.length; i++) {
            mTabEntities.add(new TabEntity(mTitles[i], mIconSelectIds[i], mIconUnselectIds[i]));
            currentTabIndex = i;
        }
        mainTab.setTabData(mTabEntities, this, R.id.framelayout, mFragments);
        mainTab.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                mainTab.setCurrentTab(position);
                if (position > 0) {
                    toolbar.setVisibility(View.GONE);
                } else {
                    toolbar.setVisibility(View.VISIBLE);
                }
                if (position == 2) {
                    mainTab.hideMsg(2);
                }
            }

            @Override
            public void onTabReselect(int position) {

            }
        });
        mainTab.setCurrentTab(0);
    }

    private void NavigationItemSelectedListener() {
        ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.open, R.string.close) {
        };//抽屉开关的效果
        mDrawerToggle.syncState();//初始化状态
        drawer.addDrawerListener(mDrawerToggle);
        navView.setNavigationItemSelectedListener((item) -> {
            switch (item.getItemId()) {
                case R.id.weatheritem:
                    startActivity(new Intent(this, WeatherActivity.class));
                    break;
                case R.id.pictureitem:
                    if (!SharedPreferencesMgr.getBoolean("image", true)) {
                        Snackbar snackbar = Snackbar.make(toolbar, "妹子图片无图模式不能浏览", Snackbar.LENGTH_INDEFINITE);
                        //设置行为的监听(和对话框的按钮很像)
                        snackbar.setAction("确定", v -> toast("请关闭无图模式"));
                        snackbar.show();
                    } else {
                        startActivity(new Intent(this, MeizhiPictureActivity.class));
                    }

                    break;
                case R.id.videoiitem:
                    startActivity(new Intent(this, VideoListActivity.class));
                    break;
                case R.id.friend:
                    startActivity(new Intent(this,ContactListActivity.class));
            }
            item.setChecked(true);//点击了把它设为选中状态
            drawer.closeDrawers();//关闭抽屉
            return true;
        });
    }

    @Override
    protected void initData() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            String packageName = getPackageName();
            PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
            if (!pm.isIgnoringBatteryOptimizations(packageName)) {
                try {
                    //some device doesn't has activity to handle this intent
                    //so add try catch
                    Intent intent = new Intent();
                    intent.setAction(android.provider.Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS);
                    intent.setData(Uri.parse("package:" + packageName));
                    startActivity(intent);
                } catch (Exception e) {
                }
            }
        }

        //当用户登录到另一个设备或删除时，确保活动不会出现在后台
        if (getIntent() != null &&
                (getIntent().getBooleanExtra(Constant.ACCOUNT_REMOVED, false) ||
                        getIntent().getBooleanExtra(Constant.ACCOUNT_KICKED_BY_CHANGE_PASSWORD, false) ||
                        getIntent().getBooleanExtra(Constant.ACCOUNT_KICKED_BY_OTHER_DEVICE, false))) {
            DemoHelper.getInstance().logout(false, null);
            finish();
            startActivity(new Intent(this, LoginActivity.class));
            return;
        } else if (getIntent() != null && getIntent().getBooleanExtra("isConflict", false)) {
            finish();
            startActivity(new Intent(this, LoginActivity.class));
            return;
        }

        StateBarTranslucentUtils.setStateBarTranslucent(this);
        initPermission();
        String n = getIntent().getStringExtra("n");
        String tit = getIntent().getStringExtra("tit");
        String txt = getIntent().getStringExtra("txt");
        headerLayout = (FrameLayout) navView.inflateHeaderView(R.layout.nav_header_main);
        mIv = (CircleImageView) headerLayout.findViewById(R.id.c_iv);
        mTv1 = (TextView) headerLayout.findViewById(R.id.tv_title);
        mTv2 = (TextView) headerLayout.findViewById(R.id.tv_name);
        mTv3 = (TextView) headerLayout.findViewById(R.id.tv_txt);
        StorageData.setHeadImage(mIv, "", getContext());  //头像
        if (n != null) {
            mTv1.setText(tit);
            mTv2.setText(n);
            mTv3.setText(txt);
            mTv3.setOnClickListener(v -> toast(txt));
        }
    }

    private void registerBroadcastReceiver() {
        broadcastManager = LocalBroadcastManager.getInstance(this);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Constant.ACTION_CONTACT_CHANAGED);
        intentFilter.addAction(Constant.ACTION_GROUP_CHANAGED);
        broadcastReceiver = new BroadcastReceiver() {

            @Override
            public void onReceive(Context context, Intent intent) {
                updateUnreadLabel();
                updateUnreadAddressLable();
                if (currentTabIndex == 2) {
                    // 刷新对话列表
                    if (messageFragment != null) {
                        messageFragment.refresh();
                    }
                } else if (currentTabIndex == 0) {  //联系人
//                    if(contactListFragment != null) {
//                        contactListFragment.refresh();
//                    }
                }
                String action = intent.getAction();
                if (action.equals(Constant.ACTION_GROUP_CHANAGED)) {
//                    if (EaseCommonUtils.getTopActivity(MainActivity.this).equals(GroupsActivity.class.getName())) {
//                        GroupsActivity.instance.onResume();
//                    }
                }

            }
        };
        broadcastManager.registerReceiver(broadcastReceiver, intentFilter);
    }


    public class MyContactListener implements EMContactListener {
        @Override
        public void onContactAdded(String username) {
        }

        @Override
        public void onContactDeleted(final String username) {
            runOnUiThread(new Runnable() {
                public void run() {
                    if (ChatActivity.activityInstance != null && ChatActivity.activityInstance.toChatUsername != null &&
                            username.equals(ChatActivity.activityInstance.toChatUsername)) {
                        String st10 = getResources().getString(R.string.have_you_removed);
                        Toast.makeText(MainActivity.this, ChatActivity.activityInstance.getToChatUsername() + st10, Toast.LENGTH_LONG)
                                .show();
                        ChatActivity.activityInstance.finish();
                    }
                }
            });
            updateUnreadAddressLable();
        }

        @Override
        public void onContactInvited(String username, String reason) {
        }

        @Override
        public void onFriendRequestAccepted(String username) {
        }

        @Override
        public void onFriendRequestDeclined(String username) {
        }
    }

    EMClientListener clientListener = new EMClientListener() {
        @Override
        public void onMigrate2x(boolean success) {
            Toast.makeText(MainActivity.this, "onUpgradeFrom 2.x to 3.x " + (success ? "success" : "fail"), Toast.LENGTH_LONG).show();
            if (success) {
                refreshUIWithMessage();
            }
        }
    };


    public class MyMultiDeviceListener implements EMMultiDeviceListener {

        @Override
        public void onContactEvent(int event, String target, String ext) {

        }

        @Override
        public void onGroupEvent(int event, String target, final List<String> username) {
            switch (event) {
                case EMMultiDeviceListener.GROUP_LEAVE:
                    ChatActivity.activityInstance.finish();
                    break;
                default:
                    break;
            }
        }
    }

    private void refreshUIWithMessage() {
        runOnUiThread(new Runnable() {
            public void run() {
                // 刷新未读计数
                updateUnreadLabel();
                if (currentTabIndex == 0) {
                    // 刷新对话列表
                    if (messageFragment != null) {
                        messageFragment.refresh();
                    }
                }
            }
        });
    }


    /**
     * 更新未读消息数
     */
    public void updateUnreadLabel() {
        int count = getUnreadMsgCountTotal();
        if (count > 0) {
            mainTab.showDot(2);
        }
    }

    /**
     * 获取未读消息数
     *
     * @return
     */
    public int getUnreadMsgCountTotal() {
        return EMClient.getInstance().chatManager().getUnreadMsgsCount();
    }

    /**
     * 更新总未读计数
     */
    public void updateUnreadAddressLable() {
        runOnUiThread(new Runnable() {
            public void run() {
                int count = getUnreadAddressCountTotal();
                if (count > 0) {
                    //   unreadAddressLable.setVisibility(View.VISIBLE);
                } else {
                    //   unreadAddressLable.setVisibility(View.INVISIBLE);
                }
            }
        });
    }

    /**
     * 获取未读的事件通知计数，包括应用程序、接受等
     *
     * @return
     */
    public int getUnreadAddressCountTotal() {
        int unreadAddressCountTotal = 0;
        unreadAddressCountTotal = inviteMessgeDao.getUnreadMessagesCount();
        return unreadAddressCountTotal;
    }

    private void unregisterBroadcastReceiver() {
        broadcastManager.unregisterReceiver(broadcastReceiver);
    }

    private void showExceptionDialogFromIntent(Intent intent) {
        if (!isExceptionDialogShow && intent.getBooleanExtra(Constant.ACCOUNT_CONFLICT, false)) {
            showExceptionDialog(Constant.ACCOUNT_CONFLICT);
        } else if (!isExceptionDialogShow && intent.getBooleanExtra(Constant.ACCOUNT_REMOVED, false)) {
            showExceptionDialog(Constant.ACCOUNT_REMOVED);
        } else if (!isExceptionDialogShow && intent.getBooleanExtra(Constant.ACCOUNT_FORBIDDEN, false)) {
            showExceptionDialog(Constant.ACCOUNT_FORBIDDEN);
        } else if (intent.getBooleanExtra(Constant.ACCOUNT_KICKED_BY_CHANGE_PASSWORD, false) ||
                intent.getBooleanExtra(Constant.ACCOUNT_KICKED_BY_OTHER_DEVICE, false)) {
            this.finish();
            startActivity(new Intent(this, LoginActivity.class));
        }
    }

    private int getExceptionMessageId(String exceptionType) {
        if (exceptionType.equals(Constant.ACCOUNT_CONFLICT)) {
            return R.string.connect_conflict;
        } else if (exceptionType.equals(Constant.ACCOUNT_REMOVED)) {
            return R.string.em_user_remove;
        } else if (exceptionType.equals(Constant.ACCOUNT_FORBIDDEN)) {
            return R.string.user_forbidden;
        }
        return R.string.Network_error;
    }

    /**
     * 当用户遇到一些异常时显示对话框:例如在另一个设备上登录，用户删除或禁止用户
     */
    private void showExceptionDialog(String exceptionType) {
        isExceptionDialogShow = true;
        DemoHelper.getInstance().logout(false, null);
        String st = getResources().getString(R.string.Logoff_notification);
        if (!MainActivity.this.isFinishing()) {
            // clear up global variables
            try {
                if (exceptionBuilder == null)
                    exceptionBuilder = new android.app.AlertDialog.Builder(MainActivity.this);
                exceptionBuilder.setTitle(st);
                exceptionBuilder.setMessage(getExceptionMessageId(exceptionType));
                exceptionBuilder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        exceptionBuilder = null;
                        isExceptionDialogShow = false;
                        finish();
                        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                });
                exceptionBuilder.setCancelable(false);
                exceptionBuilder.create().show();
                isConflict = true;
            } catch (Exception e) {
                e.getMessage();
            }
        }
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
                    Toast.makeText(MainActivity.this, "获取权限失败", Toast.LENGTH_SHORT).show();
                    break;
                }
            }
            // 用户否勾选了不再提示并且拒绝了权限，那么提示用户到设置中授权。
            if (AndPermission.hasAlwaysDeniedPermission(MainActivity.this, deniedPermissions)) {
                // 第一种：用默认的提示语。
                AndPermission.defaultSettingDialog(MainActivity.this, REQUEST_CODE_SETTING).show();
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
        // Fragment fragment=getSupportFragmentManager().findFragmentByTag("");
        // fragment.onActivityResult(requestCode,resultCode,data);
        switch (requestCode) {
            case REQUEST_CODE_SETTING: {
                //    Toast.makeText(LoginActivity.this, "系统设置中操作完成后", Toast.LENGTH_SHORT).show();
                // System.out.println("");
                break;
            }
        }
    }

    // 退出时间
    private long currentBackPressedTime = 0;
    // 退出间隔
    private static final int BACK_PRESSED_INTERVAL = 2000;

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN
                && event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            if (System.currentTimeMillis() - currentBackPressedTime > BACK_PRESSED_INTERVAL) {
                currentBackPressedTime = System.currentTimeMillis();
                toast(getString(R.string.exit_tips));
                return true;
            } else {
                finish(); // 退出
            }
        } else if (event.getKeyCode() == KeyEvent.KEYCODE_MENU) {
            return true;
        }
        return super.dispatchKeyEvent(event);
    }


    EMMessageListener messageListener = new EMMessageListener() {

        @Override
        public void onMessageReceived(List<EMMessage> messages) {
            // notify new message
            for (EMMessage message : messages) {
                DemoHelper.getInstance().getNotifier().onNewMsg(message);
            }
            refreshUIWithMessage();
        }

        @Override
        public void onCmdMessageReceived(List<EMMessage> list) {
            for (EMMessage message : list) {
                EMCmdMessageBody cmdMsgBody = (EMCmdMessageBody) message.getBody();
                final String action = cmdMsgBody.action();//获取自定义action
            }
            //end of red packet code
            refreshUIWithMessage();
        }

        @Override
        public void onMessageRead(List<EMMessage> messages) {
        }

        @Override
        public void onMessageDelivered(List<EMMessage> message) {
        }

        @Override
        public void onMessageRecalled(List<EMMessage> messages) {
            refreshUIWithMessage();
        }

        @Override
        public void onMessageChanged(EMMessage message, Object change) {
        }
    };


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putBoolean("isConflict", isConflict);
        outState.putBoolean(Constant.ACCOUNT_REMOVED, isCurrentAccountRemoved);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (!isConflict && !isCurrentAccountRemoved) {
            updateUnreadLabel();
            updateUnreadAddressLable();
        }

        // unregister this event listener when this activity enters the
        // background
        DemoHelper sdkHelper = DemoHelper.getInstance();
        sdkHelper.pushActivity(this);

        EMClient.getInstance().chatManager().addMessageListener(messageListener);
    }

    @Override
    protected void onStop() {
        EMClient.getInstance().chatManager().removeMessageListener(messageListener);
        EMClient.getInstance().removeClientListener(clientListener);
        DemoHelper sdkHelper = DemoHelper.getInstance();
        sdkHelper.popActivity(this);

        super.onStop();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (exceptionBuilder != null) {
            exceptionBuilder.create().dismiss();
            exceptionBuilder = null;
            isExceptionDialogShow = false;
        }
        unregisterBroadcastReceiver();
    }
}
