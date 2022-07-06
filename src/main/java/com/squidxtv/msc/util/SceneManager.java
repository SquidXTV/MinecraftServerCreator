package com.squidxtv.msc.util;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;
import java.util.Objects;

public class SceneManager {

    private static SceneManager instance;

    private final HashMap<Scenes, Pane> scenes = new HashMap<>();
    private final Stage main;

    public SceneManager(Stage main) {
        instance = this;

        this.main = main;
        try {
            for (Scenes scene : Scenes.values()) {
                addScene(scene);
            }
        } catch (IOException e) {
            System.exit(404);
        }
    }

    public void addScene(Scenes scene) throws IOException {
        Pane pane = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(scene.getPath())));
        scenes.put(scene, pane);
    }

    public void removeScene(Scenes scene) {
        scenes.remove(scene);
    }

    public void activate(Scenes scene) {
        if(!scenes.containsKey(scene)) return;
        if(main.getScene() == null) main.setScene(new Scene(scenes.get(scene)));
        else main.getScene().setRoot(scenes.get(scene));

    }

    public static SceneManager getInstance() {return instance;}
}
