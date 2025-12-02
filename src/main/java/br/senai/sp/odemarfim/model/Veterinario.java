package br.senai.sp.odemarfim.model;

import br.senai.sp.odemarfim.repository.RoleRepository;
import jakarta.persistence.CascadeType;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Entity
@DiscriminatorValue(value = "V")
@Data
public class Veterinario extends Pessoa {

    @NotEmpty
    private String crmv;

    @OneToMany(mappedBy = "veterinario", cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @ToString.Exclude // evita StackOverflow
    private List<Procedimento> procedimentos = new ArrayList<>();

    public static final String ROLE_VETERINARIO = "VETERINARIO";

    public void addRole(RoleRepository roleRepository) {
        Role role = roleRepository.findByName(ROLE_VETERINARIO);
        this.getUser().getRoles().add(role);
    }
}
