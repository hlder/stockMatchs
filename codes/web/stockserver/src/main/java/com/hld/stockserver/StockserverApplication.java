package com.hld.stockserver;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@MapperScan("com.hld.stockserver.mapper")
public class StockserverApplication {

	public static void main(String[] args) {
		SpringApplication.run(StockserverApplication.class, args);
	}
}
