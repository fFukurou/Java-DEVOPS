package br.com.fiap.otmav.domain.filial;

import br.com.fiap.otmav.domain.endereco.Endereco;
import br.com.fiap.otmav.domain.funcionario.Funcionario;
import br.com.fiap.otmav.domain.patio.Patio;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "filial")
@Getter
@Setter
@NoArgsConstructor
public class Filial {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_filial")
    private Long id;

    @NotBlank
    @Size(max = 150)
    @Column(name = "nome_filial", length = 150)
    private String nome;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_endereco", referencedColumnName = "id_endereco")
    private Endereco endereco;

    @OneToMany(mappedBy = "filial", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Patio> patios;

    @OneToMany(mappedBy = "filial", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Funcionario> funcionarios;


}
