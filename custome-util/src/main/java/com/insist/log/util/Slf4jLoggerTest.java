package com.insist.log.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author fanping.jwq@alibaba-inc.com
 * @Date 2020-06-05 20:48
 *
 * 如果要切换底层的日志框架，只需要替换SLF4J的日志桥接Adapter即可，
 * 比如从 java.util.logging转换到Log4j的话，
 * 只需要将slf4j-jdk14-1.7.21.jar替换到slf4j-log4j12-1.7.21.jar。
 *
 * slf4j-api-1.7.21.jar 是一直要有的，应用要使用SLF4J的接口嘛。
 */
public class Slf4jLoggerTest {

    private static final Logger logger = LoggerFactory.getLogger(Slf4jLoggerTest.class);


    public static void main(String[] args) {
        logger.info("hello world");
    }
}
