package o.krymov.photon.ru.mvp.presenters;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.MenuItemHoverListener;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.fernandocejas.frodo.annotation.RxLogSubscriber;
//import o.krymov.photon.ru.mvp.models.AccountModel;
import o.krymov.photon.ru.mvp.views.IRootView;
import o.krymov.photon.ru.ui.activities.RootActivity;
import o.krymov.photon.ru.ui.activities.SplashActivity;
import o.krymov.photon.ru.utils.App;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import mortar.Presenter;
import mortar.bundler.BundleService;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subjects.PublishSubject;

public class RootPresenter extends Presenter<IRootView> {

//    @Inject
//    AccountModel mAccountModel;

    private static int DEFAULT_MODE = 0;
    private static int TAB_MODE = 1;

    public RootPresenter() {
        App.getRootActivityRootComponent().inject(this);
    }

    @Override
    protected BundleService extractBundleService(IRootView view) {
        return (view instanceof RootActivity) ?
                BundleService.getBundleService((RootActivity) view) : //Привязываем RootPresenter к RootActivity
                BundleService.getBundleService((SplashActivity) view);
    }


    @Override
    protected void onLoad(Bundle savedInstanceState) {
        super.onLoad(savedInstanceState);


    }

    @Override
    public void dropView(IRootView view) {
        super.dropView(view);
    }



    @Nullable
    public IRootView getRootView() {
        return getView();
    }

    public ActionBarBuilder newActionBarBuilder() {
        return this.new ActionBarBuilder();
    }



    public boolean checkPermissionsAndRequestIfNotGranted(@NonNull String[] permissions, int requestCode){
        boolean allGranted = true;
        for (String permission : permissions) {
            int selfPermission = ContextCompat.checkSelfPermission(((RootActivity) getView()),permission);
            if (selfPermission != PackageManager.PERMISSION_GRANTED){
                allGranted = false;
                break;
            }
        }

        if (!allGranted){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                ((RootActivity) getView()).requestPermissions(permissions, requestCode);
            }
        }

        return allGranted;
    }


//    public void onRequstPermissionResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResult){
//        // TODO: 19.02.2017 implement request result
//    }

    public class ActionBarBuilder{
        private boolean isGoBack = false;
        private boolean isVisible = false;
        private CharSequence title;
        private List<MenuItemHolder> items = new ArrayList<>();
        private ViewPager pager;
        private int toolbarMode = DEFAULT_MODE;

        public ActionBarBuilder setBackArrow(boolean enable) {
            this.isGoBack = enable;
            return this;
        }

        public ActionBarBuilder setVisible(boolean visible) {
            this.isVisible = visible;
            return this;
        }

        public ActionBarBuilder setTitle(CharSequence title) {
            this.title = title;
            return this;
        }

        public ActionBarBuilder addAction(MenuItemHolder menuItem) {
            this.items.add(menuItem);
            return this;
        }

        public ActionBarBuilder setTabs(ViewPager viewPager) {
            this.toolbarMode = TAB_MODE;
            this.pager = viewPager;
            return this;
        }

        public void build(){
            if (getView() != null) {
                RootActivity activity = (RootActivity) getView();
                activity.setVisible(isVisible);
                activity.setActionBarTitle(title);
                activity.setBackArrow(isGoBack);
                activity.setMenuItem(items);
                if (toolbarMode == TAB_MODE){
                    activity.setTabLayout(pager);
                }else{
                    activity.removeTabLayout();
                }
            }
        }
    }

}
