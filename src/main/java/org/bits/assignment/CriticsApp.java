package org.bits.assignment;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;


@SpringBootApplication
@EnableMongoRepositories(basePackages = {"org.bits.assignment.repositories"})
public class CriticsApp extends SpringBootServletInitializer {
    public static void main(String[] args) {
        SpringApplication.run(CriticsApp.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(CriticsApp.class).bannerMode(Banner.Mode.OFF).headless(true);
    }
}