package ru.otus.http_server.application.processors;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import ru.otus.http_server.HttpRequest;

public class HelloWorldRequestProcessor implements RequestProcessor {
    @Override
    public void execute(HttpRequest httpRequest, OutputStream output) throws IOException {
        String response =
                "HTTP/1.1 200 OK\r\nContent-Type: text/html\r\n\r\n<html><link rel=\"icon\" href=\"data:,\"><body><h1>Hello World!!!</h1></body></html>";
        output.write(response.getBytes(StandardCharsets.UTF_8));
    }
}
