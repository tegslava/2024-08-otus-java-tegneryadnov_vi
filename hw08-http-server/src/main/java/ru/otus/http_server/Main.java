package ru.otus.http_server;

public class Main {
    public static void main(String[] args) {
        int port = Integer.parseInt((String) System.getProperties().getOrDefault("port", "8189"));
        new HttpServer(port).start();
    }
}
