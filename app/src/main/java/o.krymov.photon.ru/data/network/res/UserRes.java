package o.krymov.photon.ru.data.network.res;

import java.util.List;

public class UserRes {
    private String id;
    private String name;
    private String login;
    private String avatar;
    private int albumCount;
    private int photocardCount;
    private List<AlbumRes> albums = null;
}
