package com.jianghongbo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.ComponentScan;

@MapperScan("com.*.dao") //扫描的mapper
@SpringBootApplication
//@ComponentScan(basePackages = {"com.jianghongbo.controller", "com.jianghongbo.service"})
public class ShssApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShssApplication.class, args);
	}

}
