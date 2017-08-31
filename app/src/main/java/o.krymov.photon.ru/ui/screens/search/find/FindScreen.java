package o.krymov.photon.ru.ui.screens.search.find;


import o.krymov.photon.ru.R;
import o.krymov.photon.ru.data.managers.DataManager;
import o.krymov.photon.ru.di.DaggerService;
import o.krymov.photon.ru.di.scopes.DaggerScope;
import o.krymov.photon.ru.flow.AbstractScreen;
import o.krymov.photon.ru.flow.Screen;
import o.krymov.photon.ru.mvp.models.SearchModel;
import o.krymov.photon.ru.mvp.presenters.AbstractPresenter;
import o.krymov.photon.ru.ui.screens.search.SearchScreen;
import o.krymov.photon.ru.ui.screens.search.filter.FilterScreen;
import o.krymov.photon.ru.ui.screens.search.filter.FilterView;

import dagger.Provides;
import mortar.MortarScope;

@Screen(R.layout.screen_find)
public class FindScreen extends AbstractScreen<SearchScreen.Component> {


    @Override
    public Object createScreenComponent(SearchScreen.Component parentComponent) {
        return DaggerFindScreen_Component.builder()
                .component(parentComponent)
                .module(new Module())
                .build();
    }


    //region ===================== DI =========================

    @dagger.Module
    public class Module{
        @Provides
        @DaggerScope(FindScreen.class)
        FindPresenter provideFindPresenter(){return new FindPresenter();}

        @Provides
        @DaggerScope(FindScreen.class)
        DataManager provideDataManager(){return DataManager.getInstance();}
    }

    @dagger.Component(dependencies = SearchScreen.Component.class, modules = Module.class)
    @DaggerScope(FindScreen.class)
    public interface Component{
        void inject(FindPresenter presenter);
        void inject(FindView view);

        DataManager getDataManager();
    }

    //endregion ================== DI =========================

    //region ===================== Presenter =========================
    public class FindPresenter extends AbstractPresenter<FindView, SearchModel> {

        @Override
        protected void initDagger(MortarScope scope) {
            ((FindScreen.Component) scope.getService(DaggerService.SERVICE_NAME)).inject(this);

        }

        @Override
        protected void initActionBar() {

        }
    }
    //endregion ================== Presenter =========================
}
