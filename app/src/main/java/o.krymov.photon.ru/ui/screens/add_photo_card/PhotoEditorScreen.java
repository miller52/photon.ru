package o.krymov.photon.ru.ui.screens.add_photo_card;


import android.support.annotation.NonNull;
import android.view.MenuItem;

import o.krymov.photon.ru.R;
import o.krymov.photon.ru.di.DaggerService;
import o.krymov.photon.ru.di.scopes.DaggerScope;
import o.krymov.photon.ru.flow.AbstractScreen;
import o.krymov.photon.ru.flow.Screen;
import o.krymov.photon.ru.mvp.models.PhotoCardsModel;
import o.krymov.photon.ru.mvp.presenters.AbstractPresenter;
import o.krymov.photon.ru.mvp.presenters.MenuItemHolder;
import o.krymov.photon.ru.mvp.presenters.RootPresenter;
import o.krymov.photon.ru.ui.activities.RootActivity;
import o.krymov.photon.ru.ui.screens.profile.DaggerProfileScreen_Component;

import dagger.Provides;
import mortar.MortarScope;

@Screen(R.layout.screen_photo_editor)
public class PhotoEditorScreen extends AbstractScreen<RootActivity.RootComponent> {
    @Override
    public Object createScreenComponent(RootActivity.RootComponent parentComponent){
        return DaggerPhotoEditorScreen_Component.builder()
                .rootComponent(parentComponent)
                .module(new Module())
                .build();
    }

    //region ===================== DI =========================

    @dagger.Module
    public class Module {
        @Provides
        @DaggerScope(PhotoEditorScreen.class)
        PhotoCardsModel providePhotoCardsModel(){ return new PhotoCardsModel(); }

        @Provides
        @DaggerScope(PhotoEditorScreen.class)
        PhotoEditorPresenter provideProfilePresenter(){ return new PhotoEditorPresenter(); }

    }

    @dagger.Component(dependencies = RootActivity.RootComponent.class, modules = Module.class)
    @DaggerScope(PhotoEditorScreen.class)
    public interface Component{
        void inject(PhotoEditorPresenter photoEditorPresenter);
        void inject(PhotoEditorView photoEditorView);

        PhotoCardsModel photoCardsModel();

        RootPresenter getRootPresenter();
    }

    //endregion ================== DI =========================

    //region ===================== SearchPresenter =========================

    public class PhotoEditorPresenter extends AbstractPresenter<PhotoEditorView, PhotoCardsModel> {

        @Override
        protected void initDagger(MortarScope scope) {
            ((Component)scope.getService(DaggerService.SERVICE_NAME)).inject(this);
        }

        @Override
        protected void initActionBar() {
            if (getRootView() != null) {

                mRootPresenter.newActionBarBuilder()
                        .setVisible(false)
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

