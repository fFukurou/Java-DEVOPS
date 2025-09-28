package br.com.fiap.otmav.controller;

import br.com.fiap.otmav.domain.motorista.*;
import br.com.fiap.otmav.service.MotoristaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/motoristas")
@Tag(name = "Motoristas", description = "CRUD para Motoristas")
public class MotoristaController {

    @Autowired
    private MotoristaService motoristaService;

    // CREATE
    @Operation(summary = "CREATE MOTORISTA")
    @PostMapping
    public ResponseEntity<ReadMotoristaDto> create(@RequestBody @Valid CreateMotoristaDto dto, UriComponentsBuilder uriBuilder) {
        ReadMotoristaDto created = motoristaService.create(dto);
        URI uri = uriBuilder.path("/api/motoristas/{id}").buildAndExpand(created.id()).toUri();
        return ResponseEntity.created(uri).body(created);
    }

    // GET
    @Operation(summary = "Listar Motoristas")
    @ApiResponse(responseCode = "200", description = "List returned")
    @GetMapping
    public ResponseEntity<Page<ReadMotoristaDto>> findAll(
            @Parameter(description = "Plano (opcional)", example = "MottuConquiste") @RequestParam(required = false) MotoristaPlano plano,
            @Parameter(description = "Dados ID (opcional)", example = "1") @RequestParam(required = false) Long dadosId,
            @Parameter(hidden = true) @PageableDefault(size = 10, sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable) {

        return ResponseEntity.ok(motoristaService.findAllFiltered(plano, dadosId, pageable));
    }

    // GET BY ID
    @Operation(summary = "Get MOTORISTA by ID")
    @GetMapping("/{id}")
    public ResponseEntity<ReadMotoristaDto> findById(@PathVariable Long id) {
        return ResponseEntity.ok(motoristaService.findById(id));
    }

    // UPDATE
    @Operation(summary = "Update motorista")
    @PutMapping("/{id}")
    public ResponseEntity<ReadMotoristaDto> update(@PathVariable Long id, @RequestBody @Valid UpdateMotoristaDto dto) {
        return ResponseEntity.ok(motoristaService.update(id, dto));
    }

    // DELETE
    @Operation(summary = "Delete motorista")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        motoristaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
