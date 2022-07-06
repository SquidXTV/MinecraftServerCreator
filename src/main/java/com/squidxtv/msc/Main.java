package com.squidxtv.msc;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Main {

    private static ScheduledExecutorService scheduledService;

    public static void main(String[] args) {
        scheduledService = Executors.newSingleThreadScheduledExecutor();
        App.launch(App.class, args);
    }

    public static ScheduledExecutorService getScheduledService() {
        return scheduledService;
    }

    public static void shutdownApplication() {
        ScheduledExecutorService ses = Main.getScheduledService();
        ses.shutdown();
        try {
            if (!ses.awaitTermination(800, TimeUnit.MILLISECONDS)) {
                ses.shutdownNow();
            }
        } catch (InterruptedException e) {
            ses.shutdownNow();
        }
    }
}
