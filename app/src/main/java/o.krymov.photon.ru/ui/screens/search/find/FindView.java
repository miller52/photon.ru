package o.krymov.photon.ru.ui.screens.search.find;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import o.krymov.photon.ru.data.managers.DataManager;
import o.krymov.photon.ru.di.DaggerService;
import o.krymov.photon.ru.mvp.views.AbstractView;

import javax.inject.Inject;


public class FindView extends AbstractView<FindScreen.FindPresenter> {
    @Inject
    DataManager mDataManager;

    public FindView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean viewOnBackPressed() {
        return false;
    }

    @Override
    protected void initDagger(Context context) {
        DaggerService.<FindScreen.Component>getDaggerComponent(context).inject(this);

    }
}
