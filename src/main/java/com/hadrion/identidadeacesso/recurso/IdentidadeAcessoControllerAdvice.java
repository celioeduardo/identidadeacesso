package com.hadrion.identidadeacesso.recurso;

import org.springframework.hateoas.VndErrors;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class IdentidadeAcessoControllerAdvice {

	@ResponseBody
    @ExceptionHandler(RecursoNaoEncontadoException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    VndErrors userNotFoundExceptionHandler(RecursoNaoEncontadoException ex) {
        return new VndErrors("erro", ex.getMessage());
    }
	
}
