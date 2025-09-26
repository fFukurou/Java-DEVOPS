// src/main/java/br/com/fiap/otmav/infra/security/CookieBlacklistLogoutHandler.java
package br.com.fiap.otmav.infra.security;

import br.com.fiap.otmav.service.TokenBlacklistService;
import br.com.fiap.otmav.service.TokenService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class CookieBlacklistLogoutHandler implements LogoutHandler {

    private final TokenBlacklistService blacklistService;
    private final TokenService tokenService;

    public CookieBlacklistLogoutHandler(TokenBlacklistService blacklistService, TokenService tokenService) {
        this.blacklistService = blacklistService;
        this.tokenService = tokenService;
    }

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        String token = null;

        // Try Authorization header first
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7).trim();
        }

        // Fallback to cookie
        if (token == null) {
            Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                for (Cookie c : cookies) {
                    if ("OTMAV_TOKEN".equals(c.getName())) {
                        token = c.getValue();
                        break;
                    }
                }
            }
        }

        if (token != null && !token.isBlank()) {
            // Blacklist token using expiry from token if available
            Instant exp = tokenService.getExpirationInstant(token);
            if (exp == null) exp = Instant.now().plusSeconds(3600);
            blacklistService.blacklistToken(token, exp);
        }

        // Remove cookie by setting empty value + maxAge=0 and path "/"
        Cookie clear = new Cookie("OTMAV_TOKEN", "");
        clear.setHttpOnly(true);
        clear.setPath("/");
        clear.setMaxAge(0);
        // If your app uses secure cookies in production, you may also set clear.setSecure(true)
        response.addCookie(clear);
    }
}
