package br.senai.sp.odemarfim.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Procedimento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private Double valor;


    @ManyToOne
    @JoinColumn(name = "veterinario_id") // Optional, but recommended for clarity
    private Veterinario veterinario;

    @OneToMany(mappedBy = "procedimento", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private List<Agendamento> agendamentos = new ArrayList<>();
}