package o.krymov.photon.ru.ui.screens.search.filter;


import android.os.Bundle;

import o.krymov.photon.ru.R;
import o.krymov.photon.ru.di.DaggerService;
import o.krymov.photon.ru.di.scopes.DaggerScope;
import o.krymov.photon.ru.flow.AbstractScreen;
import o.krymov.photon.ru.flow.Screen;
import o.krymov.photon.ru.mvp.models.SearchModel;
import o.krymov.photon.ru.mvp.presenters.AbstractPresenter;
import o.krymov.photon.ru.ui.screens.search.SearchScreen;

import dagger.Provides;
import mortar.MortarScope;

@Screen(R.layout.screen_filter)
public class FilterScreen extends AbstractScreen<SearchScreen.Component> {
    @Override
    public Object createScreenComponent(SearchScreen.Component parentComponent) {
        return DaggerFilterScreen_Component.builder()
                .component(parentComponent)
                .module(new Module())
                .build();
    }


    //region ===================== DI =========================

    @dagger.Module
    public class Module {
        @Provides
        @DaggerScope(FilterScreen.class)
        FilterPresenter provideFilterPresenter() {
            return new FilterPresenter();
        }

    }

    @dagger.Component(dependencies = SearchScreen.Component.class, modules = Module.class)
    @DaggerScope(FilterScreen.class)
    public interface Component {
        void inject(FilterPresenter presenter);

        void inject(FilterView view);

    }
    //endregion ================== DI =========================


    //region ===================== Presenter =========================

    public class FilterPresenter extends AbstractPresenter<FilterView, SearchModel> {


        @Override
        protected void initDagger(MortarScope scope) {
            ((Component) scope.getService(DaggerService.SERVICE_NAME)).inject(this);
        }

        @Override
        protected void initActionBar() {
        }


    }
    //endregion ================== Presenter =========================
}
