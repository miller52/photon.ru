package o.krymov.photon.ru.data.network;

import o.krymov.photon.ru.data.managers.ConstantManager;
import o.krymov.photon.ru.data.network.res.PhotoCardRes;

import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

public interface RestService {

    @GET("photocard/list")
    Observable<Response<List<PhotoCardRes>>> getPhotoCardResObs(@Header(ConstantManager.HEADER_IF_MODIFIED_SINCE)String lastEntityUpdate, @Query("limit") int limit, @Query("offset")int offset);


}
