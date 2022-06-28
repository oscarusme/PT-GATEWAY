package co.com.services.indicator.kpi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

public class RequestContainers {
	
	private String nameContainer;
	private double containerValue;
	private double transportationCost;
	
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String message;
	
	public String getNameContainer() {
		return nameContainer;
	}
	public void setNameContainer(String nameContainer) {
		this.nameContainer = nameContainer;
	}	
	public double getContainerValue() {
		return containerValue;
	}
	public void setContainerValue(double containerValue) {
		this.containerValue = containerValue;
	}
	public double getTransportationCost() {
		return transportationCost;
	}
	public void setTransportationCost(double transportationCost) {
		this.transportationCost = transportationCost;
	}	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
}
