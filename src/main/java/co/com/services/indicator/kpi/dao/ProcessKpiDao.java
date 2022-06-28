package co.com.services.indicator.kpi.dao;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.PrimaryKey;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.UpdateItemOutcome;
import com.fasterxml.jackson.databind.ObjectMapper;

import co.com.services.indicator.kpi.implement.CalculateShippingContainers;
import co.com.services.indicator.kpi.model.StatsContainers;

@Service
public class ProcessKpiDao {
	
	private static Logger logger = LoggerFactory.getLogger(CalculateShippingContainers.class);
	
	AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard().build();
	DynamoDB dynamoDB = new DynamoDB(client);
	Table table = dynamoDB.getTable("tbl_stats_kpi");
	
	@Autowired
	private ObjectMapper mapper;
	
	public StatsContainers selectStats() {
		StatsContainers stats = null;
		try {
			Item item = table.getItem("key_kpi", "234");

			String respons = item.toJSONPretty();

			stats = mapper.readValue(respons, StatsContainers.class);
		} catch (Exception e) {
			logger.error("Error ejecutando la funtion selectStats: " + e.getMessage());
		}
		return stats;
	}

	public void updateStast(double budget, double containers_dispatched, double containers_not_dispatched) {

		try {
			StatsContainers stats = new StatsContainers();

			stats.setBudget_used(budget);
			stats.setContainers_dispatched(containers_dispatched);
			stats.setContainers_not_dispatched(containers_not_dispatched);

			Map<String, String> expressionAttributeNames = new HashMap<String, String>();
			expressionAttributeNames.put("#P", "budget_used");
			expressionAttributeNames.put("#F", "containers_dispatched");
			expressionAttributeNames.put("#H", "containers_not_dispatched");

			Map<String, Object> expressionAttributeValues = new HashMap<String, Object>();
			expressionAttributeValues.put(":val1", stats.getBudget_used());
			expressionAttributeValues.put(":val2", stats.getContainers_dispatched());
			expressionAttributeValues.put(":val3", stats.getContainers_not_dispatched());

			UpdateItemOutcome outcome = table.updateItem(new PrimaryKey("key_kpi", "234"),
					"set #P = :val1, #F = :val2, #H = :val3", expressionAttributeNames, expressionAttributeValues);

			Object result = outcome.getUpdateItemResult().getSdkResponseMetadata();

			logger.info("Update: " + result);
		} catch (Exception e) {
			logger.error("Error: " + e.getMessage());
		}
	}

}
