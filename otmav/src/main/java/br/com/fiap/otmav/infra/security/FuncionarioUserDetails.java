package br.com.fiap.otmav.infra.security;

import br.com.fiap.otmav.domain.funcionario.Funcionario;
import br.com.fiap.otmav.domain.dados.Dados;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class FuncionarioUserDetails implements UserDetails {

    private final String username;
    private final Funcionario funcionario;
    private final String password;
    private final List<GrantedAuthority> authorities;

    // MAPEIA O FUNCIONARIO PARA UM OBJETO 'USER' DO SECURITY...
    public FuncionarioUserDetails(Funcionario funcionario) {
        this.funcionario = funcionario;

        Dados dados = funcionario.getDados();

        this.username = dados.getEmail();
        this.password = dados.getSenha();

        // SE FOR UM 'ADMIN', SECURITY VAI MAPEAR PRA 'ROLE_ADMIN', CASO CONTRÁRIO, UM FUNCIONÁRIO NORMAL
        String cargo = funcionario.getCargo();
        if (cargo != null && cargo.equalsIgnoreCase("Admin")) {
            this.authorities = List.of(new SimpleGrantedAuthority("ROLE_ADMIN"));
        } else {
            this.authorities = List.of(new SimpleGrantedAuthority("ROLE_USER"));
        }
    }

    public Funcionario getFuncionario() {
        return funcionario;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() { return password; }

    @Override
    public String getUsername() { return username; }

    @Override
    public boolean isAccountNonExpired() { return true; }

    @Override
    public boolean isAccountNonLocked() { return true; }

    @Override
    public boolean isCredentialsNonExpired() { return true; }

    @Override
    public boolean isEnabled() { return true; }
}
