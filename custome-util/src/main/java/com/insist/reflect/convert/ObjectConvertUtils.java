package com.insist.reflect.convert;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Date 2020-02-17 09:11
 */
public class ObjectConvertUtils {

    private static final Logger logger = LoggerFactory.getLogger(ObjectConvertUtils.class);

    /**
     *  把对象实例转换成map
     *  如果value为空，则剔除。
     * @param instance
     * @return
     */
    public static Map<String,Object> convertToMap(Object instance)  {
        try {
            Map<String, Object> fieldMap = new HashMap<>(24);
            BeanInfo beanInfo = Introspector.getBeanInfo(instance.getClass(), Object.class);
            //获取所有的属性描述器
            PropertyDescriptor[] pds = beanInfo.getPropertyDescriptors();
            for (PropertyDescriptor pd : pds) {
                String key = pd.getName();
                Method getter = pd.getReadMethod();
                Object value = getter.invoke(instance);
                if(value != null) {
                    fieldMap.put(key, value);
                }
            }
            return fieldMap;
        }catch (IntrospectionException | InvocationTargetException | IllegalAccessException e){
            logger.error(instance + " : instance concert error",e);
        }
        return null;
    }


    /**
     * 把map转换为object
     * @param map
     * @param beanClass
     * @return
     */
    public static <T> T mapToObject(Map<String, Object> map, Class<T> beanClass){
        if (map == null || map.size() == 0) {
            return null;
        }

        T obj = null;
        try {
            obj = beanClass.newInstance();
            BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            for (PropertyDescriptor property : propertyDescriptors) {
                Method setter = property.getWriteMethod();
                if (setter != null) {
                    try {
                        setter.invoke(obj, map.get(property.getName()));
                    }catch (Exception e){
                        logger.error("object:{} property.getName:{} mapToObject setter error:",obj.getClass().getSimpleName(),property.getName(),e);
                    }
                }
            }
        } catch (InstantiationException |IllegalAccessException  |IntrospectionException e) {
            logger.error("mapToObject error:",e);
        }
        return obj;
    }

    /**
     * properties 的key有的且不为空的才转为map
     *
     * @param instance
     * @param properties
     * @return
     */
    public static Map<String,String> convertToMap(Object instance, List<String> properties)  {

        if(properties == null || properties.size() == 0){
            return null;
        }

        try {
            Map<String, String> fieldMap = new HashMap<>(24);
            BeanInfo beanInfo = Introspector.getBeanInfo(instance.getClass(), Object.class);
            //获取所有的属性描述器
            PropertyDescriptor[] pds = beanInfo.getPropertyDescriptors();
            for (PropertyDescriptor pd : pds) {
                String key = pd.getName();

                if(!properties.contains(key)){
                    continue;
                }
                Method getter = pd.getReadMethod();
                Object value = getter.invoke(instance);
                if(value != null) {
                    fieldMap.put(key, value.toString());
                }
            }
            return fieldMap;
        }catch (IntrospectionException | InvocationTargetException | IllegalAccessException e){
            logger.error(instance.getClass().getSimpleName() + " : instance concert error",e);
        }
        return null;
    }


    /**
     * 获取某些值
     * @param instance
     * @param filedName
     * @return
     */
    public static String getObjectFiledValue(Object instance,String filedName){
        try {

            BeanInfo beanInfo = Introspector.getBeanInfo(instance.getClass(),Object.class);
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            for (PropertyDescriptor property : propertyDescriptors) {
                Method getter = property.getReadMethod();
                if(property.getName().equals(filedName)){
                    Object value = getter.invoke(instance);
                    return value.toString();
                }

            }
        } catch (IllegalAccessException  | IntrospectionException | InvocationTargetException e) {
            logger.error(instance.getClass().getSimpleName() + ":" + filedName  + "  get Object  error:",e);
        }
        return null;
    }


    /**
     * 为某些实例设置值
     * @param instance
     * @param value
     * @param filedName
     */
    public static void setFiledValue(Object instance,Object value ,String filedName){
        try {

            BeanInfo beanInfo = Introspector.getBeanInfo(instance.getClass(),Object.class);
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            for (PropertyDescriptor property : propertyDescriptors) {
                Method writer = property.getWriteMethod();
                if(property.getName().equals(filedName)){
                    try {
                        writer.invoke(instance, value);
                    }catch (Exception e){
                        logger.error(instance.getClass().getSimpleName() + ":" + filedName  + ":set writer error:",e);
                    }
                }
            }
        } catch (IntrospectionException e) {
            logger.error(instance.getClass().getSimpleName() + ":" + filedName  +":set Object error:",e);
        }
    }
}