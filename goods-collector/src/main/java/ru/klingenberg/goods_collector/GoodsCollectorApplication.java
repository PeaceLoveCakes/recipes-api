package ru.klingenberg.goods_collector;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class GoodsCollectorApplication {

	public static void main(String[] args) {
		SpringApplication.run(GoodsCollectorApplication.class, args);
	}

}
