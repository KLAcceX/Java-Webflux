package com.kl.webflux.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.config.AbstractReactiveMongoConfiguration;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.SimpleReactiveMongoDatabaseFactory;
import org.springframework.data.mongodb.core.WriteResultChecking;

import com.mongodb.ConnectionString;
import com.mongodb.WriteConcern;

public class MongoConfiguration extends AbstractReactiveMongoConfiguration {

    @Value("${spring.data.mongodb.uri}")
    private String connectionString;

    @Value("${spring.data.mongodb.database}")
    private String databaseName;

    ReactiveMongoTemplate template;

    public @Bean ReactiveMongoTemplate reactiveMongoTemplate() throws Exception {
        template = new ReactiveMongoTemplate(
                new SimpleReactiveMongoDatabaseFactory(new ConnectionString(this.connectionString)));
        template.setWriteResultChecking(WriteResultChecking.EXCEPTION);
        template.setWriteConcern(WriteConcern.ACKNOWLEDGED);
        return template;
    }

    @Override
    protected String getDatabaseName() {
        return this.databaseName;
    }
}
