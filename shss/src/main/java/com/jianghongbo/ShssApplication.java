package com.jianghongbo;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;

@MapperScan("com.*.dao") //扫描的mapper
@EnableAsync
@SpringBootApplication
public class ShssApplication {

	public static void main(String[] args) {

		/**
		 * 隐藏banner启动方式
		 */
		SpringApplication springApplication = new SpringApplication(ShssApplication.class);
		//设置banner的模式为隐藏
		springApplication.setBannerMode(Banner.Mode.CONSOLE);
		//启动springboot应用程序
		springApplication.run(args);

		/**
		 * 原启动方式
		 */
		/*SpringApplication.run(ShssApplication.class, args);*/
	}

}
