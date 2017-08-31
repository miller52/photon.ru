package o.krymov.photon.ru.ui.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.widget.FrameLayout;

import o.krymov.photon.ru.BuildConfig;
import o.krymov.photon.ru.R;
import o.krymov.photon.ru.data.managers.DataManager;
import o.krymov.photon.ru.di.DaggerService;
import o.krymov.photon.ru.mvp.presenters.RootPresenter;
import o.krymov.photon.ru.mvp.views.IRootView;
import o.krymov.photon.ru.mvp.views.IView;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.Component;
import mortar.MortarScope;
import mortar.bundler.BundleServiceRunner;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class SplashActivity extends AppCompatActivity implements IRootView {

    @BindView(R.id.root_frame)
    FrameLayout mRootFrame;

    protected static ProgressDialog mProgressDialog;

    @Inject
    RootPresenter mRootPresenter;

    private boolean mIsCatalogLoading;

    @Override
    protected void attachBaseContext(Context newBase) {
//        newBase = Flow.configure(newBase, this)
//                .defaultKey(new SplashScreen())
//                .dispatcher(new TreeKeyDispatcher(this))
//                .install();
        super.attachBaseContext(newBase);

    }

    @Override
    public Object getSystemService(String name) {
        MortarScope mRootActivityScope = MortarScope.findChild(getApplicationContext(), RootActivity.class.getName());
        return mRootActivityScope.hasService(name) ? mRootActivityScope.getService(name) : super.getSystemService(name);
    }


    private void preloadPhotoCards() {
        mIsCatalogLoading = true;

        showLoad();
        Observable
                .timer( 3000, TimeUnit.MILLISECONDS )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
        //        .flatMap(aBoolean -> DataManager.getInstance().getPhotoCardsObsFromNetwork())
                .map(o -> {
                    if (mRootPresenter.getRootView() != null) {
                        mRootPresenter.getRootView().hideLoad();
                    }
                    startRootActivity();
                    return true;
                } )
                .subscribe();
    }

//region ==========   Life Cycle ==================


    @Override
    protected void onStop() {
        mRootPresenter.dropView(this);
        super.onStop();
    }

    @Override
    protected void onStart() {
        super.onStart();

        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);

        RootActivity.RootComponent rootComponent = DaggerService.getDaggerComponent(this);
        rootComponent.inject(this);

        mRootPresenter.takeView(this);


        preloadPhotoCards();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

//endregion==========  Life Cycle ==================


    //region==========   IRootView    ==================
    @Override
    public void showMessage(String message) {
        Snackbar.make(mRootFrame, message, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void showError(Throwable e) {
        if (BuildConfig.DEBUG) {
            showMessage(e.getMessage());
            e.printStackTrace();
        } else {
            showMessage(getString(R.string.error_message));
            //TODO: send error stacktrace to crash analytics
        }

    }

    @Override
    public void showLoad() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setCancelable(false);
            mProgressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            mProgressDialog.show();
            mProgressDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
            mProgressDialog.setContentView(R.layout.progress_root);
        } else {
            mProgressDialog.show();
            mProgressDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
            mProgressDialog.setContentView(R.layout.progress_root);
        }
    }

    @Override
    public void hideLoad() {
        if (mProgressDialog != null) {
            if (mProgressDialog.isShowing()) {
                mProgressDialog.hide();
            }
        }
    }

    @Nullable
    @Override
    public IView getCurrentScreen() {
        return (IView) mRootFrame.getChildAt(0);
    }


//endregion==========  IRootView ==================


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public boolean viewOnBackPressed() {
        return false;
    }

    public void startRootActivity() {
        Intent intent = new Intent(this, RootActivity.class);
        startActivity(intent);
        finish();
    }

}
