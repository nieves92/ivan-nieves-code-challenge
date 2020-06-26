package com.clip.challenge;

import com.clip.challenge.config.ServiceConfiguration;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@EnableDiscoveryClient
@Import(ServiceConfiguration.class)
@OpenAPIDefinition(
        info = @Info(
                title = "Transactions API",
                version = "v1",
                description = "Code challenge Open API Documentation"
        )
)
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
}
