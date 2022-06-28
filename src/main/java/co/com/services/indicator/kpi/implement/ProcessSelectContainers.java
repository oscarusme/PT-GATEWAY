package co.com.services.indicator.kpi.implement;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.OptionalDouble;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.com.services.indicator.kpi.services.IProcessSelectContainers;
import co.com.services.indicator.kpi.util.Constantes;
import co.com.services.indicator.kpi.dao.ProcessKpiDao;
import co.com.services.indicator.kpi.model.RequestContainers;
import co.com.services.indicator.kpi.model.StatsContainers;

@Service
public class ProcessSelectContainers implements IProcessSelectContainers {

	@Autowired
	private ProcessKpiDao kpiDao;

	private static Logger logger = LoggerFactory.getLogger(ProcessSelectContainers.class);

	public List<RequestContainers> selectContainers(double budget, List<RequestContainers> listContainers) {

		List<RequestContainers> resultContainersdispatched = new ArrayList<>();
		List<RequestContainers> resulContainersNotdispatched = new ArrayList<>();
		try {
			RequestContainers response = new RequestContainers();

			double totCost = 0.0;

			double sumCost = listContainers.stream().filter(c -> c.getTransportationCost() > 0)
					.mapToDouble(c -> c.getTransportationCost()).sum();

			if (budget < sumCost) {

				while (listContainers.size() > 0) {

					OptionalDouble maxContenedor = listContainers.stream().filter(c -> c.getContainerValue() > 0)
							.mapToDouble(c -> c.getContainerValue()).max();

					RequestContainers requesCon = listContainers.stream()
							.filter(c -> c.getContainerValue() == maxContenedor.orElse(0)).map(e -> e).findFirst()
							.get();

					if (totCost + requesCon.getTransportationCost() <= budget) {

						totCost += mathRound(requesCon.getTransportationCost());

						resultContainersdispatched.add(requesCon);
						listContainers.remove(requesCon);
					} else {
						logger.info("Se supero el budget");
						resulContainersNotdispatched.add(requesCon);
						listContainers.remove(requesCon);
					}
				}

				calculateContainersDispached(resultContainersdispatched, resulContainersNotdispatched, budget);

			} else {
				logger.info("Se pueden despachar todos los contenedores");
				resultContainersdispatched = listContainers;
				Collections.sort(resultContainersdispatched,
						(c, b) -> c.getNameContainer().compareTo(b.getNameContainer()));
			}
			if (resultContainersdispatched.size() == 0) {
				response.setMessage(Constantes.MESSAGE_BUDGET);
				resultContainersdispatched.add(response);
			} else {
				Collections.sort(resultContainersdispatched,
						(c, b) -> c.getNameContainer().compareTo(b.getNameContainer()));
			}
		} catch (Exception e) {
			logger.info("Error: " + e.getMessage());
		}

		return resultContainersdispatched;
	}

	private void calculateContainersDispached(List<RequestContainers> resultContainersdispatched,
			List<RequestContainers> resulContainersNotdispatched, double totBudget) {

		StatsContainers statsContainers = kpiDao.selectStats();

		double totContainersDispatched = 0.0;
		double totContainerssNotdispatched = 0.0;
		double sumContainerdispatched = 0.0;
		double sumContainersNotdispatched = 0.0;
		double totSumBudget = 0.0;

		totContainersDispatched = statsContainers.getContainers_dispatched();
		totContainerssNotdispatched = statsContainers.getContainers_not_dispatched();
		totSumBudget = statsContainers.getBudget_used();

		if (resultContainersdispatched.size() > 0) {
			sumContainerdispatched = resultContainersdispatched.stream().filter(c -> c.getContainerValue() > 0)
					.mapToDouble(c -> c.getContainerValue()).sum();
		}

		if (resulContainersNotdispatched.size() > 0) {
			sumContainersNotdispatched = resulContainersNotdispatched.stream().filter(c -> c.getContainerValue() > 0)
					.mapToDouble(c -> c.getContainerValue()).sum();
		}

		totContainersDispatched += sumContainerdispatched;

		totContainerssNotdispatched += sumContainersNotdispatched;

		totSumBudget += totBudget;

		kpiDao.updateStast(mathRound(totSumBudget), mathRound(totContainersDispatched),
				mathRound(totContainerssNotdispatched));

	}

	private double mathRound(double valRound) {
		return Math.round(valRound * 100.0) / 100.0;
	}
}
