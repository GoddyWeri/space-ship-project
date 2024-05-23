package com.example.spaceship.kafka.producer;

import java.util.Properties;

import org.apache.kafka.clients.producer.*;
import org.apache.kafka.clients.producer.ProducerRecord;

public class MyKafkaProducer {
	 public static void main(String[] args) {
	        Properties properties = new Properties();
	        // Configure Kafka properties
	        properties.put("bootstrap.servers", "localhost:9092");
	        properties.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
	        properties.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

	        // Create KafkaProducer
	        KafkaProducer<String, String> producer = new KafkaProducer<>(properties);

	        // Send messages to Kafka topic
	        for (int i = 0; i < 10; i++) {
	            ProducerRecord<String, String> record = new ProducerRecord<>("my-topic", "key-" + i, "message-" + i);
	            producer.send(record);
	        }

	        // Close producer
	        producer.close();
	    }
}

