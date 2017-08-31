package o.krymov.photon.ru.data.managers;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.HashSet;
import java.util.Set;


public class PreferencesManager {

    private static final String GALLERY_LAST_UPDATE_KEY = "GALLERY_LAST_UPDATE_KEY";
    private static final String FILTER_HISTORY_KEY = "FILTER_HISTORY_KEY";

    private final SharedPreferences mSharedPreferences;

    public PreferencesManager(Context context) {
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    //region ===================== Gallery data =========================

    public String getLastGalleryUpdate(){
        // TODO: 23.03.2017 uncomment this
        //return mSharedPreferences.getString(GALLERY_LAST_UPDATE_KEY, "Thu, 01 Jan 1970 00:00:00 GMT");
        return "Thu, 01 Jan 1970 00:00:00 GMT";
    }

    public void saveLastGalleryUpdate(String lastModified){
        SharedPreferences.Editor spEditor = mSharedPreferences.edit();
        spEditor.putString(GALLERY_LAST_UPDATE_KEY, lastModified);
        spEditor.apply();
    }

    //endregion ================== Gallery data =========================

    //region ===================== Filter History =========================

    public Set<String> getFilterHistory(){
        return mSharedPreferences.getStringSet(FILTER_HISTORY_KEY, new HashSet<>());
    }

    public void saveFilterHistory(Set<String> history){
        SharedPreferences.Editor spEditor = mSharedPreferences.edit();
        spEditor.putStringSet(FILTER_HISTORY_KEY, history);
        spEditor.apply();
    }
    //endregion ================== Filter History =========================


}
