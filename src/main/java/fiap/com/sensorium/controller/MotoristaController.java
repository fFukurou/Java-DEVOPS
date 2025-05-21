package fiap.com.sensorium.controller;

import fiap.com.sensorium.domain.moto.Moto;
import fiap.com.sensorium.domain.moto.ReadMotoDto;
import fiap.com.sensorium.domain.motorista.Motorista;
import fiap.com.sensorium.domain.motorista.MotoristaRepository;
import fiap.com.sensorium.domain.motorista.ReadMotoristaDto;
import fiap.com.sensorium.infra.exception.EntityNotFoundException;
import fiap.com.sensorium.service.MotoristaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;


@RestController
@RequestMapping("motoristas")
@Tag(name = "Motorista", description = "Gerenciamento de motoristas")

public class MotoristaController {

    @Autowired
    private MotoristaRepository motoristaRepository;

    @Autowired
    private MotoristaService motoristaService;


    @Operation(
            summary = "Listar motoristas",
            description = "Retorna motoristas paginados, filtrados por plano se fornecido"
    )
    @ApiResponse(responseCode = "200", description = "Lista de motoristas retornada com sucesso")
    @GetMapping
    public ResponseEntity<Page<ReadMotoristaDto>> listAll(
            @Parameter(description = "Plano para filtro (opcional)", example = "MottuCompleto")
            @RequestParam(required = false) String plano,

            @Parameter(hidden = true)
            @PageableDefault(size = 10, sort = "id") Pageable pageable
    ) {
        return ResponseEntity.ok(
                motoristaService.filterQuery(plano, pageable)
                        .map(ReadMotoristaDto::new)
        );
    }

    @Operation(summary = "Buscar motorista por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Motorista encontrado"),
            @ApiResponse(responseCode = "404", description = "Motorista não encontrado")
    })
    @GetMapping("/{id}")
    public ReadMotoristaDto getById(
            @Parameter(description = "ID do motorista", example = "203")
            @PathVariable Long id
    ) {
        return motoristaService.findById(id)
                .map(ReadMotoristaDto::new)
                .orElseThrow(() -> new EntityNotFoundException("Motorista não encontrado com ID: " + id));
    }

    @Operation(summary = "Limpar cache", description = "Limpa o cache de motoristas")
    @ApiResponse(responseCode = "204", description = "Cache limpo com sucesso")
    @PostMapping("/clear-cache")
    public ResponseEntity<Void> clearCache() {
        motoristaService.clearCache();
        return ResponseEntity.noContent().build();
    }
}
