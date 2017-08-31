package o.krymov.photon.ru.data.network;

import com.fernandocejas.frodo.annotation.RxLogObservable;
import o.krymov.photon.ru.data.managers.ConstantManager;
import o.krymov.photon.ru.data.managers.DataManager;
import o.krymov.photon.ru.data.network.error.ErrorUtils;
import o.krymov.photon.ru.data.network.error.NetworkAvailableError;
import o.krymov.photon.ru.utils.NetworkStatusChecker;

import retrofit2.Response;
import rx.Observable;


public class RestCallTransformer<R> implements Observable.Transformer<Response<R>, R>{
    @Override
    @RxLogObservable
    public Observable<R> call(Observable<Response<R>> responseObservable) {

        return NetworkStatusChecker.isInternetAvailiableObs()
                .flatMap(aBoolean -> aBoolean ? responseObservable : Observable.error(new NetworkAvailableError()))
                .flatMap(rResponse ->{
                    switch (rResponse.code()){
                        case 200:
                            String lastModified = rResponse.headers().get(ConstantManager.HEADER_LAST_MODIFIED);
                            if (lastModified != null){
                                DataManager.getInstance().getPreferencesManager().saveLastGalleryUpdate(lastModified);
                            }
                            return Observable.just(rResponse.body());
                        case 304:
                            return Observable.empty();
                        default:
                            return Observable.error(ErrorUtils.parseError(rResponse));
                    }

                });

    }
}
