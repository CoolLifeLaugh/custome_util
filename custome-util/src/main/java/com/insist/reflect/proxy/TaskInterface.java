package com.insist.reflect.proxy;

/**
 * 任务接口
 */
public interface TaskInterface {

    /**
     * 获取任务名称
     * @return
     */
    String getTaskName();


    /**
     * 执行任务
     */
    void doTask(String str);
}
