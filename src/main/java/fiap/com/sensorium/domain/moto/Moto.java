package fiap.com.sensorium.domain.moto;

import fiap.com.sensorium.domain.modelo.Modelo;
import fiap.com.sensorium.domain.motorista.Motorista;
import fiap.com.sensorium.domain.setor.Setor;
import fiap.com.sensorium.domain.situacao.Situacao;
import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.checkerframework.common.aliasing.qual.Unique;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "moto")
public class Moto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_moto")
    private Long id;

    @Column(length = 7, unique = true)
    @Pattern(
            regexp = "^(?:[A-Z]{3}[0-9][A-Z][0-9]{2}|[A-Z]{3}[0-9]{4})$",
            message = "Placa deve seguir padrão MERCOSUL (ABC1D23) ou antigo (AAA1111)"
    )
    private String placa;

    @Column(length = 17)
    private String chassi;

    @Column(nullable = false, length = 8)
    private String condicao;

    @Column(nullable = false, length = 5)
    private String latitude;

    @Column(nullable = false, length = 5)
    private String longitude;

    @ManyToOne
    @JoinColumn(name = "id_motorista")
    private Motorista motorista;

    @ManyToOne
    @JoinColumn(name = "id_modelo", nullable = false)
    private Modelo modelo;

    @ManyToOne
    @JoinColumn(name = "id_setor", nullable = false)
    private Setor setor;

    @ManyToOne
    @JoinColumn(name = "id_situacao", nullable = false)
    private Situacao situacao;
}