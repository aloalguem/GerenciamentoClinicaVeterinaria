package br.senai.sp.odemarfim.model;

import br.senai.sp.odemarfim.repository.RoleRepository;
import jakarta.persistence.*;


@Entity
@DiscriminatorValue(value = "A")
public class Admin extends Pessoa {

    public static final String ROLE_ADMIN = "ADMIN";

    public void addRole(RoleRepository roleRepository) {
        Role role = roleRepository.findByName(ROLE_ADMIN);
        this.getUser().getRoles().add(role);
    }
}

