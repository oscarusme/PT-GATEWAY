package co.com.services.indicator.kpi;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.core.JsonProcessingException;

import co.com.services.indicator.kpi.util.Constantes;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProcessSelectContainersTest.class)
@WebAppConfiguration
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ProcessSelectContainersTest {
	
	private MockMvc mockMvc; 
	
	@Autowired
	private WebApplicationContext webApplicationContext;
	
	@Before
	public void init() {
		mockMvc = webAppContextSetup(webApplicationContext).build();
	}
	
	@Test
	public void testGenerateKpi() throws JsonProcessingException, Exception {
		
		mockMvc.perform(post(Constantes.URL_GENERATE_KPI).contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk());
	}

}
