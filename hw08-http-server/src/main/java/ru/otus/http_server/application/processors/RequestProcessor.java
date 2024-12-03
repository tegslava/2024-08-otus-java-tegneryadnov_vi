package ru.otus.http_server.application.processors;

import java.io.IOException;
import java.io.OutputStream;
import ru.otus.http_server.HttpRequest;

public interface RequestProcessor {
    void execute(HttpRequest httpRequest, OutputStream output) throws IOException;
}
