package ru.ashabelskii.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan(value = "ru.ashabelskii.application.integration")
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
