package kz.placer.hotels.feedback.repository;

import kz.placer.hotels.feedback.models.RoomFeedback;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface RoomFeedbackRepository extends JpaRepository<RoomFeedback, Integer>{

	List<RoomFeedback> findAllByHotelId (int hotelId);

	List<RoomFeedback> findAllByRoomId (int roomId);

}
