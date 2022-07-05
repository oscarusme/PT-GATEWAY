package co.com.services.indicator.kpi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;


public class AwsConfig {

	@Bean
	public AmazonDynamoDB awsDinamoDbClient() {	

		BasicAWSCredentials awsCreds = new BasicAWSCredentials(InitConfig.conectionIdAws(),
				InitConfig.conectionKeyAws());

		return AmazonDynamoDBClientBuilder.standard().withRegion(Regions.US_EAST_1)
				.withCredentials(new AWSStaticCredentialsProvider(awsCreds)).build();
	}
}
