package com.ecy.show;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
// 开启注解事务管理
@EnableTransactionManagement
@MapperScan("com.ecy.show.mapper")
public class DemoApplication implements WebMvcConfigurer {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**").allowCredentials(true).allowedOrigins("*")
				.allowedMethods("POST", "GET", "PUT", "OPTIONS", "DELETE").allowedHeaders("*");
	}

	/**
	 * 分页插件
	 */
	@Bean
	public PaginationInterceptor paginationInterceptor() {
		return new PaginationInterceptor();
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {

	}

	@Bean
	RestTemplate restTemplate(){
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.getMessageConverters().add(new WxMappingJackson2HttpMessageConverter());
		return restTemplate;
	}

	public class WxMappingJackson2HttpMessageConverter extends MappingJackson2HttpMessageConverter {
		public WxMappingJackson2HttpMessageConverter(){
			List<MediaType> mediaTypes = new ArrayList<>();
			mediaTypes.add(MediaType.TEXT_PLAIN);
			mediaTypes.add(MediaType.TEXT_HTML);
			setSupportedMediaTypes(mediaTypes);
		}
	}
}
