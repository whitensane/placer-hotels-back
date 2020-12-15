package kz.placer.hotels.gateway.service.impl;

import kz.placer.hotels.gateway.dao.AuthRepository;
import kz.placer.hotels.gateway.model.User;
import kz.placer.hotels.gateway.model.UserDto;
import kz.placer.hotels.gateway.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Service(value = "userService")
public class UserServiceImpl implements UserDetailsService, UserService{

	@Autowired
	private AuthRepository userDao;

	@Autowired
	private BCryptPasswordEncoder bcryptEncoder;

	public UserDetails loadUserByUsername (String username) throws UsernameNotFoundException{
		User user = userDao.findByUsername(username);
		if (user == null) {
			throw new UsernameNotFoundException("Invalid username or password.");
		}
		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), getAuthority(user));
	}

	private Set<SimpleGrantedAuthority> getAuthority (User user){
		Set<SimpleGrantedAuthority> authorities = new HashSet<>();
		user.getRoles().forEach(role -> {
			authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getName()));
		});
		return authorities;
	}

	public List<User> findAll (){
		List<User> list = new ArrayList<>();
		userDao.findAll().iterator().forEachRemaining(list::add);
		return list;
	}

	@Override
	public void delete (long id){
		userDao.deleteById(id);
	}

	@Override
	public User findOne (String username){
		return userDao.findByUsername(username);
	}

	@Override
	public User findById (Long id){
		return userDao.findById(id).get();
	}

	@Override
	public User save (UserDto user){
		User newUser = new User();
		newUser.setUsername(user.getUsername());
		newUser.setPassword(bcryptEncoder.encode(user.getPassword()));
		newUser.setAge(user.getAge());
		newUser.setName(user.getName());
		newUser.setSurname(user.getSurname());
		newUser.setMail(user.getMail());
		newUser.setPhone(user.getPhone());
		return userDao.save(newUser);
	}
}
