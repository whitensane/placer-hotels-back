package kz.placer.hotels.rooms;

import kz.placer.hotels.rooms.models.RoomModel;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class Consumer{


	@KafkaListener(topics = "book_requests", groupId = "group_id")
	public void consume (RoomModel bookRequest) throws IOException{
		System.out.println(String.format("#### -> Notify user by email: -> %s", "User " + bookRequest.getFloor() + " purchased book " + bookRequest.getHotelId()));
	}
}