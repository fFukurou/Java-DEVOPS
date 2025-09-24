package br.com.fiap.otmav.domain.modelo;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "modelo")
@Getter
@Setter
@NoArgsConstructor
public class Modelo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_modelo")
    private Long id;

    @NotBlank
    @Size(max = 25)
    @Column(name = "nome_modelo", length = 25, nullable = false)
    private String nomeModelo;

    @Size(max = 50)
    @Column(name = "frenagem", length = 50)
    private String frenagem;

    @Size(max = 100)
    @Column(name = "sis_partida", length = 100)
    private String sisPartida;

    @NotNull
    @Column(name = "tanque")
    private Integer tanque;

    @NotBlank
    @Size(max = 50)
    @Column(name = "tipo_combustivel", length = 50, nullable = false)
    private String tipoCombustivel;

    @NotNull
    @Column(name = "consumo")
    private Integer consumo;
}
