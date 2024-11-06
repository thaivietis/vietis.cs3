package com.nqt.cs3;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
// @SpringBootApplication(exclude = {
// 		SecurityAutoConfiguration.class
// })
public class VietisApplication {

	public static void main(String[] args) {
		SpringApplication.run(VietisApplication.class, args);
	}

}
