package com.squidxtv.msc;

import com.squidxtv.msc.util.Scenes;
import com.squidxtv.msc.util.SceneManager;
import javafx.application.Application;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage stage) {
        stage.setTitle("Menu");
        stage.setResizable(false);

        SceneManager manager = new SceneManager(stage);
        manager.activate(Scenes.MENU);

        stage.setOnCloseRequest(windowEvent -> {
            Main.shutdownApplication();
        });
        stage.show();
    }
}
