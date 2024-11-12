package edu.fra.uas;

import org.checkerframework.checker.units.qual.s;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import edu.fra.uas.service.StreamingService;

@SpringBootApplication
public class GRpcClientStreamingApplication {

	private static final Logger log = org.slf4j.LoggerFactory.getLogger(GRpcClientStreamingApplication.class);

	@Autowired
	private StreamingService streamingService;

	public static void main(String[] args) {
		SpringApplication.run(GRpcClientStreamingApplication.class, args);
	}

	@Bean
	CommandLineRunner init() {
		return args -> {
			log.info("### Start gRPC-Client-Streaming ###");

			streamingService.serverSideStreamingListStockQuotes();
			Thread.sleep(2000);
			streamingService.clientSideStreamingStatisticsOfStocks();
			Thread.sleep(2000);
			streamingService.bidirectionalStreamingListsStockQuotes();
			Thread.sleep(2000);

			log.info("### Stop gRPC-Client-Streaming ###");
			System.exit(0);
		};
	}

}
