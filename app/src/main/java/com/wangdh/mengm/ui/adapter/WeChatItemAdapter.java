package com.wangdh.mengm.ui.adapter;

import android.widget.ImageView;
import com.chad.library.adapter.base.BaseItemDraggableAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wangdh.mengm.R;
import com.wangdh.mengm.bean.WeChatData;
import java.util.List;

public class WeChatItemAdapter  extends BaseItemDraggableAdapter<WeChatData,BaseViewHolder>{
    public WeChatItemAdapter(List<WeChatData> data) {
        super(R.layout.item_wechat,data);
    }

    @Override
    protected void convert(BaseViewHolder helper, WeChatData item) {
        helper.setText(R.id.tv_wechat_item,item.getName())
        .addOnClickListener(R.id.wechat_item);
        ImageView view =helper.getView(R.id.iv_wechat_item);
        //通过字符串来获取R下面资源的ID 值
        try {   //此方法图片必须在drawble下面
            int camera = (Integer) R.drawable.class.getField(item.getCode()).get(null);
            view.setImageResource(camera);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }
}
//   方法2
//    Resources res=getResources();
//    int i=res.getIdentifier("icon","drawable",getPackageName());