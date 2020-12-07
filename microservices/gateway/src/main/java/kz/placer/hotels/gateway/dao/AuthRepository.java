package kz.placer.hotels.gateway.dao;

import kz.placer.hotels.gateway.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthRepository extends JpaRepository<User, Long>{

	User findByUsername (String username);

}
