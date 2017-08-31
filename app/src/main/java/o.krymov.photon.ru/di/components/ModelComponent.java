package o.krymov.photon.ru.di.components;

import o.krymov.photon.ru.di.modules.ModelModule;
import o.krymov.photon.ru.mvp.models.AbstractModel;

import javax.inject.Singleton;

import dagger.Component;

@Component(modules = ModelModule.class)
@Singleton
public interface ModelComponent {
    void inject(AbstractModel abstractModel);
}
