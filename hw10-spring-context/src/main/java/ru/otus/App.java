package ru.otus;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.otus.config.AppConfig;
import ru.otus.services.*;

@SuppressWarnings({"squid:S125", "squid:S106"})
public class App {
    public static void main(String[] args) {
/*        EquationPreparer equationPreparer = new EquationPreparerImpl();
        IOService ioService = new IOServiceStreams(System.out, System.in); // NOSONAR
        PlayerService playerService = new PlayerServiceImpl(ioService);
        GameProcessor gameProcessor = new GameProcessorImpl(ioService, equationPreparer, playerService);*/

        // ApplicationContext ctx = new ClassPathXmlApplicationContext("/spring-context.xml");
        // ApplicationContext ctx = new AnnotationConfigApplicationContext(AppConfig.class);
        // GameProcessor gameProcessor = ctx.getBean(GameProcessor.class);

        //gameProcessor.startGame();

        ApplicationContext ctx = new AnnotationConfigApplicationContext(AppConfig.class);
        Cart cart = ctx.getBean(Cart.class);
        ProductRepository productRepository = ctx.getBean(ProductRepository.class);
        // GameProcessor gameProcessor = ctx.getBean(GameProcessor.class);
        EShop eShop = new EShopImpl();
        eShop.startEShop();
    }
}
