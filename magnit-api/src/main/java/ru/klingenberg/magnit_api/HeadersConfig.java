package ru.klingenberg.magnit_api;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;

import java.util.function.Consumer;

@Configuration
public class HeadersConfig {

    @Bean
    public Consumer<HttpHeaders> httpHeaders() {
        return httpHeaders -> {
            httpHeaders.set("Accept", "*/*");
            httpHeaders.set("Accept-Language", "en-US,en;q=0.9,ru;q=0.8");
            httpHeaders.set("Content-Type", "application/json");
            httpHeaders.set("x-app-version", "0.1.0");
            httpHeaders.set("x-client-name", "magnit");
            httpHeaders.set("x-device-id", "36s1expdgd");
            httpHeaders.set("x-device-platform", "Web");
            httpHeaders.set("x-device-tag", "disabled");
            httpHeaders.set("x-platform-version", "window.navigator.userAgent");
            httpHeaders.set("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64");
        };
    }

}
