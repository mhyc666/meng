package com.wangdh.mengm.ui.adapter;

import android.support.annotation.Nullable;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wangdh.mengm.R;
import com.wangdh.mengm.bean.NewListData;
import com.wangdh.mengm.utils.MyGlideImageLoader;

import java.util.List;


/**
 * wdh
 */

public class NewItemAdapter extends BaseQuickAdapter<NewListData.ResultBeanX.ResultBean.ListBean,BaseViewHolder>{

    public NewItemAdapter( @Nullable List<NewListData.ResultBeanX.ResultBean.ListBean> data) {
        super(R.layout.item_newlist, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, NewListData.ResultBeanX.ResultBean.ListBean item) {
        helper.setText(R.id.new_title,item.getTitle())
        .setText(R.id.time,item.getTime())
        .addOnClickListener(R.id.new_item);
        MyGlideImageLoader.displayImage(item.getPic(),helper.getView(R.id.new_image));
    }
}
