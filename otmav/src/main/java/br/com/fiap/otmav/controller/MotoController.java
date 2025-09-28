package br.com.fiap.otmav.controller;

import br.com.fiap.otmav.domain.moto.*;
import br.com.fiap.otmav.service.MotoService;
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
@RequestMapping("/api/motos")
@Tag(name = "Motos", description = "CRUD para Motos")
public class MotoController {

    @Autowired
    private MotoService motoService;

    // CREATE
    @Operation(summary = "CRIAR MOTO")
    @PostMapping
    public ResponseEntity<ReadMotoDto> create(@RequestBody @Valid CreateMotoDto dto, UriComponentsBuilder uriBuilder) {
        ReadMotoDto created = motoService.create(dto);
        URI uri = uriBuilder.path("/api/motos/{id}").buildAndExpand(created.id()).toUri();
        return ResponseEntity.created(uri).body(created);
    }

    // GET
    @Operation(summary = "LISTAR MOTOS")
    @ApiResponse(responseCode = "200", description = "List returned")
    @GetMapping
    public ResponseEntity<Page<ReadMotoDto>> findAll(
            @Parameter(description = "Plate") @RequestParam(required = false) String placa,
            @Parameter(description = "Chassi") @RequestParam(required = false) String chassi,
            @Parameter(description = "Condition") @RequestParam(required = false) String condicao,
            @Parameter(description = "Motorista id") @RequestParam(required = false) Long motoristaId,
            @Parameter(description = "Modelo id") @RequestParam(required = false) Long modeloId,
            @Parameter(description = "Setor id") @RequestParam(required = false) Long setorId,
            @Parameter(description = "Situacao id") @RequestParam(required = false) Long situacaoId,
            @Parameter(hidden = true) @PageableDefault(size = 10, sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable) {

        return ResponseEntity.ok(motoService.findAllFiltered(placa, chassi, condicao, motoristaId, modeloId, setorId, situacaoId, pageable));
    }

    // GET BY ID
    @Operation(summary = "Get MOTO by ID")
    @GetMapping("/{id}")
    public ResponseEntity<ReadMotoDto> findById(@PathVariable Long id) {
        return ResponseEntity.ok(motoService.findById(id));
    }

    // UPDATE
    @Operation(summary = "Update moto")
    @PutMapping("/{id}")
    public ResponseEntity<ReadMotoDto> update(@PathVariable Long id, @RequestBody @Valid UpdateMotoDto dto) {
        return ResponseEntity.ok(motoService.update(id, dto));
    }


    // DELETE
    @Operation(summary = "Delete moto")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        motoService.delete(id);
        return ResponseEntity.noContent().build();
    }

    // TRANSFER
    @Operation(summary = "Transfere Moto para outro Setor")
    @PostMapping("/{id}/transfer")
    public ResponseEntity<ReadMotoDto> transferToSetor(
            @PathVariable Long id,
            @RequestBody @Valid TransferSetorDto dto) {
        ReadMotoDto updated = motoService.transferToSetor(id, dto);
        return ResponseEntity.ok(updated);
    }

    // ASSIGN
    @Operation(summary = "Atribui Motorista para X Moto")
    @PostMapping("/{id}/assign-driver")
    public ResponseEntity<ReadMotoDto> assignDriver(
            @PathVariable Long id,
            @RequestBody @Valid AssignMotoristaDto dto) {
        ReadMotoDto updated = motoService.assignDriver(id, dto);
        return ResponseEntity.ok(updated);
    }
}
