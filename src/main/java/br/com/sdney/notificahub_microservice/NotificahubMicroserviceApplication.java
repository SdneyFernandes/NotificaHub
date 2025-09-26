package br.com.sdney.notificahub_microservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients; 
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@EnableFeignClients 
@SpringBootApplication
public class NotificahubMicroserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(NotificahubMicroserviceApplication.class, args);
	}

}
