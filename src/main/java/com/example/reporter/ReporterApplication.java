package com.example.reporter;

import com.example.reporter.entity.Message;
import com.example.reporter.entity.news.SentimentLabel;
import com.example.reporter.repository.MessageRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;

@SpringBootApplication
@ConfigurationPropertiesScan
public class ReporterApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReporterApplication.class, args);
	}

}
