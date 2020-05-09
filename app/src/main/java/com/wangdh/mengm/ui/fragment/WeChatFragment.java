package com.wangdh.mengm.ui.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.chad.library.adapter.base.callback.ItemDragAndSwipeCallback;
import com.chad.library.adapter.base.listener.OnItemDragListener;
import com.flaviofaria.kenburnsview.KenBurnsView;
import com.flaviofaria.kenburnsview.Transition;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wangdh.mengm.R;
import com.wangdh.mengm.base.BaseFragment;
import com.wangdh.mengm.base.Constant;
import com.wangdh.mengm.bean.WeChatData;
import com.wangdh.mengm.bean.WechatImage;
import com.wangdh.mengm.component.AppComponent;
import com.wangdh.mengm.component.DaggerFragmentComponent;
import com.wangdh.mengm.ui.Presenter.WeChatFragmentPresenter;
import com.wangdh.mengm.ui.activity.WeChatListActivity;
import com.wangdh.mengm.ui.adapter.WeChatItemAdapter;
import com.wangdh.mengm.ui.contract.WeChatFragmentContract;
import com.wangdh.mengm.utils.MyGlideImageLoader;
import com.wangdh.mengm.utils.RecyclerViewUtil;
import com.wangdh.mengm.utils.SharedPreferencesMgr;
import com.wangdh.mengm.widget.FilterImageView;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

public class WeChatFragment extends BaseFragment implements WeChatFragmentContract.View {
    @BindView(R.id.imag_wechat)
    KenBurnsView imagWechat;
    @BindView(R.id.recycler_wechat)
    RecyclerView recyclerWechat;
    private WeChatItemAdapter adapter;
    private List<WeChatData> Datas = new ArrayList<>();
    private Gson mGson = new Gson();
    private String wechat_item = "wechat_item", imagename = "imagename";
    @Inject
    WeChatFragmentPresenter mPresenter;
    private View headerView, footerView;
    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerFragmentComponent.builder()
                .appComponent(appComponent)
                .build()
                .inject(this);
    }

    @Override
    protected int setLayoutResourceID() {
        return R.layout.fragment_wechat;
    }

    @Override
    protected void initData() {
        mPresenter.attachView(this);
        mPresenter.getImageData();
        String selectTitle = SharedPreferencesMgr.getString(wechat_item, "");
        if (TextUtils.isEmpty(selectTitle)) {
            String[] titleStr = getResources().getStringArray(R.array.wechat_name);
            String[] titlesCode = getResources().getStringArray(R.array.wechat_type);
            String[] titlesTypeId = getResources().getStringArray(R.array.wechat_typeid);
            //默认添加了全部频道
            for (int i = 0; i < titlesCode.length; i++) {
                String t = titleStr[i];
                String code = titlesCode[i];
                String type = titlesTypeId[i];
                Datas.add(new WeChatData(t, code, type));
            }
            String selectedStr = mGson.toJson(Datas);
            SharedPreferencesMgr.setString(wechat_item, selectedStr);
        } else {
            //之前添加过
            List<WeChatData> selecteData = mGson.fromJson(selectTitle, new TypeToken<List<WeChatData>>() {
            }.getType());
            Datas.addAll(selecteData);
        }
    }

    @Override
    protected void initView() {
        adapter = new WeChatItemAdapter(Datas);
        ItemDragAndSwipeCallback itemDragAndSwipeCallback = new ItemDragAndSwipeCallback(adapter);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(itemDragAndSwipeCallback);
        itemTouchHelper.attachToRecyclerView(recyclerWechat);
        // 开启拖拽
        adapter.enableDragItem(itemTouchHelper);
        adapter.setOnItemDragListener(onItemDragListener);
        RecyclerViewUtil.Gridinit(getActivity(), recyclerWechat, adapter, 3);
        adapter.setHeaderView(getHeaderView());
        adapter.setFooterView(getFooterView());
        adapter.setOnItemChildClickListener((adapter1, view, position) -> {
            if(Datas.get(position).getType().equals("520")){
                toast("其实点我并没有用-，-");
            }else {
                Intent intent = new Intent(getActivity(), WeChatListActivity.class);
                intent.putExtra("wechattype", Datas.get(position).getType());
                startActivity(intent);
            }
        });
    }

    private OnItemDragListener onItemDragListener = new OnItemDragListener() {
        @Override
        public void onItemDragStart(RecyclerView.ViewHolder viewHolder, int pos) {

        }

        @Override
        public void onItemDragMoving(RecyclerView.ViewHolder source, int from, RecyclerView.ViewHolder target, int to) {

        }

        @Override
        public void onItemDragEnd(RecyclerView.ViewHolder viewHolder, int pos) {
            WeChatData data = Datas.get(pos);
            Datas.remove(pos);
            Datas.add(pos, data);
            //保存
            SharedPreferencesMgr.setString(wechat_item, mGson.toJson(Datas));
        }
    };

    @Override
    public void showError(String s) {
        toast(s);
    }

    @Override
    public void complete() {

    }

    @Override
    public void showImageData(WechatImage data) {
        TextView mtv = (TextView) headerView.findViewById(R.id.tv_bt);
        imagename = data.getImages().get(0).getCopyright();
        mtv.setText("\n"+imagename);
        MyGlideImageLoader.displayImage("http://www.bing.com" + data.getImages().get(0).getUrl(), imagWechat);
        imagWechat.setTransitionListener(new KenBurnsView.TransitionListener() {
            @Override
            public void onTransitionStart(Transition transition) {

            }

            @Override
            public void onTransitionEnd(Transition transition) {

            }
        });
    }


    public View getHeaderView() {
        headerView = LayoutInflater.from(getContext()).inflate(R.layout.wechatheaderview, (ViewGroup) recyclerWechat.getParent(), false);
        return headerView;
    }

    public View getFooterView() {
        footerView = LayoutInflater.from(getContext()).inflate(R.layout.wechatheaderview, (ViewGroup) recyclerWechat.getParent(), false);
        return footerView;
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.detachView();
        }
    }
}
