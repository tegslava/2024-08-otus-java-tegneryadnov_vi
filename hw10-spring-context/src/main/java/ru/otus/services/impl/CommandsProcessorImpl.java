package ru.otus.services.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.services.Cart;
import ru.otus.services.IOService;
import ru.otus.services.CommandsProcessor;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;

public class CommandsProcessorImpl implements CommandsProcessor {
    private final Cart cart;
    private final IOService ioService;
    private final Map<String, BiFunction<Cart, Long, Integer>> cmdMap = new HashMap<>();
    private static final Logger logger = LoggerFactory.getLogger(CommandsProcessor.class);
    private static final String PRPOMPT_STRING = "Введите команду:";

    private void initCmdMap() {
        cmdMap.put("add", (Cart cart, Long id) -> {
            cart.addProduct(id);
            return 0;
        });
        cmdMap.put("del", (Cart cart, Long id) -> {
            cart.removeProduct(id);
            return 0;
        });
        cmdMap.put("cart", (Cart cart, Long id) -> {
            cart.showPurchase();
            return 0;
        });
        cmdMap.put("exit", (Cart cart, Long id) -> -1);
    }

    public CommandsProcessorImpl(IOService ioService, Cart cart) {
        this.cart = cart;
        this.ioService = ioService;
        initCmdMap();
    }

    @Override
    public void processingCommands() {
        while (true) {
            var inpArr = ioService.readLn(PRPOMPT_STRING).trim().toLowerCase().split(" ");
            var cmd = cmdMap.entrySet().stream()
                    .filter(e -> e.getKey().equals(inpArr[0]))
                    .findFirst()
                    .map(Map.Entry::getValue);
            if (cmd.isEmpty()) continue;
            long id = -1L;
            if (inpArr.length > 1)
                try {
                    id = Long.parseLong(inpArr[1]);
                } catch (NumberFormatException e) {
                    logger.error(e.getMessage());
                    continue;
                }
            try {
                if (cmd.get().apply(cart, id) == -1)
                    break;
            } catch (Exception e) {
                logger.error(e.getMessage());
            }
        }
    }
}
