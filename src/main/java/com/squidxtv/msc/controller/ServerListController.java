package com.squidxtv.msc.controller;

import com.squidxtv.msc.util.SceneManager;
import com.squidxtv.msc.util.Scenes;
import javafx.fxml.FXML;

public class ServerListController {


    @FXML
    private void backButtonAction() {
        SceneManager.getInstance().activate(Scenes.MENU);
    }
}
