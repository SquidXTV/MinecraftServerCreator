package com.squidxtv.msc.controller;

import com.squidxtv.msc.App;
import com.squidxtv.msc.Main;
import com.squidxtv.msc.util.Controller;
import com.squidxtv.msc.util.Scenes;
import com.squidxtv.msc.util.Util;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.BackgroundPosition;
import javafx.util.StringConverter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

public class ServerListController implements Controller<String> {

    @FXML
    private Button back;
    @FXML
    private ComboBox<Path> serverList;

    @FXML
    private void backAction() {
        App.getManager().switchScene(Scenes.MENU, "");
    }

    @Override
    public void init(String s) {
        Util.setButtonBackground("arrow_left.png", back, 48, 48, BackgroundPosition.CENTER);
        serverList.setVisibleRowCount(7);
        try(Stream<Path> stream = Files.walk(Main.getSaves(), 1)) {
            stream.filter(path -> !path.equals(Main.getSaves())).forEach(path -> {
                serverList.getItems().add(path);
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        serverList.setConverter(new StringConverter<>() {
            @Override
            public String toString(Path path) {
                if (path == null) return "";
                return path.getFileName().toString();
            }

            @Override
            public Path fromString(String s) {
                Path path = Path.of(Main.getSaves().toUri()).resolve(s);
                if(Files.exists(path)) return path;
                return null;
            }
        });
    }

    @Override
    public void reset() {
        serverList.getItems().clear();
    }
}
