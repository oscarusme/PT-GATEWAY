package co.com.services.indicator.kpi.config;

import org.springframework.context.annotation.Configuration;

@Configuration
public class InitConfig {
			
	public static String conectionIdAws() {
		return System.getenv("ACCESS_KEY");
	}

	public static String conectionKeyAws() {
		return System.getenv("SECRECT_KEY");
	}
	
	public static String key_Kpi() {
		return System.getenv("KEY_KPI");
	}
	
	public static String val_key_Kpi() {
		return System.getenv("VAL_KEY_KPI");
	}
}
