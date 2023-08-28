package ru.ashabelskii.conveyor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan({
        "ru.ashabelskii.conveyor.config"
})
public class ConveyorApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConveyorApplication.class, args);
    }

}
