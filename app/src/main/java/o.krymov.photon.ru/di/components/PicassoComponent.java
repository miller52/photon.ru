package o.krymov.photon.ru.di.components;


import o.krymov.photon.ru.di.modules.PicassoCacheModule;
import o.krymov.photon.ru.di.scopes.DaggerScope;
import o.krymov.photon.ru.ui.activities.RootActivity;
import com.squareup.picasso.Picasso;

import dagger.Component;

@Component(dependencies = AppComponent.class, modules = PicassoCacheModule.class)
@DaggerScope(RootActivity.class)
public interface PicassoComponent {
    Picasso getPicasso();
}
