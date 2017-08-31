package o.krymov.photon.ru.di.modules;

import android.content.Context;

import o.krymov.photon.ru.data.managers.PreferencesManager;
import o.krymov.photon.ru.data.managers.RealmManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class LocalModule {
    @Provides
    @Singleton
    PreferencesManager providePreferencesManager(Context context){
        return new PreferencesManager(context);
    }

    @Provides
    @Singleton
    RealmManager provideRealmManager(Context context) {
        return new RealmManager();
    }
}
