package com.insist.reflect.convert;

import com.google.common.collect.Lists;
import org.dozer.DozerBeanMapper;

import java.util.Collection;
import java.util.List;

/**
 * Mapping tools
 ** @create 2020-02-11
 */
public class DozerBeanMapperUtils {

    private static DozerBeanMapper mapp = null;

    private static synchronized DozerBeanMapper getMapper(){
        if(mapp==null){
            mapp = new DozerBeanMapper();
        }
        return mapp;
    }

    /**
     * Based on the Dozer conversion types of objects.
     */
    public static <T> T map (Object source, Class<T> destinationClass) {
        T destinationBean = null;
        if(source != null) {
            destinationBean = getMapper().map(source, destinationClass);
        }
        return destinationBean;
    }

    /**
     * Based on the Dozer convert the type of the object in the Collection.
     */
    public static <T> List<T> mapList (@SuppressWarnings("rawtypes") Collection sourceList, Class<T> destinationClass) {
        List<T> destinationList = Lists.newArrayList();
        if(sourceList != null) {
            for (Object sourceObject : sourceList) {
                if(sourceObject != null) {
                    T destinationObject = getMapper().map(sourceObject, destinationClass);
                    destinationList.add(destinationObject);
                }
            }
        }
        return destinationList;
    }
}
