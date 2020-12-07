package kz.placer.hotels.hotels;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class Producer{

	private static final String TOPIC = "quickstart-events";

	private KafkaTemplate<String, String> kafkaTemplate = kafkaTemplate();

	public Map<String, Object> producerConfigs (){
		Map<String, Object> config = new HashMap<>();

		config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
		config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);

		return config;
	}

	public ProducerFactory<String, String> producerFactory (){
		return new DefaultKafkaProducerFactory<>(producerConfigs());
	}

	public KafkaTemplate<String, String> kafkaTemplate (){
		KafkaTemplate<String, String> template = new KafkaTemplate<>(producerFactory());
		return template;
	}

	public String Notify (String notifyBody){
		System.out.println(String.format("#### -> Producing book request to notification service -> %s", notifyBody));
		System.out.println(kafkaTemplate);
		kafkaTemplate.send(TOPIC, notifyBody);
		System.out.println(kafkaTemplate);
		System.out.println(String.format("#### -> Producing book request to notification service -> %s", notifyBody));

		return "Successfully";
	}
}