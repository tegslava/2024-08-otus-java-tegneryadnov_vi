package ru.otus.services.impl;

import ru.otus.services.Cart;
import ru.otus.services.EShop;
import ru.otus.services.IOService;
import ru.otus.services.CommandsProcessor;

public class EShopImpl implements EShop {
    private final IOService ioService;
    private final CommandsProcessor commandsProcessor;
    private final Cart cart;
    private static final String START_MESSAGE = "Выберите id товара из списка.\nДоступны команды: add <id>, del <id>, cart, exit";

    public EShopImpl(IOService ioService, Cart cart, CommandsProcessor commandProcessor) {
        this.ioService = ioService;
        this.commandsProcessor = commandProcessor;
        this.cart = cart;
    }

    @Override
    public void startEShop() {
        ioService.out(START_MESSAGE);
        cart.showProductsList();
        commandsProcessor.processingCommands();
    }
}