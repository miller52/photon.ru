package o.krymov.photon.ru.di.components;



import android.content.Context;

import o.krymov.photon.ru.di.modules.AppModule;

import dagger.Component;

@Component(modules = AppModule.class)
public interface AppComponent {
    Context getContext();
}
