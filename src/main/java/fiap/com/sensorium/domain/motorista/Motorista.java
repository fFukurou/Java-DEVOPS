package fiap.com.sensorium.domain.motorista;

import fiap.com.sensorium.domain.dados.Dados;
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
@Table(name = "motorista")
public class Motorista {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_motorista")
    private Long id;

    @Column(nullable = false, length = 20)
    private String plano;

    @OneToOne
    @JoinColumn(name = "id_dados", nullable = false)
    private Dados dados;

    @OneToMany(mappedBy = "motorista")
    private List<Moto> motos = new ArrayList<>();
}