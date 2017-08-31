package o.krymov.photon.ru.mvp.views;

import android.support.annotation.Nullable;


public interface IRootView extends IView{
    void showMessage(String message);
    void showError(Throwable e);

    void showLoad();
    void hideLoad();

    @Nullable
    IView getCurrentScreen();

}
