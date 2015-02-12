package com.hadrion.identidadeacesso;

import java.util.Arrays;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.hadrion.identidadeacesso")
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
