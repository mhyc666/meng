package com.wangdh.mengm.base;

import android.graphics.Color;
import android.os.Environment;

import com.hyphenate.easeui.EaseConstant;
import com.wangdh.mengm.utils.AppUtils;
import com.wangdh.mengm.utils.FileUtils;

public class Constant extends EaseConstant {
    public static final String BaseUrl="http://route.showapi.com/";
    public static final String jcloudKey="6d119cf4202fec65d699ebb68d1d6e5f";
    public static final String showapi_sign="f255043723fe40839e61f6a40a6b0741";
    public static final String showapi_appid="44640";
    public static final String weatherKey="65f888e8c8ef49539f89a249a5e296ed";
    public static final String SAVED_PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + "/meng";

    public static String PATH_DATA = FileUtils.createRootPath(AppUtils.getAppContext()) + "/cache";
    public static String PATH_TXT = PATH_DATA + "/mengm/";
    public static String PATH_EPUB = PATH_DATA + "/epub";
    public static final String SUFFIX_ZIP = ".zip";
    public static final int[] tagColors = new int[]{
            Color.parseColor("#90C5F0"),
            Color.parseColor("#91CED5"),
            Color.parseColor("#F88F55"),
            Color.parseColor("#C0AFD0"),
            Color.parseColor("#E78F8F"),
            Color.parseColor("#67CCB7"),
            Color.parseColor("#F6BC7E")
    };

    public static final String NEW_FRIENDS_USERNAME = "item_new_friends";
    public static final String GROUP_USERNAME = "item_groups";
    public static final String CHAT_ROOM = "item_chatroom";
    public static final String ACCOUNT_REMOVED = "account_removed";
    public static final String ACCOUNT_CONFLICT = "conflict";
    public static final String ACCOUNT_FORBIDDEN = "user_forbidden";
    public static final String ACCOUNT_KICKED_BY_CHANGE_PASSWORD = "kicked_by_change_password";
    public static final String ACCOUNT_KICKED_BY_OTHER_DEVICE = "kicked_by_another_device";
    public static final String CHAT_ROBOT = "item_robots";
    public static final String MESSAGE_ATTR_ROBOT_MSGTYPE = "msgtype";
    public static final String ACTION_GROUP_CHANAGED = "action_group_changed";
    public static final String ACTION_CONTACT_CHANAGED = "action_contact_changed";

    public static final String EXTRA_CONFERENCE_ID = "confId";
    public static final String EXTRA_CONFERENCE_PASS = "password";
    public static final String EXTRA_CONFERENCE_IS_CREATOR = "is_creator";
}
