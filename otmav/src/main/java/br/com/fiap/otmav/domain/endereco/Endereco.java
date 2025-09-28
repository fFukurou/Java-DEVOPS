package br.com.fiap.otmav.domain.endereco;

import br.com.fiap.otmav.domain.filial.Filial;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "endereco")
@Getter
@Setter
@NoArgsConstructor
public class Endereco {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_endereco")
    private Long id;

    @NotNull
    @Column(name = "numero", nullable = false)
    private Integer numero;

    @NotBlank
    @Size(max = 30)
    @Column(name = "estado", length = 30, nullable = false)
    private String estado;

    @NotBlank
    @Size(max = 50)
    @Column(name = "codigo_pais", length = 50, nullable = false)
    private String codigoPais;

    @NotBlank
    @Size(max = 50)
    @Column(name = "codigo_postal", length = 50, nullable = false)
    private String codigoPostal;

    @Size(max = 150)
    @Column(name = "complemento", length = 150)
    private String complemento;

    @NotBlank
    @Size(max = 100)
    @Column(name = "rua", length = 100, nullable = false)
    private String rua;

    @OneToMany(mappedBy = "endereco")
    private List<Filial> filiais;
}
