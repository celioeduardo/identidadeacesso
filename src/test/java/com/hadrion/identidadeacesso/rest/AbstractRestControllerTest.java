package com.hadrion.identidadeacesso.rest;

import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import com.hadrion.identidadeacesso.IdentidadeAcessoApplicationTest;

public abstract class AbstractRestControllerTest extends IdentidadeAcessoApplicationTest{

	protected MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype());

    protected MockMvc mockMvc;
    
    @Autowired
    private WebApplicationContext webApplicationContext;
    
    @Before
    public void setup() {
        this.mockMvc = webAppContextSetup(webApplicationContext).build();
    }
    
	
}
