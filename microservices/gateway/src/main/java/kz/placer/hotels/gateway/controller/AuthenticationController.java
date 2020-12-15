package kz.placer.hotels.gateway.controller;

import kz.placer.hotels.gateway.config.TokenProvider;
import kz.placer.hotels.gateway.model.*;
import kz.placer.hotels.gateway.service.UserService;
import org.hibernate.validator.internal.constraintvalidators.hv.UniqueElementsValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/auth")
public class AuthenticationController{

	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private TokenProvider jwtTokenUtil;
	@Autowired
	private UserService userService;

	@PostMapping("/signin")
	public ResponseEntity<?> login (@RequestBody LoginUser loginUser) throws AuthenticationException{

		final Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginUser.getUsername(), loginUser.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		final String token = jwtTokenUtil.generateToken(authentication);

		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.set("Authorization", "Bearer " + token);

		return ResponseEntity.ok().headers(responseHeaders).body(new AuthToken(token));
	}


	@PostMapping("/signup")
	public ResponseEntity<?> register (@RequestBody UserDto user){
		try {
			userService.save(user);
			return new ResponseEntity<>(user, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/{id}")
	public ResponseEntity<User> getUser (@PathVariable("id") long id){
		User user = userService.findById(id);

		if (user != null) {
			return new ResponseEntity<>(user, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}


	@DeleteMapping("/{id}")
	public ResponseEntity<HttpStatus> deleteUser (@PathVariable("id") long id){
		try {
			userService.delete(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
