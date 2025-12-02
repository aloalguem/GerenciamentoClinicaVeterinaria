package br.senai.sp.odemarfim.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Pet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    private String nome;

    @NotEmpty
    private String raca;

    @NotEmpty
    private String especie;

    @Column(name = "data_nascimento")
    private java.time.LocalDate dataNascimento; // LocalDate

    @NotEmpty
    private String porte;

    private Long tutorId;

    private String doenca;

    @NotEmpty
    private String peso;

    @OneToMany(mappedBy = "pet")
    @ToString.Exclude
    private List<Agendamento> agendamentos = new ArrayList<>();
}
