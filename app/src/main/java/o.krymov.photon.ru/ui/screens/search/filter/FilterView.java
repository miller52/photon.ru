package o.krymov.photon.ru.ui.screens.search.filter;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import o.krymov.photon.ru.di.DaggerService;
import o.krymov.photon.ru.mvp.views.AbstractView;

public class FilterView extends AbstractView<FilterScreen.FilterPresenter> {
    public FilterView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean viewOnBackPressed() {
        return false;
    }

    @Override
    protected void initDagger(Context context) {
        DaggerService.<FilterScreen.Component>getDaggerComponent(context).inject(this);
    }
}
