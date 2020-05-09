package com.wangdh.mengm.ui.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wangdh.mengm.R;
import com.wangdh.mengm.bean.FunnyPicturesData;
import com.wangdh.mengm.utils.MyGlideImageLoader;
import java.util.List;

public class FunnyPicturesApapter extends BaseQuickAdapter<FunnyPicturesData.ShowapiResBodyBean.ContentlistBean, BaseViewHolder> {
    public FunnyPicturesApapter(@Nullable List<FunnyPicturesData.ShowapiResBodyBean.ContentlistBean> data) {
        super(R.layout.item_funnypictures, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, FunnyPicturesData.ShowapiResBodyBean.ContentlistBean item) {
        helper.setText(R.id.tv_imgjoke, item.getTitle())
        .addOnClickListener(R.id.item_funny);
        MyGlideImageLoader.displayImage(item.getImg(), helper.getView(R.id.iv_view));
    }
}
