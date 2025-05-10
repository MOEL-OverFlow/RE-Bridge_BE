package overflow.rebridge;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class RebridgeApplication {

	public static void main(String[] args) {
		new SpringApplicationBuilder(RebridgeApplication.class)
				.properties("spring.config.location=classpath:/application.yml,classpath:/application-secret.yml")
				.run(args);
		SpringApplication.run(RebridgeApplication.class, args);
	}
}
