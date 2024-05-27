package init_spring1.init_spring;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@OpenAPIDefinition(
		info = @Info(title = "Apply Default Global SecurityScheme in springdoc-openapi", version = "1.0.0"),
		security = {@SecurityRequirement(name = "Bearer Authentication")})
@SecurityScheme(
		name = "Bearer Authentication",
		type = SecuritySchemeType.HTTP,
		bearerFormat = "JWT",
		scheme = "bearer"
)
@EnableScheduling

public class InitSpringApplication {



	public static void main(String[] args) {

        String s="hoclaptrinh";
        s.concat(" hoclaptrinhteam");//phuong thuc concat() phu them vao cuoi chuoi
        System.out.println(s);

		SpringApplication.run(InitSpringApplication.class, args);
	}

}
