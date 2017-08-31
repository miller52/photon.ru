package o.krymov.photon.ru.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import rx.Observable;

public class NetworkStatusChecker {
    public static boolean isNetworkAvailable(){
        ConnectivityManager cm = (ConnectivityManager) App.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnectedOrConnecting();
    }

    public static Observable<Boolean> isInternetAvailiableObs(){
        return Observable.just(isNetworkAvailable());
    }
}
