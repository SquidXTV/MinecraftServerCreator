package com.squidxtv.msc.controller;

import com.squidxtv.msc.Main;
import com.squidxtv.msc.util.SceneManager;
import com.squidxtv.msc.util.Scenes;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class MenuController {

    @FXML
    private Button exitButton;

    @FXML
    private void exitButtonAction() {
        ((Stage) exitButton.getScene().getWindow()).close();
        Main.shutdownApplication();
    }

    @FXML
    private void serverListButtonAction() {
        SceneManager.getInstance().activate(Scenes.SERVER_LIST);
    }

    @FXML
    private void createServerButtonAction() {
        SceneManager.getInstance().activate(Scenes.CREATE_SERVER);
    }

}
