package com.teamA.logger;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;

public class LogWriterImpl implements LogWriter {

    private final String textPath;

    public static LogWriter getInstance(LogPath logPath) {
        return new LogWriterImpl(logPath);
    }

    private LogWriterImpl(LogPath logPath) {
        this.textPath = logPath.getPath();
    }

    @Override
    public boolean write(String log) throws IOException {
        if(! checkIfFileExists(textPath) ) {
            createFile(textPath);
        }
        try ( BufferedWriter writer = new BufferedWriter(new FileWriter(textPath, true)) ) {
            writer.write(log);
        }
        return true;
    }

    private boolean checkIfFileExists(String filepath) {
        File f = new File(filepath);
        return f.exists() && !f.isDirectory();
    }

    private boolean createFile(String filepath) throws IOException {
        File path = Paths.get(filepath).toFile(); // platform independent
        path.getParentFile().mkdirs();
        return path.createNewFile();
    }
}
