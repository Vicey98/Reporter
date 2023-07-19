package com.example.reporter.service;

import com.example.reporter.converter.MessageConverter;
import com.example.reporter.entity.Message;
import com.example.reporter.repository.MessageRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.Collection;
import java.util.List;

@Service
@Slf4j
public class StockPickService {

    public WebClient webClient;

    public MessageConverter messageConverter;

    @Autowired
    public MessageRepository messageRepository;

    @Autowired
    public NotificationService notificationService;

    @Autowired
    public SNSService snsService;

    public StockPickService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
        this.messageConverter = new MessageConverter();
    }

    public Mono<String> handleStockPicks(List stockPicks) {
        log.info("Starting processing on stock pick list");

        // Refactor below
        stockPicks.stream().forEach(stockPick -> {
            var option = notificationService.process(String.valueOf(stockPick));
            option.subscribe(item -> {
                var message = messageConverter.convertToMessage(String.valueOf(stockPick), item);
                messageRepository.save(message);
                snsService.pubTopic(message.toString());
            });
        });

        log.debug("Successfully finished processing on stock pick list");

        return Mono.just("Successfully handled stock picks");
    }

    public Collection<Message> getAllMessages() {
        return messageRepository.findAll();
    }

}
