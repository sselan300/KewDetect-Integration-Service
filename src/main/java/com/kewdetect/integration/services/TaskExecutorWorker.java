package com.kewdetect.integration.services;

import com.kwm.common.analytics.Analytics;
import com.kwm.common.analytics.AnalyticsHelper;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TaskExecutorWorker {

    private Analytics analytics = AnalyticsHelper.geAnalytics(TaskExecutorWorker.class);

    public TaskExecutorWorker(String identityNo) {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(2, //  corePoolSize
                2, //  maximumPoolSize
                2, //
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(2));
        executor.execute(() -> analytics.logEvent(identityNo + " :identityNo Thread named : " + Thread.currentThread().getName()));
        executor.execute(() -> analytics.logEvent(identityNo + " :identityNo Thread named : " + Thread.currentThread().getName()));
        executor.shutdown();

        analytics.trace("This is TRACE");
        analytics.debug("This is DEBUG");
        analytics.info("This is INFO");
        analytics.warn("This is WARN");
        analytics.error("This is ERROR");
    }
}
