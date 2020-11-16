package kz.placer.hotels.rooms;

import kz.placer.hotels.rooms.models.RoomModel;
import kz.placer.hotels.rooms.models.RoomResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/rooms")
public class RoomController{

	@Autowired
	private RoomRepository roomRepository;

	@Autowired
	private LoadBalancerClient loadBalancerClient;

	private RestTemplate restTemplate = new RestTemplate();

	private String getServiceUrl (String serviceId){
		ServiceInstance serviceInstance = loadBalancerClient.choose(serviceId);
		return serviceInstance.getUri().toString();
	}

	@GetMapping("/id")
	public ResponseEntity<?> getRoom (@RequestParam int id) {
		try {
			ResponseEntity feedbacks = restTemplate.getForEntity(getServiceUrl("FEEDBACK") + "api/feedback/roomId?roomId=" + id, String.class);
			RoomResponse roomResponse = new RoomResponse(roomRepository.findById(id),  feedbacks.getBody());
			return new ResponseEntity<>(roomResponse, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping
	public ResponseEntity<?> getAllRooms (){
		try {
			return new ResponseEntity<>(roomRepository.findAll(), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/hotelId")
	public ResponseEntity<?> getRoomsOfHotel (@RequestParam int id) {
		try {
			return new ResponseEntity<>(roomRepository.findAllByHotelId(id), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}


	@DeleteMapping
	public ResponseEntity<?> deleteRoom (@RequestBody RoomModel request){
		try {
			roomRepository.delete(request);
			return new ResponseEntity<>(true, HttpStatus.ACCEPTED);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping
	public ResponseEntity<?> createRoom (@Valid @RequestBody RoomModel request){
		try {
			RoomModel room = new RoomModel();
			room.setHotelId(request.getHotelId());
			room.setNumber(request.getNumber());
			room.setFloor(request.getFloor());
			room.setSleepingPlaces(request.getSleepingPlaces());
			room.setDescription(request.getDescription());
			room.setRooms(request.getRooms());
			roomRepository.save(room);
			return new ResponseEntity<>(room, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
