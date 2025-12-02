package br.senai.sp.odemarfim.model;

import br.senai.sp.odemarfim.repository.RoleRepository;
import jakarta.persistence.*;
import lombok.Data;


import java.util.ArrayList;
import java.util.List;

@Entity
@DiscriminatorValue(value = "T")
@Data
public class Tutor extends Pessoa{

    public static final String ROLE_TUTOR = "TUTOR";

    public void addRole(RoleRepository roleRepository) {
        Role role = roleRepository.findByName(ROLE_TUTOR);
        this.getUser().getRoles().add(role);
    }

    @OneToMany (mappedBy = "tutorId")
    private List<Pet> pet = new ArrayList<Pet>();
}
