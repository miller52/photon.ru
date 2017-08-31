package o.krymov.photon.ru.data.storage.realm;

import java.io.Serializable;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class PhotoCardFiltersRealm extends RealmObject implements Serializable {
    @PrimaryKey
    private String id;
    private String name;
    private String value;

    public PhotoCardFiltersRealm() {
    }

    public PhotoCardFiltersRealm(String name, String value) {
        this.name = name;
        this.value = value;
    }

    //region ===================== Getters =========================

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    //endregion ================== Getters =========================
}
