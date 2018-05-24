package io.tpd.reactivebook.reactor;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneOffset;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.tpd.reactivebook.reactor.temperature.TemperatureRead;

import lombok.val;
import reactor.core.publisher.Flux;

import static java.time.temporal.ChronoUnit.*;

public class ReactorApp {

  private static final Instant BASE_INSTANT =
    LocalDateTime.of(1983, Month.JANUARY, 15, 20, 22)
      .toInstant(ZoneOffset.UTC);

  private static final Logger log =
    LoggerFactory.getLogger(ReactorApp.class);

  public static void main(String[] args) {
    val counter = new AtomicInteger(0);
    val temperatureFlux = Flux.fromStream(
      temperatureStream(100)
    ).doOnComplete(
      () -> log.info("Completed! Total {} passed the filter", counter.get())
    ).filter(
      t -> t.getTemperature() > 2450
    ).doOnNext(
      ignore -> counter.set(counter.get() + 1)
    ).doOnNext(
      item -> log.info("Calling onNext() with {}", item)
    ).replay().autoConnect();

    temperatureFlux.subscribe(
      temperatureRead -> log.info(temperatureRead.toString())
    );

    temperatureFlux.window(10).flatMap(temperatureReadFlux ->
      temperatureReadFlux.doOnNext(
        item -> log.info("Calling onNext() for averages with {}", item)
      ).reduce(
        (t1, t2) -> new TemperatureRead((t1.getTemperature() + t2.getTemperature()) / 2, t1.getTimestamp())
      )
    ).subscribe(temperatureRead -> log.info("Averages! {}", temperatureRead));
  }

  private static Stream<TemperatureRead> temperatureStream(final int items) {
    return IntStream.range(0, items).mapToObj(
      i -> new TemperatureRead(
        randomTemperature(),
        BASE_INSTANT.plus(i, SECONDS)
      )
    );
  }

  private static int randomTemperature() {
    return 2500 +
      ThreadLocalRandom.current().nextInt(100) *
        (ThreadLocalRandom.current().nextBoolean() ? -1 : 1);
  }

}
