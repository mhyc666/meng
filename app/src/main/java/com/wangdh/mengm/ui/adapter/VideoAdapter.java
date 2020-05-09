package com.wangdh.mengm.ui.adapter;


import android.support.annotation.Nullable;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wangdh.mengm.R;
import com.wangdh.mengm.bean.VideoData;
import com.wangdh.mengm.utils.DateUtils;
import com.wangdh.mengm.utils.MyGlideImageLoader;
import java.util.List;

public class VideoAdapter extends BaseQuickAdapter<VideoData.ShowapiResBodyBean.PagebeanBean.ContentlistBean, BaseViewHolder> {
    public VideoAdapter(@Nullable List<VideoData.ShowapiResBodyBean.PagebeanBean.ContentlistBean> data) {
        super(R.layout.item_video, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, VideoData.ShowapiResBodyBean.PagebeanBean.ContentlistBean item) {
        helper.setText(R.id.name, item.getName())
                .setText(R.id.time, DateUtils.friendlyTime(item.getCreate_time()))
                .setText(R.id.text, item.getText().trim())
                .addOnClickListener(R.id.video_item);
        MyGlideImageLoader.displayImage(item.getProfile_image(), helper.getView(R.id.tx));
    }
}
