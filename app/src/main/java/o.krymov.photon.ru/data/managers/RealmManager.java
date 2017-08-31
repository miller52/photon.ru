package o.krymov.photon.ru.data.managers;


import o.krymov.photon.ru.data.network.res.PhotoCardRes;
import o.krymov.photon.ru.data.storage.realm.PhotoCardRealm;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import io.realm.Sort;
import rx.Observable;

public class RealmManager {
    private Realm mRealmInstance;
    private QueryRealmFilter mRealmFilter;


    public void savePhotoCardResponseToRealm(PhotoCardRes photoCardRes) {
        Realm realm =Realm.getDefaultInstance();

        PhotoCardRealm productRealm= new PhotoCardRealm(photoCardRes);

//        if (!photoCardRes.getComments().isEmpty()){
//            Observable.from(photoCardRes.getComments())
//                    .doOnNext(commentRes ->{
//                        if (!commentRes.isActive()) {
//                            deleteFromRealm(CommentRealm.class, commentRes.getId());
//                        }})
//                    .filter(commentRes -> commentRes.isActive())
//                    .map(CommentRealm::new) //преобразовываем в Realm
//                    .subscribe(commentRealm -> productRealm.getComments().add(commentRealm)); //add to object
//        }

        realm.executeTransaction(realm1 -> realm1.insertOrUpdate(productRealm));
        realm.close();
    }

    public void deleteFromRealm(Class<? extends RealmObject> objectClass, String id) {
        Realm realm = Realm.getDefaultInstance();

        RealmObject entity = realm.where(objectClass).equalTo("id",id).findFirst(); //find record by ID

        if (entity != null){
            realm.executeTransaction(realm1 -> entity.deleteFromRealm()); //del from bd
            realm.close();
        }
    }


    public Observable<PhotoCardRealm> getAllPhotoCardsFromRealm(){

        RealmQuery<PhotoCardRealm> query = getQueryRealmInstance()
                .where(PhotoCardRealm.class);

        query = getQueryRealmFilter().applyFilters(query);

        RealmResults<PhotoCardRealm> managedCards = query.findAllSortedAsync("views", Sort.DESCENDING);

        return managedCards
                .asObservable()  //Get massiv
                .filter(RealmResults::isLoaded) //Get only downloading
                //.first() //IF need cold massiv
                .flatMap(Observable::from); //process in в Obs<ProductRealm>
    }

    private Realm getQueryRealmInstance() {
        if (mRealmInstance == null || mRealmInstance.isClosed()){
            mRealmInstance = Realm.getDefaultInstance();
        }
        return mRealmInstance;
    }

    public QueryRealmFilter getQueryRealmFilter(){
        if (mRealmFilter == null) {
            mRealmFilter = new QueryRealmFilter();
        }
        return mRealmFilter;
    }

    //region ===================== Realm Filter =========================
    public class QueryRealmFilter {
        private boolean isApplied = false;
        private String textFilter = "";
        private List<String> tags = new ArrayList<>();
        private List<String> dish = new ArrayList<>();
        private List<String> nuances = new ArrayList<>();
        private List<String> decor = new ArrayList<>();
        private List<String> temperature = new ArrayList<>();
        private List<String> light = new ArrayList<>();
        private List<String> lightDirection = new ArrayList<>();
        private List<String> lightSource = new ArrayList<>();

        public QueryRealmFilter() {
        }

        public QueryRealmFilter(QueryRealmFilter filter) {
            this.textFilter = filter.getTextFilter();
            this.tags = filter.getTags();
            this.dish = filter.getDish();
            this.nuances = filter.getNuances();
            this.decor = filter.getDecor();
            this.temperature = filter.getTemperature();
            this.light = filter.getLight();
            this.lightDirection = filter.getLightDirection();
            this.lightSource = filter.getLightSource();
        }

        //region ===================== getters =========================

        public boolean isApplied() {
            return isApplied;
        }

        public String getTextFilter() {
            return textFilter;
        }

        public List<String> getTags() {
            return tags;
        }

        public List<String> getDish() {
            return dish;
        }

        public List<String> getNuances() {
            return nuances;
        }

        public List<String> getDecor() {
            return decor;
        }

        public List<String> getTemperature() {
            return temperature;
        }

        public List<String> getLight() {
            return light;
        }

        public List<String> getLightDirection() {
            return lightDirection;
        }

        public List<String> getLightSource() {
            return lightSource;
        }

        //endregion ================== getters =========================

        //region ===================== setters =========================


        public void setTextFilter(String textFilter) {
            this.textFilter = textFilter;
            isApplied = false;
        }

        public void setTags(List<String> tags) {
            this.tags = tags;
            isApplied = false;
        }

        public void setDish(List<String> dish) {
            this.dish = dish;
            isApplied = false;
        }

        public void setNuances(List<String> nuances) {
            this.nuances = nuances;
            isApplied = false;
        }

        public void setDecor(List<String> decor) {
            this.decor = decor;
            isApplied = false;
        }

        public void setTemperature(List<String> temperature) {
            this.temperature = temperature;
            isApplied = false;
        }

        public void setLight(List<String> light) {
            this.light = light;
            isApplied = false;
        }

        public void setLightDirection(List<String> lightDirection) {
            this.lightDirection = lightDirection;
            isApplied = false;
        }

        public void setLightSource(List<String> lightSource) {
            this.lightSource = lightSource;
            isApplied = false;
        }

        //endregion ================== setters =========================

        private RealmQuery<PhotoCardRealm> applyFilters(RealmQuery<PhotoCardRealm> query){
            RealmQuery<PhotoCardRealm> result = query;
            isApplied = true;

            return result;
        }

        public boolean isEmptyFilter(){
            return textFilter.isEmpty()
                    && tags.isEmpty()
                    && dish.isEmpty()
                    && nuances.isEmpty()
                    && decor.isEmpty()
                    && temperature.isEmpty()
                    && light.isEmpty()
                    && lightDirection.isEmpty()
                    && lightSource.isEmpty();
        }

    }
    //endregion ================== Realm Filter =========================

}
