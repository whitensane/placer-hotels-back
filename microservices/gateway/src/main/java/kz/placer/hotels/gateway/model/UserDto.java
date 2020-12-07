package kz.placer.hotels.gateway.model;

import lombok.Data;
import lombok.NonNull;

import javax.persistence.Column;

@Data
public class UserDto{

	@NonNull
	private String username;
	@NonNull
	private String password;
	@NonNull
	private int age;
	@NonNull
	private int salary;

}
