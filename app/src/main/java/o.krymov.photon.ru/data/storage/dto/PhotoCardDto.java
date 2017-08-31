package o.krymov.photon.ru.data.storage.dto;

import android.os.Parcel;
import android.os.Parcelable;

import o.krymov.photon.ru.data.network.res.PhotoCardRes;
import o.krymov.photon.ru.data.storage.realm.PhotoCardFiltersRealm;
import o.krymov.photon.ru.data.storage.realm.PhotoCardRealm;
import o.krymov.photon.ru.data.storage.realm.PhotoCardTagsRealm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PhotoCardDto implements Parcelable {
    private String id;
    private String owner;
    private String title;
    private String photo;
    private int views;
    private int favorites;
    private List<String> tags = new ArrayList<>();
    private Map<String, String> filters = new HashMap<>();

    public PhotoCardDto(PhotoCardRes cardRes) {
        this.id = cardRes.getId();
        this.owner = cardRes.getOwner();
        this.title = cardRes.getTitle();
        this.photo = cardRes.getPhoto();
        this.views = cardRes.getViews();
        this.favorites = cardRes.getFavorites();
        this.tags = cardRes.getTags();
        this.filters = cardRes.getFilters();
    }

    public PhotoCardDto(PhotoCardRealm cardRealm) {
        this.id = cardRealm.getId();
        this.owner = cardRealm.getOwner();
        this.title = cardRealm.getTitle();
        this.photo = cardRealm.getPhoto();
        this.views = cardRealm.getViews();
        this.favorites = cardRealm.getFavorites();
        for (PhotoCardTagsRealm tag : cardRealm.getTags()) {
            this.tags.add(tag.getTag());
        }
        for (PhotoCardFiltersRealm filter : cardRealm.getFilters()) {
            this.filters.put(filter.getName(), filter.getValue());
        }
    }

    public PhotoCardDto(String id, String owner, String title, String photo, int views, int favorites, List<String> tags, Map<String, String> filters) {
        this.id = id;
        this.owner = owner;
        this.title = title;
        this.photo = photo;
        this.views = views;
        this.favorites = favorites;
        this.tags = tags;
        this.filters = filters;
    }
    
    //region ===================== Parcelable =========================

    public static final Creator<PhotoCardDto> CREATOR = new Creator<PhotoCardDto>() {
        @Override
        public PhotoCardDto createFromParcel(Parcel in) {
            return new PhotoCardDto(in);
        }

        @Override
        public PhotoCardDto[] newArray(int size) {
            return new PhotoCardDto[size];
        }
    };

    public PhotoCardDto(Parcel in) {
        this.id = in.readString();
        this.owner = in.readString();
        this.title = in.readString();
        this.photo = in.readString();
        this.views = in.readInt();
        this.favorites = in.readInt();
        in.readList(this.tags, null);
        in.readMap(this.filters, null);

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.owner);
        dest.writeString(this.title);
        dest.writeString(this.photo);
        dest.writeInt(this.views);
        dest.writeInt(this.favorites);
        dest.writeList(this.tags);
        dest.writeMap(this.filters);
    }

    //endregion ================== Parcelable =========================

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

    public List<String> getTags() {
        return tags;
    }

    public Map<String, String> getFilters() {
        return filters;
    }

    //endregion ================== Getters =========================

    //region ===================== Setters =========================

    public void setId(String id) {
        this.id = id;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public void setViews(int views) {
        this.views = views;
    }

    public void setFavorites(int favorites) {
        this.favorites = favorites;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public void setFilters(Map<String, String> filters) {
        this.filters = filters;
    }

    //endregion ================== Setters =========================
}
