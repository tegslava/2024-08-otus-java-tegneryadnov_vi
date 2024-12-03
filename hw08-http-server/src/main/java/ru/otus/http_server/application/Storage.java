package ru.otus.http_server.application;

import java.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings({"java:S2140"})
public class Storage {
    private static List<Item> items;
    private static final Logger logger = LoggerFactory.getLogger(Storage.class);

    private Storage() {}

    public static void init() {
        logger.info("Хранилище проинициализировано");
        items = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            items.add(new Item("item " + i, 100 + (int) (Math.random() * 1000)));
        }
    }

    public static List<Item> getItems() {
        return Collections.unmodifiableList(items);
    }

    public static void save(Item item) {
        item.setId(UUID.randomUUID());
        items.add(item);
    }

    public static void add(Item item) {
        items.add(item);
    }

    public static boolean replaceItem(Item itemForReplace) {
        boolean replaced = false;
        int i = 0;
        Iterator<Item> iterator = items.iterator();
        do {
            Item item = iterator.next();
            if (item.getId().equals(itemForReplace.getId())) {
                items.set(i, itemForReplace);
                replaced = true;
                break;
            }
            i++;
        } while (iterator.hasNext());
        return replaced;
    }
}
