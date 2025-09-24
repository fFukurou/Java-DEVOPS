package br.com.fiap.otmav.domain.dados;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "dados")
@Getter
@Setter
@NoArgsConstructor
public class Dados {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_dados")
    private Long id;

    @NotBlank
    @Size(min = 11, max = 11)
    @Column(name = "cpf", length = 11, nullable = false, unique = true)
    private String cpf;

    @Size(max = 13)
    @Column(name = "telefone", length = 13)
    private String telefone;

    @Size(max = 255)
    @Column(name = "email", length = 255)
    private String email;

    @Size(max = 200)
    @Column(name = "senha", length = 200)
    private String senha;

    @NotBlank
    @Size(max = 150)
    @Column(name = "nome", length = 150, nullable = false)
    private String nome;
}
