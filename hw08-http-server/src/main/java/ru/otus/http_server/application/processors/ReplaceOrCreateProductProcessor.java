package ru.otus.http_server.application.processors;

import com.google.gson.Gson;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import ru.otus.http_server.HttpRequest;
import ru.otus.http_server.application.Item;
import ru.otus.http_server.application.Storage;

public class ReplaceOrCreateProductProcessor implements RequestProcessor {
    @Override
    public void execute(HttpRequest httpRequest, OutputStream output) throws IOException {
        Gson gson = new Gson();
        Item item = gson.fromJson(httpRequest.getBody(), Item.class);
        String statusCode = "200 OK";
        if (!Storage.replaceItem(item)) {
            Storage.add(item);
            statusCode = "201 Created";
        }
        String response = String.format(
                "HTTP/1.1 %s\r%nContent-Type: application/json\r%n\r%n %s", statusCode, httpRequest.getBody());
        output.write(response.getBytes(StandardCharsets.UTF_8));
    }
}
