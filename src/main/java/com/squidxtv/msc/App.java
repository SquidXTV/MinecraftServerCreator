package com.squidxtv.msc;

import com.squidxtv.msc.util.Manager;
import com.squidxtv.msc.util.Scenes;
import javafx.application.Application;
import javafx.stage.Stage;

public class App extends Application {

    private static Manager manager;

    @Override
    public void start(Stage stage) {
        stage.setTitle("Minecraft Server Creator");
        stage.setResizable(false);

        manager = new Manager(stage);
        manager.switchScene(Scenes.MENU, "");

        stage.setOnCloseRequest(windowEvent -> Main.shutdownApplication());
        stage.show();
    }

    public static Manager getManager() {return manager;}

}
