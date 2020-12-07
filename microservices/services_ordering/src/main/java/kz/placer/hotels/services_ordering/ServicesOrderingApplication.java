package kz.placer.hotels.services_ordering;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class ServicesOrderingApplication{

	public static void main (String[] args){
		SpringApplication.run(ServicesOrderingApplication.class, args);
	}

}
