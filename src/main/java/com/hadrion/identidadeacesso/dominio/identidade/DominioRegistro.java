package com.hadrion.identidadeacesso.dominio.identidade;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class DominioRegistro implements ApplicationContextAware {

	private static ApplicationContext applicationContext;
	
	@Override
	public synchronized void setApplicationContext(
			ApplicationContext applicationContext) throws BeansException {
		if (DominioRegistro.applicationContext == null) {
			DominioRegistro.applicationContext = applicationContext;
		}
	}
	
	public static EncriptacaoService encriptacaoService(){
		return (EncriptacaoService) 
				applicationContext.getBean(EncriptacaoService.class);
	}
	
	public static SenhaService senhaService(){
		return (SenhaService) 
				applicationContext.getBean(SenhaService.class);
	}

	//private static EventoDominioPublicador eventoDominioPublicador;
	
	//	public static EventoDominioPublicador eventoDominioPublicador(){
	//		if (eventoDominioPublicador == null){
	//			
	//			if (applicationContext == null)
	//				throw new RuntimeException("ApplicationContext não foi definido.");
	//			
	//			eventoDominioPublicador = (EventoDominioPublicador) 
	//				applicationContext.getBean(EventoDominioPublicadorDefault.class);
	//		}
	//		return eventoDominioPublicador;
	//	}
	
//	public synchronized static void setEventoDominioPublicador(
//			EventoDominioPublicador publicador){
//		eventoDominioPublicador = publicador;
//	}
}