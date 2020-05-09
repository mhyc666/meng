package com.wangdh.mengm.ui.adapter;


import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wangdh.mengm.R;
import com.wangdh.mengm.bean.HealthitemListData;
import com.wangdh.mengm.utils.MyGlideImageLoader;
import java.util.List;

public class HealthListAdapter extends BaseQuickAdapter<HealthitemListData.ShowapiResBodyBean.PagebeanBean.ContentlistBean,BaseViewHolder>{
    public HealthListAdapter(@Nullable List<HealthitemListData.ShowapiResBodyBean.PagebeanBean.ContentlistBean> data) {
        super(R.layout.item_cookbookslist, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, HealthitemListData.ShowapiResBodyBean.PagebeanBean.ContentlistBean item) {
        helper.setText(R.id.cook_name,item.getTitle())
                .setText(R.id.cook_text,item.getIntro())
                .setText(R.id.time, "")
                .setText(R.id.num,item.getTname())
                .addOnClickListener(R.id.cook_item);
        MyGlideImageLoader.displayImage(item.getImg(),helper.getView(R.id.cook_image));
    }
}
