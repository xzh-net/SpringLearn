package net.xzh.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * 根容器
 */
@Configuration
@ComponentScan(basePackages = { "net.xzh" }, excludeFilters = {
		@ComponentScan.Filter(type = FilterType.ANNOTATION, value = EnableWebMvc.class) })
//@MapperScan("com.hbjr.mapper") // 配置mybatis设置
public class RootConfig {
}