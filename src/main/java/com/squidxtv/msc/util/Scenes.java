package com.squidxtv.msc.util;

public enum Scenes {
    MENU("Menu"),
    SERVER_LIST("ServerList"),
    CREATE_SERVER("CreateServer"),
    SETUP("Setup"),
    SETTINGS("Settings");

    private final String name;

    Scenes(String path) {
        this.name = path;
    }

    public String getName() {
        return name;
    }

}
