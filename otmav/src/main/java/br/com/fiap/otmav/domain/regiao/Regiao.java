package br.com.fiap.otmav.domain.regiao;

import br.com.fiap.otmav.domain.patio.Patio;
import br.com.fiap.otmav.domain.setor.Setor;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "regiao")
@Getter
@Setter
@NoArgsConstructor
public class Regiao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_regiao")
    private Long id;

    // NOTE: DB column in the provided schema is MDSYS.SDO_GEOMETRY.
    // For simplicity we store a textual WKT representation in the JPA layer.
    // If you must keep SDO_GEOMETRY, we'll need native queries or custom mapping.
    @Column(name = "localizacao", length = 255)
    private String localizacaoWkt;

    @NotNull
    @Column(name = "area", nullable = false)
    private Double area;

    @OneToMany(mappedBy = "regiao", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Patio> patios;

    @OneToMany(mappedBy = "regiao", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Setor> setores;
}
