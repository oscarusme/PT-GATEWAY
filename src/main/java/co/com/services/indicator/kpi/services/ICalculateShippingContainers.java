package co.com.services.indicator.kpi.services;

import org.springframework.http.ResponseEntity;

public interface ICalculateShippingContainers {
	
	public ResponseEntity<Object> requestContainers(double budget, String listRequestContainers);
	
	public ResponseEntity<Object> getStatsContainers();
}
