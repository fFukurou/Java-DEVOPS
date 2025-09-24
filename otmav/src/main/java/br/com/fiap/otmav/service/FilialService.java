package br.com.fiap.otmav.service;

import br.com.fiap.otmav.domain.endereco.Endereco;
import br.com.fiap.otmav.domain.endereco.EnderecoRepository;
import br.com.fiap.otmav.domain.filial.*;
import br.com.fiap.otmav.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FilialService {

    @Autowired
    private FilialRepository filialRepository;

    @Autowired
    private EnderecoRepository enderecoRepository;

    // CREATE
    @Transactional
    public ReadFilialDto create(CreateFilialDto dto) {
        Endereco endereco = null;
        if (dto.enderecoId() != null) {
            endereco = enderecoRepository.findById(dto.enderecoId())
                    .orElseThrow(() -> new NotFoundException("Endereco not found with id: " + dto.enderecoId()));
        }

        Filial filial = new Filial();
        filial.setNome(dto.nome());
        filial.setEndereco(endereco);

        return new ReadFilialDto(filialRepository.save(filial));
    }

    // LIST (paginated)
    public Page<ReadFilialDto> findAll(Pageable pageable) {
        return filialRepository.findAll(pageable).map(ReadFilialDto::new);
    }

    // FIND ALL FILTERED
    public Page<ReadFilialDto> findAllFiltered(
            String nome,
            Long enderecoId,
            Pageable pageable) {

        return filialRepository.findAllFiltered(
                nome,
                enderecoId,
                pageable
        ).map(ReadFilialDto::new);
    }

    // FIND BY ID
    public ReadFilialDto findById(Long id) {
        return filialRepository.findById(id)
                .map(ReadFilialDto::new)
                .orElseThrow(() -> new NotFoundException("Filial not found with id: " + id));
    }

    // UPDATE
    @Transactional
    public ReadFilialDto update(Long id, UpdateFilialDto dto) {
        Filial filial = filialRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Filial not found with id: " + id));

        if (dto.nome() != null) filial.setNome(dto.nome());

        if (dto.enderecoId() != null) {
            Endereco endereco = enderecoRepository.findById(dto.enderecoId())
                    .orElseThrow(() -> new NotFoundException("Endereco not found with id: " + dto.enderecoId()));
            filial.setEndereco(endereco);
        }

        return new ReadFilialDto(filialRepository.save(filial));
    }

    // DELETE
    @Transactional
    public void delete(Long id) {
        filialRepository.deleteById(id);
    }
}