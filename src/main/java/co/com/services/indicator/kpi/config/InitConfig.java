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
}
