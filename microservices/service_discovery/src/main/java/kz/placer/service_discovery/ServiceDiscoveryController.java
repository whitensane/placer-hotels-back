package kz.placer.service_discovery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/api")
public class ServiceDiscoveryController{

	@Autowired
	private LoadBalancerClient loadBalancerClient;


	private String getServiceUrl (String serviceId){
		ServiceInstance serviceInstance = loadBalancerClient.choose(serviceId);
		return serviceInstance.getUri().toString();
	}

	@GetMapping("/routes")
	public List<ServiceRoutes> getRoutes (){
		List<ServiceRoutes> routes = new ArrayList<>();

		String serviceNames[];
		serviceNames = new String[] {"ROOM", "AUTH", "BOOKING", "FEEDBACK", "HOTELS", "SERVICE-ORDERING"};

		for (String serviceName : serviceNames) {
			ServiceRoutes serviceRoutes = new ServiceRoutes();
			serviceRoutes.setId(serviceName);
			serviceRoutes.setPath(getServiceUrl(serviceName));
			routes.add(serviceRoutes);
		}
		return routes;
	}


}

class ServiceRoutes{

	private String id;
	private String path;

	public ServiceRoutes (){

	}

	public ServiceRoutes (String id, String path){
		this.id = id;
		this.path = path;
	}

	public String getId (){
		return id;
	}

	public void setId (String id){
		this.id = id;
	}

	public String getPath (){
		return path;
	}

	public void setPath (String path){
		this.path = path;
	}
}
