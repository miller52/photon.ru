package o.krymov.photon.ru.data.network.error;

public class ApiError extends Throwable{
    private int statusCode;
    private String message;

    public ApiError(int statusCode) {
        super("Error by server: " + statusCode);
    }

    @Override
    public String getMessage() {
        return "Error "+statusCode+" : "+ message;
    }
}
