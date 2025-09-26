// src/main/java/br/com/fiap/otmav/controller/web/HomeController.java
package br.com.fiap.otmav.controller.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("title", "OTMAV - Home");
        return "index";
    }
}
