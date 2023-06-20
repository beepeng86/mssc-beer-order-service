package guru.sfg.beer.order.service.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by jt on 2019-07-20.
 */
@Configuration
public class RabbitmqConfig {

    public static final String VALIDATE_ORDER_QUEUE          = "validate-order";
    public static final String VALIDATE_ORDER_RESPONSE_QUEUE = "validate-order-response";
    public static final String ALLOCATE_ORDER_QUEUE          = "allocate-order";
    public static final String ALLOCATE_ORDER_RESPONSE_QUEUE = "allocate-order-response";
    public static final String ALLOCATE_FAILURE_QUEUE        = "allocation-failure";
    public static final String DEALLOCATE_ORDER_QUEUE        = "deallocate-order" ;

    @Bean
    public Jackson2JsonMessageConverter producerJackson2MessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    Queue validateOrderQueue() {
        return QueueBuilder.durable(VALIDATE_ORDER_QUEUE)
                .build();
    }

    @Bean
    Queue validateOrderResponseQueue() {
        return QueueBuilder.durable(VALIDATE_ORDER_RESPONSE_QUEUE)
                        .build();
    }

    @Bean
    Queue allocateOrderQueue() {
        return QueueBuilder.durable(ALLOCATE_ORDER_QUEUE)
                .build();
    }

    @Bean
    Queue allocateOrderFailureQueue() {
        return QueueBuilder.durable(ALLOCATE_FAILURE_QUEUE)
                .build();
    }

    @Bean
    Queue allocateOrderResponseQueue() {
        return QueueBuilder.durable(ALLOCATE_ORDER_RESPONSE_QUEUE)
                .build();
    }

    @Bean
    Queue deallocateQueue() {
        return QueueBuilder.durable(DEALLOCATE_ORDER_QUEUE)
                .build();
    }

    @Bean
    TopicExchange exchange() {
        return ExchangeBuilder.topicExchange("spring-boot-exchange").build();
    }

    @Bean
    Binding validateOrderBinding(Queue validateOrderQueue, TopicExchange exchange) {
        return BindingBuilder.bind(validateOrderQueue).to(exchange)
                .with(VALIDATE_ORDER_QUEUE);
    }

    @Bean
    Binding validateResponseBinding(Queue validateOrderResponseQueue, TopicExchange exchange) {
        return BindingBuilder.bind(validateOrderResponseQueue).to(exchange)
                .with(VALIDATE_ORDER_RESPONSE_QUEUE);
    }

    @Bean
    Binding allocateBinding(Queue allocateOrderQueue, TopicExchange exchange) {
        return BindingBuilder.bind(allocateOrderQueue).to(exchange)
                .with(ALLOCATE_ORDER_QUEUE);
    }

    @Bean
    Binding allocateResponseBinding(Queue allocateOrderResponseQueue, TopicExchange exchange) {
        return BindingBuilder.bind(allocateOrderResponseQueue).to(exchange)
                .with(ALLOCATE_ORDER_RESPONSE_QUEUE);
    }

    @Bean
    Binding allocateFailureBinding(Queue allocateOrderFailureQueue, TopicExchange exchange) {
        return BindingBuilder.bind(allocateOrderFailureQueue).to(exchange)
                .with(ALLOCATE_FAILURE_QUEUE);
    }

    @Bean
    Binding deallocateBinding(Queue deallocateQueue, TopicExchange exchange) {
        return BindingBuilder.bind(deallocateQueue).to(exchange)
                .with(DEALLOCATE_ORDER_QUEUE);
    }

//    @Bean
//    SimpleMessageListenerContainer container(ConnectionFactory connectionFactory) {
//        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
//        container.setConnectionFactory(connectionFactory);
//        container.setQueueNames(VALIDATE_ORDER_QUEUE, VALIDATE_ORDER_RESPONSE_QUEUE,
//                ALLOCATE_ORDER_QUEUE, ALLOCATE_ORDER_RESPONSE_QUEUE, ALLOCATE_FAILURE_QUEUE,
//                DEALLOCATE_ORDER_QUEUE);
////        container.setMessageListener(listenerAdapter);
//        return container;
//    }

//    @Bean
//    MessageListenerAdapter listenerAdapter(BeerOrderAllocationResultListener receiver, org.springframework.amqp.support.converter.MessageConverter converter) {
//        MessageListenerAdapter a = new MessageListenerAdapter(receiver, "receiveMessage");
//        a.setMessageConverter(converter);
//        return a;
//    }

}
