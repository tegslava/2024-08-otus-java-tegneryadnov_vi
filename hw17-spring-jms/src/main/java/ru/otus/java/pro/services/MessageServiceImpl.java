package ru.otus.java.pro.services;

import java.util.List;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.java.pro.activemq.ActiveMqProducerTopic;
import ru.otus.java.pro.dtos.MessageDto;
import ru.otus.java.pro.entities.Message;
import ru.otus.java.pro.repositories.MessageRepository;

@Service
@AllArgsConstructor
public class MessageServiceImpl implements MessageService {
    private final MessageRepository messageRepository;
    private final ActiveMqProducerTopic activeMqProducerTopic;
    private static final Function<Message, MessageDto> ENTITY_TO_DTO = t -> new MessageDto(t.getUuid(), t.getText());

    @Override
    public Message create(String messageText) {
        return new Message(UUID.randomUUID(), messageText);
    }

    @Override
    public MessageDto save(Message message) {
        return ENTITY_TO_DTO.apply(messageRepository.save(message));
    }

    @Override
    public List<MessageDto> findAll() {
        return messageRepository.findAll().stream().map(ENTITY_TO_DTO).collect(Collectors.toList());
    }

    @Override
    public void send(Object messageObject) {
        activeMqProducerTopic.convertAndSend(messageObject);
    }
}
