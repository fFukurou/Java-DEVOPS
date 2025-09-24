package br.com.fiap.otmav.domain.motorista;

import br.com.fiap.otmav.domain.dados.Dados;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "motorista")
@Getter
@Setter
@NoArgsConstructor
public class Motorista {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_motorista")
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "plano", length = 50, nullable = false)
    private MotoristaPlano plano;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_dados", referencedColumnName = "id_dados", nullable = false)
    private Dados dados;
}
