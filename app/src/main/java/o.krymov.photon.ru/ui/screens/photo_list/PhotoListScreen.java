package o.krymov.photon.ru.ui.screens.photo_list;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.MenuItem;

import o.krymov.photon.ru.R;
import o.krymov.photon.ru.data.storage.realm.PhotoCardRealm;
import o.krymov.photon.ru.di.DaggerService;
import o.krymov.photon.ru.di.scopes.DaggerScope;
import o.krymov.photon.ru.flow.AbstractScreen;
import o.krymov.photon.ru.flow.Screen;
import o.krymov.photon.ru.mvp.models.PhotoCardsModel;
import o.krymov.photon.ru.mvp.presenters.AbstractPresenter;
import o.krymov.photon.ru.mvp.presenters.MenuItemHolder;
import o.krymov.photon.ru.mvp.presenters.RootPresenter;
import o.krymov.photon.ru.ui.activities.RootActivity;
import o.krymov.photon.ru.ui.activities.SplashActivity;
import o.krymov.photon.ru.ui.screens.photo_card.PhotoCardScreen;
import o.krymov.photon.ru.ui.screens.search.SearchScreen;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import dagger.Provides;
import flow.Flow;
import mortar.MortarScope;
import rx.Subscriber;
import rx.Subscription;

@Screen(R.layout.screen_photo_list)
public class PhotoListScreen extends AbstractScreen<RootActivity.RootComponent>{
    @Override
    public Object createScreenComponent(RootActivity.RootComponent parentComponent) {
        return DaggerPhotoListScreen_Component.builder()
                .rootComponent(parentComponent)
                .module(new Module())
                .build();

    }

    //region ===================== DI =========================

    @dagger.Module
    public class Module {
        @Provides
        @DaggerScope(PhotoListScreen.class)
        PhotoCardsModel providePhotoCardsModel(){ return new PhotoCardsModel(); }

        @Provides
        @DaggerScope(PhotoListScreen.class)
        PhotoListPresenter providePhotoCardsPresenter(){ return new PhotoListPresenter(); }

    }

    @dagger.Component(dependencies = RootActivity.RootComponent.class, modules = Module.class)
    @DaggerScope(PhotoListScreen.class)
    public interface Component{
        void inject(PhotoListPresenter photoListPresenter);
        void inject(PhotoListView photoListView);

        PhotoCardsModel photoCardsModel();

        Picasso getPicasso();

        RootPresenter getRootPresenter();
    }

    //endregion ================== DI =========================

    //region ===================== SearchPresenter =========================

    public class PhotoListPresenter extends AbstractPresenter<PhotoListView, PhotoCardsModel>{
        private Subscription mProductSubs;
        private int lastPagerPosition = 0;

        @Override
        protected void initDagger(MortarScope scope) {
            ((Component)scope.getService(DaggerService.SERVICE_NAME)).inject(this);
        }

        @Override
        protected void initActionBar() {
            if (getRootView() != null) {
                String appName = ((RootActivity) getRootView()).getString(R.string.app_name);
                ((RootActivity) getRootView()).setActionBarTitle(appName);

                MenuItemHolder miSettings = new MenuItemHolder("Settings", R.drawable.ic_custom_gear_black_24dp, null, true);
                if (mModel.getDataManager().isAuthUser()){
                    miSettings.addSubMenuItem(new MenuItemHolder("Exit", 0, this::menuItemLogoutSelect, true));
                }else{
                    miSettings.addSubMenuItem(new MenuItemHolder("Enter", 0, this::menuItemLoginSelect, true));
                    miSettings.addSubMenuItem(new MenuItemHolder("Registration", 0, this::menuItemRegisterSelect, true));
                }

                mRootPresenter.newActionBarBuilder()
                        .setTitle(appName)
                        .setBackArrow(false)
                        .setVisible(true)
                        .addAction(new MenuItemHolder("Search", R.drawable.ic_custom_search_black_24dp, this::menuItemSearchSelect, true))
                        .addAction(miSettings)
                        .build();
            }
        }


        //region ===================== Menu Item Select Listeners =========================

        private boolean menuItemLoginSelect(@NonNull MenuItem item){
            // TODO: 29.07.2017 Make user login
            mModel.getDataManager().setUserAuth(true);
            initActionBar();
            return true;
        }

        private boolean menuItemLogoutSelect(@NonNull MenuItem item){
            // TODO: 29.07.2017 Make user logout
            mModel.getDataManager().setUserAuth(false);
            initActionBar();
            return true;
        }

        private boolean menuItemRegisterSelect(@NonNull MenuItem item){
            if (getRootView() != null) {
                getRootView().showMessage(item.getTitle().toString());
            }
            return true;
        }

        private boolean menuItemSearchSelect(@NonNull MenuItem item){
            if (getView() != null) {
                Flow.get(getView()).set(new SearchScreen());
            }
            return true;
        }

        //endregion ================== Menu Item Select Listeners =========================



        @Override
        protected void onLoad(Bundle savedInstanceState) {
            super.onLoad(savedInstanceState);
            mCompSubs.add(subscribeOnProductRealmObs());

            getView().showPhotoListView();
        }

        @Override
        public void dropView(PhotoListView view) {
            super.dropView(view);
        }

        private Subscription subscribeOnProductRealmObs() {

            return mModel.getPhotoCardObs()
                    .subscribe(new RealmSubscriber());
        }

        private class RealmSubscriber extends Subscriber<PhotoCardRealm> {
            PhotoListAdapter mAdapter = getView().getAdapter();

            @Override
            public void onStart() {
                super.onStart();

//                if (getRootView() != null) {
//                    getRootView().showLoad();
//                }
            }

            @Override
            public void onCompleted() {
//                if (getRootView() != null) {
//                    getRootView().hideLoad();
//                }
            }

            @Override
            public void onError(Throwable e) {
                if (getRootView() != null) {
//                    getRootView().hideLoad();
                    getRootView().showError(e);
                }
            }

            @Override
            public void onNext(PhotoCardRealm photoCardRealm) {
                mAdapter.addItem(photoCardRealm);

            }
        }



    }
    //endregion ================== SearchPresenter =========================




}
