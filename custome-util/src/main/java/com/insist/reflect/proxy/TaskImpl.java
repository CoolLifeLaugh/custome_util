package com.insist.reflect.proxy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 任务执行
 */
public class TaskImpl implements TaskInterface{

    private static final Logger logger = LoggerFactory.getLogger(TaskImpl.class);


    @Override
    public String getTaskName() {
        return "can be proxy task";
    }

    @Override
    public void doTask(String str) {
        logger.info("hello, I'm {}, and working for the task",getTaskName());
    }
}
