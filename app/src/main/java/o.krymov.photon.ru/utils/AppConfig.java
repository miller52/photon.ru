package o.krymov.photon.ru.utils;

public class AppConfig {
    //public static final String BASE_URL = "https://private-anon-ffacf8a0ff-photonapp.apiary-mock.com/";
    // Retrofit config

    public static final String BASE_URL = "http://207.154.248.163:5000/";
    public static final int MAX_CONNECTION_TIMEOUT = 5000;
    public static final int MAX_READ_TIMEOUT = 5000;
    public static final int MAX_WRITE_TIMEOUT = 5000;

    public static final int MIN_CONSUMER_COUNT = 1;
    public static final int MAX_CONSUMER_COUNT = 3;
    public static final int LOAD_FACTOR = 3;
    public static final int KEEP_ALIVE = 120;
    public static final int INITIAL_BACK_OFF_IN_MS = 1000;
    public static final long JOB_UPDATE_DATA_INTERVAL = 30;
    public static final int GET_DATA_RETRY_COUNT = 5;


    // Search
    public static final int SEARCH_DELAY = 500;

    // Email
    public static final String SUPPORT_EMAIL = "oleg5217@yandex.ru";
}
