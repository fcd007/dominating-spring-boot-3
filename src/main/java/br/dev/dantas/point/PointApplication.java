package br.dev.dantas.point;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import java.util.Arrays;

@SpringBootApplication
@ComponentScan(basePackages = {"test.outside", "br.dev.dantas.point"})
public class PointApplication {

	public static void main(String[] args) {

		var applicationContext = SpringApplication.run(PointApplication.class, args);
		Arrays.stream(applicationContext.getBeanDefinitionNames())
				.forEach(System.out::println);
	}
}
