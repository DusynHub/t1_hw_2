package mas.metricsproducer.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mas.metricsproducer.model.Metric;
import mas.metricsproducer.model.metrics.MeasurementType;
import mas.metricsproducer.model.metrics.MetricsOptions;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
@Slf4j
public class MetricsServiceImpl implements MetricsService {

    private final ScheduledExecutorService executorService
            = Executors.newSingleThreadScheduledExecutor();
    private final KafkaMetricService kafkaMetricService;

    @Override
    public void sendMessages(MetricsOptions metricsOptions) {
        log.info("");
        if (metricsOptions.getMeasurementTypes().length > 0) {
            executorService.scheduleAtFixedRate(
                    () -> kafkaMetricService.send(makeMetric(metricsOptions)),
                    0,
                    metricsOptions.getDelayInSeconds(),
                    TimeUnit.SECONDS
            );
        }
    }

    private double getRandomNumber(double min, double max) {
        return (Math.random() * (max - min)) + min;
    }


    private Metric makeMetric(MetricsOptions metricsOptions) {

        MeasurementType measurementType = getRandomMeasurement(
                metricsOptions.getMeasurementTypes()
        );

        Long sensorId = switch (measurementType) {
            case TEMPERATURE -> (long) getRandomNumber(1, 10);
            case VOLTAGE -> (long) getRandomNumber(11, 20);
            case POWER -> (long) getRandomNumber(21, 30);
        };

        double measurementValue = switch (measurementType) {
            case TEMPERATURE -> getRandomNumber(1, 100);
            case VOLTAGE -> getRandomNumber(1, 15);
            case POWER -> getRandomNumber(1, 5000);
        };

        Metric metric = new Metric();
        metric.setSensorId(sensorId);
        metric.setMeasurementType(measurementType);
        metric.setMeasurement(measurementValue);
        metric.setTimestamp(LocalDateTime.now());
        return metric;
    }

    private MeasurementType getRandomMeasurement(
            MeasurementType[] measurementTypes
    ) {
        int randomTypeId = (int) (Math.random() * measurementTypes.length);
        return measurementTypes[randomTypeId];
    }

}
