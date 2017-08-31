package o.krymov.photon.ru.di.components;

import o.krymov.photon.ru.data.managers.DataManager;
import o.krymov.photon.ru.di.modules.LocalModule;
import o.krymov.photon.ru.di.modules.NetworkModule;

import javax.inject.Singleton;

import dagger.Component;

@Component(dependencies = AppComponent.class, modules = {LocalModule.class, NetworkModule.class})
@Singleton
public interface DataManagerComponent {
    void inject(DataManager dataManager);
}
