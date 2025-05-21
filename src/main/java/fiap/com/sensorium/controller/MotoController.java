package fiap.com.sensorium.controller;

import fiap.com.sensorium.domain.modelo.ModeloRepository;
import fiap.com.sensorium.domain.moto.*;
import fiap.com.sensorium.domain.motorista.MotoristaRepository;
import fiap.com.sensorium.domain.setor.SetorRepository;
import fiap.com.sensorium.domain.situacao.SituacaoRepository;
import fiap.com.sensorium.service.MotoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("motos")
@Tag(name = "Moto", description = "CRUD completo de motos")
public class MotoController {

    @Autowired
    private MotoRepository motoRepository;
    @Autowired
    private MotoristaRepository motoristaRepository;

    @Autowired
    private ModeloRepository modeloRepository;

    @Autowired
    private SetorRepository setorRepository;

    @Autowired
    private SituacaoRepository situacaoRepository;

    @Autowired
    private MotoService motoService;


    // GET
    @Operation(
            summary = "Listar motos",
            description = "Retorna motos paginadas com filtros opcionais"
    )
    @ApiResponse(responseCode = "200", description = "Lista de motos retornada com sucesso")
    @GetMapping
    public ResponseEntity<Page<ReadMotoDto>> listAll(
            @Parameter(description = "Condição da moto (opcional)", example = "ATIVA")
            @RequestParam(required = false) String condicao,

            @Parameter(description = "ID do setor (opcional)", example = "4")
            @RequestParam(required = false) Long setorId,

            @Parameter(hidden = true)
            @PageableDefault(size = 10, sort = "placa") Pageable pageable
    ) {
        return ResponseEntity.ok(
                motoService.filterQuery(condicao, setorId, pageable)
                        .map(ReadMotoDto::new)
        );
    }

    // GET BY ID
    @Operation(summary = "Buscar moto por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Moto encontrada"),
            @ApiResponse(responseCode = "404", description = "Moto não encontrada")
    })
    @GetMapping("/{id}")
    public ReadMotoDto getById(
            @Parameter(description = "ID da moto", example = "1004")
            @PathVariable Long id
    ) {
        return new ReadMotoDto(motoService.findByIdOrThrow(id));
    }

    // POST
    @Operation(summary = "Cadastrar nova moto")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Moto criada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    @PostMapping
    @Transactional
    public ResponseEntity<ReadMotoDto> create(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Dados da nova moto",
                    required = true
            )
            @RequestBody @Valid CreateMotoDto dto
    ) {
        Moto moto = new Moto();
        motoService.updateMotoFromDto(moto, dto);

        Moto savedMoto = motoRepository.save(moto);
        motoService.clearCache();

        return ResponseEntity
                .created(URI.create("/motos/" + savedMoto.getId()))
                .body(new ReadMotoDto(savedMoto));
    }

    // PUT
    @Operation(summary = "Atualizar moto existente")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Moto atualizada"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "404", description = "Moto não encontrada")
    })
    @PutMapping("/{id}")
    @Transactional
    public ReadMotoDto update(
            @Parameter(description = "ID da moto", example = "1004")
            @PathVariable Long id,

            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Dados atualizados da moto",
                    required = true
            )
            @RequestBody @Valid UpdateMotoDto dto
    ) {
        Moto moto = motoService.findByIdOrThrow(id);
        motoService.updateMotoFromDto(moto, dto);
        motoService.clearCache();
        return new ReadMotoDto(moto);
    }

    // DELETE
    @Operation(summary = "Excluir moto")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Moto excluída"),
            @ApiResponse(responseCode = "404", description = "Moto não encontrada")
    })
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> delete(
            @Parameter(description = "ID da moto", example = "5")
            @PathVariable Long id
    ) {
        Moto moto = motoService.findByIdOrThrow(id);
        motoRepository.delete(moto);
        motoService.clearCache();
        return ResponseEntity.noContent().build();
    }

    // CLEAR CACHE
    @Operation(summary = "Limpar cache de motos")
    @ApiResponse(responseCode = "204", description = "Cache limpo")
    @PostMapping("/clear-cache")
    public ResponseEntity<Void> clearCache() {
        motoService.clearCache();
        return ResponseEntity.noContent().build();
    }

}
