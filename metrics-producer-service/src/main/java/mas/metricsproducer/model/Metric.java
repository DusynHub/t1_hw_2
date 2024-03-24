package mas.metricsproducer.model;

import lombok.Getter;
import lombok.Setter;
import mas.metricsproducer.model.metrics.MeasurementType;

import java.time.LocalDateTime;

@Getter
@Setter
public class Metric {

    private Long sensorId;
    private LocalDateTime timestamp;
    private double measurement;
    private MeasurementType measurementType;
}
