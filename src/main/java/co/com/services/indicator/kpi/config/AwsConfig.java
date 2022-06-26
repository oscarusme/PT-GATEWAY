package co.com.services.indicator.kpi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;

@Configuration
public class AwsConfig {

	@Bean
	public AmazonDynamoDB awsDinamoDbClient() {

		BasicAWSCredentials awsCreds = new BasicAWSCredentials("AKIA6CAFJSWJPCGQ43EY",
				"7VWmY9l9r9ZTDC4J9FcmghlbNvOYg/XRETA9RWT4");

		return AmazonDynamoDBClientBuilder.standard().withRegion(Regions.US_EAST_1)
				.withCredentials(new AWSStaticCredentialsProvider(awsCreds)).build();
	}
}