package br.dev.dantas.point.repository.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import test.outside.Connection;

@Configuration
public class BeanConfig {

    @Value("${database.url}")
    private String url;

    @Value("${database.username}")
    private String username;

    @Value("${database.password}")
    private String password;

    @Bean(name = "live")
    @Profile("live")
    public Connection connectionPostgres() {
        return new Connection(url, username, password);
    }

    @Bean(name = "test")
    @Profile("test")
    public Connection connectionMysql() {
        return new Connection(url, username, password);
    }
}