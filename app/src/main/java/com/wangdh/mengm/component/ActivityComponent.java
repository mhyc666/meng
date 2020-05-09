package com.wangdh.mengm.component;

import com.wangdh.mengm.ui.activity.CookBooksListActivity;
import com.wangdh.mengm.ui.activity.HealthListActivity;
import com.wangdh.mengm.ui.activity.JokeActivity;
import com.wangdh.mengm.ui.activity.MeizhiPictureActivity;
import com.wangdh.mengm.ui.activity.SearchCookBooksActivity;
import com.wangdh.mengm.ui.activity.SplashActivity;
import com.wangdh.mengm.ui.activity.VideoListActivity;
import com.wangdh.mengm.ui.activity.WeChatListActivity;
import com.wangdh.mengm.ui.activity.WeatherActivity;

import dagger.Component;

/**
 * wdh
 */
@Component(dependencies = AppComponent.class)
public interface ActivityComponent {
    SplashActivity inject(SplashActivity activity);
    WeChatListActivity inject(WeChatListActivity activity);
    JokeActivity inject(JokeActivity activity);
    MeizhiPictureActivity inject(MeizhiPictureActivity activity);
    VideoListActivity inject(VideoListActivity activity);
    CookBooksListActivity inject(CookBooksListActivity activity);
    HealthListActivity inject(HealthListActivity activity);
    WeatherActivity inject(WeatherActivity activity);
    SearchCookBooksActivity inject(SearchCookBooksActivity activity);
}
