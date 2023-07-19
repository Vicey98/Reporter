package com.example.reporter.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sns.SnsClient;
import software.amazon.awssdk.services.sns.model.PublishRequest;
import software.amazon.awssdk.services.sns.model.PublishResponse;
import software.amazon.awssdk.services.sns.model.SnsException;

@Component
public class SNSService {


    public static final Logger log = LogManager.getLogger(SNSService.class);
    public String topicArn = "arn:aws:sns:ap-southeast-2:980722221710:MessageSNSTopic";

    private final Region region = Region.AP_SOUTHEAST_2;

    public SnsClient getSnsClient() {
        return SnsClient.builder()
                .region(region)
                .build();
    }

    public void pubTopic(String message) {
        log.info("Sending message: \n" + message);
        try {
            var snsClient = getSnsClient();
            var request = PublishRequest.builder()
                    .message(message)
                    .topicArn(topicArn)
                    .build();

            var result = snsClient.publish(request);
            log.info("Message sent in. Status was " + result.sdkHttpResponse().statusCode());
        } catch (SnsException e) {
            log.error(e.awsErrorDetails().errorMessage());
            System.exit(1);
        }
    }

}
