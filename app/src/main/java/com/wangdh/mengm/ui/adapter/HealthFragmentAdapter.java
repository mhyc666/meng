package com.wangdh.mengm.ui.adapter;

import android.support.annotation.Nullable;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wangdh.mengm.R;
import com.wangdh.mengm.bean.HealthitemData;
import java.util.List;

public class HealthFragmentAdapter extends BaseQuickAdapter<HealthitemData.ShowapiResBodyBean.ListBean,BaseViewHolder>{
    public HealthFragmentAdapter(@Nullable List<HealthitemData.ShowapiResBodyBean.ListBean> data) {
        super(R.layout.item_health, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, HealthitemData.ShowapiResBodyBean.ListBean item) {
        helper.setText(R.id.tv_wechat_item,item.getName())
                .addOnClickListener(R.id.wechat_item);
    }
}
