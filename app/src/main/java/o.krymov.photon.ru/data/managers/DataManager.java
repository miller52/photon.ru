package o.krymov.photon.ru.data.managers;


import o.krymov.photon.ru.data.network.RestCallTransformer;
import o.krymov.photon.ru.data.network.RestService;
import o.krymov.photon.ru.data.network.res.PhotoCardRes;
import o.krymov.photon.ru.data.storage.dto.PhotoCardDto;
import o.krymov.photon.ru.data.storage.realm.PhotoCardRealm;
import o.krymov.photon.ru.di.DaggerService;
import o.krymov.photon.ru.di.components.DaggerDataManagerComponent;
import o.krymov.photon.ru.di.components.DataManagerComponent;
import o.krymov.photon.ru.di.modules.LocalModule;
import o.krymov.photon.ru.di.modules.NetworkModule;
import o.krymov.photon.ru.utils.App;
import o.krymov.photon.ru.utils.AppConfig;
import o.krymov.photon.ru.utils.NetworkStatusChecker;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import retrofit2.Retrofit;
import rx.Observable;
import rx.schedulers.Schedulers;


public class DataManager {
    private static DataManager ourInstance;

    @Inject
    PreferencesManager mPreferencesManager;
    @Inject
    RestService mRestService;
    @Inject
    Retrofit mRetrofit;
    @Inject
    RealmManager mRealmManager;

    private boolean userAuth=false;


    private DataManager() {
        DataManagerComponent dmComponent = DaggerService.getComponent(DataManagerComponent.class);
        if (dmComponent==null){
            dmComponent = DaggerDataManagerComponent.builder()
                    .appComponent(App.getAppComponent())
                    .localModule(new LocalModule())
                    .networkModule(new NetworkModule())
                    .build();
            DaggerService.registerComponent(DataManagerComponent.class, dmComponent);
        }
        dmComponent.inject(this);

        updateLocalDataWithTimer();
    }

    public static DataManager getInstance() {
        if (ourInstance == null) {
            ourInstance = new DataManager();
        }
        return ourInstance;
    }

    private void updateLocalDataWithTimer() {
        Observable.interval(AppConfig.JOB_UPDATE_DATA_INTERVAL, TimeUnit.SECONDS) //generation massiv from elements every 30 sek
                .flatMap(aLong -> NetworkStatusChecker.isInternetAvailiableObs()) //check internet
                .filter(aBoolean -> aBoolean) //go forward if internet exist
                .flatMap(aBoolean -> getPhotoCardsObsFromNetwork()) //get new albums from network
                .subscribe(productRealm -> {

                }, throwable -> {

                });
    }


    //region ===================== Getters =========================

    public PreferencesManager getPreferencesManager() {
        return mPreferencesManager;
    }

    public Retrofit getRetrofit() {
        return mRetrofit;
    }

    //endregion ================== Getters =========================


    //region ===================== UserInfo =========================

    public boolean isAuthUser() {
        //TODO check auth token in shared preferences
        return userAuth;
    }

    public void setUserAuth(boolean state){
        userAuth = state;
    }






    //endregion ================== UserInfo =========================

    //region ===================== PhotoCards =========================
    public List<PhotoCardDto> getPhotoCardList() {
        return new ArrayList<>();
    }

    public Observable<PhotoCardRealm> getPhotoCardsObsFromNetwork(){
        return mRestService.getPhotoCardResObs(mPreferencesManager.getLastGalleryUpdate(), 60, 0)
                .compose(new RestCallTransformer<List<PhotoCardRes>>()) //transform response and get ApiError if error exist, check status network before quiry, process cods of answers
                .flatMap(Observable::from) //List of ProductRes
                .subscribeOn(Schedulers.newThread())
                .observeOn(Schedulers.io())
                .doOnNext(productRes -> {
//                    if (!productRes.isActive()) {
//                        mRealmManager.deleteFromRealm(ProductRealm.class, productRes.getId()); //del record from local bd
//                    }
                })
                //.filter(PhotoCardRes::isActive) //only active albums
                .doOnNext(productRes ->  mRealmManager.savePhotoCardResponseToRealm(productRes)) //Save data on disk
                .retryWhen(errorObservable -> errorObservable
                        .zipWith(Observable.range(1, AppConfig.GET_DATA_RETRY_COUNT), (throwable, retryCount) -> retryCount)  // massiv if attemts from 1 to 5\
                        .doOnNext(retryCount -> {
                        })
                        .map(retryCount -> (long) (AppConfig.INITIAL_BACK_OFF_IN_MS * Math.pow(Math.E, retryCount))) //genetate delay ekspotcionalno
                        .doOnNext(delay -> {
                        })
                        .flatMap(delay -> Observable.timer(delay, TimeUnit.MILLISECONDS)))  //run timer
                .flatMap(productRes -> Observable.empty());

    }


    public Observable<PhotoCardRealm> getPhotoCardsFromRealm() {
        return mRealmManager.getAllPhotoCardsFromRealm();
    }



    //endregion ================== PhotoCards =========================



}
