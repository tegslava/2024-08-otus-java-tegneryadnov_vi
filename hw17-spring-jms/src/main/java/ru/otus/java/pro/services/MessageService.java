package ru.otus.java.pro.services;

import java.util.List;
import ru.otus.java.pro.dtos.MessageDto;
import ru.otus.java.pro.entities.Message;

public interface MessageService {
    List<MessageDto> findAll();

    void send(Object messageObject);

    Message create(String messageText);
}
