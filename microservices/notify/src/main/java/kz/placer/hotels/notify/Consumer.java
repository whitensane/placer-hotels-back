package kz.placer.hotels.notify;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class Consumer{


	@KafkaListener(topics = "quickstart-events", groupId = "group_id")
	public void consume (String bookRequest) throws IOException{
		System.out.println(String.format("#### -> Notify user by email: -> %s", "User " + bookRequest));
	}
}