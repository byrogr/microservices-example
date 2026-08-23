package com.ecommerce.product_service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoCredential;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class MongoConfig extends AbstractMongoClientConfiguration {
    private final AppProperties appProperties;

    @Override
    protected String getDatabaseName() {
        return appProperties.getDatabase();
    }

    @Override
    @Bean
    public MongoClient mongoClient() {
        MongoCredential credential = MongoCredential.createCredential(
            appProperties.getUsername(),
            appProperties.getAuthDatabase(),
            appProperties.getPassword().toCharArray()
        );

        String connString = String.format("mongodb://%s:%d", appProperties.getHost(), appProperties.getPort());

        MongoClientSettings settings = MongoClientSettings.builder()
            .applyConnectionString(new ConnectionString(connString))
            .credential(credential)
            .build();
        
        return MongoClients.create(settings);
    }

}
