package br.senai.sp.odemarfim.repository;

import br.senai.sp.odemarfim.model.User;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    List<User> findByEmailContaining(String email);

    User findByEmail(String email);
}
