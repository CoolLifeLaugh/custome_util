package com.insist.reflect;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;

public class GenerateUtil {

    public static void main(String[] args) throws IntrospectionException {
        System.out.println();
//        generatorGet(BusinessTypeRequestDTO.class);
//        generatorIsNULL(ReversePickupCoverageQuery.class);
        generatorSet(Object.class,"reverseQuery");
    }

    static void generatorGet(Class cl) throws IntrospectionException {
        BeanInfo beanInfo = Introspector.getBeanInfo(cl, Object.class);
        //获取所有的属性描述器
        PropertyDescriptor[] pds = beanInfo.getPropertyDescriptors();
        for (PropertyDescriptor pd : pds) {
            String key = pd.getName();
            String str = "get" + key.substring(0,1).toUpperCase() + key.substring(1) + "();";

            String obj = cl.getSimpleName().substring(0,1).toLowerCase() +  cl.getSimpleName().substring(1);

            String get = obj + "." +str;
            System.out.println(get);
        }
    }

    static void generatorIsNULL(Class cl) throws IntrospectionException {
        BeanInfo beanInfo = Introspector.getBeanInfo(cl, Object.class);
        //获取所有的属性描述器
        PropertyDescriptor[] pds = beanInfo.getPropertyDescriptors();
        for (PropertyDescriptor pd : pds) {
            String key = pd.getName();
            String str = "get" + key.substring(0,1).toUpperCase() + key.substring(1) + "()";

            String obj = cl.getSimpleName().substring(0,1).toLowerCase() +  cl.getSimpleName().substring(1);

            String get = obj + "." +str;

            String ifGetIsNull = "if(StringUtils.isEmpty(" + get + ")){";
            String throwError = "throw new BizException(ErrorEnum.NOT_NULL,\""+key+"\");";
            String ifGetIsNullEnd = "};";

            System.out.println(ifGetIsNull);
            System.out.println(throwError);
            System.out.println(ifGetIsNullEnd);
        }
    }

    static void generatorSet(Class cl,String setStrss) throws IntrospectionException {
        BeanInfo beanInfo = Introspector.getBeanInfo(cl, Object.class);
        //获取所有的属性描述器
        PropertyDescriptor[] pds = beanInfo.getPropertyDescriptors();
        for (PropertyDescriptor pd : pds) {
            String key = pd.getName();
            String str = "get" + key.substring(0,1).toUpperCase() + key.substring(1) + "()";

            String obj = cl.getSimpleName().substring(0,1).toLowerCase() +  cl.getSimpleName().substring(1);
            String get = obj + "." +str;



            String setStr = "set" + key.substring(0,1).toUpperCase() + key.substring(1) + "(" + get +");";
//            String setStr = "set" + key.substring(0,1).toUpperCase() + key.substring(1) + "(\"" + key +"\");";

            String setObj = cl.getSimpleName().substring(0,1).toLowerCase() +  cl.getSimpleName().substring(1)
                    + "2";
            if(setStrss != null) {
                setObj = setStrss;
            }
            String set = setObj + "." +setStr;


            System.out.println(set);
        }
    }

}
