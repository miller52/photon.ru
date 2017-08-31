package o.krymov.photon.ru.mvp.presenters;


import android.os.Bundle;
import android.support.annotation.Nullable;

import o.krymov.photon.ru.mvp.models.AbstractModel;
import o.krymov.photon.ru.mvp.views.AbstractView;
import o.krymov.photon.ru.mvp.views.IRootView;

import javax.inject.Inject;

import mortar.MortarScope;
import mortar.ViewPresenter;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public abstract class AbstractPresenter<V extends AbstractView, M extends AbstractModel>  extends ViewPresenter<V>{
    @Inject
    protected M mModel;

    @Inject
    protected RootPresenter mRootPresenter;

    protected CompositeSubscription mCompSubs;

    @Override
    protected void onEnterScope(MortarScope scope) {
        super.onEnterScope(scope);
        initDagger(scope);
    }

    @Override
    protected void onLoad(Bundle savedInstanceState) {
        super.onLoad(savedInstanceState);
        mCompSubs = new CompositeSubscription();
        initActionBar();
    }

    @Override
    public void dropView(V view) {
        if (mCompSubs.hasSubscriptions()){
            mCompSubs.unsubscribe();
        }
        super.dropView(view);
    }

    protected abstract void initDagger(MortarScope scope);

    protected abstract void initActionBar();

    @Nullable
    protected IRootView getRootView(){
        return mRootPresenter.getRootView();
    }

    protected abstract class ViewSubscriber<T> extends Subscriber<T> {
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
