package com.interiors.interiorsadminapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(
        scanBasePackages = {
                "com.polymorphic.media",
                "com.interiors.interiorsadminapi"
        }
)
@EnableJpaRepositories(
        basePackages = "com.polymorphic.media.internal.infrastructure.taxonomy.persistence.jpa"
)
@EntityScan(
        basePackages = "com.polymorphic.media.internal.infrastructure.taxonomy.persistence.entity"
)
public class InteriorApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(InteriorApiApplication.class, args);
    }

}
