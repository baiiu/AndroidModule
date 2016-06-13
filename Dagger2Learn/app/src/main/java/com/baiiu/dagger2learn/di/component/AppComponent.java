package com.baiiu.dagger2learn.di.component;

import android.content.Context;
import com.baiiu.dagger2learn.MyApplication;
import com.baiiu.dagger2learn.di.module.ApplicationModule;
import com.baiiu.dagger2learn.di.scope.PerApp;
import dagger.Component;

/**
 * author: baiiu
 * date: on 16/6/12 16:04
 * description:
 */
@PerApp //标明该Component中有Module使用了@PerApp
@Component(
        modules = { ApplicationModule.class }

)
public interface AppComponent {
    Context getContext();

    void inject(MyApplication myApplication);
}
