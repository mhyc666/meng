package com.wangdh.mengm.ui.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.design.internal.NavigationMenu;
import android.view.MenuItem;

import com.wangdh.mengm.R;
import com.wangdh.mengm.base.BaseActivity;
import com.wangdh.mengm.base.Constant;
import com.wangdh.mengm.component.AppComponent;
import com.wangdh.mengm.utils.MyGlideImageLoader;
import com.wangdh.mengm.widget.PinchImageView;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import butterknife.BindView;
import io.github.yavski.fabspeeddial.FabSpeedDial;

public class PicturesActivity extends BaseActivity {
    public String mImgUrl;
    public String mImgName;
    @BindView(R.id.phoioview)
    PinchImageView phoioview;
    @BindView(R.id.fab_speed_dial)
    FabSpeedDial fab;
    private Bitmap bitmap;
    @Override
    protected void setupActivityComponent(AppComponent appComponent) {

    }

    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_pictures;
    }

    @Override
    protected void initView() {
        MyGlideImageLoader.displayImage(mImgUrl, phoioview);
        fab.setMenuListener(new FabSpeedDial.MenuListener() {

            @Override
            public boolean onPrepareMenu(NavigationMenu navigationMenu) {
                return true;
            }

            @Override
            public boolean onMenuItemSelected(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.action_back:
                        onBackPressed();//当用户在按这个键的时候，会调用这个方法
                        break;
                    case R.id.action_save:
                        toast("图片保存在" + Constant.SAVED_PATH);
                        phoioview.buildDrawingCache();
                        bitmap = phoioview.getDrawingCache();
                        if (bitmap == null) {
                            toast("无法下载到图片,请检查网络");
                        }
                        new Thread() {
                            @Override
                            public void run() {
                                saveImage();
                            }
                        }.start();
                        break;
                }
                return true;
            }

            @Override
            public void onMenuClosed() {

            }
        });
    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        mImgUrl = intent.getStringExtra("IMG_URL");
        mImgName = intent.getStringExtra("IMG_NAME");
    }

    /**
     * 保存图片
     */
    private void saveImage() {
        //将bitmap转换成二进制，写入本地
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        File dir = new File(Constant.SAVED_PATH);
        if (!dir.exists()) {
            dir.mkdir();
        }
        File file = new File(dir, "pic_" + mImgName + ".png");

        try {
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(byteArray, 0, byteArray.length);
            fos.flush();
            //使用广播通知系统相册进行更新相册
            Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            Uri uri = Uri.fromFile(file);
            intent.setData(uri);
            PicturesActivity.this.sendBroadcast(intent);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
