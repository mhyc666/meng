package com.wangdh.mengm.ui.adapter;

import android.support.annotation.Nullable;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wangdh.mengm.R;
import com.wangdh.mengm.bean.MeizhiData;
import com.wangdh.mengm.utils.MyGlideImageLoader;

import java.util.List;

public class MeizhiAdapter extends BaseQuickAdapter<MeizhiData.ResultsBean, BaseViewHolder> {
    public MeizhiAdapter(@Nullable List<MeizhiData.ResultsBean> data) {
        super(R.layout.item_funnypictures, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MeizhiData.ResultsBean item) {
        helper.addOnClickListener(R.id.item_funny);
        helper.getView(R.id.tv_imgjoke).setVisibility(View.GONE);
        MyGlideImageLoader.displayImage(item.getUrl(), helper.getView(R.id.iv_view));
    }
}
