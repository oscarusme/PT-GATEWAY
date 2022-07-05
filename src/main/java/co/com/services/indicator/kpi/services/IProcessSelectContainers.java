package co.com.services.indicator.kpi.services;

import java.util.List;

import co.com.services.indicator.kpi.model.RequestContainers;
import co.com.services.indicator.kpi.model.ResponseContainers;

public interface IProcessSelectContainers {
	
	public ResponseContainers selectContainers(double budget, List<RequestContainers> listContainers );

}
