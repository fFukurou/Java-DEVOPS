package br.com.fiap.otmav.infra.security;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * CONTROLE DE SEGURANÇA
 *
 * - ENDPOINTS DE API --> /api/
 * - MVC -> /
 */

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final SecurityFilter securityFilter;
    private final CookieBlacklistLogoutHandler cookieLogoutHandler;

    public SecurityConfig(SecurityFilter securityFilter,
                          CookieBlacklistLogoutHandler cookieLogoutHandler) {
        this.securityFilter = securityFilter;
        this.cookieLogoutHandler = cookieLogoutHandler;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())

                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED))

                .authorizeHttpRequests(auth -> auth

                        // --------------------------
                        // RECURSOS PUBLICOS / STATICS
                        // --------------------------
                        .requestMatchers("/", "/index", "/home").permitAll()
                        .requestMatchers("/css/**", "/js/**", "/images/**", "/webjars/**", "/favicon.ico").permitAll()
                        .requestMatchers("/swagger-ui.html/**", "/swagger-ui/**", "/v3/api-docs/**").permitAll()
                        .requestMatchers("/error", "/error/**").permitAll()

                        // --------------------------
                        // AUTH
                        // --------------------------
                        .requestMatchers("/login", "/login/**", "/logout", "/access-denied").permitAll()
                        .requestMatchers("/api/auth/**").permitAll()

                        // --------------------------
                        // API
                        // --------------------------
                        // PUBLICO
                        .requestMatchers(HttpMethod.GET, "/api/filiais/**", "/api/modelos/**", "/api/situacoes/**", "/api/motos", "/api/motos/*").permitAll()

                        // AUTHENTICADO
                        .requestMatchers(HttpMethod.DELETE, "/api/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/filiais/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/filiais/**").hasRole("ADMIN")

                        .requestMatchers(HttpMethod.POST, "/api/dados/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/motoristas").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/funcionarios").permitAll()

                        .requestMatchers(HttpMethod.POST, "/api/motos/*/transfer", "/api/motos/*/assign-driver").authenticated()

                        // --------------------------
                        // WEB MVC
                        // --------------------------

                        .requestMatchers(HttpMethod.GET, "/filiais/new").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/motos", "/motos/*", "/motoristas", "/motoristas/*", "/filiais", "/filiais/*", "/situacoes", "/situacoes/*", "/funcionarios", "/funcionarios/*").permitAll()

                        // GERENCIAMENTO DE MOTOS --> AUTHENTICADO
                        .requestMatchers(HttpMethod.GET, "/motos/manage").authenticated()
                        .requestMatchers(HttpMethod.GET, "/motos/manage/**").authenticated()
                        .requestMatchers(HttpMethod.POST, "/motos/manage/**").authenticated()

                        // ADMIN ONLY --> RECURSOS ESSENCIAIS
                        .requestMatchers(HttpMethod.GET, "/dados").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/motos/*/delete").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/filiais/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/enderecos/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/motoristas/*/delete").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/funcionarios/*/delete").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/motoristas/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/funcionarios/**").hasRole("ADMIN")

                        .requestMatchers("/funcionarios/new", "/funcionarios").permitAll()
                        .requestMatchers("/motoristas/new", "/motoristas").permitAll()

                        .anyRequest().authenticated()
                )

                // REQUISIÇÕES WEB MANDARÃO O USUÁRIO PARA UM REDIRECT DE LOGIN;
                // REQUISIÇÕES DIRETAMENTE NA API RETORNARÃO 401 UNAUTHORIZED;
                // EXCEPTION DE AUTENTICAÇÃO
                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint((req, res, authEx) -> {
                            String accept = req.getHeader("Accept");
                            if (accept != null && accept.contains("text/html")) {
                                String redirectTo = req.getRequestURI();
                                res.sendRedirect("/login?redirectTo=" + java.net.URLEncoder.encode(redirectTo, java.nio.charset.StandardCharsets.UTF_8));
                            } else {
                                res.sendError(HttpServletResponse.SC_UNAUTHORIZED);
                            }
                        })
                        .accessDeniedHandler(new CustomAccessDeniedHandler())
                )

                // ADICIONANDO O FILTRO DE SEGURANÇA ANTES DAS REQUISIÇOES
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)

                // PASSANDO 'EMAIL' COMO 'USERNAME' PRO SECURITY COOPERAR ;)
                .formLogin(login -> login
                        .loginPage("/login")
                        .permitAll()
                        .loginProcessingUrl("/permit_login")
                        .usernameParameter("email")
                        .passwordParameter("password")
                        .defaultSuccessUrl("/", false)
                        .failureUrl("/login?error")
                )

                // LOGOUT VAI INVALIDAR A SESSÃO E MATAR OS COOKIES
                // A /API VAI ADICIONAR O TOKEN À UMA BLACKLIST
                .logout(logout -> logout
                        .logoutUrl("/logout-web")
                        .addLogoutHandler(cookieLogoutHandler)
                        .logoutSuccessUrl("/?logout")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                );
        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    // CRIPTOGRAFAR A SENHA, CLARO
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
