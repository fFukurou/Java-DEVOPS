package br.com.fiap.otmav.domain.regiao;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
}
