package com.keepu.webAPI;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WebApiApplication {

	public static void main(String[] args) {
		if (isDevelopment()) {
			Dotenv dotenv = Dotenv.configure().load();
			System.setProperty("SPRING_DATASOURCE_URL", dotenv.get("SPRING_DATASOURCE_URL"));
			System.setProperty("SPRING_DATASOURCE_USERNAME", dotenv.get("SPRING_DATASOURCE_USERNAME"));
			System.setProperty("SPRING_DATASOURCE_PASSWORD", dotenv.get("SPRING_DATASOURCE_PASSWORD"));
		}
		SpringApplication.run(WebApiApplication.class, args);
	}

	private static boolean isDevelopment() {
		String env = System.getenv("ENVIRONMENT");
		return env == null || env.equalsIgnoreCase("development");
	}
}