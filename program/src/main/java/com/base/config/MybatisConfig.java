package com.base.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * mybatis配置类
 * @author junior
 *
 */
@Configuration
@MapperScan({ "com.base.dao" })
public class MybatisConfig {

}
