package kz.placer.hotels.rooms;

import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.deser.std.StringDeserializer;
import kz.placer.hotels.rooms.models.RoomModel;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.support.converter.BatchMessagingMessageConverter;
import org.springframework.kafka.support.converter.StringJsonMessageConverter;

import java.util.HashMap;
import java.util.Map;


@SpringBootApplication
@EnableDiscoveryClient
public class RoomsApplication{

	public static void main (String[] args){
		SpringApplication.run(RoomsApplication.class, args);
	}

	@Bean
	public KafkaListenerContainerFactory<?> batchFactory (){
		ConcurrentKafkaListenerContainerFactory<Long, RoomModel> factory = new ConcurrentKafkaListenerContainerFactory<>();
		factory.setConsumerFactory(consumerFactory());
		factory.setBatchListener(true);
		factory.setMessageConverter(new BatchMessagingMessageConverter(converter()));
		return factory;
	}

	@Bean
	public KafkaListenerContainerFactory<?> singleFactory (){
		ConcurrentKafkaListenerContainerFactory<Long, RoomModel> factory = new ConcurrentKafkaListenerContainerFactory<>();
		factory.setConsumerFactory(consumerFactory());
		factory.setBatchListener(false);
		factory.setMessageConverter(new StringJsonMessageConverter());
		return factory;
	}

	@Bean
	public ConsumerFactory<Long, RoomModel> consumerFactory (){
		return new DefaultKafkaConsumerFactory<>(consumerConfigs());
	}

	@Bean
	public Map<String, Object> consumerConfigs (){
		Map<String, Object> props = new HashMap<>();
		props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, true);
		props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
		props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
		props.put(ConsumerConfig.GROUP_ID_CONFIG, "json");

		return props;
	}

	@Bean
	public StringJsonMessageConverter converter (){
		return new StringJsonMessageConverter();
	}

}
