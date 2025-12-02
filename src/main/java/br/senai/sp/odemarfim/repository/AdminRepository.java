package br.senai.sp.odemarfim.repository;

import br.senai.sp.odemarfim.model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AdminRepository extends JpaRepository<Admin, Long> {

    List<Admin> findByNomeContainingIgnoreCase(String nome);

}