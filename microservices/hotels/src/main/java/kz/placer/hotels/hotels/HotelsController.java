package kz.placer.hotels.hotels;

import kz.placer.hotels.hotels.models.HotelModel;
import kz.placer.hotels.hotels.models.HotelResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/api")
public class HotelsController{

	@Autowired
	private HotelRepository hotelRepository;

	@Autowired
	private LoadBalancerClient loadBalancerClient;

	private RestTemplate restTemplate = new RestTemplate();

	private String getServiceUrl (String serviceId){
		ServiceInstance serviceInstance = loadBalancerClient.choose(serviceId);
		return serviceInstance.getUri().toString();
	}

	@GetMapping
	public Iterable<HotelModel> getHotels (){
		return hotelRepository.findAll();
	}

	@GetMapping("/id")
	public ResponseEntity getHotel (@RequestParam int id){

		try {
			ResponseEntity rooms = restTemplate.getForEntity(getServiceUrl("ROOM") + "api/rooms/hotelId?id=" + id, String.class);
			ResponseEntity feedbacks = restTemplate.getForEntity(getServiceUrl("FEEDBACK") + "api/feedback/hotelId?id=" + id, String.class);
			HotelResponse hotelResponse = new HotelResponse(rooms.getBody(), feedbacks.getBody());
			return new ResponseEntity<>(hotelResponse, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@PostMapping
	public ResponseEntity<?> createHotel (@RequestBody HotelModel request){
		try {
			HotelModel hotel = new HotelModel();
			hotel.setStars(request.getStars());
			hotel.setTitle(request.getTitle());
			hotel.setAddress(request.getAddress());
			hotel.setPhone(request.getPhone());
			hotel.setEmail(request.getEmail());
			hotelRepository.save(hotel);
			return new ResponseEntity<>(hotel, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}


	@DeleteMapping
	public String deleteHotel (@RequestBody HotelModel request){
		hotelRepository.delete(request);
		return request.getId() + " successfully deleted";
	}

}
