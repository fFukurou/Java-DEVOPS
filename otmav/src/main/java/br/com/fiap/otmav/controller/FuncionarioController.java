package br.com.fiap.otmav.controller;

import br.com.fiap.otmav.domain.funcionario.*;
import br.com.fiap.otmav.service.FuncionarioService;
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
@RequestMapping("/api/funcionarios")
@Tag(name = "Funcionarios", description = "CRUD for employees and admin role management")
public class FuncionarioController {

    @Autowired
    private FuncionarioService funcionarioService;

    // Create
    @Operation(summary = "Create new Funcionario (employee)")
    @ApiResponse(responseCode = "201", description = "Funcionario created")
    @PostMapping
    public ResponseEntity<ReadFuncionarioDto> create(
            @RequestBody @Valid CreateFuncionarioDto dto,
            UriComponentsBuilder uriBuilder) {
        ReadFuncionarioDto created = funcionarioService.create(dto);
        URI uri = uriBuilder.path("/api/funcionarios/{id}").buildAndExpand(created.id()).toUri();
        return ResponseEntity.created(uri).body(created);
    }

    // GET list with filters
    @Operation(summary = "List funcionarios with optional filters")
    @ApiResponse(responseCode = "200", description = "List returned")
    @GetMapping
    public ResponseEntity<Page<ReadFuncionarioDto>> findAll(
            @Parameter(description = "Cargo (role/title) to filter (optional)", example = "Admin")
            @RequestParam(required = false) String cargo,

            @Parameter(description = "Filial ID (optional)", example = "1")
            @RequestParam(required = false) Long filialId,

            @Parameter(description = "Dados ID (optional)", example = "1")
            @RequestParam(required = false) Long dadosId,

            @Parameter(hidden = true)
            @PageableDefault(size = 10, sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable) {

        return ResponseEntity.ok(funcionarioService.findAllFiltered(cargo, filialId, dadosId, pageable));
    }

    // GET by id
    @Operation(summary = "Get Funcionario by ID")
    @GetMapping("/{id}")
    public ResponseEntity<ReadFuncionarioDto> findById(@PathVariable Long id) {
        return ResponseEntity.ok(funcionarioService.findById(id));
    }

    // PUT update
    @Operation(summary = "Update Funcionario")
    @PutMapping("/{id}")
    public ResponseEntity<ReadFuncionarioDto> update(
            @PathVariable Long id,
            @RequestBody @Valid UpdateFuncionarioDto dto) {
        return ResponseEntity.ok(funcionarioService.update(id, dto));
    }

    // DELETE (restricted to ADMIN in your SecurityConfig)
    @Operation(summary = "Delete Funcionario")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        funcionarioService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
