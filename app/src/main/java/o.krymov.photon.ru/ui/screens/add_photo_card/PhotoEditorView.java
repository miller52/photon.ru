package o.krymov.photon.ru.ui.screens.add_photo_card;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import o.krymov.photon.ru.di.DaggerService;
import o.krymov.photon.ru.mvp.presenters.RootPresenter;
import o.krymov.photon.ru.mvp.views.AbstractView;
import o.krymov.photon.ru.ui.screens.profile.ProfileScreen;

import javax.inject.Inject;

public class PhotoEditorView extends AbstractView<PhotoEditorScreen.PhotoEditorPresenter> {

    public PhotoEditorView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    //region ===================== AbstractView =========================

    @Override
    public boolean viewOnBackPressed() {
        return false;
    }

    @Override
    protected void initDagger(Context context) {
        DaggerService.<PhotoEditorScreen.Component>getDaggerComponent(context).inject(this);
    }

    //endregion ================== AbstractView =========================

}
