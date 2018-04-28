package com.mdv.io;

import java.io.IOException;

public interface Queue {
    void popMessage() throws IOException;
    void getMessage() throws IOException;
    void pushMessage(String message) throws IOException;
    void clean() throws IOException;
}
