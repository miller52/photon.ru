package o.krymov.photon.ru.data.network.res;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PhotoCardRes {
    private String id;
    private String owner;
    private String title;
    private String photo;
    private int views;
    private int favorits;
    private List<String> tags = null;
    private Map<String, String> filters;

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
        return favorits;
    }

    public List<String> getTags() {
        return tags;
    }

    public Map<String, String> getFilters() {
        return filters;
    }

//endregion ================== Getters =========================
}
