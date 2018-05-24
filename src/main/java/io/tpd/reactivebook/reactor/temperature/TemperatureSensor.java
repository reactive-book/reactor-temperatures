package io.tpd.reactivebook.reactor.temperature;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneOffset;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.time.temporal.ChronoUnit.SECONDS;

public class TemperatureSensor {

  private static final Instant BASE_INSTANT =
    LocalDateTime.of(1983, Month.JANUARY, 15, 20, 22)
      .toInstant(ZoneOffset.UTC);
  private static final int BASE_TEMP = 2500;
  private static final int MAX_TEMP_VARIATION = 100;

  private static final Logger log =
    LoggerFactory.getLogger(TemperatureSensor.class);

  private final AtomicLong counter;

  public TemperatureSensor() {
    this.counter = new AtomicLong(0);
  }

  public Flux<TemperatureRead> temperatureFlux() {
    return Flux.fromStream(
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
    return BASE_TEMP +
      ThreadLocalRandom.current().nextInt(MAX_TEMP_VARIATION) *
        (ThreadLocalRandom.current().nextBoolean() ? -1 : 1);
  }
}
