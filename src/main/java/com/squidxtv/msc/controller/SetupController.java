package com.squidxtv.msc.controller;

import com.squidxtv.msc.App;
import com.squidxtv.msc.Main;
import com.squidxtv.msc.server.spigot.SpigotVersion;
import com.squidxtv.msc.util.Controller;
import com.squidxtv.msc.util.Scenes;
import com.squidxtv.msc.util.Util;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
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
                // create save dir and store path
                Files.createDirectories(save);
                Path spigotSave = Path.of(Main.getBackup().toUri()).resolve(args[1].replace("Spigot - ", "spigot-") + ".jar");
                // checks if server already exists and if not install in Backup folder
                // then copy from Backup to spigotSave
                if(!Files.exists(spigotSave)) {
                    installServer();
                }
                Files.copy(spigotSave, Path.of(save.toUri()).resolve(args[1].replace("Spigot - ", "spigot-") + ".jar"));
                // success
                Text t = new Text("Successfully copied Server from Backup folder to " + name + " folder!\n");
                t.setFill(Color.GREEN);
                print(t);

                setupServer();
            } catch (IOException | InterruptedException e) {
                process.destroyForcibly();
                throw new RuntimeException(e);
            }
        });
    }

    /**
     * installs the server into the Backup folder
     * and prints command lines using {@link SetupController#print(BufferedReader)}
     * @throws IOException
     * @throws InterruptedException
     */
    private void installServer() throws IOException, InterruptedException {
        Text t;
        // configure and start process
        ProcessBuilder builder = new ProcessBuilder("java", "-jar", "BuildTools.jar", "--rev", version.getBuild(), "--output-dir", Main.getBackup().toString());
        builder.directory(new File(Main.getTools().toUri()));
        process = builder.start();
        // printing configuration
        t = new Text("PID: " + process.pid());
        t.setFill(Color.GREEN);
        print(t);
        print(process.inputReader());
        print(process.errorReader());
        process.waitFor();
        // done
        t = new Text("\nDone downloading Spigot Server!\n");
        t.setFill(Color.GREEN);
        print(t);
    }

    private void setupServer() throws IOException {
        Path startBatch = Path.of(save.toUri()).resolve("start.bat");
        Files.createFile(startBatch);
        String command = "java -Xmx4G -Xms2G -jar " + args[1].replace("Spigot - ", "spigot-") + ".jar --nogui"; // ToDo: add Settings functionality!
        Files.write(startBatch, List.of(command, "pause"));
    }

    /**
     * reads lines from {@link BufferedReader} and calls {@link SetupController#print(String)}
     * @param reader
     */
    private void print(BufferedReader reader) {
        Main.getService().submit(() -> {
            try {
                String line;
                while((line = reader.readLine()) != null) {
                    print(line + "\n");
                }
            }catch (IOException e) {
                throw new RuntimeException();
            }
        });
    }

    /**
     * converts String to Text and then calls {@link SetupController#print(Text)}
     * @param s string
     */
    private void print(String s) {
        print(new Text(s));
    }

    /**
     * adds text to TextFlow
     * @param t specific text to add
     */
    private void print(Text t) {
        Platform.runLater(() -> {
            text.getChildren().add(t);
            scroll.setHvalue(0.0);
            scroll.setVvalue(1.0);
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
