package com.microservicios;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;


@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication
@EntityScan({"com.microservicios.entity"})
public class MicroserviciosAlumnosApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicroserviciosAlumnosApplication.class, args);
	}

}
