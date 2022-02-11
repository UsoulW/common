package cn.syag.common;

import java.util.Collection;

public class CollectionUtil {

    public static boolean isEmpty(Collection collection){
        return collection==null || collection.isEmpty();
    }

    public static boolean isNotEmpty(Collection collection){
        return isEmpty(collection);
    }
}
