package com.example.reporter.testcontainer;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.extension.ExtensionContext.Store.*;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.ext.ScriptUtils;
import org.testcontainers.jdbc.JdbcDatabaseDelegate;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.utility.DockerImageName;

@Slf4j
@Getter
public class ContainerEnvironmentResource extends Environment implements CloseableResource, AutoCloseable {

    private static final DockerImageName POSTGRES_IMAGE = DockerImageName.parse("postgres:13.2").asCompatibleSubstituteFor("postgres");

    @Container
    protected static PostgreSQLContainer postgreSQLContainer = new PostgreSQLContainer(POSTGRES_IMAGE)
            .withDatabaseName("test-db")
            .withUsername("user")
            .withPassword("password");

    ContainerEnvironmentResource() {
        log.info("Starting ContainerEnvironmentResource");

        postgreSQLContainer.start();

        var containerDelegate = new JdbcDatabaseDelegate(postgreSQLContainer, "");
        ScriptUtils.runInitScript(containerDelegate, "db/create-fights-table.sql");
        ScriptUtils.runInitScript(containerDelegate, "db/create-odds-table.sql");
        ScriptUtils.runInitScript(containerDelegate, "db/insert-mock-data-into-fights-table.sql");
        ScriptUtils.runInitScript(containerDelegate, "db/insert-mock-data-into-odds-table.sql");

        this.setDbUrl(postgreSQLContainer.getJdbcUrl());
        this.setDbUsername(postgreSQLContainer.getUsername());
        this.setDbPassword(postgreSQLContainer.getPassword());

        log.info("Started  postgres container with URL: {}", postgreSQLContainer.getJdbcUrl());
    }

    @Override
    public void close() {
        log.info("Closing ContainerEnvironmentResource");
        postgreSQLContainer.stop();
    }
}
