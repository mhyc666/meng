package com.wangdh.mengm.ui.activity;


import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.hyphenate.easeui.ui.EaseBaseActivity;
import com.wangdh.mengm.R;
import com.wangdh.mengm.bean.im.InviteMessage;
import com.wangdh.mengm.db.InviteMessgeDao;
import com.wangdh.mengm.ui.adapter.NewFriendsMsgAdapter;

import java.util.Collections;
import java.util.List;

public class NewFriendsMsgActivity extends EaseBaseActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.em_activity_new_friends_msg);

        ListView listView = (ListView) findViewById(R.id.list);
        InviteMessgeDao dao = new InviteMessgeDao(this);
        List<InviteMessage> msgs = dao.getMessagesList();
        Collections.reverse(msgs);

        NewFriendsMsgAdapter adapter = new NewFriendsMsgAdapter(this, 1, msgs);
        listView.setAdapter(adapter);
        dao.saveUnreadMessageCount(0);

    }

    public void back(View view) {
        finish();
    }
}
