package com.example.reporter;

import com.example.reporter.entity.StockPick;
import com.example.reporter.repository.StockPickRepository;
import com.example.reporter.service.TransactionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.Optional;

@SpringBootApplication
//@SpringBootApplication(exclude={DataSourceAutoConfiguration.class})
@ConfigurationPropertiesScan
//@ComponentScan(basePackages = { "com.example.reporter.entity" })
//@EnableJpaRepositories(basePackages = "com.example.reporter.repository")
public class ReporterApplication {

	//		Todo - command line runner alternative to populate tables with data
//			Spring MVC vs Spring Webflux

	private static final Logger log = LoggerFactory.getLogger(ReporterApplication.class);


	public static void main(String[] args) {
		SpringApplication.run(ReporterApplication.class, args);
	}


//	@Configuration
//	static class RouterConfig {
//
//		@Bean
//		public RouterFunction<ServerResponse> routes(TestHandler testHandler) {
//			return route(GET("/transactions"), testHandler::getTransactions)
//					.andRoute(GET("/times"), testHandler::sendTimePerSec);
//		}
//
//	}
//
//	@Component
//	static class TestHandler {
//
////		@Autowired
////		private final WebClient webClient;
//
//		@Autowired
//		private final StockPickRepository stockPickRepository;
//
//		public TestHandler(StockPickRepository stockPickRepository) {
////			this.webClient = webClientBuilder.baseUrl("https://tradestie.com/api/v1/apps/reddit").build();
//			this.stockPickRepository = stockPickRepository;
//		}
//
////		public TestHandler(StockPickRepository stockPickRepository, WebClient.Builder webClientBuilder) {
////			this.stockPickRepository = stockPickRepository;
////			this.webClient = webClientBuilder.baseUrl("https://tradestie.com/api/v1/apps/reddit").build();
////		}
//
//		public Mono<ServerResponse> getTime(ServerRequest serverRequest) {
//			return ok().contentType(MediaType.TEXT_PLAIN).body(Mono.just("Now is " + LocalDateTime.now()),
//					String.class);
//		}
//
//		public Mono<ServerResponse> getTransactions(ServerRequest request) {
//			log.info("Getting transactions from blockchain.com");
//			var transactions = new StockPick("APPL", 20, "Bullish", 0.2f);
////			var transactions = webClient.get()
////					.accept(MediaType.APPLICATION_JSON)
////					.retrieve()
////					.bodyToMono(StockPick.class)
////					.block();
//			stockPickRepository.save(transactions);
//			return ServerResponse.ok().body(Mono.just(transactions), StockPick.class);
//		}
//
//		public Mono<ServerResponse> sendTimePerSec(ServerRequest serverRequest) {
//			return ok().contentType(MediaType.TEXT_EVENT_STREAM)
//					.body(Flux.interval(Duration.ofSeconds(1)).map(l -> LocalDateTime.now().toString()), String.class);
//		}
//
//	}


	@Bean
	public CommandLineRunner demo(StockPickRepository repository) {
		return (args) -> {
			// save a few StockPick.java

			repository.save(new StockPick("APPL", 20, "Bullish", 0.2f));
			repository.save(new StockPick("TSLA", 150, "Bullish", 0.6f));

			// fetch all customers
			log.info("Stocks found with findAll():");
			log.info("-------------------------------");
			for (StockPick stockPick : repository.findAll()) {
				log.info(stockPick.ticker);
				log.info(stockPick.toString());
			}
			log.info("");

			// fetch an individual customer by ID
			Optional<StockPick> stockPick = Optional.ofNullable(repository.findByTicker("AAPL"));
			log.info("StockPick found with ticker AAPL");
			log.info("--------------------------------");
			if (stockPick.isPresent()) {
				log.info(stockPick.get().toString());
			} else {
				log.info("No stock found with ticker AAPL");
			}
			log.info("");

			// fetch customers by last name
			log.info("Customer found with findByLastName('TSLA'):");
			log.info("--------------------------------------------");
			log.info("");
		};
	}
}
