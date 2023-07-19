package com.example.reporter.entity;

import com.example.reporter.entity.news.NewsPiece;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class NewsPieceTest {

    @SneakyThrows
    @Test
    void matchesJson() {
        ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        File file = new ClassPathResource("/sample-news-piece-response.json").getFile();
        NewsPiece json = mapper.readValue(file, NewsPiece.class);

        System.out.println(json.toString());
        assertNotNull(json);
    }
}