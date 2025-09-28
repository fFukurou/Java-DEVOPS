package br.com.fiap.otmav.infra.security;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

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
                .authorizeHttpRequests(authorize -> authorize

//                        // static and swagger
//                        .requestMatchers("/swagger-ui.html/**").permitAll()
//                        .requestMatchers("/swagger-ui/**").permitAll()
//                        .requestMatchers("/error/**").permitAll()
//                        .requestMatchers("/error").permitAll()
//                        .requestMatchers("/v3/api-docs/**").permitAll()
//                        .requestMatchers("/webjars/**", "/images/**").permitAll()
//                        .requestMatchers("/", "/index", "/css/**", "/js/**", "/static/**", "/favicon.ico").permitAll()
//
//                        // protect mutating actions (API + MVC forms)
//                        .requestMatchers(HttpMethod.DELETE, "/api/**").hasRole("ADMIN")
//                        .requestMatchers(HttpMethod.POST, "/api/filiais/**").hasRole("ADMIN")
//                        .requestMatchers(HttpMethod.DELETE, "/filiais/**").hasRole("ADMIN")
//                        .requestMatchers(HttpMethod.POST, "/filiais/**").hasRole("ADMIN")
//
//                        // admin-only reads if needed
//                        .requestMatchers(HttpMethod.GET, "/api/regioes/**").hasRole("ADMIN")
//
//                        // other authenticated API actions
//                        .requestMatchers(HttpMethod.PUT, "/api/filiais/**").authenticated()
//                        .requestMatchers(HttpMethod.PUT, "/api/dados/**").authenticated()
//                        .requestMatchers(HttpMethod.POST, "/api/auth/logout").authenticated()
//
//                        // public API endpoints
//                        .requestMatchers(HttpMethod.POST, "/api/auth/login", "/api/auth/register", "/login", "/logout").permitAll()
//                        .requestMatchers(HttpMethod.GET,"/login", "/logout", "/access-denied", "access-denied").permitAll()
//                        .requestMatchers(HttpMethod.POST, "/api/funcionarios").permitAll()
//                        .requestMatchers(HttpMethod.POST, "/api/dados/**").permitAll()
//                        .requestMatchers(HttpMethod.GET, "/api/filiais/**").permitAll()
//
//                        // Filiais pages: allow viewing but require auth for create/edit/delete routes
//                        .requestMatchers(HttpMethod.GET, "/motoristas/**").permitAll()
//                        .requestMatchers(HttpMethod.POST, "/motoristas/**").permitAll()
//                        .requestMatchers(HttpMethod.GET, "/filiais/**").permitAll()
//                        .requestMatchers("/dados/**").hasRole("ADMIN")
//                        .requestMatchers(HttpMethod.DELETE, "/enderecos/**").hasRole("ADMIN")
//                        .requestMatchers(HttpMethod.DELETE, "/api/enderecos/**").hasRole("ADMIN")
//                        .requestMatchers("/enderecos/**").authenticated()

                        .anyRequest().permitAll()
                )
                .exceptionHandling(ex -> ex
                        // redirect browser requests to login page
                        .authenticationEntryPoint((req, res, authEx) -> {
                            String accept = req.getHeader("Accept");
                            if (accept != null && accept.contains("text/html")) {
                                // append redirectTo so user returns after login
                                String redirectTo = req.getRequestURI();
                                res.sendRedirect("/login?redirectTo=" + java.net.URLEncoder.encode(redirectTo, java.nio.charset.StandardCharsets.UTF_8));
                            } else {
                                res.sendError(HttpServletResponse.SC_UNAUTHORIZED);
                            }
                        })
//                        SE FOR WEB, ACCESS DENIED SERÁ UM REDIRECT; SE FOR JSON, SERÁ UM 403;
                        .accessDeniedHandler(new CustomAccessDeniedHandler())
                )
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                // we keep formLogin so Spring knows login page; but we actually use our WebAuthController
                .formLogin(login -> login
                        .loginPage("/login")// your login GET page
                        .permitAll()
                        .loginProcessingUrl("/permit_login")       // form POST target
                        .usernameParameter("email")         // <input name="email">
                        .passwordParameter("password")
                        .defaultSuccessUrl("/", false)
                        .failureUrl("/login?error")
                )
                .logout(logout -> logout
                        .logoutUrl("/logout-web")            // match the form action
                        .addLogoutHandler(cookieLogoutHandler)     // <- register it here
                        .logoutSuccessUrl("/?logout")        // where to go after logout
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                );

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
