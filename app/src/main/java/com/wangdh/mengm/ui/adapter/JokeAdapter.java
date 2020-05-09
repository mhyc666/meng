package com.wangdh.mengm.ui.adapter;

import android.support.annotation.Nullable;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wangdh.mengm.R;
import com.wangdh.mengm.bean.JokeData;
import com.wangdh.mengm.utils.StringUtils;

import java.util.List;

public class JokeAdapter extends BaseQuickAdapter<JokeData.ShowapiResBodyBean.ContentlistBean,BaseViewHolder>{
    public JokeAdapter(@Nullable List<JokeData.ShowapiResBodyBean.ContentlistBean> data) {
        super(R.layout.item_joke, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, JokeData.ShowapiResBodyBean.ContentlistBean item) {
        helper.setText(R.id.title,item.getTitle())
                .setText(R.id.text, StringUtils.formatContent(item.getText()));
    }
}
