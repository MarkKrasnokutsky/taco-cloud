package com.mark.taco_cloud.config;

import com.mark.taco_cloud.domain.dto.Alert;
import com.mark.taco_cloud.domain.dto.GratuityIn;
import com.mark.taco_cloud.domain.dto.GratuityOut;
import com.mark.taco_cloud.domain.dto.StockQuote;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.rsocket.RSocketRequester;
import reactor.core.publisher.Flux;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.Instant;

@Configuration
@Slf4j
public class RSocketClientConfig {

    @Bean
    public ApplicationRunner sender(RSocketRequester.Builder requesterBuilder) {
        return args -> {
            RSocketRequester tcp = requesterBuilder.tcp("localhost", 7000);
            String stockSymbol = "XYZ";
            String who = "John";
            tcp
                    .route("greeting/{name}", who)
                    .data("Hello RSocket !")
                    .retrieveMono(String.class)
                    .subscribe(res -> log.info("Got a response: {}", res));
            tcp
                    .route("stock/{symbol}", stockSymbol)
                    .retrieveFlux(StockQuote.class)
                    .doOnNext(stockQuote -> {
                        log.info(
                                "Price of {}: {} (at {})",
                                stockQuote.getSymbol(),
                                stockQuote.getPrice(),
                                stockQuote.getTimestamp()
                        );
                    })
                    .subscribe();
            tcp
                    .route("alert")
                    .data(new Alert(Alert.Level.RED, "Mark", Instant.now()))
                    .send()
                    .subscribe();
            log.info("Alert sent");

            Flux<GratuityIn> gratuityInFlux =
                    Flux.fromArray(new GratuityIn[] {
                                    new GratuityIn(BigDecimal.valueOf(35.50), 18),
                                    new GratuityIn(BigDecimal.valueOf(10.00), 15),
                                    new GratuityIn(BigDecimal.valueOf(23.25), 20),
                                    new GratuityIn(BigDecimal.valueOf(52.75), 18),
                                    new GratuityIn(BigDecimal.valueOf(80.00), 15)
                            })
                            .delayElements(Duration.ofSeconds(1));

            tcp
                    .route("gratuity")
                    .data(gratuityInFlux)
                    .retrieveFlux(GratuityOut.class)
                    .subscribe(out -> {
                        log.info(out.getPercent() + " % gratuity on "
                        + out.getBillTotal() + " is "
                        + out.getGratuity());
                    });
        };
    }

}
