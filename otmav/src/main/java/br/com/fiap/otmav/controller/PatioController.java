package br.com.fiap.otmav.controller;

import br.com.fiap.otmav.domain.patio.*;
import br.com.fiap.otmav.service.PatioService;
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
@RequestMapping("/api/patios")
@Tag(name = "Patios", description = "CRUD para PATIOS")
public class PatioController {

    @Autowired
    private PatioService patioService;

    // CREATE
    @Operation(summary = "CREATE PATIO")
    @PostMapping
    public ResponseEntity<ReadPatioDto> create(@RequestBody @Valid CreatePatioDto dto, UriComponentsBuilder uriBuilder) {
        ReadPatioDto created = patioService.create(dto);
        URI uri = uriBuilder.path("/api/patios/{id}").buildAndExpand(created.id()).toUri();
        return ResponseEntity.created(uri).body(created);
    }

    // GET
    @Operation(summary = "Listar Patios")
    @ApiResponse(responseCode = "200", description = "List returned")
    @GetMapping
    public ResponseEntity<Page<ReadPatioDto>> findAll(
            @Parameter(description = "Total motos (exato)") @RequestParam(required = false) Integer totalMotos,
            @Parameter(description = "Capacidade moto (exato)") @RequestParam(required = false) Integer capacidadeMoto,
            @Parameter(description = "Filial id (opcional)") @RequestParam(required = false) Long filialId,
            @Parameter(description = "Regiao id (opcional)") @RequestParam(required = false) Long regiaoId,
            @Parameter(hidden = true) @PageableDefault(size = 10, sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable) {

        return ResponseEntity.ok(patioService.findAllFiltered(totalMotos, capacidadeMoto, filialId, regiaoId, pageable));
    }
    // GET BY ID
    @Operation(summary = "Get patio by ID")
    @GetMapping("/{id}")
    public ResponseEntity<ReadPatioDto> findById(@PathVariable Long id) {
        return ResponseEntity.ok(patioService.findById(id));
    }

    //  UPDATE
    @Operation(summary = "Update patio")
    @PutMapping("/{id}")
    public ResponseEntity<ReadPatioDto> update(@PathVariable Long id, @RequestBody @Valid UpdatePatioDto dto) {
        return ResponseEntity.ok(patioService.update(id, dto));
    }

    // DELETE
    @Operation(summary = "Delete patio")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        patioService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
