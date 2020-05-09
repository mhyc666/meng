package com.wangdh.mengm.ui.adapter;

import android.support.annotation.Nullable;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wangdh.mengm.R;
import com.wangdh.mengm.bean.CookBookslistData;
import com.wangdh.mengm.utils.MyGlideImageLoader;

import java.util.List;

public class CookBooksItemAdapter extends BaseQuickAdapter<CookBookslistData.ResultBeanX.ResultBean.ListBean,BaseViewHolder>{
    public CookBooksItemAdapter(@Nullable List<CookBookslistData.ResultBeanX.ResultBean.ListBean> data) {
        super(R.layout.item_cookbookslist,data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CookBookslistData.ResultBeanX.ResultBean.ListBean item) {
      helper.setText(R.id.cook_name,item.getName())
              .setText(R.id.cook_text,item.getContent().replaceAll("<br/>","").replaceAll("<br />",""))
              .setText(R.id.time, "烹饪时间:"+item.getCookingtime())
              .setText(R.id.num,"用餐人数:"+item.getPeoplenum())
              .addOnClickListener(R.id.cook_item);
        MyGlideImageLoader.displayImage(item.getPic(),helper.getView(R.id.cook_image));
    }
}
