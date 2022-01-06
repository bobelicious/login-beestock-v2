package br.com.serratec.beestock.config;

import org.springframework.context.annotation.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
@Configuration
public class AppConfig implements WebMvcConfigurer{

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		System.out.println("BCRYPT");
		return new BCryptPasswordEncoder();
	}
}

