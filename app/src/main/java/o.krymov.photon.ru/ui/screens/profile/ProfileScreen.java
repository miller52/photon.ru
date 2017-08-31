package o.krymov.photon.ru.ui.screens.profile;


import android.support.annotation.NonNull;
import android.view.MenuItem;

import o.krymov.photon.ru.R;
import o.krymov.photon.ru.di.DaggerService;
import o.krymov.photon.ru.di.scopes.DaggerScope;
import o.krymov.photon.ru.flow.AbstractScreen;
import o.krymov.photon.ru.flow.Screen;
import o.krymov.photon.ru.mvp.models.ProfileModel;
import o.krymov.photon.ru.mvp.presenters.AbstractPresenter;
import o.krymov.photon.ru.mvp.presenters.MenuItemHolder;
import o.krymov.photon.ru.mvp.presenters.RootPresenter;
import o.krymov.photon.ru.ui.activities.RootActivity;

import dagger.Provides;
import mortar.MortarScope;

@Screen(R.layout.screen_profile)
public class ProfileScreen extends AbstractScreen<RootActivity.RootComponent> {
    @Override
    public Object createScreenComponent(RootActivity.RootComponent parentComponent){
        return DaggerProfileScreen_Component.builder()
                .rootComponent(parentComponent)
                .module(new Module())
                .build();
    }

    //region ===================== DI =========================

    @dagger.Module
    public class Module {
        @Provides
        @DaggerScope(ProfileScreen.class)
        ProfileModel provideProfileModel(){ return new ProfileModel(); }

        @Provides
        @DaggerScope(ProfileScreen.class)
        ProfilePresenter provideProfilePresenter(){ return new ProfilePresenter(); }

    }

    @dagger.Component(dependencies = RootActivity.RootComponent.class, modules = Module.class)
    @DaggerScope(ProfileScreen.class)
    public interface Component{
        void inject(ProfilePresenter profilePresenter);
        void inject(ProfileView profileView);

        ProfileModel profileModel();

        RootPresenter getRootPresenter();
    }

    //endregion ================== DI =========================

    //region ===================== SearchPresenter =========================

    public class ProfilePresenter extends AbstractPresenter<ProfileView, ProfileModel> {

        @Override
        protected void initDagger(MortarScope scope) {
            ((Component)scope.getService(DaggerService.SERVICE_NAME)).inject(this);
        }

        @Override
        protected void initActionBar() {
            if (getRootView() != null) {

                mRootPresenter.newActionBarBuilder()
                        .setTitle("Profile")
                        .setBackArrow(false)
                        .setVisible(true)
                        .addAction(new MenuItemHolder("New album", 0, this::menuItemSelect, false))
                        .addAction(new MenuItemHolder("Change profile", 0, this::menuItemSelect, false))
                        .addAction(new MenuItemHolder("Change avatar", 0, this::menuItemSelect, false))
                        .addAction(new MenuItemHolder("Exit", 0, this::menuItemSelect, false))
                        //.setTabs(getView().getViewPager())
                        .build();
            }
        }

        private boolean menuItemSelect(@NonNull MenuItem item){
            if (getRootView() != null) {
                getRootView().showMessage(item.getTitle().toString());
            }
            return true;
        }
    }
    //endregion ================== SearchPresenter =========================



}

