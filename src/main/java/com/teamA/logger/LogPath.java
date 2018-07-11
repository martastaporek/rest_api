package com.teamA.logger;

public enum LogPath {

    SYSTEM_LOG("src/main/resources/logs/systemLog/logs.txt");

    private String path;

    LogPath(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}