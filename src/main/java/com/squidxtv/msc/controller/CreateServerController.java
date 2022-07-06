package com.squidxtv.msc.controller;

import com.squidxtv.msc.Main;
import com.squidxtv.msc.util.SceneManager;
import com.squidxtv.msc.util.Scenes;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;

import java.io.File;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class CreateServerController {

    private DirectoryChooser directoryChooser;
    private File selectedFile;
    private String path;
    private Future currentFuture;

    @FXML
    private Button selectFolderButton;
    @FXML
    private TextField name;
    @FXML
    private Label folderLabel;
    @FXML
    private ComboBox<String> versions;

    @FXML
    private Label nameErrorMessage;
    @FXML
    private Label versionErrorMessage;
    @FXML
    private Label folderErrorMessage;

    @FXML
    private void initialize() {
        directoryChooser = new DirectoryChooser();
        versions.setVisibleRowCount(5);
        versions.getItems().add("Spigot - 1.18.1");
        versions.getItems().add("Spigot - 1.18");
        versions.getItems().add("Spigot - 1.17.1");
        versions.getItems().add("Spigot - 1.17");
        versions.getItems().add("Spigot - 1.16.5");
        versions.getItems().add("Spigot - 1.16.4");
        versions.getItems().add("Spigot - 1.16.3");
        versions.getItems().add("Spigot - 1.16.2");
        versions.getItems().add("Spigot - 1.16.1");
        versions.getItems().add("Spigot - 1.15.2");
        versions.getItems().add("Spigot - 1.15.1");
        versions.getItems().add("Spigot - 1.15");
        versions.getItems().add("Spigot - 1.14.4");
        versions.getItems().add("Spigot - 1.14.3");
        versions.getItems().add("Spigot - 1.14.2");
        versions.getItems().add("Spigot - 1.14.1");
        versions.getItems().add("Spigot - 1.14");
        versions.getItems().add("Spigot - 1.13.2");
        versions.getItems().add("Spigot - 1.13.1");
        versions.getItems().add("Spigot - 1.13");
        versions.getItems().add("Spigot - 1.12.2");
        versions.getItems().add("Spigot - 1.12.1");
        versions.getItems().add("Spigot - 1.12");
        versions.getItems().add("Spigot - 1.11.2");
        versions.getItems().add("Spigot - 1.11.1");
        versions.getItems().add("Spigot - 1.11");
        versions.getItems().add("Spigot - 1.10.2");
        versions.getItems().add("Spigot - 1.10");
        versions.getItems().add("Spigot - 1.9.4");
        versions.getItems().add("Spigot - 1.9.2");
        versions.getItems().add("Spigot - 1.9");
        versions.getItems().add("Spigot - 1.8.8");
        versions.getItems().add("Spigot - 1.8.7");
        versions.getItems().add("Spigot - 1.8.6");
        versions.getItems().add("Spigot - 1.8.5");
        versions.getItems().add("Spigot - 1.8.4");
        versions.getItems().add("Spigot - 1.8.3");
        versions.getItems().add("Spigot - 1.8");
        versions.getItems().add("Spigot - 1.7.10");
        versions.getItems().add("Spigot - 1.7.9");
        versions.getItems().add("Spigot - 1.7.8");
        versions.getItems().add("Spigot - 1.7.5");
        versions.getItems().add("Spigot - 1.7.2");
        versions.getItems().add("Spigot - 1.6.4");
        versions.getItems().add("Spigot - 1.6.2");
        versions.getItems().add("Spigot - 1.5.2");
        versions.getItems().add("Spigot - 1.5.1");
        versions.getItems().add("Spigot - 1.4.7");
        versions.getItems().add("Spigot - 1.4.6");
    }

    @FXML
    private void createServer() {
        if(currentFuture != null) currentFuture.cancel(true);
        if(name.getText() == null || name.getText().equals("")) nameErrorMessage.setVisible(true);
        if(versions.getValue() == null) versionErrorMessage.setVisible(true);
        if(selectedFile == null || !selectedFile.isDirectory()) folderErrorMessage.setVisible(true);

        currentFuture = Main.getScheduledService().schedule(() -> {
            Platform.runLater(() -> {
                nameErrorMessage.setVisible(false);
                versionErrorMessage.setVisible(false);
                folderErrorMessage.setVisible(false);
            });
        }, 5, TimeUnit.SECONDS);

        if(nameErrorMessage.isVisible() || versionErrorMessage.isVisible() || folderErrorMessage.isVisible()) return;
        // ToDo: impl create directory, download jar, run start.bat, accept eula, start again | Concurrent!
    }

    @FXML
    private void selectFolder() {
        selectedFile = directoryChooser.showDialog(selectFolderButton.getScene().getWindow());
        if(selectedFile == null) return;
        path = selectedFile.getAbsolutePath().replace("\\", "/");
        folderLabel.setText(path);
    }

    @FXML
    private void backButtonAction() {
        reset();
        SceneManager.getInstance().activate(Scenes.MENU);
    }

    private void reset() {
        name.setText("");
        versions.setValue(null);
        path = "";
        selectedFile = null;
        folderLabel.setText("");
        nameErrorMessage.setVisible(false);
        versionErrorMessage.setVisible(false);
        folderErrorMessage.setVisible(false);
    }
}
