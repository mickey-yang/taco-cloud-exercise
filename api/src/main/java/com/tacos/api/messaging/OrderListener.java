package com.tacos.api.messaging;

import com.tacos.domain.TacoOrder;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Component;

@Component
public class OrderListener {

//    @KafkaListener(topics = "tacocloud.orders.topic")
//    public void handle(TacoOrder order) {
//        System.out.println("Received order: " + order);
//    }

    @KafkaListener(topics = "tacocloud.orders.topic", groupId = "test-consumer-group")
    public void handle(TacoOrder order, Message<TacoOrder> message) {
        MessageHeaders headers = message.getHeaders();
        System.out.println("Message received: Partition " + headers.get(KafkaHeaders.RECEIVED_PARTITION));
        System.out.println("Timestamp: " + headers.get(KafkaHeaders.RECEIVED_TIMESTAMP));
        System.out.println("Received order: " + order);
    }


}
