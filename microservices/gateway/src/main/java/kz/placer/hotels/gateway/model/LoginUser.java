package kz.placer.hotels.gateway.model;

import lombok.Data;
import lombok.NonNull;

@Data
public class LoginUser{

	@NonNull
	private String username;
	@NonNull
	private String password;

}
