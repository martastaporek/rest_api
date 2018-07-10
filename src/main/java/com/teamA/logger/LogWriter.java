package com.teamA.logger;

import java.io.IOException;

public interface LogWriter {

    boolean write(String log) throws IOException;

}
