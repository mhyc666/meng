package com.wangdh.mengm.ui.adapter;

import android.support.annotation.Nullable;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wangdh.mengm.R;
import com.wangdh.mengm.bean.CookBooksData;
import java.util.List;

public class CookBooksListAdapter extends BaseQuickAdapter<CookBooksData.ResultBeanX.ResultBean.ListBean,BaseViewHolder>{
    public CookBooksListAdapter( @Nullable List<CookBooksData.ResultBeanX.ResultBean.ListBean> data) {
        super(R.layout.item_health, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CookBooksData.ResultBeanX.ResultBean.ListBean item) {
        helper.setText(R.id.tv_wechat_item,item.getName())
                .addOnClickListener(R.id.wechat_item);
    }
}
