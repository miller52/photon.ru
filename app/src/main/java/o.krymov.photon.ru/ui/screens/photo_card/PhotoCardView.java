package o.krymov.photon.ru.ui.screens.photo_card;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import o.krymov.photon.ru.di.DaggerService;
import o.krymov.photon.ru.mvp.views.AbstractView;


public class PhotoCardView extends AbstractView<PhotoCardScreen.PhotoCardPresenter> {

    public PhotoCardView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    //region ===================== AbstractView =========================

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
    }

    //endregion ================== AbstractView =========================


    @Override
    public boolean viewOnBackPressed() {
        return false;
    }

    @Override
    protected void initDagger(Context context) {
        DaggerService.<PhotoCardScreen.Component>getDaggerComponent(context).inject(this);
    }
}
