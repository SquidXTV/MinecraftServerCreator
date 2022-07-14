package com.squidxtv.msc.controller;

import com.squidxtv.msc.App;
import com.squidxtv.msc.Main;
import com.squidxtv.msc.server.spigot.SpigotVersion;
import com.squidxtv.msc.util.Controller;
import com.squidxtv.msc.util.Scenes;
import com.squidxtv.msc.util.Util;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class SetupController implements Controller<String> {

    @FXML
    private ScrollPane scroll;
    @FXML
    private TextFlow text;
    @FXML
    private Button back;
    @FXML
    private Label info;

    private String[] args = null;
    private String name = null;
    private SpigotVersion version = null;
    private Path save = null;

    private Future<?> future = null;
    private Process process = null;

    @Override
    public void init(String s) {
        Util.setButtonBackground("arrow_left.png", back, 48, 48, BackgroundPosition.CENTER);

        args = s.split("\\|");
        System.out.println("Debug: " + Arrays.toString(args));
        name = args[0];
        version = SpigotVersion.fromVersion(args[1]);
        save = Path.of(Main.getSaves().toUri()).resolve(name);
        info.setText(args[1] + " - " + name);

        future = Main.getService().submit(() -> {
            try {
                Files.createDirectories(save);
                Path spigotSave = Path.of(Main.getBackup().toUri()).resolve(name.replace("Spigot - ", "spigot-") + ".jar");
                if(!Files.exists(spigotSave)) {
                    ProcessBuilder builder = new ProcessBuilder("java", "-jar", "BuildTools.jar", "--rev", version.getBuild(), "--output-dir", Main.getBackup().toString());
                    builder.directory(new File(Main.getTools().toUri()));
                    process = builder.start();
                    System.out.println("Process PID: " + process.pid());
                    printInputStream(process.inputReader());
                    printInputStream(process.errorReader());
                    process.waitFor();
                    Platform.runLater(() -> {
                        text.getChildren().add(new Text("DONE!"));
                        scroll.setVvalue(1.0);
                        scroll.setHvalue(0);
                    });
                }
                Files.copy(spigotSave, Path.of(save.toUri()).resolve(name.replace("Spigot - ", "spigot-") + ".jar"));
            } catch (IOException | InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private void printInputStream(BufferedReader reader) {
        Main.getService().submit(() -> {
            try{
                String line;
                while((line = reader.readLine()) != null) {
                    String finalLine = line;
                    Platform.runLater(() -> {
                        text.getChildren().add(new Text(finalLine + "\n"));
                        scroll.setVvalue(1.0);
                    });
                    TimeUnit.MICROSECONDS.sleep(100);
                }
            } catch (IOException | InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Override
    public void reset() {
        args = null;
        name = null;
        version = null;
        save = null;
        if(future != null) future.cancel(true);
        future = null;
        if(process != null) {
            process.destroy();
            process = null;
        }
    }

    @FXML
    private void backAction() {
        App.getManager().switchScene(Scenes.CREATE_SERVER, "");
    }
}
