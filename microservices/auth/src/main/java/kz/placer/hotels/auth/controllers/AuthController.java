package kz.placer.hotels.auth.controllers;

import kz.placer.hotels.auth.models.AuthModel;
import kz.placer.hotels.auth.repository.AuthRepository;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api")
public class AuthController{

	@Autowired
	AuthRepository authRepository;

	@GetMapping("/auth")
	public ResponseEntity<List<AuthModel>> getAllUsers (@RequestParam(required = false) String name){
		try {
			List<AuthModel> users = new ArrayList<>();

			if (name == null) authRepository.findAll().forEach(users::add);
			else authRepository.findByNameContaining(name).forEach(users::add);

			if (users.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}

			return new ResponseEntity<>(users, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/auth/{id}")
	public ResponseEntity<AuthModel> getUser (@PathVariable("id") long id){
		Optional<AuthModel> user = authRepository.findById(id);

		if (user.isPresent()) {
			return new ResponseEntity<>(user.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping("/auth")
	public ResponseEntity<?> createUser (@Valid @RequestBody AuthModel tutorial){
		try {
			AuthModel user = authRepository.save(new AuthModel(tutorial.getName(), tutorial.getLastname(), tutorial.getUsername(), tutorial.getPassword(), tutorial.getEmail(), tutorial.getPhone()));
			return new ResponseEntity<>(user, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("/auth/login")
	public @ResponseBody
	Object method (HttpServletRequest request) throws JSONException{
		System.out.println(request.getParameterMap());
		JSONObject json = new JSONObject();
		json.put("example", "example message");

		return json;
//		try {
//			System.out.println(tutorial);
//			List<AuthModel> user = authRepository.findByUsernameAndPasswordEquals(tutorial.getUsername(), tutorial.getPassword());

//			AuthModel user = authRepository.save(new AuthModel(tutorial.getName(), tutorial.getLastname(), tutorial.getUsername(), tutorial.getPassword(), tutorial.getEmail(), tutorial.getPhone()));
//			return new ResponseEntity<>("user", HttpStatus.CREATED);
//		} catch (Exception e) {
//			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
//		}
	}

//
//    @PutMapping("/auth/{id}")
//    public ResponseEntity<AuthModel> updateUser(@PathVariable("id") long id, @RequestBody AuthModel tutorial) {
//        Optional<AuthModel> user = authRepository.findById(id);
//
//        if (user.isPresent()) {
//            AuthModel _tutorial = user.get();
//            _tutorial.setName(tutorial.getName());
//            _tutorial.setDescription(tutorial.getDescription());
//            _tutorial.setLogged(tutorial.isLogged());
//            return new ResponseEntity<>(authRepository.save(_tutorial), HttpStatus.OK);
//        } else {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//    }

	@DeleteMapping("/auth/{id}")
	public ResponseEntity<HttpStatus> deleteUser (@PathVariable("id") long id){
		try {
			authRepository.deleteById(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/auth")
	public ResponseEntity<HttpStatus> deleteAllUsers (){
		try {
			authRepository.deleteAll();
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
//
//	@GetMapping("/auth/logged_in")
//	public ResponseEntity<List<AuthModel>> getLoggedIn (){
//		try {
//			List<AuthModel> users = authRepository.findByLogged(true);
//
//			if (users.isEmpty()) {
//				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//			}
//			return new ResponseEntity<>(users, HttpStatus.OK);
//		} catch (Exception e) {
//			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//		}
//	}

//	@GetMapping("/auth/logged_out")
//	public ResponseEntity<List<AuthModel>> getLoggedOut (){
//		try {
//			List<AuthModel> users = authRepository.findByLogged(false);
//
//			if (users.isEmpty()) {
//				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//			}
//			return new ResponseEntity<>(users, HttpStatus.OK);
//		} catch (Exception e) {
//			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//		}
//	}

}