package com.supplierBHX;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
@SecurityScheme(
		name = "token_auth",
		type = SecuritySchemeType.HTTP,
		scheme = "bearer",
		bearerFormat = "JWT"
)
@OpenAPIDefinition(
		info = @Info(
				title = "Swagger for BHX communicate with supplier",
				description = "This is list of endpoints and documentations of REST API",
				version = "1.0"
		),
		servers = {
				@Server(url = "http://localhost:8080", description = "Local development server"),
//				@Server(url = "http://localhost:5000", description = "Local production server")
		},
		security = {
				@SecurityRequirement(name = "token_auth")
		}
)
public class SupplierBhxApplication {

	public static void main(String[] args) {
		SpringApplication.run(SupplierBhxApplication.class, args);
	}

}
