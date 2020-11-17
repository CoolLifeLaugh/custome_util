package com.insist.reflect.proxy;

//import org.springframework.cglib.proxy.InvocationHandler;
//import org.springframework.cglib.proxy.Proxy;

import java.lang.reflect.Proxy;

/**
 *
 */
public class ProxyMain {

    public static void main(String[] args) {
        TaskInterface taskInterface = new TaskImpl();
        taskInterface.doTask("fuck");;

        TaskInterface testInterface = (TaskInterface)Proxy.newProxyInstance(taskInterface.getClass().getClassLoader(), new Class[]{TaskInterface.class}, new CustomInvocationHandler(taskInterface));
        testInterface.doTask("global perspective");



    }
}
