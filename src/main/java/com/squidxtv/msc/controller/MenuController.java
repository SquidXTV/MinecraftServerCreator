package com.squidxtv.msc.controller;

//public class MenuController implements Initializable {
//
//    @FXML
//    public Button settingsButton;
//    @FXML
//    private Button exitButton;
//
//
//    @FXML
//    private void exitButtonAction() {
//        ((Stage) exitButton.getScene().getWindow()).close();
//        Main.shutdownApplication();
//    }
//
//    @FXML
//    private void serverListButtonAction() {
//        App.getManager().switchScene(Scenes.SERVER_LIST, Collections.EMPTY_LIST);
//    }
//
//    @FXML
//    private void createServerButtonAction() {
//        App.getManager().switchScene(Scenes.CREATE_SERVER, Collections.EMPTY_LIST);
//    }
//
//    @Override
//    public void initialize(URL url, ResourceBundle resourceBundle) {
//        Util.setButtonBackground("settings.png", settingsButton, 48, 48);
//    }
//
//    public void settingsButtonAction(ActionEvent event) {
//        App.getManager().switchScene(Scenes.SETTINGS, Collections.EMPTY_LIST);
//    }
//}

import com.squidxtv.msc.App;
import com.squidxtv.msc.Main;
import com.squidxtv.msc.util.Controller;
import com.squidxtv.msc.util.Scenes;
import com.squidxtv.msc.util.Util;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class MenuController implements Controller<String> {

    @FXML
    public Button settings;
    @FXML
    private Button exit;


    @FXML
    private void createServerAction() {
        App.getManager().switchScene(Scenes.CREATE_SERVER, "");
    }

    @FXML
    private void serverListAction() {
        App.getManager().switchScene(Scenes.SERVER_LIST, "");
    }

    @FXML
    private void settingsAction() {
        App.getManager().switchScene(Scenes.SETTINGS, "");
    }

    @FXML
    private void exitAction() {
        ((Stage) exit.getScene().getWindow()).close();
        Main.shutdownApplication();
    }

    @Override
    public void init(String s) {
        Util.setButtonBackground("settings.png", settings, 48, 48);
    }

    @Override
    public void reset() {

    }
}