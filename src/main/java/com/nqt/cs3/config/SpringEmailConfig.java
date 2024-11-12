package com.nqt.cs3.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.SimpleMailMessage;

@Configuration
@PropertySource(value={"classpath:application.properties"})
public class SpringEmailConfig {

    // @Bean
    // public SimpleMailMessage templateSimpleMessage() {
    //     SimpleMailMessage message = new SimpleMailMessage();
    //     message.setText("Luận ơi, về trả tiền đi em, đừng để anh đợi lâu quá");
    //     return message;
    // }
}
