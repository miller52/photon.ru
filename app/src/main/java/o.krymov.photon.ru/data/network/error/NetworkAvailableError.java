package o.krymov.photon.ru.data.network.error;

public class NetworkAvailableError extends Throwable {
    public NetworkAvailableError() {
        super("Internet not works, Try later !");
    }
}
