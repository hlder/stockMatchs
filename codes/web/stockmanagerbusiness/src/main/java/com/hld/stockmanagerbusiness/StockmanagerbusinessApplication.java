package com.hld.stockmanagerbusiness;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@MapperScan("com.hld.stockmanagerbusiness.mapper")
public class StockmanagerbusinessApplication {

	public static void main(String[] args) {
		SpringApplication.run(StockmanagerbusinessApplication.class, args);
	}
}
