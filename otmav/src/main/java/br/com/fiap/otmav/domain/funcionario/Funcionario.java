package br.com.fiap.otmav.domain.funcionario;

import br.com.fiap.otmav.domain.dados.Dados;
import br.com.fiap.otmav.domain.filial.Filial;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "funcionario")
@Getter
@Setter
@NoArgsConstructor
public class Funcionario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_func")
    private Long id;

    @NotBlank
    @Size(max = 100)
    @Column(name = "cargo", length = 100, nullable = false)
    private String cargo;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_dados", referencedColumnName = "id_dados", nullable = false)
    private Dados dados;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_filial", referencedColumnName = "id_filial")
    private Filial filial;
}
