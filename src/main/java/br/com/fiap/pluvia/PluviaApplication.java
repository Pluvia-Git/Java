package br.com.fiap.pluvia;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class PluviaApplication {

	public static void main(String[] args) {
		SpringApplication.run(PluviaApplication.class, args);
	}
}