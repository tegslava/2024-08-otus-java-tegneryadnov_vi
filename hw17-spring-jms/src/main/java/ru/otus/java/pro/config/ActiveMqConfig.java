package ru.otus.java.pro.config;

import jakarta.jms.ConnectionFactory;

import java.util.concurrent.TimeUnit;

import org.apache.activemq.ActiveMQConnectionFactory;
// import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jms.DefaultJmsListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.connection.CachingConnectionFactory;
import org.springframework.jms.core.JmsTemplate;

@Configuration
public class ActiveMqConfig {

    public static final String JMS_TEMPLATE = "activeMqJmsTemplate";
    public static final String JMS_TEMPLATE_TOPIC = "activeMqJmsTemplateTopic";
    public static final String JMS_LISTENER_CONTAINER_FACTORY = "activeMqJmsListenerContainerFactory";
    public static final String JMS_LISTENER_TOPIC_CONTAINER_FACTORY = "activeMqJmsListenerTopicContainerFactory";
    private static final String CONNECTION_FACTORY = "activeMqConnectionFactory";

    public static final String DESTINATION_NAME = "foo";
    public static final String TOPIC_EXCHANGE = "topic-exchange";
    public static final String CLASS_NAME = "className";

    /***
     * точка входа для работы с JMS
     * ConnectionFactory->Connection->Session->MessageProducer->send
     * @return
     */
    @Bean(CONNECTION_FACTORY)
    public ConnectionFactory connectionFactory() {
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
        connectionFactory.setUserName("myactivemquser");
        connectionFactory.setPassword("myactivemquserpass");
        connectionFactory.setBrokerURL("tcp://localhost:61616");
        connectionFactory.setTrustAllPackages(true);
        return connectionFactory;
    }

    @Bean(JMS_TEMPLATE)
    public JmsTemplate jmsTemplate(@Qualifier(CONNECTION_FACTORY) ConnectionFactory cachingConnectionFactory) {
        JmsTemplate jmsTemplate = new JmsTemplate(cachingConnectionFactory);
        jmsTemplate.setReceiveTimeout(TimeUnit.SECONDS.toMillis(10));
        return jmsTemplate;
    }

    /***
     *  pubSubDomain используется для конфигурирования JmsTemplate, если известно, какой домен JMS используется.
     *  По умолчанию значение этого свойства установлено в false, что указывает на использование домена
     *  "точка-точка", Queues.
     *  setReceiveTimeout значение времени ожидания для синхронных вызовов
     * @param connectionFactory
     * @return
     */
    @Bean(JMS_TEMPLATE_TOPIC)
    public JmsTemplate jmsTemplateTopic(@Qualifier(CONNECTION_FACTORY) ConnectionFactory connectionFactory) {
        JmsTemplate jmsTemplate = new JmsTemplate(connectionFactory);
        jmsTemplate.setReceiveTimeout(TimeUnit.SECONDS.toMillis(10));
        jmsTemplate.setPubSubDomain(true);
        return jmsTemplate;
    }

    @Bean(JMS_LISTENER_CONTAINER_FACTORY)
    public JmsListenerContainerFactory<?> jmsListenerContainerFactory(
            @Qualifier(CONNECTION_FACTORY) ConnectionFactory connectionFactory,
            DefaultJmsListenerContainerFactoryConfigurer configurer) {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        configurer.configure(factory, connectionFactory);
        return factory;
    }

    @Bean
    public CachingConnectionFactory cachingConnectionFactory(
            @Qualifier(CONNECTION_FACTORY) ConnectionFactory connectionFactory) {
        return new CachingConnectionFactory(connectionFactory);
    }

    @Bean(JMS_LISTENER_TOPIC_CONTAINER_FACTORY)
    public JmsListenerContainerFactory<?> topicListenerFactory(CachingConnectionFactory cachingConnectionFactory) {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        factory.setConnectionFactory(cachingConnectionFactory);
        factory.setPubSubDomain(true);
        return factory;
    }
}
