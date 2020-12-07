package kz.placer.hotels.booking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.List;
import java.util.Map;


@SpringBootApplication
@EnableEurekaClient
public class BookingApplication{

	@Autowired
	private LoadBalancerClient loadBalancerClient;


	public static void main (String[] args){
		SpringApplication.run(BookingApplication.class, args);
	}

	@GetMapping("/")
	private String print (){
		return "HI";
	}

	private String getBaseUrl (){
		ServiceInstance serviceInstance = loadBalancerClient.choose("room-client");
		return serviceInstance.getUri().toString();
	}

}
