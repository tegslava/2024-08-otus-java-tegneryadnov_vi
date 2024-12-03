package ru.otus.http_server.application.processors;

import com.google.gson.Gson;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.http_server.HttpRequest;
import ru.otus.http_server.application.Item;
import ru.otus.http_server.application.Storage;

public class GetAllProductsProcessor implements RequestProcessor {
    private static final Logger logger = LoggerFactory.getLogger(GetAllProductsProcessor.class);

    @Override
    public void execute(HttpRequest httpRequest, OutputStream output) throws IOException {
        List<Item> items = Storage.getItems();
        Gson gson = new Gson();
        String result = "HTTP/1.1 200 OK\r\nContent-Type: application/json\r\n\r\n" + gson.toJson(items);
        output.write(result.getBytes(StandardCharsets.UTF_8));
        logger.debug(result);
    }
}
