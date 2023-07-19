package com.example.reporter.repository;

import com.example.reporter.config.TestBase;
import com.example.reporter.entity.Message;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class MessageRepositoryTest extends TestBase {

    @Autowired
    private MessageRepository messageRepository;
    @Test
    void shouldInitialiseTestBase() {
        messageRepository.save(new Message());
        assertThat(messageRepository.findAll().size()).isEqualTo(1);
    }

    

}