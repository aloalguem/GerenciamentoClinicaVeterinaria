package br.senai.sp.odemarfim.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;

@Entity
@Data
public class Feedback {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O nome é obrigatório.")
    @Size(min = 2, max = 60, message = "O nome deve ter entre 2 e 60 caracteres.")
    @Column(nullable = false, length = 60)
    private String name;

    @NotBlank(message = "O e-mail é obrigatório.")
    @Email(message = "Informe um e-mail válido.")
    @Column(nullable = false, length = 120)
    private String email;

    @NotNull(message = "A avaliação é obrigatória.")
    @Min(value = 1, message = "A avaliação mínima é 1.")
    @Max(value = 5, message = "A avaliação máxima é 5.")
    @Column(nullable = false)
    private Integer rating;

    @NotBlank(message = "O comentário é obrigatório.")
    @Size(min = 10, message = "O comentário deve ter pelo menos 10 caracteres.")
    @Column(nullable = false, columnDefinition = "TEXT")
    private String message;
}
