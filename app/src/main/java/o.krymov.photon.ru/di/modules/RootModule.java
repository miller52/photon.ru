package o.krymov.photon.ru.di.modules;

import o.krymov.photon.ru.di.scopes.DaggerScope;
import o.krymov.photon.ru.mvp.presenters.RootPresenter;
import o.krymov.photon.ru.ui.activities.RootActivity;

import dagger.Provides;

//import o.krymov.photon.ru.mvp.models.AccountModel;


@dagger.Module
public class RootModule {
    @Provides
    @DaggerScope(RootActivity.class)
    RootPresenter provideRootPresenter() {
        return new RootPresenter();
    }

//    @Provides
//    @RootScope
//    AccountModel provideAccountModel(){
//        return new AccountModel();
//    }
}
