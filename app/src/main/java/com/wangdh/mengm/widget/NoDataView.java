package com.wangdh.mengm.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import com.wangdh.mengm.R;

/**
 * wdh
 */

public class NoDataView extends RelativeLayout{
    private Context context;

    public NoDataView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    private void initView(Context context) {
        this.context=context;
        View view = LayoutInflater.from(context).inflate(R.layout.view_nodata_layout, this);
    }
}
