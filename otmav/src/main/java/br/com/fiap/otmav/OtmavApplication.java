package br.com.fiap.otmav;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.awt.*;
import java.net.URI;

@SpringBootApplication
public class OtmavApplication {
	// RODA O PROGRAMA
	public static void main(String[] args) {
		SpringApplication.run(OtmavApplication.class, args);
	}


	// ABRE O BROWSWER NA STARTUP (obrigado theSushil do StackOverflow)
	@EventListener(ApplicationReadyEvent.class)
	public void openBrowser() {
		String url = "http://localhost:8080";
		String os = System.getProperty("os.name").toLowerCase();

		try {
			Runtime rt = Runtime.getRuntime();

			if (os.contains("win")) {
				// Windows
				rt.exec("rundll32 url.dll,FileProtocolHandler " + url);
			} else if (os.contains("mac")) {
				// macOS
				rt.exec(new String[]{"open", url});
			} else if (os.contains("nix") || os.contains("nux")) {
				// Linux/Unix
				rt.exec(new String[]{"xdg-open", url});
			} else {
				// Reza
				if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
					Desktop.getDesktop().browse(new URI(url));
				}
			}
			System.out.println("✅ Abrindo browswer em: " + url);
		} catch (Exception e) {
			System.out.println("❌ Não foi possível abrir o browser. Por favor visite: " + url);
		}
	}

}
