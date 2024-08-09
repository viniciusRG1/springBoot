package com.garagem.demo_rest_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(exclude={DataSourceAutoConfiguration.class})
@ComponentScan(basePackages = {"com.garagem.demo_rest_api.repository.UsuarioRepository", "com.garagem.demo_rest_api.entity.Usuario", "com.garagem.demo_rest_api.service.UsuarioService", "com.garagem.demo_rest_api.web.controller.UsuarioController"})
public class DemoRestApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoRestApiApplication.class, args);
	}

}
