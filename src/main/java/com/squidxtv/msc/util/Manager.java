package com.squidxtv.msc.util;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class Manager {
    private final Stage window;
    private final Map<Scenes, View> scenes = new HashMap<>();
    private Scenes current = null;

    public Manager(Stage window) {
        this.window = window;
        for(Scenes s : Scenes.values()) {
            try(InputStream stream = getClass().getResourceAsStream("/view/" + s.getName() + ".fxml")) {
                FXMLLoader loader = new FXMLLoader();
                Scene scene = new Scene(loader.load(stream));
                scene.getStylesheets().add("/css/" + s.getName() + ".css");
                View view = new View(scene, loader.getController());
                scenes.put(s, view);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @SuppressWarnings("unchecked")
    public <T> void switchScene(Scenes scene, T args) {
        View view = scenes.get(scene);
        ((Controller<T>) view.controller()).init(args);
        if(current != null) scenes.get(current).controller().reset();
        window.setScene(view.scene());
        current = scene;
    }
}
