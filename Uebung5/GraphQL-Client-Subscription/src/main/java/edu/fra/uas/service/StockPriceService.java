package edu.fra.uas.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.graphql.client.WebSocketGraphQlClient;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.socket.client.WebSocketClient;
import org.springframework.web.reactive.socket.client.ReactorNettyWebSocketClient;

import reactor.core.Disposable;
import reactor.core.publisher.Flux;

import edu.fra.uas.model.StockPrice;

@Service
public class StockPriceService {

    private static final Logger log = LoggerFactory.getLogger(StockPriceService.class);

    private static final String wsURL = "ws://localhost:8080/graphqlws";

    private WebSocketGraphQlClient wsGraphQlClient;

    public StockPriceService() {
        WebSocketClient wsClient = new ReactorNettyWebSocketClient();
        wsGraphQlClient = WebSocketGraphQlClient.builder(wsURL, wsClient).build();
    }

    public Disposable subscribeToStockPrice(String symbol, String identifier) {
        // GraphQL subscription syntax
        String document = "subscription { stockPrice(symbol: \"" + symbol + "\"){ symbol price timestamp }}";

        Flux<StockPrice> stockPriceFlux = wsGraphQlClient.document(document).retrieveSubscription("stockPrice").toEntity(StockPrice.class);
        Disposable disposable = stockPriceFlux.subscribe(stockPrice -> log.info("### " + identifier + " --> " + stockPrice.toString()));

        return disposable;
    }

}
