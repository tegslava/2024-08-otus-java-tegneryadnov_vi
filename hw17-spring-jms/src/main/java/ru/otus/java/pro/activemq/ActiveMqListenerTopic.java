package ru.otus.java.pro.activemq;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.jms.*;
import java.io.Serializable;
import lombok.RequiredArgsConstructor;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;
import ru.otus.java.pro.config.ActiveMqConfig;
import ru.otus.java.pro.repositories.MessageRepository;

@Service
@RequiredArgsConstructor
public class ActiveMqListenerTopic {

    private final ObjectMapper objectMapper;
    private final MessageRepository messageRepository;

    @JmsListener(
            destination = ActiveMqConfig.TOPIC_EXCHANGE,
            containerFactory = ActiveMqConfig.JMS_LISTENER_TOPIC_CONTAINER_FACTORY)
    public void onMessage(Message message) {
        try {
            if (message instanceof TextMessage) onTextMessage((TextMessage) message);
            else if (message instanceof ObjectMessage) onObjectMessage((ObjectMessage) message);
            else throw new IllegalArgumentException("Message Error");
        } catch (IllegalArgumentException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static void onTextMessage(TextMessage message) throws JMSException {
        String msg = message.getText();
        System.out.format("[active-mq-topic: text] : %s\n", msg);
    }

    private void onObjectMessage(ObjectMessage message)
            throws JMSException, ClassNotFoundException, JsonProcessingException {
        String className = message.getStringProperty(ActiveMqConfig.CLASS_NAME);
        if (className == null) onSerializableObjectMessage(message);
        else onCustomObjectMessage(Class.forName(className), message);
    }

    private static void onSerializableObjectMessage(ObjectMessage message) throws JMSException {
        Serializable obj = message.getObject();
        System.out.format("[active-mq-topic: serializable] : %s\n", obj);
    }

    private void onCustomObjectMessage(Class<?> cls, ObjectMessage message)
            throws JMSException, JsonProcessingException {
        String json = String.valueOf(message.getObject());
        Object obj = objectMapper.readValue(json, cls);
        if (cls.getSimpleName().equals("Message")) {
            messageRepository.save((ru.otus.java.pro.entities.Message) obj);
        }
        System.out.format("[active-mq-topic: customObject  %s] : %s\n", cls.getSimpleName(), obj);
    }
}
