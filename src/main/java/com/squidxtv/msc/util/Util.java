package com.squidxtv.msc.util;

import com.squidxtv.msc.App;
import javafx.scene.image.Image;
import javafx.scene.layout.*;

import java.io.IOException;
import java.io.InputStream;

public class Util {

    public static <T extends Region> void setButtonBackground(String file, T t, int width, int height) {
        setButtonBackground(file, t, width, height, null);
    }

    public static <T extends Region> void setButtonBackground(String file, T t, int width, int height, BackgroundPosition pos) {
        try(InputStream in = App.class.getResourceAsStream("/images/" + file)) {
            if(in == null){
                System.err.println("Util#setButtonBackground: in == null\n" +
                        "couldn't load image: " + file);
                return;
            }
            Image image = new Image(in);
            BackgroundSize size = new BackgroundSize(width, height, false, false, false, false);
            BackgroundImage background = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, pos, size);
            t.setBackground(new Background(background));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
