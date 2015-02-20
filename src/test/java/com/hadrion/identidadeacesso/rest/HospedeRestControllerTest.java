package com.hadrion.identidadeacesso.rest;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.hadrion.identidadeacesso.aplicacao.IdentidadeAplicacaoService;
import com.hadrion.identidadeacesso.aplicacao.comando.AlocarHospedeComando;
import com.hadrion.identidadeacesso.aplicacao.data.HospedeData;

public class HospedeRestControllerTest extends AbstractRestControllerTest {
	@Autowired
	private IdentidadeAplicacaoService servico;

	private List<HospedeData> hospedeList = new ArrayList<HospedeData>();

	@Before
	public void setup() {
		super.setup();

		hospedeList.add(servico.alocarHospede(new AlocarHospedeComando("Apple Inc.",
				"Empresa Apple", "Steve Jobs", "jobs@apple.com")));
		hospedeList.add(servico.alocarHospede(new AlocarHospedeComando("Oracle Corporation",
				"Empresa Oracle", "Larry Ellison", "ellison@oracle.com")));
	}
	
	@Test
    public void hospedes() throws Exception {
        mockMvc.perform(get("/hospedes"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(contentType))
            .andExpect(jsonPath("$.content", hasSize(2)))
            .andExpect(jsonPath("$.content.[0].hospede.hospedeId", is(this.hospedeList.get(0).getHospedeId())))
            .andExpect(jsonPath("$.content.[0].hospede.nome", is(this.hospedeList.get(0).getNome())))
            .andExpect(jsonPath("$.content.[0].hospede.descricao", is(this.hospedeList.get(0).getDescricao())))
            .andExpect(jsonPath("$.content.[1].hospede.hospedeId", is(this.hospedeList.get(1).getHospedeId())))
            .andExpect(jsonPath("$.content.[1].hospede.nome", is(this.hospedeList.get(1).getNome())))
            .andExpect(jsonPath("$.content.[1].hospede.descricao", is(this.hospedeList.get(1).getDescricao())));
    }
}
