package com.kewdetect.integration.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class TaskExecutorWorker {

    private static Logger log = LoggerFactory.getLogger(TaskExecutorWorker.class);

    public TaskExecutorWorker(String identityNo){
        ThreadPoolExecutor executor = new ThreadPoolExecutor(2, //  corePoolSize
                2, //  maximumPoolSize
                2, //
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(2));
        executor.execute(() -> log.info(identityNo + " :identityNo Thread named : " + Thread.currentThread().getName()));
        executor.execute(() -> log.info(identityNo + " :identityNo Thread named : " + Thread.currentThread().getName()));
        executor.shutdown();
    }
}
