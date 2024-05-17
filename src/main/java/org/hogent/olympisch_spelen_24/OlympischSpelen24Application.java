package org.hogent.olympisch_spelen_24;

import org.hogent.olympisch_spelen_24.utils.CompetitionValidation;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import java.util.Locale;

@SpringBootApplication
@EnableJpaRepositories("org.hogent.olympisch_spelen_24.repository")
@EntityScan("org.hogent.olympisch_spelen_24.domain")
public class OlympischSpelen24Application implements WebMvcConfigurer {

    public static void main(String[] args) {
        SpringApplication.run(OlympischSpelen24Application.class, args);
    }

    @Bean
    CompetitionValidation competitionValidation() {
        return new CompetitionValidation();
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addRedirectViewController("/", "/sport");
    }
}
