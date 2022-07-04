package com.decathlon.cndp_interface_dev;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
@MapperScan("com.decathlon.cndp_interface_dev.mapper")
public class CndpInterfaceDevApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(CndpInterfaceDevApplication.class, args);
		System.out.println(context);

	}
//s
}
