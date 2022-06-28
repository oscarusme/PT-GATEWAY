package co.com.services.indicator.kpi.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import co.com.services.indicator.kpi.services.ICalculateShippingContainers;
import co.com.services.indicator.kpi.util.Constantes;

@RestController
@RequestMapping("/containers")
public class ContainerController {
	
	@Autowired
	ICalculateShippingContainers icalShippinContainers;
	
	
	@PostMapping(Constantes.URL_GENERATE_KPI)
	public ResponseEntity<Object> generateOutputContainers(@RequestParam("budget") double budget, @RequestBody String requestContainers){
		return icalShippinContainers.requestContainers(budget, requestContainers);		
	}
	
	@GetMapping("/stats")
	public ResponseEntity<Object> getStats(){
		return icalShippinContainers.getStatsContainers();		
	}

}
