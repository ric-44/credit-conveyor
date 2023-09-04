package ru.ashabelskii.dossier;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan({
        "ru.ashabelskii.dossier.kafka.config",
        "ru.ashabelskii.dossier.config",
        "ru.ashabelskii.dossier.integration"
})
public class DossierApplication {

    public static void main(String[] args) {
        SpringApplication.run(DossierApplication.class, args);
    }

}
