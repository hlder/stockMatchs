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


//清理数据库
//		delete from wechat_formIds;
//		delete from user_shop_his;
//		delete from user_info_account_his;
//		delete from user_info_account;
//		delete from user_info;
//		delete from user_holder_stock;
//		delete from user_entrust_stock_his;
//		delete from user_entrust_stock;
//		delete from auth_code_his;
//		delete from auth_code;