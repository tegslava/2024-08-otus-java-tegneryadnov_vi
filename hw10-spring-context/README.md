# Домашнее задание
## Практика многопоточности

### Цель: Научиться работать с Spring Context

Написать консольное приложение, позволяющее управлять корзиной
* Имеется класс Product (id, название, цена).
* Товары хранятся в бине ProductRepository, в виде List,
  при старте приложения в него необходимо добавить 10 любых товаров.
* ProductRepository должен позволять получить весь список или один товар по id.
* Создаем бин Cart, в который можно добавлять и удалять товары по id из репозитория.
* При каждом запросе корзины из контекста, должна создаваться новая корзина.