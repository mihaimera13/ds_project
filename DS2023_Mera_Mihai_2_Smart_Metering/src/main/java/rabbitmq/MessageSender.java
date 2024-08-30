package rabbitmq;

import config.RabbitConfig;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class MessageSender {

    private final RabbitTemplate rabbitTemplate;
    private RabbitConfig rabbitConfig;

    public MessageSender() {
        rabbitConfig = new RabbitConfig();
        rabbitTemplate = new RabbitTemplate(rabbitConfig.connectionFactory());
    }

    public void send(String message) {
        rabbitTemplate.convertAndSend("queue_ds", message);
    }
}
