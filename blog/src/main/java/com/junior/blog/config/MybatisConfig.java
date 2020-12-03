package com.junior.blog.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * mybatis配置类
 * @author junior
 *
 */
@Configuration
@MapperScan({ "com.junior.blog.dao" })
public class MybatisConfig {

}
