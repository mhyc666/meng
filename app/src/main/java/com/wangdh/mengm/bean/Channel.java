package com.wangdh.mengm.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.io.Serializable;


public class Channel implements Serializable, MultiItemEntity {
    public static final int TYPE_MY = 1;
    public static final int TYPE_OTHER = 2;
    public static final int TYPE_MY_CHANNEL = 3;
    public static final int TYPE_OTHER_CHANNEL = 4;
    public String Title;
    public String TitleCode;
    private int itemType;

    public Channel(String title, String titleCode) {
        this(TYPE_MY_CHANNEL, title, titleCode);
    }

    public Channel(int type, String title, String titleCode) {
        Title = title;
        TitleCode = titleCode;
        itemType = type;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getTitleCode() {
        return TitleCode;
    }

    public void setTitleCode(String titleCode) {
        TitleCode = titleCode;
    }

    public void setItemType(int itemType) {
        this.itemType = itemType;
    }

    @Override
    public int getItemType() {
        if (itemType==1){
            return TYPE_MY;
        }else if(itemType==2){
            return TYPE_OTHER;
        }else if(itemType==3){
            return TYPE_MY_CHANNEL;
        }else {
            return TYPE_OTHER_CHANNEL;
        }

    }
}
