package br.senai.sp.odemarfim.repository;

import br.senai.sp.odemarfim.model.Tutor;
import br.senai.sp.odemarfim.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TutorRepository extends JpaRepository<Tutor, Long> {
    List<Tutor> findByEmailContaining(String email);

    Tutor findByUser(User user);
}
