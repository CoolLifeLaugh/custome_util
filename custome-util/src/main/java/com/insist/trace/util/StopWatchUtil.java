package com.insist.trace.util;

import com.google.common.base.Stopwatch;

public class StopWatchUtil {

    public static void main(String[] args) throws InterruptedException {

        Stopwatch stopwatch = Stopwatch.createStarted();
        Thread.sleep(10000L);
        stopwatch.elapsed();

        System.out.printf(stopwatch.toString());

    }
}
