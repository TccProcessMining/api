package preprocessingmining.com.example.preprocessingmining;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication

//@EnableAutoConfiguration(exclude={CassandraDataAutoConfiguration.class})
public class PreprocessingMiningApplication {

	public static void main(String[] args) {
		SpringApplication.run(PreprocessingMiningApplication.class, args);
	}
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}


