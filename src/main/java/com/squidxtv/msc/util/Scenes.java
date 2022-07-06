package com.squidxtv.msc.util;

public enum Scenes {
    MENU("/view/Menu.fxml"),
    SERVER_LIST("/view/ServerList.fxml"),
    CREATE_SERVER("/view/CreateServer.fxml");

    private final String path;

    Scenes(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}
