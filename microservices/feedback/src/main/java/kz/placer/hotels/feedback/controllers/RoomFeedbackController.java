package kz.placer.hotels.feedback.controllers;

import kz.placer.hotels.feedback.models.RoomFeedback;
import kz.placer.hotels.feedback.repository.RoomFeedbackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/feedback")
public class RoomFeedbackController{

	@Autowired
	private RoomFeedbackRepository roomFeedbackRepository;


	@GetMapping("/id")
	public ResponseEntity<?> getFeedback (@RequestParam int id){
		try {
			return new ResponseEntity<>(roomFeedbackRepository.findById(id), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping
	public ResponseEntity<?> getFeedbacks (){
		try {
			return new ResponseEntity<>(roomFeedbackRepository.findAll(), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/hotelId")
	public ResponseEntity<?> getFeedbackOfHotel (@RequestParam int id){
		try {
			return new ResponseEntity<>(roomFeedbackRepository.findAllByHotelId(id), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/roomId")
	public ResponseEntity<?> getFeedbackOfRoom (@RequestParam int roomId){
		try {
			return new ResponseEntity<>(roomFeedbackRepository.findAllByRoomId(roomId), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}


	@DeleteMapping
	public ResponseEntity<?> deleteFeedback (@RequestBody RoomFeedback request){
		try {
			roomFeedbackRepository.delete(request);
			return new ResponseEntity<>(true, HttpStatus.ACCEPTED);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("/room")
	public ResponseEntity<?> createFeedback (@Valid @RequestBody RoomFeedback request){
		try {
			RoomFeedback room = new RoomFeedback();

			room.setHotelId(request.getHotelId());
			room.setRoomId(request.getRoomId());
			room.setTitle(request.getTitle());
			room.setDescription(request.getDescription());
			room.setRating(request.getRating());
			room.setUserId(request.getUserId());

			roomFeedbackRepository.save(room);

			return new ResponseEntity<>(room, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
