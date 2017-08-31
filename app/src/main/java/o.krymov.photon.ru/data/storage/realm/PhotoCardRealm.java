package o.krymov.photon.ru.data.storage.realm;

import android.os.Build;

import o.krymov.photon.ru.data.network.res.PhotoCardRes;

import java.util.Map;
import java.util.stream.Collectors;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;


public class PhotoCardRealm extends RealmObject {
    @PrimaryKey
    private String id;
    private String owner;
    private String title;
    private String photo;
    private int views;
    private int favorites;
    private RealmList<PhotoCardTagsRealm> tags = new RealmList<>();
    private RealmList<PhotoCardFiltersRealm> filters = new RealmList<>();

    public PhotoCardRealm() { //For realm
    }

    public PhotoCardRealm(PhotoCardRes cardRes) {
        this.id = cardRes.getId();
        this.owner = cardRes.getOwner();
        this.title = cardRes.getTitle();
        this.photo = cardRes.getPhoto();
        this.views = cardRes.getViews();
        this.favorites = cardRes.getFavorites();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            this.tags.addAll(cardRes.getTags().stream().map(PhotoCardTagsRealm::new).collect(Collectors.toList()));
        }else{
            //noinspection Convert2streamapi
            for (String tag : cardRes.getTags()) {
                this.tags.add(new PhotoCardTagsRealm(tag));
            }
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            this.filters.addAll(cardRes.getFilters().entrySet().stream().map(entry -> new PhotoCardFiltersRealm(entry.getKey(), entry.getValue())).collect(Collectors.toList()));
        }else{
            //noinspection Convert2streamapi
            for (Map.Entry<String, String> entry: cardRes.getFilters().entrySet()) {
                this.filters.add(new PhotoCardFiltersRealm(entry.getKey(), entry.getValue()));
            }
        }
    }

    //region ===================== Getters =========================


    public String getId() {
        return id;
    }

    public String getOwner() {
        return owner;
    }

    public String getTitle() {
        return title;
    }

    public String getPhoto() {
        return photo;
    }

    public int getViews() {
        return views;
    }

    public int getFavorites() {
        return favorites;
    }

    public RealmList<PhotoCardTagsRealm> getTags() {
        return tags;
    }

    public RealmList<PhotoCardFiltersRealm> getFilters() {
        return filters;
    }

    //endregion ================== Getters =========================

    //region ===================== Other =========================

    public void photoCardViewd(){
        this.views++;
    }

    public void addFavourite(){
        this.favorites++;
    }

    public void removeFavourite(){
        this.favorites--;
    }

    //endregion ================== Other =========================
}
