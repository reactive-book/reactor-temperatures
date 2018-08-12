package io.tpd.reactivebook.reactor.temperature.producer;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneOffset;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import io.tpd.reactivebook.reactor.temperature.domain.Measurement;

import static java.time.temporal.ChronoUnit.SECONDS;

public class StreamGenerator implements TemperatureGenerator {

  private static final Instant BASE_INSTANT =
    LocalDateTime.of(1983, Month.JANUARY, 15, 20, 22)
      .toInstant(ZoneOffset.UTC);
  private static final int BASE_TEMP = 2500;
  private static final int MAX_TEMP_VARIATION = 100;

  public Stream<Measurement> temperatureStream(final int items) {
    return IntStream.range(0, items).mapToObj(
      i -> new Measurement(
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
