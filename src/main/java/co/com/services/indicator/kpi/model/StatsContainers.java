package co.com.services.indicator.kpi.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.fasterxml.jackson.annotation.JsonIgnore;

@DynamoDBTable(tableName = "tbl_stats_kpi")
public class StatsContainers {
	
	@JsonIgnore
	private String key_kpi;
	private double containers_dispatched;
	private double containers_not_dispatched;
	private double budget_used;
	
	
	 @DynamoDBHashKey(attributeName = "key_kpi")
	public String getKey_kpi() {
		return key_kpi;
	}
	public void setKey_kpi(String key_kpi) {
		this.key_kpi = key_kpi;
	}
	
	@DynamoDBAttribute(attributeName = "containers_dispatched")
	public double getContainers_dispatched() {
		return containers_dispatched;
	}
	public void setContainers_dispatched(double containers_dispatched) {
		this.containers_dispatched = containers_dispatched;
	}
	
	@DynamoDBAttribute(attributeName = "containers_not_dispatched")
	public double getContainers_not_dispatched() {
		return containers_not_dispatched;
	}
	public void setContainers_not_dispatched(double containers_not_dispatched) {
		this.containers_not_dispatched = containers_not_dispatched;
	}
	
	@DynamoDBAttribute(attributeName = "budget_used")
	public double getBudget_used() {
		return budget_used;
	}
	public void setBudget_used(double budget_used) {
		this.budget_used = budget_used;
	}
}
