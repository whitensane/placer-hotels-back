package kz.placer.hotels.hotels;

import kz.placer.hotels.hotels.models.BookRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class Producer {

	private static final String TOPIC = "book_requests";

	@Autowired
	private KafkaTemplate<String, BookRequest> myMessageKafkaTemplate;

	public String bookRequestNotify(BookRequest bookRequest) {
		System.out.println(String.format("#### -> Producing book request to notification service -> %s", bookRequest));
		this.myMessageKafkaTemplate.send(TOPIC, bookRequest);
		System.out.println(String.format("#### -> Producing book request to notification service -> %s", bookRequest));
		return "Successfully";
	}
}
//
//import com.fasterxml.jackson.databind.JsonSerializer;
//import kz.placer.hotels.hotels.models.HotelModel;
//import kz.placer.hotels.hotels.models.RoomModel;
//import org.apache.kafka.clients.producer.ProducerConfig;
//import org.apache.kafka.common.serialization.LongSerializer;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.kafka.core.DefaultKafkaProducerFactory;
//import org.springframework.kafka.core.KafkaTemplate;
//import org.springframework.kafka.core.ProducerFactory;
//import org.springframework.kafka.support.converter.StringJsonMessageConverter;
//import org.springframework.stereotype.Service;
//
//import java.util.HashMap;
//import java.util.Map;
//
//@Service
//public class Producer {
//
//	private static final String TOPIC = "book_requests";
//
//	@Autowired
//	private KafkaTemplate<String, HotelModel> kafkaTemplate;
//
//	public String bookRequestNotify(HotelModel bookRequest) {
//		System.out.println(String.format("#### -> Producing book request to notification service -> %s", bookRequest));
//		this.kafkaTemplate.send(TOPIC, bookRequest);
//		return "Successfully";
//	}
//}
//
//@Configuration
//class KafkaProducerConfig {
//
//	@Bean
//	public Map<String, Object> producerConfigs() {
//		Map<String, Object> props = new HashMap<>();
//		props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
//		props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, LongSerializer.class);
//		props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
//		return props;
//	}
//
//	@Bean
//	public ProducerFactory<Long, RoomModel> producerStarshipFactory() {
//		return new DefaultKafkaProducerFactory<>(producerConfigs());
//	}
//
//	@Bean
//	public KafkaTemplate<String, RoomModel> kafkaTemplate() {
//		KafkaTemplate<String, RoomModel> template = new KafkaTemplate(producerStarshipFactory());
//		template.setMessageConverter(new StringJsonMessageConverter());
//		return template;
//	}
//}