package ru.otus.java.pro.activemq;

import static ru.otus.java.pro.config.ActiveMqConfig.JMS_TEMPLATE_TOPIC;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.jms.ObjectMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;
import ru.otus.java.pro.config.ActiveMqConfig;
import ru.otus.java.pro.entities.Message;

@Slf4j
@Service
public class ActiveMqProducerTopic {
    private final JmsTemplate jmsTemplate;
    private final ObjectMapper objectMapper;

    public ActiveMqProducerTopic(@Qualifier(JMS_TEMPLATE_TOPIC) JmsTemplate jmsTemplate, ObjectMapper objectMapper) {
        this.jmsTemplate = jmsTemplate;
        this.objectMapper = objectMapper;
    }

    public void convertAndSend(Object messageObject) {
        try {
            if (messageObject instanceof String)
                jmsTemplate.convertAndSend(ActiveMqConfig.TOPIC_EXCHANGE, messageObject);
            else if (messageObject instanceof Message)
                jmsTemplate.send(ActiveMqConfig.TOPIC_EXCHANGE, createMessageWithMessage((Message) messageObject));
            else throw new IllegalArgumentException("Message Error");
        } catch (IllegalArgumentException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private MessageCreator createMessageWithMessage(Message messageObj) {
        return session -> {
            try {
                ObjectMessage objectMessage = session.createObjectMessage();
                objectMessage.setStringProperty(ActiveMqConfig.CLASS_NAME, Message.class.getName());
                objectMessage.setObject(objectMapper.writeValueAsString(messageObj));
                return objectMessage;
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        };
    }
}
