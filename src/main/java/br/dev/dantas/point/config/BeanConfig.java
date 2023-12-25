package br.dev.dantas.point.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import test.outside.Connection;

@Configuration
public class BeanConfig {

    @Bean(name = "mysql" )
    public Connection connectionMySql() {
        return new Connection("localhost:3306", "mysql", "1234");
    }
    @Bean(name = "postgres" )
    public Connection connectionPostgresql() {
        return new Connection("localhost:5432", "postgres", "1234");
    }

}
