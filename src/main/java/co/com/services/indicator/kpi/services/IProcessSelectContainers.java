package co.com.services.indicator.kpi.services;

import java.util.List;

import co.com.services.indicator.kpi.model.RequestContainers;

public interface IProcessSelectContainers {
	
	public List<RequestContainers> selectContainers(double budget, List<RequestContainers> listContainers );

}
