package co.com.services.indicator.kpi;

import static org.hamcrest.CoreMatchers.any;
import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import java.io.IOException;
import java.net.URISyntaxException;

import org.junit.FixMethodOrder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.KeyAttribute;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.Gson;

import co.com.services.indicator.kpi.implement.CalculateShippingContainers;
import co.com.services.indicator.kpi.model.StatsContainers;
import co.com.services.indicator.kpi.util.Constantes;

@RunWith(SpringRunner.class)
@WebAppConfiguration
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@SpringBootTest(classes = ServicesIndicatorKpiApplication.class)
@SpringBootApplication
class ServicesIndicatorKpiApplicationTests {

	private MockMvc mockMvc;
	
	@Autowired
	private Gson gson;

	@Autowired
	private WebApplicationContext webApplicationContext;

	@Autowired
	private CalculateShippingContainers calShipingContainers;

	private CalculateShippingContainers calShipingContainersnull = new CalculateShippingContainers();

	@BeforeEach
	public void init() throws IOException, URISyntaxException {

		MockitoAnnotations.initMocks(this);

		mockMvc = webAppContextSetup(webApplicationContext).build();
	}

	private double budget = 1468.65;
	private double budgetAll = 2560.65;
	private double insufficientbudget = 100.65;

	
	AmazonDynamoDB client = Mockito.mock(AmazonDynamoDB.class);
	
	@Mock
	DynamoDB dynamoDB = new DynamoDB(client);
	
	private Table table = Mockito.mock(Table.class);

	private String request = "{" + "    \"listContainers\": [" + "        {" + "            \"nameContainer\": \"C1\","
			+ "            \"containerValue\": \"1744.03\"," + "            \"transportationCost\": \"571.40\""
			+ "        }," + "        {" + "            \"nameContainer\": \"C2\","
			+ "            \"containerValue\": \"3579.07\"," + "            \"transportationCost\": \"537.33\""
			+ "        }," + "        {" + "            \"nameContainer\": \"C3\","
			+ "            \"containerValue\": \"1379.26\"," + "            \"transportationCost\": \"434.66\""
			+ "        }," + "        {" + "            \"nameContainer\": \"C4\","
			+ "            \"containerValue\": \"1700.12\"," + "            \"transportationCost\": \"347.28\""
			+ "        }," + "        {" + "            \"nameContainer\": \"C5\","
			+ "            \"containerValue\": \"3678.80\"," + "            \"transportationCost\": \"270.54\""
			+ "        }," + "        {" + "            \"nameContainer\": \"C6\",\n"
			+ "            \"containerValue\": \"5678.80\"," + "            \"transportationCost\": \"370.54\""
			+ "        }" + "    ]" + "}";

	@Test
	public void requestContainersTest() throws JsonProcessingException, Exception {

		ResponseEntity<Object> result;

		result = calShipingContainers.requestContainers(budget, request);

		assertEquals(HttpStatus.OK, result.getStatusCode());
	}

	@Test
	public void requestContainersTestAll() throws JsonProcessingException, Exception {

		ResponseEntity<Object> result;

		result = calShipingContainers.requestContainers(budgetAll, request);

		assertEquals(HttpStatus.OK, result.getStatusCode());
	}

	@Test
	public void requestContainersTestInsufficient() throws JsonProcessingException, Exception {

		ResponseEntity<Object> result;

		result = calShipingContainers.requestContainers(insufficientbudget, request);

		assertEquals(HttpStatus.OK, result.getStatusCode());
	}

	@Test
	public void requestContainersTestNull() throws JsonProcessingException, Exception {

		ResponseEntity<Object> result;

		result = calShipingContainersnull.requestContainers(budget, request);

		assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
	}

	@Test
	public void getStatsContainersTest() throws JsonProcessingException, Exception {

		ResponseEntity<Object> result;

		StatsContainers stats = new StatsContainers();
		stats.setBudget_used(567.90);
		stats.setContainers_dispatched(345543.98);
		stats.setContainers_not_dispatched(45533.65);
		
		String requestJson = gson.toJson(stats);
		
		Item item = new Item().withPrimaryKey("key_kpi", "234").withJSON("tbl_stats_kpi", requestJson);

		 Mockito.when(table.getItem("key_kpi", "234")).thenReturn(item);

		result = calShipingContainers.getStatsContainers();

		assertEquals(HttpStatus.OK, result.getStatusCode());
	}

	@Test
	public void getStatsContainersTestNull() throws JsonProcessingException, Exception {

		ResponseEntity<Object> result;

		result = calShipingContainersnull.getStatsContainers();

		assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
	}

	@Test
	public void generateOutputContainersTest() throws JsonProcessingException, Exception {

		mockMvc.perform(post("/containers" + Constantes.URL_GENERATE_KPI).param("budget", "1468.65")
				.contentType(MediaType.APPLICATION_JSON).content(request)).andExpect(status().is(200));
	}

	@Test
	public void getStatsTest() throws JsonProcessingException, Exception {

		mockMvc.perform(get("/containers/stats").contentType(MediaType.APPLICATION_JSON).content("{}"))
				.andExpect(status().is(200));
	}

}
