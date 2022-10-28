package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.demo.param.QiitaParam;

@Configuration
public class QiitaConfig {

    @Bean
    public QiitaParam qiitaParam() {
        return new QiitaParam();
    }
}
