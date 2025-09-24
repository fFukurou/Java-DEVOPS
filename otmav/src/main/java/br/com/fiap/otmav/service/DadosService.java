package br.com.fiap.otmav.service;

import br.com.fiap.otmav.domain.dados.*;
import br.com.fiap.otmav.domain.filial.ReadFilialDto;
import br.com.fiap.otmav.exception.DuplicateEntryException;
import br.com.fiap.otmav.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DadosService {

    @Autowired
    private final DadosRepository dadosRepository;

    @Autowired
    private final PasswordEncoder passwordEncoder;

    // CREATE (hash password)
    @Transactional
    public ReadDadosDto create(CreateDadosDto dto) {
        if (dto.cpf() != null && dadosRepository.existsByCpf(dto.cpf())) {
            throw new DuplicateEntryException("CPF already registered");
        }
        if (dto.email() != null && dadosRepository.findByEmail(dto.email()).isPresent()) {
            throw new DuplicateEntryException("Email already registered");
        }

        Dados dados = new Dados();
        dados.setCpf(dto.cpf());
        dados.setTelefone(dto.telefone());
        dados.setEmail(dto.email());
        // Hash the password before saving
        if (dto.senha() != null) {
            dados.setSenha(passwordEncoder.encode(dto.senha()));
        } else {
            dados.setSenha(null);
        }
        dados.setNome(dto.nome());

        Dados saved = dadosRepository.save(dados);
        return new ReadDadosDto(saved);
    }

    // LIST (paginated)
    public Page<ReadDadosDto> findAll(Pageable pageable) {
        return dadosRepository.findAll(pageable).map(ReadDadosDto::new);
    }

    // FIND BY ID
    public ReadDadosDto findById(Long id) {
        return dadosRepository.findById(id)
                .map(ReadDadosDto::new)
                .orElseThrow(() -> new NotFoundException("Dados not found with id: " + id));
    }

    // UPDATE
    @Transactional
    public ReadDadosDto update(Long id, UpdateDadosDto dto) {
        Dados dados = dadosRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Dados not found with id: " + id));

        if (dto.telefone() != null) dados.setTelefone(dto.telefone());
        if (dto.email() != null) dados.setEmail(dto.email());
        if (dto.senha() != null) {
            // encode new password
            dados.setSenha(passwordEncoder.encode(dto.senha()));
        }
        if (dto.nome() != null) dados.setNome(dto.nome());

        Dados saved = dadosRepository.save(dados);
        return new ReadDadosDto(saved);
    }

    // DELETE
    @Transactional
    public void delete(Long id) {
        if (!dadosRepository.existsById(id)) {
            throw new NotFoundException("Dados not found with id: " + id);
        }
        dadosRepository.deleteById(id);
    }

    // FIND ALL FILTERED
    public Page<ReadDadosDto> findAllFiltered(
            String cpf,
            String nome,
            String email,
            Pageable pageable) {

        return dadosRepository.findAllFiltered(
                cpf,
                nome,
                email,
                pageable
        ).map(ReadDadosDto::new);
    }


}
