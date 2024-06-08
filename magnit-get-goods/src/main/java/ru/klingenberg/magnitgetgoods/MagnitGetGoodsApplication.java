package ru.klingenberg.magnitgetgoods;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class MagnitGetGoodsApplication {

	public static void main(String[] args) {
		SpringApplication.run(MagnitGetGoodsApplication.class, args);
	}

}
