package br.com.fiap.otmav.domain.dados;

import br.com.fiap.otmav.domain.funcionario.Funcionario;
import br.com.fiap.otmav.domain.motorista.Motorista;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

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
    @Column(name = "email", length = 255, nullable = true, unique = true)
    private String email;

    @Size(max = 200)
    @Column(name = "senha", length = 200)
    private String senha;

    @NotBlank
    @Size(max = 150)
    @Column(name = "nome", length = 150, nullable = false)
    private String nome;

    @OneToMany(mappedBy = "dados", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Motorista> motoristas;

    @OneToMany(mappedBy = "dados", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Funcionario> funcionarios;
}
