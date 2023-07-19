package com.example.reporter.config;

import com.example.reporter.repository.MessageRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.parallel.ResourceLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

@Slf4j
@ExtendWith(ContainerEnvironmentExtension.class)
@ResourceLock(Environment.ID)
public abstract class TestBase {
    protected static String dbUrl;
    protected static String dbUsername;
    protected static String dbPassword;

    @Autowired
    private MessageRepository messageRepository;

    @BeforeAll
    static void prepareContainerEnvironment(Environment resource) {
        dbUrl = resource.getDbUrl();
        dbUsername = resource.getDbUsername();
        dbPassword = resource.getDbPassword();
    }

    @DynamicPropertySource
    static void dynamicPropertySource(DynamicPropertyRegistry registry) {
        registry.add("REPORTER_DB_URL", () -> dbUrl);
        registry.add("REPORTER_DB_USERNAME", () -> dbUsername);
        registry.add("REPORTER_DB_PASSWORD", () -> dbPassword);

        log.info("Started postgres docker test container with URL: {}", dbUrl);
    }
}
