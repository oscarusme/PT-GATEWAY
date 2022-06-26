package co.com.services.indicator.kpi.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import co.com.services.indicator.kpi.model.RequestContainers;
import co.com.services.indicator.kpi.services.ICalculateShippingContainers;

@RestController
@RequestMapping("/containers/")
public class ContainerController {
	
	@Autowired
	ICalculateShippingContainers icalShippinContainers;
	
	
	@PostMapping("generate/kpi")
	public ResponseEntity<Object> generateOutputContainers(@RequestParam("budget") double budget, @RequestBody String requestContainers){
		return icalShippinContainers.requestContainers(budget, requestContainers);		
	}
	
	@GetMapping("stats")
	public ResponseEntity<Object> getStats(){
		return icalShippinContainers.getStatsContainers();		
	}

}
