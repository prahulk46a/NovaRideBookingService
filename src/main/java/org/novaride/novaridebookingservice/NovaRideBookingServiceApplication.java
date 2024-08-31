package org.novaride.novaridebookingservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
@EnableDiscoveryClient


@EntityScan("org.novaride.modelentity.models")
public class NovaRideBookingServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(NovaRideBookingServiceApplication.class, args);
    }

}
