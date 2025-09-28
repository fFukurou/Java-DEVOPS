package br.com.fiap.otmav.service;

import br.com.fiap.otmav.domain.funcionario.Funcionario;
import br.com.fiap.otmav.domain.funcionario.FuncionarioRepository;
import br.com.fiap.otmav.infra.security.FuncionarioUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthService implements UserDetailsService {

    private final FuncionarioRepository funcionarioRepository;

    @Autowired
    public AuthService(FuncionarioRepository funcionarioRepository) {
        this.funcionarioRepository = funcionarioRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Funcionario func = funcionarioRepository.findByDadosEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Funcionario nao encontrado com email: " + email));
        return new FuncionarioUserDetails(func);
    }
}
