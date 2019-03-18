package com.jianghongbo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.mybatis.spring.annotation.MapperScan;

@MapperScan("com.*.dao") //扫描的mapper
@SpringBootApplication
public class ShssApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShssApplication.class, args);
	}

}
