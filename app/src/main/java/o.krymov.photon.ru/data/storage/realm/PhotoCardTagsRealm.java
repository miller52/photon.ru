package o.krymov.photon.ru.data.storage.realm;

import java.io.Serializable;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class PhotoCardTagsRealm extends RealmObject implements Serializable{
    @PrimaryKey
    private String id;
    private String tag;

    public PhotoCardTagsRealm() {
    }

    public PhotoCardTagsRealm(String tag) {
        this.tag = tag;
    }

    //region ===================== Getters =========================

    public String getId() {
        return id;
    }

    public String getTag() {
        return tag;
    }

    //endregion ================== Getters =========================
}
