package com.wangdh.mengm.ui.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.hyphenate.chat.EMClient;
import com.wangdh.mengm.R;
import com.wangdh.mengm.base.BaseFragment;
import com.wangdh.mengm.base.Constant;
import com.wangdh.mengm.component.AppComponent;
import com.wangdh.mengm.manager.LoginEvent;
import com.wangdh.mengm.ui.activity.ContactListActivity;
import com.wangdh.mengm.ui.activity.LoginActivity;
import com.wangdh.mengm.ui.activity.WebViewDetailsActivity;
import com.wangdh.mengm.utils.ACache;
import com.wangdh.mengm.utils.MyGlideImageLoader;
import com.wangdh.mengm.utils.SharedPreferencesMgr;
import com.wangdh.mengm.utils.StorageData;
import com.wangdh.mengm.widget.PinchImageView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 我的
 */

public class MyFragment extends BaseFragment {
    @BindView(R.id.imageView)
    CircleImageView imageView;
    @BindView(R.id.my_tv)
    TextView myTv;
    @BindView(R.id.coll_layout)
    CollapsingToolbarLayout collLayout;
    @BindView(R.id.my_toolbar)
    Toolbar toolbar;
    @BindView(R.id.i_switch)
    Switch mSwitch;
    @BindView(R.id.login)
    Button mButton;
    @BindView(R.id.cache)
    TextView cacheTv;
    @BindView(R.id.cachesize)
    TextView cachesize;
    @BindView(R.id.exit)
    TextView edxtTv;
    @BindView(R.id.gyu)
    TextView gy;
    @BindView(R.id.web_tv)
    TextView webTv;
    @BindView(R.id.layout_gyu)
    LinearLayout layout;
    @BindView(R.id.friend_tv)
    TextView mTv;
    private Dialog dialog;
    private static final int PHOTO_REQUEST_TAKEPHOTO = 1;// 拍照
    private static final int PHOTO_REQUEST_GALLERY = 2;// 从相册中选择
    private static final int PHOTO_REQUEST_CUT = 3;// 结果
    // 创建一个以当前时间为名称的文件
    private File tempFile = new File(Environment.getExternalStorageDirectory(), getPhotoFileName());
    private File f = new File(Constant.PATH_DATA, "data");
    private boolean b = true;

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {

    }

    @Override
    protected int setLayoutResourceID() {
        return R.layout.fragment_my;
    }

    @Override
    protected void initData() {
        EventBus.getDefault().register(this);
        mTv.setOnClickListener(v -> startActivity(new Intent(getActivity(), ContactListActivity.class)));
        cachesize.setText("文字：" + ACache.getCacheSize(f) + "  图片：" + MyGlideImageLoader.getCacheSize(getContext()));
        StorageData.setHeadImage(imageView, StorageData.getString(), getContext());  //头像
        if (!SharedPreferencesMgr.getString("password", "").equals("") &&
                !SharedPreferencesMgr.getString("name", "").equals("")) {
            mButton.setText("已登陆");
            mButton.setBackgroundResource(R.drawable.button_all_round2);
        }

        if (!SharedPreferencesMgr.getBoolean("image", true)) {
            mSwitch.setChecked(true);
        }
    }

    @Override
    protected void initView() {
        intToolbar();
        imageView.setOnClickListener(v -> Dialog());
        mButton.setOnClickListener(v ->
                startActivity(new Intent(getActivity(), LoginActivity.class)));
        if (!SharedPreferencesMgr.getString("name", "").equals("")) {
            myTv.setText(SharedPreferencesMgr.getString("name", ""));
        }
        edxtTv.setOnClickListener(v -> {
            if (!SharedPreferencesMgr.getString("password", "").equals("") &&
                    !SharedPreferencesMgr.getString("name", "").equals("")) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("退出登陆").setIcon(R.mipmap.login_qc)
                        .setMessage("确定退出登陆")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                SharedPreferencesMgr.remove(getContext(), "name");
                                SharedPreferencesMgr.remove(getContext(), "password");
                                EMClient.getInstance().logout(true);
                                toast("您已退出登陆");
                                mButton.setText("登陆");
                                mButton.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.loginButton));
                            }
                        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.i("toast", "取消了");
                    }
                });
                builder.create().show();
            } else {
                toast("您没有登陆，不需要退出登陆");
            }
        });


        cacheTv.setOnClickListener(v -> {
            toast("清除缓存...");
            ACache.deleteDir(f);
            MyGlideImageLoader.clearImageAllCache(getContext());
            cachesize.setText("0");
        });


        mSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                ACache.deleteDir(f);
                MyGlideImageLoader.clearImageAllCache(getContext());
                SharedPreferencesMgr.setBoolean("image", false);
            } else {
                SharedPreferencesMgr.setBoolean("image", true);
            }
        });

        gy.setOnClickListener(v -> {
            if (b) {
                layout.setVisibility(View.VISIBLE);
                b = false;
            } else {
                layout.setVisibility(View.GONE);
                b = true;
            }
        });

        webTv.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), WebViewDetailsActivity.class);
            intent.putExtra("wechaturl", "https://github.com/mhyc666/meng");
            startActivity(intent);
        });
    }

    private void Dialog() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.photo_choose_dialog, null);
        initViews(view);
        dialog = new Dialog(getContext(), R.style.transparentFrameWindowStyle);
        dialog.setContentView(view, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        Window window = dialog.getWindow();
        // 设置显示动画
        window.setWindowAnimations(R.style.main_menu_animstyle);
        WindowManager.LayoutParams wl = window.getAttributes();
        wl.x = 0;
        wl.y = getActivity().getWindowManager().getDefaultDisplay().getHeight();
        // 以下这两句是为了保证按钮可以水平满屏
        wl.width = ViewGroup.LayoutParams.MATCH_PARENT;
        wl.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        // 设置显示位置
        dialog.onWindowAttributesChanged(wl);
        // 设置点击外围解散
        dialog.setCanceledOnTouchOutside(true);
        getActivity().setFinishOnTouchOutside(true);
        dialog.show();
    }

    private void initViews(final View view) {
        Button mBt1, mBt2, mBt3, mBt4;
        mBt1 = (Button) view.findViewById(R.id.bt);
        mBt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 调用系统的拍照功能
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                // 指定调用相机拍照后照片的储存路径
                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(tempFile));
                startActivityForResult(intent, PHOTO_REQUEST_TAKEPHOTO);
            }
        });
        mBt2 = (Button) view.findViewById(R.id.bt2);
        mBt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //打开相册
                Intent intent = new Intent(Intent.ACTION_PICK, null);
                intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(intent, PHOTO_REQUEST_GALLERY);
            }
        });
        mBt3 = (Button) view.findViewById(R.id.bt3);
        mBt3.setOnClickListener(new View.OnClickListener() {        //放大图片
            @Override
            public void onClick(View v) {
                final AlertDialog dialog = new AlertDialog.Builder(getContext()).create();
                dialog.show();
                Window window = dialog.getWindow();
                window.setContentView(R.layout.imageshower);
                PinchImageView dialogIv = (PinchImageView) dialog.findViewById(R.id.dialogImage);
                dialogIv.setImageDrawable(imageView.getDrawable());

                dialogIv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
            }
        });
        mBt4 = (Button) view.findViewById(R.id.qx);
        mBt4.setOnClickListener(new View.OnClickListener() {    //取消dialog
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }


    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        super.startActivityForResult(intent, requestCode);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case PHOTO_REQUEST_TAKEPHOTO:
                startPhotoZoom(Uri.fromFile(tempFile), 150);
                break;
            case PHOTO_REQUEST_GALLERY:
                if (data != null)
                    startPhotoZoom(data.getData(), 150);
                break;
            case PHOTO_REQUEST_CUT:
                if (data != null)
                    setPicToView(data);
                break;
        }

    }

    private void startPhotoZoom(Uri uri, int size) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        // crop为true是设置在开启的intent中设置显示的view可以剪裁
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX,outputY 是剪裁图片的宽高
        intent.putExtra("outputX", size);
        intent.putExtra("outputY", size);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, PHOTO_REQUEST_CUT);
    }

    private String urlpath;

    //将进行剪裁后的图片显示到UI界面上
    private void setPicToView(Intent picdata) {
        Bundle bundle = picdata.getExtras();
        if (bundle != null) {
            Bitmap photo = bundle.getParcelable("data");
            imageView.setImageBitmap(photo);
            urlpath = StorageData.saveFile(getContext(), "temphead.jpg", photo);
            StorageData.storePath(urlpath);
        }
    }

    // 使用系统当前日期加以调整作为照片的名称
    private String getPhotoFileName() {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat("'IMG'_yyyyMMdd_HHmmss");
        return dateFormat.format(date) + ".jpg";
    }

    private void intToolbar() {
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        // toolbar.setNavigationIcon(R.drawable.ximalogo);
        toolbar.setTitle("我的");
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void LoginText(LoginEvent event) {
        mButton.setText("已登陆");
        mButton.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.tabGray));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
