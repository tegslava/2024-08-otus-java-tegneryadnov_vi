package ru.otus.services.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.services.Cart;
import ru.otus.services.IOService;
import ru.otus.services.CommandsProcessor;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

public class CommandsProcessorImpl implements CommandsProcessor {
    private final Cart cart;
    private final IOService ioService;
    private final Map<String, BiConsumer<Cart, Long>> cmdMap = new HashMap<>();
    private static final Logger logger = LoggerFactory.getLogger(CommandsProcessor.class);
    private static final String PRPOMPT_STRING = "Введите команду:";
    private boolean doing = true;

    private void initCmdMap() {
        cmdMap.put("add", Cart::addProduct);
        cmdMap.put("del", Cart::removeProduct);
        cmdMap.put("cart", (cart, id) ->
                cart.showPurchase());
        cmdMap.put("exit", (cart, id) -> doing = false);
    }

    public CommandsProcessorImpl(IOService ioService, Cart cart) {
        this.cart = cart;
        this.ioService = ioService;
        initCmdMap();
    }

    @Override
    public void processingCommands() {
        while (doing) {
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
                cmd.get().accept(cart, id);
            } catch (Exception e) {
                logger.error(e.getMessage());
            }
        }
    }
}
