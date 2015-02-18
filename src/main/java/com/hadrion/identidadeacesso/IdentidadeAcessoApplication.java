package com.hadrion.identidadeacesso;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.hateoas.config.EnableEntityLinks;
import org.springframework.hateoas.config.EnableHypermediaSupport;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@SpringBootApplication
@ComponentScan(basePackages = "com.hadrion.identidadeacesso")
@EnableEntityLinks
@EnableWebMvc
@EnableHypermediaSupport(type = {EnableHypermediaSupport.HypermediaType.HAL})
public class IdentidadeAcessoApplication extends WebMvcConfigurerAdapter {

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		//registry.addInterceptor(localeInterceptor);
	}

	public static void main(String[] args) {
		SpringApplication.run(IdentidadeAcessoApplication.class, args);

//		ApplicationContext ctx = SpringApplication.run(
//				IdentidadeAcessoApplication.class, args);

//		String[] beanNames = ctx.getBeanDefinitionNames();
//		Arrays.sort(beanNames);
//		for (String beanName : beanNames) {
//			if (beanName.endsWith("Service"))
//				System.out.println(beanName);
//		}

	}
}
