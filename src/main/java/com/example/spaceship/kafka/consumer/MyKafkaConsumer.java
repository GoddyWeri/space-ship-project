package com.example.spaceship.kafka.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.example.spaceship.utils.Utils;

//@Component
public class MyKafkaConsumer {

    @KafkaListener(topics = Utils.SPACESHIP_TOPIC)
    public void listen(ConsumerRecord<String, String> record) {
        System.out.println("Received message: " + record.value());
    }
}
