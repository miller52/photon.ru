package o.krymov.photon.ru.di;


import android.content.Context;
import android.support.annotation.Nullable;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class DaggerService {
    public static final String SERVICE_NAME = "DUDNIK_DAGGER_SERVICE";

    @SuppressWarnings("unchecked")
    public static  <T> T getDaggerComponent(Context context){
        //no inspection resource type
        return (T) context.getSystemService(SERVICE_NAME);
    }


    //TODO: fix me
    private static Map<Class, Object> sComponentMap = new HashMap<>();


    public static void registerComponent(Class componentClass, Object component){
        sComponentMap.put(componentClass, component);
    }

    @Nullable
    @SuppressWarnings("unchecked")
    public static <T> T getComponent(Class<T> componentClass){
        Object component = sComponentMap.get(componentClass);

        return (T) component;
    }

    public static void unregisterScope(Class<? extends Annotation> scopeAnnotation){
        Iterator<Map.Entry<Class, Object>> iterator = sComponentMap.entrySet().iterator();
        while (iterator.hasNext()){
            Map.Entry<Class, Object> entry = iterator.next();
            if (entry.getKey().isAnnotationPresent(scopeAnnotation)){
                iterator.remove();
            }
        }
    }

}
