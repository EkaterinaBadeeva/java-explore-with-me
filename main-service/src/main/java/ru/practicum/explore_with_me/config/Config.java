package ru.practicum.explore_with_me.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

//@Configuration
//public class Config {
//    @Bean
//    public HitClient getStatsClient(@Value("${stats-server.url}") String uriBase, RestTemplate rest) {
//        return new HitClient(uriBase, rest);
//    }
//}
@Configuration
public class Config {
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}