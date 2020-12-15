package kz.placer.hotels.hotels;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import kz.placer.hotels.hotels.models.HotelModel;
import kz.placer.hotels.hotels.models.HotelResponse;
import kz.placer.hotels.hotels.models.RoomFeedback;
import kz.placer.hotels.hotels.models.RoomModel;
import kz.placer.hotels.hotels.service.HotelService;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.File;
import java.io.IOException;

@RestController
@RequestMapping("/hotels")
public class HotelsController{

	private final RestTemplate restTemplate = new RestTemplate();
	private Producer producer = new Producer();
	@Autowired
	private HotelService hotelService;
	@Autowired
	private LoadBalancerClient loadBalancerClient;
	@Autowired
	private FileStorageService fileStorageService;


	private String getServiceUrl (String serviceId){
		ServiceInstance serviceInstance = loadBalancerClient.choose(serviceId);
		return serviceInstance.getUri().toString();
	}

	@GetMapping
	public Iterable<HotelModel> getHotels (){
		return hotelService.findAll();
	}


	@GetMapping("/id")
	@HystrixCommand(fallbackMethod = "getHotelFallback", threadPoolKey = "getUserBooks", threadPoolProperties = {@HystrixProperty(name = "coreSize", value = "100"), @HystrixProperty(name = "maxQueueSize", value = "50"),})
	public ResponseEntity getHotel (@RequestParam int id){

		try {
			String apiCredentials = "rest-client:p@ssword";
			String base64Credentials = new String(Base64.encodeBase64(apiCredentials.getBytes()));

			HttpHeaders headers = new HttpHeaders();
			headers.add("Authorization", "Basic " + base64Credentials);

			HttpEntity<String> entity = new HttpEntity<>(headers);

			ResponseEntity rooms = restTemplate.exchange(getServiceUrl("ROOM") + "rooms/hotelId?id=" + id, HttpMethod.GET, entity, RoomModel[].class);

			ResponseEntity feedbacks = restTemplate.exchange(getServiceUrl("FEEDBACK") + "feedbacks/hotelId?id=" + id, HttpMethod.GET, entity, RoomFeedback[].class);

			HotelResponse hotelResponse = new HotelResponse(rooms.getBody(), feedbacks.getBody());

			producer.Notify(feedbacks.getBody().toString());

			return new ResponseEntity<>(hotelResponse, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@SuppressWarnings("unused")
	private ResponseEntity<?> getHotelFallback (int id){
		return new ResponseEntity<>("Something went wrong", HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<?> createHotel (@ModelAttribute HotelModel request, @ModelAttribute("file") MultipartFile file) throws IOException{

		System.out.println(file);
		String fileName = fileStorageService.storeFile(file);
		String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/resources/").path(fileName).toUriString();
		System.out.println(fileDownloadUri);
		try {
			HotelModel hotel = new HotelModel();
			hotel.setStars(request.getStars());
			hotel.setTitle(request.getTitle());
			hotel.setAddress(request.getAddress());
			hotel.setPhone(request.getPhone());
			hotel.setEmail(request.getEmail());
			hotel.setAvatar(fileName);
			System.out.println(hotel);
			hotelService.save(hotel);
			return new ResponseEntity<>(hotel, HttpStatus.CREATED);

		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}


	@DeleteMapping
	public String deleteHotel (@RequestBody HotelModel request){
		hotelService.delete(request.getId());
		return request.getId() + " successfully deleted";
	}

}
