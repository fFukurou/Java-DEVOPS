// br.com.fiap.otmav.controller.WebAuthController
package br.com.fiap.otmav.controller.web;

import br.com.fiap.otmav.infra.security.LoginDto;
import br.com.fiap.otmav.infra.security.TokenResponse;
import br.com.fiap.otmav.service.FuncionarioService;
import br.com.fiap.otmav.service.TokenService;
import br.com.fiap.otmav.service.TokenBlacklistService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.Duration;
import java.time.Instant;

@Controller
public class WebAuthController {

    @Autowired
    private FuncionarioService funcionarioService;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private TokenBlacklistService blacklistService;

    // show login form
    @GetMapping("/login")
    public String loginForm(@RequestParam(value = "redirectTo", required = false) String redirectTo, Model model) {
        model.addAttribute("loginDto", new LoginDto("", ""));
        model.addAttribute("redirectTo", redirectTo);
        return "auth/login";
    }

    // process form login, call your service that returns token, set cookie and redirect
    @PostMapping("/login")
    public String loginProcess(@ModelAttribute("loginDto") @Valid LoginDto dto,
                               BindingResult binding,
                               @RequestParam(value = "redirectTo", required = false) String redirectTo,
                               HttpServletResponse response,
                               RedirectAttributes redirectAttrs) {
        if (binding.hasErrors()) {
            return "auth/login";
        }

        // call service: it already returns ResponseEntity<TokenResponse>
        try {
            var resp = funcionarioService.login(dto);
            if (!resp.getStatusCode().is2xxSuccessful() || resp.getBody() == null) {
                redirectAttrs.addFlashAttribute("error", "Credenciais inválidas.");
                return "redirect:/login";
            }

            TokenResponse tokenResponse = resp.getBody();
            String token = tokenResponse.token();

            // set cookie
            Cookie cookie = new Cookie("OTMAV_TOKEN", token);
            cookie.setHttpOnly(true);
            cookie.setPath("/");
            cookie.setMaxAge((int) Duration.ofHours(8).getSeconds()); // adjust TTL
            // consider cookie.setSecure(true) in production with HTTPS
            response.addCookie(cookie);

            redirectAttrs.addFlashAttribute("success", "Login efetuado com sucesso.");

            if (redirectTo != null && !redirectTo.isBlank()) {
                return "redirect:" + redirectTo;
            }
            return "redirect:/";
        } catch (Exception ex) {
            redirectAttrs.addFlashAttribute("error", "Erro ao autenticar: " + ex.getMessage());
            return "redirect:/login";
        }
    }

    // web logout: blacklist cookie token and remove cookie
    @PostMapping("/logout-web")
    public String logoutWeb(HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttrs) {
        String token = null;
        var cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie c : cookies) {
                if ("OTMAV_TOKEN".equals(c.getName())) {
                    token = c.getValue();
                    // clear cookie
                    Cookie clear = new Cookie("OTMAV_TOKEN", "");
                    clear.setPath("/");
                    clear.setMaxAge(0);
                    response.addCookie(clear);
                }
            }
        }

        if (token != null) {
            Instant exp = tokenService.getExpirationInstant(token);
            if (exp == null) exp = Instant.now().plusSeconds(3600);
            blacklistService.blacklistToken(token, exp);
        }

        redirectAttrs.addFlashAttribute("success", "Desconectado com sucesso.");
        return "redirect:/";
    }

    // access denied page
    @GetMapping("/access-denied")
    public String accessDenied(Model model) {
        model.addAttribute("error", "Acesso negado: você não tem permissão para acessar este recurso.");
        return "auth/access-denied";
    }
}
