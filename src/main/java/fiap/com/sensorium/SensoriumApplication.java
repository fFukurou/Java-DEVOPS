package fiap.com.sensorium;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class SensoriumApplication {

	public static void main(String[] args) {
		SpringApplication.run(SensoriumApplication.class, args);
	}

}
