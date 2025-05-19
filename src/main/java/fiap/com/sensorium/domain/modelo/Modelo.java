package fiap.com.sensorium.domain.modelo;

import fiap.com.sensorium.domain.moto.Moto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "modelo")
public class Modelo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_modelo")
    private Long id;

    @Column(name = "nome_modelo", nullable = false, length = 25)
    private String nome;

    @Column(length = 50)
    private String frenagem;

    @Column(name = "sis_partida", length = 100)
    private String sistemaPartida;

    @Column(nullable = false)
    private Integer tanque;

    @Column(name = "tipo_combustivel", nullable = false, length = 50)
    private String combustivel;

    @Column(nullable = false)
    private Integer consumo;

    @OneToMany(mappedBy = "modelo")
    private List<Moto> motos = new ArrayList<>();
}