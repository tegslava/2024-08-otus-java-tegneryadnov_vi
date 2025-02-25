## Домашнее задание
### Практика работы с Spring JMS
#### Цель: Научиться работать с ActiveMQ из Spring веб-приложения

1. Развернуть ActiveMQ в Docker
2. Организовать подключение к ActiveMQ через Spring JMS
3. Реализовать возможность отправки и получения объектов типа Message(UUID uuid, String text) из 
   топика ActiveMQ
4. Для отправки сообщений реализуйте отдельный тестовый endpoint

```txt
1) Запустить контейнер из docker-compose.yaml

2) Запустить программу (ActiveMQApp)

4) Перейти в браузере http://localhost:8189/api/v1/messages/create, создать текстовое сообщение. 
   Отправить.

5) В endpoint @PostMapping("/send") создается Message(UUID uuid, String text), отправляется через 
   messageService.send(message) в топик ActiveMQ "topic-exchange". 
   Далее идет задержка, для того чтобы сообщение дошло до листинера топика. Затем следует редирект 
   на endpoint  http://localhost:8189/api/v1/messages/
   
6) На прослушивание топика настроен ActiveMqListenerTopic. Получив сообщение, листинер извлекает 
   json, восстанавливает объект Message  и сохраняет объект в репозиторий  MessageRepository.
   
7) Помимо отправки через топик в проекте реализована возможность отправки автогенерируемых 
   сообщений через очередь foo    
```