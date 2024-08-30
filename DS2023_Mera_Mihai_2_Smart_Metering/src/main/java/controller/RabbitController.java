package controller;

import rabbitmq.MessageSender;

public class RabbitController {

    private final MessageSender messageSender;

    public RabbitController() {
        this.messageSender = new MessageSender();
    }

    public void send(String message) {
        messageSender.send(message);
    }
}
