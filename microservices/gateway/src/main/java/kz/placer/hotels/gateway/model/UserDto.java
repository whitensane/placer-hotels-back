package kz.placer.hotels.gateway.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NonNull;

import java.util.Set;

@Data
public class UserDto{

	@NonNull
	private String username;
	@NonNull
	private String password;
	@NonNull
	private String name;
	@NonNull
	private String surname;
	@NonNull
	private String phone;
	@NonNull
	private String mail;
	@NonNull
	private int age;
	@JsonIgnore
	Set<Role> roles;

}
