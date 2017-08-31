package o.krymov.photon.ru.data.network.error;

public class AccessError extends Exception {
    public AccessError() {
        super("Invalid Login or Password");
    }
}
