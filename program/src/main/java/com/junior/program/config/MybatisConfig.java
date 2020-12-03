package com.junior.program.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * mybatis配置类
 * @author junior
 *
 */
@Configuration
@MapperScan({ "com.junior.program.dao" })
public class MybatisConfig {

}
