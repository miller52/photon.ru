package o.krymov.photon.ru.mvp.presenters;

import android.support.annotation.Nullable;
import android.view.ViewGroup;

import o.krymov.photon.ru.mvp.views.IRootView;

import mortar.ViewPresenter;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public abstract class SubscribePresenter<V extends ViewGroup> extends ViewPresenter<V>{
    @Nullable
    protected abstract IRootView getRootView();

    protected abstract class ViewSubscriber<T> extends Subscriber<T>{
        @Override
        public abstract void onNext(T t);

        @Override
        public void onError(Throwable e) {
            if (getRootView()!=null) {
                getRootView().showError(e);
            }
        }

        @Override
        public void onCompleted() {

        }
    }

    protected <T> Subscription subscribe(Observable<T> observable, ViewSubscriber<T> subscriber){
        return observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

}
