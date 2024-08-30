package config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    @Bean
    Queue queue() {
        return new Queue("queue_ds", true);
    }

    @Bean
    DirectExchange exchange() {
        return new DirectExchange("exchange_ds");
    }

    @Bean
    Binding binding(Queue queue, DirectExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with("queue_ds");
    }

    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setUri("amqps://rat.rmq2.cloudamqp.com/zkeorwan");
        connectionFactory.setUsername("zkeorwan");
        connectionFactory.setPassword("DfVV4v5GHo8N8-hxIsWP_5OUpU3OJ1Wt");
        connectionFactory.setVirtualHost("zkeorwan");
        connectionFactory.setPort(5671);

        return connectionFactory;
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> {
            if (ack) {
                System.out.println("Message received by broker");
            } else {
                System.out.println("Message not received by broker");
            }
        });
        return rabbitTemplate;
    }
}
