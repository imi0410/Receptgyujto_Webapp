package hu.unideb.inf.receptgyujto;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ReceptgyujtoApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReceptgyujtoApplication.class, args);
	}

    @Bean
    ModelMapper modelMapper(){
        ModelMapper m = new ModelMapper();
        return m;
    }
}
