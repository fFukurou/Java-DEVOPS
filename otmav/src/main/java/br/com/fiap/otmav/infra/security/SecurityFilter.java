package br.com.fiap.otmav.infra.security;

import br.com.fiap.otmav.domain.funcionario.FuncionarioRepository;
import br.com.fiap.otmav.service.TokenService;
import br.com.fiap.otmav.service.TokenBlacklistService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import jakarta.servlet.http.Cookie;


import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(SecurityFilter.class);

    @Autowired
    private TokenService tokenService;

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    @Autowired
    private TokenBlacklistService tokenBlacklistService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        try {
            var token = recoverToken(request);
            if (token != null) {
                // check blacklist first
                if (tokenBlacklistService.isBlacklisted(token)) {
                    logger.debug("Rejected request with blacklisted token for {}", request.getRequestURI());
                    // Do not set authentication; let security handle unauthenticated requests
                } else {
                    var email = tokenService.validateToken(token); // returns subject/email or null
                    if (email != null) {
                        var userOpt = funcionarioRepository.findByDadosEmailFetchDados(email);
                        if (userOpt.isPresent()) {
                            UserDetails userDetails = new FuncionarioUserDetails(userOpt.get());
                            var authentication = new UsernamePasswordAuthenticationToken(
                                    userDetails, null, userDetails.getAuthorities());
                            SecurityContextHolder.getContext().setAuthentication(authentication);
                            logger.debug("Authenticated user {} for {}", userDetails.getUsername(), request.getRequestURI());
                        } else {
                            logger.debug("No funcionario found for token subject: {}", email);
                        }
                    } else {
                        logger.debug("Token invalid or expired for request to {}", request.getRequestURI());
                    }
                }
            }
        } catch (Exception ex) {
            logger.error("Error while trying to authenticate request: " + ex.getMessage(), ex);
        }

        filterChain.doFilter(request, response);
    }

    private String recoverToken(HttpServletRequest request) {
        // 1) header
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7).trim();
        }

        // 2) cookie fallback
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie c : cookies) {
                if ("OTMAV_TOKEN".equals(c.getName())) {
                    String value = c.getValue();
                    if (value != null && !value.isBlank()) return value.trim();
                }
            }
        }
        return null;
    }
}
