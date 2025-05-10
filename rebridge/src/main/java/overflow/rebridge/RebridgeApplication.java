package overflow.rebridge;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class RebridgeApplication {

	public static void main(String[] args) {
		SpringApplication.run(RebridgeApplication.class, args);
	}

}
