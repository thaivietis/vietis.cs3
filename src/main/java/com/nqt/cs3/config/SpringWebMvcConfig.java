package com.nqt.cs3.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource(value = { "classpath:application.properties" })
public class SpringWebMvcConfig {

}
