package com.example.reporter.service;

import com.example.reporter.entity.Message;
import nl.altindag.log.LogCaptor;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import software.amazon.awssdk.auth.credentials.EnvironmentVariableCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sns.SnsClient;
import software.amazon.awssdk.services.sns.model.PublishRequest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class SNSServiceTest {

    @Mock
    private static SnsClient snsClient;

    private static LogCaptor logCaptor = LogCaptor.forClass(SNSService.class);

    @InjectMocks
    private SNSService snsService;

    @BeforeAll
    public static void setUp() {
        snsClient = mock(SnsClient.class);
    }

//    Todo add localstack for testing
//    @Test
    void shouldPublishMessageToTopic() {
        doReturn(null).when(snsClient).publish((PublishRequest) any());

        snsService.pubTopic("Test message");

        assertThat(logCaptor.getInfoLogs()).contains("Sending message: ");
    }

}