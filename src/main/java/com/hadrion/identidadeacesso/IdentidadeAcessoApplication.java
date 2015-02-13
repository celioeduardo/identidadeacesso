package com.hadrion.identidadeacesso;

import java.util.Arrays;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.hateoas.config.EnableEntityLinks;
import org.springframework.hateoas.config.EnableHypermediaSupport;
import org.springframework.hateoas.config.EnableHypermediaSupport.HypermediaType;

@SpringBootApplication
@ComponentScan(basePackages = "com.hadrion.identidadeacesso")
@EnableEntityLinks
@EnableHypermediaSupport(type = HypermediaType.HAL)
//@EntityScan(basePackages = "com.hadrion.identidadeacesso.dominio")
//@EnableJpaRepositories(basePackages = "com.hadrion.identidadeacesso.infraestrutura.persistencia")
public class IdentidadeAcessoApplication {

    public static void main(String[] args) {
        //SpringApplication.run(IdentidadeacessoApplication.class, args);
        
        ApplicationContext ctx = SpringApplication.run(IdentidadeAcessoApplication.class, args);

		String[] beanNames = ctx.getBeanDefinitionNames();
		Arrays.sort(beanNames);
		for (String beanName : beanNames) {
			if (beanName.endsWith("Service"))
				System.out.println(beanName);
		}
    }
}
