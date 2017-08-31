package o.krymov.photon.ru.mvp.models;

import o.krymov.photon.ru.data.storage.dto.PhotoCardDto;
import o.krymov.photon.ru.data.storage.realm.PhotoCardRealm;

import java.util.List;

import rx.Observable;

public class PhotoCardsModel extends AbstractModel{


    public PhotoCardsModel() {

    }

    public List<PhotoCardDto> getPhotoCardList(){
        return mDataManager.getPhotoCardList();
    }


    public Observable<PhotoCardRealm> getPhotoCardObs(){
        Observable<PhotoCardRealm> disk = getPhotoCardObsFromDisk();
        Observable<PhotoCardRealm> network = getPhotoCardObsFromNetwork();

        return Observable.mergeDelayError(disk, network)
                .distinct(PhotoCardRealm::getId);

    }

    private Observable<PhotoCardRealm> getPhotoCardObsFromNetwork(){
        return mDataManager.getPhotoCardsObsFromNetwork();
    }

    private Observable<PhotoCardRealm> getPhotoCardObsFromDisk(){
        return mDataManager.getPhotoCardsFromRealm();
    }

}
