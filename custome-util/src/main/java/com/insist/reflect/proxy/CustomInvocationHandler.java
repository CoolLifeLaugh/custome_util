package com.insist.reflect.proxy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * 自定义调用handler
 */
public class CustomInvocationHandler implements InvocationHandler {

    private static final Logger logger = LoggerFactory.getLogger(CustomInvocationHandler.class);


    Object object;

    public CustomInvocationHandler(Object object){
        this.object = object;
    }
    /**
     *
     * @param proxy  proxy其实是代理对象，返回proxy的话可以对该代理对象进行连续调用。
     * @param method
     * @param args
     * @return
     * @throws Throwable
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        logger.info("proxy name:{}",proxy.getClass().getSimpleName());
        logger.info("proxy method:{}",method.getName());
        logger.info("proxy args:{}",args.length);
        Object returnObj = method.invoke(object,args);
        return returnObj;
    }
}
