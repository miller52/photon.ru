package o.krymov.photon.ru.di.modules;

import android.content.Context;

import com.jakewharton.picasso.OkHttp3Downloader;
import o.krymov.photon.ru.di.scopes.DaggerScope;
import o.krymov.photon.ru.ui.activities.RootActivity;
import com.squareup.picasso.Picasso;

import dagger.Module;
import dagger.Provides;

@Module
public class PicassoCacheModule {
    @Provides
    @DaggerScope(RootActivity.class)
    Picasso providePicasso(Context context){
        OkHttp3Downloader okHttpDownloader = new OkHttp3Downloader(context, Integer.MAX_VALUE);
        Picasso picasso = new Picasso.Builder(context)
                .downloader(okHttpDownloader)
                //.debugging(true)
                .indicatorsEnabled(true)
                .build();
        Picasso.setSingletonInstance(picasso);
        return picasso;
    }
}
