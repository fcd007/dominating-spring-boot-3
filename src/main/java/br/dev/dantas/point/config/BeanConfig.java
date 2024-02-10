package br.dev.dantas.point.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import test.outside.Connection;

@Configuration
public class BeanConfig {

    @Value("${database.url}")
    private String url;

    @Value("${database.username}")
    private String username;

    @Value("${database.password}")
    private String password;

    @Bean(name = "database")
    public Connection connection() {
        return new Connection(url, username, password);
    }

}
