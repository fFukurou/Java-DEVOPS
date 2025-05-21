package fiap.com.sensorium.controller;

import fiap.com.sensorium.domain.situacao.ReadSituacaoDto;
import fiap.com.sensorium.domain.situacao.Situacao;
import fiap.com.sensorium.domain.situacao.SituacaoRepository;
import fiap.com.sensorium.service.SituacaoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("situacao")
@Tag(name = "Situação", description = "Operações relacionadas a situações de motos")
public class SituacaoController {


    @Autowired
    private SituacaoRepository repository;

    @Autowired
    private SituacaoService situacaoService;

    @Operation(
            summary = "Listar situações",
            description = "Retorna uma lista paginada de situações, podendo filtrar por status"
    )
    @ApiResponse(responseCode = "200", description = "Lista de situações retornada com sucesso")
    @GetMapping
    public ResponseEntity<Page<ReadSituacaoDto>> listByStatus(
            @Parameter(description = "Status para filtro (opcional)", example = "Ativo")
            @RequestParam(required = false) String status,

            @Parameter(hidden = true) // Hide Pageable from Swagger (it adds its own)
            @PageableDefault(size = 10, sort = "status") Pageable pageable
    ) {
        Page<Situacao> page = (status != null)
                ? situacaoService.findByStatusIgnoreCase(status, pageable)
                : situacaoService.findAllIgnoreCase(pageable);

        return ResponseEntity.ok(page.map(ReadSituacaoDto::new));
    }

    @Operation(summary = "Limpar cache", description = "Limpa o cache de Situacoes")
    @ApiResponse(responseCode = "204", description = "Cache limpo com sucesso")
    @PostMapping("/clear-cache")
    public ResponseEntity<Void> clearCache() {
        situacaoService.clearCache();
        return ResponseEntity.noContent().build();
    }
}

