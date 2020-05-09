package com.wangdh.mengm.ui.adapter;

import android.support.annotation.Nullable;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wangdh.mengm.R;
import com.wangdh.mengm.bean.CookBookslistData;
import com.wangdh.mengm.utils.MyGlideImageLoader;
import java.util.List;

public class CookBooksDetailsAdapter2 extends BaseQuickAdapter<CookBookslistData.ResultBeanX.ResultBean.ListBean.ProcessBean,BaseViewHolder>{
    public CookBooksDetailsAdapter2(@Nullable List<CookBookslistData.ResultBeanX.ResultBean.ListBean.ProcessBean> data) {
        super(R.layout.item_cookbooksdetails2, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CookBookslistData.ResultBeanX.ResultBean.ListBean.ProcessBean item) {
      helper.setText(R.id.tv,item.getPcontent());
        MyGlideImageLoader.displayImage(item.getPic(),helper.getView(R.id.iv));
    }
}
