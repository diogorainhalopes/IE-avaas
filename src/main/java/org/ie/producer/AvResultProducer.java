package org.ie.producer;

import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.protocol.types.Field.UUID;
import org.ie.model.AvResult;

public class AvResultProducer {

	final static String topic = "av-result";
	static String brokerList = "ec2-107-22-157-15.compute-1.amazonaws.com:9092";

	static void SendMessage(String message, KafkaProducer<String, String> producer, String topicTarget) {
		String seqkey = topic + UUID.class.toString();

		System.out.println("Sending new message to Kafka topic=" + topic + " with key=" + seqkey);

		ProducerRecord<String, String> record = new ProducerRecord<>(topicTarget, seqkey, message);

		producer.send(record);

		System.out.println("Message sent...");
	}

	public static void produceMessage(String msg) {
		Properties kafkaProps = new Properties();

		System.out.println("------ AV Result Producing message -----");

		kafkaProps.put("bootstrap.servers", brokerList);
		kafkaProps.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		kafkaProps.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		KafkaProducer<String, String> producer = new KafkaProducer<String, String>(kafkaProps);

		SendMessage(msg, producer, topic);

	}
}
