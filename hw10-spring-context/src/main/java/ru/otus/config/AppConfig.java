package ru.otus.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.otus.services.*;
import ru.otus.services.impl.*;

@Configuration
public class AppConfig {
    @Bean
    public ProductRepository productRepository() {
        return new ProductRepositoryImpl();
    }

    @SuppressWarnings("squid:S106")
    @Bean
    public IOService ioService() {
        return new IOServiceStreams(System.out, System.in);
    }

    @Bean
    public Cart cart(IOService ioService, ProductRepository productRepository) {
        return new CartImpl(ioService, productRepository);
    }

    @Bean
    public CommandsProcessor commandProcessor(IOService ioService, Cart cart) {
        return new CommandsProcessorImpl(ioService, cart);
    }

    @Bean
    public EShop eShop(IOService ioService, Cart cart, CommandsProcessor commandProcessor) {
        return new EShopImpl(ioService, cart, commandProcessor);
    }
}
