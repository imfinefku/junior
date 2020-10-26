package com.base.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan({ "com.base.dao" })
public class MybatisConfig {

}
