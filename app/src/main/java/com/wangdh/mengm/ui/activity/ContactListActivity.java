package com.wangdh.mengm.ui.activity;

import android.os.Bundle;

import com.hyphenate.easeui.ui.EaseBaseActivity;
import com.wangdh.mengm.R;
import com.wangdh.mengm.ui.fragment.ContactListFragment;


public class ContactListActivity extends EaseBaseActivity{
  private ContactListFragment fragment;
    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.em_activity_chat);
        fragment=new ContactListFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.container,fragment).commit();
    }

}
