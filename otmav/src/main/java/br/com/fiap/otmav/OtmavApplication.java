package br.com.fiap.otmav;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class OtmavApplication {

	public static void main(String[] args) {
		SpringApplication.run(OtmavApplication.class, args);
	}

}
