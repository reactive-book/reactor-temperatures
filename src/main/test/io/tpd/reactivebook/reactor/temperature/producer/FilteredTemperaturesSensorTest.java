package io.tpd.reactivebook.reactor.temperature.producer;

import java.time.Instant;
import java.util.stream.IntStream;

import org.junit.Before;
import org.junit.Test;

import io.tpd.reactivebook.reactor.temperature.domain.Measurement;

import reactor.test.StepVerifier;

import static org.assertj.core.api.Assertions.assertThat;

public class FilteredTemperaturesSensorTest {

  private FilteredTemperaturesSensor sensor;
  private TemperatureGenerator generator;

  @Before
  public void setup() {
    generator = items -> IntStream.of(1, 5, 4, 2, 6).mapToObj(
      i -> new Measurement(i, Instant.now())
    );
  }

  @Test
  public void filterTemperatures() {
    sensor = new FilteredTemperaturesSensor(4, generator);
    StepVerifier.create(sensor.temperatures())
      .expectSubscription()
      .assertNext(m -> assertThat(m.getTemperature()).isEqualTo(5))
      .assertNext(m -> assertThat(m.getTemperature()).isEqualTo(6))
      .verifyComplete();
  }
}
