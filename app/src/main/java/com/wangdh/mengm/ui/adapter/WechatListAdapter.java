package com.wangdh.mengm.ui.adapter;

import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.text.TextUtils;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wangdh.mengm.R;
import com.wangdh.mengm.bean.WeChatListData;
import com.wangdh.mengm.utils.MyGlideImageLoader;
import com.wangdh.mengm.utils.SharedPreferencesMgr;

import java.util.List;

public class WechatListAdapter extends BaseQuickAdapter<WeChatListData.ShowapiResBodyBean.PagebeanBean.ContentlistBean, BaseViewHolder> {

    public WechatListAdapter(@Nullable List<WeChatListData.ShowapiResBodyBean.PagebeanBean.ContentlistBean> data) {
        super(R.layout.item_wechatlist, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, WeChatListData.ShowapiResBodyBean.PagebeanBean.ContentlistBean item) {
        helper.setText(R.id.tv_wechatitem, TextUtils.isEmpty(item.getTitle()) ? mContext.getString(R.string.wechat_select) : item.getTitle())
                .addOnClickListener(R.id.wechat_listitem);
//        if (helper.getAdapterPosition()==1){
//            MyGlideImageLoader.loadIntoUseFitWidth(item.getContentImg(), helper.getView(R.id.iv_wecharitem), (float) 1.42);
//
//        }else {
        if(!SharedPreferencesMgr.getBoolean("image",true)){
          ImageView iv= helper.getView(R.id.iv_wecharitem);
           iv.setImageResource(R.mipmap.timg);
        }else {
            MyGlideImageLoader.displayImage(item.getContentImg(), helper.getView(R.id.iv_wecharitem));
        }

       // }

    }
}
