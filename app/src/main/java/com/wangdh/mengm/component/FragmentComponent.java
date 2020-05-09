package com.wangdh.mengm.component;

import com.wangdh.mengm.ui.fragment.CookBooksFragment;
import com.wangdh.mengm.ui.fragment.FunnyPicturesFragment;
import com.wangdh.mengm.ui.fragment.HealthFragment;
import com.wangdh.mengm.ui.fragment.NewListFragment;
import com.wangdh.mengm.ui.fragment.WeChatFragment;

import dagger.Component;

@Component(dependencies = AppComponent.class)
public interface FragmentComponent {
    WeChatFragment inject(WeChatFragment fragment);
    NewListFragment inject(NewListFragment fragment);
    CookBooksFragment inject(CookBooksFragment fragment);
    HealthFragment inject(HealthFragment fragment);
    FunnyPicturesFragment inject(FunnyPicturesFragment fragment);
}
