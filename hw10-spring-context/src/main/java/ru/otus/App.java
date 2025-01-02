package ru.otus;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.otus.config.AppConfig;
import ru.otus.services.*;

@SuppressWarnings({"squid:S125", "squid:S106"})
public class App {
    public static void main(String[] args) {
        ApplicationContext ctx = new AnnotationConfigApplicationContext(AppConfig.class);
        EShop eshop =ctx.getBean(EShop.class);
        eshop.startEShop();
    }
}
