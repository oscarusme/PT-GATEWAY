package co.com.services.indicator.kpi.implement;


import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.ObjectMapper;

import co.com.services.indicator.kpi.dao.ProcessKpiDao;
import co.com.services.indicator.kpi.model.ListContainers;
import co.com.services.indicator.kpi.model.RequestContainers;
import co.com.services.indicator.kpi.model.StatsContainers;
import co.com.services.indicator.kpi.services.ICalculateShippingContainers;
import co.com.services.indicator.kpi.util.Constantes;

@Service
public class CalculateShippingContainers implements ICalculateShippingContainers {

	private static Logger logger = LoggerFactory.getLogger(CalculateShippingContainers.class);

	@Autowired
	private ObjectMapper mapper;

	@Autowired
	private ProcessSelectContainers selectContainers;

	@Autowired
	private ProcessKpiDao kpiDao;

	public ResponseEntity<Object> requestContainers(double budget, String listRequestContainers) {
		List<RequestContainers> listResult = new ArrayList<>();
		try {
			ListContainers listContainers = mapper.readValue(listRequestContainers, ListContainers.class);

			listResult = selectContainers.selectContainers(budget, listContainers.getListContainers());

		} catch (Exception e) {
			logger.info(Constantes.ERROR_REQUEST_CONTAINERS + e.getMessage());
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<>(listResult, HttpStatus.OK);
	}

	public ResponseEntity<Object> getStatsContainers() {

		StatsContainers statsContainers = new StatsContainers();
		try {
			statsContainers = kpiDao.selectStats();
		} catch (Exception e) {
			logger.info(Constantes.ERROR_STATS_CONTAINERS + e.getMessage());
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(statsContainers, HttpStatus.OK);
	}
}
