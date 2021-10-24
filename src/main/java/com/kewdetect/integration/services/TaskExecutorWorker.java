package com.kewdetect.integration.services;

import com.google.gson.Gson;
import com.kewdetect.integration.model.payload.request.TaskModelRequest;
import com.kwm.common.analytics.Analytics;
import com.kwm.common.analytics.AnalyticsHelper;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class TaskExecutorWorker {

    private Analytics analytics = AnalyticsHelper.geAnalytics(TaskExecutorWorker.class);

    public TaskExecutorWorker(TaskModelRequest taskModelRequest) {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(2, //  corePoolSize
                2, //  maximumPoolSize
                2, //
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(2));
        executor.execute(() -> analytics.logEvent(new Gson().toJson(taskModelRequest) + " :Thread named : " + Thread.currentThread().getName()));
        executor.execute(() -> analytics.logEvent(new Gson().toJson(taskModelRequest) + " :Thread named : " + Thread.currentThread().getName()));
        executor.shutdown();
        analytics.trace("This is TRACE");
        analytics.debug("This is DEBUG");
        analytics.info("This is INFO");
        analytics.warn("This is WARN");
        analytics.error("This is ERROR");
    }
}
