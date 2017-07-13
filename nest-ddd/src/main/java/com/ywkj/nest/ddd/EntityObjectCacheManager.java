package com.ywkj.nest.ddd;

import com.ywkj.nest.core.cache.CacheClient;
import com.ywkj.nest.core.cache.CacheFactory;
import com.ywkj.nest.core.utils.SpringUtils;

/**
 * Created by Jove on 2016/8/31.
 */
public class EntityObjectCacheManager {


    private static String getCacheKey(EntityObject entityObject) {
        return entityObject.getClass().getName() + "_" + entityObject.getId();
    }

    private static String getCacheKey(Class clazz, String id) {
        return clazz.getName() + "_" + id;
    }

    private static CacheClient cacheClient;

    public synchronized static CacheClient getCacheClient() {
        if (cacheClient == null) {
            CacheFactory cacheFactory = SpringUtils.getInstance(CacheFactory.class);
            cacheClient = cacheFactory.getCacheClient("entityObject");
        }
        return cacheClient;
    }

    public static void put(EntityObject entityObject) {
        if (entityObject != null)
            getCacheClient().put(getCacheKey(entityObject), entityObject);
    }

    public static void remove(EntityObject entityObject) {
        if (entityObject != null)
            getCacheClient().remove(getCacheKey(entityObject));
    }

    public static <T extends EntityObject> T get(Class<T> tClass, String id) {
        return getCacheClient().get(tClass, id);
    }
    public static void removeAll(){
        getCacheClient().removeAll();
    }
}
