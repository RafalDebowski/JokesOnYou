package com.rafaldebowski.JokesOnYou;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rafaldebowski.JokesOnYou.service.external.ExternalJokeProvider;
import com.rafaldebowski.JokesOnYou.service.external.Sv443JokeProvider;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class JokesOnYouApplication {

    public static void main(String[] args) {
        SpringApplication.run(JokesOnYouApplication.class, args);
    }

    @Bean
    public RestTemplate provideRestTemplate() {
        return new RestTemplate();
    }

    @Bean
    public ObjectMapper provideObjectMapper() {
        return new ObjectMapper();
    }

    @Bean
    public List<ExternalJokeProvider> provideExternalJokeImplementations(final RestTemplate restTemplate,
                                                                         final ObjectMapper objectMapper) {
        List<ExternalJokeProvider> list = new ArrayList<>();
        list.add(new Sv443JokeProvider(restTemplate, objectMapper));
        list.add(new com.rafaldebowski.JokesOnYou.service.external.ChucknorrisJokeProvider(restTemplate, objectMapper));
        return list;
    }
}


