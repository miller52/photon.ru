package o.krymov.photon.ru.data.network.res;

import java.util.List;

public class AlbumRes {
    private String id;
    private String owner;
    private String title;
    private String preview;
    private String description;
    private int views;
    private int favorits;
    private List<PhotoCardRes> photocards = null;
}
