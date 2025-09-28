package br.com.fiap.otmav.domain.moto;

import br.com.fiap.otmav.domain.motorista.Motorista;
import br.com.fiap.otmav.domain.modelo.Modelo;
import br.com.fiap.otmav.domain.setor.Setor;
import br.com.fiap.otmav.domain.situacao.Situacao;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "moto")
@Getter
@Setter
@NoArgsConstructor
public class Moto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_moto")
    private Long id;

    @Size(max = 100)
    @Column(name = "placa", length = 100)
    private String placa;

    @Size(max = 17)
    @Column(name = "chassi", length = 17)
    private String chassi;

    @NotBlank
    @Column(name = "condicao", length = 255, nullable = false)
    private String condicao;

    @Column(name = "localizacao_moto", length = 4000, nullable = false)
    private String localizacaoWkt;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_motorista", referencedColumnName = "id_motorista")
    private Motorista motorista;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_modelo", referencedColumnName = "id_modelo")
    private Modelo modelo;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_setor", referencedColumnName = "id_setor")
    private Setor setor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_situacao", referencedColumnName = "id_situacao")
    private Situacao situacao;
}
