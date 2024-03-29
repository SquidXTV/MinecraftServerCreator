package com.squidxtv.msc;

import com.squidxtv.msc.server.DownloadManager;
import javafx.application.Application;
import javafx.application.Platform;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Main {

    private static ScheduledExecutorService scheduledService;
    private static ExecutorService service;
    private static Path saves;
    private static Path backup;
    private static Path tools;

    public static void main(String[] args) throws IOException {
        scheduledService = Executors.newSingleThreadScheduledExecutor();
        service = Executors.newFixedThreadPool(3);

        saves = Files.createDirectories(Path.of(System.getenv("APPDATA"), "Minecraft Server Creator", "saves"));
        backup = Files.createDirectories(Path.of(System.getenv("APPDATA"), "Minecraft Server Creator", "backup"));
        tools = Files.createDirectories(Path.of(System.getenv("APPDATA")).resolve("Minecraft Server Creator").resolve("Tools"));

        DownloadManager.downloadBuildTools();
        Application.launch(App.class, args);
    }

    public static ScheduledExecutorService getScheduledService() {
        return scheduledService;
    }
    public static ExecutorService getService() {return service;}
    public static Path getSaves() {return saves;}
    public static Path getBackup() {return backup;}
    public static Path getTools() {return tools;}

    public static void shutdownApplication() {
        shutdownService(getScheduledService());
        shutdownService(getService());
        // shutdown Process

        Platform.exit();
    }

    private static void shutdownService(ExecutorService es) {
        es.shutdown();
        try {
            if(!es.awaitTermination(800, TimeUnit.MILLISECONDS)) {
                es.shutdownNow();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            es.shutdownNow();
        }
    }
}
