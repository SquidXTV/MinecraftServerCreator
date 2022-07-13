package com.squidxtv.msc.controller;

import com.squidxtv.msc.App;
import com.squidxtv.msc.util.Controller;
import com.squidxtv.msc.util.Scenes;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;

import java.io.File;
import java.util.concurrent.Future;

//public class CreateServerController {
//
//    private DirectoryChooser directoryChooser;
//    private File directory;
//    private String path;
//    private Future<?> currentFuture;
//
//    @FXML
//    private Button backButton;
//    @FXML
//    private Button selectFolderButton;
//    @FXML
//    private TextField nameTextField;
//    @FXML
//    private Label folderLabel;
//    @FXML
//    private ComboBox<String> versions;
//
//    @FXML
//    private Label errorMessage;
//
//    @FXML
//    private void initialize() {
//        App.setButtonBackground("arrow_left.png", backButton, 48, 48, BackgroundPosition.CENTER);
//        directoryChooser = new DirectoryChooser();
//        versions.setVisibleRowCount(5);
//        for(SpigotVersion sv : SpigotVersion.values()) {
//            versions.getItems().add(sv.name().replace("S", "Spigot - ").replace("_", "."));
//        }
//    }
//
//    @FXML
//    private void createServer() {
//        if(!validate()) {
//            errorMessage.setVisible(true);
//            currentFuture = Main.getScheduledService().schedule(() -> Platform.runLater(() -> errorMessage.setVisible(false)), 5, TimeUnit.SECONDS);
//            return;
//        }
//        errorMessage.setVisible(false);
//        if(currentFuture != null) currentFuture.cancel(true);
//
//        String name = nameTextField.getText();
//        SpigotVersion version = SpigotVersion.fromVersion(versions.getValue());
//        Path save = Path.of(Main.getSaves().toUri()).resolve(name);
//
//        Main.getScheduledService().schedule(() -> {
//            try {
//                Files.createDirectories(save);
//                Path spigotFile = Path.of(Main.getBackup().toUri()).resolve(name.replace("Spigot - ", "spigot-") + ".jar");
//                if(!Files.exists(spigotFile)) {
//                    // download with buildtools into backup folder
//                    ProcessBuilder builder = new ProcessBuilder("java", "-jar", "BuildTools.jar", "--rev", version.getBuild(), "--output-dir", Main.getBackup().toString());
//                    builder.directory(new File(Main.getTools().toUri()));
//                    Process p = builder.start();
//                    p.waitFor();
//                }
//                Files.copy(spigotFile, save);
//                // generate and run start.bat
//                // accept eula
//                // run start.bat
//            } catch (IOException | InterruptedException e) {
//                throw new RuntimeException(e);
//            }
//        }, 100, TimeUnit.MILLISECONDS);
//    }
//
//    @FXML
//    private void selectFolder() {
//        directory = directoryChooser.showDialog(selectFolderButton.getScene().getWindow());
//        if(directory == null) return;
//        path = directory.getAbsolutePath().replace("\\", "/");
//        folderLabel.setText(path);
//    }
//
//    @FXML
//    private void backButtonAction() {
//        reset();
//        SceneManager.getInstance().activate(Scenes.MENU);
//    }
//
//    private void reset() {
//        nameTextField.setText("");
//        versions.setValue(null);
//        path = "";
//        directory = null;
//        folderLabel.setText("");
//        errorMessage.setVisible(false);
//    }
//
//    private boolean validate() {
//        String name = nameTextField.getText();
//
//        if(currentFuture != null) currentFuture.cancel(true);
//        if(directory == null) {
//            errorMessage.setText("No Directory selected!");
//            return false;
//        }
//        if(versions.getValue() == null) {
//            errorMessage.setText("No Version selected!");
//            return false;
//        }
//        if(!Pattern.matches("^[\\w\\s.-]{1,244}$", name)) {
//            errorMessage.setText("Name of Server is invalid, allowed characters: A-Za-z0-9._- and whitespaces!");
//            return false;
//        }
//        if(Files.exists(Path.of(Main.getSaves().toUri()).resolve(name))) {
//            errorMessage.setText("Server with that name already exists!");
//        }
//        return true;
//    }
//}

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
    private void backAction() {
        App.getManager().switchScene(Scenes.MENU, "");
    }

    @Override
    public void init(String s) {}

    @Override
    public void reset() {}
}