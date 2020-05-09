package com.wangdh.mengm.ui.adapter;

import android.support.annotation.Nullable;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wangdh.mengm.R;
import com.wangdh.mengm.bean.CookBooksData;
import java.util.List;

public class CookBooksAdapter extends BaseQuickAdapter<CookBooksData.ResultBeanX.ResultBean, BaseViewHolder> {
    public CookBooksAdapter(@Nullable List<CookBooksData.ResultBeanX.ResultBean> data) {
        super(R.layout.item_cookbooks, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CookBooksData.ResultBeanX.ResultBean item) {
        helper.setText(R.id.cookbooks_tv,item.getName())
                .addOnClickListener(R.id.cookbooks_item);
    }
}
