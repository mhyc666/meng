package com.wangdh.mengm.ui.adapter;

import android.support.annotation.Nullable;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wangdh.mengm.R;
import com.wangdh.mengm.bean.CookBookslistData;
import java.util.List;

public class CookBooksDetailsAdapter extends BaseQuickAdapter<CookBookslistData.ResultBeanX.ResultBean.ListBean.MaterialBean,BaseViewHolder>{
    public CookBooksDetailsAdapter(@Nullable List<CookBookslistData.ResultBeanX.ResultBean.ListBean.MaterialBean> data) {
        super(R.layout.item_cookbooksdetails1, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CookBookslistData.ResultBeanX.ResultBean.ListBean.MaterialBean item) {
      helper.setText(R.id.tv1,item.getMname())
              .setText(R.id.tv2,item.getAmount());
    }
}
