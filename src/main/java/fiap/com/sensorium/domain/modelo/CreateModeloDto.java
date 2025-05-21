package fiap.com.sensorium.domain.modelo;

import jakarta.validation.constraints.*;

public record CreateModeloDto(
        @NotBlank @Size(max = 25) String nome,
        @Size(max = 50) String frenagem,
        @Size(max = 100) String sistemaPartida,
        @NotNull @Positive Integer tanque,
        @NotBlank @Size(max = 50) String combustivel,
        @NotNull @Positive Integer consumo
) {
}
