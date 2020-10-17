package com.ecy.show.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@PropertySource(value = "classpath:application.properties")
public class FileConfig implements WebMvcConfigurer {

	@Value("${web.upload-path}")
	private String path;

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		// 读取配置文件中的上传路径
		registry.addResourceHandler("/upload/**").addResourceLocations("file:" +path);
	}
}
