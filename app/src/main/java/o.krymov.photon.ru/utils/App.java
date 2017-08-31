package o.krymov.photon.ru.utils;

import android.app.Application;
import android.content.Context;
import android.util.Log;


import o.krymov.photon.ru.di.DaggerService;
import o.krymov.photon.ru.di.components.AppComponent;
import o.krymov.photon.ru.di.components.DaggerAppComponent;
import o.krymov.photon.ru.di.modules.AppModule;
//import o.krymov.photon.ru.di.modules.PicassoCacheModule;
//import o.krymov.photon.ru.di.modules.RootModule;
import o.krymov.photon.ru.mortar.ScreenScoper;
import o.krymov.photon.ru.di.modules.PicassoCacheModule;
import o.krymov.photon.ru.di.modules.RootModule;
import o.krymov.photon.ru.ui.activities.DaggerRootActivity_RootComponent;
import o.krymov.photon.ru.ui.activities.RootActivity;
//import com.uphyca.stetho_realm.RealmInspectorModulesProvider;

import io.realm.Realm;
import mortar.MortarScope;
import mortar.bundler.BundleServiceRunner;


public class App extends Application {
    public static AppComponent sAppComponent;
    private static Context sContext;
    private MortarScope mRootScope;
    private MortarScope mRootActivityScope;
    private static RootActivity.RootComponent mRootActivityRootComponent;

    @Override
    public Object getSystemService(String name) {
        if (mRootScope != null) {
            return mRootScope.hasService(name) ? mRootScope.getService(name) : super.getSystemService(name);
        }
        return super.getSystemService(name);
    }

    @Override
    public void onCreate() {
        super.onCreate();

        Realm.init(this);

        createAppComponent();
        createRootActivityComponent();
        sContext = getApplicationContext();

        mRootScope = MortarScope.buildRootScope()
                .withService(DaggerService.SERVICE_NAME, sAppComponent)
                .build("Root");
        mRootActivityScope = mRootScope.buildChild()
                .withService(DaggerService.SERVICE_NAME, mRootActivityRootComponent)
                .withService(BundleServiceRunner.SERVICE_NAME, new BundleServiceRunner())
                .build(RootActivity.class.getName());

        ScreenScoper.registerScope(mRootScope);
        ScreenScoper.registerScope(mRootActivityScope);

    }


    public static AppComponent getAppComponent() {
        return sAppComponent;
    }

    private void createAppComponent() {
        sAppComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(getApplicationContext()))
                .build();
    }

    private void createRootActivityComponent() {
           mRootActivityRootComponent = DaggerRootActivity_RootComponent.builder()
                .appComponent(sAppComponent)
                .rootModule(new RootModule())
                .picassoCacheModule(new PicassoCacheModule())
                .build();

    }

    public static RootActivity.RootComponent getRootActivityRootComponent() {
        return mRootActivityRootComponent;
    }

    public static Context getContext() {
        return sContext;
    }
}
