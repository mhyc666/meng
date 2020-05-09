package com.wangdh.mengm.ui.activity;


import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import com.wangdh.mengm.R;
import com.wangdh.mengm.base.BaseActivity;
import com.wangdh.mengm.component.AppComponent;
import com.wangdh.mengm.widget.FilterImageView;

import java.util.HashMap;

import butterknife.BindView;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

public class VideoActivity extends BaseActivity {
    @BindView(R.id.videoplayer)
    JCVideoPlayerStandard videoplayer;
    @BindView(R.id.filter_iv)
    FilterImageView back;

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {

    }

    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_video;
    }

    @Override
    protected void initView() {
        back.setOnClickListener(v -> {
            finish();
            overridePendingTransition(R.anim.fade_exit, R.anim.hold);
        });
    }

    @Override
    protected void initData() {
        String url = getIntent().getStringExtra("videourl");

        videoplayer.setUp(url, JCVideoPlayerStandard.SCREEN_LAYOUT_NORMAL, "");
        videoplayer.thumbImageView.setImageBitmap(getNetVideoBitmap(url));
    }
    /**
     *  服务器返回url，通过url去获取视频的第一帧
     *  Android 原生给我们提供了一个MediaMetadataRetriever类
     *  提供了获取url视频第一帧的方法,返回Bitmap对象
     *
     *  @param videoUrl
     *  @return
     */
    public static Bitmap getNetVideoBitmap(String videoUrl) {
        Bitmap bitmap = null;

        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        try {
            //根据url获取缩略图
            retriever.setDataSource(videoUrl, new HashMap());
            //获得第一帧图片
            bitmap = retriever.getFrameAtTime();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } finally {
            retriever.release();
        }
        return bitmap;
    }

    @Override
    public void onBackPressed() {
        if (JCVideoPlayer.backPress()) {
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        JCVideoPlayer.releaseAllVideos();
    }
}
