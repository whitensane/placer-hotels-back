package kz.placer.hotels.gateway.controller;

import kz.placer.hotels.gateway.config.TokenProvider;
import kz.placer.hotels.gateway.dao.AuthRepository;
import kz.placer.hotels.gateway.model.AuthToken;
import kz.placer.hotels.gateway.model.LoginUser;
import kz.placer.hotels.gateway.model.User;
import kz.placer.hotels.gateway.model.UserDto;
import kz.placer.hotels.gateway.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/user")
public class UserController{

	@Autowired
	AuthRepository authRepository;
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private TokenProvider jwtTokenUtil;
	@Autowired
	private UserService userService;


	@GetMapping("/{id}")
	public ResponseEntity<User> getUser (@PathVariable("id") long id){
		Optional<User> user = authRepository.findById(id);

		if (user.isPresent()) {
			return new ResponseEntity<>(user.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	//@Secured({"ROLE_ADMIN", "ROLE_USER"})
	@PreAuthorize("hasRole('ADMIN')")
	@RequestMapping(value = "/users", method = RequestMethod.GET)
	public List<User> listUser (){
		return userService.findAll();
	}

	//@Secured("ROLE_USER")
	@PreAuthorize("hasRole('USER')")
	////@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
	@RequestMapping(value = "/users/{id}", method = RequestMethod.GET)
	public User getOne (@PathVariable(value = "id") Long id){
		return userService.findById(id);
	}


}
