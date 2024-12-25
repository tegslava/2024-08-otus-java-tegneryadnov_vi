package ru.otus.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EShopImpl implements EShop {
    private static final Logger logger = LoggerFactory.getLogger(EShopImpl.class.getName());

    @Override
    public void startEShop() {
        logger.info("Я родился!");
    }
}
