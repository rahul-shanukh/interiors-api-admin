package com.interiors.interiorsadminapi;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class DatabaseDiagnostic {

    @Bean
    CommandLineRunner verifyDatabase(DataSource dataSource) {
        return args -> {
            try (var connection = dataSource.getConnection()) {

                System.out.println("JDBC URL: "
                        + connection.getMetaData().getURL());

                System.out.println("Database: "
                        + connection.getCatalog());

                System.out.println("Schema: "
                        + connection.getSchema());

                try (var statement = connection.createStatement();
                     var result = statement.executeQuery("""
                         SELECT table_schema, table_name
                         FROM information_schema.tables
                         WHERE table_schema = 'public'
                         ORDER BY table_name
                         """)) {

                    System.out.println("===== DATABASE TABLES =====");

                    while (result.next()) {
                        System.out.println(
                                result.getString("table_schema")
                                        + "."
                                        + result.getString("table_name"));
                    }

                    System.out.println("==========================");
                }
            }
        };
    }
}