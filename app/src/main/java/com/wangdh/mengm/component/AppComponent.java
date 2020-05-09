
package com.wangdh.mengm.component;

import android.content.Context;
import com.wangdh.mengm.api.RetrofitManager;
import com.wangdh.mengm.module.ApiModule;
import com.wangdh.mengm.module.AppModule;

import dagger.Component;

@Component(modules = {AppModule.class, ApiModule.class})
public interface AppComponent {

    Context getContext();

    RetrofitManager getAppApi();

}