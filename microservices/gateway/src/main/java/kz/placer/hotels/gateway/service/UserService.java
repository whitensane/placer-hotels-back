package kz.placer.hotels.gateway.service;


import kz.placer.hotels.gateway.model.User;
import kz.placer.hotels.gateway.model.UserDto;

import java.util.List;

public interface UserService{

	User save (UserDto user);

	List<User> findAll ();

	void delete (long id);

	User findOne (String username);

	User findById (Long id);

}
