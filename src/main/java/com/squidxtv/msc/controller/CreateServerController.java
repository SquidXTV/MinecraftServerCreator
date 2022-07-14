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
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BackgroundPosition;
import javafx.stage.DirectoryChooser;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

public class CreateServerController implements Controller<String> {

    private final DirectoryChooser dirChooser = new DirectoryChooser();
    private File dir = null;
    private Future<?> future = null;

    @FXML
    private TextField name;
    @FXML
    private ComboBox<String> versions;
    @FXML
    private Label path;
    @FXML
    private Button back;
    @FXML
    private Label error;

    @FXML
    private void createAction() {
        if(!validate()) {
            error.setVisible(true);
            future = Main.getScheduledService().schedule(() -> Platform.runLater(() -> error.setVisible(false)), 5, TimeUnit.SECONDS);
            return;
        }
        error.setVisible(false);
        if(future != null) future.cancel(true);

        String n = name.getText();
        System.out.println("Debug: " + n);
        System.out.println("Debug: " + versions.getValue());
        App.getManager().switchScene(Scenes.SETUP, n + "|" + versions.getValue());
    }

    @FXML
    private void backAction() {
        App.getManager().switchScene(Scenes.MENU, "");
    }

    @FXML
    private void folderAction() {
        dir = dirChooser.showDialog(error.getScene().getWindow());
        if(dir == null) return;
        path.setText(dir.getAbsolutePath().replace("\\", "/"));
    }

    @Override
    public void init(String s) {
        Util.setButtonBackground("arrow_left.png", back, 48, 48, BackgroundPosition.CENTER);
        versions.setVisibleRowCount(5);
        for(SpigotVersion sv : SpigotVersion.values()) {
            versions.getItems().add(sv.name().replace("S", "Spigot - ").replace("_", "."));
        }
    }

    @Override
    public void reset() {
        dir = null;
        name.setText("");
        path.setText("");
        versions.setValue(null);
        if(future!=null) future.cancel(true);
        future = null;
        error.setVisible(false);
    }

    private boolean validate() {
        String n = name.getText();
        if(dir == null) {
            error.setText("No Directory selected!");
            return false;
        }
        if(versions.getValue() == null) {
            error.setText("No Version selected!");
            return false;
        }
        if(!Pattern.matches("^[\\w\\s.-]{1,244}$", n)) {
            error.setText("Name of Server is invalid, allowed characters: A-Za-z0-9._- and whitespaces!");
            return false;
        }
        if(Files.exists(Path.of(Main.getSaves().toUri()).resolve(n))) {
            error.setText("Server with that name already exists!");
        }
        return true;
    }
}