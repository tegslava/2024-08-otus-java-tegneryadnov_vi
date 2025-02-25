package ru.otus.java.pro;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableJms
@EnableScheduling
@EntityScan("ru.otus.java.pro.entities")
@SpringBootApplication
public class ActiveMQApp {

    public static void main(String[] args) {
        SpringApplication.run(ActiveMQApp.class, args);
    }
}
