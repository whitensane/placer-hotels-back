package kz.placer.hotels.auth.repository;
import java.util.List;

import kz.placer.hotels.auth.models.AuthModel;
import org.springframework.data.jpa.repository.JpaRepository;


public interface AuthRepository extends JpaRepository<AuthModel, Long> {
    List<AuthModel> findByNameContaining(String name);
    List<AuthModel> findByUsernameAndPasswordEquals(String username, String password);
}