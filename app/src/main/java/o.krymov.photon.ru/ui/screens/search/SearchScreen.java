package o.krymov.photon.ru.ui.screens.search;


import android.support.annotation.NonNull;

import o.krymov.photon.ru.R;
import o.krymov.photon.ru.di.DaggerService;
import o.krymov.photon.ru.di.scopes.DaggerScope;
import o.krymov.photon.ru.flow.AbstractScreen;
import o.krymov.photon.ru.flow.Screen;
import o.krymov.photon.ru.mvp.models.SearchModel;
import o.krymov.photon.ru.mvp.presenters.AbstractPresenter;
import o.krymov.photon.ru.mvp.presenters.RootPresenter;
import o.krymov.photon.ru.ui.activities.RootActivity;
import o.krymov.photon.ru.ui.screens.photo_list.PhotoListScreen;

import javax.inject.Inject;

import dagger.Provides;
import flow.TreeKey;
import mortar.MortarScope;

@Screen(R.layout.screen_search)
public class SearchScreen extends AbstractScreen<RootActivity.RootComponent> implements TreeKey {
    @Override
    public Object createScreenComponent(RootActivity.RootComponent parentComponent) {
        return DaggerSearchScreen_Component.builder()
                .rootComponent(parentComponent)
                .module(new Module())
                .build();
    }



    //region ===================== DI =========================
    @dagger.Module
    public class Module{
        @Provides
        @DaggerScope(SearchScreen.class)
        SearchPresenter provideSearchPresenter(){
            return new SearchPresenter();
        }

        @Provides
        @DaggerScope(SearchScreen.class)
        SearchModel provideSearchModel(){
            return new SearchModel();
        }
    }

    @dagger.Component(dependencies = RootActivity.RootComponent.class, modules = Module.class)
    @DaggerScope(SearchScreen.class)
    public interface Component{
        void inject(SearchPresenter presenter);
        void inject(SearchView view);

        SearchModel getDetailsModel();
        RootPresenter getRootPresenter();
    }

    //endregion ================== DI =========================

    //region ===================== SearchPresenter =========================
    public class SearchPresenter extends AbstractPresenter<SearchView, SearchModel> {
        @Inject
        SearchModel mModel;

        @Override
        protected void initDagger(MortarScope scope) {
            ((Component) scope.getService(DaggerService.SERVICE_NAME)).inject(this);
        }

        @Override
        protected void initActionBar() {
            mRootPresenter.newActionBarBuilder()
                    .setTabs(getView().getViewPager())
                    .build();
        }
    }
    //endregion ================== SearchPresenter =========================

    //region ===================== TreeKey =========================

    @NonNull
    @Override
    public Object getParentKey() {
        return new PhotoListScreen();
    }

    //endregion ================== TreeKey =========================


}
